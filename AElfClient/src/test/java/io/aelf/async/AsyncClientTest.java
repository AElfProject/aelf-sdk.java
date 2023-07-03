package io.aelf.async;

import io.aelf.schemas.BlockDto;
import io.aelf.sdk.BlockChainSdkTest;
import io.aelf.sdkv2.AsyncClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Since the AsyncClient only provides async-wrapped API,
 * and actually it doesn't contain additional code, This test
 * file will only test some of its APIS.
 * 
 * @see BlockChainSdkTest
 */
@SuppressWarnings("DataFlowIssue")
public class AsyncClientTest {
    private AsyncClient client;

    private static final String HTTP_URL = "http://192.168.66.61:8000";

    String privateKey = "cd86ab6347d8e52bbbe8532141fc59ce596268143a308d1d40fedf385528b458";
    String address = "";

    @Before
    public void init() {
        this.client = new AsyncClient(HTTP_URL);
        this.address = client.getAddressFromPrivateKey(privateKey);
    }

    private void onFail(AsyncResult<Void> e) {
        System.out.println("Test Failed!" + e.toString());
        throw new RuntimeException();
    }

    @Test
    public void getBlockHeightAsyncTest() {
        AsyncTestSingleLooper<Long> looper = new AsyncTestSingleLooper<>(height -> height.result > 0);
        client.getBlockHeightAsync(looper::setResult, this::onFail);
        looper.loop();
    }

    @Test
    public void getBlockByHashAsyncTest() throws Exception {
        long blockHeight = client.getBlockHeight();
        Assert.assertTrue(blockHeight > 0);
        BlockDto blockDto = client.getBlockByHeight(blockHeight);
        AsyncTestSingleLooper<BlockDto> looper = new AsyncTestSingleLooper<>(block -> block.result != null);
        client.getBlockByHashAsync(blockDto.getBlockHash(), looper::setResult, this::onFail);
        looper.loop();
    }

    @Test
    public void manyRequestsPressureTest() {
        AsyncTestLooper<Long> looper = new AsyncTestLooper<>(res -> res.result > 0,
                res -> res != null && !res.isOk(),60*1000);
        int size = 100;
        looper.setDeterminedSize(size);
        for (int i = 0; i < size; i++) {
            int finalI = i;
            client.getBlockHeightAsync(res -> looper.putResultAtPosition(finalI, res), this::onFail);
        }
        looper.loop();
    }

}
