package org.ln.wechat.miniapp.bean.common;

public class ApiResponse<T> {

  private Boolean success;
  private String message;
  private String code;
  private T data;

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ApiResponse(Boolean success, String message, String code) {
    this.success = success;
    this.message = message;
    this.code = code;
  }

  public ApiResponse(Boolean success, String code, String message, T data) {
    this.success = success;
    this.code = code;
    this.data = data;
    this.message = message;
  }

  public ApiResponse() {}
}
