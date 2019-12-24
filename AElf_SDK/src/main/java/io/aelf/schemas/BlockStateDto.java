package io.aelf.schemas;

import java.util.HashMap;
import java.util.List;

/**
 * @author linhui linhui@tydic.com
 * @title: BlockStateDto
 * @description: TODO
 * @date 2019/12/2222:57
 */
public class BlockStateDto {
    private String BlockHash;
    private String PreviousHash;
    private long BlockHeight;
    private HashMap<String,String> Changes;
    private List<String> Deletes;
    public String getBlockHash() {
        return BlockHash;
    }
    public void setBlockHash(String blockHash) {
        this.BlockHash = blockHash;
    }
    public String getPreviousHash() {
        return PreviousHash;
    }
    public void setPreviousHash(String previousHash) {
        this.PreviousHash = previousHash;
    }
    public long getBlockHeight() {
        return BlockHeight;
    }
    public void setBlockHeight(long blockHeight) {
        this.BlockHeight = blockHeight;
    }
    public HashMap<String, String> getChanges() {
        return Changes;
    }
    public void setChanges(HashMap<String, String> changes) {
        this.Changes = changes;
    }
    public List<String> getDeletes() {
        return Deletes;
    }
    public void setDeletes(List<String> deletes) {
        this.Deletes = deletes;
    }
}
