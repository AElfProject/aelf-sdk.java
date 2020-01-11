package io.aelf.schemas;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SendTransactionInput {
  @JsonProperty("RawTransaction")
  private String rawTransaction;
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
}
