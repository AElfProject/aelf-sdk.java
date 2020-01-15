package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecuteTransactionDto {

  @JsonProperty("RawTransaction")
  private String rawTransaction;

  /**
   * raw transaction.
   */
  public String getRawTransaction() {
    return rawTransaction;
  }

  /**
   * raw transaction.
   */
  public void setRawTransaction(String rawTransaction) {
    this.rawTransaction = rawTransaction;
  }
}
