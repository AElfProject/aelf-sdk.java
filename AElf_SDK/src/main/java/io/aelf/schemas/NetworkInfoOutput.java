package io.aelf.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NetworkInfoOutput {

  @JsonProperty("Version")
  private String version;
  @JsonProperty("ProtocolVersion")
  private int protocolVersion;
  @JsonProperty("Connections")
  private int connections;

  /**
   * node version.
   */
  public String getVersion() {
    return version;
  }

  /**
   * node version.
   */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * network protocol version.
   */
  public int getProtocolVersion() {
    return protocolVersion;
  }

  /**
   * network protocol version.
   */
  public void setProtocolVersion(int protocolVersion) {
    this.protocolVersion = protocolVersion;
  }

  /**
   * total number of open connections between this node and other nodes.
   */
  public int getConnections() {
    return connections;
  }

  /**
   * total number of open connections between this node and other nodes.
   */
  public void setConnections(int connections) {
    this.connections = connections;
  }
}
