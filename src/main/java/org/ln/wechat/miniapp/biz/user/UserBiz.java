package org.ln.wechat.miniapp.biz.user;

import org.ln.wechat.miniapp.bean.user.QcUserBean;

public interface UserBiz {

  /**
   * 微信授权登录
   *
   * @param code
   * @return
   */
  Object loginByWechat(String code);

  QcUserBean getUserById(Integer userId);

  int updateUser(QcUserBean qcUserBean);
}
