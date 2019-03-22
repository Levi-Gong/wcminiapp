package org.ln.wechat.miniapp.biz.user;

import org.ln.wechat.miniapp.bean.user.QcUserBean;

public interface UserBiz {

  /**
   * @param code js_code
   * @param scene 推荐人信息
   * @return
   */
  Object loginByWechat(String code, String scene);

  QcUserBean getUserById(Integer userId);

  int updateUser(QcUserBean qcUserBean);
}
