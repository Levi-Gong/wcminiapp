package org.ln.wechat.miniapp.system.security;

import org.ln.wechat.miniapp.bean.user.QcUserBean;
import org.ln.wechat.miniapp.bean.user.QcUserRoleBean;
import org.ln.wechat.miniapp.bean.user.UserPrincipal;
import org.ln.wechat.miniapp.service.user.RoleService;
import org.ln.wechat.miniapp.service.user.UserRoleService;
import org.ln.wechat.miniapp.service.user.UserService;
import org.ln.wechat.miniapp.utils.EmptyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class CustomUserDetailService implements UserDetailsService {

  @Autowired private UserService userService;
  @Autowired private UserRoleService userRoleService;
  @Autowired private RoleService roleService;

  @Override
  public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
    QcUserBean userBean = userService.findUserByNickName(nickName);
    return getUserDetails(userBean);
  }

  @Transactional
  public UserDetails loadUserById(Integer userId) {

    QcUserBean userBean = userService.findById(userId);
    return getUserDetails(userBean);
  }

  private UserDetails getUserDetails(QcUserBean userBean) {
    if (userBean == null) {
      throw new UsernameNotFoundException("用户信息不存在！");
    }
    setUserRole(userBean);
    return UserPrincipal.create(userBean);
  }

  private void setUserRole(QcUserBean userBean) {

    List<QcUserRoleBean> userRoles = userRoleService.getUserRoleByUserId(userBean.getId());
    if (EmptyUtils.isEmpty(userRoles)) {
      userBean.setRoles(new ArrayList<>());
      return;
    }
    List<Integer> roleIds = new ArrayList<>();
    for (QcUserRoleBean qcUserRoleBean : userRoles) {
      roleIds.add(qcUserRoleBean.getRoleId());
    }
    userBean.setRoles(roleService.getRolesByRoleIds(roleIds));
  }
}
