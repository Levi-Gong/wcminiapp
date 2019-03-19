package org.ln.wechat.miniapp.utils;

public class CommonUser implements UserObserver {

  @Override
  public void updateP(UserObject userObject) {

    System.out.printf("当前没有任何代理，不执行订单插入操作");
  }
}
