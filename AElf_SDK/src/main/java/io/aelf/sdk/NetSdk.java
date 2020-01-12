package io.aelf.sdk;

import io.aelf.schemas.AddPeerInput;
import io.aelf.schemas.NetworkInfoOutput;
import io.aelf.schemas.PeerDto;
import io.aelf.schemas.RequestMetric;
import io.aelf.schemas.Timestamp;
import io.aelf.utils.HttpClientUtil;
import io.aelf.utils.HttpClientUtilExt;
import io.aelf.utils.JsonUtil;
import io.aelf.utils.MapEntry;
import io.aelf.utils.Maps;
import io.aelf.utils.StringUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class NetSdk {

  private String aelfSdkUrl;
  private static final String WA_ADDPEER = "/api/net/peer";
  private static final String WA_REMOVEPEER = "/api/net/peer";
  private static final String WA_GETPEERS = "/api/net/peers";
  private static final String WA_GETNETWORKINFO = "/api/net/networkInfo";

  /**
   * Object construction through the url path.
   */
  public NetSdk(String url) {
    this.aelfSdkUrl = url;
  }

  private NetSdk() {
  }

  /**
   * Attempts to add a node to the connected network nodes wa:/api/net/peer.
   */
  public Boolean addPeer(AddPeerInput input) throws Exception {
    String url = this.aelfSdkUrl + WA_ADDPEER;
    MapEntry mapParmas = Maps.newMap();
    mapParmas.put("Address", input.getAddress());
    String responseBobyResult = HttpClientUtilExt
        .sendPostRequest(url, JsonUtil.toJsonString(mapParmas));
    if ("true".equals(responseBobyResult)) {
      return true;
    }
    return false;
  }

  /**
   * Attempts to remove a node from the connected network nodes wa:/api/net/peer.
   */
  public Boolean removePeer(String address) throws Exception {
    String url = this.aelfSdkUrl + WA_REMOVEPEER + "?address=" + address;
    String responseBobyResult = HttpClientUtilExt.sendDeleteRequest(url, "UTF-8");
    if ("true".equals(responseBobyResult)) {
      return true;
    }
    return false;
  }

  /**
   * Gets information about the peer nodes of the current node.Optional whether to include metrics.
   * wa:/api/net/peers?withMetrics=false
   */
  public List<PeerDto> getPeers(Boolean withMetrics) throws Exception {
    String url = this.aelfSdkUrl + WA_GETPEERS + "?withMetrics=" + withMetrics;
    String peersChain = HttpClientUtil.sendGetRequest(url, "UTF-8");
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
    String networkChain = HttpClientUtil
        .sendGetRequest(this.aelfSdkUrl + WA_GETNETWORKINFO, "UTF-8");
    MapEntry responseBobyMap = JsonUtil.parseObject(networkChain);
    NetworkInfoOutput networkInfoOutput = new NetworkInfoOutput();
    networkInfoOutput.setVersion(responseBobyMap.getString("Version"));
    networkInfoOutput.setConnections(responseBobyMap.getInteger("Connections", 0));
    networkInfoOutput.setProtocolVersion(responseBobyMap.getInteger("ProtocolVersion", 0));
    return networkInfoOutput;
  }


}
