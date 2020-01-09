package io.aelf.test;
import com.google.protobuf.ByteString;
import io.aelf.proto.Core;
import io.aelf.schemas.*;
import io.aelf.sdk.AelfSdk;
import io.aelf.utils.*;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linhui linhui@tydic.com
 * @title: BlcokChainSdkTest
 * @description: TODO
 * @date 2019/12/1622:36
 */
public class BlockChainSdkTest {
    private final String httpUrl="http://127.0.0.1:8200";
    AelfSdk aelfSdk=null;
    String privateKey="09da44778f8db2e602fb484334f37df19e221c84c4582ce5b7770ccfbc3ddbef";
    String address="";
    @Before
    public void init() throws Exception {

        aelfSdk=new AelfSdk(httpUrl);
        //"2bWwpsN9WSc4iKJPHYL4EZX3nfxVY7XLadecnNMar1GdSb4hJz"
        address=aelfSdk.getAddressFromPrivateKey(privateKey);

    }

    @Test
    public void testGetPublicKeyAsHex() throws Exception {
        Assert.assertTrue("2bWwpsN9WSc4iKJPHYL4EZX3nfxVY7XLadecnNMar1GdSb4hJz".equals(address));
    }



    @Test
    public void getBlockHeightAsyncTest() throws Exception{
        long blockHeight=aelfSdk.getBlockChainSdkObj().getBlockHeightAsync();
        Assert.assertTrue(blockHeight>0);
    }

    @Test
    public void getBlockByHeightAsyncTest() throws Exception {
        long blockHeight=aelfSdk.getBlockChainSdkObj().getBlockHeightAsync();
        aelfSdk.getBlockChainSdkObj().getBlockByHeightAsync(blockHeight,false);
    }

    @Test
    public void getBlockByHashAsyncTest() throws Exception {
        long blockHeight=aelfSdk.getBlockChainSdkObj().getBlockHeightAsync();
        BlockDto blockDto=aelfSdk.getBlockChainSdkObj().getBlockByHeightAsync(blockHeight,false);
        aelfSdk.getBlockChainSdkObj().getBlockByHashAsync(blockDto.getBlockHash());

    }

    @Test
    public void getTransactionPoolStatusAsyncTest() throws Exception {
        aelfSdk.getBlockChainSdkObj().getTransactionPoolStatusAsync();
    }
    /**暂时废弃
    @Test
    public void getBlockStateAsyncTest() throws Exception{
        long blockHeight=aelfSdk.getBlockChainSdkObj().getBlockHeightAsync();
        BlockDto blockDto=aelfSdk.getBlockChainSdkObj().getBlockByHeightAsync(blockHeight,false);
        aelfSdk.getBlockChainSdkObj().getBlockState(blockDto.getBlockHash());
    }
     */
    @Test
    public void getChainStatusAsyncTest() throws Exception{
        aelfSdk.getBlockChainSdkObj().getChainStatusAsync();
    }

    @Test
    public void getContractFilCeDescriptorSetAsyncTest() throws Exception{
        long blockHeight=aelfSdk.getBlockChainSdkObj().getBlockHeightAsync();
        BlockDto blockDto=aelfSdk.getBlockChainSdkObj().getBlockByHeightAsync(blockHeight,false);
        List<TransactionResultDto> transactionResultDtoList=aelfSdk.getBlockChainSdkObj().getTransactionResultsAsync(blockDto.getBlockHash(),0,10);
        for(TransactionResultDto transactionResultDtoObj:transactionResultDtoList){
            aelfSdk.getBlockChainSdkObj().getContractFilCeDescriptorSetAsync(transactionResultDtoObj.getTransaction().getTo());
        }

    }

    @Test
    public void getTaskQueueStatusAsyncTest() throws Exception {
        List<TaskQueueInfoDto> listTaskQueue=aelfSdk.getBlockChainSdkObj().getTaskQueueStatusAsync();
        Assert.assertTrue(listTaskQueue.size()>0);
    }

