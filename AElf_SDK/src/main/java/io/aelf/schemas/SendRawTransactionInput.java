package io.aelf.schemas;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SendRawTransactionInput {
  @JsonProperty("Transaction")
  private String transaction;
  @JsonProperty("Signature")
  private String signature;
  @JsonProperty("ReturnTransaction")
  private boolean returnTransaction;
  /**
   * raw transaction
   * @return
   */
  public String getTransaction() {
        return transaction;
    }
  /**
   * raw transaction
   * @param transaction
   */
  public void setTransaction(String transaction) {
      this.transaction = transaction;
  }
  /**
   * signature
   * @return
   */
  public String getSignature() {
      return this.signature;
  }

  /**
   * signature
   * @param signature
   */
  public void setSignature(String signature) {
      this.signature = signature;
  }
  /**
   * return transaction detail or not
   * @return
   */
  public boolean isReturnTransaction() {
        return returnTransaction;
    }
  /**
   * return transaction detail or not
   * @param returnTransaction
   */
  public void setReturnTransaction(boolean returnTransaction) {
        this.returnTransaction = returnTransaction;
    }
}
