# AElf-Client

## Introduction

This is a Java client library, used to communicate with the [AElf](https://github.com/AElfProject/AElf)  API.

### Getting Started

You should build the "AElfClient" project 

### Basic usage

``` JAVA
private static final String httpUrl="http://127.0.0.1:8200";

// get client instance
AElfClient AElfClient = new AElfClient(httpUrl);
long blockHeight = client.getBlockHeight();
```

### Class

#### AElfClient
``` JAVA
    public AElfClient(String url);
    
    public AElfClient(String url,String version);
   
    public TransactionDto generateTransaction(String from, String to, String methodName, String params) throws Exception;
    
    public TransactionDto signTransaction(String privateKeyHex,TransactionDto transaction);
     
    public String getGenesisContractAddress() throws Exception;
    
    public String getAddressFromPrivateKey(String privateKey);
    
    public String GetSignatureWithToHex(String privateKey, byte[] txData) throws Exception;
    
    public boolean isConnected();
    
    public BlockDto getBlockByHash(String blockHash, boolean includeTransactions) throws Exception;
     
    public BlockDto getBlockByHeight(long blockHeight, boolean includeTransactions) throws Exception;
     
    public BlockStateDto getBlockState(String blockHash) throws  Exception;
     
    public ChainstatusDto getChainStatus() throws Exception;
     
    public byte[] getContractFilCeDescriptorSet(String address) throws  Exception;
     
    public List<TaskQueueInfoDto> getTaskQueueStatus() throws Exception;
     
    public TransactionPoolStatusOutput getTransactionPoolStatus() throws Exception;
     
    public String executeTransaction(ExecuteTransactionDto input) throws Exception;
     
    public CreateRawTransactionOutput createRawTransaction(CreateRawTransactionInput input) throws Exception;
     
    public String  executeRawTransaction(ExecuteRawTransactionDto input) throws Exception;
     
    public SendRawTransactionOutput sendRawTransaction(SendRawTransactionInput input) throws Exception;
     
    public SendTransactionOutput sendTransaction(SendTransactionInput input) throws Exception;
     
    public List<String> sendTransactions(SendTransactionsInput input) throws Exception;
     
    public TransactionResultDto getTransactionResult(String transactionId) throws Exception;
     
    public List<TransactionResultDto>  getTransactionResults(String blockHash, int offset,int limit) throws Exception;
     
    public MerklePathDto getMerklePathByTransactionId(String transactionId) throws Exception;
     
    public List<TransactionResultDto>  getTransactionResults(String blockHash) throws Exception;
     
    public BlockDto getBlockByHeight(long blockHeight) throws Exception;
     
    public BlockDto getBlockByHash(String blockHash) throws Exception;
     
    public int getChainId() throws Exception;
    
    public Boolean addPeer(AddPeerInput input) throws Exception;
    
    public Boolean removePeer(String address) throws Exception;
    
    public List<PeerDto> getPeers(Boolean withMetrics) throws Exception;
    
    public NetworkInfoOutput getNetworkInfo() throws Exception;
    
 ```   
####

### Test

This module contains tests for all services provided by client. You can see how to properly use services provided by AElf_SDK here.

You need to firstly set necessary parameters to make sure tests can run successfully.

1. Set baseUrl to your target url.

   ``` JAVA
   String httpUrl="http://127.0.0.1:8200";
   ```

2. Give a valid privateKey of a node.

   ``` JAVA
   String privateKey="09da44778f8db2e602fb484334f37df19e221c84c4582ce5b7770ccfbc3ddbef";
   ```

### Note

You need to run a local or remote AElf node to run the unit test successfully. If you're not familiar with how to run a node or multiple nodes, please see [Running a node](https://docs.aelf.io/v/dev/main/main/run-node) / [Running multiple nodes](https://doc