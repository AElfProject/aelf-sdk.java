package io.aelf.sdk;

import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;
import com.google.protobuf.StringValue;
import io.aelf.contract.GlobalContract;
import io.aelf.contract.IContractBehaviour;
import io.aelf.network.interceptor.CommonHeaderInterceptor;
import io.aelf.network.RetrofitFactory;
import io.aelf.protobuf.generated.Client;
import io.aelf.protobuf.generated.Core;
import io.aelf.response.ResultCode;
import io.aelf.schemas.*;
import io.aelf.internal.sdkv2.AElfClientV2;
import io.aelf.utils.*;
import org.apache.http.util.TextUtils;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import retrofit2.Retrofit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * @deprecated - This class is deprecated, and we recommend you to use the
 * {@link AElfClientV2}
 * which contains both internal APIs and common APIs.
 */
@SuppressWarnings({"UnusedReturnValue", "DeprecatedIsStillUsed"})
public class AElfClient implements IContractBehaviour {
    private BlockChainSdk blockchainSdk;
    private NetSdk netSdk;

    /**
     * Init AElfClient only with the peer's URL.
     *
     * @param url peer's URL
     */
    public AElfClient(String url) {
        this(url, null);
    }

    /**
     * Init AElfClient with peer's URL and version.
     *
     * @param url     peer's URL
     * @param version used for network connection, default is "1.0"
     */
    public AElfClient(String url, String version) {
        this(url, version, null, null);
    }

    /**
     * Init AElfClient with peer's auth information,
     * this will allow you to get access to some operations, such as
     * {@link NetSdk#addPeer(AddPeerInput)} and {@link NetSdk#removePeer(String)}.
     *
     * @param url      peer's URL
     * @param username peer's username
     * @param password peer's password
     */
    public AElfClient(String url, String username, String password) {
        this(url, null, username, password);
    }


    /**
     * Init AElfClient with those params.
     *
     * @param url      peer's URL
     * @param version  used for network connection, default is "1.0"
     * @param username peer's username
     * @param password peer's password
     */
    public AElfClient(@Nullable String url, @Nullable String version, @Nullable String username,
                      @Nullable String password) {
        this(url, version, username, password, null);
    }

    /**
     * Init AElfClient with all the required params.
     *
     * @param url      peer's URL
     * @param version  used for network connection, default is "1.0"
     * @param username peer's username
     * @param password peer's password
     * @param retrofit you can provide a retrofit object to replace the default one;
     *                 if you wish to not change the base url set outside,
     *                 please provide a blank url parameter.
     */
    public AElfClient(@Nullable String url, @Nullable String version, @Nullable String username,
                      @Nullable String password, @Nullable Retrofit.Builder retrofit) {
        this.initBlockChainConfig();
        this.initNetSdkConfig(username, password);
        RetrofitFactory.init(url, retrofit);
        initBasicConfig(version);
    }

    private void initBasicConfig(String version) {
        CommonHeaderInterceptor.updateEntireContentType(null, null, version);
    }

    private void initBlockChainConfig() {
        blockchainSdk = new BlockChainSdk();
    }

    private BlockChainSdk getBlockChainConfig() {
        return blockchainSdk;
    }

    private void initNetSdkConfig(String userName, String password) {
        netSdk = new NetSdk(userName, password);
    }

    private NetSdk getNetSdkConfig() {
        return netSdk;
    }

    /**
     * Get the height of the current chain.
     *
     * @return chain's height, value that below zero means failure
     */
    @AElfUrl(url = "wa://api/blockChain/blockHeight")
    public long getBlockHeight() throws Exception {
        return this.getBlockChainConfig().getBlockHeight();
    }

    /**
     * Get information of a block by given block hash.
     *
     * @return {@link BlockDto} block information
     */
    public BlockDto getBlockByHash(String blockHash) throws Exception {
        return this.getBlockByHash(blockHash, false);
    }

