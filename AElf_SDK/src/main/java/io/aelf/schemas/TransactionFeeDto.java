package io.aelf.schemas;

import java.util.HashMap;
import java.util.List;


public class TransactionFeeDto {
    private HashMap<String,Long> value;

    public HashMap<String,Long> getValue() {
        return value;
    }

    public void setValue(HashMap<String,Long> value) {
        this.value = value;
    }
}
