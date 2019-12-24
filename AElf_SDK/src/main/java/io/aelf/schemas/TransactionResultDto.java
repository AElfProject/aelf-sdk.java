package io.aelf.schemas;

import java.util.List;

/**
 * @author linhui linhui@tydic.com
 * @title: TransactionResultDto
 * @description: TODO
 * @date 2019/12/1516:25
 */
public class TransactionResultDto {
    private String TransactionId;
    private String Status;
    private List<LogEventDto> Logs;
    private String Bloom;
    private long BlockNumber;
    private String BlockHash;
    private TransactionDto Transaction;
    private String ReturnValue;
    private String ReadableReturnValue;
    private String Error;
    private TransactionFeeDto TransactionFee;

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        this.TransactionId = transactionId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public List<LogEventDto> getLogs() {
        return Logs;
    }

    public void setLogs(List<LogEventDto> logs) {
        this.Logs = logs;
    }

    public String getBloom() {
        return Bloom;
    }

    public void setBloom(String bloom) {
        this.Bloom = bloom;
    }

    public long getBlockNumber() {
        return BlockNumber;
    }

    public void setBlockNumber(long blockNumber) {
        this.BlockNumber = blockNumber;
    }

    public String getBlockHash() {
        return BlockHash;
    }

    public void setBlockHash(String blockHash) {
        this.BlockHash = blockHash;
    }

    public TransactionDto getTransaction() {
        return Transaction;
    }

    public void setTransaction(TransactionDto transaction) {
        this.Transaction = transaction;
    }

    public String getReturnValue() {
        return ReturnValue;
    }

    public void setReturnValue(String returnValue) {
        this.ReturnValue = returnValue;
    }

    public String getReadableReturnValue() {
        return ReadableReturnValue;
    }

    public void setReadableReturnValue(String readableReturnValue) {
        this.ReadableReturnValue = readableReturnValue;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        this.Error = error;
    }

    public TransactionFeeDto getTransactionFee() {
        return TransactionFee;
    }

    public void setTransactionFee(TransactionFeeDto transactionFee) {
        this.TransactionFee = transactionFee;
    }
}
