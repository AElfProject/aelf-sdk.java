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

  public static boolean checkSum(String data){
    byte[] addressBytes = Base58.decode(data);
    byte[] sumFromData = new byte[CheckSumSize];
    System.arraycopy(addressBytes, addressBytes.length - CheckSumSize, sumFromData, 0, CheckSumSize);
    byte[] dataWithoutSum = new byte[addressBytes.length - CheckSumSize];
    System.arraycopy(addressBytes, 0, dataWithoutSum, 0, addressBytes.length-CheckSumSize);
    byte[] sumHash = Sha256Hash.hashTwice(dataWithoutSum);
    byte[] sum = new byte[CheckSumSize];
    System.arraycopy(sumHash, 0, sum, 0, CheckSumSize);

    return Arrays.equals(sumFromData, sum);
  }
}