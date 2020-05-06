package io.aelf.test;

import com.google.protobuf.ByteString;
import io.aelf.protobuf.generated.Core;
import io.aelf.protobuf.generated.Core.TransactionResultStatus;
import io.aelf.protobuf.generated.TokenContract.GetBalanceOutput;
import io.aelf.protobuf.generated.TransactionFee.TransactionFeeCharged;
import io.aelf.protobuf.generated.TransactionFee.TransactionFeeCharged.Builder;
import io.aelf.schemas.BlockDto;
import io.aelf.schemas.ChainstatusDto;
import io.aelf.schemas.CreateRawTransactionInput;
import io.aelf.schemas.CreateRawTransactionOutput;
import io.aelf.schemas.ExecuteRawTransactionDto;
import io.aelf.schemas.ExecuteTransactionDto;
import io.aelf.schemas.KeyPairInfo;
import io.aelf.schemas.LogEventDto;
import io.aelf.schemas.SendRawTransactionInput;
import io.aelf.schemas.SendTransactionInput;
import io.aelf.schemas.SendTransactionOutput;
import io.aelf.schemas.SendTransactionsInput;
import io.aelf.schemas.TaskQueueInfoDto;
import io.aelf.schemas.TransactionResultDto;
import io.aelf.sdk.AElfClient;
import io.aelf.utils.ByteArrayHelper;
import io.aelf.utils.JsonUtil;
import io.aelf.utils.MapEntry;
import io.aelf.utils.Maps;
import io.aelf.utils.Sha256;
import io.aelf.utils.StringUtil;
import io.aelf.utils.TransactionResultDtoExtension;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

public class BlockChainSdkTest {

  static final String HTTPURL = "http://127.0.0.1:8000";
  AElfClient client = null;
  String privateKey = "cd86ab6347d8e52bbbe8532141fc59ce596268143a308d1d40fedf385528b458";
  String address = "";

  /**
   * init junit.
   *
   * @throws Exception not blank
   */
  @Before
  public void init() throws Exception {
    client = new AElfClient(HTTPURL);
    address = client.getAddressFromPrivateKey(privateKey);
  }

  @Test
  public void getTransactionFeeTest() throws Exception {
    TransactionResultDto transactionResultDto = new TransactionResultDto();
    transactionResultDto.setLogs(new ArrayList<>());

    LogEventDto logEventDto=new LogEventDto();
    logEventDto.setName("TransactionFeeCharged");
    Base64 base64 = new Base64();
    Builder tfeeCharged=TransactionFeeCharged.newBuilder();
    tfeeCharged.setSymbol("ELF");
    tfeeCharged.setAmount(1000);
    String nonIndexed=base64.encodeToString(tfeeCharged.build().toByteArray());
    logEventDto.setNonIndexed(nonIndexed);
    transactionResultDto.getLogs().add(logEventDto);

    logEventDto=new LogEventDto();
    logEventDto.setName("ResourceTokenCharged");
    tfeeCharged=TransactionFeeCharged.newBuilder();
    tfeeCharged.setSymbol("READ");
    tfeeCharged.setAmount(800);
    nonIndexed=base64.encodeToString(tfeeCharged.build().toByteArray());
    logEventDto.setNonIndexed(nonIndexed);
    transactionResultDto.getLogs().add(logEventDto);

    logEventDto=new LogEventDto();
    logEventDto.setName("ResourceTokenCharged");
    tfeeCharged=TransactionFeeCharged.newBuilder();
    tfeeCharged.setSymbol("WRITE");
    tfeeCharged.setAmount(600);
    nonIndexed=base64.encodeToString(tfeeCharged.build().toByteArray());
    logEventDto.setNonIndexed(nonIndexed);
    transactionResultDto.getLogs().add(logEventDto);

    logEventDto=new LogEventDto();
    logEventDto.setName("ResourceTokenOwned");
    tfeeCharged=TransactionFeeCharged.newBuilder();
    tfeeCharged.setSymbol("READ");
    tfeeCharged.setAmount(200);
    nonIndexed=base64.encodeToString(tfeeCharged.build().toByteArray());
    logEventDto.setNonIndexed(nonIndexed);
    transactionResultDto.getLogs().add(logEventDto);

    HashMap<String,Long> transactionFees = TransactionResultDtoExtension.getTransactionFees(transactionResultDto);
    Assert.assertTrue(transactionFees.keySet().size()==3);
    Assert.assertTrue(transactionFees.get("ELF")==1000);
    Assert.assertTrue(transactionFees.get("READ")==800);
    Assert.assertTrue(transactionFees.get("WRITE")==600);

    transactionResultDto = new TransactionResultDto();
    transactionResultDto.setLogs(new ArrayList<>());
    transactionFees = TransactionResultDtoExtension.getTransactionFees(transactionResultDto);
    Assert.assertTrue(transactionFees.size()==0);

  }


