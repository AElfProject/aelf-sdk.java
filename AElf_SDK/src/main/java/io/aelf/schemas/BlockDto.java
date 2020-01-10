package io.aelf.schemas;
public class BlockDto {
    private String BlockHash;
    private BlockHeaderDto Header;
    private BlockBodyDto Body;
    public String getBlockHash() {
        return this.BlockHash;
    }
    public void setBlockHash(String blockHash) {
        this.BlockHash = blockHash;
    }
    public BlockHeaderDto getHeader() {
        return Header;
    }
    public void setHeader(BlockHeaderDto header) {
        this.Header = header;
    }
    public BlockBodyDto getBody() {
        return Body;
    }
    public void setBody(BlockBodyDto body) {
        this.Body = body;
    }

}
