package io.aelf.sdk;

import com.google.protobuf.ByteString;
import com.google.protobuf.StringValue;
import io.aelf.protobuf.generated.Client;
import io.aelf.protobuf.generated.Core;
import io.aelf.schemas.AddPeerInput;
import io.aelf.schemas.BlockDto;
import io.aelf.schemas.ChainstatusDto;
import io.aelf.schemas.CreateRawTransactionInput;
import io.aelf.schemas.CreateRawTransactionOutput;
import io.aelf.schemas.ExecuteRawTransactionDto;
import io.aelf.schemas.ExecuteTransactionDto;
import io.aelf.schemas.KeyPairInfo;
import io.aelf.schemas.MerklePathDto;
import io.aelf.schemas.NetworkInfoOutput;
import io.aelf.schemas.PeerDto;
import io.aelf.schemas.SendRawTransactionInput;
import io.aelf.schemas.SendRawTransactionOutput;
import io.aelf.schemas.SendTransactionInput;
import io.aelf.schemas.SendTransactionOutput;
import io.aelf.schemas.SendTransactionsInput;
import io.aelf.schemas.TaskQueueInfoDto;
import io.aelf.schemas.TransactionPoolStatusOutput;
import io.aelf.schemas.TransactionResultDto;
import io.aelf.utils.Base58Ext;
import io.aelf.utils.ByteArrayHelper;
import io.aelf.utils.JsonUtil;
import io.aelf.utils.Sha256;
import io.aelf.utils.StringUtil;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;

public class AElfClient {

  private String AElfClientUrl;
  private String version = "1.0";
  private String UserName;
  private String Password;
  private BlockChainSdk blcokChainSdk;
  private NetSdk netSdk;


  /**
   * Object construction through the url path.
   *
   * @param url Http Request Url exp:(http://xxxx)
   */
  public AElfClient(String url) {
    this.AElfClientUrl = url;
    this.getBlockChainSdkObj();
    this.getNetSdkObj();

  }

  /**
   * Object dconstruction through the url path and basic auth.
   * @param url
   * @param userName
   * @param password
   */
  public AElfClient(String url, String userName, String password) {
    this.AElfClientUrl = url;
    this.UserName = userName;
    this.Password = password;
    this.getBlockChainSdkObj();
    this.getNetSdkObj();
  }

  /**
   * Object construction through the url path.
   *
   * @param url Http Request Url exp:(http://xxxx)
   * @param version application/json;v={version}
   */
  public AElfClient(String url, String version) {
    this.AElfClientUrl = url;
    this.version = version;
    this.getBlockChainSdkObj();
    this.getNetSdkObj();
  }

  /**
   * Object dconstruction through the url path and basic auth.
   * @param url
   * @param version
   * @param userName
   * @param password
   */
  public AElfClient(String url, String version, String userName, String password) {
    this.AElfClientUrl = url;
    this.version = version;
    this.UserName = userName;
    this.Password = password;
    this.getBlockChainSdkObj();
    this.getNetSdkObj();
  }

  private AElfClient() {

  }

  /**
   * Get the instance object of BlcokChainSdk.
   *
   * @return BlockChainSdk Object ins
   */
  private BlockChainSdk getBlockChainSdkObj() {
    if (blcokChainSdk == null) {
      blcokChainSdk = new BlockChainSdk(this.AElfClientUrl, this.version);
    }

    return blcokChainSdk;
  }

  /**
   * Get the instance object of getNetSdkObj.
   */
  private NetSdk getNetSdkObj() {
    if (netSdk == null) {
      netSdk = new NetSdk(this.AElfClientUrl, this.version, this.UserName, this.Password);
    }
    return netSdk;
  }

  /**
   * Get the height of the current chain. wa:/api/blockChain/blockHeight
   */
  public long getBlockHeight() throws Exception {
    return this.getBlockChainSdkObj().getBlockHeight();
  }

  /**
   * Get information of a block by given block hash. Optional whether to include transaction
   * information.
   */
  public BlockDto getBlockByHash(String blockHash) throws Exception {
    return this.getBlockByHash(blockHash, false);
  }

