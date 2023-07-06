package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LogEventDto {

  @JsonProperty("Address")
  private String address;
  @JsonProperty("Name")
  private String name;
  @JsonProperty("Indexed")
  private List<String> indexed;
  @JsonProperty("NonIndexed")
  private String nonIndexed;

  public String getAddress() {
    return address;
  }

  public LogEventDto setAddress(String address) {
    this.address = address;
    return this;
  }

  public String getName() {
    return name;
  }

  public LogEventDto setName(String name) {
    this.name = name;
    return this;
  }

  public List<String> getIndexed() {
    return this.indexed;
  }

  public LogEventDto setIndexed(List<String> indexed) {
    this.indexed = indexed;
    return this;
  }

  public String getNonIndexed() {
    return this.nonIndexed;
  }

  public LogEventDto setNonIndexed(String nonIndexed) {
    this.nonIndexed = nonIndexed;
    return this;
  }
}
