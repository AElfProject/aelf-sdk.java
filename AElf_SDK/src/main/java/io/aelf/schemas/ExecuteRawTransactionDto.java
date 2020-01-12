package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecuteRawTransactionDto {

  @JsonProperty("RawTransaction")
  private String rawTransaction;
  @JsonProperty("Signature")
  private String signature;

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

  /**
   * signature.
   */
  public String getSignature() {
    return signature;
  }

  /**
   * signature.
   */
  public void setSignature(String signature) {
    this.signature = signature;
  }
}
