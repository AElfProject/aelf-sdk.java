package io.aelf.network;

import com.google.gson.JsonElement;
import io.aelf.schemas.CalculateTransactionFeeOutput;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface APIService {
    @GET(APIPath.WA_BLOCK_HEIGHT)
    Call<String> getBlockHeight();

    @GET(APIPath.WA_BLOCK)
    Call<JsonElement> getBlockByHash(@Query("blockHash") String blockHash,
                                     @Query("includeTransactions") boolean includeTransactions);

    @GET(APIPath.WA_BLOCK_BY_HEIGHT)
    Call<JsonElement> getBlockByHeight(@Query("blockHeight") long blockHeight,
                                       @Query("includeTransactions") boolean includeTransactions);

    @GET(APIPath.WA_GET_CHAIN_STATUS)
    Call<JsonElement> getChainStatus();

    @GET(APIPath.WA_GET_DESCRIPTOR_SET)
    Call<JsonElement> getContractFileDescriptorSet(@Query("address") String address);

    @GET(APIPath.WA_GET_TASK_QUEUE_STATUS)
    Call<JsonElement> getTaskQueueStatus();

    @GET(APIPath.WA_GET_TRANSACTION_POOL_STATUS)
    Call<JsonElement> getTransactionPoolStatus();

    @POST(APIPath.WA_EXECUTE_TRANSACTION)
    Call<String> executeTransaction(@Body JsonElement input);

    @POST(APIPath.WA_CREATE_RAW_TRANSACTION)
    Call<JsonElement> createRawTransaction(@Body JsonElement input);

    @POST(APIPath.WA_EXECUTE_RAW_TRANSACTION)
    Call<JsonElement> executeRawTransaction(@Body JsonElement input);

    @POST(APIPath.WA_SEND_RAW_TRANSACTION)
    Call<JsonElement> sendRawTransaction(@Body JsonElement input);

    @POST(APIPath.WA_SEND_TRANSACTION)
    Call<JsonElement> sendTransaction(@Body JsonElement input);

    @POST(APIPath.WA_SEND_TRANSACTIONS)
    Call<List<String>> sendTransactions(@Body JsonElement input);

    @GET(APIPath.WA_GET_TRANSACTION_RESULT)
    Call<JsonElement> getTransactionResult(@Query("transactionId") String transactionId);

    @GET(APIPath.WA_GET_TRANSACTION_RESULTS)
    Call<JsonElement> getTransactionResults(@Query("blockHash") String blockHash,
                                            @Query("offset") int offset,
                                            @Query("limit") int limit);

    @GET(APIPath.WA_GET_M_BY_TRANSACTION_ID)
    Call<JsonElement> getMerklePathByTransactionId(@Query("transactionId") String transactionId);

    @POST(APIPath.WA_CALCULATE_TRANSACTION_FEE)
    Call<CalculateTransactionFeeOutput> calculateTransactionFee(@Body JsonElement input);

    @POST(APIPath.WA_ADD_PEER)
    Call<JsonElement> addPeer(@Body JsonElement input, @Header("Authorization") String auth);

    @DELETE(APIPath.WA_REMOVE_PEER)
    Call<JsonElement> removePeer(@Query("address") String address, @Header("Authorization") String auth);

    @GET(APIPath.WA_GET_PEERS)
    Call<JsonElement> getPeers(@Query("withMetrics") Boolean withMetrics);

    @GET(APIPath.WA_GET_NETWORK_INFO)
    Call<JsonElement> getNetworkInfo();
}
