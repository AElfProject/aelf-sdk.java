package io.aelf.test;

import io.aelf.schemas.*;
import io.aelf.sdk.AelfSdk;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author linhui linhui@tydic.com
 * @title: BlcokChainSdkTest
 * @description: TODO
 * @date 2019/12/1622:36
 */
public class NetSdkTest {
    private static final String httpUrl="http://127.0.0.1:8200";
    private static final String opreationAddress="127.0.0.1:7003";
    AelfSdk aelfSdk=null;
    @Before
    public void init(){
        aelfSdk=new AelfSdk(httpUrl);
    }
    @Test
    public void getNetworkInfoAsync() throws Exception {
        aelfSdk.getNetSdkObj().getNetworkInfoAsync();
    }

    @Test
    public void getPeerAsync() throws Exception{
        List<PeerDto> listPeerDto=aelfSdk.getNetSdkObj().getPeersAsync(true);
        Assert.assertTrue(listPeerDto.size()>0);
    }

    @Test
    public void addPeerAsyncTest() throws Exception {
        AddPeerInput addPeerInput=new AddPeerInput();
        addPeerInput.setAddress(opreationAddress);
        aelfSdk.getNetSdkObj().addPeerAsync(addPeerInput);
    }
    @Test
    public void removePeerAsyncTest() throws Exception {
        aelfSdk.getNetSdkObj().removePeerAsync(opreationAddress);
    }


}
