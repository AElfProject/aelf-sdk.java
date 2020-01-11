package io.aelf.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StringUtil {

  public StringUtil() {
  }

  public static int getSubStrCount(String original_str, String sub_str) {
    int count = 0;
    if (!isBlank(original_str) && !isBlank(sub_str)) {
      int index = original_str.indexOf(sub_str);
      if (index != -1) {
        ++count;
        count += getSubStrCount(original_str.substring(index + sub_str.length()), sub_str);
      }
    }

    return count;
  }

  public static String toSelectStr(List resultList, String key, String value) {
    StringBuilder selectStr = new StringBuilder();
    Iterator var5 = resultList.iterator();

    while (var5.hasNext()) {
      Object object = var5.next();
      Map _obj = (Map) object;
      selectStr.append("<option value='" + _obj.get(key) + "'>" + _obj.get(value) + "</option>");
    }

    return selectStr.toString();
  }

  public static String toString(Object object) {
    return object != null ? object.toString().trim() : "";
  }

  public static String firstCharToLowerCase(String str) {
    Character firstChar = str.charAt(0);
    String tail = str.substring(1);
    str = Character.toLowerCase(firstChar) + tail;
    return str;
  }

  public static String firstCharToUpperCase(String str) {
    Character firstChar = str.charAt(0);
    String tail = str.substring(1);
    str = Character.toUpperCase(firstChar) + tail;
    return str;
  }

  public static boolean isBlank(String str) {
    return str == null || "".equals(str.trim());
  }

  public static boolean notBlank(String str) {
    return str != null && !"".equals(str.trim());
  }

  public static boolean notBlank(String... strings) {
    if (strings == null) {
      return false;
    } else {
      String[] var4 = strings;
      int var3 = strings.length;

      for (int var2 = 0; var2 < var3; ++var2) {
        String str = var4[var2];
        if (str == null || "".equals(str.trim())) {
          return false;
        }
      }

      return true;
    }
  }

  public static boolean notNull(Object... paras) {
    if (paras == null) {
      return false;
    } else {
      Object[] var4 = paras;
      int var3 = paras.length;

      for (int var2 = 0; var2 < var3; ++var2) {
        Object obj = var4[var2];
        if (obj == null) {
          return false;
        }
      }

      return true;
    }
  }

  public static String nvl(String string) {
    return nvl(string, "");
  }

  public static String nvl(Object o) {
    return nvl(toString(o), "");
  }

  public static String nvl(String string, String string2) {
    return isBlank(string) ? string2 : string;
  }

  public static String toStringBySeparator(List<Map> list, String separator, String key) {
    if (list != null && !list.isEmpty()) {
      StringBuffer str = new StringBuffer();
      Iterator var5 = list.iterator();

      while (var5.hasNext()) {
        Map map = (Map) var5.next();
        str.append(map.get(key) + ",");
      }

      int length = str.length();
      if (length > 1) {
        str.replace(length - 1, length, "");
      }

      return str.toString();
    } else {
      return "";
    }
  }

  public static String defaultString(String str) {
    return str == null ? "" : str;
  }

  public static Object minusOne(String str) {
    if (str != null && !"".equals(str)) {
      str = str.substring(0, str.length() - 1);
    }

    return str;
  }

  public static List<String> toList(String str, int len) {
    List<String> result = new ArrayList();
    int end = len;
    int start = 0;

    while (start < str.length()) {
      if (end > str.length()) {
        end = str.length();
      }

      String s = str.substring(start, end);
      start = end;
      end += len;
      result.add(s);
    }

    return result;
  }
}
