package io.aelf.schemas;
import java.util.List;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonProperty;

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
