package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class BlockHeaderDto {

  @JsonProperty("PreviousBlockHash")
  private String previousBlockHash;
  @JsonProperty("MerkleTreeRootOfTransactions")
  private String merkleTreeRootOfTransactions;
  @JsonProperty("MerkleTreeRootOfWorldState")
  private String merkleTreeRootOfWorldState;
  @JsonProperty("MerkleTreeRootOfTransactionState")
  private String merkleTreeRootOfTransactionState;
  @JsonProperty("Extra")
  private String extra;
  @JsonProperty("Height")
  private long height;
  @JsonProperty("Time")
  private Date time;
  @JsonProperty("ChainId")
  private String chainId;
  @JsonProperty("Bloom")
  private String bloom;
  @JsonProperty("SignerPubkey")
  private String signerPubkey;

  public String getPreviousBlockHash() {
    return previousBlockHash;
  }

  public void setPreviousBlockHash(String previousBlockHash) {
    this.previousBlockHash = previousBlockHash;
  }

  public String getMerkleTreeRootOfTransactions() {
    return merkleTreeRootOfTransactions;
  }

  public void setMerkleTreeRootOfTransactions(String merkleTreeRootOfTransactions) {
    this.merkleTreeRootOfTransactions = merkleTreeRootOfTransactions;
  }

  public String getMerkleTreeRootOfWorldState() {
    return merkleTreeRootOfWorldState;
  }

  public void setMerkleTreeRootOfWorldState(String merkleTreeRootOfWorldState) {
    this.merkleTreeRootOfWorldState = merkleTreeRootOfWorldState;
  }

  public String getMerkleTreeRootOfTransactionState() {
    return merkleTreeRootOfTransactionState;
  }

  public void setMerkleTreeRootOfTransactionState(String merkleTreeRootOfTransactionState) {
    this.merkleTreeRootOfTransactionState = merkleTreeRootOfTransactionState;
  }

  public String getExtra() {
    return extra;
  }

  public void setExtra(String extra) {
    this.extra = extra;
  }

  public long getHeight() {
    return height;
  }

  public void setHeight(long height) {
    this.height = height;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public String getChainId() {
    return chainId;
  }

  public void setChainId(String chainId) {
    this.chainId = chainId;
  }

  public String getBloom() {
    return bloom;
  }

  public void setBloom(String bloom) {
    this.bloom = bloom;
  }

  public String getSignerPubkey() {
    return signerPubkey;
  }

  public void setSignerPubkey(String signerPubkey) {
    this.signerPubkey = signerPubkey;
  }
}
