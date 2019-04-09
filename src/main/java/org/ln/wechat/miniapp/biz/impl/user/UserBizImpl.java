package org.ln.wechat.miniapp.biz.impl.user;

import com.alibaba.fastjson.JSONObject;
import org.ln.wechat.miniapp.bean.common.ApiConstant;
import org.ln.wechat.miniapp.bean.common.ApiResponse;
import org.ln.wechat.miniapp.bean.common.HttpClientResult;
import org.ln.wechat.miniapp.bean.jms.UserLevelMessage;
import org.ln.wechat.miniapp.bean.user.QcUserBean;
import org.ln.wechat.miniapp.bean.user.UserPrincipal;
import org.ln.wechat.miniapp.biz.user.UserBiz;
import org.ln.wechat.miniapp.service.user.UserService;
import org.ln.wechat.miniapp.system.message.MessageProductor;
import org.ln.wechat.miniapp.system.security.JwtTokenProvider;
import org.ln.wechat.miniapp.utils.EmptyUtils;
import org.ln.wechat.miniapp.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserBizImpl implements UserBiz {

  public static final Logger log = LoggerFactory.getLogger(UserBizImpl.class);

  public static final String WC_ERROR_SYSTEM_BUSY = "-1";
  public static final String WC_SUCCESS = "0";
  public static final String WC_ERROR_CODE_INVALID = "40029";
  public static final String WC_ERROR_INTERFACE_INVOK_LIMIT = "45011";
  public static final String SYSTEM_ERROR_NONE_USER = "00000";

  @Autowired private UserService userService;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private MessageProductor messageProductor;
  @Autowired private JwtTokenProvider jwtTokenProvider;
  @Autowired private AuthenticationManager authenticationManager;

  @Value("${wechat.miniapp.app.id}")
  private String appId;

  @Value("${wechat.minapp.app.secret}")
  private String appSecrect;

  @Value("${system.user.level.limit}")
  private String sysDefaultLevel;

  @Value("${system.user.default.password}")
  private String defaultPwd;

  /**
   * 微信授权登录
   *
   * @param code
   * @return
   */
  @Override
  public ApiResponse<String> loginByWechat(String code) {

    //      判断当前code是否为空
    ApiResponse<String> response = new ApiResponse<>();
    response.setSuccess(ApiConstant.FALSE);
    if (EmptyUtils.isEmpty(code)) {
      response.setMessage("请先授权微信登录！");
      response.setCode(WC_ERROR_CODE_INVALID);
      return response;
    }

    try {

      JSONObject codeSession = codeToSession(code);
      String errorCode = codeSession.getString("errcode");
      if (EmptyUtils.isEmpty(errorCode)) {

        String openId = codeSession.getString("openid");
        String sessionKey = codeSession.getString("session_key");
        QcUserBean existUser = userService.findUserByOpenId(openId);
        if (EmptyUtils.isNotEmpty(existUser)) {
          QcUserBean updateBean = new QcUserBean();
          updateBean.setSessionKey(sessionKey);
          updateBean.setModifyTime(new Date());
          updateBean.setId(existUser.getId());
          userService.updateUserSelectiveById(updateBean);
          String token = jwtTokenProvider.generateToken(Integer.toString(existUser.getId()));
          response.setSuccess(ApiConstant.TRUE);
          response.setData(token);

        } else {
          response.setCode(SYSTEM_ERROR_NONE_USER);
          response.setMessage("新用户，需先绑定微信注册！");
        }

      } else {
        response.setCode(WC_ERROR_SYSTEM_BUSY);
        response.setMessage("微信服务器忙，请稍后再试！");
      }

    } catch (Exception e) {
      log.error("调用微信服务端发生异常！---------异常原因：" + e.getMessage());
      response.setMessage("调用微信服务端发生异常！");
    }
    return response;
  }

  @Override
  public QcUserBean getUserById(Integer userId) {
    return userService.findById(userId);
  }

  @Override
  public int updateUser(QcUserBean qcUserBean) {
    return userService.updateUserSelectiveById(qcUserBean);
  }

  @Override
  public ApiResponse<QcUserBean> getUserDetail() {

    ApiResponse<QcUserBean> response = new ApiResponse<>();
    response.setMessage(ApiConstant.ERROR);
    response.setSuccess(ApiConstant.FALSE);
    UserPrincipal userPrincipal =
        (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    int userId = userPrincipal.getId();
    QcUserBean qcUserBean = userService.findById(userId);
    if (EmptyUtils.isNotEmpty(qcUserBean)) {
      response.setSuccess(ApiConstant.TRUE);
      response.setMessage(ApiConstant.SUCCESS);
      response.setData(qcUserBean);
    }
    return response;
  }

  @Override
  public ApiResponse<String> saveUserWithWechat(QcUserBean user) {
    ApiResponse<String> response = new ApiResponse<>();
    response.setSuccess(ApiConstant.FALSE);
    if (EmptyUtils.isEmpty(user.getWechatCode())) {
      response.setMessage("请先授权微信登录！");
      response.setCode(WC_ERROR_CODE_INVALID);
      return response;
    }
    try {

      JSONObject codeSession = codeToSession(user.getWechatCode());
      String errorCode = codeSession.getString("errcode");
      if (EmptyUtils.isEmpty(errorCode)) {
        String openId = codeSession.getString("openid");
        String sessionKey = codeSession.getString("session_key");
        user.setCreateTime(new Date());
        user.setPassword(passwordEncoder.encode(defaultPwd));
        user.setLevel(sysDefaultLevel);
        user.setSessionKey(sessionKey);
        user.setOpenId(openId);
        int saveCount = userService.saveUser(user);

        if (EmptyUtils.isNotEmpty(user.getReferrer()) && saveCount > 0) {
          UserLevelMessage userLevelMessage =
              UserLevelMessage.builder(user.getId(), user.getLevel(), user.getReferrer());
          messageProductor.sendMessage("INBOUND.USER.LEVEL.UPDATE", userLevelMessage);
        }
        String token = jwtTokenProvider.generateToken(Integer.toString(user.getId()));
        response.setSuccess(ApiConstant.TRUE);
        response.setData(token);
        response.setMessage("注册成功!");

      } else {
        response.setCode(WC_ERROR_SYSTEM_BUSY);
        response.setMessage("微信服务器忙，请稍后再试！");
      }
    } catch (Exception e) {
      log.error("调用微信服务端发生异常！---------异常原因：" + e.getMessage());
      response.setMessage("调用微信服务端发生异常！");
    }
    return response;
  }

  private JSONObject codeToSession(String code) throws Exception {

    Map<String, String> params = new HashMap<>();
    params.put("appid", appId);
    params.put("secret", appSecrect);
    params.put("js_code", code);
    params.put("grant_type", "authorization_code");
    HttpClientResult result =
        HttpClientUtils.doGet("https://api.weixin.qq.com/sns/jscode2session", params);
    JSONObject codeSession = JSONObject.parseObject(result.getContent());
    return codeSession;
  }
}
