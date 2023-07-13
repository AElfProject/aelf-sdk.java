package io.aelf.sdk;

import io.aelf.schemas.AddPeerInput;
import io.aelf.schemas.NetworkInfoOutput;
import io.aelf.schemas.PeerDto;
import io.aelf.schemas.RequestMetric;
import io.aelf.schemas.Timestamp;
import io.aelf.utils.AElfUrl;
import io.aelf.utils.HttpUtilExt;
import io.aelf.utils.JsonUtil;
import io.aelf.utils.MapEntry;
import io.aelf.utils.Maps;
import io.aelf.utils.StringUtil;
import io.aelf.network.APIPath;
import io.aelf.network.NetworkConnector;

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

  /**
   * Init the NetSdk.
   * 
   * @param url      the url of the node
   * @param version  the version string
   * @param userName the user name, can be null
   * @param password the password, can be null
   */
  public NetSdk(String url, String version, @Nullable String userName, @Nullable String password) {
    this.AElfClientUrl = url;
    this.version = version;
    String combineString = userName + ":" + password;
    this.combineAuth = "Basic " + Base64.getEncoder().encodeToString(combineString.getBytes());
  }

  /**
   * Attempts to add a node to the connected network nodes.
   * 
   * @param input {@link AddPeerInput} node's information
   * @return true if success
   */
  @AElfUrl(url = "wa://api/net/peer")
  public Boolean addPeer(AddPeerInput input) throws Exception {
    String url = this.AElfClientUrl + APIPath.WA_ADD_PEER;
    MapEntry<String, String> mapParams = Maps.newMap();
    mapParams.put("Address", input.getAddress());
    String responseBodyResult = HttpUtilExt
        .sendPostWithAuth(url, JsonUtil.toJsonString(mapParams), this.version, this.combineAuth);
    return "true".equals(responseBodyResult);
  }

  /**
   * Attempts to remove a node from the connected network nodes.
   * 
   * @param address the url of the node
   * @return true if success
   */
  @AElfUrl(url = "wa://api/net/peer")
  public Boolean removePeer(String address) {
    String url = this.AElfClientUrl + APIPath.WA_REMOVE_PEER + "?address=" + address;
    String responseBodyResult = HttpUtilExt.sendDelete(url, "UTF-8", this.version, this.combineAuth);
    return "true".equals(responseBodyResult);
  }

  /**
   * Gets information about the peer nodes of the current node.
   * <p>
   * Optional: whether to include metrics.
   * 
   * @param withMetrics whether to include metrics
   * @return {@link NetworkInfoOutput} the information of the network
   */
  @AElfUrl(url = "wa://api/net/peers?withMetrics={withMetrics}")
  public List<PeerDto> getPeers(Boolean withMetrics) {
    String url = this.AElfClientUrl + APIPath.WA_GET_PEERS + "?withMetrics=" + withMetrics;
    String peersChain = NetworkConnector.getIns().sendGet(url, "UTF-8", this.version);
    List<PeerDto> listPeerDto = new ArrayList<>();
    List<LinkedHashMap<String, ?>> responseBobyList = JsonUtil.parseObject(peersChain, List.class);
    for (LinkedHashMap<String, ?> responseBodyObj : responseBobyList) {
      MapEntry<String, ?> responseBodyMapObj = Maps.cloneMapEntry(responseBodyObj);
      PeerDto peerDtoObj = new PeerDto()
          .setIpAddress(responseBodyMapObj.getString("IpAddress", ""))
          .setProtocolVersion(responseBodyMapObj.getInteger("ProtocolVersion", 0))
          .setConnectionTime(responseBodyMapObj.getLong("ConnectionTime", 0))
          .setConnectionStatus(responseBodyMapObj.getString("ConnectionStatus", ""))
          .setInbound(responseBodyMapObj.getBoolean("Inbound", false))
          .setBufferedAnnouncementsCount(
              responseBodyMapObj.getInteger("BufferedTransactionsCount", 0))
          .setBufferedBlocksCount(responseBodyMapObj.getInteger("BufferedBlocksCount", 0))
          .setBufferedTransactionsCount(
              responseBodyMapObj.getInteger("BufferedAnnouncementsCount", 0))
          .setRequestMetrics(new ArrayList<>())
          .setNodeVersion(responseBodyMapObj.getString("NodeVersion", ""));
      List<LinkedHashMap<String, ?>> requestMetricsList = responseBodyMapObj
          .getArrayList("RequestMetrics", new ArrayList<LinkedHashMap<String, ?>>());
      for (LinkedHashMap<String, ?> requestMetricsObj : requestMetricsList) {
        MapEntry<String, ?> requestMetricsMapObj = Maps.cloneMapEntry(requestMetricsObj);
        RequestMetric requestMetricObj = new RequestMetric()
            .setMethodName(requestMetricsMapObj.getString("MethodName", ""))
            .setRoundTripTime(requestMetricsMapObj.getLong("RoundTripTime", 0))
            .setInfo(requestMetricsMapObj.getString("Info", ""))
            .setRequestTime(new Timestamp());

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
   * Get information about the node’s connection to the network.
   * 
   * @return {@link NetworkInfoOutput} the information of the network
   */
  @AElfUrl(url = "wa://api/net/networkInfo")
  public NetworkInfoOutput getNetworkInfo() {
    String networkChain = NetworkConnector.getIns()
        .sendGet(this.AElfClientUrl + APIPath.WA_GET_NETWORK_INFO, "UTF-8", this.version);
    MapEntry<String, ?> responseBodyMap = JsonUtil.parseObject(networkChain);
    if (responseBodyMap == null)
      throw new RuntimeException();
    return new NetworkInfoOutput()
        .setVersion(responseBodyMap.getString("Version"))
        .setConnections(responseBodyMap.getInteger("Connections", 0))
        .setProtocolVersion(responseBodyMap.getInteger("ProtocolVersion", 0));
  }
}
