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

  public void setHash(String hash) {
    this.hash = hash;
  }

  public boolean isLeftChildNode() {
    return isLeftChildNode;
  }

  public void setLeftChildNode(boolean leftChildNode) {
    isLeftChildNode = leftChildNode;
  }
}
