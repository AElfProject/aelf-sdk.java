package io.aelf.schemas;


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
