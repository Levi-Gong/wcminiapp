package org.ln.wechat.miniapp.service.user;

import org.ln.wechat.miniapp.bean.user.QcUserRoleBean;

import java.util.List;

public interface UserRoleService {
  List<QcUserRoleBean> getUserRoleByUserId(Integer id);
}
