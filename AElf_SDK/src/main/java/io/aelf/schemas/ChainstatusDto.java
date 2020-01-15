package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;

public class ChainstatusDto {

  @JsonProperty("ChainId")
  private String chainId;
  @JsonProperty("Branches")
  private HashMap<String, Long> branches;
  @JsonProperty("NotLinkedBlocks")
  private HashMap<String, String> notLinkedBlocks;
  @JsonProperty("LongestChainHeight")
  private long longestChainHeight;
  @JsonProperty("LongestChainHash")
  private String longestChainHash;
  @JsonProperty("GenesisBlockHash")
  private String genesisBlockHash;
  @JsonProperty("GenesisContractAddress")
  private String genesisContractAddress;
  @JsonProperty("LastIrreversibleBlockHash")
  private String lastIrreversibleBlockHash;
  @JsonProperty("LastIrreversibleBlockHeight")
  private long lastIrreversibleBlockHeight;
  @JsonProperty("BestChainHash")
  private String bestChainHash;
  @JsonProperty("BestChainHeight")
  private long bestChainHeight;

  public String getChainId() {
    return chainId;
  }

  public void setChainId(String chainId) {
    this.chainId = chainId;
  }

  public HashMap<String, Long> getBranches() {
    return branches;
  }

  public void setBranches(HashMap<String, Long> branches) {
    this.branches = branches;
  }

  public HashMap<String, String> getNotLinkedBlocks() {
    return notLinkedBlocks;
  }

  public void setNotLinkedBlocks(HashMap<String, String> notLinkedBlocks) {
    this.notLinkedBlocks = notLinkedBlocks;
  }

  public long getLongestChainHeight() {
    return longestChainHeight;
  }

  public void setLongestChainHeight(long longestChainHeight) {
    this.longestChainHeight = longestChainHeight;
  }

  public String getLongestChainHash() {
    return longestChainHash;
  }

  public void setLongestChainHash(String longestChainHash) {
    this.longestChainHash = longestChainHash;
  }

  public String getGenesisBlockHash() {
    return genesisBlockHash;
  }

  public void setGenesisBlockHash(String genesisBlockHash) {
    this.genesisBlockHash = genesisBlockHash;
  }

  public String getGenesisContractAddress() {
    return genesisContractAddress;
  }

  public void setGenesisContractAddress(String genesisContractAddress) {
    this.genesisContractAddress = genesisContractAddress;
  }

  public String getLastIrreversibleBlockHash() {
    return lastIrreversibleBlockHash;
  }

  public void setLastIrreversibleBlockHash(String lastIrreversibleBlockHash) {
    this.lastIrreversibleBlockHash = lastIrreversibleBlockHash;
  }

  public long getLastIrreversibleBlockHeight() {
    return lastIrreversibleBlockHeight;
  }

  public void setLastIrreversibleBlockHeight(long lastIrreversibleBlockHeight) {
    this.lastIrreversibleBlockHeight = lastIrreversibleBlockHeight;
  }

  public String getBestChainHash() {
    return bestChainHash;
  }

  public void setBestChainHash(String bestChainHash) {
    this.bestChainHash = bestChainHash;
  }

  public long getBestChainHeight() {
    return bestChainHeight;
  }

  public void setBestChainHeight(long bestChainHeight) {
    this.bestChainHeight = bestChainHeight;
  }
}
