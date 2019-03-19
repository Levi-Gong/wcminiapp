package org.ln.wechat.miniapp.biz.user;

public interface UserBiz {

  /**
   * 微信授权登录
   *
   * @param code
   * @return
   */
  Object loginByWechat(String code);
}
