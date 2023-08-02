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

  public TransactionDto setFrom(String from) {
    this.from = from;
    return this;
  }

  public String getTo() {
    return to;
  }

  public TransactionDto setTo(String to) {
    this.to = to;
    return this;
  }

  public long getRefBlockNumber() {
    return refBlockNumber;
  }

  public TransactionDto setRefBlockNumber(long refBlockNumber) {
    this.refBlockNumber = refBlockNumber;
    return this;
  }

  public String getRefBlockPrefix() {
    return refBlockPrefix;
  }

  public TransactionDto setRefBlockPrefix(String refBlockPrefix) {
    this.refBlockPrefix = refBlockPrefix;
    return this;
  }

  public String getMethodName() {
    return methodName;
  }

  public TransactionDto setMethodName(String methodName) {
    this.methodName = methodName;
    return this;
  }

  public String getParams() {
    return params;
  }

  public TransactionDto setParams(String params) {
    this.params = params;
    return this;
  }

  public String getSignature() {
    return signature;
  }

  public TransactionDto setSignature(String signature) {
    this.signature = signature;
    return this;
  }
}
