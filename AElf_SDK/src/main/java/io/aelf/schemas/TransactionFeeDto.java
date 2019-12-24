package io.aelf.schemas;

import java.util.HashMap;
import java.util.List;

/**
 * @author linhui linhui@tydic.com
 * @title: TransactionFeeDto
 * @description: TODO
 * @date 2019/12/1516:23
 */
public class TransactionFeeDto {
    private HashMap<String,Long> value;

    public HashMap<String,Long> getValue() {
        return value;
    }

    public void setValue(HashMap<String,Long> value) {
        this.value = value;
    }
}