    @Test
    public void executeTransactionAsyncTest() throws Exception{
        String toAddress = aelfSdk.getGenesisContractAddressAsync();
        String methodName = "GetContractAddressByName";
        byte[] bytes = Sha256.getBytesSHA256("AElf.ContractNames.TokenConverter");
        Core.Transaction.Builder transaction=aelfSdk.generateTransaction(address,toAddress,methodName,bytes);
        Core.Transaction transactionObj=transaction.build();
        String signature=aelfSdk.signTransaction(privateKey,transactionObj);
        transaction.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
        transactionObj=transaction.build();
        ExecuteTransactionDto executeTransactionDtoObj=new ExecuteTransactionDto();
        executeTransactionDtoObj.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
        aelfSdk.getBlockChainSdkObj().executeTransactionAsync(executeTransactionDtoObj);
    }
    @Test
    public void executeRawTransactionAsyncTest() throws Exception{
        String toAddress = aelfSdk.getGenesisContractAddressAsync();
        String methodName = "GetContractAddressByName";
        byte[] paramBytes = Sha256.getBytesSHA256("AElf.ContractNames.Consensus");
        ChainstatusDto status = aelfSdk.getBlockChainSdkObj().getChainStatusAsync();
        long height = status.getBestChainHeight();
        String blockHash = status.getBestChainHash();
        MapEntry mapParamsObj=Maps.newMapEntry();
        Base64 base64 = new Base64();
        mapParamsObj.put("value",base64.encodeToString(paramBytes));
        String param=JSONUtil.toJSONString(mapParamsObj);
        CreateRawTransactionInput createRawTransactionInputObj=new CreateRawTransactionInput();
        createRawTransactionInputObj.setFrom(address);
        createRawTransactionInputObj.setTo(toAddress);
        createRawTransactionInputObj.setMethodName(methodName);
        createRawTransactionInputObj.setParams(param);
        createRawTransactionInputObj.setRefBlockNumber(height);
        createRawTransactionInputObj.setRefBlockHash(blockHash);
        CreateRawTransactionOutput createRawTransactionOutputObj=aelfSdk.getBlockChainSdkObj().createRawTransactionAsync(createRawTransactionInputObj);

        byte[] rawTransactionBytes=ByteArrayHelper.hexToByteArray(createRawTransactionOutputObj.getRawTransaction());
        byte[] transactionId=Sha256.getBytesSHA256(rawTransactionBytes);
        String signature=aelfSdk.GetSignatureWithPrivateKey(privateKey,transactionId);

        ExecuteRawTransactionDto executeRawTransactionDtoObj=new ExecuteRawTransactionDto();
        executeRawTransactionDtoObj.setRawTransaction(createRawTransactionOutputObj.getRawTransaction());
        executeRawTransactionDtoObj.setSignature(signature);

        aelfSdk.getBlockChainSdkObj().executeRawTransactionAsync(executeRawTransactionDtoObj);
    }
    @Test
    public void createRawTransactionAsyncTest() throws Exception{
        String toAddress = aelfSdk.getGenesisContractAddressAsync();
        String methodName = "GetContractAddressByName";
        byte[] paramBytes = Sha256.getBytesSHA256("AElf.ContractNames.Token");
        ChainstatusDto status = aelfSdk.getBlockChainSdkObj().getChainStatusAsync();
        long height = status.getBestChainHeight();
        String blockHash = status.getBestChainHash();
        MapEntry mapParamsObj=Maps.newMapEntry();
        Base64 base64 = new Base64();
        mapParamsObj.put("value",base64.encodeToString(paramBytes));
        String param=JSONUtil.toJSONString(mapParamsObj);

        CreateRawTransactionInput createRawTransactionInputObj=new CreateRawTransactionInput();
        createRawTransactionInputObj.setFrom(address);
        createRawTransactionInputObj.setTo(toAddress);
        createRawTransactionInputObj.setMethodName(methodName);
        createRawTransactionInputObj.setParams(param);
        createRawTransactionInputObj.setRefBlockNumber(height);
        createRawTransactionInputObj.setRefBlockHash(blockHash);
         aelfSdk.getBlockChainSdkObj().createRawTransactionAsync(createRawTransactionInputObj);
    }

    @Test
    public void sendRawTransactionAsyncTest() throws Exception{
        String toAddress = aelfSdk.getGenesisContractAddressAsync();
        String methodName = "GetContractAddressByName";
        String param="";
        byte[] paramBytes = Sha256.getBytesSHA256("AElf.ContractNames.Token");
        ChainstatusDto status = aelfSdk.getBlockChainSdkObj().getChainStatusAsync();
        long height = status.getBestChainHeight();
        String blockHash = status.getBestChainHash();
        MapEntry mapParamsObj=Maps.newMapEntry();
        Base64 base64 = new Base64();
        mapParamsObj.put("value",base64.encodeToString(paramBytes));
        param=JSONUtil.toJSONString(mapParamsObj);
        CreateRawTransactionInput createRawTransactionInputObj=new CreateRawTransactionInput();
        createRawTransactionInputObj.setFrom(address);
        createRawTransactionInputObj.setTo(toAddress);
        createRawTransactionInputObj.setMethodName(methodName);
        createRawTransactionInputObj.setParams(param);
        createRawTransactionInputObj.setRefBlockNumber(height);
        createRawTransactionInputObj.setRefBlockHash(blockHash);
        CreateRawTransactionOutput createRawTransactionOutputObj=aelfSdk.getBlockChainSdkObj().createRawTransactionAsync(createRawTransactionInputObj);

        byte[] rawTransactionBytes=ByteArrayHelper.hexToByteArray(createRawTransactionOutputObj.getRawTransaction());
        byte[] transactionId=Sha256.getBytesSHA256(rawTransactionBytes);
        String signature=aelfSdk.GetSignatureWithPrivateKey(privateKey,transactionId);

        SendRawTransactionInput sendRawTransactionInputObj=new SendRawTransactionInput();
        sendRawTransactionInputObj.setTransaction(createRawTransactionOutputObj.getRawTransaction());
        sendRawTransactionInputObj.setSignature(signature);
        sendRawTransactionInputObj.setReturnTransaction(true);
        aelfSdk.getBlockChainSdkObj().sendRawTransactionAsync(sendRawTransactionInputObj);

    }

