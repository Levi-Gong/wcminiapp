package org.ln.wechat.miniapp.model.user;

import java.util.Date;

public class QcRole {
  private Integer id;

  private String roleName;

  private Integer superiorRoleId;

  private Date createTime;

  private Date modifyTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public Integer getSuperiorRoleId() {
    return superiorRoleId;
  }

  public void setSuperiorRoleId(Integer superiorRoleId) {
    this.superiorRoleId = superiorRoleId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }
}
