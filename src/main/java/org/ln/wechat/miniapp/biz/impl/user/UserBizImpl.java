package org.ln.wechat.miniapp.biz.impl.user;

import org.ln.wechat.miniapp.bean.common.ApiConstant;
import org.ln.wechat.miniapp.bean.common.ApiResponse;
import org.ln.wechat.miniapp.bean.user.QcUserBean;
import org.ln.wechat.miniapp.biz.user.UserBiz;
import org.ln.wechat.miniapp.service.user.UserService;
import org.ln.wechat.miniapp.utils.EmptyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserBizImpl implements UserBiz {

  @Autowired private UserService userService;

  @Value("${wechat.miniapp.app.id}")
  private String appId;

  @Value("${wechat.minapp.app.secret}")
  private String appSecrect;

  /**
   * 微信授权登录
   *
   * @param code
   * @return
   */
  @Override
  public Object loginByWechat(String code) {

    //      判断当前code是否为空
    ApiResponse<String> response = new ApiResponse<>();

    if (EmptyUtils.isEmpty(code)) {
      response.setMessage("请先授权微信登录！");
      response.setSuccess(ApiConstant.FALSE);
      return response;
    }
    //      请求微信服务器，获取openid 和 sessionKey

    return null;
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