  /**
   * Get information about a given block by block hash. Otionally with the list of its transactions.
   * wa://api/blockChain/block?includeTransactions={includeTransactions}
   */
  public BlockDto getBlockByHash(String blockHash, boolean includeTransactions) throws Exception {
    return this.getBlockChainSdkObj().getBlockByHash(blockHash, includeTransactions);
  }

  /**
   * Get information of a block by specified height. Optional whether to include transaction
   * information.
   */
  public BlockDto getBlockByHeight(long blockHeight) throws Exception {
    return this.getBlockByHeight(blockHeight, false);
  }

  /**
   * Get information of a block by specified height. Optional whether to include transaction
   * information. wa://api/blockChain/blockByHeight?includeTransactions={includeTransactions}
   */
  public BlockDto getBlockByHeight(long blockHeight, boolean includeTransactions) throws Exception {
    return this.getBlockChainSdkObj().getBlockByHeight(blockHeight, includeTransactions);
  }

  /**
   * Get the current status of the block chain. wa:/api/blockChain/chainStatus
   */
  public ChainstatusDto getChainStatus() throws Exception {
    return this.getBlockChainSdkObj().getChainStatus();
  }

  /**
   * Get the protobuf definitions related to a contract /api/blockChain/contractFileDescriptorSet.
   */
  public byte[] getContractFileDescriptorSet(String address) throws Exception {
    return this.getBlockChainSdkObj().getContractFileDescriptorSet(address);
  }

  /**
   * Gets the status information of the task queue wa:/api/blockChain/taskQueueStatus.
   */
  public List<TaskQueueInfoDto> getTaskQueueStatus() throws Exception {
    return this.getBlockChainSdkObj().getTaskQueueStatus();
  }

  /**
   * Gets information about the current transaction pool.wa:/api/blockChain/transactionPoolStatus
   */
  public TransactionPoolStatusOutput getTransactionPoolStatus() throws Exception {
    return this.getBlockChainSdkObj().getTransactionPoolStatus();
  }

  /**
   * Call a read-only method of a contract. wa:/api/blockChain/executeTransaction
   */
  public String executeTransaction(ExecuteTransactionDto input) throws Exception {
    return this.getBlockChainSdkObj().executeTransaction(input);
  }

  /**
   * Creates an unsigned serialized transaction wa:/api/blockChain/rawTransaction.
   */
  public CreateRawTransactionOutput createRawTransaction(CreateRawTransactionInput input)
      throws Exception {
    return this.getBlockChainSdkObj().createRawTransaction(input);
  }

  /**
   * Call a method of a contract by given serialized str wa:/api/blockChain/executeRawTransaction.
   */
  public String executeRawTransaction(ExecuteRawTransactionDto input) throws Exception {
    return this.getBlockChainSdkObj().executeRawTransaction(input);
  }

  /**
   * Broadcast a serialized transaction. wa:/api/blockChain/sendRawTransaction
   */
  public SendRawTransactionOutput sendRawTransaction(SendRawTransactionInput input)
      throws Exception {
    return this.getBlockChainSdkObj().sendRawTransaction(input);
  }

  /**
   * Broadcast a transaction wa:/api/blockChain/sendTransaction.
   */
  public SendTransactionOutput sendTransaction(SendTransactionInput input) throws Exception {
    return this.getBlockChainSdkObj().sendTransaction(input);
  }

  /**
   * Broadcast volume transactions wa:/api/blockChain/sendTransactions.
   */
  public List<String> sendTransactions(SendTransactionsInput input) throws Exception {
    return this.getBlockChainSdkObj().sendTransactions(input);
  }

  /**
   * Get the current status of a transaction wa:/api/blockChain/transactionResult.
   */
  public TransactionResultDto getTransactionResult(String transactionId) throws Exception {
    return this.getBlockChainSdkObj().getTransactionResult(transactionId);
  }

  /**
   * Get results of multiple transactions by specified blockHash and the offset.
   * wa:/api/blockChain/transactionResults
   */
  public List<TransactionResultDto> getTransactionResults(String blockHash) throws Exception {
    return this.getTransactionResults(blockHash, 0, 10);
  }

  /**
   * Get multiple transaction results. wa:/api/blockChain/transactionResults
   */
  public List<TransactionResultDto> getTransactionResults(String blockHash, int offset, int limit)
      throws Exception {
    return this.getBlockChainSdkObj().getTransactionResults(blockHash, offset, limit);
  }

