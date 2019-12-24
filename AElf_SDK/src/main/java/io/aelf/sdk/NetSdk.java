package io.aelf.sdk;
import io.aelf.schemas.*;
import io.aelf.utils.*;

import java.util.*;

/**
 * @author linhui linhui@tydic.com
 * @title: NetSdk
 * @description: TODO
 * @date 2019/12/1512:02
 */
public class NetSdk {
    private String aelfSdkUrl;
    private static final String WEBAPI_ADDPEERASYNC="/api/net/peer";
    private static final String WEBAPI_REMOVEPEERASYNC="/api/net/peer";
    private static final String WEBAPI_GETPEERSASYNC="/api/net/peers";
    private static final String WEBAPI_GETNETWORKINFOASYNC="/api/net/networkInfo";

    /**
     * Object construction through the url path
     * @param url
     */
    public NetSdk(String url){
        this.aelfSdkUrl=url;
    }
    private NetSdk(){}

    /**
     * Attempts to add a node to the connected network nodes
     * webapi:/api/net/peer
     * @param input
     * @return
     */
    public Boolean addPeerAsync(AddPeerInput input) throws Exception{
        String url=this.aelfSdkUrl+WEBAPI_ADDPEERASYNC;
        MapEntry mapParmas=Maps.newMap();
        mapParmas.put("Address",input.getAddress());
        String responseBobyResult=HttpClientUtilExt.sendPostRequest(url,JSONUtil.toJSONString(mapParmas));
        if("true".equals(responseBobyResult)){
            return true;
        }
        return false;
    }

    /**
     * Attempts to remove a node from the connected network nodes
     * webapi:/api/net/peer
     * @param address
     * @return
     */
    public Boolean  removePeerAsync(String address) throws Exception{
        String url=this.aelfSdkUrl+WEBAPI_REMOVEPEERASYNC+"?address="+address;
        String responseBobyResult=HttpClientUtilExt.sendDeleteRequest(url,"UTF-8");
        if("true".equals(responseBobyResult)){
            return true;
        }
        return false;
    }

    /**
     * Gets information about the peer nodes of the current node.Optional whether to include metrics.
     * webapi:/api/net/peers?withMetrics=false
     * @param withMetrics
     * @return
     */
    public List<PeerDto> getPeersAsync(Boolean withMetrics) throws Exception{
        String url=this.aelfSdkUrl+WEBAPI_GETPEERSASYNC+"?withMetrics="+withMetrics;
        String peersChain=HttpClientUtil.sendGetRequest(url,"UTF-8");
        List<PeerDto> listPeerDto=new ArrayList<PeerDto>();
        List<LinkedHashMap> responseBobyList= JSONUtil.parseObject(peersChain,List.class);
        for(LinkedHashMap responseBobyObj:responseBobyList){
            MapEntry responseBobyMapObj=Maps.cloneMapEntry(responseBobyObj);
            PeerDto peerDtoObj=new PeerDto();
            peerDtoObj.setIpAddress(responseBobyMapObj.getString("IpAddress",""));
            peerDtoObj.setProtocolVersion(responseBobyMapObj.getInteger("ProtocolVersion",0));
            peerDtoObj.setConnectionTime(responseBobyMapObj.getLong("ConnectionTime",0));
            peerDtoObj.setConnectionStatus(responseBobyMapObj.getString("ConnectionStatus",""));
            peerDtoObj.setInbound(responseBobyMapObj.getBoolean("Inbound",false));
            peerDtoObj.setBufferedAnnouncementsCount(responseBobyMapObj.getInteger("BufferedTransactionsCount",0));
            peerDtoObj.setBufferedBlocksCount(responseBobyMapObj.getInteger("BufferedBlocksCount",0));
            peerDtoObj.setBufferedTransactionsCount(responseBobyMapObj.getInteger("BufferedAnnouncementsCount",0));
            peerDtoObj.setRequestMetrics(new ArrayList());
            List<LinkedHashMap> requestMetricsList=responseBobyMapObj.getArrayList("RequestMetrics",new ArrayList<LinkedHashMap>());
            for(LinkedHashMap requestMetricsObj:requestMetricsList){
                MapEntry requestMetricsMapObj=Maps.cloneMapEntry(requestMetricsObj);
                RequestMetric requestMetricObj=new RequestMetric();
                requestMetricObj.setMethodName(requestMetricsMapObj.getString("MethodName",""));
                requestMetricObj.setRoundTripTime(requestMetricsMapObj.getLong("RoundTripTime",0));
                requestMetricObj.setInfo(requestMetricsMapObj.getString("Info",""));
                requestMetricObj.setRequestTime(new Timestamp());

                LinkedHashMap  requestTimeObj=requestMetricsMapObj.getLinkedHashMap("RequestTime",new LinkedHashMap());
                if(requestTimeObj.containsKey("Nanos")){
                    int value= Integer.parseInt(StringUtil.toString(requestTimeObj.getOrDefault("Nanos","0")));
                    requestMetricObj.getRequestTime().setNanos(value);
                }
                if(requestTimeObj.containsKey("Seconds")){
                    long value= Long.parseLong(StringUtil.toString(requestTimeObj.getOrDefault("Nanos","0")));
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
     * webapi:/api/net/networkInfo
     * @return
     */
    public NetworkInfoOutput getNetworkInfoAsync() throws Exception{
        String networkChain=HttpClientUtil.sendGetRequest(this.aelfSdkUrl+WEBAPI_GETNETWORKINFOASYNC,"UTF-8");
        MapEntry responseBobyMap= JSONUtil.parseObject(networkChain);
        NetworkInfoOutput networkInfoOutput=new NetworkInfoOutput();
        networkInfoOutput.setVersion(responseBobyMap.getString("Version"));
        networkInfoOutput.setConnections(responseBobyMap.getInteger("Connections",0));
        networkInfoOutput.setProtocolVersion(responseBobyMap.getInteger("ProtocolVersion",0));
        return networkInfoOutput;
    }





}
