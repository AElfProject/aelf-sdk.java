package io.aelf.test;

import io.aelf.schemas.AddPeerInput;
import io.aelf.schemas.PeerDto;
import io.aelf.sdk.AElfClient;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class NetSdkTest {

  static final String HTTPURL = "http://127.0.0.1:8000";
  static final String OPREATIONADDRESS = "127.0.0.1:7003";
  AElfClient client = null;

  @Before
  public void init() {
    client = new AElfClient(HTTPURL);
  }

  @Test
  public void getNetworkInfo() throws Exception {
    Assert.assertTrue(client.getNetworkInfo()!=null);
  }

  @Test
  public void getPeer() throws Exception {
    List<PeerDto> listPeerDto = client.getPeers(true);
    Assert.assertTrue(listPeerDto.size() >= 0);
  }

  @Ignore
  @Test
  public void addPeerTest() throws Exception {
    AddPeerInput addPeerInput = new AddPeerInput();
    addPeerInput.setAddress(OPREATIONADDRESS);
    Assert.assertTrue(client.addPeer(addPeerInput));
  }

  @Ignore
  @Test
  public void removePeerTest() throws Exception {
    Assert.assertTrue(client.removePeer(OPREATIONADDRESS));
  }


}
