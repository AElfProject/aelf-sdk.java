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
  public NetworkInfoOutput setVersion(String version) {
    this.version = version;
    return this;
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
  public NetworkInfoOutput setProtocolVersion(int protocolVersion) {
    this.protocolVersion = protocolVersion;
    return this;
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
  public NetworkInfoOutput setConnections(int connections) {
    this.connections = connections;
    return this;
  }
}
