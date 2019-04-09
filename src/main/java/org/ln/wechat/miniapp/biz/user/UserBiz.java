package org.ln.wechat.miniapp.biz.user;

import org.ln.wechat.miniapp.bean.common.ApiResponse;
import org.ln.wechat.miniapp.bean.user.QcUserBean;

public interface UserBiz {

  /**
   * @param code js_code
   * @param scene 推荐人信息
   * @return
   */
  ApiResponse<String> loginByWechat(String code);

  QcUserBean getUserById(Integer userId);

  int updateUser(QcUserBean qcUserBean);

  ApiResponse<QcUserBean> getUserDetail();

  ApiResponse<String> saveUserWithWechat(QcUserBean user);
}
