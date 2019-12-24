package io.aelf.schemas;

/**
 * @author linhui linhui@tydic.com
 * @title: SendRawTransactionOutput
 * @description: TODO
 * @date 2019/12/1516:14
 */
public class SendRawTransactionOutput {
    private String TransactionId;
    private TransactionDto Transaction;

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public TransactionDto getTransaction() {
        return Transaction;
    }

    public void setTransaction(TransactionDto transaction) {
        Transaction = transaction;
    }
}
