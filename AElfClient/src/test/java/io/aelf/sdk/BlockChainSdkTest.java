package io.aelf.sdk;

import com.google.protobuf.ByteString;

import io.aelf.contract.GlobalContract;
import io.aelf.internal.global.TestParams;
import io.aelf.protobuf.generated.Client;
import io.aelf.protobuf.generated.Core;
import io.aelf.protobuf.generated.TransactionFee.TransactionFeeCharged;
import io.aelf.protobuf.generated.TransactionFee.TransactionFeeCharged.Builder;
import io.aelf.schemas.*;
import io.aelf.utils.ByteArrayHelper;
import io.aelf.utils.JsonUtil;
import io.aelf.utils.MapEntry;
import io.aelf.utils.Maps;
import io.aelf.utils.Sha256;
import io.aelf.utils.TransactionResultDtoExtension;

import java.math.BigInteger;
import java.util.*;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class BlockChainSdkTest {

    AElfClient client = null;
    String address;

    /**
     * init junit.
     */
    @Before
    public void init() {
        client = new AElfClient(TestParams.CLIENT_HTTP_URL);
        address = client.getAddressFromPrivateKey(TestParams.TEST_PRIVATE_KEY);
        Assert.assertTrue(address.length() > 0);
    }

    @Test
    public void getTransactionFeeTest() throws Exception {
        TransactionResultDto transactionResultDto = new TransactionResultDto();
        transactionResultDto.setLogs(new ArrayList<>());
        Base64.Encoder encoder = Base64.getEncoder();
        transactionResultDto.getLogs().add(
                createMockLogItem("TransactionFeeCharged", "ELF", 1000, encoder));
        transactionResultDto.getLogs().add(
                createMockLogItem("ResourceTokenCharged", "READ", 800, encoder));

        transactionResultDto.getLogs().add(
                createMockLogItem("ResourceTokenCharged", "WRITE", 600, encoder));

        transactionResultDto.getLogs().add(
                createMockLogItem("ResourceTokenOwned", "READ", 200, encoder));

        HashMap<String, Long> transactionFees = TransactionResultDtoExtension.getTransactionFees(transactionResultDto);
        Assert.assertEquals(3, transactionFees.keySet().size());
        Assert.assertEquals(1000, (long) transactionFees.get("ELF"));
        Assert.assertEquals(800, (long) transactionFees.get("READ"));
        Assert.assertEquals(600, (long) transactionFees.get("WRITE"));

        transactionResultDto = new TransactionResultDto();
        transactionResultDto.setLogs(new ArrayList<>());
        transactionFees = TransactionResultDtoExtension.getTransactionFees(transactionResultDto);
        Assert.assertEquals(0, transactionFees.size());

    }

    private LogEventDto createMockLogItem(String name, String symbol, int amount, @Nonnull Base64.Encoder encoder) {
        LogEventDto logEventDto = new LogEventDto();
        logEventDto.setName(name);
        Builder tFeeCharged = TransactionFeeCharged.newBuilder();
        tFeeCharged.setSymbol(symbol);
        tFeeCharged.setAmount(amount);
        String nonIndexed = encoder.encodeToString(tFeeCharged.build().toByteArray());
        logEventDto.setNonIndexed(nonIndexed);
        return logEventDto;
    }

    @Test
    public void getAddressFromPubKeyTest() {
        org.bitcoinj.core.ECKey aelfKey = org.bitcoinj.core.ECKey
                .fromPrivate(new BigInteger(TestParams.TEST_PRIVATE_KEY, 16)).decompress();
        String pubKey = Hex.toHexString(aelfKey.getPubKey());
        String pubKeyAddress = client.getAddressFromPubKey(pubKey);
        Assert.assertEquals(pubKeyAddress, address);
    }

    @Test
    public void getFormattedAddressTest() throws Exception {
        String addressVal = client.getFormattedAddress(TestParams.TEST_PRIVATE_KEY, address);
        Assert.assertEquals(("ELF_" + address + "_AELF"), addressVal);
    }

    @Test
    public void getNewKeyPairInfoTest() throws Exception {
        KeyPairInfo keyPairInfo = client.generateKeyPairInfo();
        Assert.assertEquals(client.getAddressFromPrivateKey(keyPairInfo.getPrivateKey()),
                keyPairInfo.getAddress());
    }

    @Test
    public void getPublicKeyAsHexTest() throws Exception {
        Assert.assertEquals(TestParams.TEST_PUBLIC_KEY, address);
    }

    @Test
    public void getBlockHeightTest() throws Exception {
        long blockHeight = client.getBlockHeight();
        Assert.assertTrue(blockHeight > 0);
    }

    @Test
    public void getBlockByHeightDefaultTest() throws Exception {
        long blockHeight = client.getBlockHeight();
        Assert.assertTrue(blockHeight > 0);
        BlockDto block = client.getBlockByHeight(blockHeight);
        Assert.assertTrue(block.getBlockHash().length() > 0);
    }

    @Test
    public void getBlockByHeightForFalseTest() throws Exception {
        long blockHeight = client.getBlockHeight();
        Assert.assertTrue(blockHeight > 0);
        BlockDto block = client.getBlockByHeight(blockHeight, false);
        Assert.assertTrue(block.getBlockHash().length() > 0);
    }

    @Test
    public void getBlockByHeightForTrueTest() throws Exception {
        long blockHeight = client.getBlockHeight();
        Assert.assertTrue(blockHeight > 0);
        BlockDto block = client.getBlockByHeight(blockHeight, true);
        Assert.assertTrue(block.getBlockHash().length() > 0);
    }

    @Test
    public void getBlockByHashDefaultTest() throws Exception {
        long blockHeight = client.getBlockHeight();
        Assert.assertTrue(blockHeight > 0);
        BlockDto blockDto = client.getBlockByHeight(blockHeight);
        BlockDto block = client.getBlockByHash(blockDto.getBlockHash());
        Assert.assertTrue(block.getBlockHash().length() > 0);

    }

    @Test
    public void getBlockByHashForFalseTest() throws Exception {
        long blockHeight = client.getBlockHeight();
        Assert.assertTrue(blockHeight > 0);
        BlockDto blockDto = client.getBlockByHeight(blockHeight, false);
        BlockDto block = client.getBlockByHash(blockDto.getBlockHash());
        Assert.assertTrue(block.getBlockHash().length() > 0);
    }

    @Test
    public void getBlockByHashForFalseTrue() throws Exception {
        long blockHeight = client.getBlockHeight();
        Assert.assertTrue(blockHeight > 0);
        BlockDto blockDto = client.getBlockByHeight(blockHeight, true);
        BlockDto block = client.getBlockByHash(blockDto.getBlockHash());
        Assert.assertTrue(block.getBlockHash().length() > 0);
    }

    @Test
    public void getTransactionPoolStatusTest() throws Exception {
        TransactionPoolStatusOutput statusOutput = client.getTransactionPoolStatus();
        Assert.assertNotNull(statusOutput);
    }

    @Test
    public void getChainStatusTest() throws Exception {
        ChainstatusDto chainstatusDto = client.getChainStatus();
        Assert.assertNotNull(chainstatusDto.getChainId());
    }

    @Test
    public void getContractFileDescriptorSetTest() throws Exception {
        long blockHeight = client.getBlockHeight();
        Assert.assertTrue(blockHeight > 0);
        BlockDto blockDto = client.getBlockByHeight(blockHeight, false);
        List<TransactionResultDto> transactionResultDtoList = client
                .getTransactionResults(blockDto.getBlockHash(), 0, 10);
        for (TransactionResultDto transactionResultDtoObj : transactionResultDtoList) {
            client
                    .getContractFileDescriptorSet(transactionResultDtoObj.getTransaction().getTo());
        }
    }

    @Test
    public void getTaskQueueStatusTest() throws Exception {
        List<TaskQueueInfoDto> listTaskQueue = client.getTaskQueueStatus();
        Assert.assertTrue(listTaskQueue.size() > 0);
    }

    @Test
    public void executeTransactionTest() throws Exception {
        String toAddress = client.getGenesisContractAddress();
        String methodName = GlobalContract.genesisContract.method_getContractAddressByName;
        byte[] bytes = Sha256.getBytesSha256(GlobalContract.tokenConverterContract.name);
        Client.Hash.Builder hash = Client.Hash.newBuilder();
        hash.setValue(ByteString.copyFrom(bytes));
        Client.Hash hashObj = hash.build();
        Core.Transaction.Builder transaction = client
                .generateTransaction(address, toAddress, methodName, hashObj.toByteArray());
        Core.Transaction transactionObj = transaction.build();
        String signature = client.signTransaction(TestParams.TEST_PRIVATE_KEY, transactionObj);
        transaction.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
        transactionObj = transaction.build();
        ExecuteTransactionDto executeTransactionDtoObj = new ExecuteTransactionDto();
        executeTransactionDtoObj.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
        client.executeTransaction(executeTransactionDtoObj);
    }

    @Test
    public void executeRawTransactionTest() throws Exception {
        String toAddress = client.getGenesisContractAddress();
        final String methodName = GlobalContract.genesisContract.method_getContractAddressByName;
        byte[] paramBytes = Sha256.getBytesSha256(GlobalContract.consensusContract.name);
        ChainstatusDto status = client.getChainStatus();
        final long height = status.getBestChainHeight();
        final String blockHash = status.getBestChainHash();
        MapEntry<String, String> mapParamsObj = Maps.newMapEntry();
        Base64.Encoder encoder = Base64.getEncoder();
        mapParamsObj.put("value", encoder.encodeToString(paramBytes));
        String param = JsonUtil.toJsonString(mapParamsObj);
        CreateRawTransactionInput createRawTransactionInputObj = createRowBuild(toAddress, methodName,
                param, height, blockHash);
        CreateRawTransactionOutput createRawTransactionOutputObj = client
                .createRawTransaction(createRawTransactionInputObj);
        byte[] rawTransactionBytes = ByteArrayHelper
                .hexToByteArray(createRawTransactionOutputObj.getRawTransaction());
        byte[] transactionId = Sha256.getBytesSha256(rawTransactionBytes);
        String signature = client.getSignatureWithPrivateKey(TestParams.TEST_PRIVATE_KEY, transactionId);
        ExecuteRawTransactionDto executeRawTransactionDtoObj = new ExecuteRawTransactionDto();
        executeRawTransactionDtoObj
                .setRawTransaction(createRawTransactionOutputObj.getRawTransaction());
        executeRawTransactionDtoObj.setSignature(signature);
        client.executeRawTransaction(executeRawTransactionDtoObj);
    }

    @Test
    public void createRawTransactionTest() throws Exception {
        String toAddress = client.getGenesisContractAddress();
        final String methodName = GlobalContract.genesisContract.method_getContractAddressByName;
        byte[] paramBytes = Sha256.getBytesSha256(GlobalContract.tokenContract.name);
        ChainstatusDto status = client.getChainStatus();
        final long height = status.getBestChainHeight();
        final String blockHash = status.getBestChainHash();
        MapEntry<String, String> mapParamsObj = Maps.newMapEntry();
        Base64.Encoder encoder = Base64.getEncoder();
        mapParamsObj.put("value", encoder.encodeToString(paramBytes));
        String param = JsonUtil.toJsonString(mapParamsObj);
        CreateRawTransactionInput createRawTransactionInputObj = createRowBuild(toAddress, methodName,
                param, height, blockHash);
        client.createRawTransaction(createRawTransactionInputObj);
    }

    @Test
    public void sendRawTransactionTest() throws Exception {
        final String toAddress = client.getGenesisContractAddress();
        final String methodName = GlobalContract.genesisContract.method_getContractAddressByName;
        byte[] paramBytes = Sha256.getBytesSha256(GlobalContract.tokenContract.name);
        ChainstatusDto status = client.getChainStatus();
        final long height = status.getBestChainHeight();
        final String blockHash = status.getBestChainHash();
        MapEntry<String, String> mapParamsObj = Maps.newMapEntry();
        Base64.Encoder encoder = Base64.getEncoder();
        mapParamsObj.put("value", encoder.encodeToString(paramBytes));
        String param = JsonUtil.toJsonString(mapParamsObj);
        CreateRawTransactionInput createRawTransactionInputObj = createRowBuild(toAddress, methodName,
                param, height, blockHash);
        CreateRawTransactionOutput createRawTransactionOutputObj = client
                .createRawTransaction(createRawTransactionInputObj);
        byte[] rawTransactionBytes = ByteArrayHelper
                .hexToByteArray(createRawTransactionOutputObj.getRawTransaction());
        byte[] transactionId = Sha256.getBytesSha256(rawTransactionBytes);
        String signature = client.getSignatureWithPrivateKey(TestParams.TEST_PRIVATE_KEY, transactionId);
        SendRawTransactionInput sendRawTransactionInputObj = new SendRawTransactionInput();
        sendRawTransactionInputObj.setTransaction(createRawTransactionOutputObj.getRawTransaction());
        sendRawTransactionInputObj.setSignature(signature);
        sendRawTransactionInputObj.setReturnTransaction(true);
        client.sendRawTransaction(sendRawTransactionInputObj);
    }

    @Test
    public void sendTransactionTest() throws Exception {
        String toAddress = client.getGenesisContractAddress();
        String methodName = GlobalContract.genesisContract.method_getContractAddressByName;
        byte[] bytes = Sha256.getBytesSha256(GlobalContract.voteContract.name);
        Client.Hash.Builder hash = Client.Hash.newBuilder();
        hash.setValue(ByteString.copyFrom(bytes));
        Client.Hash hashObj = hash.build();
        Core.Transaction transactionObj = buildTransaction(toAddress, methodName, hashObj.toByteArray());
        SendTransactionInput sendTransactionInputObj = new SendTransactionInput();
        sendTransactionInputObj.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
        client.sendTransaction(sendTransactionInputObj);
    }

    @Test
    public void sendTransactionsTest() throws Exception {
        String toAddress = client.getGenesisContractAddress();
        byte[] param1 = Sha256.getBytesSha256(GlobalContract.tokenContract.name);
        byte[] param2 = Sha256.getBytesSha256(GlobalContract.voteContract.name);
        String methodName = GlobalContract.genesisContract.method_getContractAddressByName;
        List<byte[]> parameters = new ArrayList<>();
        parameters.add(param1);
        parameters.add(param2);
        for (byte[] tmp : parameters) {
            Client.Hash.Builder hash = Client.Hash.newBuilder();
            hash.setValue(ByteString.copyFrom(tmp));
            Client.Hash hashObj = hash.build();
            Core.Transaction transactionObj = buildTransaction(toAddress, methodName, hashObj.toByteArray());
            SendTransactionsInput sendTransactionsInputs = new SendTransactionsInput();
            String rawTransactions = Hex.toHexString(transactionObj.toByteArray());
            sendTransactionsInputs.setRawTransactions(rawTransactions);
            List<String> listString = client
                    .sendTransactions(sendTransactionsInputs);
            Assert.assertTrue(listString.size() > 0);
        }
    }

    @Test
    public void getTransactionResultsTest() throws Exception {
        long blockHeight = client.getBlockHeight();
        Assert.assertTrue(blockHeight > 0);
        BlockDto blockDto = client.getBlockByHeight(blockHeight, false);
        client.getTransactionResults(blockDto.getBlockHash(), 0, 10);
    }

    @Test
    public void getTransactionResultTest() throws Exception {
        long blockHeight = client.getBlockHeight();
        Assert.assertTrue(blockHeight > 0);
        BlockDto blockDto = client.getBlockByHeight(blockHeight, false);
        List<TransactionResultDto> transactionResultDtoList = client
                .getTransactionResults(blockDto.getBlockHash(), 0, 10);
        for (TransactionResultDto transactionResultDtoObj : transactionResultDtoList) {
            Assert.assertNotNull(client
                    .getTransactionResult(transactionResultDtoObj.getTransactionId())
                    .getTransactionId());
        }
    }

    @Test
    public void getMerklePathByTransactionIdTest() throws Exception {
        long blockHeight = client.getBlockHeight();
        Assert.assertTrue(blockHeight > 0);
        BlockDto blockDto = client.getBlockByHeight(blockHeight, false);
        List<TransactionResultDto> transactionResultDtoList = client
                .getTransactionResults(blockDto.getBlockHash(), 0, 10);
        for (TransactionResultDto transactionResultDtoObj : transactionResultDtoList) {
            Assert.assertNotNull(
                    client.getMerklePathByTransactionId(transactionResultDtoObj.getTransactionId()));
        }
    }

    /**
     * This test runs under TestNet mainchain.
     */
    @Test
    public void getChainIdTest() throws Exception {
        int chainId = client.getChainId();
        Assert.assertEquals(9992731, chainId);
    }

    @Test
    public void isConnectedTest() {
        Assert.assertTrue(client.isConnected());
    }

    @Test
    public void protobufTest() throws Exception {
        String toAddress = client.getGenesisContractAddress();
        byte[] bytes = Sha256.getBytesSha256(GlobalContract.voteContract.name);
        String methodName = GlobalContract.genesisContract.method_getContractAddressByName;
        Client.Hash.Builder hash = Client.Hash.newBuilder();
        hash.setValue(ByteString.copyFrom(bytes));
        Client.Hash hashObj = hash.build();
        Core.Transaction transactionObj = buildTransaction(toAddress, methodName, hashObj.toByteArray());
        SendTransactionInput sendTransactionInputObj = new SendTransactionInput();
        sendTransactionInputObj.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
        Assert.assertNotNull(client.sendTransaction(sendTransactionInputObj).getTransactionId());
    }

//    @Test
//    @FIX_ME For an unknown reason, this test will always fail

//    public void calculateTransactionFeeResultTest() throws Exception {
//        String toAddress = client.getGenesisContractAddress();
//        final String methodName = "GetContractAddressByName";
//        byte[] paramBytes = Sha256.getBytesSha256("AElf.ContractNames.Token");
//        ChainstatusDto status = client.getChainStatus();
//        final long height = status.getBestChainHeight();
//        final String blockHash = status.getBestChainHash();
//        MapEntry<String,String> mapParamsObj = Maps.newMapEntry();
//        Base64.Encoder encoder=Base64.getEncoder();
//        mapParamsObj.put("value", encoder.encodeToString(paramBytes));
//        String param = JsonUtil.toJsonString(mapParamsObj);
//        CreateRawTransactionInput createRawTransactionInputObj = createRowBuild(toAddress, methodName,
//                param, height, blockHash);
//        CreateRawTransactionOutput out = client.createRawTransaction(createRawTransactionInputObj);
//        CalculateTransactionFeeInput input = new CalculateTransactionFeeInput();
//        input.setRawTransaction(out.getRawTransaction());
//        CalculateTransactionFeeOutput output = client.calculateTransactionFee(input);
//        System.out.println(JsonUtil.toJsonString(output));
//        Assert.assertTrue(String.valueOf(output.getTransactionFee().get("ELF") > 18000000), true);
//        Assert.assertTrue(String.valueOf(output.getTransactionFee().get("ELF") < 19000000), true);
//
//    }

    private Core.Transaction buildTransaction(String toAddress, String methodName, byte[] tmp)
            throws Exception {
        Core.Transaction.Builder transaction = client
                .generateTransaction(address, toAddress, methodName, tmp);
        Core.Transaction transactionObj = transaction.build();
        String signature = client.signTransaction(TestParams.TEST_PRIVATE_KEY, transactionObj);
        transaction.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
        transactionObj = transaction.build();
        return transactionObj;
    }

    @SuppressWarnings("SameParameterValue")
    private CreateRawTransactionInput createRowBuild(String toAddress, String methodName,
                                                     String param, long height, String blockHash) {
        CreateRawTransactionInput createRawTransactionInputObj = new CreateRawTransactionInput();
        createRawTransactionInputObj.setFrom(address);
        createRawTransactionInputObj.setTo(toAddress);
        createRawTransactionInputObj.setMethodName(methodName);
        createRawTransactionInputObj.setParams(param);
        createRawTransactionInputObj.setRefBlockNumber(height);
        createRawTransactionInputObj.setRefBlockHash(blockHash);
        return createRawTransactionInputObj;
    }
}
