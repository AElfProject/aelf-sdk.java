package io.aelf.schemas;


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
