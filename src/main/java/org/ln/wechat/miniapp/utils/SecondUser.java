package org.ln.wechat.miniapp.utils;

public class SecondUser implements UserObserver {

  @Override
  public void updateP(UserObject userObject) {

    UserBoundManger userBoundManger = (UserBoundManger) userObject;
    CurrentUser currentUser = userBoundManger.getCurrentUser();
    String curryUserLevel = currentUser.getProxyLevel();
    System.out.println("当前用户的代理等级是：" + curryUserLevel + "--------");

    if ("2".equals(curryUserLevel)) {
      System.out.println("找出所有一级供应商，向他们增加一条订单记录");
      System.out.println("订单记录生成，金额比例：0.8 实际金额：" + Double.toString(currentUser.getOrderAmount()));
    } else if ("3".equals(curryUserLevel)) {
      System.out.println("找出所有一级,二级供应商，向他们增加一条订单记录");
      System.out.println(
          "订单记录生成，金额比例：0.8-0.6 实际金额：" + Double.toString(currentUser.getOrderAmount()));
    } else if ("1".equals(curryUserLevel)) {
      System.out.println("不增加任何抽成记录");
    }
  }
}
