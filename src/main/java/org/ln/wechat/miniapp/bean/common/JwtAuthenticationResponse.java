package org.ln.wechat.miniapp.bean.common;

public class JwtAuthenticationResponse {

  private String accessToken;
  private String tokenType = "Bearer";
  private boolean success;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public JwtAuthenticationResponse(String accessToken, boolean success) {
    this.accessToken = accessToken;
    this.success = success;
  }
}
