package io.aelf.sdkv2;

import io.aelf.async.*;
import io.aelf.schemas.*;
import io.aelf.sdk.AElfClient;
import io.aelf.utils.AElfException;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings({"unused", "deprecation"})
public abstract class ClientAsync extends AElfClient {
    @NotNull
    private final AsyncCaller caller;

    public ClientAsync(String url) {
        this(url, null);
    }

    public ClientAsync(String url, String version) {
        this(url, version, null, null);
    }

    public ClientAsync(String url, String userName, String password) {
        this(url, null, userName, password);
    }

    public ClientAsync(String url, String version, String userName, String password) {
        this(url, version, userName, password, null);
    }

    public ClientAsync(String url, String version, String userName, String password, @Nullable AsyncCaller caller) {
        super(url, version, userName, password);
        this.caller = caller != null ? caller : this.getCaller();
    }

    protected abstract AsyncCaller getCaller();

    // Some methods throw Exception rather than AElfException, this method will
    // convert them.
    @NotNull
    @org.jetbrains.annotations.Contract(pure = true, value = "_ -> !null")
    protected final <T> AsyncFunction<T> convertFunction(@NotNull FunctionPrimal<T> func) {
        return () -> {
            try {
                return new AsyncResult<>(func.run());
            } catch (Exception e) {
                e.printStackTrace();
                throw new AElfException(e);
            }
        };
    }

    public void getBlockHeightAsync(SuccessCallback<Long> callback, @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(this::getBlockHeight), callback, onFail);
    }

    public void getBlockByHashAsync(String blockHash, SuccessCallback<BlockDto> callback,
                                    @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getBlockByHash(blockHash)), callback, onFail);
    }

    public void getBlockByHashAsync(String blockHash, boolean includeTransactions, SuccessCallback<BlockDto> callback,
                                    @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getBlockByHash(blockHash, includeTransactions)), callback, onFail);
    }

    public void getBlockByHeightAsync(long blockHeight, SuccessCallback<BlockDto> callback,
                                      @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getBlockByHeight(blockHeight)), callback, onFail);
    }

    public void getBlockByHeightAsync(long blockHeight, boolean includeTransactions, SuccessCallback<BlockDto> callback,
                                      @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getBlockByHeight(blockHeight, includeTransactions)), callback,
                onFail);
    }

    public void getChainStatusAsync(SuccessCallback<ChainstatusDto> callback, @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(this::getChainStatus), callback, onFail);
    }

    public void getContractFileDescriptorSetAsync(String address, SuccessCallback<byte[]> callback,
                                                  @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getContractFileDescriptorSet(address)), callback, onFail);
    }

    public void getTaskQueueStatusAsync(SuccessCallback<List<TaskQueueInfoDto>> callback,
                                        @Nullable FailCallback<Void> onFail) {
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
                                                     SuccessCallback<String> callback,
                                                     @NotNull FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> executeTransaction(rawTransaction)), callback, onFail);
    }

    public void getTransactionPoolStatusAsync(SuccessCallback<TransactionPoolStatusOutput> callback,
                                              @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(this::getTransactionPoolStatus), callback, onFail);
    }

    public void getMerklePathByTransactionIdAsync(String transactionId, SuccessCallback<MerklePathDto> callback,
                                                  @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getMerklePathByTransactionId(transactionId)), callback, onFail);
    }

    public void createRawTransactionAsync(CreateRawTransactionInput input,
                                          SuccessCallback<CreateRawTransactionOutput> callback,
                                          @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> createRawTransaction(input)), callback, onFail);
    }

    public void executeRawTransactionAsync(ExecuteRawTransactionDto input, SuccessCallback<String> callback,
                                           @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> executeRawTransaction(input)), callback, onFail);
    }

    public void sendRawTransactionAsync(SendRawTransactionInput input,
                                        SuccessCallback<SendRawTransactionOutput> callback,
                                        @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> sendRawTransaction(input)), callback, onFail);
    }

    public void sendTransactionsAsync(SendTransactionsInput input, SuccessCallback<List<String>> callback,
                                      @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> sendTransactions(input)), callback, onFail);
    }

    public void getTransactionResultAsync(String transactionId, SuccessCallback<TransactionResultDto> callback,
                                          @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getTransactionResult(transactionId)), callback, onFail);
    }

    public void getTransactionResultsAsync(String blockHash, SuccessCallback<List<TransactionResultDto>> callback,
                                           @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getTransactionResults(blockHash)), callback, onFail);
    }

    public void getTransactionResultsAsync(String blockHash, int offset, int limit,
                                           SuccessCallback<List<TransactionResultDto>> callback,
                                           @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getTransactionResults(blockHash, offset, limit)), callback, onFail);
    }

    public void getChainIdAsync(SuccessCallback<Integer> callback, @Nullable FailCallback<Void> onFail) {
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
    public void addPeerAsync(AddPeerInput input, SuccessCallback<Boolean> callback,
                             @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> addPeer(input)), callback, onFail);
    }

    public void removePeerAsync(String address, SuccessCallback<Boolean> callback,
                                @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> removePeer(address)), callback, onFail);
    }

    public void getPeersAsync(Boolean withMetrics, SuccessCallback<List<PeerDto>> callback,
                              @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getPeers(withMetrics)), callback, onFail);
    }

    public void getNetworkInfoAsync(SuccessCallback<NetworkInfoOutput> callback, @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(this::getNetworkInfo), callback, onFail);
    }

    public void getGenesisContractAddressAsync(SuccessCallback<String> callback,
                                               @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(this::getGenesisContractAddress), callback, onFail);
    }

    public void getFormattedAddressAsync(String privateKey, String address, SuccessCallback<String> callback,
                                         @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getFormattedAddress(privateKey, address)), callback, onFail);
    }

    public void getContractAddressByNameAsync(String privateKey, byte[] contractNameHash,
                                              SuccessCallback<String> callback,
                                              @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getContractAddressByName(privateKey, contractNameHash)), callback,
                onFail);
    }

    public void getSignatureWithPrivateKeyAsync(String privateKey, byte[] txData, SuccessCallback<String> callback,
                                                @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> getSignatureWithPrivateKey(privateKey, txData)), callback, onFail);
    }

    public void calculateTransactionFeeAsync(CalculateTransactionFeeInput input,
                                             SuccessCallback<CalculateTransactionFeeOutput> callback,
                                             @Nullable FailCallback<Void> onFail) {
        this.caller.asyncCall(convertFunction(() -> calculateTransactionFee(input)), callback, onFail);
    }

}


