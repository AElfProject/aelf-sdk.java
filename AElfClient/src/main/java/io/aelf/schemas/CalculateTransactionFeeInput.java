package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalculateTransactionFeeInput {

    @JsonProperty("RawTransaction")
    private String RawTransaction;

    public String getRawTransaction() {
        return RawTransaction;
    }

    public void setRawTransaction(String rawTransaction) {
        RawTransaction = rawTransaction;
    }
}
