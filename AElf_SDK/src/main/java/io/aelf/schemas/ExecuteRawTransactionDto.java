package io.aelf.schemas;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecuteRawTransactionDto {
  @JsonProperty("RawTransaction")
  private String rawTransaction;
  @JsonProperty("Signature")
  private String signature;

  /**
   * raw transaction
   * @return
   */
  public String getRawTransaction() {
      return rawTransaction;
  }
  /**
   * raw transaction
   * @param rawTransaction
   */
  public void setRawTransaction(String rawTransaction) {
        this.rawTransaction = rawTransaction;
    }
  /**
   * signature
   * @return
   */
  public String getSignature() {
      return signature;
  }
  /**
   * signature
   * @param signature
   */
  public void setSignature(String signature) {
      this.signature = signature;
  }
}
