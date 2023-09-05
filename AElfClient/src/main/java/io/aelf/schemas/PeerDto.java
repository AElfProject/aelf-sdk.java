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
  @JsonProperty("NodeVersion")
  private String nodeVersion;

  public String getIpAddress() {
    return ipAddress;
  }

  public PeerDto setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

  public int getProtocolVersion() {
    return protocolVersion;
  }

  public PeerDto setProtocolVersion(int protocolVersion) {
    this.protocolVersion = protocolVersion;
    return this;
  }

  public long getConnectionTime() {
    return connectionTime;
  }

  public PeerDto setConnectionTime(long connectionTime) {
    this.connectionTime = connectionTime;
    return this;
  }

  public String getConnectionStatus() {
    return this.connectionStatus;
  }

  public PeerDto setConnectionStatus(String connectionStatus) {
    this.connectionStatus = connectionStatus;
    return this;
  }

  public boolean isInbound() {
    return this.inbound;
  }

  public PeerDto setInbound(boolean inbound) {
    this.inbound = inbound;
    return this;
  }

  public int getBufferedTransactionsCount() {
    return this.bufferedTransactionsCount;
  }

  public PeerDto setBufferedTransactionsCount(int bufferedTransactionsCount) {
    this.bufferedTransactionsCount = bufferedTransactionsCount;
    return this;
  }

  public int getBufferedBlocksCount() {
    return this.bufferedBlocksCount;
  }

  public PeerDto setBufferedBlocksCount(int bufferedBlocksCount) {
    this.bufferedBlocksCount = bufferedBlocksCount;
    return this;
  }

  public int getBufferedAnnouncementsCount() {
    return this.bufferedAnnouncementsCount;
  }

  public PeerDto setBufferedAnnouncementsCount(int bufferedAnnouncementsCount) {
    this.bufferedAnnouncementsCount = bufferedAnnouncementsCount;
    return this;
  }

  public List<RequestMetric> getRequestMetrics() {
    return requestMetrics;
  }

  public PeerDto setRequestMetrics(List<RequestMetric> requestMetrics) {
    this.requestMetrics = requestMetrics;
    return this;
  }

  public String getNodeVersion() {
    return nodeVersion;
  }

  public PeerDto setNodeVersion(String nodeVersion) {
    this.nodeVersion = nodeVersion;
    return this;
  }
}
