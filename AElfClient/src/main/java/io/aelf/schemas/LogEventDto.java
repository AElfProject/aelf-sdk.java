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

  public void setAddress(String address) {
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getIndexed() {
    return this.indexed;
  }

  public void setIndexed(List<String> indexed) {
    this.indexed = indexed;
  }

  public String getNonIndexed() {
    return this.nonIndexed;
  }

  public void setNonIndexed(String nonIndexed) {
    this.nonIndexed = nonIndexed;
  }
}
