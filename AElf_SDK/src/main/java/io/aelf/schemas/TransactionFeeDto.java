package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;


public class TransactionFeeDto {
  @JsonProperty("value")
  private HashMap<String,Long> value;

  public HashMap<String,Long> getValue() {
    return value;
  }

  public void setValue(HashMap<String,Long> value) {
    this.value = value;
  }
}
