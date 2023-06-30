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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("unchecked")
public class NetSdk {
  private final String AElfClientUrl;
  private final String version;
  private final String combineAuth;
  private static final String WA_ADD_PEER = "/api/net/peer";
  private static final String WA_REMOVE_PEER = "/api/net/peer";
  private static final String WA_GET_PEERS = "/api/net/peers";
  private static final String WA_GET_NETWORK_INFO = "/api/net/networkInfo";

  /**
   * Object construction through the url path.
   */
  public NetSdk(String url, String version, @Nullable String userName, @Nullable String password) {
    this.AElfClientUrl = url;
    this.version = version;
    String combineString = userName + ":" + password;
    this.combineAuth = "Basic " + Base64.getEncoder().encodeToString(combineString.getBytes());
  }

  /**
   * Attempts to add a node to the connected network nodes wa:/api/net/peer.
   */
  public Boolean addPeer(AddPeerInput input) throws Exception {
    String url = this.AElfClientUrl + WA_ADD_PEER;
    MapEntry<String,String> mapParams = Maps.newMap();
    mapParams.put("Address", input.getAddress());
    String responseBodyResult = HttpUtilExt
        .sendPostWithAuth(url, JsonUtil.toJsonString(mapParams), this.version, this.combineAuth);
    return "true".equals(responseBodyResult);
  }

  /**
   * Attempts to remove a node from the connected network nodes wa:/api/net/peer.
   */
  public Boolean removePeer(String address) throws Exception {
    String url = this.AElfClientUrl + WA_REMOVE_PEER + "?address=" + address;
    String responseBodyResult = HttpUtilExt.sendDelete(url, "UTF-8", this.version, this.combineAuth);
   return "true".equals(responseBodyResult);
  }

  /**
   * Gets information about the peer nodes of the current node.Optional whether to include metrics.
   * wa:/api/net/peers?withMetrics=false
   */
  public List<PeerDto> getPeers(Boolean withMetrics) throws Exception {
    String url = this.AElfClientUrl + WA_GET_PEERS + "?withMetrics=" + withMetrics;
    String peersChain = ClientUtil.sendGet(url, "UTF-8", this.version);
    List<PeerDto> listPeerDto = new ArrayList<>();
    List<LinkedHashMap<String,?>> responseBobyList = JsonUtil.parseObject(peersChain, List.class);
    for (LinkedHashMap<String,?> responseBodyObj : responseBobyList) {
      MapEntry<String,?> responseBodyMapObj = Maps.cloneMapEntry(responseBodyObj);
      PeerDto peerDtoObj = new PeerDto();
      peerDtoObj.setIpAddress(responseBodyMapObj.getString("IpAddress", ""));
      peerDtoObj.setProtocolVersion(responseBodyMapObj.getInteger("ProtocolVersion", 0));
      peerDtoObj.setConnectionTime(responseBodyMapObj.getLong("ConnectionTime", 0));
      peerDtoObj.setConnectionStatus(responseBodyMapObj.getString("ConnectionStatus", ""));
      peerDtoObj.setInbound(responseBodyMapObj.getBoolean("Inbound", false));
      peerDtoObj.setBufferedAnnouncementsCount(
          responseBodyMapObj.getInteger("BufferedTransactionsCount", 0));
      peerDtoObj.setBufferedBlocksCount(responseBodyMapObj.getInteger("BufferedBlocksCount", 0));
      peerDtoObj.setBufferedTransactionsCount(
          responseBodyMapObj.getInteger("BufferedAnnouncementsCount", 0));
      peerDtoObj.setRequestMetrics(new ArrayList<>());
      peerDtoObj.setNodeVersion(responseBodyMapObj.getString("NodeVersion",""));
      List<LinkedHashMap<String,?>> requestMetricsList = responseBodyMapObj
          .getArrayList("RequestMetrics", new ArrayList<LinkedHashMap<String,?>>());
      for (LinkedHashMap<String,?> requestMetricsObj : requestMetricsList) {
        MapEntry<String,?> requestMetricsMapObj = Maps.cloneMapEntry(requestMetricsObj);
        RequestMetric requestMetricObj = new RequestMetric();
        requestMetricObj.setMethodName(requestMetricsMapObj.getString("MethodName", ""));
        requestMetricObj.setRoundTripTime(requestMetricsMapObj.getLong("RoundTripTime", 0));
        requestMetricObj.setInfo(requestMetricsMapObj.getString("Info", ""));
        requestMetricObj.setRequestTime(new Timestamp());

        LinkedHashMap<String, String> requestTimeObj = requestMetricsMapObj
            .getLinkedHashMap("RequestTime", new LinkedHashMap<>());
        if (requestTimeObj.containsKey("Nanos")) {
          int value = Integer
              .parseInt(StringUtil.toString(requestTimeObj.getOrDefault("Nanos", "0")));
          requestMetricObj.getRequestTime().setNanos(value);
        }
        if (requestTimeObj.containsKey("Seconds")) {
          long value = Long
              .parseLong(StringUtil.toString(
                      requestTimeObj.getOrDefault("Nanos", "0")));
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
        .sendGet(this.AElfClientUrl + WA_GET_NETWORK_INFO, "UTF-8", this.version);
    MapEntry<String,?> responseBodyMap = JsonUtil.parseObject(networkChain);
    if(responseBodyMap==null) throw new RuntimeException();
    NetworkInfoOutput networkInfoOutput = new NetworkInfoOutput();
    networkInfoOutput.setVersion(responseBodyMap.getString("Version"));
    networkInfoOutput.setConnections(responseBodyMap.getInteger("Connections", 0));
    networkInfoOutput.setProtocolVersion(responseBodyMap.getInteger("ProtocolVersion", 0));
    return networkInfoOutput;
  }
}
