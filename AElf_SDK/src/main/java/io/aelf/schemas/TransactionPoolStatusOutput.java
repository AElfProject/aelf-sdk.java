package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionPoolStatusOutput {
  @JsonProperty("Queued")
  private int queued;
  @JsonProperty("Validated")
  private int validated;

  public int getQueued() {
    return queued;
  }

  public void setQueued(int queued) {
    this.queued = queued;
  }

  public int getValidated() {
    return validated;
  }

  public void setValidated(int validated) {
    this.validated = validated;
  }
}
