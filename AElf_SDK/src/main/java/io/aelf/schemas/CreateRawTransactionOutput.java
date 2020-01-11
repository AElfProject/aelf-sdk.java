package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateRawTransactionOutput {

  @JsonProperty("RawTransaction")
  private String rawTransaction;

  public String getRawTransaction() {
    return rawTransaction;
  }

  public void setRawTransaction(String rawTransaction) {
    this.rawTransaction = rawTransaction;
  }
}
