package io.aelf.utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * ## Deprecated ##
 * This class contains code that leaks generic constraints,
 * which may result in the wrong type cast.
 * <p>
 * DO NOT USE IT in your new code by now, we are trying to
 * replace it with its generic version in the future.
 */
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
@Deprecated
public class MapEntry<K, V> extends HashMap<K, V> implements IMapEntry<K, V> {

    /**
     * MapEntry constructor.
     */
    public MapEntry() {
    }

    /**
     * MapEntry constructor.
     *
     * @param size not blank
     */
    public MapEntry(int size) {
        super(size);
    }

    /**
     * MapEntry constructor.
     *
     * @param map not blank
     */
    public MapEntry(Map map) {
        super(map);
    }

    /**
     * getFile.
     *
     * @param key not blank
     */
    public File getFile(K key) {
        return this.getFile(key, null);
    }

    /**
     * getFile.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return File
     */
    public File getFile(K key, File defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else {
            return obj instanceof File ? (File) obj : defaultValue;
        }
    }

    /**
     * getInputStream.
     *
     * @param key not blank
     * @return InputStream
     */
    public InputStream getInputStream(K key) {
        return this.getInputStream(key, null);
    }

    /**
     * getInputStream.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return InputStream
     */
    public InputStream getInputStream(K key, InputStream defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else {
            return obj instanceof InputStream ? (InputStream) obj : defaultValue;
        }
    }

    /**
     * getOutputStream.
     *
     * @param key not blank
     * @return OutputStream
     */
    public OutputStream getOutputStream(K key) {
        return this.getOutputStream(key, null);
    }

    /**
     * getOutputStream.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return OutputStream
     */
    public OutputStream getOutputStream(K key, OutputStream defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else {
            return obj instanceof OutputStream ? (OutputStream) obj : defaultValue;
        }
    }

    /**
     * getCollection.
     *
     * @param key not blank
     * @return Collection
     */
    public Collection getCollection(K key) {
        return this.getCollection(key, null);
    }

    /**
     * getCollection.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return Collection
     */
    public Collection getCollection(K key, Collection defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else {
            return obj instanceof Collection ? (Collection) obj : defaultValue;
        }
    }

    /**
     * getMap.
     *
     * @param key not blank
     * @return Map
     */
    public Map getMap(K key) {
        return this.getMap(key, null);
    }

    /**
     * getMap.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return Map
     */
    public Map getMap(K key, Map defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else {
            return obj instanceof Map ? (Map) obj : defaultValue;
        }
    }

    /**
     * getHashMap.
     *
     * @param key not blank
     * @return HashMap
     */
    public HashMap getHashMap(K key) {
        return this.getHashMap(key, null);
    }

    /**
     * getHashMap.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return HashMap
     */
    public HashMap getHashMap(K key, HashMap defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else if (obj instanceof HashMap) {
            return (HashMap) obj;
        } else if (obj instanceof Map) {
            MapEntry map = Maps.newMapEntry();
            map.putAll((Map) obj);
            return map;
        } else {
            return defaultValue;
        }
    }

    /**
     * getTreeMap.
     *
     * @param key not blank
     * @return TreeMap
     */
    public TreeMap getTreeMap(K key) {
        return this.getTreeMap(key, null);
    }

    /**
     * getTreeMap.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return TreeMap
     */
    public TreeMap getTreeMap(K key, TreeMap defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else {
            return obj instanceof TreeMap ? (TreeMap) obj : defaultValue;
        }
    }

    /**
     * getLinkedHashMap.
     *
     * @param key not blank
     * @return LinkedHashMap.
     */
    public LinkedHashMap getLinkedHashMap(K key) {
        return this.getLinkedHashMap(key, null);
    }

    /**
     * getLinkedHashMap.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return LinkedHashMap
     */
    public LinkedHashMap getLinkedHashMap(K key, LinkedHashMap defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else if (obj instanceof LinkedHashMap) {
            return (LinkedHashMap) obj;
        } else if (obj instanceof Map) {
            LinkedHashMap map = Maps.newLinkedMapEntry();
            map.putAll((Map) obj);
            return map;
        } else {
            return defaultValue;
        }
    }

    /**
     * getMapEntry.
     *
     * @param key not blank
     * @return MapEntry
     */
    public MapEntry getMapEntry(K key) {
        return this.getMapEntry(key, null);
    }

    /**
     * getMapEntry.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return MapEntry
     */
    public MapEntry getMapEntry(K key, MapEntry defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else if (obj instanceof MapEntry) {
            return (MapEntry) obj;
        } else if (obj instanceof Map) {
            MapEntry map = Maps.newMapEntry();
            map.putAll((Map) obj);
            return map;
        } else {
            return defaultValue;
        }
    }

    /**
     * getLinkedMapEntry.
     *
     * @param key not blank
     * @return LinkedMapEntry
     */
    public LinkedMapEntry getLinkedMapEntry(K key) {
        return this.getLinkedMapEntry(key, null);
    }

