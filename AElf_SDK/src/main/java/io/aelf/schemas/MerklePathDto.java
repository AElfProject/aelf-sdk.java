package io.aelf.schemas;

import java.util.List;

/**
 * @author linhui linhui@tydic.com
 * @title: MerklePathDto
 * @description: TODO
 * @date 2019/12/1516:40
 */
public class MerklePathDto {
    private List<MerklePathNodeDto> MerklePathNodes;

    public List<MerklePathNodeDto> getMerklePathNodes() {
        return MerklePathNodes;
    }

    public void setMerklePathNodes(List<MerklePathNodeDto> merklePathNodes) {
        MerklePathNodes = merklePathNodes;
    }
}
