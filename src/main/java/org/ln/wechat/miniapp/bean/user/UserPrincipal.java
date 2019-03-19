package org.ln.wechat.miniapp.bean.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/** 自定义用户认证实体 */
public class UserPrincipal implements UserDetails {

  private Integer id;

  private String nickName;

  private String username;

  @JsonIgnore private String mobile;

  @JsonIgnore private String password;

  private Collection<? extends GrantedAuthority> authorities;

  UserPrincipal(
      Integer id,
      String nickName,
      String username,
      String mobile,
      String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.nickName = nickName;
    this.username = username;
    this.mobile = mobile;
    this.password = password;
    this.authorities = authorities;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public static UserPrincipal create(QcUserBean user) {
    List<GrantedAuthority> authorities =
        user.getRoles()
            .stream()
            .map(roleBean -> new SimpleGrantedAuthority(roleBean.getRoleName()))
            .collect(Collectors.toList());
    return new UserPrincipal(
        user.getId(),
        user.getNickName(),
        user.getActualName(),
        user.getMobile(),
        user.getPassword(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserPrincipal that = (UserPrincipal) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }
}
