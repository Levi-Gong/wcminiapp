package org.ln.wechat.miniapp.biz.impl.user;

import org.ln.wechat.miniapp.bean.user.QcUserBean;
import org.ln.wechat.miniapp.biz.user.UserBiz;
import org.ln.wechat.miniapp.service.user.UserService;
import org.ln.wechat.miniapp.utils.EmptyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBizImpl implements UserBiz {

  @Autowired private UserService userService;
  /**
   * 微信授权登录
   *
   * @param code
   * @return
   */
  @Override
  public Object loginByWechat(String code) {

    //      判断当前code是否为空
    if (EmptyUtils.isEmpty(code)) {
      return "None_User";
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
