package org.ln.wechat.miniapp.service.impl.user;

import org.ln.wechat.miniapp.bean.user.QcUserRoleBean;
import org.ln.wechat.miniapp.mapper.UserRoleMapper;
import org.ln.wechat.miniapp.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRoleServiceImpl implements UserRoleService {

  @Autowired private UserRoleMapper userRoleMapper;

  @Override
  public List<QcUserRoleBean> getUserRoleByUserId(Integer id) {

    Map<String, Object> columMap = new HashMap<>();
    columMap.put("role_id", id);
    return userRoleMapper.selectByMap(columMap);
  }
}
