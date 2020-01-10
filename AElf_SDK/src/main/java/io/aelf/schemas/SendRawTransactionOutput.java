package io.aelf.schemas;


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