  @Test
  public void getAddressFromPubKeyTest() {
    org.bitcoinj.core.ECKey aelfKey = org.bitcoinj.core.ECKey
        .fromPrivate(new BigInteger(privateKey, 16)).decompress();
    String pubKey = Hex.toHexString(aelfKey.getPubKey());
    String pubKeyAddress = client.getAddressFromPubKey(pubKey);
    Assert.assertTrue(pubKeyAddress.equals(address));
  }

  @Test
  public void getFormattedAddressTest() throws Exception {
    String addressVal = client.getFormattedAddress(privateKey, address);
    Assert.assertTrue(("ELF_" + address + "_AELF").equals(addressVal));
  }

  @Test
  public void getNewKeyPairInfoTest() throws Exception {
    KeyPairInfo keyPairInfo = client.generateKeyPairInfo();
    Assert.assertEquals(client.getAddressFromPrivateKey(keyPairInfo.getPrivateKey()),
        keyPairInfo.getAddress());
  }

  @Test
  public void getPublicKeyAsHexTest() throws Exception {
    Assert.assertTrue("SD6BXDrKT2syNd1WehtPyRo3dPBiXqfGUj8UJym7YP9W9RynM".equals(address));
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
    client.getBlockByHeight(blockHeight);
  }


  @Test
  public void getBlockByHeightForFalseTest() throws Exception {
    long blockHeight = client.getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    client.getBlockByHeight(blockHeight, false);
  }

  @Test
  public void getBlockByHeightForTrueTest() throws Exception {
    long blockHeight = client.getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    client.getBlockByHeight(blockHeight, true);
  }


  @Test
  public void getBlockByHashDefaultTest() throws Exception {
    long blockHeight = client.getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    BlockDto blockDto = client.getBlockByHeight(blockHeight);
    client.getBlockByHash(blockDto.getBlockHash());
  }

  @Test
  public void getBlockByHashForFalseTest() throws Exception {
    long blockHeight = client.getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    BlockDto blockDto = client.getBlockByHeight(blockHeight, false);
    client.getBlockByHash(blockDto.getBlockHash());
  }

  @Test
  public void getBlockByHashForFalseTrue() throws Exception {
    long blockHeight = client.getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    BlockDto blockDto = client.getBlockByHeight(blockHeight, true);
    client.getBlockByHash(blockDto.getBlockHash());
  }

  @Test
  public void getTransactionPoolStatusTest() throws Exception {
    client.getTransactionPoolStatus();
  }

  @Test
  public void getChainStatusTest() throws Exception {
    client.getChainStatus();
  }

