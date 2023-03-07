package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class CalculateTransactionFeeOutput {
    @JsonProperty("Success")


    private boolean success;
    @JsonProperty("TransactionFee")

    private HashMap<String,Long> transactionFee;
    @JsonProperty("ResourceFee")

    private HashMap<String,Long> resourceFee;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public HashMap<String, Long> getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(HashMap<String, Long> transactionFee) {
        this.transactionFee = transactionFee;
    }

    public HashMap<String, Long> getResourceFee() {
        return resourceFee;
    }

    public void setResourceFee(HashMap<String, Long> resourceFee) {
        this.resourceFee = resourceFee;
    }
}
