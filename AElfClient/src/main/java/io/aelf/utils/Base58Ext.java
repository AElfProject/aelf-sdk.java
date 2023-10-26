package io.aelf.utils;

import java.util.Arrays;

import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;

public class Base58Ext {

  final static int CheckSumSize = 4;
  /**
   * Base58 encodeChecked custom.
   *
   * @param payload bytes content
   * @return String
   */
  public static String encodeChecked(byte[] payload) {
    // A stringified buffer is:
    // 1 byte version + data bytes + 4 bytes check code (a truncated hash)
    byte[] addressBytes = new byte[payload.length + 4];
    System.arraycopy(payload, 0, addressBytes, 0, payload.length);
    byte[] checksum = Sha256Hash.hashTwice(addressBytes, 0, payload.length);
    System.arraycopy(checksum, 0, addressBytes, payload.length, 4);
    return Base58.encode(addressBytes);
  }

  public static boolean verifyChecksum(String data){
    byte[] addressBytes = Base58.decode(data);
    byte[] checksumFromData = new byte[CheckSumSize];
    System.arraycopy(addressBytes, addressBytes.length - CheckSumSize, checksumFromData, 0, CheckSumSize);
    byte[] checksumHash = Sha256Hash.hashTwice(addressBytes,0,addressBytes.length - CheckSumSize);
    byte[] checksum = new byte[CheckSumSize];
    System.arraycopy(checksumHash, 0, checksum, 0, CheckSumSize);

    return Arrays.equals(checksumFromData, checksum);
  }
}