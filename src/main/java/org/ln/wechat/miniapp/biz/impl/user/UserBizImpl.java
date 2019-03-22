package org.ln.wechat.miniapp.biz.impl.user;

import com.alibaba.fastjson.JSONObject;
import org.ln.wechat.miniapp.bean.common.ApiConstant;
import org.ln.wechat.miniapp.bean.common.ApiResponse;
import org.ln.wechat.miniapp.bean.common.HttpClientResult;
import org.ln.wechat.miniapp.bean.jms.UserLevelMessage;
import org.ln.wechat.miniapp.bean.user.QcUserBean;
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

  public static final String DEFAULT_PASSWORDE = "123456";

  @Autowired private UserService userService;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private MessageProductor messageProductor;
  @Autowired private JwtTokenProvider jwtTokenProvider;
  @Autowired private AuthenticationManager authenticationManager;

  @Value("${wechat.miniapp.app.id}")
  private String appId;

  @Value("${wechat.minapp.app.secret}")
  private String appSecrect;

  @Value("system.user.level.limit")
  private String sysDefaultLevel;

  /**
   * 微信授权登录
   *
   * @param code
   * @return
   */
  @Override
  public Object loginByWechat(String code, String scene) {

    //      判断当前code是否为空
    ApiResponse<String> response = new ApiResponse<>();
    response.setSuccess(ApiConstant.FALSE);
    if (EmptyUtils.isEmpty(code)) {
      response.setMessage("请先授权微信登录！");
      return response;
    }
    //      请求微信服务器，获取openid 和 sessionKey
    Map<String, String> params = new HashMap<>();
    params.put("appid", appId);
    params.put("secret", appSecrect);
    params.put("js_code", code);
    params.put("grant_type", "authorization_code");
    try {
      HttpClientResult result =
          HttpClientUtils.doGet("https://api.weixin.qq.com/sns/jscode2session", params);
      if ("200".equals(String.valueOf(result.getCode()))) {
        JSONObject codeSession = JSONObject.parseObject(result.getContent());
        if (EmptyUtils.isEmpty(codeSession.get("errcode"))) {

          String openId = codeSession.getString("openid");
          String sessionKey = codeSession.getString("session_key");
          // 查找用户信息，看是否有用户信息 如果存在更新seesion
          QcUserBean userBean = userService.findUserByOpenId(openId);
          if (EmptyUtils.isEmpty(userBean)) {
            // 注册新用户
            userBean = new QcUserBean();
            userBean.setCreateTime(new Date());
            userBean.setNickName(openId);
            userBean.setPassword(passwordEncoder.encode(DEFAULT_PASSWORDE));
            userBean.setLevel(sysDefaultLevel);
            userBean.setSessionKey(sessionKey);
            userBean.setOpenId(openId);
            int saveCount = userService.saveUser(userBean);
            if (EmptyUtils.isNotEmpty(scene) && saveCount > 0) {
              UserLevelMessage userLevelMessage =
                  UserLevelMessage.builder(
                      userBean.getId(), userBean.getLevel(), userBean.getReferrer());
              messageProductor.sendMessage("INBOUND.USER.LEVEL.UPDATE", userLevelMessage);
            }

          } else {
            QcUserBean updateBean = new QcUserBean();
            updateBean.setSessionKey(sessionKey);
            updateBean.setModifyTime(new Date());
            userService.updateUserSelectiveById(updateBean);
          }
          Authentication authentication =
              authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(
                      userBean.getNickName(), userBean.getPassword()));
          SecurityContextHolder.getContext().setAuthentication(authentication);
          String token = jwtTokenProvider.generateToken(authentication);
          response.setSuccess(ApiConstant.TRUE);
          response.setData(token);
          response.setMessage("登录成功!");
        } else {

          response.setMessage("调用微信服务端发生异常！异常信息：" + codeSession.get("errmsg"));
        }
      }

    } catch (Exception e) {
      log.error("调用微信服务端发生异常！---------异常原因：" + e.getMessage());
      response.setMessage("调用微信服务端发生异常！");
      return response;
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
}
