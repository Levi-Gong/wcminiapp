package org.ln.wechat.miniapp.bean.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QcRoleBean implements Serializable {
  private Integer id;

  private String roleName;

  private Integer superiorRoleId;

  private Date createTime;

  private Date modifyTime;
}
