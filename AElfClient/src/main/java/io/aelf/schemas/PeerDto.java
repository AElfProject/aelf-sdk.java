package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PeerDto {

  @JsonProperty("IpAddress")
  private String ipAddress;
  @JsonProperty("ProtocolVersion")
  private int protocolVersion;
  @JsonProperty("ConnectionTime")
  private long connectionTime;
  @JsonProperty("ConnectionStatus")
  private String connectionStatus;
  @JsonProperty("Inbound")
  private boolean inbound;
  @JsonProperty("BufferedTransactionsCount")
  private int bufferedTransactionsCount;
  @JsonProperty("BufferedBlocksCount")
  private int bufferedBlocksCount;
  @JsonProperty("BufferedAnnouncementsCount")
  private int bufferedAnnouncementsCount;
  @JsonProperty("RequestMetrics")
  private List<RequestMetric> requestMetrics;

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    ipAddress = ipAddress;
  }

  public int getProtocolVersion() {
    return protocolVersion;
  }

  public void setProtocolVersion(int protocolVersion) {
    this.protocolVersion = protocolVersion;
  }

  public long getConnectionTime() {
    return connectionTime;
  }

  public void setConnectionTime(long connectionTime) {
    this.connectionTime = connectionTime;
  }

  public String getConnectionStatus() {
    return this.connectionStatus;
  }

  public void setConnectionStatus(String connectionStatus) {
    this.connectionStatus = connectionStatus;
  }

  public boolean isInbound() {
    return this.inbound;
  }

  public void setInbound(boolean inbound) {
    this.inbound = inbound;
  }

  public int getBufferedTransactionsCount() {
    return this.bufferedTransactionsCount;
  }

  public void setBufferedTransactionsCount(int bufferedTransactionsCount) {
    this.bufferedTransactionsCount = bufferedTransactionsCount;
  }

  public int getBufferedBlocksCount() {
    return this.bufferedBlocksCount;
  }

  public void setBufferedBlocksCount(int bufferedBlocksCount) {
    this.bufferedBlocksCount = bufferedBlocksCount;
  }

  public int getBufferedAnnouncementsCount() {
    return this.bufferedAnnouncementsCount;
  }

  public void setBufferedAnnouncementsCount(int bufferedAnnouncementsCount) {
    this.bufferedAnnouncementsCount = bufferedAnnouncementsCount;
  }

  public List<RequestMetric> getRequestMetrics() {
    return requestMetrics;
  }

  public void setRequestMetrics(List<RequestMetric> requestMetrics) {
    this.requestMetrics = requestMetrics;
  }
}