  /**
   * Get merkle path of a transaction. wa:/api/blockChain/merklePathByTransactionId
   */
  public MerklePathDto getMerklePathByTransactionId(String transactionId) {
    return this.getBlockChainSdkObj().getMerklePathByTransactionId(transactionId);
  }

  /**
   * Get id of the chain.
   */
  public int getChainId() throws Exception {
    return this.getBlockChainSdkObj().getChainId();
  }

  /**
   * Attempts to add a node to the connected network nodes wa:/api/net/peer.
   */
  public Boolean addPeer(AddPeerInput input) throws Exception {
    return this.getNetSdkObj().addPeer(input);
  }

  /**
   * Attempts to remove a node from the connected network nodes wa:/api/net/peer.
   */
  public Boolean removePeer(String address) throws Exception {
    return this.getNetSdkObj().removePeer(address);
  }

  /**
   * Gets information about the peer nodes of the current node.Optional whether to include metrics.
   * wa:/api/net/peers?withMetrics=false
   */
  public List<PeerDto> getPeers(Boolean withMetrics) throws Exception {
    return this.getNetSdkObj().getPeers(withMetrics);
  }

  /**
   * Get information about the node’s connection to the network. wa:/api/net/networkInfo
   */
  public NetworkInfoOutput getNetworkInfo() throws Exception {
    return this.getNetSdkObj().getNetworkInfo();
  }

  /**
   * Build a transaction from the input parameters.
   */
  public Core.Transaction.Builder generateTransaction(String from, String to, String methodName,
      byte[] params) throws Exception {
    final ChainstatusDto chainStatus = this.getBlockChainSdkObj().getChainStatus();
    final Core.Transaction.Builder transaction = Core.Transaction.newBuilder();
    Client.Address.Builder addressForm = Client.Address.newBuilder();
    Client.Address.Builder addressTo = Client.Address.newBuilder();
    addressForm.setValue(ByteString.copyFrom(Base58.decodeChecked(from)));
    addressTo.setValue(ByteString.copyFrom(Base58.decodeChecked(to)));
    Client.Address addressFormObj = addressForm.build();
    Client.Address addressToObj = addressTo.build();
    transaction.setFrom(addressFormObj);
    transaction.setTo(addressToObj);
    transaction.setMethodName(methodName);
    transaction.setParams(ByteString.copyFrom(params));
    transaction.setRefBlockNumber(chainStatus.getBestChainHeight());
    byte[] refBlockPrefix = ByteArrayHelper.hexToByteArray(chainStatus.getBestChainHash());
    refBlockPrefix = Arrays.copyOf(refBlockPrefix, 4);
    transaction.setRefBlockPrefix(ByteString.copyFrom(refBlockPrefix));
    return transaction;
  }

  /**
   * Sign a transaction using private key.
   */
  public String signTransaction(String privateKeyHex, Core.Transaction transaction)
      throws Exception {
    byte[] transactionData = Sha256.getBytesSha256(transaction.toByteArray());
    return this.getSignatureWithPrivateKey(privateKeyHex, transactionData);
  }

  /**
   * Get the address of genesis contract.
   *
   * @return address
   */
  public String getGenesisContractAddress() throws Exception {
    ChainstatusDto chainstatusDto = this.getBlockChainSdkObj().getChainStatus();
    return chainstatusDto.getGenesisContractAddress();
  }

  /**
   * Get the account address through the public key.
   *
   * @param pubKey pubKey hex
   * @return Str
   */
  public String getAddressFromPubKey(@Nullable String pubKey) {
    byte[] publicKey = ByteArrayHelper.hexToByteArray(pubKey);
    byte[] hashTwice = Sha256Hash.hashTwice(publicKey);
    String address = Base58Ext.encodeChecked(hashTwice);
    return address;
  }

