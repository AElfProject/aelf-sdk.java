package io.aelf.schemas;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MerklePathDto {
  @JsonProperty("MerklePathNodes")
  private List<MerklePathNodeDto> merklePathNodes;
  public List<MerklePathNodeDto> getMerklePathNodes() {
      return merklePathNodes;
  }
  public void setMerklePathNodes(List<MerklePathNodeDto> merklePathNodes) {
        this.merklePathNodes = merklePathNodes;
    }
}
