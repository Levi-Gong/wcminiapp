package org.ln.wechat.miniapp.service.impl.user;

import org.ln.wechat.miniapp.bean.user.QcRoleBean;
import org.ln.wechat.miniapp.mapper.RoleMapper;
import org.ln.wechat.miniapp.service.user.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
  @Autowired private RoleMapper roleMapper;

  @Override
  public List<QcRoleBean> getRolesByRoleIds(List<Integer> roleIds) {

    return roleMapper.selectBatchIds(roleIds);
  }
}
