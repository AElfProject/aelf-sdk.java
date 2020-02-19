package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionDto {

  @JsonProperty("From")
  private String from;
  @JsonProperty("To")
  private String to;
  @JsonProperty("RefBlockNumber")
  private long refBlockNumber;
  @JsonProperty("RefBlockPrefix")
  private String refBlockPrefix;
  @JsonProperty("MethodName")
  private String methodName;
  @JsonProperty("Params")
  private String params;
  @JsonProperty("Signature")
  private String signature;

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public long getRefBlockNumber() {
    return refBlockNumber;
  }

  public void setRefBlockNumber(long refBlockNumber) {
    this.refBlockNumber = refBlockNumber;
  }

  public String getRefBlockPrefix() {
    return refBlockPrefix;
  }

  public void setRefBlockPrefix(String refBlockPrefix) {
    this.refBlockPrefix = refBlockPrefix;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public String getParams() {
    return params;
  }

  public void setParams(String params) {
    this.params = params;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }
}
