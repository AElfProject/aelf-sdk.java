package io.aelf.utils;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Digital terabyte array utility class.
 */
@SuppressWarnings("unused")
public class BitConverter {

  /**
   * Returns the specified Boolean value as a byte array.
   *
   * @param data A Boolean value
   * @return An array of bytes of length 1
   */
  public static byte[] getBytes(boolean data) {
    byte[] bytes = new byte[1];
    bytes[0] = (byte) (data ? 1 : 0);
    return bytes;
  }

  /**
   * Returns the specified 16-bit signed integer value as a byte array.
   *
   * @param data The number to convert
   * @return An array of bytes of length 2
   */
  public static byte[] getBytes(short data) {
    byte[] bytes = new byte[2];
    if (isLittleEndian()) {
      bytes[0] = (byte) (data & 0xff);
      bytes[1] = (byte) ((data & 0xff00) >> 8);
    } else {
      bytes[1] = (byte) (data & 0xff);
      bytes[0] = (byte) ((data & 0xff00) >> 8);
    }
    return bytes;
  }

  /**
   * Returns the specified Unicode character value as a byte array.
   *
   * @param data The character to convert
   * @return An array of bytes of length 2
   */
  public static byte[] getBytes(char data) {
    byte[] bytes = new byte[2];
    if (isLittleEndian()) {
      bytes[0] = (byte) (data);
      bytes[1] = (byte) (data >> 8);
    } else {
      bytes[1] = (byte) (data);
      bytes[0] = (byte) (data >> 8);
    }
    return bytes;
  }

  /**
   * Returns the specified 32-bit signed integer value as a byte array.
   *
   * @param data The number to convert
   * @return Byte array of length 4
   */
  public static byte[] getBytes(int data) {
    byte[] bytes = new byte[4];
    if (isLittleEndian()) {
      bytes[0] = (byte) (data & 0xff);
      bytes[1] = (byte) ((data & 0xff00) >> 8);
      bytes[2] = (byte) ((data & 0xff0000) >> 16);
      bytes[3] = (byte) ((data & 0xff000000) >> 24);
    } else {
      bytes[3] = (byte) (data & 0xff);
      bytes[2] = (byte) ((data & 0xff00) >> 8);
      bytes[1] = (byte) ((data & 0xff0000) >> 16);
      bytes[0] = (byte) ((data & 0xff000000) >> 24);
    }
    return bytes;
  }

  /**
   * Returns the specified 64-bit signed integer value as a byte array.
   *
   * @param data The number to convert
   * @return A byte array of length 8
   */
  public static byte[] getBytes(long data) {
    byte[] bytes = new byte[8];
    if (isLittleEndian()) {
      bytes[0] = (byte) (data & 0xff);
      bytes[1] = (byte) ((data >> 8) & 0xff);
      bytes[2] = (byte) ((data >> 16) & 0xff);
      bytes[3] = (byte) ((data >> 24) & 0xff);
      bytes[4] = (byte) ((data >> 32) & 0xff);
      bytes[5] = (byte) ((data >> 40) & 0xff);
      bytes[6] = (byte) ((data >> 48) & 0xff);
      bytes[7] = (byte) ((data >> 56) & 0xff);
    } else {
      bytes[7] = (byte) (data & 0xff);
      bytes[6] = (byte) ((data >> 8) & 0xff);
      bytes[5] = (byte) ((data >> 16) & 0xff);
      bytes[4] = (byte) ((data >> 24) & 0xff);
      bytes[3] = (byte) ((data >> 32) & 0xff);
      bytes[2] = (byte) ((data >> 40) & 0xff);
      bytes[1] = (byte) ((data >> 48) & 0xff);
      bytes[0] = (byte) ((data >> 56) & 0xff);
    }
    return bytes;
  }

  /**
   * Returns the specified 64-bit signed integer value as a byte array.
   *
   * @param data The number to convert
   * @return Byte array of length 4
   */
  public static byte[] getBytes(float data) {
    return getBytes(Float.floatToIntBits(data));
  }

  /**
   * Returns the specified double - precision floating-point value as a byte array.
   *
   * @param data The number to convert
   * @return A byte array of length 8
   */
  public static byte[] getBytes(double data) {
    return getBytes(Double.doubleToLongBits(data));
  }

