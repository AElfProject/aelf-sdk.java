package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendTransactionsInput {

  @JsonProperty("RawTransactions")
  private String rawTransactions;

  /**
   * raw transactions.
   *
   * @return String
   */
  public String getRawTransactions() {
    return rawTransactions;
  }

  /**
   * raw transactions.
   */
  public void setRawTransactions(final String rawTransactions) {
    this.rawTransactions = rawTransactions;
  }
}
