package io.aelf.schemas;

/**
 * @author linhui linhui@tydic.com
 * @title: MerklePathNodeDto
 * @description: TODO
 * @date 2019/12/1516:40
 */
public class MerklePathNodeDto {
    private String Hash;
    private boolean IsLeftChildNode;

    public String getHash() {
        return Hash;
    }

    public void setHash(String hash) {
        Hash = hash;
    }

    public boolean isLeftChildNode() {
        return IsLeftChildNode;
    }

    public void setLeftChildNode(boolean leftChildNode) {
        IsLeftChildNode = leftChildNode;
    }
}
