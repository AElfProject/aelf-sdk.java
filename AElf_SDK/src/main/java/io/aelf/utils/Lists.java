//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.aelf.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public final class Lists extends CollectionUtil {
    private static ArrayList arrayList = new ArrayList(20);
    private static LinkedList linkedList = new LinkedList();

    public Lists() {
    }

    public static List newList() {
        return Collections.EMPTY_LIST;
    }

    public static List newList(int size) {
        return newArraylist(size);
    }

    public static List newArraylist() {
        return (List)arrayList.clone();
    }

    public static List newArraylist(int size) {
        return new ArrayList(size);
    }

    public static List newLinkedlist() {
        return (List)linkedList.clone();
    }

    public static List newVector() {
        return newVector(0);
    }

    public static List newVector(int size) {
        return new Vector(size);
    }

    public static List arrayToList(Object source) {
        return new ArrayList(Arrays.asList(ObjectUtil.toObjectArray(source)));
    }

    public static <T> List<T> cloneList(List<T> list) {
        try {
            Class<?> clazz = list.getClass();
            Method method = clazz.getDeclaredMethod("clone");
            return (List)(method != null && method.isAccessible() ? (List)method.invoke(list, (Object[])null) : new ArrayList(list));
        } catch (Exception var3) {
            return new ArrayList(list);
        }
    }

    public static void sortByColumn(List<MapEntry> list, String column) {
        sortByColumn(list, column, true);
    }

    public static void sortByColumn(List<MapEntry> list, final String column, final boolean isAsc) {
        Collections.sort(list, new Comparator<MapEntry>() {
            public int compare(MapEntry o1, MapEntry o2) {
                if (o1.containsKey(column) && o2.containsKey(column)) {
                    int compare = o1.getString(column).compareTo(o2.getString(column));
                    return isAsc ? compare : (compare == 0 ? 0 : (compare > 0 ? -1 : 1));
                } else if (!o1.containsKey(column) && !o2.containsKey(column)) {
                    return isAsc ? 1 : -1;
                } else if (o1.containsKey(column)) {
                    return isAsc ? 1 : -1;
                } else {
                    return isAsc ? -1 : 1;
                }
            }
        });
    }
}
