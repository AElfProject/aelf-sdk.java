package io.aelf.schemas;

import java.util.List;


public class PeerDto {
    private String IpAddress;
    private int ProtocolVersion;
    private long ConnectionTime;
    private String ConnectionStatus;
    private boolean Inbound;
    private int BufferedTransactionsCount;
    private int BufferedBlocksCount;
    private int BufferedAnnouncementsCount;
    private List<RequestMetric> RequestMetrics;
    public String getIpAddress() {
        return IpAddress;
    }

    public void setIpAddress(String ipAddress) {
        IpAddress = ipAddress;
    }

    public int getProtocolVersion() {
        return ProtocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        ProtocolVersion = protocolVersion;
    }

    public long getConnectionTime() {
        return ConnectionTime;
    }

    public void setConnectionTime(long connectionTime) {
        ConnectionTime = connectionTime;
    }

    public String getConnectionStatus() {
        return ConnectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        ConnectionStatus = connectionStatus;
    }

    public boolean isInbound() {
        return Inbound;
    }

    public void setInbound(boolean inbound) {
        Inbound = inbound;
    }

    public int getBufferedTransactionsCount() {
        return BufferedTransactionsCount;
    }

    public void setBufferedTransactionsCount(int bufferedTransactionsCount) {
        BufferedTransactionsCount = bufferedTransactionsCount;
    }

    public int getBufferedBlocksCount() {
        return BufferedBlocksCount;
    }

    public void setBufferedBlocksCount(int bufferedBlocksCount) {
        BufferedBlocksCount = bufferedBlocksCount;
    }

    public int getBufferedAnnouncementsCount() {
        return BufferedAnnouncementsCount;
    }

    public void setBufferedAnnouncementsCount(int bufferedAnnouncementsCount) {
        BufferedAnnouncementsCount = bufferedAnnouncementsCount;
    }

    public List<RequestMetric> getRequestMetrics() {
        return RequestMetrics;
    }

    public void setRequestMetrics(List<RequestMetric> requestMetrics) {
        RequestMetrics = requestMetrics;
    }
}
