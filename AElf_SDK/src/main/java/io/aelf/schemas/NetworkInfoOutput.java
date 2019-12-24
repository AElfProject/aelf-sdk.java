package io.aelf.schemas;

/**
 * @author linhui linhui@tydic.com
 * @title: NetworkInfoOutput
 * @description: TODO
 * @date 2019/12/1514:01
 */
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
