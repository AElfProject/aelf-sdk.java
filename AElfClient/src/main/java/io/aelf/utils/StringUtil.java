package io.aelf.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class StringUtil {

  /**
   * StringUtil constructor.
   */
  public StringUtil() {
  }

  /**
   * Gets the number of substrings.
   * @param originalStr not blank
   * @param subStr not blank
   * @return int
   */
  public static int getSubStrCount(String originalStr,String subStr) {
    int count = 0;
    if (!isBlank(originalStr) && !isBlank(subStr)) {
      int index = originalStr.indexOf(subStr);
      if (index != -1) {
        ++count;
        count += getSubStrCount(originalStr.substring(index + subStr.length()), subStr);
      }
    }

    return count;
  }

  /**
   * String query.
   * @param resultList not blank
   * @param key not blank
   * @param value not blank
   * @return string
   */
  public static String toSelectStr(List<Map<?,?>> resultList, String key, String value) {
    StringBuilder selectStr = new StringBuilder();

    for (Map<?,?> map : resultList) {
      selectStr.append("<option value='").append(map.get(key)).append("'>").append(map.get(value)).append("</option>");
    }

    return selectStr.toString();
  }

  /**
   * String formatting.
   * @param object not blank
   * @return string
   */
  public static String toString(Object object) {
    return object != null ? object.toString().trim() : "";
  }

  /**
   * The first letter is lowercase.
   * @param str not blank
   * @return string
   */
  public static String firstCharToLowerCase(String str) {
    char firstChar = str.charAt(0);
    String tail = str.substring(1);
    str = Character.toLowerCase(firstChar) + tail;
    return str;
  }

  /**
   * Capitalize the first letter.
   * @param str not blank
   * @return string
   */
  public static String firstCharToUpperCase(String str) {
    char firstChar = str.charAt(0);
    String tail = str.substring(1);
    str = Character.toUpperCase(firstChar) + tail;
    return str;
  }

  /**
   * The string is empty.
   * @param str not blank
   * @return boolean
   */
  public static boolean isBlank(String str) {
    return str == null || "".equals(str.trim());
  }

  /**
   * The string is not empty.
   * @param str not blank
   * @return boolean
   */
  public static boolean notBlank(String str) {
    return str != null && !"".equals(str.trim());
  }

  /**
   * The string is not empty.
   * @param strings not blank
   * @return boolean
   */
  public static boolean notBlank(String... strings) {
    if (strings == null) {
      return false;
    } else {
      int var3 = strings.length;

      for (String str : strings) {
        if (str == null || "".equals(str.trim())) {
          return false;
        }
      }

      return true;
    }
  }

  /**
   * The string is not null.
   * @param params not blank
   * @return boolean
   */
  public static boolean notNull(Object... params) {
    if (params == null) {
      return false;
    } else {
      int var3 = params.length;

      for (Object obj : params) {
        if (obj == null) {
          return false;
        }
      }

      return true;
    }
  }

  /**
   * The string is a null character.
   * @param string not blank
   * @return string
   */
  public static String nvl(String string) {
    return nvl(string, "");
  }

  /**
   * The string is a null character.
   * @param o not blank
   * @return string
   */
  public static String nvl(Object o) {
    return nvl(toString(o), "");
  }

  /**
   * The string is a null character.
   * @param string not blank
   * @param string2 not blank
   * @return string
   */
  public static String nvl(String string, String string2) {
    return isBlank(string) ? string2 : string;
  }

  /**
   * String splitting.
   * @param list not blank
   * @param separator not blank
   * @param key not blank
   * @return string
   */
  public static String toStringBySeparator(List<Map<String,?>> list, String separator, String key) {
    if (list != null && !list.isEmpty()) {
      StringBuilder str = new StringBuilder();

      for (Map<String,?> map : list) {
        str.append(map.get(key)).append(",");
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

  /**
   * String default content.
   * @param str not blank
   * @return Str
   */
  public static String defaultString(String str) {
    return str == null ? "" : str;
  }

  /**
   * get str.
   * @param str not blank
   * @return Object class
   */
  public static Object minusOne(String str) {
    if (str != null && !"".equals(str)) {
      str = str.substring(0, str.length() - 1);
    }

    return str;
  }

  /**
   * String to array.
   * @param str not blank
   * @param len less zero
   * @return List string Object
   */
  public static List<String> toList(String str, int len) {
    List<String> result = new ArrayList<>();
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
