package io.aelf.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256 {

  /**
   * Converts hexadecimal byte to bytes.
   *
   * @return bytes
   */
  public static byte[] getBytesSha256(byte[] str) {
    MessageDigest messageDigest;
    byte[] digestBytes = new byte[0];
    try {
      messageDigest = MessageDigest.getInstance("SHA-256");
      messageDigest.update(str);
      digestBytes = messageDigest.digest();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return digestBytes;
  }

  /**
   * Converts hexadecimal to bytes.
   *
   * @return bytes
   */
  public static byte[] getBytesSha256(String str) {
    MessageDigest messageDigest;
    byte[] digestBytes = new byte[0];
    try {
      messageDigest = MessageDigest.getInstance("SHA-256");
      messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
      digestBytes = messageDigest.digest();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return digestBytes;
  }

  /**
   * * Converts  bytes to hexadecimal     * @param bytes     * @return hex     .
   */
  private static String byte2Hex(byte[] bytes) {
    StringBuilder stringBuffer = new StringBuilder();
    String temp = null;
    for (byte aByte : bytes) {
      temp = Integer.toHexString(aByte & 0xFF);
      if (temp.length() == 1) {
        stringBuffer.append("0");
      }
      stringBuffer.append(temp);
    }
    return stringBuffer.toString();
  }

  /**
   * Converts bytes to String.
   *
   * @return string
   */
  public static String getSha256ForBytes(byte[] bytes) {
    MessageDigest messageDigest;
    String encodestr = "";
    try {
      messageDigest = MessageDigest.getInstance("SHA-256");
      messageDigest.update(bytes);
      encodestr = byte2Hex(messageDigest.digest());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return encodestr;
  }


}
