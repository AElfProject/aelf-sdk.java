package io.aelf.test;
import io.aelf.schemas.*;
import io.aelf.sdk.AelfSdk;
import io.aelf.utils.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.util.List;

/**
 * @author linhui linhui@tydic.com
 * @title: BlcokChainSdkTest
 * @description: TODO
 * @date 2019/12/1622:36
 */
public class BlcokChainSdkTest {
    private static final String httpUrl="http://127.0.0.1:8200";
    AelfSdk aelfSdk=null;
    String privateKey="09da44778f8db2e602fb484334f37df19e221c84c4582ce5b7770ccfbc3ddbef";
    String address="";
    @Before
    public void init() throws Exception {

        aelfSdk=new AelfSdk(httpUrl);
        address="2bWwpsN9WSc4iKJPHYL4EZX3nfxVY7XLadecnNMar1GdSb4hJz";
        //address=aelfSdk.getAddressFromPrivateKey(privateKey);
    }


    @Test
    public void getBlockHeightAsyncTest() throws Exception{
        long blockHeight=aelfSdk.getBlcokChainSdkObj().getBlockHeightAsync();
        Assert.assertTrue(blockHeight>0);
    }

    @Test
    public void getBlockByHeightAsyncTest() throws Exception {
        long blockHeight=aelfSdk.getBlcokChainSdkObj().getBlockHeightAsync();
        aelfSdk.getBlcokChainSdkObj().getBlockByHeightAsync(blockHeight,false);
    }

    @Test
    public void getBlockByHashAsyncTest() throws Exception {
        long blockHeight=aelfSdk.getBlcokChainSdkObj().getBlockHeightAsync();
        BlockDto blockDto=aelfSdk.getBlcokChainSdkObj().getBlockByHeightAsync(blockHeight,false);
        aelfSdk.getBlcokChainSdkObj().getBlockByHashAsync(blockDto.getBlockHash());

    }

    @Test
    public void getTransactionPoolStatusAsyncTest() throws Exception {
        aelfSdk.getBlcokChainSdkObj().getTransactionPoolStatusAsync();
    }
    @Test
    public void getBlockStateAsyncTest() throws Exception{
        long blockHeight=aelfSdk.getBlcokChainSdkObj().getBlockHeightAsync();
        BlockDto blockDto=aelfSdk.getBlcokChainSdkObj().getBlockByHeightAsync(blockHeight,false);
        aelfSdk.getBlcokChainSdkObj().getBlockState(blockDto.getBlockHash());
    }
    @Test
    public void getChainStatusAsyncTest() throws Exception{
        aelfSdk.getBlcokChainSdkObj().getChainStatusAsync();
    }

    @Test
    public void getContractFilCeDescriptorSetAsyncTest() throws Exception{
        long blockHeight=aelfSdk.getBlcokChainSdkObj().getBlockHeightAsync();
        BlockDto blockDto=aelfSdk.getBlcokChainSdkObj().getBlockByHeightAsync(blockHeight,false);
        List<TransactionResultDto> transactionResultDtoList=aelfSdk.getBlcokChainSdkObj().getTransactionResultsAsync(blockDto.getBlockHash(),0,10);
        for(TransactionResultDto transactionResultDtoObj:transactionResultDtoList){
            aelfSdk.getBlcokChainSdkObj().getContractFilCeDescriptorSetAsync(transactionResultDtoObj.getTransaction().getTo());
        }

    }

    @Test
    public void getTaskQueueStatusAsyncTest() throws Exception {
        List<TaskQueueInfoDto> listTaskQueue=aelfSdk.getBlcokChainSdkObj().getTaskQueueStatusAsync();
        Assert.assertTrue(listTaskQueue.size()>0);
    }

