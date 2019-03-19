package org.ln.wechat.miniapp.bean.user;

import lombok.Data;

import java.util.Date;

@Data
public class QcUserRoleBean {
  private Integer id;

  private Integer userId;

  private Integer roleId;

  private Date createTime;

  private Date modityTime;
}
