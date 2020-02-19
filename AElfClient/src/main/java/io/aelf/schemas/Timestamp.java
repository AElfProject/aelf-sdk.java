package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Timestamp {
  @JsonProperty("Seconds")
  private long seconds;
  @JsonProperty("Nanos")
  private int nanos;

  public long getSeconds() {
    return seconds;
  }

  public void setSeconds(long seconds) {
    this.seconds = seconds;
  }

  public int getNanos() {
    return nanos;
  }

  public void setNanos(int nanos) {
    this.nanos = nanos;
  }
}