    @Test
    public void executeTransactionAsyncTest() throws Exception{
        ExecuteTransactionDto executeTransactionDtoObj=new ExecuteTransactionDto();
        executeTransactionDtoObj.setRawTransaction("0a220a20d211cf723f17396f7cf282dc03a26368e82a026d56989303f35665ecb43c708612220a20dd8eea50c31966e06e4a2662bebef7ed81d09a47b2eb1eb3729f2f0cc78129ae1881c70122042ef6caf82a18476574436f6e74726163744164647265737342794e616d6532220a20b7eb587576d2f1b1bfad8046c595d3ec5576c581a21e81a243fe9dad8b44276482f10441e584847422d5379a6ceb9ed286cf8e1ebc68a41e53b202eb05e9160f1f1fecc5562c9fa23ccd9bfd528b51c42c5ba8b20b93beb23033c7acab448b18189f017001");
        aelfSdk.getBlcokChainSdkObj().executeTransactionAsync(executeTransactionDtoObj);
        throw new Exception("存在异常(protobuff)");
    }
    @Test
    public void executeRawTransactionAsyncTest() throws Exception{
        String toAddress = aelfSdk.getGenesisContractAddressAsync();
        String methodName = "GetContractAddressByName";
        byte[] paramBytes = Sha256.getBytesSHA256("AElf.ContractNames.Consensus");
        ChainstatusDto status = aelfSdk.getBlcokChainSdkObj().getChainStatusAsync();
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
        CreateRawTransactionOutput createRawTransactionOutputObj=aelfSdk.getBlcokChainSdkObj().rawTransactionAsync(createRawTransactionInputObj);

        byte[] rawTransactionBytes=ByteArrayHelper.hexToByteArray(createRawTransactionOutputObj.getRawTransaction());
        byte[] transactionId=Sha256.getBytesSHA256(rawTransactionBytes);
        String signature=aelfSdk.GetSignatureWithToHex(privateKey,transactionId);

        ExecuteRawTransactionDto executeRawTransactionDtoObj=new ExecuteRawTransactionDto();
        executeRawTransactionDtoObj.setRawTransaction(createRawTransactionOutputObj.getRawTransaction());
        executeRawTransactionDtoObj.setSignature(signature);

        aelfSdk.getBlcokChainSdkObj().executeRawTransactionAsync(executeRawTransactionDtoObj);
    }
    @Test
    public void rawTransactionAsyncTest() throws Exception{
        String toAddress = aelfSdk.getGenesisContractAddressAsync();
        String methodName = "GetContractAddressByName";
        byte[] paramBytes = Sha256.getBytesSHA256("AElf.ContractNames.Token");

        ChainstatusDto status = aelfSdk.getBlcokChainSdkObj().getChainStatusAsync();
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
         aelfSdk.getBlcokChainSdkObj().rawTransactionAsync(createRawTransactionInputObj);
    }

    @Test
    public void sendRawTransactionAsyncTest() throws Exception{
        String toAddress = aelfSdk.getGenesisContractAddressAsync();
        String methodName = "GetContractAddressByName";
        String param="";
        byte[] paramBytes = Sha256.getBytesSHA256("AElf.ContractNames.Token");
        ChainstatusDto status = aelfSdk.getBlcokChainSdkObj().getChainStatusAsync();
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
        CreateRawTransactionOutput createRawTransactionOutputObj=aelfSdk.getBlcokChainSdkObj().rawTransactionAsync(createRawTransactionInputObj);

        byte[] rawTransactionBytes=ByteArrayHelper.hexToByteArray(createRawTransactionOutputObj.getRawTransaction());
        byte[] transactionId=Sha256.getBytesSHA256(rawTransactionBytes);
        String signature=aelfSdk.GetSignatureWithToHex(privateKey,transactionId);

        SendRawTransactionInput sendRawTransactionInputObj=new SendRawTransactionInput();
        sendRawTransactionInputObj.setTransaction(createRawTransactionOutputObj.getRawTransaction());
        sendRawTransactionInputObj.setSignature(signature);
        sendRawTransactionInputObj.setReturnTransaction(true);
        aelfSdk.getBlcokChainSdkObj().sendRawTransactionAsync(sendRawTransactionInputObj);
    }

    @Test
    public void sendTransactionAsyncTest() throws Exception{
        SendTransactionInput sendTransactionInputObj=new SendTransactionInput();
        sendTransactionInputObj.setRawTransaction("0a220a20d211cf723f17396f7cf282dc03a26368e82a026d56989303f35665ecb43c708612220a20dd8eea50c31966e06e4a2662bebef7ed81d09a47b2eb1eb3729f2f0cc78129ae1881c70122042ef6caf82a18476574436f6e74726163744164647265737342794e616d6532220a20b7eb587576d2f1b1bfad8046c595d3ec5576c581a21e81a243fe9dad8b44276482f10441e584847422d5379a6ceb9ed286cf8e1ebc68a41e53b202eb05e9160f1f1fecc5562c9fa23ccd9bfd528b51c42c5ba8b20b93beb23033c7acab448b18189f017001");
        aelfSdk.getBlcokChainSdkObj().sendTransactionAsync(sendTransactionInputObj);
        throw  new Exception("存在异常(protobuff)");
    }

