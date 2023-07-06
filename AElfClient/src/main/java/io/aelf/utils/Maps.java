//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.aelf.utils;

import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;

public final class Maps {

  private static MapEntry entry = new MapEntry(20);
  private static LinkedMapEntry linkedMapEntry = new LinkedMapEntry(20);

  public Maps() {
  }

  public static MapEntry newMap() {
    return (MapEntry) entry.clone();
  }

  public static MapEntry newMapEntry() {
    return (MapEntry) entry.clone();
  }

  public static Map newHashMap() {
    return (MapEntry) entry.clone();
  }

  public static LinkedMapEntry newLinkedHashMap() {
    return (LinkedMapEntry) linkedMapEntry.clone();
  }

  public static LinkedMapEntry newLinkedMapEntry() {
    return (LinkedMapEntry) linkedMapEntry.clone();
  }

  public static TreeMap newTreeMap() {
    return new TreeMap();
  }

  public static EnumMap newEnumMap(Map map) {
    return new EnumMap(map);
  }

  public static WeakHashMap newWeakHashMap() {
    return new WeakHashMap(20);
  }

  public static Hashtable newHashtable() {
    return new Hashtable(20);
  }

  public static IdentityHashMap newIdentityHashMap() {
    return new IdentityHashMap(20);
  }

  public static ConcurrentHashMap newConcurrentHashMap() {
    return new ConcurrentHashMap(20);
  }

  /**
   * removeKeys.
   * @param map not blank
   * @param removeKeys not blank
   * @param <T> T
   * @return T extends Map
   */
  public static <T extends Map> T removeKeys(T map, Object... removeKeys) {
    Object[] var5 = removeKeys;
    int var4 = removeKeys.length;

    for (int var3 = 0; var3 < var4; ++var3) {
      Object removeKey = var5[var3];
      map.remove(removeKey);
    }

    return map;
  }

  /**
   * newMapHasKeys.
   * @param map not blank
   * @param keys not blank
   * @param <T> T
   * @return T extends Map
   */
  public static <T extends Map> Map<Object, Object> newMapHasKeys(T map, Object... keys) {
    Map<Object, Object> newMap = null;
    if (map instanceof HashMap) {
      newMap = (HashMap<Object, Object>) ((HashMap) map).clone();
      newMap.clear();
    } else {
      newMap = new HashMap<>(20);
    }

    Object[] var6 = keys;
    int var5 = keys.length;

    for (int var4 = 0; var4 < var5; ++var4) {
      Object key = var6[var4];
      newMap.put(key, map.get(key));
    }

    return newMap;
  }

  /**
   * isBlankKey.
   * @param map not blank
   * @param key not blank
   * @return boolean
   */
  public static boolean isBlankKey(Map map, String key) {
    return !map.containsKey(key) || map.get(key) == null || StringUtils
        .isBlank(map.get(key).toString());
  }

  /**
   * isNotBlankKey.
   * @param map not blank
   * @param key not blank
   * @return boolean
   */
  public static boolean isNotBlankKey(Map map, String key) {
    return map.containsKey(key) && map.get(key) != null && StringUtils
        .isNotBlank(map.get(key).toString());
  }

  /**
   * isBlankKeys.
   * @param map not blank
   * @param keys not blank
   * @return boolean
   */
  public static boolean isBlankKeys(Map map, String... keys) {
    String[] var5 = keys;
    int var4 = keys.length;

    for (int var3 = 0; var3 < var4; ++var3) {
      String key = var5[var3];
      if (isBlankKey(map, key)) {
        return true;
      }
    }

    return false;
  }

  /**
   * isNotBlankKeys.
   * @param map not blank
   * @param keys not blank
   * @return boolean
   */
  public static boolean isNotBlankKeys(Map map, String... keys) {
    String[] var5 = keys;
    int var4 = keys.length;

    for (int var3 = 0; var3 < var4; ++var3) {
      String key = var5[var3];
      if (isNotBlankKey(map, key)) {
        return true;
      }
    }

    return false;
  }

  /**
   * cloneMapEntry.
   * @param map not blank
   * @param <K> not blank
   * @param <V> not blank
   * @return MapEntry
   */
  public static <K, V> MapEntry<K, V> cloneMapEntry(Map<K, V> map) {
    return cloneMapEntry(map, true);
  }

  /**
   * cloneMapEntry.
   * @param map not blank
   * @param rs not blank
   * @param <K> not blank
   * @param <V> not blank
   * @return MapEntry
   */
  public static <K, V> MapEntry<K, V> cloneMapEntry(Map<K, V> map, boolean rs) {
    if (!rs && map instanceof MapEntry) {
      return (MapEntry<K, V>) ((MapEntry) map).clone();
    } else {
      MapEntry maps = newMapEntry();
      maps.putAll(map);
      return maps;
    }
  }

  /**
   * cloneMap.
   * @param map not blank
   * @param <K> not blank
   * @param <V> not blank
   * @return Map
   */
  public static <K, V> Map<K, V> cloneMap(Map<K, V> map) {
    try {
      Class<?> clazz = map.getClass();
      Method method = clazz.getDeclaredMethod("clone");
      return (method != null && method.isAccessible() ? (Map) method
          .invoke(map, (Object[]) null) : new MapEntry(map));
    } catch (Exception var3) {
      return new MapEntry<>(map);
    }
  }

  /**
   * toString.
   * @param map not blank
   * @return String
   */
  public static String toString(Map map) {
    if (map == null) {
      return null;
    } else {
      String rs = map.toString();
      return rs.substring(1, rs.length() - 1);
    }
  }
}
