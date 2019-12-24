package io.aelf.schemas;

import java.util.Date;

/**
 * @author linhui linhui@tydic.com
 * @title: BlockHeaderDto
 * @description: TODO
 * @date 2019/12/1511:59
 */
public class BlockHeaderDto {
    private String PreviousBlockHash;
    private String MerkleTreeRootOfTransactions;
    private String MerkleTreeRootOfWorldState;
    private String MerkleTreeRootOfTransactionState;
    private String Extra;
    private long Height;
    private Date Time;
    private String ChainId;
    private String Bloom;
    private String SignerPubkey;
    public String getPreviousBlockHash() {
        return PreviousBlockHash;
    }
    public void setPreviousBlockHash(String previousBlockHash) {
        this.PreviousBlockHash = previousBlockHash;
    }
    public String getMerkleTreeRootOfTransactions() {
        return MerkleTreeRootOfTransactions;
    }
    public void setMerkleTreeRootOfTransactions(String merkleTreeRootOfTransactions) {
        this.MerkleTreeRootOfTransactions = merkleTreeRootOfTransactions;
    }
    public String getMerkleTreeRootOfWorldState() {
        return MerkleTreeRootOfWorldState;
    }
    public void setMerkleTreeRootOfWorldState(String merkleTreeRootOfWorldState) {
        this.MerkleTreeRootOfWorldState = merkleTreeRootOfWorldState;
    }
    public String getMerkleTreeRootOfTransactionState() {
        return MerkleTreeRootOfTransactionState;
    }
    public void setMerkleTreeRootOfTransactionState(String merkleTreeRootOfTransactionState) {
        this.MerkleTreeRootOfTransactionState = merkleTreeRootOfTransactionState;
    }
    public String getExtra() {
        return Extra;
    }
    public void setExtra(String extra) {
        this.Extra = extra;
    }
    public long getHeight() {
        return Height;
    }
    public void setHeight(long height) {
        this.Height = height;
    }
    public Date getTime() {
        return Time;
    }
    public void setTime(Date time) {
        this.Time = time;
    }
    public String getChainId() {
        return ChainId;
    }
    public void setChainId(String chainId) {
        this.ChainId = chainId;
    }
    public String getBloom() {
        return Bloom;
    }
    public void setBloom(String bloom) {
        this.Bloom = bloom;
    }
    public String getSignerPubkey() {
        return SignerPubkey;
    }
    public void setSignerPubkey(String signerPubkey) {
        this.SignerPubkey = signerPubkey;
    }

}
