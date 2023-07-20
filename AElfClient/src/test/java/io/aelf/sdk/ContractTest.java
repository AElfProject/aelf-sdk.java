package io.aelf.sdk;

import io.aelf.contract.GlobalContract;
import io.aelf.internal.global.TestParams;
import io.aelf.internal.sdkv2.AElfClientAsync;
import io.aelf.internal.sdkv2.AElfClientV2;
import io.aelf.utils.AElfException;
import org.apache.http.util.TextUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class ContractTest {
    private final AElfClientAsync client = new AElfClientV2(TestParams.CLIENT_HTTP_URL);

    @Test
    public void ViewContractTest() throws AElfException {
        String result = client.callContractMethod(
                GlobalContract.tokenContract.name,
                GlobalContract.tokenContract.method_getPrimaryTokenSymbol,
                TestParams.TEST_PRIVATE_KEY,
                true
        );
        Assert.assertFalse(TextUtils.isEmpty(result));
    }

    @Ignore
    @Test
    public void SendContractTest() throws AElfException {
        // Calling send Method will cost some token, this test is not finished yet.
        Assert.fail();
    }
}