    @Test
    public void sendTransactionsAsyncTest() throws Exception{


        String privateKeyHex="09da44778f8db2e602fb484334f37df19e221c84c4582ce5b7770ccfbc3ddbef";

        String toAddress = aelfSdk.getGenesisContractAddressAsync();
        String param = Sha256.getSHA256("AElf.ContractNames.Vote");
        String methodName = "GetContractAddressByName";



        TransactionDto transaction=aelfSdk.generateTransaction(address,toAddress,methodName,param);

        transaction=aelfSdk.signTransaction(privateKeyHex,transaction);

        SendTransactionsInput sendTransactionsInputs=new SendTransactionsInput();
        String rawTransactions="0a220a20d211cf723f17396f7cf282dc03a26368e82a026d56989303f35665ecb43c708612220a20dd8eea50c31966e06e4a2662bebef7ed81d09a47b2eb1eb3729f2f0cc78129ae18182204406eae3a2a18476574436f6e74726163744164647265737342794e616d6532220a20a2a00f8583c08daa00b80b0bbac4684396fe966b683ea956a63bd8845eee6ae782f1044137ec1270b9d13edd3492923345e7b2bdef50741b98815c252b10ce379481217c2279927d17ca3051cf4a272e188f01cdaf566c1608a8993246aa3c314165b5c901,0a220a20d211cf723f17396f7cf282dc03a26368e82a026d56989303f35665ecb43c708612220a20dd8eea50c31966e06e4a2662bebef7ed81d09a47b2eb1eb3729f2f0cc78129ae18182204406eae3a2a18476574436f6e74726163744164647265737342794e616d6532220a20d48d76882aad8bf04e747c8e057ad32d13bfdffd95df8171abc6a22e5a75c8ed82f10441293aa41666da5eff03b85a34abf3d735a1015a2dbda94608df94edaa6cb231ac57af680e228902714f38e8528b9443c5d66580f80fa6f50403e7c86c5259aa8600";
        sendTransactionsInputs.setRawTransactions(rawTransactions);
        List<String> listString=aelfSdk.getBlcokChainSdkObj().sendTransactionsAsync(sendTransactionsInputs);
        Assert.assertTrue(listString.size()>0);

        throw new Exception("存在异常");

    }


    @Test
    public void getTransactionResultsAsyncTest() throws Exception{
        long blockHeight=aelfSdk.getBlcokChainSdkObj().getBlockHeightAsync();
        BlockDto blockDto=aelfSdk.getBlcokChainSdkObj().getBlockByHeightAsync(blockHeight,false);
        aelfSdk.getBlcokChainSdkObj().getTransactionResultsAsync(blockDto.getBlockHash(),0,10);
    }
    @Test
    public void getTransactionResultAsyncTest() throws Exception{
        long blockHeight=aelfSdk.getBlcokChainSdkObj().getBlockHeightAsync();
        BlockDto blockDto=aelfSdk.getBlcokChainSdkObj().getBlockByHeightAsync(blockHeight,false);
        List<TransactionResultDto> transactionResultDtoList=aelfSdk.getBlcokChainSdkObj().getTransactionResultsAsync(blockDto.getBlockHash(),0,10);
        for(TransactionResultDto transactionResultDtoObj:transactionResultDtoList){
            aelfSdk.getBlcokChainSdkObj().getTransactionResultAsync(transactionResultDtoObj.getTransactionId());
        }
    }
    @Test
    public  void getMerklePathByTransactionIdAsyncTest() throws Exception{
        long blockHeight=aelfSdk.getBlcokChainSdkObj().getBlockHeightAsync();
        BlockDto blockDto=aelfSdk.getBlcokChainSdkObj().getBlockByHeightAsync(blockHeight,false);
        List<TransactionResultDto> transactionResultDtoList=aelfSdk.getBlcokChainSdkObj().getTransactionResultsAsync(blockDto.getBlockHash(),0,10);
        for(TransactionResultDto transactionResultDtoObj:transactionResultDtoList){
            aelfSdk.getBlcokChainSdkObj().getMerklePathByTransactionIdAsync(transactionResultDtoObj.getTransactionId());
        }
    }




}
