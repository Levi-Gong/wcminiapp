package org.ln.wechat.miniapp.utils;

import java.util.Collection;
import java.util.Map;

public class EmptyUtils {
  private EmptyUtils() {}

  public static boolean hasOneEmpty(String[] args) {
    if (args == null) {
      return false;
    } else {
      for (int i = 0; i < args.length; ++i) {
        if (isAnyoneEmpty(args[i])) {
          return true;
        }
      }

      return false;
    }
  }

  public static boolean isNotEmpty(Object str) {
    return !isEmpty(str);
  }

  public static boolean isTrimEmpty(String str) {
    return isAnyoneEmpty(str) ? true : str.trim().length() < 1;
  }

  public static boolean isEqual(Object obj1, Object obj2) {
    if (obj1 == null && obj2 == null) {
      return true;
    } else if (obj1 == null && obj2 != null) {
      return false;
    } else {
      return obj2 == null && obj1 != null ? false : obj1.equals(obj2);
    }
  }

  public static boolean isEmpty(Object obj) {
    return isAnyoneEmpty(obj);
  }

  public static boolean isAnyoneEmpty(Object obj) {
    if (obj == null) {
      return obj == null;
    } else if (obj instanceof Collection) {
      return ((Collection) obj).isEmpty();
    } else if (obj instanceof String) {
      return obj.toString().length() == 0;
    } else if (obj.getClass().isArray()) {
      return ((Object[]) obj).length == 0;
    } else if (obj instanceof Map) {
      return ((Map) obj).isEmpty();
    } else if (obj instanceof StringBuilder) {
      return ((StringBuilder) obj).length() == 0;
    } else if (obj instanceof StringBuffer) {
      return ((StringBuffer) obj).length() == 0;
    } else {
      return false;
    }
  }
}
