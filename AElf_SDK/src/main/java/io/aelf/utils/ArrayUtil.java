//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.aelf.utils;

import java.lang.reflect.Array;

public class ArrayUtil {
    public ArrayUtil() {
    }

    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    public static boolean isNotEmpty(Object[] obj) {
        return !isEmpty(obj);
    }

    public static Class commonClass(Class c1, Class c2) {
        if (c1 == c2) {
            return c1;
        } else if (c1 != Object.class && !c1.isAssignableFrom(c2)) {
            if (c2.isAssignableFrom(c1)) {
                return c2;
            } else if (!c1.isPrimitive() && !c2.isPrimitive()) {
                return Object.class;
            } else {
                throw new IllegalArgumentException("incompatible types " + c1 + " and " + c2);
            }
        } else {
            return c1;
        }
    }

    public static Object concat(Object arr1, Object arr2) {
        int len1 = arr1 == null ? -1 : Array.getLength(arr1);
        if (len1 <= 0) {
            return arr2;
        } else {
            int len2 = arr2 == null ? -1 : Array.getLength(arr2);
            if (len2 <= 0) {
                return arr1;
            } else {
                Class commonComponentType = commonClass(arr1.getClass().getComponentType(), arr2.getClass().getComponentType());
                Object newArray = Array.newInstance(commonComponentType, len1 + len2);
                System.arraycopy(arr1, 0, newArray, 0, len1);
                System.arraycopy(arr2, 0, newArray, len1, len2);
                return newArray;
            }
        }
    }

    public static String[][] dimension2Planar(String[] oneDimensionArray, int cols) {
        int tempindex = 0;
        int row = oneDimensionArray.length / cols;
        String[][] result = new String[row][];

        for(int i = 0; i < row; ++i) {
            result[i] = new String[cols];

            for(int j = tempindex; j < tempindex + result[i].length; ++j) {
                result[i][j - tempindex] = oneDimensionArray[j];
            }

            tempindex += result[i].length;
        }

        return result;
    }

    public static String[] safeOneDimensionArray(String[] oneDimensionArray) {
        int oneDimensionArrayLength = oneDimensionArray.length;
        int notNullMaxLength = oneDimensionArrayLength;

        for(int i = oneDimensionArrayLength - 1; i >= 0; --i) {
            if (oneDimensionArray[i] != null) {
                notNullMaxLength = i + 1;
                break;
            }
        }

        if (notNullMaxLength == oneDimensionArrayLength) {
            return oneDimensionArray;
        } else {
            String[] safeArray = new String[notNullMaxLength];

            for(int index = 0; index < notNullMaxLength; ++index) {
                safeArray[index] = oneDimensionArray[index];
            }

            return safeArray;
        }
    }
}
