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

  public ChainstatusDto setChainId(String chainId) {
    this.chainId = chainId;
    return this;
  }

  public HashMap<String, Long> getBranches() {
    return branches;
  }

  public ChainstatusDto setBranches(HashMap<String, Long> branches) {
    this.branches = branches;
    return this;
  }

  public HashMap<String, String> getNotLinkedBlocks() {
    return notLinkedBlocks;
  }

  public ChainstatusDto setNotLinkedBlocks(HashMap<String, String> notLinkedBlocks) {
    this.notLinkedBlocks = notLinkedBlocks;
    return this;
  }

  public long getLongestChainHeight() {
    return longestChainHeight;
  }

  public ChainstatusDto setLongestChainHeight(long longestChainHeight) {
    this.longestChainHeight = longestChainHeight;
    return this;
  }

  public String getLongestChainHash() {
    return longestChainHash;
  }

  public ChainstatusDto setLongestChainHash(String longestChainHash) {
    this.longestChainHash = longestChainHash;
    return this;
  }

  public String getGenesisBlockHash() {
    return genesisBlockHash;
  }

  public ChainstatusDto setGenesisBlockHash(String genesisBlockHash) {
    this.genesisBlockHash = genesisBlockHash;
    return this;
  }

  public String getGenesisContractAddress() {
    return genesisContractAddress;
  }

  public ChainstatusDto setGenesisContractAddress(String genesisContractAddress) {
    this.genesisContractAddress = genesisContractAddress;
    return this;
  }

  public String getLastIrreversibleBlockHash() {
    return lastIrreversibleBlockHash;
  }

  public ChainstatusDto setLastIrreversibleBlockHash(String lastIrreversibleBlockHash) {
    this.lastIrreversibleBlockHash = lastIrreversibleBlockHash;
    return this;
  }

  public long getLastIrreversibleBlockHeight() {
    return lastIrreversibleBlockHeight;
  }

  public ChainstatusDto setLastIrreversibleBlockHeight(long lastIrreversibleBlockHeight) {
    this.lastIrreversibleBlockHeight = lastIrreversibleBlockHeight;
    return this;
  }

  public String getBestChainHash() {
    return bestChainHash;
  }

  public ChainstatusDto setBestChainHash(String bestChainHash) {
    this.bestChainHash = bestChainHash;
    return this;
  }

  public long getBestChainHeight() {
    return bestChainHeight;
  }

  public ChainstatusDto setBestChainHeight(long bestChainHeight) {
    this.bestChainHeight = bestChainHeight;
    return this;
  }
}