  /**
   * Encodes all characters in the specified string as a sequence of bytes.
   *
   * @param data A string containing the characters to be encoded
   * @return An array of bytes containing the result of encoding the specified character set
   */
  public static byte[] getBytes(String data) {
    return data.getBytes(StandardCharsets.UTF_8);
  }

  /**
   * Encodes all characters in the specified string as a sequence of bytes.
   *
   * @param data A string containing the characters to be encoded
   * @param charsetName Character set coding
   * @return An array of bytes containing the result of encoding the specified character set
   */
  public static byte[] getBytes(String data, String charsetName) {
    return data.getBytes(Charset.forName(charsetName));
  }

  /**
   * Returns a Boolean value converted from an array of bytes.
   *
   * @param bytes byte array
   * @return Boolean value
   */
  public static boolean toBoolean(byte[] bytes) {
    return bytes[0] != 0;
  }

  /**
   * Returns a Boolean value converted from a specified byte in the byte array.
   *
   * @param bytes byte array
   * @param startIndex Starting the subscript
   * @return Boolean value
   */
  public static boolean toBoolean(byte[] bytes, int startIndex) {
    return toBoolean(copyFrom(bytes, startIndex, 1));
  }

  /**
   * Returns a 16-bit signed integer converted from a byte array.
   *
   * @param bytes byte array
   * @return A 16-bit signed integer consisting of two bytes
   */
  public static short toShort(byte[] bytes) {
    if (isLittleEndian()) {
      return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    } else {
      return (short) ((0xff & bytes[1]) | (0xff00 & (bytes[0] << 8)));
    }
  }

  /**
   * Returns a 16-bit signed integer converted from the specified two bytes in the byte array.
   *
   * @param bytes byte array
   * @param startIndex Starting the subscript
   * @return A 16-bit signed integer consisting of two bytes
   */
  public static short toShort(byte[] bytes, int startIndex) {
    return toShort(copyFrom(bytes, startIndex, 2));
  }

  /**
   * Returns a Unicode character converted from a byte array.
   *
   * @param bytes byte array
   * @return A character consisting of two bytes
   */
  public static char toChar(byte[] bytes) {
    if (isLittleEndian()) {
      return (char) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    } else {
      return (char) ((0xff & bytes[1]) | (0xff00 & (bytes[0] << 8)));
    }
  }

  /**
   * Returns the Unicode character converted from the specified two bytes in the byte array.
   *
   * @param bytes byte array
   * @param startIndex Starting the subscript
   * @return A character consisting of two bytes
   */
  public static char toChar(byte[] bytes, int startIndex) {
    return toChar(copyFrom(bytes, startIndex, 2));
  }

  /**
   * Returns a 32-bit signed integer converted from a byte array.
   *
   * @param bytes byte array
   * @return A 32-bit signed integer consisting of four bytes
   */
  public static int toInt(byte[] bytes) {
    if (isLittleEndian()) {
      return (0xff & bytes[0])
          | (0xff00 & (bytes[1] << 8))
          | (0xff0000 & (bytes[2] << 16))
          | (0xff000000 & (bytes[3] << 24));
    } else {
      return (0xff & bytes[3])
          | (0xff00 & (bytes[2] << 8))
          | (0xff0000 & (bytes[1] << 16))
          | (0xff000000 & (bytes[0] << 24));
    }
  }

  /**
   * Returns a 32-bit signed integer converted from the specified four bytes in the byte array.
   *
   * @param bytes byte array
   * @param startIndex Starting the subscript
   * @return A 32-bit signed integer consisting of four bytes
   */
  public static int toInt(byte[] bytes, int startIndex) {
    return toInt(copyFrom(bytes, startIndex, 4));
  }

