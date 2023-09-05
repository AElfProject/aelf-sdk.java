package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MerklePathNodeDto {

  @JsonProperty("Hash")
  private String hash;
  @JsonProperty("IsLeftChildNode")
  private boolean isLeftChildNode;

  public String getHash() {
    return hash;
  }

  public MerklePathNodeDto setHash(String hash) {
    this.hash = hash;
    return this;
  }

  public boolean isLeftChildNode() {
    return isLeftChildNode;
  }

  public MerklePathNodeDto setLeftChildNode(boolean leftChildNode) {
    isLeftChildNode = leftChildNode;
    return this;
  }
}
