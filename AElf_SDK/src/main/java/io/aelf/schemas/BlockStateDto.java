package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;

public class BlockStateDto {
  @JsonProperty("BlockHash")
  private String blockHash;
  @JsonProperty("PreviousHash")
  private String previousHash;
  @JsonProperty("BlockHeight")
  private long blockHeight;
  @JsonProperty("Changes")
  private HashMap<String,String> changes;
  @JsonProperty("Deletes")
  private List<String> deletes;

  public String getBlockHash() {
    return blockHash;
  }

  public void setBlockHash(String blockHash) {
    this.blockHash = blockHash;
  }

  public String getPreviousHash() {
    return previousHash;
  }

  public void setPreviousHash(String previousHash) {
    this.previousHash = previousHash;
  }

  public long getBlockHeight() {
    return blockHeight;
  }

  public void setBlockHeight(long blockHeight) {
    this.blockHeight = blockHeight;
  }

  public HashMap<String, String> getChanges() {
    return changes;
  }

  public void setChanges(HashMap<String, String> changes) {
    this.changes = changes;
  }

  public List<String> getDeletes() {
    return deletes;
  }

  public void setDeletes(List<String> deletes) {
    this.deletes = deletes;
  }
}
