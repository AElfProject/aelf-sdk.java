package io.aelf.sdk;

import com.google.protobuf.ByteString;

import io.aelf.protobuf.generated.Client;
import io.aelf.protobuf.generated.Core;
import io.aelf.protobuf.generated.Core.TransactionResultStatus;
import io.aelf.protobuf.generated.TokenContract;
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
import io.aelf.utils.AddressHelper;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.bitcoinj.core.Base58;

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
  public void getBlockDefaultTest() throws Exception {
    long blockHeight = client.getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    BlockDto blockByHeight = client.getBlockByHeight(blockHeight);
    BlockDto blockByHash = client.getBlockByHash(blockByHeight.getBlockHash());
    
    Assert.assertEquals(JsonUtil.toJsonString(blockByHeight), JsonUtil.toJsonString(blockByHash));
    Assert.assertEquals(blockByHeight.getBlockHash(),blockByHash.getBlockHash());
    Assert.assertEquals(blockByHeight.getHeader().getHeight(), blockHeight);
    VerifyBlock(blockByHeight,false);
  }


  @Test
  public void getBlockForFalseTest() throws Exception {
    long blockHeight = client.getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    BlockDto blockByHeight = client.getBlockByHeight(blockHeight,false);
    BlockDto blockByHash = client.getBlockByHash(blockByHeight.getBlockHash(),false);
    
    Assert.assertEquals(JsonUtil.toJsonString(blockByHeight), JsonUtil.toJsonString(blockByHash));
    Assert.assertEquals(blockByHeight.getHeader().getHeight(), blockHeight);
    VerifyBlock(blockByHeight,false);
  }

  @Test
  public void getBlockForTrueTest() throws Exception {
    long blockHeight = client.getBlockHeight();
    Assert.assertTrue(blockHeight > 0);
    BlockDto blockByHeight = client.getBlockByHeight(blockHeight,true);
    BlockDto blockByHash = client.getBlockByHash(blockByHeight.getBlockHash(),true);
    
    Assert.assertEquals(JsonUtil.toJsonString(blockByHeight), JsonUtil.toJsonString(blockByHash));
    Assert.assertEquals(blockByHeight.getBlockHash(),blockByHash.getBlockHash());
    Assert.assertEquals(blockByHeight.getHeader().getHeight(), blockHeight);
    VerifyBlock(blockByHeight,true);
  }

  private void VerifyBlock(BlockDto block, Boolean includeTransactions) throws Exception {
    Assert.assertFalse(block.getBlockHash().isEmpty());
    Assert.assertTrue(block.getHeader().getHeight()>0);
    Assert.assertFalse( block.getHeader().getPreviousBlockHash().isEmpty());
    Assert.assertFalse(block.getHeader().getMerkleTreeRootOfTransactions().isEmpty());
    Assert.assertFalse(block.getHeader().getMerkleTreeRootOfTransactionState().isEmpty());
    Assert.assertFalse(block.getHeader().getExtra().isEmpty());
    Assert.assertEquals("AELF", block.getHeader().getChainId());
    Assert.assertFalse(block.getHeader().getBloom().isEmpty());
    Assert.assertFalse(block.getHeader().getSignerPubkey().isEmpty());
    Assert.assertTrue(block.getHeader().getTime().after(new Date()));
	  Assert.assertTrue(block.getBody().getTransactionsCount() > 0);

    if(includeTransactions){
      Assert.assertTrue(block.getBody().getTransactions().size() == block.getBody().getTransactionsCount());
      for (String txId : block.getBody().getTransactions()) {   
         Assert.assertFalse(txId.isEmpty());
     } 
    }else{
      Assert.assertTrue(block.getBody().getTransactions().size() == 0);
    }
	  
	  BlockDto previousBlock = client.getBlockByHash(block.getHeader().getPreviousBlockHash(), false);
	  Assert.assertEquals(previousBlock.getBlockHash(), block.getHeader().getPreviousBlockHash());
	  Assert.assertEquals(previousBlock.getHeader().getHeight(), block.getHeader().getHeight()-1);
  }

  @Test
  public void getTransactionPoolStatusTest() throws Exception {
    client.getTransactionPoolStatus();
  }

  @Test
  public void getChainStatusTest() throws Exception {
    ChainstatusDto chainStatus = client.getChainStatus();

    Assert.assertEquals("AELF", chainStatus.getChainId());
	  Assert.assertTrue(chainStatus.getBranches().size() >0);
	  Assert.assertTrue(chainStatus.getLongestChainHeight() > 0);
	  Assert.assertFalse(chainStatus.getLongestChainHash().isEmpty());
	  Assert.assertFalse(chainStatus.getGenesisContractAddress().isEmpty());
	  Assert.assertFalse(chainStatus.getGenesisBlockHash().isEmpty());
	  Assert.assertTrue(chainStatus.getLastIrreversibleBlockHeight() > 0);
	  Assert.assertFalse(chainStatus.getLastIrreversibleBlockHash().isEmpty());
	  Assert.assertTrue(chainStatus.getBestChainHeight() > 0);
	  Assert.assertFalse(chainStatus.getBestChainHash().isEmpty());

	  BlockDto longestChainBlock = client.getBlockByHash(chainStatus.getLongestChainHash(), false);
	  Assert.assertEquals(longestChainBlock.getHeader().getHeight(), chainStatus.getLongestChainHeight());

	  BlockDto genesisBlock = client.getBlockByHash(chainStatus.getGenesisBlockHash(), false);
	  Assert.assertEquals(1, genesisBlock.getHeader().getHeight());

	  BlockDto lastIrreversibleBlock = client.getBlockByHash(chainStatus.getLastIrreversibleBlockHash(), false);
	  Assert.assertEquals(lastIrreversibleBlock.getHeader().getHeight(), chainStatus.getLastIrreversibleBlockHeight());

	  BlockDto bestChainBlock = client.getBlockByHash(chainStatus.getBestChainHash(), false);
	  Assert.assertEquals(bestChainBlock.getHeader().getHeight(), chainStatus.getBestChainHeight());

	  String genesisContractAddress = client.getGenesisContractAddress();
	  Assert.assertEquals(genesisContractAddress, chainStatus.getGenesisContractAddress());
  }

  @Test
  public void getContractFileDescriptorSetTest() throws Exception {
    String contractAddress = client.getGenesisContractAddress();
    byte[] descriptorSet = client.getContractFileDescriptorSet(contractAddress);
    Assert.assertTrue(descriptorSet.length>0);
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
    Client.Hash.Builder hash = Client.Hash.newBuilder();
    hash.setValue(ByteString.copyFrom(bytes));
    Client.Hash hashObj = hash.build();
    Core.Transaction.Builder transaction = client
        .generateTransaction(address, toAddress, methodName, hashObj.toByteArray());
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
    String tokenContractAddress = client.getContractAddressByName(privateKey, Sha256.getBytesSha256("AElf.ContractNames.Token"));
    KeyPairInfo keyPairInfo = client.generateKeyPairInfo();
    Core.Transaction transaction = createTransferTransaction(keyPairInfo.getAddress());

    SendTransactionInput sendTransactionInputObj = new SendTransactionInput();
    sendTransactionInputObj.setRawTransaction(Hex.toHexString(transaction.toByteArray()));
    SendTransactionOutput sendResult = client.sendTransaction(sendTransactionInputObj);
    Assert.assertFalse(sendResult.getTransactionId().isEmpty());

    Thread.sleep(4000);

    TransactionResultDto transactionResult = client.getTransactionResult(sendResult.getTransactionId());
    Assert.assertEquals("MINED",transactionResult.getStatus());
    Assert.assertEquals(sendResult.getTransactionId(),transactionResult.getTransactionId());
    Assert.assertTrue(transactionResult.getError().isEmpty());

    Assert.assertTrue(transactionResult.getLogs().size() == 2);

    Assert.assertEquals(tokenContractAddress, transactionResult.getLogs().get(0).getAddress());
    Assert.assertEquals("TransactionFeeCharged", transactionResult.getLogs().get(0).getName());
    byte[] nonIndexedBytes = Base64.decodeBase64(transactionResult.getLogs().get(0).getNonIndexed());
    TransactionFeeCharged feeCharged=TransactionFeeCharged.getDefaultInstance().parseFrom(nonIndexedBytes);
	  Assert.assertEquals("ELF", feeCharged.getSymbol());
	  Assert.assertTrue(feeCharged.getAmount() > 0);

	  Assert.assertEquals(tokenContractAddress, transactionResult.getLogs().get(1).getAddress());
	  Assert.assertEquals("Transferred", transactionResult.getLogs().get(1).getName());
    byte[] indexedBytes = Base64.decodeBase64(transactionResult.getLogs().get(1).getIndexed().get(0));
	  TokenContract.Transferred transferred = TokenContract.Transferred.getDefaultInstance().parseFrom(indexedBytes);
	  Assert.assertEquals(address, AddressHelper.addressToBase58(transferred.getFrom()));

    indexedBytes = Base64.decodeBase64(transactionResult.getLogs().get(1).getIndexed().get(1));
	  transferred = TokenContract.Transferred.getDefaultInstance().parseFrom(indexedBytes);
	  Assert.assertEquals(keyPairInfo.getAddress(), AddressHelper.addressToBase58(transferred.getTo()));
	
    indexedBytes = Base64.decodeBase64(transactionResult.getLogs().get(1).getIndexed().get(2));
	  transferred = TokenContract.Transferred.getDefaultInstance().parseFrom(indexedBytes);
	  Assert.assertEquals("ELF", transferred.getSymbol());

    nonIndexedBytes = Base64.decodeBase64(transactionResult.getLogs().get(1).getNonIndexed());
	  transferred = TokenContract.Transferred.getDefaultInstance().parseFrom(nonIndexedBytes);
	  Assert.assertEquals(1000000000, transferred.getAmount());
    Assert.assertEquals("transfer in test", transferred.getMemo());
  }

  @Test
  public void sendFailedTransactionTest() throws Exception {
    String tokenContractAddress = client.getContractAddressByName(privateKey, Sha256.getBytesSha256("AElf.ContractNames.Token"));
    KeyPairInfo keyPairInfo = client.generateKeyPairInfo();

    Client.Address to = AddressHelper.base58ToAddress(address);

    TokenContract.TransferInput.Builder paramTransfer = TokenContract.TransferInput.newBuilder();
    paramTransfer.setTo(to);
    paramTransfer.setSymbol("ELF");
    paramTransfer.setAmount(1000000000);
    paramTransfer.setMemo("transfer in test");
    TokenContract.TransferInput paramTransferObj = paramTransfer.build();

    String ownerAddress = client.getAddressFromPrivateKey(privateKey);

    Core.Transaction.Builder transactionTransfer = client.generateTransaction(keyPairInfo.getAddress(), tokenContractAddress, "Transfer", paramTransferObj.toByteArray());
    Core.Transaction transactionTransferObj = transactionTransfer.build();
    transactionTransfer.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(client.signTransaction(keyPairInfo.getPrivateKey(), transactionTransferObj))));
    transactionTransferObj = transactionTransfer.build();

    SendTransactionInput sendTransactionInputObj = new SendTransactionInput();
    sendTransactionInputObj.setRawTransaction(Hex.toHexString(transactionTransferObj.toByteArray()));
    SendTransactionOutput sendResult = client.sendTransaction(sendTransactionInputObj);
    Assert.assertFalse(sendResult.getTransactionId().isEmpty());

    Thread.sleep(4000);

    TransactionResultDto transactionResult = client.getTransactionResult(sendResult.getTransactionId());
    Assert.assertEquals("NODEVALIDATIONFAILED",transactionResult.getStatus());
    Assert.assertEquals("Pre-Error: Transaction fee not enough.",transactionResult.getError());
  }

  @Test
  public void sendTransactionsTest() throws Exception {
    List<String> transactions = new ArrayList<String>();
    for(int i=0;i<2;i++){
      KeyPairInfo keyPairInfo = client.generateKeyPairInfo();
      Core.Transaction transaction = createTransferTransaction(keyPairInfo.getAddress());
      String rawTransaction = Hex.toHexString(transaction.toByteArray());
      transactions.add(rawTransaction);
    }

    SendTransactionsInput sendTransactionsInputs = new SendTransactionsInput();
    sendTransactionsInputs.setRawTransactions(String.join(",",transactions));
    List<String> results = client.sendTransactions(sendTransactionsInputs);

    Thread.sleep(4000);

    for(int i=0;i<2;i++){
      TransactionResultDto transactionResult = client.getTransactionResult(results.get(i));
      Assert.assertEquals("MINED",transactionResult.getStatus());
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

    AElfClient wrongClient = new AElfClient("http://127.0.0.1:1111");
    Assert.assertFalse(wrongClient.isConnected());
  }

  @Test
  public void potobuffTest() throws Exception {
    String toAddress = client.getGenesisContractAddress();
    byte[] bytes = Sha256.getBytesSha256("AElf.ContractNames.Vote");
    String methodName = "GetContractAddressByName";
    Client.Hash.Builder hash = Client.Hash.newBuilder();
    hash.setValue(ByteString.copyFrom(bytes));
    Client.Hash hashObj = hash.build();
    Core.Transaction transactionObj = buildTransaction(toAddress, methodName, hashObj.toByteArray());
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

  private Core.Transaction createTransferTransaction(String toAddress) throws Exception {
    String tokenContractAddress = client.getContractAddressByName(privateKey, Sha256.getBytesSha256("AElf.ContractNames.Token"));

    Client.Address to = AddressHelper.base58ToAddress(toAddress);

    TokenContract.TransferInput.Builder paramTransfer = TokenContract.TransferInput.newBuilder();
    paramTransfer.setTo(to);
    paramTransfer.setSymbol("ELF");
    paramTransfer.setAmount(1000000000);
    paramTransfer.setMemo("transfer in test");
    TokenContract.TransferInput paramTransferObj = paramTransfer.build();

    String ownerAddress = client.getAddressFromPrivateKey(privateKey);

    Core.Transaction.Builder transactionTransfer = client.generateTransaction(ownerAddress, tokenContractAddress, "Transfer", paramTransferObj.toByteArray());
    Core.Transaction transactionTransferObj = transactionTransfer.build();
    transactionTransfer.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(client.signTransaction(privateKey, transactionTransferObj))));
    transactionTransferObj = transactionTransfer.build();

    return transactionTransferObj;
  }

  // private GetBalanceOutput getBalance(String ownerAddress){
  //   String tokenContractAddress = client.GetContractAddressByName(privateKey, "AElf.ContractNames.Token");

  //   Client.Address.Builder owner = Client.Address.newBuilder();
  //   owner.setValue(ByteString.copyFrom(Base58.decodeChecked(ownerAddress)));
  //   Client.Address ownerObj = owner.build();

  //   GetBalanceInput.Builder paramGetBalance = GetBalanceInput.newBuilder();
  //   paramGetBalance.setSymbol("ELF");
  //   paramGetBalance.setOwner(ownerObj);
  //   GetBalanceInput paramGetBalanceObj = paramGetBalance.build();

  //   Core.Transaction.Builder transactionGetBalance = client.generateTransaction(ownerAddress, tokenContractAddress, "GetBalance", paramGetBalanceObj.toByteArray());
  //   Core.Transaction transactionGetBalanceObj = transactionGetBalance.build();
  //   String signature = client.signTransaction(privateKey, transactionGetBalanceObj);
  //   transactionGetBalance.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
  //   transactionGetBalanceObj = transactionGetBalance.build();

  //   ExecuteTransactionDto executeTransactionDto = new ExecuteTransactionDto();
  //   executeTransactionDto.setRawTransaction(Hex.toHexString(transactionGetBalanceObj.toByteArray()));
  //   String transactionGetBalanceResult = client.executeTransaction(executeTransactionDto);

  //   GetBalanceOutput balance = GetBalanceOutput.getDefaultInstance().parseFrom(ByteArrayHelper.hexToByteArray(transactionGetBalanceResult));
  //   return balance;
  // }
}
