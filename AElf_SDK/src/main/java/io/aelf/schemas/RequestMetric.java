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

  public void setRoundTripTime(long roundTripTime) {
    this.roundTripTime = roundTripTime;
  }

  public String getMethodName() {
    return this.methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public String getInfo() {
    return this.info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public Timestamp getRequestTime() {
    return this.requestTime;
  }

  public void setRequestTime(Timestamp requestTime) {
    this.requestTime = requestTime;
  }
}
