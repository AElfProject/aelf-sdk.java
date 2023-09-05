package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

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
  @JsonProperty("Error")
  private String error;
  @JsonProperty("TransactionFee")
  private TransactionFeeDto transactionFee;

  public String getTransactionId() {
    return transactionId;
  }

  public TransactionResultDto setTransactionId(String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public TransactionResultDto setStatus(String status) {
    this.status = status;
    return this;
  }

  public List<LogEventDto> getLogs() {
    return logs;
  }

  public TransactionResultDto setLogs(List<LogEventDto> logs) {
    this.logs = logs;
    return this;
  }

  public String getBloom() {
    return bloom;
  }

  public TransactionResultDto setBloom(String bloom) {
    this.bloom = bloom;
    return this;
  }

  public long getBlockNumber() {
    return blockNumber;
  }

  public TransactionResultDto setBlockNumber(long blockNumber) {
    this.blockNumber = blockNumber;
    return this;
  }

  public String getBlockHash() {
    return blockHash;
  }

  public TransactionResultDto setBlockHash(String blockHash) {
    this.blockHash = blockHash;
    return this;
  }

  public TransactionDto getTransaction() {
    return transaction;
  }

  public TransactionResultDto setTransaction(TransactionDto transaction) {
    this.transaction = transaction;
    return this;
  }

  public String getReturnValue() {
    return returnValue;
  }

  public TransactionResultDto setReturnValue(String returnValue) {
    this.returnValue = returnValue;
    return this;
  }

  public String getError() {
    return error;
  }

  public TransactionResultDto setError(String error) {
    this.error = error;
    return this;
  }

  public TransactionFeeDto getTransactionFee() {
    return transactionFee;
  }

  public TransactionResultDto setTransactionFee(TransactionFeeDto transactionFee) {
    this.transactionFee = transactionFee;
    return this;
  }
}
