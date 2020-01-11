package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskQueueInfoDto {
  @JsonProperty("Name")
  private String name;
  @JsonProperty("Size")
  private int size;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
