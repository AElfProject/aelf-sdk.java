package io.aelf.schemas;

/**
 * @author linhui linhui@tydic.com
 * @title: RequestMetric
 * @description: TODO
 * @date 2019/12/1513:57
 */
public class RequestMetric {
    private  long RoundTripTime;
    private String MethodName;
    private String Info;
    private Timestamp RequestTime;

    public long getRoundTripTime() {
        return RoundTripTime;
    }

    public void setRoundTripTime(long roundTripTime) {
        RoundTripTime = roundTripTime;
    }

    public String getMethodName() {
        return MethodName;
    }

    public void setMethodName(String methodName) {
        MethodName = methodName;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public Timestamp getRequestTime() {
        return RequestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        RequestTime = requestTime;
    }
}
