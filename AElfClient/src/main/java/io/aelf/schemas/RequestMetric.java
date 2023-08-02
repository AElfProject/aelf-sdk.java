package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestMetric {

  @JsonProperty("RoundTripTime")
  private long roundTripTime;
  @JsonProperty("MethodName")
  private String methodName;
  @JsonProperty("Info")
  private String info;
  @JsonProperty("RequestTime")
  private Timestamp requestTime;

  public long getRoundTripTime() {
    return roundTripTime;
  }

  public RequestMetric setRoundTripTime(long roundTripTime) {
    this.roundTripTime = roundTripTime;
    return this;
  }

  public String getMethodName() {
    return this.methodName;
  }

  public RequestMetric setMethodName(String methodName) {
    this.methodName = methodName;
    return this;
  }

  public String getInfo() {
    return this.info;
  }

  public RequestMetric setInfo(String info) {
    this.info = info;
    return this;
  }

  public Timestamp getRequestTime() {
    return this.requestTime;
  }

  public RequestMetric setRequestTime(Timestamp requestTime) {
    this.requestTime = requestTime;
    return this;
  }
}