    @Test
    public void sendTransactionAsyncTest() throws Exception{

        String toAddress = aelfSdk.getGenesisContractAddressAsync();
        String methodName = "GetContractAddressByName";
        byte[] bytes = Sha256.getBytesSHA256("AElf.ContractNames.Vote");
        Core.Transaction.Builder transaction=aelfSdk.generateTransaction(address,toAddress,methodName,bytes);
        Core.Transaction transactionObj=transaction.build();
        String signature=aelfSdk.signTransaction(privateKey,transactionObj);
        transaction.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
        transactionObj=transaction.build();
        SendTransactionInput sendTransactionInputObj=new SendTransactionInput();
        sendTransactionInputObj.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
        aelfSdk.getBlockChainSdkObj().sendTransactionAsync(sendTransactionInputObj);
    }

    @Test
    public void sendTransactionsAsyncTest() throws Exception{
        String toAddress = aelfSdk.getGenesisContractAddressAsync();
        byte[] param1 = Sha256.getBytesSHA256("AElf.ContractNames.Token");
        byte[] param2 = Sha256.getBytesSHA256("AElf.ContractNames.Vote");
        String methodName = "GetContractAddressByName";
        List<byte[]> parameters = new ArrayList<byte[]>();
        parameters.add(param1);
        parameters.add(param2);
        for(byte[] tmp:parameters){
            Core.Transaction.Builder transaction=aelfSdk.generateTransaction(address,toAddress,methodName,tmp);
            Core.Transaction transactionObj=transaction.build();
            String signature=aelfSdk.signTransaction(privateKey,transactionObj);
            transaction.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
            transactionObj=transaction.build();
            SendTransactionsInput sendTransactionsInputs=new SendTransactionsInput();
            String rawTransactions=Hex.toHexString(transactionObj.toByteArray());
            sendTransactionsInputs.setRawTransactions(rawTransactions);
            List<String> listString=aelfSdk.getBlockChainSdkObj().sendTransactionsAsync(sendTransactionsInputs);
            Assert.assertTrue(listString.size()>0);

        }

    }


    @Test
    public void getTransactionResultsAsyncTest() throws Exception{
        long blockHeight=aelfSdk.getBlockChainSdkObj().getBlockHeightAsync();
        BlockDto blockDto=aelfSdk.getBlockChainSdkObj().getBlockByHeightAsync(blockHeight,false);
        aelfSdk.getBlockChainSdkObj().getTransactionResultsAsync(blockDto.getBlockHash(),0,10);
    }
    @Test
    public void getTransactionResultAsyncTest() throws Exception{
        long blockHeight=aelfSdk.getBlockChainSdkObj().getBlockHeightAsync();
        BlockDto blockDto=aelfSdk.getBlockChainSdkObj().getBlockByHeightAsync(blockHeight,false);
        List<TransactionResultDto> transactionResultDtoList=aelfSdk.getBlockChainSdkObj().getTransactionResultsAsync(blockDto.getBlockHash(),0,10);
        for(TransactionResultDto transactionResultDtoObj:transactionResultDtoList){
            aelfSdk.getBlockChainSdkObj().getTransactionResultAsync(transactionResultDtoObj.getTransactionId());
        }
    }
    @Test
    public void getMerklePathByTransactionIdAsyncTest() throws Exception{
        long blockHeight=aelfSdk.getBlockChainSdkObj().getBlockHeightAsync();
        BlockDto blockDto=aelfSdk.getBlockChainSdkObj().getBlockByHeightAsync(blockHeight,false);
        List<TransactionResultDto> transactionResultDtoList=aelfSdk.getBlockChainSdkObj().getTransactionResultsAsync(blockDto.getBlockHash(),0,10);
        for(TransactionResultDto transactionResultDtoObj:transactionResultDtoList){
            aelfSdk.getBlockChainSdkObj().getMerklePathByTransactionIdAsync(transactionResultDtoObj.getTransactionId());
        }
    }
    @Test
    public void getChainIdAsyncTest() throws Exception {
        int chainId=aelfSdk.getBlockChainSdkObj().getChainIdAsync();
        Assert.assertTrue(chainId==9992731);
    }
    @Test
    public void isConnectedTest(){
        Assert.assertTrue(aelfSdk.isConnected());
    }

    @Test
    public void potobuffTest() throws Exception{

        String toAddress = aelfSdk.getGenesisContractAddressAsync();
        byte[] bytes = Sha256.getBytesSHA256("AElf.ContractNames.Vote");
        String methodName = "GetContractAddressByName";
        Core.Transaction.Builder transaction=aelfSdk.generateTransaction(address,toAddress,methodName,bytes);
        Core.Transaction transactionObj=transaction.build();
        String signature=aelfSdk.signTransaction(privateKey,transactionObj);
        transaction.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
        transactionObj=transaction.build();

        SendTransactionInput sendTransactionInputObj=new SendTransactionInput();
        sendTransactionInputObj.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
        aelfSdk.getBlockChainSdkObj().sendTransactionAsync(sendTransactionInputObj);

    }



}
