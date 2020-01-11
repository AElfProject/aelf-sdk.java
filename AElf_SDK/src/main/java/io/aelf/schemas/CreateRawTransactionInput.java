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
   * from address
   * @return
   */
  public String getFrom() {
        return from;
    }
  /**
   * from address
   * @param from
   */
  public void setFrom(String from) {
        this.from = from;
    }

  /**
   * to address
   * @return
   */
  public String getTo() {
        return to;
    }

  /**
   * to address
   * @param to
   */
  public void setTo(String to) {
        this.to = to;
    }
  /**
   * refer block height
   * @return
   */
  public long getRefBlockNumber() {
        return refBlockNumber;
    }

  /**
   * refer block height
   * @param refBlockNumber
   */
  public void setRefBlockNumber(long refBlockNumber) {
        this.refBlockNumber = refBlockNumber;
    }

  /**
   * refer block height
   * @return
   */
  public String getRefBlockHash() {
        return this.refBlockHash;
    }

  /**
   * refer block hash
   * @param refBlockHash
   */
  public void setRefBlockHash(String refBlockHash) {
        this.refBlockHash = refBlockHash;
    }

  /**
   * contract method name
   * @return
   */
  public String getMethodName() {
        return this.methodName;
    }

  /**
   * contract method name
   * @param methodName
   */
  public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

   /**
    * contract method parameters
    * @return
    */
  public String getParams() {
        return params;
    }
  /**
   * contract method parameters
   * @param params
   */
  public void setParams(String params) {
      this.params = params;
  }
}
