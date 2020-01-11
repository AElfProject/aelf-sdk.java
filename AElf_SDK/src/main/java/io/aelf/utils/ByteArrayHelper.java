package io.aelf.utils;

public class ByteArrayHelper {


  /**
   * The hex string turns into a byte array.
   *
   * @param inHex Hex string to be converted
   * @return The converted byte array results
   */
  public static byte[] hexToByteArray(String inHex) {
    int hexlen = inHex.length();
    byte[] result;
    if (hexlen >= 0 && hexlen % 2 == 1) {
      hexlen++;
      result = new byte[(hexlen / 2)];
      inHex = "0" + inHex;
    } else {
      result = new byte[(hexlen / 2)];
    }
    int j = 0;
    for (int i = 0; i < hexlen; i += 2) {
      result[j] = hexToByte(inHex.substring(i, i + 2));
      j++;
    }
    return result;
  }

  /**
   * Byte arrays to hexadecimal.
   *
   * @param bytes Byte array to convert
   * @return Converted Hex string
   */
  public static String bytesToHex(byte[] bytes) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < bytes.length; i++) {
      String hex = Integer.toHexString(bytes[i] & 0xFF);
      if (hex.length() < 2) {
        sb.append(0);
      }
      sb.append(hex);
    }
    return sb.toString();
  }

  /**
   * The Hex string turns to byte.
   *
   * @param inHex Hex string to be converted
   * @return Converted byte
   */
  private static byte hexToByte(String inHex) {
    return (byte) Integer.parseInt(inHex, 16);
  }
}
