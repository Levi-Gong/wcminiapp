package org.ln.wechat.miniapp.utils;

import lombok.Data;

@Data
public class CurrentUser {

  private String proxyLevel;
  private String OrderNo;
  private Double orderAmount;
  private String userName;
}
