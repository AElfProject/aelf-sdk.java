package io.aelf.schemas;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionResultDto {
  @JsonProperty("TransactionId")
  private String transactionId;
  @JsonProperty("Status")
  private String status;
  @JsonProperty("Logs")
  private List<LogEventDto> logs;
  @JsonProperty("Bloom")
  private String bloom;
  @JsonProperty("BlockNumber")
  private long blockNumber;
  @JsonProperty("BlockHash")
  private String blockHash;
  @JsonProperty("Transaction")
  private TransactionDto transaction;
  @JsonProperty("ReturnValue")
  private String returnValue;
  @JsonProperty("ReadableReturnValue")
  private String readableReturnValue;
  @JsonProperty("Error")
  private String error;
  @JsonProperty("TransactionFee")
  private TransactionFeeDto transactionFee;

  public String getTransactionId() {
        return transactionId;
    }
  public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
  public String getStatus() {
        return status;
    }
  public void setStatus(String status) {
        this.status = status;
    }
  public List<LogEventDto> getLogs() {
        return logs;
    }
  public void setLogs(List<LogEventDto> logs) {
        this.logs = logs;
    }
  public String getBloom() {
        return bloom;
    }
  public void setBloom(String bloom) {
        this.bloom = bloom;
    }
  public long getBlockNumber() {
        return blockNumber;
    }
  public void setBlockNumber(long blockNumber) {
        this.blockNumber = blockNumber;
    }
  public String getBlockHash() {
        return blockHash;
    }
  public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }
  public TransactionDto getTransaction() {
        return transaction;
    }
  public void setTransaction(TransactionDto transaction) {
        this.transaction = transaction;
    }
  public String getReturnValue() {
        return returnValue;
    }
  public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }
  public String getReadableReturnValue() {
        return readableReturnValue;
    }
  public void setReadableReturnValue(String readableReturnValue) {
        this.readableReturnValue = readableReturnValue;
    }
  public String getError() {
        return error;
    }
  public void setError(String error) {
        this.error = error;
    }
  public TransactionFeeDto getTransactionFee() {
        return transactionFee;
    }
  public void setTransactionFee(TransactionFeeDto transactionFee) {
        this.transactionFee = transactionFee;
    }
}
