package io.aelf.schemas;

/**
 * @author linhui linhui@tydic.com
 * @title: TransactionPoolStatusOutput
 * @description: TODO
 * @date 2019/12/1514:39
 */
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
