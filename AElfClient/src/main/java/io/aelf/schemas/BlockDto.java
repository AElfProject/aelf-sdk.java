package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlockDto {
  @JsonProperty("BlockHash")
  private String blockHash;
  @JsonProperty("Header")
  private BlockHeaderDto header;
  @JsonProperty("Body")
  private BlockBodyDto body;

  public String getBlockHash() {
    return this.blockHash;
  }

  public void setBlockHash(String blockHash) {
    this.blockHash = blockHash;
  }

  public BlockHeaderDto getHeader() {
    return header;
  }

  public void setHeader(BlockHeaderDto header) {
    this.header = header;
  }

  public BlockBodyDto getBody() {
    return body;
  }

  public void setBody(BlockBodyDto body) {
    this.body = body;
  }

}