    /**
     * getLinkedMapEntry.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return LinkedMapEntry
     */
    public LinkedMapEntry getLinkedMapEntry(K key, LinkedMapEntry defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else if (obj instanceof LinkedMapEntry) {
            return (LinkedMapEntry) obj;
        } else if (obj instanceof Map) {
            LinkedMapEntry map = Maps.newLinkedMapEntry();
            map.putAll((Map) obj);
            return map;
        } else {
            return defaultValue;
        }
    }

    /**
     * getList.
     *
     * @param key not blank
     * @return List
     */
    public List getList(K key) {
        return this.getList(key, null);
    }

    /**
     * getList.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return List
     */
    public List getList(K key, List defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else {
            return obj instanceof List ? (List) obj : defaultValue;
        }
    }

    /**
     * getArrayList.
     *
     * @param key not blank
     * @return ArrayList
     */
    public ArrayList getArrayList(K key) {
        return this.getArrayList(key, null);
    }

    /**
     * getArrayList.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return ArrayList
     */
    public ArrayList getArrayList(K key, ArrayList defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else {
            return obj instanceof ArrayList ? (ArrayList) obj : defaultValue;
        }
    }

    /**
     * getLinkedList.
     *
     * @param key not blank
     * @return LinkedList
     */
    public LinkedList getLinkedList(K key) {
        return this.getLinkedList(key, null);
    }

    /**
     * getLinkedList.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return LinkedList
     */
    public LinkedList getLinkedList(K key, LinkedList defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else {
            return obj instanceof LinkedList ? (LinkedList) obj : defaultValue;
        }
    }

    /**
     * getSet.
     *
     * @param key not blank
     * @return Set
     */
    public Set getSet(K key) {
        return this.getSet(key, null);
    }

    /**
     * getSet.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return Set
     */
    public Set getSet(K key, Set defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else {
            return obj instanceof Set ? (Set) obj : defaultValue;
        }
    }

    /**
     * getHashSet.
     *
     * @param key not blank
     * @return HashSet
     */
    public HashSet getHashSet(K key) {
        return this.getHashSet(key, null);
    }

    /**
     * getHashSet.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return HashSet
     */
    public HashSet getHashSet(K key, HashSet defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else {
            return obj instanceof HashSet ? (HashSet) obj : defaultValue;
        }
    }

    /**
     * getTreeSet.
     *
     * @param key not blank
     * @return TreeSet
     */
    public TreeSet getTreeSet(K key) {
        return this.getTreeSet(key, null);
    }

    /**
     * getTreeSet.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return TreeSet
     */
    public TreeSet getTreeSet(K key, TreeSet defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else {
            return obj instanceof TreeSet ? (TreeSet) obj : defaultValue;
        }
    }

    /**
     * getLinkedHashSet.
     *
     * @param key not blank
     * @return LinkedHashSet
     */
    public LinkedHashSet getLinkedHashSet(K key) {
        return this.getLinkedHashSet(key, null);
    }

    /**
     * getLinkedHashSet.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return LinkedHashSet
     */
    public LinkedHashSet getLinkedHashSet(K key, LinkedHashSet defaultValue) {
        Object obj = this.get(key);
        if (obj == null) {
            return defaultValue;
        } else {
            return obj instanceof LinkedHashSet ? (LinkedHashSet) obj : defaultValue;
        }
    }

    /**
     * getString.
     *
     * @param key not blank
     * @return String
     */
    public String getString(K key) {
        Object object = this.get(key);
        return object == null ? null : object.toString();
    }

    /**
     * getString.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return String
     */
    public String getString(K key, String defaultValue) {
        Object object = this.get(key);
        return object == null ? defaultValue : object.toString();
    }

    /**
     * getObject.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return Object
     */
    public Object getObject(Object key, Object defaultValue) {
        Object object = this.get(key);
        return object == null ? defaultValue : object;
    }

    /**
     * getObject.
     *
     * @param key not blank
     * @return Object
     */
    public Object getObject(Object key) {
        return this.getObject(key, null);
    }

    /**
     * getNumber.
     *
     * @param key not blank
     * @return Number
     */
    protected Number getNumber(K key) {
        return this.getNumber(key, 0);
    }

    /**
     * getNumber.
     *
     * @param key not blank
     * @param num not blank
     * @return Number
     */
    protected Number getNumber(K key, Number num) {
        Object object = this.get(key);
        if (object == null) {
            return num;
        } else {
            return NumberUtils.isNumber(object.toString()) ? NumberUtils.createNumber(object.toString())
                    : num;
        }
    }

    /**
     * getBigDecimal.
     *
     * @param key not blank
     * @return BigDecimal
     */
    public BigDecimal getBigDecimal(K key) {
        return this.getBigDecimal(key, 0);
    }

    /**
     * getBigDecimal.
     *
     * @param key not blank
     * @param num not blank
     * @return BigDecimal
     */
    public BigDecimal getBigDecimal(K key, Number num) {
        return new BigDecimal(this.getNumber(key, num).toString());
    }