  @Test
  public void getContractFilCeDescriptorSetTest() throws Exception {
    long blockHeight = client.getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    BlockDto blockDto = client.getBlockByHeight(blockHeight, false);
    List<TransactionResultDto> transactionResultDtoList = client
        .getTransactionResults(blockDto.getBlockHash(), 0, 10);
    for (TransactionResultDto transactionResultDtoObj : transactionResultDtoList) {
      client
          .getContractFilCeDescriptorSet(transactionResultDtoObj.getTransaction().getTo());
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
    String methodName = "GetContractAddressByName";
    byte[] bytes = Sha256.getBytesSha256("AElf.ContractNames.TokenConverter");
    Core.Transaction.Builder transaction = client
        .generateTransaction(address, toAddress, methodName, bytes);
    Core.Transaction transactionObj = transaction.build();
    String signature = client.signTransaction(privateKey, transactionObj);
    transaction.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
    transactionObj = transaction.build();
    ExecuteTransactionDto executeTransactionDtoObj = new ExecuteTransactionDto();
    executeTransactionDtoObj.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
    client.executeTransaction(executeTransactionDtoObj);
  }

  @Test
  public void executeRawTransactionTest() throws Exception {
    String toAddress = client.getGenesisContractAddress();
    final String methodName = "GetContractAddressByName";
    byte[] paramBytes = Sha256.getBytesSha256("AElf.ContractNames.Consensus");
    ChainstatusDto status = client.getChainStatus();
    final long height = status.getBestChainHeight();
    final String blockHash = status.getBestChainHash();
    MapEntry mapParamsObj = Maps.newMapEntry();
    Base64 base64 = new Base64();
    mapParamsObj.put("value", base64.encodeToString(paramBytes));
    String param = JsonUtil.toJsonString(mapParamsObj);
    CreateRawTransactionInput createRawTransactionInputObj = createRowBuild(toAddress, methodName,
        param, height, blockHash);
    CreateRawTransactionOutput createRawTransactionOutputObj = client
        .createRawTransaction(createRawTransactionInputObj);
    byte[] rawTransactionBytes = ByteArrayHelper
        .hexToByteArray(createRawTransactionOutputObj.getRawTransaction());
    byte[] transactionId = Sha256.getBytesSha256(rawTransactionBytes);
    String signature = client.getSignatureWithPrivateKey(privateKey, transactionId);
    ExecuteRawTransactionDto executeRawTransactionDtoObj = new ExecuteRawTransactionDto();
    executeRawTransactionDtoObj
        .setRawTransaction(createRawTransactionOutputObj.getRawTransaction());
    executeRawTransactionDtoObj.setSignature(signature);
    client.executeRawTransaction(executeRawTransactionDtoObj);
  }

  @Test
  public void createRawTransactionTest() throws Exception {
    String toAddress = client.getGenesisContractAddress();
    final String methodName = "GetContractAddressByName";
    byte[] paramBytes = Sha256.getBytesSha256("AElf.ContractNames.Token");
    ChainstatusDto status = client.getChainStatus();
    final long height = status.getBestChainHeight();
    final String blockHash = status.getBestChainHash();
    MapEntry mapParamsObj = Maps.newMapEntry();
    Base64 base64 = new Base64();
    mapParamsObj.put("value", base64.encodeToString(paramBytes));
    String param = JsonUtil.toJsonString(mapParamsObj);
    CreateRawTransactionInput createRawTransactionInputObj = createRowBuild(toAddress, methodName,
        param, height, blockHash);
    client.createRawTransaction(createRawTransactionInputObj);
  }

  @Test
  public void sendRawTransactionTest() throws Exception {
    final String toAddress = client.getGenesisContractAddress();
    final String methodName = "GetContractAddressByName";
    byte[] paramBytes = Sha256.getBytesSha256("AElf.ContractNames.Token");
    ChainstatusDto status = client.getChainStatus();
    final long height = status.getBestChainHeight();
    final String blockHash = status.getBestChainHash();
    MapEntry mapParamsObj = Maps.newMapEntry();
    Base64 base64 = new Base64();
    mapParamsObj.put("value", base64.encodeToString(paramBytes));
    String param = JsonUtil.toJsonString(mapParamsObj);
    CreateRawTransactionInput createRawTransactionInputObj = createRowBuild(toAddress, methodName,
        param, height, blockHash);
    CreateRawTransactionOutput createRawTransactionOutputObj = client
        .createRawTransaction(createRawTransactionInputObj);
    byte[] rawTransactionBytes = ByteArrayHelper
        .hexToByteArray(createRawTransactionOutputObj.getRawTransaction());
    byte[] transactionId = Sha256.getBytesSha256(rawTransactionBytes);
    String signature = client.getSignatureWithPrivateKey(privateKey, transactionId);
    SendRawTransactionInput sendRawTransactionInputObj = new SendRawTransactionInput();
    sendRawTransactionInputObj.setTransaction(createRawTransactionOutputObj.getRawTransaction());
    sendRawTransactionInputObj.setSignature(signature);
    sendRawTransactionInputObj.setReturnTransaction(true);
    client.sendRawTransaction(sendRawTransactionInputObj);
  }

  @Test
  public void sendTransactionTest() throws Exception {
    String toAddress = client.getGenesisContractAddress();
    String methodName = "GetContractAddressByName";
    byte[] bytes = Sha256.getBytesSha256("AElf.ContractNames.Vote");
    Core.Transaction transactionObj = buildTransaction(toAddress, methodName, bytes);
    SendTransactionInput sendTransactionInputObj = new SendTransactionInput();
    sendTransactionInputObj.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
    client.sendTransaction(sendTransactionInputObj);
  }

  @Test
  public void sendTransactionsTest() throws Exception {
    String toAddress = client.getGenesisContractAddress();
    byte[] param1 = Sha256.getBytesSha256("AElf.ContractNames.Token");
    byte[] param2 = Sha256.getBytesSha256("AElf.ContractNames.Vote");
    String methodName = "GetContractAddressByName";
    List<byte[]> parameters = new ArrayList<byte[]>();
    parameters.add(param1);
    parameters.add(param2);
    for (byte[] tmp : parameters) {
      Core.Transaction transactionObj = buildTransaction(toAddress, methodName, tmp);
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
      client
          .getTransactionResult(transactionResultDtoObj.getTransactionId());
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
      client
          .getMerklePathByTransactionId(transactionResultDtoObj.getTransactionId());
    }
  }

  @Test
  public void getChainIdTest() throws Exception {
    int chainId = client.getChainId();
    Assert.assertTrue(chainId == 9992731);
  }

  @Test
  public void isConnectedTest() {
    Assert.assertTrue(client.isConnected());
  }

  @Test
  public void potobuffTest() throws Exception {
    String toAddress = client.getGenesisContractAddress();
    byte[] bytes = Sha256.getBytesSha256("AElf.ContractNames.Vote");
    String methodName = "GetContractAddressByName";
    Core.Transaction transactionObj = buildTransaction(toAddress, methodName, bytes);
    SendTransactionInput sendTransactionInputObj = new SendTransactionInput();
    sendTransactionInputObj.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
    client.sendTransaction(sendTransactionInputObj);
  }



  private Core.Transaction buildTransaction(String toAddress, String methodName, byte[] tmp)
      throws Exception {
    Core.Transaction.Builder transaction = client
        .generateTransaction(address, toAddress, methodName, tmp);
    Core.Transaction transactionObj = transaction.build();
    String signature = client.signTransaction(privateKey, transactionObj);
    transaction.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
    transactionObj = transaction.build();
    return transactionObj;
  }

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
