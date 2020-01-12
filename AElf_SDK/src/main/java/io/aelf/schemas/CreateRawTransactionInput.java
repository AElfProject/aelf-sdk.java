package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateRawTransactionInput {

  @JsonProperty("From")
  private String from;
  @JsonProperty("To")
  private String to;
  @JsonProperty("RefBlockNumber")
  private long refBlockNumber;
  @JsonProperty("RefBlockHash")
  private String refBlockHash;
  @JsonProperty("MethodName")
  private String methodName;
  @JsonProperty("Params")
  private String params;

  /**
   * from address.
   */
  public String getFrom() {
    return from;
  }

  /**
   * from address.
   */
  public void setFrom(String from) {
    this.from = from;
  }

  /**
   * to address.
   */
  public String getTo() {
    return to;
  }

  /**
   * to address.
   */
  public void setTo(String to) {
    this.to = to;
  }

  /**
   * refer block height.
   */
  public long getRefBlockNumber() {
    return refBlockNumber;
  }

  /**
   * refer block height.
   */
  public void setRefBlockNumber(long refBlockNumber) {
    this.refBlockNumber = refBlockNumber;
  }

  /**
   * refer block height.
   */
  public String getRefBlockHash() {
    return this.refBlockHash;
  }

  /**
   * refer block hash.
   */
  public void setRefBlockHash(String refBlockHash) {
    this.refBlockHash = refBlockHash;
  }

  /**
   * contract method name.
   */
  public String getMethodName() {
    return this.methodName;
  }

  /**
   * contract method name.
   */
  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  /**
   * contract method parameters.
   */
  public String getParams() {
    return params;
  }

  /**
   * contract method parameters.
   */
  public void setParams(String params) {
    this.params = params;
  }
}
