package io.aelf.schemas;

/**
 * @author linhui linhui@tydic.com
 * @title: SendTransactionsInput
 * @description: TODO
 * @date 2019/12/1516:21
 */
public class SendTransactionsInput {
    private String RawTransactions;

    public String getRawTransactions() {
        return RawTransactions;
    }

    public void setRawTransactions(String rawTransactions) {
        RawTransactions = rawTransactions;
    }
}
