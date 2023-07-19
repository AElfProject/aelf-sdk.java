package io.aelf.sdk;

import com.google.gson.JsonParser;
import io.aelf.network.RetrofitFactory;
import io.aelf.schemas.AddPeerInput;
import io.aelf.schemas.NetworkInfoOutput;
import io.aelf.schemas.PeerDto;
import io.aelf.schemas.RequestMetric;
import io.aelf.schemas.Timestamp;
import io.aelf.utils.AElfUrl;
import io.aelf.utils.JsonUtil;
import io.aelf.utils.MapEntry;
import io.aelf.utils.Maps;
import io.aelf.utils.StringUtil;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings({"unchecked", "DataFlowIssue", "deprecation"})
public class NetSdk {
    private final String combineAuth;

    /**
     * Init the NetSdk.
     *
     * @param userName the username can be null
     * @param password the password can be null
     */
    public NetSdk(@Nullable String userName, @Nullable String password) {
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
        MapEntry<String, String> mapParams = Maps.newMap();
        mapParams.put("Address", input.getAddress());
        String responseBodyResult = RetrofitFactory.getAPIService()
                .addPeer(JsonParser.parseString(JsonUtil.toJsonString(input)), combineAuth)
                .execute()
                .body()
                .toString();
        return "true".equals(responseBodyResult);
    }

    /**
     * Attempts to remove a node from the connected network nodes.
     *
     * @param address the url of the node
     * @return true if success
     */
    @AElfUrl(url = "wa://api/net/peer")
    public Boolean removePeer(String address) throws IOException {
        String responseBodyResult = RetrofitFactory.getAPIService()
                .removePeer(address, combineAuth)
                .execute()
                .body()
                .toString();
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
    public List<PeerDto> getPeers(Boolean withMetrics) throws IOException {
        String peersChain = RetrofitFactory.getAPIService()
                .getPeers(withMetrics)
                .execute()
                .body()
                .toString();
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
    public NetworkInfoOutput getNetworkInfo() throws IOException {
        String networkChain = RetrofitFactory.getAPIService()
                .getNetworkInfo()
                .execute()
                .body()
                .toString();
        MapEntry<String, ?> responseBodyMap = JsonUtil.parseObject(networkChain);
        if (responseBodyMap == null)
            throw new RuntimeException();
        return new NetworkInfoOutput()
                .setVersion(responseBodyMap.getString("Version"))
                .setConnections(responseBodyMap.getInteger("Connections", 0))
                .setProtocolVersion(responseBodyMap.getInteger("ProtocolVersion", 0));
    }
}
