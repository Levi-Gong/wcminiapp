package org.ln.wechat.miniapp.model.user;

import java.util.Date;

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

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getActualName() {
    return actualName;
  }

  public void setActualName(String actualName) {
    this.actualName = actualName;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public String getSessionKey() {
    return sessionKey;
  }

  public void setSessionKey(String sessionKey) {
    this.sessionKey = sessionKey;
  }

  public Integer getReferrer() {
    return referrer;
  }

  public void setReferrer(Integer referrer) {
    this.referrer = referrer;
  }

  public Date getCreatTime() {
    return creatTime;
  }

  public void setCreatTime(Date creatTime) {
    this.creatTime = creatTime;
  }

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public String getOtherRefrence() {
    return otherRefrence;
  }

  public void setOtherRefrence(String otherRefrence) {
    this.otherRefrence = otherRefrence;
  }
}
