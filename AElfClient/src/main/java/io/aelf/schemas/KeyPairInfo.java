package io.aelf.schemas;

/**
 * @author linhui linhui@tydic.com
 * @title: KeyPairInfo
 * @description: TODO
 * @date 2020/2/1020:37
 */
public class KeyPairInfo {
  private String privateKey;
  private String publicKey;
  private String address;

  public String getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