    /**
     * Get information about a given block by block hash,
     * optionally with the list of its transactions or not.
     *
     * @return {@link BlockDto} block information
     */
    @AElfUrl(url = "wa://api/blockChain/block?includeTransactions={includeTransactions}")
    public BlockDto getBlockByHash(String blockHash, boolean includeTransactions) throws Exception {
        return this.getBlockChainConfig().getBlockByHash(blockHash, includeTransactions);
    }

    /**
     * Get information of a block by specified height.
     *
     * @param blockHeight block height
     * @return {@link BlockDto} block information
     */
    public BlockDto getBlockByHeight(long blockHeight) throws Exception {
        return this.getBlockByHeight(blockHeight, false);
    }

    /**
     * Get information of a block by specified height,
     * optionally whether to include transaction information or not.
     *
     * @param blockHeight         block height
     * @param includeTransactions whether to include transaction information or not
     * @return {@link BlockDto} block information
     */
    @AElfUrl(url = "wa://api/blockChain/blockByHeight?includeTransactions={includeTransactions}")
    public BlockDto getBlockByHeight(long blockHeight, boolean includeTransactions) throws Exception {
        return this.getBlockChainConfig().getBlockByHeight(blockHeight, includeTransactions);
    }

    /**
     * Get the current status of the blockchain.
     *
     * @return {@link ChainstatusDto} chain status
     */
    @AElfUrl(url = "wa://api/blockChain/chainStatus")
    public ChainstatusDto getChainStatus() throws IOException {
        return this.getBlockChainConfig().getChainStatus();
    }

    /**
     * Get the protobuf definitions related to a contract.
     *
     * @param address contract address
     * @return byte[] protobuf definitions
     */
    @AElfUrl(url = "wa://api/blockChain/contractFileDescriptorSet?address={address}")
    public byte[] getContractFileDescriptorSet(String address) throws Exception {
        return this.getBlockChainConfig().getContractFileDescriptorSet(address);
    }

    /**
     * Get the status information of the peer's task queue.
     *
     * @return {@link TaskQueueInfoDto} task queue information
     */
    @AElfUrl(url = "wa://api/blockChain/taskQueueStatus")
    public List<TaskQueueInfoDto> getTaskQueueStatus() throws Exception {
        return this.getBlockChainConfig().getTaskQueueStatus();
    }

    /**
     * Gets information about the current transaction
     * pool.wa:/api/blockChain/transactionPoolStatus
     */
    public TransactionPoolStatusOutput getTransactionPoolStatus() throws Exception {
        return this.getBlockChainConfig().getTransactionPoolStatus();
    }

    /**
     * Call a read-only method of a contract.
     *
     * @param input {@link TransactionWrapper} input
     * @return {@link String} contract's output
     */
    @AElfUrl(url = "wa://api/blockChain/executeTransaction")
    public String executeTransaction(TransactionWrapper input) throws Exception {
        return this.getBlockChainConfig().executeTransaction(input);
    }

    /**
     * Creates an unsigned serialized transaction for the caller to use later.
     *
     * @param input {@link CreateRawTransactionInput} input
     * @return {@link CreateRawTransactionOutput} unsigned serialized transaction
     */
    @AElfUrl(url = "wa://api/blockChain/rawTransaction")
    public CreateRawTransactionOutput createRawTransaction(CreateRawTransactionInput input)
            throws Exception {
        return this.getBlockChainConfig().createRawTransaction(input);
    }

    /**
     * Call a method of a contract by given serialized transaction info.
     *
     * @param input {@link ExecuteRawTransactionDto} input
     * @return {@link String} contract's output
     */
    @AElfUrl(url = "wa://api/blockChain/executeRawTransaction")
    public String executeRawTransaction(ExecuteRawTransactionDto input) throws Exception {
        return this.getBlockChainConfig().executeRawTransaction(input);
    }

    /**
     * Broadcast a raw-serialized transaction to the blockchain network.
     *
     * @param input {@link SendRawTransactionInput} input
     * @return {@link SendRawTransactionOutput} transaction id
     */
    @AElfUrl(url = "wa://api/blockChain/sendRawTransaction")
    public SendRawTransactionOutput sendRawTransaction(SendRawTransactionInput input)
            throws Exception {
        return this.getBlockChainConfig().sendRawTransaction(input);
    }

