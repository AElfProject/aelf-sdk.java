# AElf-Client

## Introduction

This is a Java client library, used to communicate with the [AElf](https://github.com/AElfProject/AElf)  API.

### Getting Started

You should build the "AElf_SDK" project 

### Basic usage

``` JAVA
private static final String httpUrl="http://127.0.0.1:8200";

// get client instance
AelfSdk aelfSdk=new AelfSdk(httpUrl);
long blockHeight = aelfSdk.getBlockChainSdkObj().getBlockHeightAsync();
```

### Class

#### AelfSdk
    public AelfSdk(String url);
    
    public BlockChainSdk getBlockChainSdkObj();
    
    public NetSdk getNetSdkObj();
    
    public TransactionDto generateTransaction(String from, String to, String methodName, String params) throws Exception;
    
    public TransactionDto signTransaction(String privateKeyHex,TransactionDto transaction);
     
    public String getGenesisContractAddressAsync() throws Exception;
    
    public String getAddressFromPrivateKey(String privateKey);
    
    public String GetSignatureWithToHex(String privateKey, byte[] txData) throws Exception;
    
    public boolean isConnected();
    
####

#### BlockChainSdk

``` JAVA
 public AelfSdk(String url);
 
 public BlockChainSdk getBlockChainSdkObj();

 public BlockDto getBlockByHashAsync(String blockHash, boolean includeTransactions) throws Exception
 
 public BlockDto getBlockByHeightAsync(long blockHeight, boolean includeTransactions) throws Exception
 
 public BlockStateDto getBlockState(String blockHash) throws  Exception
 
 public ChainstatusDto getChainStatusAsync() throws Exception
 
 public byte[] getContractFilCeDescriptorSetAsync(String address) throws  Exception
 
 public List<TaskQueueInfoDto> getTaskQueueStatusAsync() throws Exception
 
 public TransactionPoolStatusOutput getTransactionPoolStatusAsync() throws Exception
 
 public String executeTransactionAsync(ExecuteTransactionDto input) throws Exception
 
 public CreateRawTransactionOutput createRawTransactionAsync(CreateRawTransactionInput input) throws Exception
 
 public String  executeRawTransactionAsync(ExecuteRawTransactionDto input) throws Exception
 
 public SendRawTransactionOutput sendRawTransactionAsync(SendRawTransactionInput input) throws Exception
 
 public SendTransactionOutput sendTransactionAsync(SendTransactionInput input) throws Exception
 
 public List<String> sendTransactionsAsync(SendTransactionsInput input) throws Exception
 
 public TransactionResultDto getTransactionResultAsync(String transactionId) throws Exception
 
 public List<TransactionResultDto>  getTransactionResultsAsync(String blockHash, int offset,int limit) throws Exception
 
 public MerklePathDto getMerklePathByTransactionIdAsync(String transactionId) throws Exception
 
 public List<TransactionResultDto>  getTransactionResultsAsync(String blockHash) throws Exception
 
 public BlockDto getBlockByHeightAsync(long blockHeight) throws Exception
 
 public BlockDto getBlockByHashAsync(String blockHash) throws Exception
 
 public int getChainIdAsync() throws Exception
```

#### NetSdk

``` JAVA
public NetSdk(String url);

public Boolean addPeerAsync(AddPeerInput input) throws Exception

public Boolean removePeerAsync(String address) throws Exception

public List<PeerDto> getPeersAsync(Boolean withMetrics) throws Exception

public NetworkInfoOutput getNetworkInfoAsync() throws Exception

```


### Test

This module contains tests for all services provided by AElfClient. You can see how to properly use services provided by AElf_SDK here.

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