  /**
   * Returns a 64-bit signed integer converted from a byte array.
   *
   * @param bytes byte array
   * @return A 64-bit signed integer consisting of eight bytes
   */
  public static long toLong(byte[] bytes) {
    if (isLittleEndian()) {
      return (0xffL & (long) bytes[0])
          | (0xff00L & ((long) bytes[1] << 8))
          | (0xff0000L & ((long) bytes[2] << 16))
          | (0xff000000L & ((long) bytes[3] << 24))
          | (0xff00000000L & ((long) bytes[4] << 32))
          | (0xff0000000000L & ((long) bytes[5] << 40))
          | (0xff000000000000L & ((long) bytes[6] << 48))
          | (0xff00000000000000L & ((long) bytes[7] << 56));
    } else {
      return (0xffL & (long) bytes[7])
          | (0xff00L & ((long) bytes[6] << 8))
          | (0xff0000L & ((long) bytes[5] << 16))
          | (0xff000000L & ((long) bytes[4] << 24))
          | (0xff00000000L & ((long) bytes[3] << 32))
          | (0xff0000000000L & ((long) bytes[2] << 40))
          | (0xff000000000000L & ((long) bytes[1] << 48))
          | (0xff00000000000000L & ((long) bytes[0] << 56));
    }
  }

  /**
   * Returns a 64-bit signed integer converted from the specified eight bytes in the byte array.
   *
   * @param bytes byte array
   * @param startIndex Starting the subscript
   * @return A 64-bit signed integer consisting of eight bytes
   */
  public static long toLong(byte[] bytes, int startIndex) {
    return toLong(copyFrom(bytes, startIndex, 8));
  }

  /**
   * Returns a single-precision floating-point number converted from a byte array.
   *
   * @param bytes byte array
   * @return A single-precision floating-point number consisting of four bytes
   */
  public static float toFloat(byte[] bytes) {
    return Float.intBitsToFloat(toInt(bytes));
  }

  /**
   * Returns a single-precision floating-point number converted from the specified four bytes in the
   * byte array.
   *
   * @param bytes byte array
   * @param startIndex Starting the subscript
   * @return A single-precision floating-point number consisting of four bytes
   */
  public static float toFloat(byte[] bytes, int startIndex) {
    return Float.intBitsToFloat(toInt(copyFrom(bytes, startIndex, 4)));
  }

  /**
   * Returns a double - precision floating-point number converted from a byte array.
   *
   * @param bytes byte array
   * @return A double - precision floating-point number consisting of eight bytes
   */
  public static double toDouble(byte[] bytes) {
    return Double.longBitsToDouble(toLong(bytes));
  }

  /**
   * Returns a double - precision floating-point number converted from the specified eight bytes in
   * the byte array.
   *
   * @param bytes byte array
   * @param startIndex Starting the subscript
   * @return A double - precision floating-point number consisting of eight bytes
   */
  public static double toDouble(byte[] bytes, int startIndex) {
    return Double.longBitsToDouble(toLong(copyFrom(bytes, startIndex, 8)));
  }

  /**
   * Returns a string converted from an array of bytes.
   *
   * @param bytes byte array
   * @return character string
   */
  public static String toString(byte[] bytes) {
    return new String(bytes, StandardCharsets.UTF_8);
  }

  /**
   * Returns a string converted from an array of bytes.
   *
   * @param bytes byte array
   * @param charsetName Character set coding
   * @return character string
   */
  public static String toString(byte[] bytes, String charsetName) {
    return new String(bytes, Charset.forName(charsetName));
  }

  /**
   * Returns the contents of the byte array as a string representation.
   *
   * @param bytes byte array
   * @return string-like
   */
  public static String toHexString(byte[] bytes) {
    if (bytes == null) {
      return "null";
    }
    int imax = bytes.length - 1;
    if (imax == -1) {
      return "[]";
    }
    StringBuilder b = new StringBuilder();
    b.append('[');
    for (int i = 0; ; i++) {
      b.append(String.format("%02x", bytes[i] & 0xFF));
      if (i == imax) {
        return b.append(']').toString();
      }
      b.append(", ");
    }
  }

  // --------------------------------------------------------------------------------------------


  /**
   * Array copy.
   *
   * @param src byte array
   * @param off Starting the subscript。
   * @param len Copy the length。
   * @return An array of bytes of specified length。
   */
  private static byte[] copyFrom(byte[] src, int off, int len) {
    // return Arrays.copyOfRange(src, off, off + len);
    byte[] bits = new byte[len];
    for (int i = off, j = 0; i < src.length && j < len; i++, j++) {
      bits[j] = src[i];
    }
    return bits;
  }

  /**
   * Determine if CPU Endian is Little.
   *
   * @return result
   */
  private static boolean isLittleEndian() {
    return ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
  }
}