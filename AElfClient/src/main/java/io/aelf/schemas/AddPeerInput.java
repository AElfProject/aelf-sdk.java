package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddPeerInput {
  @JsonProperty("address")
  private String address;

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