    /**
     * getBigInteger.
     *
     * @param key not blank
     * @return BigInteger
     */
    public BigInteger getBigInteger(K key) {
        return this.getBigInteger(key, 0);
    }

    /**
     * getBigInteger.
     *
     * @param key not blank
     * @param num not blank
     * @return BigInteger
     */
    public BigInteger getBigInteger(K key, Number num) {
        return new BigInteger(this.getNumber(key, num).toString());
    }

    /**
     * getByte.
     *
     * @param key not blank
     * @return byte[]
     */
    public byte[] getByte(K key) {
        return this.getByte(key, null);
    }

    /**
     * getByte.
     *
     * @param key not blank
     * @param num not blank
     * @return byte[]
     */
    public byte[] getByte(K key, byte[] num) {
        Object object = this.get(key);
        if (object == null) {
            return num;
        } else {
            return object instanceof byte[] ? (byte[]) object : num;
        }
    }

    /**
     * getShort.
     *
     * @param key not blank
     * @return Short
     */
    public Short getShort(K key) {
        return this.getShort(key, 0);
    }

    /**
     * getShort.
     *
     * @param key not blank
     * @param num not blank
     * @return Short
     */
    public Short getShort(K key, Number num) {
        return this.getNumber(key, num).shortValue();
    }

    /**
     * getInteger.
     *
     * @param key not blank
     * @return Integer
     */
    public Integer getInteger(K key) {
        return this.getInteger(key, 0);
    }

    /**
     * getInteger.
     *
     * @param key not blank
     * @param num not blank
     * @return Integer
     */
    public Integer getInteger(K key, Number num) {
        return this.getNumber(key, num).intValue();
    }

    /**
     * getLong.
     *
     * @param key not blank
     * @return Long
     */
    public Long getLong(K key) {
        return this.getLong(key, 0);
    }

    /**
     * getLong.
     *
     * @param key not blank
     * @param num not blank
     * @return Long
     */
    public Long getLong(K key, Number num) {
        return this.getNumber(key, num).longValue();
    }

    /**
     * getDouble.
     *
     * @param key not blank
     * @return Double
     */
    public Double getDouble(K key) {
        return this.getDouble(key, 0);
    }

    /**
     * getDouble.
     *
     * @param key not blank
     * @param num not blank
     * @return Double
     */
    public Double getDouble(K key, Number num) {
        return this.getNumber(key, num).doubleValue();
    }

    /**
     * getFloat.
     *
     * @param key not blank
     * @return Float
     */
    public Float getFloat(K key) {
        return this.getFloat(key, 0);
    }

    /**
     * getFloat.
     *
     * @param key not blank
     * @param num not blank
     * @return Float
     */
    public Float getFloat(K key, Number num) {
        return this.getNumber(key, num).floatValue();
    }

    /**
     * getBoolean.
     *
     * @param key not blank
     * @return Boolean
     */
    public Boolean getBoolean(K key) {
        return this.getBoolean(key, Boolean.FALSE);
    }

    /**
     * getBoolean.
     *
     * @param key not blank
     * @param b   not blank
     * @return Boolean
     */
    public Boolean getBoolean(K key, Boolean b) {
        Object obj = this.get(key);
        if (obj == null) {
            return b;
        } else if (obj instanceof Boolean) {
            return (Boolean) obj;
        } else if (obj instanceof String) {
            if (NumberUtils.isNumber(obj.toString())) {
                return NumberUtils.createInteger(obj.toString()) >= 1;
            } else if (!((String) obj).equalsIgnoreCase("Y") && !((String) obj).equalsIgnoreCase("TRUE")
                    && !((String) obj).equalsIgnoreCase("YES") && !obj.equals("是")) {
                return !((String) obj).equalsIgnoreCase("N") && !((String) obj).equalsIgnoreCase("FALSE")
                        && !((String) obj).equalsIgnoreCase("NO") && !obj.equals("否") ? BooleanUtils
                        .toBooleanObject(obj.toString()) : false;
            } else {
                return true;
            }
        } else if (obj instanceof Number) {
            return obj.equals(1) || ((Number) obj).intValue() >= 1;
        } else {
            return b;
        }
    }

    /**
     * getDate.
     *
     * @param key not blank
     * @return Date
     */
    public Date getDate(K key) {
        return this.getDate(key, null);
    }

    /**
     * getDate.
     *
     * @param key  not blank
     * @param date not blank
     * @return Date
     */
    public Date getDate(K key, Date date) {
        Object object = this.get(key);
        if (object == null) {
            return date;
        } else {
            return object instanceof Date ? (Date) object : date;
        }
    }

    /**
     * getArray.
     *
     * @param key not blank
     * @return String[]
     */
    public String[] getArray(K key) {
        return this.getArray(key, null);
    }

    /**
     * getArray.
     *
     * @param key          not blank
     * @param defaultValue not blank
     * @return String[]
     */
    public String[] getArray(K key, String[] defaultValue) {
        Object object = this.get(key);
        if (object == null) {
            return defaultValue;
        } else {
            return object instanceof String[] ? (String[]) object : defaultValue;
        }
    }
}
