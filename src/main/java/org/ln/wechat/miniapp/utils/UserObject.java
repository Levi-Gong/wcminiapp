package org.ln.wechat.miniapp.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class UserObject {

  private List<UserObserver> observers;

  public void registObserver(UserObserver userObserver) {

    if (observers == null) {
      this.observers = new ArrayList<>();
    }
    observers.add(userObserver);
  }

  public void removeObserver(UserObserver userObserver) {
    int index = observers.indexOf(userObserver);
    if (index > -1) {
      observers.remove(index);
    }
  }

  public void notifyALl() {
    for (UserObserver observer : observers) {
      observer.updateP(this);
    }
  }
}
