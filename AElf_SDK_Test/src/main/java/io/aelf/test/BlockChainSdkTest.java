package io.aelf.test;

import com.google.protobuf.ByteString;
import io.aelf.proto.Core;
import io.aelf.schemas.BlockDto;
import io.aelf.schemas.ChainstatusDto;
import io.aelf.schemas.CreateRawTransactionInput;
import io.aelf.schemas.CreateRawTransactionOutput;
import io.aelf.schemas.ExecuteRawTransactionDto;
import io.aelf.schemas.ExecuteTransactionDto;
import io.aelf.schemas.SendRawTransactionInput;
import io.aelf.schemas.SendTransactionInput;
import io.aelf.schemas.SendTransactionsInput;
import io.aelf.schemas.TaskQueueInfoDto;
import io.aelf.schemas.TransactionResultDto;
import io.aelf.sdk.AelfSdk;
import io.aelf.utils.ByteArrayHelper;
import io.aelf.utils.JsonUtil;
import io.aelf.utils.MapEntry;
import io.aelf.utils.Maps;
import io.aelf.utils.Sha256;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BlockChainSdkTest {

  static final String HTTPURL = "http://127.0.0.1:8200";
  AelfSdk aelfSdk = null;
  String privateKey = "cd86ab6347d8e52bbbe8532141fc59ce596268143a308d1d40fedf385528b458";
  String address = "";

  /**
   * init junit.
   *
   * @throws Exception not blank
   */
  @Before
  public void init() throws Exception {
    aelfSdk = new AelfSdk(HTTPURL);
    address = aelfSdk.getAddressFromPrivateKey(privateKey);
  }

  @Test
  public void getAddressFromPubKeyTest() {
    org.bitcoinj.core.ECKey aelfKey = org.bitcoinj.core.ECKey
        .fromPrivate(new BigInteger(privateKey, 16)).decompress();
    String pubKey = Hex.toHexString(aelfKey.getPubKey());
    String pubKeyAddress = aelfSdk.getAddressFromPubKey(pubKey);
    Assert.assertTrue(pubKeyAddress.equals(address));
  }

  @Test
  public void getFormattedAddressTest() throws Exception {
    String addressVal = aelfSdk.getFormattedAddress(privateKey, address);
    Assert.assertTrue(("ELF_" + address + "_AELF").equals(addressVal));
  }

  @Test
  public void getPublicKeyAsHexTest() throws Exception {
    Assert.assertTrue("SD6BXDrKT2syNd1WehtPyRo3dPBiXqfGUj8UJym7YP9W9RynM".equals(address));
  }

  @Test
  public void getBlockHeightTest() throws Exception {
    long blockHeight = aelfSdk.getBlockChainSdkObj().getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
  }

  @Test
  public void getBlockByHeightDefaultTest() throws Exception {
    long blockHeight = aelfSdk.getBlockChainSdkObj().getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    aelfSdk.getBlockChainSdkObj().getBlockByHeight(blockHeight);
  }


  @Test
  public void getBlockByHeightForFalseTest() throws Exception {
    long blockHeight = aelfSdk.getBlockChainSdkObj().getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    aelfSdk.getBlockChainSdkObj().getBlockByHeight(blockHeight, false);
  }

  @Test
  public void getBlockByHeightForTrueTest() throws Exception {
    long blockHeight = aelfSdk.getBlockChainSdkObj().getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    aelfSdk.getBlockChainSdkObj().getBlockByHeight(blockHeight, true);
  }


  @Test
  public void getBlockByHashDefaultTest() throws Exception {
    long blockHeight = aelfSdk.getBlockChainSdkObj().getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    BlockDto blockDto = aelfSdk.getBlockChainSdkObj().getBlockByHeight(blockHeight);
    aelfSdk.getBlockChainSdkObj().getBlockByHash(blockDto.getBlockHash());
  }

  @Test
  public void getBlockByHashForFalseTest() throws Exception {
    long blockHeight = aelfSdk.getBlockChainSdkObj().getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    BlockDto blockDto = aelfSdk.getBlockChainSdkObj().getBlockByHeight(blockHeight, false);
    aelfSdk.getBlockChainSdkObj().getBlockByHash(blockDto.getBlockHash());
  }

  @Test
  public void getBlockByHashForFalseTrue() throws Exception {
    long blockHeight = aelfSdk.getBlockChainSdkObj().getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    BlockDto blockDto = aelfSdk.getBlockChainSdkObj().getBlockByHeight(blockHeight, true);
    aelfSdk.getBlockChainSdkObj().getBlockByHash(blockDto.getBlockHash());
  }

  @Test
  public void getTransactionPoolStatusTest() throws Exception {
    aelfSdk.getBlockChainSdkObj().getTransactionPoolStatus();
  }

  @Test
  public void getChainStatusTest() throws Exception {
    aelfSdk.getBlockChainSdkObj().getChainStatus();
  }

  @Test
  public void getContractFilCeDescriptorSetTest() throws Exception {
    long blockHeight = aelfSdk.getBlockChainSdkObj().getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    BlockDto blockDto = aelfSdk.getBlockChainSdkObj().getBlockByHeight(blockHeight, false);
    List<TransactionResultDto> transactionResultDtoList = aelfSdk.getBlockChainSdkObj()
        .getTransactionResults(blockDto.getBlockHash(), 0, 10);
    for (TransactionResultDto transactionResultDtoObj : transactionResultDtoList) {
      aelfSdk.getBlockChainSdkObj()
          .getContractFilCeDescriptorSet(transactionResultDtoObj.getTransaction().getTo());
    }
  }

  @Test
  public void getTaskQueueStatusTest() throws Exception {
    List<TaskQueueInfoDto> listTaskQueue = aelfSdk.getBlockChainSdkObj().getTaskQueueStatus();
    Assert.assertTrue(listTaskQueue.size() > 0);
  }

  @Test
  public void executeTransactionTest() throws Exception {
    String toAddress = aelfSdk.getGenesisContractAddress();
    String methodName = "GetContractAddressByName";
    byte[] bytes = Sha256.getBytesSha256("AElf.ContractNames.TokenConverter");
    Core.Transaction.Builder transaction = aelfSdk
        .generateTransaction(address, toAddress, methodName, bytes);
    Core.Transaction transactionObj = transaction.build();
    String signature = aelfSdk.signTransaction(privateKey, transactionObj);
    transaction.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
    transactionObj = transaction.build();
    ExecuteTransactionDto executeTransactionDtoObj = new ExecuteTransactionDto();
    executeTransactionDtoObj.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
    aelfSdk.getBlockChainSdkObj().executeTransaction(executeTransactionDtoObj);
  }

  @Test
  public void executeRawTransactionTest() throws Exception {
    String toAddress = aelfSdk.getGenesisContractAddress();
    final String methodName = "GetContractAddressByName";
    byte[] paramBytes = Sha256.getBytesSha256("AElf.ContractNames.Consensus");
    ChainstatusDto status = aelfSdk.getBlockChainSdkObj().getChainStatus();
    final long height = status.getBestChainHeight();
    final String blockHash = status.getBestChainHash();
    MapEntry mapParamsObj = Maps.newMapEntry();
    Base64 base64 = new Base64();
    mapParamsObj.put("value", base64.encodeToString(paramBytes));
    String param = JsonUtil.toJsonString(mapParamsObj);
    CreateRawTransactionInput createRawTransactionInputObj = createRowBuild(toAddress, methodName,
        param, height, blockHash);
    CreateRawTransactionOutput createRawTransactionOutputObj = aelfSdk.getBlockChainSdkObj()
        .createRawTransaction(createRawTransactionInputObj);
    byte[] rawTransactionBytes = ByteArrayHelper
        .hexToByteArray(createRawTransactionOutputObj.getRawTransaction());
    byte[] transactionId = Sha256.getBytesSha256(rawTransactionBytes);
    String signature = aelfSdk.getSignatureWithPrivateKey(privateKey, transactionId);
    ExecuteRawTransactionDto executeRawTransactionDtoObj = new ExecuteRawTransactionDto();
    executeRawTransactionDtoObj
        .setRawTransaction(createRawTransactionOutputObj.getRawTransaction());
    executeRawTransactionDtoObj.setSignature(signature);
    aelfSdk.getBlockChainSdkObj().executeRawTransaction(executeRawTransactionDtoObj);
  }

  @Test
  public void createRawTransactionTest() throws Exception {
    String toAddress = aelfSdk.getGenesisContractAddress();
    final String methodName = "GetContractAddressByName";
    byte[] paramBytes = Sha256.getBytesSha256("AElf.ContractNames.Token");
    ChainstatusDto status = aelfSdk.getBlockChainSdkObj().getChainStatus();
    final long height = status.getBestChainHeight();
    final String blockHash = status.getBestChainHash();
    MapEntry mapParamsObj = Maps.newMapEntry();
    Base64 base64 = new Base64();
    mapParamsObj.put("value", base64.encodeToString(paramBytes));
    String param = JsonUtil.toJsonString(mapParamsObj);
    CreateRawTransactionInput createRawTransactionInputObj = createRowBuild(toAddress, methodName,
        param, height, blockHash);
    aelfSdk.getBlockChainSdkObj().createRawTransaction(createRawTransactionInputObj);
  }

  @Test
  public void sendRawTransactionTest() throws Exception {
    final String toAddress = aelfSdk.getGenesisContractAddress();
    final String methodName = "GetContractAddressByName";
    byte[] paramBytes = Sha256.getBytesSha256("AElf.ContractNames.Token");
    ChainstatusDto status = aelfSdk.getBlockChainSdkObj().getChainStatus();
    final long height = status.getBestChainHeight();
    final String blockHash = status.getBestChainHash();
    MapEntry mapParamsObj = Maps.newMapEntry();
    Base64 base64 = new Base64();
    mapParamsObj.put("value", base64.encodeToString(paramBytes));
    String param = JsonUtil.toJsonString(mapParamsObj);
    CreateRawTransactionInput createRawTransactionInputObj = createRowBuild(toAddress, methodName,
        param, height, blockHash);
    CreateRawTransactionOutput createRawTransactionOutputObj = aelfSdk.getBlockChainSdkObj()
        .createRawTransaction(createRawTransactionInputObj);
    byte[] rawTransactionBytes = ByteArrayHelper
        .hexToByteArray(createRawTransactionOutputObj.getRawTransaction());
    byte[] transactionId = Sha256.getBytesSha256(rawTransactionBytes);
    String signature = aelfSdk.getSignatureWithPrivateKey(privateKey, transactionId);
    SendRawTransactionInput sendRawTransactionInputObj = new SendRawTransactionInput();
    sendRawTransactionInputObj.setTransaction(createRawTransactionOutputObj.getRawTransaction());
    sendRawTransactionInputObj.setSignature(signature);
    sendRawTransactionInputObj.setReturnTransaction(true);
    aelfSdk.getBlockChainSdkObj().sendRawTransaction(sendRawTransactionInputObj);
  }

  @Test
  public void sendTransactionTest() throws Exception {
    String toAddress = aelfSdk.getGenesisContractAddress();
    String methodName = "GetContractAddressByName";
    byte[] bytes = Sha256.getBytesSha256("AElf.ContractNames.Vote");
    Core.Transaction transactionObj = buildTransaction(toAddress, methodName, bytes);
    SendTransactionInput sendTransactionInputObj = new SendTransactionInput();
    sendTransactionInputObj.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
    aelfSdk.getBlockChainSdkObj().sendTransaction(sendTransactionInputObj);
  }

  @Test
  public void sendTransactionsTest() throws Exception {
    String toAddress = aelfSdk.getGenesisContractAddress();
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
      List<String> listString = aelfSdk.getBlockChainSdkObj()
          .sendTransactions(sendTransactionsInputs);
      Assert.assertTrue(listString.size() > 0);
    }
  }


  @Test
  public void getTransactionResultsTest() throws Exception {
    long blockHeight = aelfSdk.getBlockChainSdkObj().getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    BlockDto blockDto = aelfSdk.getBlockChainSdkObj().getBlockByHeight(blockHeight, false);
    aelfSdk.getBlockChainSdkObj().getTransactionResults(blockDto.getBlockHash(), 0, 10);
  }

  @Test
  public void getTransactionResultTest() throws Exception {
    long blockHeight = aelfSdk.getBlockChainSdkObj().getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    BlockDto blockDto = aelfSdk.getBlockChainSdkObj().getBlockByHeight(blockHeight, false);
    List<TransactionResultDto> transactionResultDtoList = aelfSdk.getBlockChainSdkObj()
        .getTransactionResults(blockDto.getBlockHash(), 0, 10);
    for (TransactionResultDto transactionResultDtoObj : transactionResultDtoList) {
      aelfSdk.getBlockChainSdkObj()
          .getTransactionResult(transactionResultDtoObj.getTransactionId());
    }
  }

  @Test
  public void getMerklePathByTransactionIdTest() throws Exception {
    long blockHeight = aelfSdk.getBlockChainSdkObj().getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    BlockDto blockDto = aelfSdk.getBlockChainSdkObj().getBlockByHeight(blockHeight, false);
    List<TransactionResultDto> transactionResultDtoList = aelfSdk.getBlockChainSdkObj()
        .getTransactionResults(blockDto.getBlockHash(), 0, 10);
    for (TransactionResultDto transactionResultDtoObj : transactionResultDtoList) {
      aelfSdk.getBlockChainSdkObj()
          .getMerklePathByTransactionId(transactionResultDtoObj.getTransactionId());
    }
  }

  @Test
  public void getChainIdTest() throws Exception {
    int chainId = aelfSdk.getBlockChainSdkObj().getChainId();
    Assert.assertTrue(chainId == 9992731);
  }

  @Test
  public void isConnectedTest() {
    Assert.assertTrue(aelfSdk.isConnected());
  }

  @Test
  public void potobuffTest() throws Exception {
    String toAddress = aelfSdk.getGenesisContractAddress();
    byte[] bytes = Sha256.getBytesSha256("AElf.ContractNames.Vote");
    String methodName = "GetContractAddressByName";
    Core.Transaction transactionObj = buildTransaction(toAddress, methodName, bytes);
    SendTransactionInput sendTransactionInputObj = new SendTransactionInput();
    sendTransactionInputObj.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
    aelfSdk.getBlockChainSdkObj().sendTransaction(sendTransactionInputObj);
  }

  private Core.Transaction buildTransaction(String toAddress, String methodName, byte[] tmp)
      throws Exception {
    Core.Transaction.Builder transaction = aelfSdk
        .generateTransaction(address, toAddress, methodName, tmp);
    Core.Transaction transactionObj = transaction.build();
    String signature = aelfSdk.signTransaction(privateKey, transactionObj);
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
