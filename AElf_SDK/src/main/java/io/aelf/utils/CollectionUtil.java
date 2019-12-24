package io.aelf.utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CollectionUtil {
    public CollectionUtil() {
    }

    public static Boolean isBlankOrEmpty(Collection collection) {
        return collection != null && !collection.isEmpty() ? false : true;
    }

    public static Boolean isBlankOrEmpty(Map map) {
        return map != null && !map.isEmpty() ? false : true;
    }

    public static Boolean isNotBlankOrEmpty(Collection collection) {
        return !isBlankOrEmpty(collection);
    }

    public static Boolean isNotBlankOrEmpty(Map map) {
        return !isBlankOrEmpty(map);
    }

    public static List arrayToList(Object source) {
        return new ArrayList(Arrays.asList(ObjectUtil.toObjectArray(source)));
    }

    public static Collection mergeArrayIntoCollection(Object array, Collection collection) {
        if (collection == null) {
            throw new IllegalArgumentException("value not null");
        } else {
            Object[] arr = ObjectUtil.toObjectArray(array);

            for(int i = 0; i < arr.length; ++i) {
                collection.add(arr[i]);
            }

            return collection;
        }
    }

    public static void mergePropertiesIntoMap(Properties props, Map map) {
        if (map == null) {
            throw new IllegalArgumentException("value not null");
        } else {
            if (props != null) {
                Enumeration en = props.propertyNames();

                while(en.hasMoreElements()) {
                    String key = (String)en.nextElement();
                    map.put(key, props.getProperty(key));
                }
            }

        }
    }

    public static boolean contains(Iterator iterator, Object element) {
        if (iterator != null) {
            while(iterator.hasNext()) {
                Object candidate = iterator.next();
                if (ObjectUtil.nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean contains(Enumeration enumeration, Object element) {
        if (enumeration != null) {
            while(enumeration.hasMoreElements()) {
                Object candidate = enumeration.nextElement();
                if (ObjectUtil.nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean containsInstance(Collection collection, Object element) {
        if (collection != null) {
            Iterator it = collection.iterator();

            while(it.hasNext()) {
                Object candidate = it.next();
                if (candidate == element) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean containsAny(Collection source, Collection candidates) {
        if (!isBlankOrEmpty(source) && !isBlankOrEmpty(candidates)) {
            Iterator it = candidates.iterator();

            while(it.hasNext()) {
                if (source.contains(it.next())) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static Object findFirstMatch(Collection source, Collection candidates) {
        if (!isBlankOrEmpty(source) && !isBlankOrEmpty(candidates)) {
            Iterator it = candidates.iterator();

            while(it.hasNext()) {
                Object candidate = it.next();
                if (source.contains(candidate)) {
                    return candidate;
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static Object findValueOfType(Collection collection, Class type) {
        if (isBlankOrEmpty(collection)) {
            return null;
        } else {
            Object value = null;
            Iterator it = collection.iterator();

            while(true) {
                Object obj;
                do {
                    if (!it.hasNext()) {
                        return value;
                    }

                    obj = it.next();
                } while(type != null && !type.isInstance(obj));

                if (value != null) {
                    return null;
                }

                value = obj;
            }
        }
    }

    public static Object findValueOfType(Collection collection, Class[] types) {
        if (!isBlankOrEmpty(collection) && !ArrayUtil.isEmpty(types)) {
            for(int i = 0; i < types.length; ++i) {
                Object value = findValueOfType(collection, types[i]);
                if (value != null) {
                    return value;
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static boolean hasUniqueObject(Collection collection) {
        if (isBlankOrEmpty(collection)) {
            return false;
        } else {
            boolean hasCandidate = false;
            Object candidate = null;
            Iterator it = collection.iterator();

            while(it.hasNext()) {
                Object elem = it.next();
                if (!hasCandidate) {
                    hasCandidate = true;
                    candidate = elem;
                } else if (candidate != elem) {
                    return false;
                }
            }

            return true;
        }
    }

    public static List removeNullObject(Collection collection) {
        List list = Lists.newArraylist();
        if (!isBlankOrEmpty(collection)) {
            Iterator var3 = collection.iterator();

            while(var3.hasNext()) {
                Object obj = var3.next();
                if (obj != null) {
                    list.add(obj);
                }
            }
        }

        return list;
    }
}
