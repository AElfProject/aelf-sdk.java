package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used for the public super class
 * for both {@link ExecuteTransactionDto} and {@link SendTransactionInput}
 * to erase their differences.
 */
public class TransactionWrapper {
    @JsonProperty("RawTransaction")
    protected String rawTransaction;

    /**
     * raw transaction.
     */
    public String getRawTransaction() {
        return rawTransaction;
    }

    /**
     * raw transaction.
     */
    public void setRawTransaction(String rawTransaction) {
        this.rawTransaction = rawTransaction;
    }
}
