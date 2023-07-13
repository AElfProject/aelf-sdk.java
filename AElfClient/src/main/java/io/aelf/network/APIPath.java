package io.aelf.network;

public class APIPath {
    public static final String WA_BLOCK_HEIGHT = "/api/blockChain/blockHeight";
    public static final String WA_BLOCK = "/api/blockChain/block";
    public static final String WA_BLOCK_BY_HEIGHT = "/api/blockChain/blockByHeight";
    public static final String WA_GET_TRANSACTION_POOL_STATUS = "/api/blockChain/transactionPoolStatus";
    public static final String WA_GET_CHAIN_STATUS = "/api/blockChain/chainStatus";
    public static final String WA_GET_DESCRIPTOR_SET = "/api/blockChain/contractFileDescriptorSet";
    public static final String WA_GET_TASK_QUEUE_STATUS = "/api/blockChain/taskQueueStatus";
    public static final String WA_CREATE_RAW_TRANSACTION = "/api/blockChain/rawTransaction";
    public static final String WA_EXECUTE_TRANSACTION = "/api/blockChain/executeTransaction";
    public static final String WA_EXECUTE_RAW_TRANSACTION = "/api/blockChain/executeRawTransaction";
    public static final String WA_SEND_RAW_TRANSACTION = "/api/blockChain/sendRawTransaction";
    public static final String WA_SEND_TRANSACTION = "/api/blockChain/sendTransaction";
    public static final String WA_GET_TRANSACTION_RESULT = "/api/blockChain/transactionResult";
    public static final String WA_GET_TRANSACTION_RESULTS = "/api/blockChain/transactionResults";
    public static final String WA_SEND_TRANSACTIONS = "/api/blockChain/sendTransactions";
    public static final String WA_GET_M_BY_TRANSACTION_ID = "/api/blockChain/merklePathByTransactionId";
    public static final String WA_CALCULATE_TRANSACTION_FEE = "/api/blockChain/calculateTransactionFee";
    public static final String WA_ADD_PEER = "/api/net/peer";
    public static final String WA_REMOVE_PEER = "/api/net/peer";
    public static final String WA_GET_PEERS = "/api/net/peers";
    public static final String WA_GET_NETWORK_INFO = "/api/net/networkInfo";
}
