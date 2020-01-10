package io.aelf.schemas;

import java.util.HashMap;


public class ChainstatusDto {
    private String ChainId;
    private HashMap<String,Long> Branches;
    private HashMap<String,String> NotLinkedBlocks;
    private long LongestChainHeight;
    private String LongestChainHash;
    private String GenesisBlockHash;
    private String GenesisContractAddress;
    private String LastIrreversibleBlockHash;
    private long LastIrreversibleBlockHeight;
    private String BestChainHash;
    private long BestChainHeight;
    public String getChainId() {
        return ChainId;
    }
    public void setChainId(String chainId) {
        this.ChainId = chainId;
    }
    public HashMap<String, Long> getBranches() {
        return Branches;
    }
    public void setBranches(HashMap<String, Long> branches) {
        this.Branches = branches;
    }
    public HashMap<String, String> getNotLinkedBlocks() {
        return NotLinkedBlocks;
    }
    public void setNotLinkedBlocks(HashMap<String, String> notLinkedBlocks) {
        this.NotLinkedBlocks = notLinkedBlocks;
    }
    public long getLongestChainHeight() {
        return LongestChainHeight;
    }
    public void setLongestChainHeight(long longestChainHeight) {
        this.LongestChainHeight = longestChainHeight;
    }
    public String getLongestChainHash() {
        return LongestChainHash;
    }
    public void setLongestChainHash(String longestChainHash) {
        this.LongestChainHash = longestChainHash;
    }
    public String getGenesisBlockHash() {
        return GenesisBlockHash;
    }
    public void setGenesisBlockHash(String genesisBlockHash) {
        this.GenesisBlockHash = genesisBlockHash;
    }
    public String getGenesisContractAddress() {
        return GenesisContractAddress;
    }
    public void setGenesisContractAddress(String genesisContractAddress) {
        this.GenesisContractAddress = genesisContractAddress;
    }
    public String getLastIrreversibleBlockHash() {
        return LastIrreversibleBlockHash;
    }
    public void setLastIrreversibleBlockHash(String lastIrreversibleBlockHash) {
        this.LastIrreversibleBlockHash = lastIrreversibleBlockHash;
    }
    public long getLastIrreversibleBlockHeight() {
        return LastIrreversibleBlockHeight;
    }
    public void setLastIrreversibleBlockHeight(long lastIrreversibleBlockHeight) {
        this.LastIrreversibleBlockHeight = lastIrreversibleBlockHeight;
    }
    public String getBestChainHash() {
        return BestChainHash;
    }
    public void setBestChainHash(String bestChainHash) {
        this.BestChainHash = bestChainHash;
    }
    public long getBestChainHeight() {
        return BestChainHeight;
    }
    public void setBestChainHeight(long bestChainHeight) {
        this.BestChainHeight = bestChainHeight;
    }
}
