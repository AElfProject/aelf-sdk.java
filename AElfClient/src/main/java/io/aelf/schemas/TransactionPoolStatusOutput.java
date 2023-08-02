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

  public TransactionPoolStatusOutput setQueued(int queued) {
    this.queued = queued;
    return this;
  }

  public int getValidated() {
    return validated;
  }

  public TransactionPoolStatusOutput setValidated(int validated) {
    this.validated = validated;
    return this;
  }
}
