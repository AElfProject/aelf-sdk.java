package io.aelf.schemas;


public class ExecuteRawTransactionDto {
    private String RawTransaction;
    private String Signature;

    public String getRawTransaction() {
        return RawTransaction;
    }

    public void setRawTransaction(String rawTransaction) {
        RawTransaction = rawTransaction;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }
}
