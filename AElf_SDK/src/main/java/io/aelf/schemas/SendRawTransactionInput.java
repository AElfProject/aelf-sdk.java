package io.aelf.schemas;

/**
 * @author linhui linhui@tydic.com
 * @title: SendRawTransactionInput
 * @description: TODO
 * @date 2019/12/1516:14
 */
public class SendRawTransactionInput {
    private String Transaction;
    private String Signature;
    private boolean ReturnTransaction;

    public String getTransaction() {
        return Transaction;
    }

    public void setTransaction(String transaction) {
        Transaction = transaction;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public boolean isReturnTransaction() {
        return ReturnTransaction;
    }

    public void setReturnTransaction(boolean returnTransaction) {
        ReturnTransaction = returnTransaction;
    }
}
