package io.aelf.schemas;


public class NetworkInfoOutput {
    private String Version;
    private int ProtocolVersion;
    private int Connections;
    public String getVersion() {
        return Version;
    }
    public void setVersion(String version) {
        this.Version = version;
    }
    public int getProtocolVersion() {
        return ProtocolVersion;
    }
    public void setProtocolVersion(int protocolVersion) {
        this.ProtocolVersion = protocolVersion;
    }
    public int getConnections() {
        return Connections;
    }
    public void setConnections(int connections) {
        Connections = connections;
    }

}
