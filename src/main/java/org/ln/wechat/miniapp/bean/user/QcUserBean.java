package org.ln.wechat.miniapp.bean.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class QcUserBean implements Serializable {
  private Integer id;

  private String nickName;

  private String password;

  private String actualName;

  private String mobile;

  private String openId;

  private String sessionKey;

  private Integer referrer;

  private Date createTime;

  private Date modifyTime;

  private String otherRefrence;

  private List<QcRoleBean> roles;
}
