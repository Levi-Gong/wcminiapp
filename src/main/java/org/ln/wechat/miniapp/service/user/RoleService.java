package org.ln.wechat.miniapp.service.user;

import org.ln.wechat.miniapp.bean.user.QcRoleBean;

import java.util.List;

public interface RoleService {
  List<QcRoleBean> getRolesByRoleIds(List<Integer> roleIds);
}