    /**
     * Broadcast a transaction to the blockchain network.
     *
     * @param input {@link SendTransactionInput} input
     * @return {@link SendTransactionOutput} transaction id
     */
    @AElfUrl(url = "wa://api/blockChain/sendTransaction")
    public SendTransactionOutput sendTransaction(SendTransactionInput input) throws Exception {
        return this.getBlockChainConfig().sendTransaction(input);
    }

    /**
     * Broadcast volume transactions.
     *
     * @param input {@link SendTransactionsInput} input that contains many
     *              transactions
     * @return {@link List} transaction ids
     */
    @AElfUrl(url = "wa://api/blockChain/sendTransactions")
    public List<String> sendTransactions(SendTransactionsInput input) throws Exception {
        return this.getBlockChainConfig().sendTransactions(input);
    }

    /**
     * Get the current status of a transaction.
     *
     * @param transactionId transaction id
     * @return {@link TransactionResultDto} transaction status
     */
    @AElfUrl(url = "wa://api/blockChain/transactionResult")
    public TransactionResultDto getTransactionResult(String transactionId) throws Exception {
        return this.getBlockChainConfig().getTransactionResult(transactionId);
    }

    /**
     * Get the results of multiple transactions.
     * wa:/api/blockChain/transactionResults
     */
    @AElfUrl(url = "wa://api/blockChain/transactionResults?blockHash={blockHash}")
    public List<TransactionResultDto> getTransactionResults(String blockHash) throws Exception {
        return this.getTransactionResults(blockHash, 0, 10);
    }

    /**
     * Get multiple transaction results by specified blockHash and the offset.
     *
     * @param blockHash block hash
     * @param offset    offset config
     * @param limit     limit config
     * @return {@link TransactionResultDto} transaction results
     */
    @AElfUrl(url = "wa://api/blockChain/transactionResults?blockHash={blockHash}&offset={offset}&limit={limit}")
    public List<TransactionResultDto> getTransactionResults(String blockHash, int offset, int limit) throws Exception {
        return this.getBlockChainConfig().getTransactionResults(blockHash, offset, limit);
    }

    /**
     * Get merkle tree's path of a transaction.
     *
     * @param transactionId transaction id
     * @return {@link MerklePathDto} merkle tree's path
     */
    @AElfUrl(url = "wa://api/blockChain/merklePathByTransactionId?transactionId={transactionId}")
    public MerklePathDto getMerklePathByTransactionId(String transactionId) throws Exception {
        return this.getBlockChainConfig().getMerklePathByTransactionId(transactionId);
    }

    /**
     * Get id of the chain.
     *
     * @return chain id
     */
    public int getChainId() throws Exception {
        return this.getBlockChainConfig().getChainId();
    }

    /**
     * Attempts to add a node to the connected network nodes.
     * <p>
     * Attention: if you wish to call this method, you should have provided the
     * username and password in the constructor of {@link AElfClient}.
     */
    @AElfUrl(url = "wa://api/net/peer")
    public Boolean addPeer(AddPeerInput input) throws Exception {
        return this.getNetSdkConfig().addPeer(input);
    }

    /**
     * Attempts to remove a node from the connected network nodes.
     * <p>
     * Attention: if you wish to call this method, you should have provided the
     * username and password in the constructor of {@link AElfClient}.
     *
     * @param address peer's address
     */
    @AElfUrl(url = "wa://api/net/peer?address={address}")
    public Boolean removePeer(String address) throws Exception {
        return this.getNetSdkConfig().removePeer(address);
    }

    /**
     * Gets information about the peer nodes of the current node.
     * <p>
     * Optional: whether to include metrics.
     *
     * @param withMetrics whether to include metrics
     * @return {@link PeerDto} peer nodes
     */
    @AElfUrl(url = "wa://api/net/peers?withMetrics={withMetrics}")
    public List<PeerDto> getPeers(Boolean withMetrics) throws Exception {
        return this.getNetSdkConfig().getPeers(withMetrics);
    }

