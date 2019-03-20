package org.ln.wechat.miniapp.model.user;

import lombok.Data;

import java.util.Date;

@Data
public class QcUser {
  private Integer id;

  private String nickName;

  private String password;

  private String actualName;

  private String mobile;

  private String openId;

  private String sessionKey;

  private Integer referrer;

  private Date creatTime;

  private Date modifyTime;

  private String otherRefrence;

  private String level;
}
