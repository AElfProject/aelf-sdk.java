package io.aelf.utils;

import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;

public class Base58Ext {

  /**
   * Base58 encodeChecked custom.
   *
   * @param payload bytes content
   * @return String
   */
  public static String encodeChecked(byte[] payload) {
    // A stringify-buffer is:
    // 1 byte version + data bytes + 4 bytes check code (a truncated hash)
    byte[] addressBytes = new byte[payload.length + 4];
    System.arraycopy(payload, 0, addressBytes, 0, payload.length);
    byte[] checksum = Sha256Hash.hashTwice(addressBytes, 0, payload.length);
    System.arraycopy(checksum, 0, addressBytes, payload.length, 4);
    return Base58.encode(addressBytes);
  }
}