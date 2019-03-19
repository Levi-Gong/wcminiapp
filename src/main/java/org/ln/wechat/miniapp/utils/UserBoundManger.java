package org.ln.wechat.miniapp.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserBoundManger extends UserObject {

  private CurrentUser currentUser;

  private boolean isNewOrder = false;

  private void hasNewOrder(boolean isNewOrder) {
    this.isNewOrder = isNewOrder;
    if (isNewOrder) {
      System.out.println("当前用户：" + currentUser.getUserName() + "新增一笔订单");
      if ("1".equals(currentUser.getProxyLevel())) {
        System.out.println("一级代理，不做返利操作");
        isNewOrder = false;
        return;
      }
      System.out.println(currentUser.getProxyLevel() + "级代理，执行新增上级返利");
      notifyALl();
      isNewOrder = false;
    }
  }

  public static void main(String[] args) {
    UserBoundManger userBoundManger = new UserBoundManger();
    SecondUser secondUser = new SecondUser();
    CurrentUser currentUser = new CurrentUser();
    currentUser.setUserName("levi");
    currentUser.setProxyLevel("2");
    currentUser.setOrderAmount(100.0);
    userBoundManger.setCurrentUser(currentUser);
    List<UserObserver> allParntes = new ArrayList<>();
    allParntes.add(secondUser);
    userBoundManger.setObservers(allParntes);
    userBoundManger.hasNewOrder(true);
  }
}
