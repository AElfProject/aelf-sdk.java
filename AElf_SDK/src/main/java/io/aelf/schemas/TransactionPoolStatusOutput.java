package io.aelf.schemas;


public class TransactionPoolStatusOutput {
    private int Queued;
    private int Validated;
    public int getQueued() {
        return Queued;
    }
    public void setQueued(int queued) {
        this.Queued = queued;
    }
    public int getValidated() {
        return Validated;
    }
    public void setValidated(int validated) {
        this.Validated = validated;
    }
}
