package io.aelf.network;

public interface APIPath {
    String WA_BLOCK_HEIGHT = "/api/blockChain/blockHeight";
    String WA_BLOCK = "/api/blockChain/block";
    String WA_BLOCK_BY_HEIGHT = "/api/blockChain/blockByHeight";
    String WA_GET_TRANSACTION_POOL_STATUS = "/api/blockChain/transactionPoolStatus";
    String WA_GET_CHAIN_STATUS = "/api/blockChain/chainStatus";
    String WA_GET_DESCRIPTOR_SET = "/api/blockChain/contractFileDescriptorSet";
    String WA_GET_TASK_QUEUE_STATUS = "/api/blockChain/taskQueueStatus";
    String WA_CREATE_RAW_TRANSACTION = "/api/blockChain/rawTransaction";
    String WA_EXECUTE_TRANSACTION = "/api/blockChain/executeTransaction";
    String WA_EXECUTE_RAW_TRANSACTION = "/api/blockChain/executeRawTransaction";
    String WA_SEND_RAW_TRANSACTION = "/api/blockChain/sendRawTransaction";
    String WA_SEND_TRANSACTION = "/api/blockChain/sendTransaction";
    String WA_GET_TRANSACTION_RESULT = "/api/blockChain/transactionResult";
    String WA_GET_TRANSACTION_RESULTS = "/api/blockChain/transactionResults";
    String WA_SEND_TRANSACTIONS = "/api/blockChain/sendTransactions";
    String WA_GET_M_BY_TRANSACTION_ID = "/api/blockChain/merklePathByTransactionId";
    String WA_CALCULATE_TRANSACTION_FEE = "/api/blockChain/calculateTransactionFee";
    String WA_ADD_PEER = "/api/net/peer";
    String WA_REMOVE_PEER = "/api/net/peer";
    String WA_GET_PEERS = "/api/net/peers";
    String WA_GET_NETWORK_INFO = "/api/net/networkInfo";
}
