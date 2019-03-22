package org.ln.wechat.miniapp.service.user;

import org.ln.wechat.miniapp.bean.user.QcUserBean;

public interface UserService {
  QcUserBean findById(Integer userId);

  QcUserBean findUserByNickName(String nickName);

  int updateUserSelectiveById(QcUserBean qcUserBean);

  /**
   * 根据微信 open_id查找用户
   *
   * @param openId
   * @return
   */
  QcUserBean findUserByOpenId(String openId);

  int saveUser(QcUserBean userBean);
}
