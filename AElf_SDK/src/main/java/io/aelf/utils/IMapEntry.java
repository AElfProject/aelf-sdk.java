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

public interface IMapEntry<K, V> extends Map<K, V> {

  File getFile(K var1);

  File getFile(K var1, File var2);

  InputStream getInputStream(K var1);

  InputStream getInputStream(K var1, InputStream var2);

  OutputStream getOutputStream(K var1);

  OutputStream getOutputStream(K var1, OutputStream var2);

  Collection getCollection(K var1);

  Collection getCollection(K var1, Collection var2);

  Map getMap(K var1);

  Map getMap(K var1, Map var2);

  String[] getArray(K var1);

  String[] getArray(K var1, String[] var2);

  HashMap getHashMap(K var1);

  HashMap getHashMap(K var1, HashMap var2);

  TreeMap getTreeMap(K var1);

  TreeMap getTreeMap(K var1, TreeMap var2);

  LinkedHashMap getLinkedHashMap(K var1);

  LinkedHashMap getLinkedHashMap(K var1, LinkedHashMap var2);

  MapEntry getMapEntry(K var1);

  MapEntry getMapEntry(K var1, MapEntry var2);

  LinkedMapEntry getLinkedMapEntry(K var1);

  LinkedMapEntry getLinkedMapEntry(K var1, LinkedMapEntry var2);

  List getList(K var1);

  List getList(K var1, List var2);

  ArrayList getArrayList(K var1);

  ArrayList getArrayList(K var1, ArrayList var2);

  LinkedList getLinkedList(K var1);

  LinkedList getLinkedList(K var1, LinkedList var2);

  Set getSet(K var1);

  Set getSet(K var1, Set var2);

  HashSet getHashSet(K var1);

  HashSet getHashSet(K var1, HashSet var2);

  TreeSet getTreeSet(K var1);

  TreeSet getTreeSet(K var1, TreeSet var2);

  LinkedHashSet getLinkedHashSet(K var1);

  LinkedHashSet getLinkedHashSet(K var1, LinkedHashSet var2);

  String getString(K var1);

  String getString(K var1, String var2);

  Object getObject(Object var1, Object var2);

  Object getObject(Object var1);


  BigDecimal getBigDecimal(K var1);

  BigDecimal getBigDecimal(K var1, Number var2);

  BigInteger getBigInteger(K var1);

  BigInteger getBigInteger(K var1, Number var2);

  byte[] getByte(K var1);

  byte[] getByte(K var1, byte[] var2);

  Short getShort(K var1);

  Short getShort(K var1, Number var2);

  Integer getInteger(K var1);

  Integer getInteger(K var1, Number var2);

  Long getLong(K var1);

  Long getLong(K var1, Number var2);

  Double getDouble(K var1);

  Double getDouble(K var1, Number var2);

  Float getFloat(K var1);

  Float getFloat(K var1, Number var2);

  Date getDate(K var1);

  Date getDate(K var1, Date var2);

  Boolean getBoolean(K var1);

  Boolean getBoolean(K var1, Boolean var2);
}
