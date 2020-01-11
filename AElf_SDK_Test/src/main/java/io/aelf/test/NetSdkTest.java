package io.aelf.test;

import io.aelf.schemas.AddPeerInput;
import io.aelf.schemas.PeerDto;
import io.aelf.sdk.AelfSdk;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NetSdkTest {

  static final String HTTPURL = "http://127.0.0.1:8200";
  static final String OPREATIONADDRESS = "127.0.0.1:7003";
  AelfSdk aelfSdk = null;

  @Before
  public void init() {
    aelfSdk = new AelfSdk(HTTPURL);
  }

  @Test
  public void getNetworkInfo() throws Exception {
    aelfSdk.getNetSdkObj().getNetworkInfo();
  }

  @Test
  public void getPeer() throws Exception {
    List<PeerDto> listPeerDto = aelfSdk.getNetSdkObj().getPeers(true);
    Assert.assertTrue(listPeerDto.size() > 0);
  }

  @Test
  public void addPeerTest() throws Exception {
    AddPeerInput addPeerInput = new AddPeerInput();
    addPeerInput.setAddress(OPREATIONADDRESS);
    aelfSdk.getNetSdkObj().addPeer(addPeerInput);
  }

  @Test
  public void removePeerTest() throws Exception {
    aelfSdk.getNetSdkObj().removePeer(OPREATIONADDRESS);
  }


}
