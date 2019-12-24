package io.aelf.schemas;

/**
 * @author linhui linhui@tydic.com
 * @title: Timestamp
 * @description: TODO
 * @date 2019/12/1513:58
 */
public class Timestamp {
    private long Seconds;
    private int Nanos;

    public long getSeconds() {
        return Seconds;
    }

    public void setSeconds(long seconds) {
        Seconds = seconds;
    }

    public int getNanos() {
        return Nanos;
    }

    public void setNanos(int nanos) {
        Nanos = nanos;
    }
}
