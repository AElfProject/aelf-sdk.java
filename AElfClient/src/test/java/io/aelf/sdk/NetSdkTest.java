package io.aelf.sdk;

import io.aelf.async.global.TestParams;
import io.aelf.schemas.AddPeerInput;
import io.aelf.schemas.NetworkInfoOutput;
import io.aelf.schemas.PeerDto;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class NetSdkTest {

    static final String OPERATION_ADDRESS = "127.0.0.1:7003";

//    static final String VERSION = "1.2.3.0";

    AElfClient client = null;

    @Before
    public void init() {
        client = new AElfClient(TestParams.CLIENT_HTTP_URL);
    }

    @Test
    public void getNetworkInfo() throws Exception {
        NetworkInfoOutput networkInfo = client.getNetworkInfo();
        Assert.assertNotNull(networkInfo);
//        Assert.assertEquals(networkInfo.getVersion(),VERSION);
    }

    @Test
    public void getPeer() throws Exception {
        List<PeerDto> listPeerDto = client.getPeers(true);
        Assert.assertNotNull(listPeerDto);
        Assert.assertNotEquals(listPeerDto.size(),0);
    }

    // Those tests were ignored because they need the auth to operate.
    // If you control a node, provide the username and password in init().
    @Ignore
    @Test
    public void addPeerTest() throws Exception {
        AddPeerInput addPeerInput = new AddPeerInput();
        addPeerInput.setAddress(OPERATION_ADDRESS);
        Assert.assertTrue(client.addPeer(addPeerInput));
    }

    @Ignore
    @Test
    public void removePeerTest() throws Exception {
        Assert.assertTrue(client.removePeer(OPERATION_ADDRESS));
    }


}
