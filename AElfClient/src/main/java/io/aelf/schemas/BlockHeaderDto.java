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

  public BlockHeaderDto setPreviousBlockHash(String previousBlockHash) {
    this.previousBlockHash = previousBlockHash;
    return this;
  }

  public String getMerkleTreeRootOfTransactions() {
    return merkleTreeRootOfTransactions;
  }

  public BlockHeaderDto setMerkleTreeRootOfTransactions(String merkleTreeRootOfTransactions) {
    this.merkleTreeRootOfTransactions = merkleTreeRootOfTransactions;
    return this;
  }

  public String getMerkleTreeRootOfWorldState() {
    return merkleTreeRootOfWorldState;
  }

  public BlockHeaderDto setMerkleTreeRootOfWorldState(String merkleTreeRootOfWorldState) {
    this.merkleTreeRootOfWorldState = merkleTreeRootOfWorldState;
    return this;
  }

  public String getMerkleTreeRootOfTransactionState() {
    return merkleTreeRootOfTransactionState;
  }

  public BlockHeaderDto setMerkleTreeRootOfTransactionState(String merkleTreeRootOfTransactionState) {
    this.merkleTreeRootOfTransactionState = merkleTreeRootOfTransactionState;
    return this;
  }

  public String getExtra() {
    return extra;
  }

  public BlockHeaderDto setExtra(String extra) {
    this.extra = extra;
    return this;
  }

  public long getHeight() {
    return height;
  }

  public BlockHeaderDto setHeight(long height) {
    this.height = height;
    return this;
  }

  public Date getTime() {
    return time;
  }

  public BlockHeaderDto setTime(Date time) {
    this.time = time;
    return this;
  }

  public String getChainId() {
    return chainId;
  }

  public BlockHeaderDto setChainId(String chainId) {
    this.chainId = chainId;
    return this;
  }

  public String getBloom() {
    return bloom;
  }

  public BlockHeaderDto setBloom(String bloom) {
    this.bloom = bloom;
    return this;
  }

  public String getSignerPubkey() {
    return signerPubkey;
  }

  public BlockHeaderDto setSignerPubkey(String signerPubkey) {
    this.signerPubkey = signerPubkey;
    return this;
  }
}