    /**
     * Get information about the node’s connection to the network.
     *
     * @return {@link NetworkInfoOutput} network info
     */
    @AElfUrl(url = "wa://api/net/networkInfo")
    public NetworkInfoOutput getNetworkInfo() throws Exception {
        return this.getNetSdkConfig().getNetworkInfo();
    }

    /**
     * Build a transaction from the input parameters.
     *
     * @param from       from address(sender)
     * @param to         to address(receiver)
     * @param methodName method name
     * @param params     params
     */
    public Core.Transaction.Builder generateTransaction(String from, String to, String methodName,
                                                        byte[] params) throws IOException {
        final ChainstatusDto chainStatus = this.getBlockChainConfig().getChainStatus();
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
     * Attention: the transaction should be built by {@link #generateTransaction}.
     *
     * @param privateKeyHex private key hex
     * @param transaction   transaction that will be signed
     * @return signature string
     */
    public String signTransaction(String privateKeyHex, Core.Transaction transaction) {
        byte[] transactionData = Sha256.getBytesSha256(transaction.toByteArray());
        return this.getSignatureWithPrivateKey(privateKeyHex, transactionData);
    }

    /**
     * Get the address of the genesis contract.
     *
     * @return genesis contract address
     */
    public String getGenesisContractAddress() throws IOException {
        ChainstatusDto chainstatusDto = this.getBlockChainConfig().getChainStatus();
        return chainstatusDto.getGenesisContractAddress();
    }

    /**
     * Get the account address through the public key.
     *
     * @param pubKey pubKey hex
     * @return account address
     */
    public String getAddressFromPubKey(@Nonnull String pubKey) {
        byte[] publicKey = ByteArrayHelper.hexToByteArray(pubKey);
        byte[] hashTwice = Sha256Hash.hashTwice(publicKey);
        return Base58Ext.encodeChecked(hashTwice);
    }

    /**
     * Convert the primal address to the formatted version.
     * <p>
     * String：symbol_base58-string_base58-string-chain-id.
     *
     * @param address    primal address
     * @param privateKey private key to sign
     */
    public String getFormattedAddress(String privateKey, String address) throws Exception {
        String chainIdString = this.getBlockChainConfig().getChainStatus().getChainId();
        String symbol = callContractMethod(
                GlobalContract.tokenContract.name,
                GlobalContract.tokenContract.method_getPrimaryTokenSymbol,
                privateKey,
                true
        );
        return symbol + "_" + address + "_" + chainIdString;
    }

    /**
     * generate key pair info that contains both the public key and the private key.
     *
     * @return {@link KeyPairInfo} key pair info
     */
    public KeyPairInfo generateKeyPairInfo() {
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
     * Get the address of a contract by given contractNameHash.
     *
     * @param privateKey       private key to sign
     * @param contractNameHash contract name hash
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
        String response = this.blockchainSdk.executeTransaction(executeTransactionDto);
        byte[] byteArray = ByteArrayHelper.hexToByteArray(response);
        return Base58Ext.encodeChecked(
                Client.Address.parseFrom(byteArray).getValue().toByteArray());
    }

    /**
     * See its overload method
     * {@link AElfClient#callContractMethod(String, String, String, boolean, String)}
     * for more information.
     */
    @Override
    public String callContractMethod(@Nonnull String contractName, @Nonnull String methodName,
                                     @Nonnull String privateKey, boolean isViewMethod) throws AElfException {
        return callContractMethod(contractName, methodName, privateKey, isViewMethod, "");
    }

    /**
     * See its overload method
     * {@link AElfClient#callContractMethod(String, String, String, boolean, String)}
     * for more information.
     */
    @Override
    public String callContractMethod(@Nonnull String contractName, @Nonnull String methodName,
                                     @Nonnull String privateKey, boolean isViewMethod, @NotNull JsonObject optionalParams) throws AElfException {
        return callContractMethod(
                contractName,
                methodName,
                privateKey,
                isViewMethod,
                optionalParams.toString()
        );
    }


    /**
     * Call a VIEW/SEND method in the particular contract
     * on the blockchain network, and get the result.
     * <p>
     * Actually, when you are trying to execute a transaction, in fact, you
     * are calling the AElf.ContractNames.Token contract's transfer method
     * to execute this token transaction (ELF token or others).
     * <p>
     * Things go on a similar way when you are trying to call a contract's
     * method, whether it is a VIEW method or a SEND method.
     * <p>
     * When calling this method, a {@link TransactionDto} object will be sent
     * to the peer, it contains information that will be used when calling
     * a contract method.
     *
     * @param contractName   name of the contract that contains target method
     * @param methodName     method name that is about to call
     * @param privateKey     since calling a contract method is actually
     *                       sending a transaction to the blockchain system,
     *                       privateKey is needed for sign
     * @param isViewMethod   whether this method is a VIEW method, calling VIEW
     *                       methods will have no consequences and side effects,
     *                       they will only provide a result and have no effect
     *                       on the contract's State.The other side, SEND methods,
     *                       will change its contract's State.
     * @param optionalParams params that need to be sent to
     * @return {@link String} method result
     * @throws AElfException when issues happen
     */
    @Override

    public String callContractMethod(@Nonnull String contractName, @Nonnull String methodName,
                                     @Nonnull String privateKey, boolean isViewMethod,
                                     @Nullable String optionalParams) throws AElfException {
        try {
            String fromAddress = this.getAddressFromPrivateKey(privateKey);
            String toAddress = this
                    .getContractAddressByName(privateKey, Sha256.getBytesSha256(contractName));
            Core.Transaction.Builder transaction = this.generateTransaction(
                    fromAddress,
                    toAddress,
                    methodName,
                    TextUtils.isBlank(optionalParams)
                            ? new byte[0]
                            : optionalParams.getBytes()
            );
            String signature = this.signTransaction(privateKey, transaction.build());
            transaction.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
            Core.Transaction transactionObj = transaction.build();
            TransactionWrapper executeTransactionDto = new ExecuteTransactionDto();
            executeTransactionDto.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
            String response = isViewMethod
                    ? this.blockchainSdk.executeTransaction(executeTransactionDto)
                    : JsonUtil.toJsonString(this.blockchainSdk.sendTransaction(executeTransactionDto));
            return StringValue.parseFrom(ByteArrayHelper.hexToByteArray(response)).getValue();
        } catch (IOException e) {
            throw new AElfException(ResultCode.NETWORK_DISCONNECTED);
        } catch (Exception e) {
            throw new AElfException(e);
        }
    }

    /**
     * Get the address from a private key.
     *
     * @param privateKey private key hex
     * @return address string
     */
    public String getAddressFromPrivateKey(String privateKey) {
        org.bitcoinj.core.ECKey aelfKey = org.bitcoinj.core.ECKey
                .fromPrivate(new BigInteger(privateKey, 16)).decompress();
        byte[] publicKey = aelfKey.getPubKey();
        byte[] hashTwice = Sha256Hash.hashTwice(publicKey);
        return Base58Ext.encodeChecked(hashTwice);
    }

    /**
     * Get the sha256 signature of data with privateKey string.
     *
     * @param privateKey private key hex
     * @param txData     data to be signed
     * @return signature string
     */
    public String getSignatureWithPrivateKey(String privateKey, byte[] txData) {
        BigInteger key = new BigInteger(privateKey, 16);
        BigInteger pubKey = Sign.publicKeyFromPrivate(key);
        ECKeyPair keyPair = new ECKeyPair(key, pubKey);
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
            this.getBlockChainConfig().getChainStatus();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * calculate the transactionFee.
     *
     * @param input {@link CalculateTransactionFeeInput} input
     * @return {@link CalculateTransactionFeeOutput} output
     */
    @AElfUrl(url = "/api/blockChain/calculateTransactionFee")
    public CalculateTransactionFeeOutput calculateTransactionFee(CalculateTransactionFeeInput input) throws Exception {
        return this.getBlockChainConfig().calculateTransactionFee(input);
    }

}