  /**
   * Convert the Address to the displayed string：symbol_base58-string_base58-string-chain-id.
   */
  public String getFormattedAddress(String privateKey, String address) throws Exception {
    String chainIdString = this.getBlockChainSdkObj().getChainStatus().getChainId();
    String fromAddress = this.getAddressFromPrivateKey(privateKey);
    String toAddress = this
        .getContractAddressByName(privateKey, Sha256.getBytesSha256("AElf.ContractNames.Token"));
    String methodName = "GetPrimaryTokenSymbol";
    byte[] bytes = new byte[0];
    Core.Transaction.Builder transaction = this
        .generateTransaction(fromAddress, toAddress, methodName, bytes);
    String signature = this.signTransaction(privateKey, transaction.build());
    transaction.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
    Core.Transaction transactionObj = transaction.build();
    ExecuteTransactionDto executeTransactionDto = new ExecuteTransactionDto();
    executeTransactionDto.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
    String response = this.blcokChainSdk.executeTransaction(executeTransactionDto);
    StringValue symbol = StringValue.parseFrom(ByteArrayHelper.hexToByteArray(response));
    return symbol.getValue() + "_" + address + "_" + chainIdString;
  }

  /**
   * new generateKeyPairInfo;
   */
  public KeyPairInfo generateKeyPairInfo()
      throws Exception {
    ECKey keyPair = new ECKey();
    String privateKey = keyPair.getPrivateKeyAsHex();
    String publicKey = keyPair.getPublicKeyAsHex();
    String address = getAddressFromPrivateKey(privateKey);
    KeyPairInfo keyPairInfo = new KeyPairInfo();
    keyPairInfo.setPrivateKey(privateKey);
    keyPairInfo.setPublicKey(publicKey);
    keyPairInfo.setAddress(address);
    return keyPairInfo;
  }

  /**
   * Get address of a contract by given contractNameHash.
   */
  public String getContractAddressByName(String privateKey, byte[] contractNameHash)
      throws Exception {
    String from = this.getAddressFromPrivateKey(privateKey);
    String to = this.getGenesisContractAddress();
    String methodName = "GetContractAddressByName";
    Client.Hash.Builder hash = Client.Hash.newBuilder();
    hash.setValue(ByteString.copyFrom(contractNameHash));
    Client.Hash hashObj = hash.build();
    Core.Transaction.Builder transaction = this
        .generateTransaction(from, to, methodName, hashObj.toByteArray());
    String signature = this.signTransaction(privateKey, transaction.build());
    transaction.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
    Core.Transaction transactionObj = transaction.build();
    ExecuteTransactionDto executeTransactionDto = new ExecuteTransactionDto();
    executeTransactionDto.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
    String response = this.blcokChainSdk.executeTransaction(executeTransactionDto);
    byte[] byteArray = ByteArrayHelper.hexToByteArray(response);
    String base58Str = Base58Ext.encodeChecked(
        Client.Address.getDefaultInstance().parseFrom(byteArray).getValue().toByteArray());
    return base58Str;
  }

  /**
   * Get address of a contract by given contractNameHash.
   */
  public String getAddressFromPrivateKey(String privateKey) {
    org.bitcoinj.core.ECKey aelfKey = org.bitcoinj.core.ECKey
        .fromPrivate(new BigInteger(privateKey, 16)).decompress();
    byte[] publicKey = aelfKey.getPubKey();
    byte[] hashTwice = Sha256Hash.hashTwice(publicKey);
    String address = Base58Ext.encodeChecked(hashTwice);
    return address;
  }

  /**
   * Get the private sha256 signature.
   */
  public String getSignatureWithPrivateKey(String privateKey, byte[] txData) throws Exception {
    BigInteger privKey = new BigInteger(privateKey, 16);
    BigInteger pubKey = Sign.publicKeyFromPrivate(privKey);
    ECKeyPair keyPair = new ECKeyPair(privKey, pubKey);
    Sign.SignatureData signature = Sign.signMessage(txData, keyPair, false);
    String signatureStr = Hex.toHexString(signature.getR()) + Hex.toHexString(signature.getS());
    String res = StringUtil.toString(signature.getV() - 27);
    if (res.length() == 1) {
      res = "0" + res;
    }
    signatureStr = signatureStr + (res);
    return signatureStr;
  }

  /**
   * Verify whether this sdk successfully connects the chain.
   *
   * @return IsConnected or not
   */
  public boolean isConnected() {
    try {
      this.getBlockChainSdkObj().getChainStatus();
      return true;
    } catch (Exception ex) {
      return false;
    }
  }
}
