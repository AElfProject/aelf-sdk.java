package io.aelf.schemas;

/**
 * @author linhui linhui@tydic.com
 * @title: ExecuteRawTransactionDto
 * @description: TODO
 * @date 2019/12/1514:44
 */
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
