package io.aelf.schemas;


public class TransactionDto {
    private String From;
    private String To;
    private long RefBlockNumber;
    private String RefBlockPrefix;
    private String MethodName;
    private String Params;
    private String Signature;

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

    public String getRefBlockPrefix() {
        return RefBlockPrefix;
    }

    public void setRefBlockPrefix(String refBlockPrefix) {
        RefBlockPrefix = refBlockPrefix;
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

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }
}
