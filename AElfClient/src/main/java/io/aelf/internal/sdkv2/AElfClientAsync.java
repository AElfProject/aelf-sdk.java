package io.aelf.internal.sdkv2;

import io.aelf.internal.*;
import io.aelf.schemas.*;
import io.aelf.sdk.AElfClient;
import io.aelf.sdk.NetSdk;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;

import javax.annotation.Nullable;
import java.util.List;

import static io.aelf.internal.AsyncCaller.convertFunction;

@SuppressWarnings({"unused", "deprecation"})
public abstract class AElfClientAsync extends AElfClient {
    @NotNull
    private final AsyncCaller caller;

    /**
     * Init AElfClient only with the peer's URL.
     *
     * @param url peer's URL
     */
    public AElfClientAsync(String url) {
        this(url, null);
    }

    /**
     * Init AElfClient with peer's URL and version.
     *
     * @param url     peer's URL
     * @param version used for network connection, default is "1.0"
     */
    public AElfClientAsync(String url, String version) {
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
    public AElfClientAsync(String url, String username, String password) {
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
    public AElfClientAsync(@Nullable String url, @Nullable String version,
                           @Nullable String username, @Nullable String password) {
        this(url, version, username, password, null, null);
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
    public AElfClientAsync(@Nullable String url, @Nullable String version,
                           @Nullable String username, @Nullable String password,
                           @Nullable Retrofit.Builder retrofit, @Nullable AsyncCaller caller) {
        super(url, version, username, password, retrofit);
        this.caller = caller != null ? caller : this.getCaller();
    }


    protected abstract AsyncCaller getCaller();

    public void getBlockHeightAsync(ISuccessCallback<Long> callback, @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(this::getBlockHeight), callback, onFail);
    }

    public void getBlockByHashAsync(String blockHash, ISuccessCallback<BlockDto> callback,
                                    @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getBlockByHash(blockHash)), callback, onFail);
    }

    public void getBlockByHashAsync(String blockHash, boolean includeTransactions, ISuccessCallback<BlockDto> callback,
                                    @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getBlockByHash(blockHash, includeTransactions)), callback, onFail);
    }

    public void getBlockByHeightAsync(long blockHeight, ISuccessCallback<BlockDto> callback,
                                      @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getBlockByHeight(blockHeight)), callback, onFail);
    }

    public void getBlockByHeightAsync(long blockHeight, boolean includeTransactions, ISuccessCallback<BlockDto> callback,
                                      @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getBlockByHeight(blockHeight, includeTransactions)), callback,
                onFail);
    }

    public void getChainStatusAsync(ISuccessCallback<ChainstatusDto> callback, @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(this::getChainStatus), callback, onFail);
    }

    public void getContractFileDescriptorSetAsync(String address, ISuccessCallback<byte[]> callback,
                                                  @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getContractFileDescriptorSet(address)), callback, onFail);
    }

    public void getTaskQueueStatusAsync(ISuccessCallback<List<TaskQueueInfoDto>> callback,
                                        @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(this::getTaskQueueStatus), callback, onFail);
    }

    /**
     * Send a transaction to the chain.
     *
     * @param rawTransaction - The raw transaction info
     * @param callback       - Returns the transaction result
     * @param onFail         - This callback can not be null, you should check the
     *                       result and handle it
     */
    public synchronized void executeTransactionAsync(final ExecuteTransactionDto rawTransaction,
                                                     ISuccessCallback<String> callback,
                                                     @NotNull IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> executeTransaction(rawTransaction)), callback, onFail);
    }

    public void getTransactionPoolStatusAsync(ISuccessCallback<TransactionPoolStatusOutput> callback,
                                              @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(this::getTransactionPoolStatus), callback, onFail);
    }

    public void getMerklePathByTransactionIdAsync(String transactionId, ISuccessCallback<MerklePathDto> callback,
                                                  @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getMerklePathByTransactionId(transactionId)), callback, onFail);
    }

    public void createRawTransactionAsync(CreateRawTransactionInput input,
                                          ISuccessCallback<CreateRawTransactionOutput> callback,
                                          @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> createRawTransaction(input)), callback, onFail);
    }

    public void executeRawTransactionAsync(ExecuteRawTransactionDto input, ISuccessCallback<String> callback,
                                           @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> executeRawTransaction(input)), callback, onFail);
    }

    public void sendRawTransactionAsync(SendRawTransactionInput input,
                                        ISuccessCallback<SendRawTransactionOutput> callback,
                                        @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> sendRawTransaction(input)), callback, onFail);
    }

    public void sendTransactionsAsync(SendTransactionsInput input, ISuccessCallback<List<String>> callback,
                                      @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> sendTransactions(input)), callback, onFail);
    }

    public void getTransactionResultAsync(String transactionId, ISuccessCallback<TransactionResultDto> callback,
                                          @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getTransactionResult(transactionId)), callback, onFail);
    }

    public void getTransactionResultsAsync(String blockHash, ISuccessCallback<List<TransactionResultDto>> callback,
                                           @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getTransactionResults(blockHash)), callback, onFail);
    }

    public void getTransactionResultsAsync(String blockHash, int offset, int limit,
                                           ISuccessCallback<List<TransactionResultDto>> callback,
                                           @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getTransactionResults(blockHash, offset, limit)), callback, onFail);
    }

    public void getChainIdAsync(ISuccessCallback<Integer> callback, @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(this::getChainId), callback, onFail);
    }

    /**
     * Add a new peer to the list.
     *
     * @param input    - The peer info
     * @param callback - Returns `true` if success, remember that you can still
     *                 receive a `false` means failure
     * @param onFail   - Failure callback, if you don't want to handle the failure,
     *                 provides `null`
     */
    public void addPeerAsync(AddPeerInput input, ISuccessCallback<Boolean> callback,
                             @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> addPeer(input)), callback, onFail);
    }

    public void removePeerAsync(String address, ISuccessCallback<Boolean> callback,
                                @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> removePeer(address)), callback, onFail);
    }

    public void getPeersAsync(Boolean withMetrics, ISuccessCallback<List<PeerDto>> callback,
                              @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getPeers(withMetrics)), callback, onFail);
    }

    public void getNetworkInfoAsync(ISuccessCallback<NetworkInfoOutput> callback, @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(this::getNetworkInfo), callback, onFail);
    }

    public void getGenesisContractAddressAsync(ISuccessCallback<String> callback,
                                               @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(this::getGenesisContractAddress), callback, onFail);
    }

    public void getFormattedAddressAsync(String privateKey, String address, ISuccessCallback<String> callback,
                                         @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getFormattedAddress(privateKey, address)), callback, onFail);
    }

    public void getContractAddressByNameAsync(String privateKey, byte[] contractNameHash,
                                              ISuccessCallback<String> callback,
                                              @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getContractAddressByName(privateKey, contractNameHash)), callback,
                onFail);
    }

    public void getSignatureWithPrivateKeyAsync(String privateKey, byte[] txData, ISuccessCallback<String> callback,
                                                @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getSignatureWithPrivateKey(privateKey, txData)), callback, onFail);
    }

    public void calculateTransactionFeeAsync(CalculateTransactionFeeInput input,
                                             ISuccessCallback<CalculateTransactionFeeOutput> callback,
                                             @Nullable IFailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> calculateTransactionFee(input)), callback, onFail);
    }

}
