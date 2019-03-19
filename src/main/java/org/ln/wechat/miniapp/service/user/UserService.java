package org.ln.wechat.miniapp.service.user;

import org.ln.wechat.miniapp.bean.user.QcUserBean;

public interface UserService {
  QcUserBean findById(Integer userId);

  QcUserBean findUserByNickName(String nickName);
}
