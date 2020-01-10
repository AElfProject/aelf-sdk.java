package io.aelf.schemas;

import java.util.List;


public class MerklePathDto {
    private List<MerklePathNodeDto> MerklePathNodes;

    public List<MerklePathNodeDto> getMerklePathNodes() {
        return MerklePathNodes;
    }

    public void setMerklePathNodes(List<MerklePathNodeDto> merklePathNodes) {
        MerklePathNodes = merklePathNodes;
    }
}
