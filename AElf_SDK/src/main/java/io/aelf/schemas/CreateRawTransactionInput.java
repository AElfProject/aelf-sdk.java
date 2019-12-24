package io.aelf.schemas;

/**
 * @author linhui linhui@tydic.com
 * @title: CreateRawTransactionInput
 * @description: TODO
 * @date 2019/12/1514:49
 */
public class CreateRawTransactionInput {
    private String From;
    private String To;
    private long RefBlockNumber;
    private String RefBlockHash;
    private String MethodName;
    private String Params;

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public long getRefBlockNumber() {
        return RefBlockNumber;
    }

    public void setRefBlockNumber(long refBlockNumber) {
        RefBlockNumber = refBlockNumber;
    }

    public String getRefBlockHash() {
        return RefBlockHash;
    }

    public void setRefBlockHash(String refBlockHash) {
        RefBlockHash = refBlockHash;
    }

    public String getMethodName() {
        return MethodName;
    }

    public void setMethodName(String methodName) {
        MethodName = methodName;
    }

    public String getParams() {
        return Params;
    }

    public void setParams(String params) {
        Params = params;
    }
}
