package io.aelf.sdk;

import io.aelf.schemas.AddPeerInput;
import io.aelf.schemas.NetworkInfoOutput;
import io.aelf.schemas.PeerDto;
import io.aelf.schemas.RequestMetric;
import io.aelf.schemas.Timestamp;
import io.aelf.utils.ClientUtil;
import io.aelf.utils.HttpUtilExt;
import io.aelf.utils.JsonUtil;
import io.aelf.utils.MapEntry;
import io.aelf.utils.Maps;
import io.aelf.utils.StringUtil;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("unchecked")
public class NetSdk {

  private String AElfClientUrl;
  private String version;
  private String UserName;
  private String Password;
  private static final String WA_ADDPEER = "/api/net/peer";
  private static final String WA_REMOVEPEER = "/api/net/peer";
  private static final String WA_GETPEERS = "/api/net/peers";
  private static final String WA_GETNETWORKINFO = "/api/net/networkInfo";

  /**
   * Object construction through the url path.
   *
   * @param url Http Request Url exp:(http://xxxx)
   * @param version application/json;v={version}
   * @param userName
   * @param password
   */
  public NetSdk(String url, String version, String userName, String password) {
    this.AElfClientUrl = url;
    this.version = version;
    this.UserName = userName;
    this.Password = password;
  }

  private NetSdk() {
  }

  /**
   * Attempts to add a node to the connected network nodes wa:/api/net/peer.
   */
  public Boolean addPeer(AddPeerInput input) throws Exception {
    String url = this.AElfClientUrl + WA_ADDPEER;
    MapEntry mapParmas = Maps.newMap();

    String combineString = this.UserName + ":" + this.Password;
    String combineAuth = "Basic " + Base64.getEncoder().encodeToString(combineString.getBytes());

    mapParmas.put("Address", input.getAddress());
    String responseBobyResult = HttpUtilExt
        .sendPostWithAuth(url, JsonUtil.toJsonString(mapParmas), this.version, combineAuth);
    if ("true".equals(responseBobyResult)) {
      return true;
    }
    return false;
  }

  /**
   * Attempts to remove a node from the connected network nodes wa:/api/net/peer.
   */
  public Boolean removePeer(String address) throws Exception {
    String url = this.AElfClientUrl + WA_REMOVEPEER + "?address=" + address;

    String combineString = this.UserName + ":" + this.Password;
    String combineAuth = "Basic " + Base64.getEncoder().encodeToString(combineString.getBytes());

    String responseBobyResult = HttpUtilExt.sendDelete(url, "UTF-8", this.version, combineAuth);
   return "true".equals(responseBobyResult);
  }

  /**
   * Gets information about the peer nodes of the current node.Optional whether to include metrics.
   * wa:/api/net/peers?withMetrics=false
   */
  public List<PeerDto> getPeers(Boolean withMetrics) throws Exception {
    String url = this.AElfClientUrl + WA_GETPEERS + "?withMetrics=" + withMetrics;
    String peersChain = ClientUtil.sendGet(url, "UTF-8", this.version);
    List<PeerDto> listPeerDto = new ArrayList<PeerDto>();
    List<LinkedHashMap> responseBobyList = JsonUtil.parseObject(peersChain, List.class);
    for (LinkedHashMap responseBobyObj : responseBobyList) {
      MapEntry responseBobyMapObj = Maps.cloneMapEntry(responseBobyObj);
      PeerDto peerDtoObj = new PeerDto();
      peerDtoObj.setIpAddress(responseBobyMapObj.getString("IpAddress", ""));
      peerDtoObj.setProtocolVersion(responseBobyMapObj.getInteger("ProtocolVersion", 0));
      peerDtoObj.setConnectionTime(responseBobyMapObj.getLong("ConnectionTime", 0));
      peerDtoObj.setConnectionStatus(responseBobyMapObj.getString("ConnectionStatus", ""));
      peerDtoObj.setInbound(responseBobyMapObj.getBoolean("Inbound", false));
      peerDtoObj.setBufferedAnnouncementsCount(
          responseBobyMapObj.getInteger("BufferedTransactionsCount", 0));
      peerDtoObj.setBufferedBlocksCount(responseBobyMapObj.getInteger("BufferedBlocksCount", 0));
      peerDtoObj.setBufferedTransactionsCount(
          responseBobyMapObj.getInteger("BufferedAnnouncementsCount", 0));
      peerDtoObj.setRequestMetrics(new ArrayList());
      List<LinkedHashMap> requestMetricsList = responseBobyMapObj
          .getArrayList("RequestMetrics", new ArrayList<LinkedHashMap>());
      for (LinkedHashMap requestMetricsObj : requestMetricsList) {
        MapEntry requestMetricsMapObj = Maps.cloneMapEntry(requestMetricsObj);
        RequestMetric requestMetricObj = new RequestMetric();
        requestMetricObj.setMethodName(requestMetricsMapObj.getString("MethodName", ""));
        requestMetricObj.setRoundTripTime(requestMetricsMapObj.getLong("RoundTripTime", 0));
        requestMetricObj.setInfo(requestMetricsMapObj.getString("Info", ""));
        requestMetricObj.setRequestTime(new Timestamp());

        LinkedHashMap requestTimeObj = requestMetricsMapObj
            .getLinkedHashMap("RequestTime", new LinkedHashMap());
        if (requestTimeObj.containsKey("Nanos")) {
          int value = Integer
              .parseInt(StringUtil.toString(requestTimeObj.getOrDefault("Nanos", "0")));
          requestMetricObj.getRequestTime().setNanos(value);
        }
        if (requestTimeObj.containsKey("Seconds")) {
          long value = Long
              .parseLong(StringUtil.toString(requestTimeObj.getOrDefault("Nanos", "0")));
          requestMetricObj.getRequestTime().setSeconds(value);
        }
        peerDtoObj.getRequestMetrics().add(requestMetricObj);

      }
      listPeerDto.add(peerDtoObj);
    }
    return listPeerDto;
  }

  /**
   * Get information about the node’s connection to the network. wa:/api/net/networkInfo
   */
  public NetworkInfoOutput getNetworkInfo() throws Exception {
    String networkChain = ClientUtil
        .sendGet(this.AElfClientUrl + WA_GETNETWORKINFO, "UTF-8", this.version);
    MapEntry responseBobyMap = JsonUtil.parseObject(networkChain);
    NetworkInfoOutput networkInfoOutput = new NetworkInfoOutput();
    networkInfoOutput.setVersion(responseBobyMap.getString("Version"));
    networkInfoOutput.setConnections(responseBobyMap.getInteger("Connections", 0));
    networkInfoOutput.setProtocolVersion(responseBobyMap.getInteger("ProtocolVersion", 0));
    return networkInfoOutput;
  }
}
