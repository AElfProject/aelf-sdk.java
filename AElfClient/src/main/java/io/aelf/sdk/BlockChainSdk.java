package io.aelf.sdk;

import io.aelf.schemas.BlockBodyDto;
import io.aelf.schemas.BlockDto;
import io.aelf.schemas.BlockHeaderDto;
import io.aelf.schemas.ChainstatusDto;
import io.aelf.schemas.CreateRawTransactionInput;
import io.aelf.schemas.CreateRawTransactionOutput;
import io.aelf.schemas.ExecuteRawTransactionDto;
import io.aelf.schemas.ExecuteTransactionDto;
import io.aelf.schemas.LogEventDto;
import io.aelf.schemas.MerklePathDto;
import io.aelf.schemas.MerklePathNodeDto;
import io.aelf.schemas.SendRawTransactionInput;
import io.aelf.schemas.SendRawTransactionOutput;
import io.aelf.schemas.SendTransactionInput;
import io.aelf.schemas.SendTransactionOutput;
import io.aelf.schemas.SendTransactionsInput;
import io.aelf.schemas.TaskQueueInfoDto;
import io.aelf.schemas.TransactionDto;
import io.aelf.schemas.TransactionFeeDto;
import io.aelf.schemas.TransactionPoolStatusOutput;
import io.aelf.schemas.TransactionResultDto;
import io.aelf.utils.BitConverter;
import io.aelf.utils.ClientUtil;
import io.aelf.utils.HttpUtilExt;
import io.aelf.utils.JsonUtil;
import io.aelf.utils.MapEntry;
import io.aelf.utils.Maps;
import io.aelf.utils.StringUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.codec.binary.Base64;
import org.bitcoinj.core.Base58;

@SuppressWarnings("unchecked")
public class BlockChainSdk {

  private String AElfClientUrl;
  private String version;
  private static final String WA_BLOCKHEIGHT = "/api/blockChain/blockHeight";
  private static final String WA_BLOCK = "/api/blockChain/block";
  private static final String WA_BLOCKBYHEIGHT = "/api/blockChain/blockByHeight";
  private static final String WA_GETTRANSACTIONPOOLSTATUS = "/api/blockChain/transactionPoolStatus";
  private static final String WA_GETCHAINSTATUS = "/api/blockChain/chainStatus";
  private static final String WA_GETCFCRIPTORSET = "/api/blockChain/contractFileDescriptorSet";
  private static final String WA_GETTASKQUEUESTATUS = "/api/blockChain/taskQueueStatus";
  private static final String WA_EXECUTETRANSACTION = "/api/blockChain/executeTransaction";
  private static final String WA_EXECUTERAWTRANSACTION = "/api/blockChain/executeRawTransaction";
  private static final String WA_CREATERAWTRANSACTION = "/api/blockChain/rawTransaction";
  private static final String WA_SENDRAWTRANSACTION = "/api/blockChain/sendRawTransaction";
  private static final String WA_SENDTRANSACTION = "/api/blockChain/sendTransaction";
  private static final String WA_GETTRANSACTIONRESULT = "/api/blockChain/transactionResult";
  private static final String WA_GETTRANSACTIONRESULTS = "/api/blockChain/transactionResults";
  private static final String WA_SENDTRANSACTIONS = "/api/blockChain/sendTransactions";
  private static final String WA_GETMBYTRANSACTIONID = "/api/blockChain/merklePathByTransactionId";

  /**
   * Object construction through the url path.
   */
  public BlockChainSdk(String url, String version) {
    this.AElfClientUrl = url;
    this.version = version;
  }

  private BlockChainSdk() {
  }

  /**
   * Get the height of the current chain. wa:/api/blockChain/blockHeight
   */
  public long getBlockHeight() throws Exception {
    String chainContext = HttpUtilExt
        .sendGet(this.AElfClientUrl + WA_BLOCKHEIGHT, "UTF-8", this.version);
    return Long.parseLong(chainContext);
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
    String chainContext = HttpUtilExt.sendGet(
        this.AElfClientUrl + WA_BLOCK + "?blockHash=" + blockHash + "&includeTransactions="
            + includeTransactions, "UTF-8", this.version);
    MapEntry mapObjJson = JsonUtil.parseObject(chainContext);
    return createBlockDto(mapObjJson, includeTransactions);
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
    if (blockHeight == 0) {
      throw new RuntimeException("[20001]Not found");
    }
    String url = this.AElfClientUrl + WA_BLOCKBYHEIGHT + "?blockHeight=" + blockHeight
        + "&includeTransactions=" + includeTransactions;
    String chainContext = HttpUtilExt.sendGet(url, "UTF-8", this.version);
    MapEntry mapObjJson = JsonUtil.parseObject(chainContext);
    return createBlockDto(mapObjJson, includeTransactions);
  }


  /**
   * Get the current status of the block chain. wa:/api/blockChain/chainStatus
   */
  public ChainstatusDto getChainStatus() throws Exception {
    String url = this.AElfClientUrl + WA_GETCHAINSTATUS;
    String chainContext = ClientUtil.sendGet(url, "UTF-8", this.version);
    MapEntry mapObjJson = JsonUtil.parseObject(chainContext);
    LinkedHashMap<String, Integer> branchesMap = mapObjJson
        .getLinkedHashMap("Branches", new LinkedHashMap());
    Iterator<Map.Entry<String, Integer>> branchesMapSet = branchesMap.entrySet().iterator();
    LinkedHashMap<String, String> notLinkedBlocksMap = mapObjJson
        .getLinkedHashMap("NotLinkedBlocks", new LinkedHashMap());
    final Iterator<Map.Entry<String, String>> notLinkedBlocksSets = notLinkedBlocksMap.entrySet()
        .iterator();

    ChainstatusDto chainstatusDto = new ChainstatusDto();
    chainstatusDto.setChainId(mapObjJson.getString("ChainId", ""));
    chainstatusDto.setBranches(new HashMap<String, Long>());
    while (branchesMapSet.hasNext()) {
      Map.Entry<String, Integer> tmp = branchesMapSet.next();
      String key = tmp.getKey();
      Integer valueInteger = tmp.getValue();
      Long value = valueInteger.longValue();
      chainstatusDto.getBranches().put(key, value);
    }
    chainstatusDto.setNotLinkedBlocks(new HashMap<String, String>());

    while (notLinkedBlocksSets.hasNext()) {
      Map.Entry<String, String> tmp = notLinkedBlocksSets.next();
      String key = tmp.getKey();
      String value = tmp.getValue();
      chainstatusDto.getNotLinkedBlocks().put(key, value);
    }
    chainstatusDto.setLongestChainHeight(mapObjJson.getLong("LongestChainHeight", 0));
    chainstatusDto.setLongestChainHash(mapObjJson.getString("LongestChainHash", ""));
    chainstatusDto.setGenesisBlockHash(mapObjJson.getString("GenesisBlockHash", ""));
    chainstatusDto.setGenesisContractAddress(mapObjJson.getString("GenesisContractAddress", ""));
    chainstatusDto
        .setLastIrreversibleBlockHash(mapObjJson.getString("LastIrreversibleBlockHash", ""));
    chainstatusDto
        .setLastIrreversibleBlockHeight(mapObjJson.getLong("LastIrreversibleBlockHeight", 0));
    chainstatusDto.setBestChainHash(mapObjJson.getString("BestChainHash", ""));
    chainstatusDto.setBestChainHeight(mapObjJson.getLong("BestChainHeight", 0));
    return chainstatusDto;
  }

  /**
   * Get the protobuf definitions related to a contract /api/blockChain/contractFileDescriptorSet.
   */
  public byte[] getContractFileDescriptorSet(String address) throws Exception {
    String url = this.AElfClientUrl + WA_GETCFCRIPTORSET + "?address=" + address;
    String chainContext = HttpUtilExt.sendGet(url, "UTF-8", this.version);
    if (chainContext.startsWith("\"") && chainContext.endsWith("\"")) {
      return chainContext.getBytes();
    } else {
      throw new RuntimeException("getContractFileDescriptorSet body Exception");
    }

  }

  /**
   * Gets the status information of the task queue wa:/api/blockChain/taskQueueStatus.
   */
  public List<TaskQueueInfoDto> getTaskQueueStatus() throws Exception {
    String responseBody = HttpUtilExt
        .sendGet(this.AElfClientUrl + WA_GETTASKQUEUESTATUS, "UTF-8", this.version);
    List<LinkedHashMap> responseBodyList = JsonUtil.parseObject(responseBody, List.class);
    List<TaskQueueInfoDto> listTaskQueueInfoDto = new ArrayList<TaskQueueInfoDto>();
    for (LinkedHashMap linkedHashMapObj : responseBodyList) {
      TaskQueueInfoDto taskQueueInfoDto = new TaskQueueInfoDto();
      String sizeStr = StringUtil.toString(linkedHashMapObj.get("Size"));
      int size = sizeStr.length() == 0 ? 0 : Integer.parseInt(sizeStr);
      taskQueueInfoDto.setName(StringUtil.toString(linkedHashMapObj.get("Name")));
      taskQueueInfoDto.setSize(size);
      listTaskQueueInfoDto.add(taskQueueInfoDto);
    }
    return listTaskQueueInfoDto;

  }

  /**
   * Gets information about the current transaction pool.wa:/api/blockChain/transactionPoolStatus
   */
  public TransactionPoolStatusOutput getTransactionPoolStatus() throws Exception {
    String url = this.AElfClientUrl + WA_GETTRANSACTIONPOOLSTATUS;
    String responseBody = HttpUtilExt.sendGet(url, "UTF-8", this.version);
    MapEntry responseBobyMap = JsonUtil.parseObject(responseBody);
    TransactionPoolStatusOutput poolStatusOp = new TransactionPoolStatusOutput();
    poolStatusOp.setQueued(responseBobyMap.getInteger("Queued"));
    poolStatusOp.setValidated(responseBobyMap.getInteger("Validated"));
    return poolStatusOp;
  }


  /**
   * Call a read-only method of a contract. wa:/api/blockChain/executeTransaction
   */
  public String executeTransaction(ExecuteTransactionDto input) throws Exception {
    String url = this.AElfClientUrl + WA_EXECUTETRANSACTION;
    return HttpUtilExt.sendPost(url, JsonUtil.toJsonString(input), this.version);
  }

  /**
   * Creates an unsigned serialized transaction wa:/api/blockChain/rawTransaction.
   */
  public CreateRawTransactionOutput createRawTransaction(CreateRawTransactionInput input)
      throws Exception {
    String url = this.AElfClientUrl + WA_CREATERAWTRANSACTION;
    String responseBody = HttpUtilExt.sendPost(url, JsonUtil.toJsonString(input), this.version);
    MapEntry responseBodyMap = JsonUtil.parseObject(responseBody);
    String rawTransaction = responseBodyMap.getString("RawTransaction", "");
    CreateRawTransactionOutput createRawTransactionOutput = new CreateRawTransactionOutput();
    createRawTransactionOutput.setRawTransaction(rawTransaction);
    return createRawTransactionOutput;
  }

  /**
   * Call a method of a contract by given serialized str wa:/api/blockChain/executeRawTransaction.
   */
  public String executeRawTransaction(ExecuteRawTransactionDto input) throws Exception {
    String url = this.AElfClientUrl + WA_EXECUTERAWTRANSACTION;
    String responseBody = HttpUtilExt.sendPost(url, JsonUtil.toJsonString(input), this.version);
    return responseBody;
  }


  /**
   * Broadcast a serialized transaction. wa:/api/blockChain/sendRawTransaction
   */
  public SendRawTransactionOutput sendRawTransaction(SendRawTransactionInput input)
      throws Exception {
    String url = this.AElfClientUrl + WA_SENDRAWTRANSACTION;
    String responseBody = HttpUtilExt.sendPost(url, JsonUtil.toJsonString(input), this.version);
    MapEntry responseBodyMap = JsonUtil.parseObject(responseBody);
    final String transactionId = responseBodyMap.getString("TransactionId", "");

    TransactionDto transactionDtoObj = new TransactionDto();
    LinkedHashMap transactionObj = responseBodyMap
        .getLinkedHashMap("Transaction", new LinkedHashMap());
    MapEntry transactionObjMap = Maps.cloneMapEntry(transactionObj);
    transactionDtoObj.setFrom(transactionObjMap.getString("From", ""));
    transactionDtoObj.setTo(transactionObjMap.getString("To", ""));
    transactionDtoObj.setRefBlockNumber(transactionObjMap.getLong("RefBlockNumber", 0));
    transactionDtoObj.setRefBlockPrefix(transactionObjMap.getString("RefBlockPrefix", ""));
    transactionDtoObj.setMethodName(transactionObjMap.getString("MethodName", ""));
    transactionDtoObj.setParams(transactionObjMap.getString("Params", ""));
    transactionDtoObj.setSignature(transactionObjMap.getString("Signature", ""));

    SendRawTransactionOutput sendRawTransactionOutput = new SendRawTransactionOutput();
    sendRawTransactionOutput.setTransactionId(transactionId);
    sendRawTransactionOutput.setTransaction(transactionDtoObj);
    return sendRawTransactionOutput;
  }


  /**
   * Broadcast a transaction wa:/api/blockChain/sendTransaction.
   */
  public SendTransactionOutput sendTransaction(SendTransactionInput input) throws Exception {
    String url = this.AElfClientUrl + WA_SENDTRANSACTION;
    String responseBody = HttpUtilExt.sendPost(url, JsonUtil.toJsonString(input), this.version);
    MapEntry responseBodyMap = JsonUtil.parseObject(responseBody);
    String rawTransaction = responseBodyMap.getString("TransactionId", "");
    SendTransactionOutput sendTransactionOutputObj = new SendTransactionOutput();
    sendTransactionOutputObj.setTransactionId(rawTransaction);
    return sendTransactionOutputObj;
  }

  /**
   * Broadcast volume transactions wa:/api/blockChain/sendTransactions.
   */
  public List<String> sendTransactions(SendTransactionsInput input) throws Exception {
    String url = this.AElfClientUrl + WA_SENDTRANSACTIONS;
    String responseBody = HttpUtilExt.sendPost(url, JsonUtil.toJsonString(input), this.version);
    List<String> listString = JsonUtil.parseObject(responseBody, List.class);
    return listString;
  }

  /**
   * Get the current status of a transaction wa:/api/blockChain/transactionResult.
   */
  public TransactionResultDto getTransactionResult(String transactionId) throws Exception {
    String url =
        this.AElfClientUrl + WA_GETTRANSACTIONRESULT + "?transactionId=" + transactionId;
    String responseBody = ClientUtil.sendGet(url, "UTF-8", this.version);
    MapEntry responseBobyMap = JsonUtil.parseObject(responseBody);
    return createTransactionResultDto(responseBobyMap);
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
    if (offset < 0) {
      throw new RuntimeException("Error.InvalidOffset");
    }
    if (limit <= 0 || limit > 100) {
      throw new RuntimeException("Error.InvalidLimit");
    }
    String url =
        this.AElfClientUrl + WA_GETTRANSACTIONRESULTS + "?blockHash=" + blockHash + "&offset="
            + offset + "&limit=" + limit;
    String responseBody = ClientUtil.sendGet(url, "UTF-8", this.version);
    List<LinkedHashMap> responseBobyList = JsonUtil.parseObject(responseBody, List.class);
    List<TransactionResultDto> transactionResultDtoList = new ArrayList<TransactionResultDto>();
    for (LinkedHashMap responseBodyObj : responseBobyList) {
      MapEntry responseBodyObjMap = Maps.cloneMapEntry(responseBodyObj);
      transactionResultDtoList.add(createTransactionResultDto(responseBodyObjMap));
    }
    return transactionResultDtoList;
  }

  /**
   * Get merkle path of a transaction. wa:/api/blockChain/merklePathByTransactionId
   */
  public MerklePathDto getMerklePathByTransactionId(String transactionId) {
    String url = this.AElfClientUrl + WA_GETMBYTRANSACTIONID + "?transactionId="
        + transactionId;
    String responseBody = ClientUtil.sendGet(url, "UTF-8", this.version);
    MapEntry responseBobyMap = JsonUtil.parseObject(responseBody);
    MerklePathDto merklePathDtoObj = new MerklePathDto();
    merklePathDtoObj.setMerklePathNodes(new ArrayList<MerklePathNodeDto>());

    ArrayList<LinkedHashMap> merklePathNodesList = responseBobyMap
        .getArrayList("MerklePathNodes", new ArrayList());
    for (LinkedHashMap merklePathNodesObj : merklePathNodesList) {
      MapEntry merklePathNodesObjMap = Maps.cloneMapEntry(merklePathNodesObj);
      MerklePathNodeDto merklePathNodeDtoObj = new MerklePathNodeDto();
      merklePathNodeDtoObj.setHash(merklePathNodesObjMap.getString("Hash", ""));
      merklePathNodeDtoObj
          .setLeftChildNode(merklePathNodesObjMap.getBoolean("IsLeftChildNode", false));
      merklePathDtoObj.getMerklePathNodes().add(merklePathNodeDtoObj);
    }
    return merklePathDtoObj;
  }


  private BlockDto createBlockDto(MapEntry block, Boolean includeTransactions) throws Exception {
    if (block == null) {
      throw new RuntimeException("not found");
    }
    String heightStr = StringUtil.toString(block.getLinkedHashMap("Header").get("Height"));
    final long height = heightStr.length() == 0 ? 0 : Long.parseLong(heightStr);
    String bloomStr = StringUtil.toString(block.getLinkedHashMap("Header").get("Bloom"));
    bloomStr = bloomStr.length() == 0 ? Base64.encodeBase64String(new byte[256]) : bloomStr;
    final String timeStr = StringUtil.toString(block.getLinkedHashMap("Header").get("Time"));
    SimpleDateFormat df;
    if(timeStr.length() == 20){
      df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }else{
      df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }
    df.setTimeZone(TimeZone.getTimeZone("UTC"));
    BlockDto blockDto = new BlockDto();
    blockDto.setBlockHash(block.getString("BlockHash"));
    blockDto.setHeader(new BlockHeaderDto());
    blockDto.getHeader().setPreviousBlockHash(
        StringUtil.toString(block.getLinkedHashMap("Header").get("PreviousBlockHash")));
    blockDto.getHeader().setMerkleTreeRootOfTransactions(
        StringUtil.toString(block.getLinkedHashMap("Header").get("MerkleTreeRootOfTransactions")));
    blockDto.getHeader().setMerkleTreeRootOfWorldState(
        StringUtil.toString(block.getLinkedHashMap("Header").get("MerkleTreeRootOfWorldState")));
    blockDto.getHeader().setMerkleTreeRootOfTransactionState(StringUtil
        .toString(block.getLinkedHashMap("Header").get("MerkleTreeRootOfTransactionState")));
    blockDto.getHeader()
        .setExtra(StringUtil.toString(block.getLinkedHashMap("Header").get("Extra")));
    blockDto.getHeader().setHeight(height);
    blockDto.getHeader().setTime(df.parse(timeStr));
    blockDto.getHeader()
        .setChainId(StringUtil.toString(block.getLinkedHashMap("Header").get("ChainId")));
    blockDto.getHeader().setBloom(bloomStr);
    blockDto.getHeader()
        .setSignerPubkey(StringUtil.toString(block.getLinkedHashMap("Header").get("SignerPubkey")));
    if (!includeTransactions) {
      return blockDto;
    }

    List<String> transactions = (List<String>) block.getLinkedHashMap("Body").get("Transactions");
    if (transactions == null) {
      transactions = new ArrayList<>();
    }
    List txs = new ArrayList<String>();
    for (String transactionId : transactions) {
      txs.add(StringUtil.toString(transactionId));
    }
    blockDto.setBody(new BlockBodyDto());
    blockDto.getBody().setTransactionsCount(transactions.size());
    blockDto.getBody().setTransactions(txs);
    return blockDto;
  }

  private TransactionResultDto createTransactionResultDto(MapEntry transactionResult) {
    TransactionResultDto transactionResultObj = new TransactionResultDto();
    transactionResultObj.setTransactionId(transactionResult.getString("TransactionId", ""));
    transactionResultObj.setStatus(transactionResult.getString("Status", ""));
    transactionResultObj.setBloom(transactionResult.getString("Bloom", ""));
    transactionResultObj.setBlockNumber(transactionResult.getLong("BlockNumber", 0));
    transactionResultObj.setBlockHash(transactionResult.getString("BlockHash", ""));
    transactionResultObj.setReturnValue(transactionResult.getString("ReturnValue", ""));
    transactionResultObj.setError(transactionResult.getString("Error", ""));
    TransactionDto transactionDtoObj = new TransactionDto();
    LinkedHashMap transactionObj = transactionResult
        .getLinkedHashMap("Transaction", new LinkedHashMap());
    MapEntry transactionObjMap = Maps.cloneMapEntry(transactionObj);
    transactionDtoObj.setFrom(transactionObjMap.getString("From", ""));
    transactionDtoObj.setTo(transactionObjMap.getString("To", ""));
    transactionDtoObj.setRefBlockNumber(transactionObjMap.getLong("RefBlockNumber", 0));
    transactionDtoObj.setRefBlockPrefix(transactionObjMap.getString("RefBlockPrefix", ""));
    transactionDtoObj.setMethodName(transactionObjMap.getString("MethodName", ""));
    transactionDtoObj.setParams(transactionObjMap.getString("Params", ""));
    transactionDtoObj.setSignature(transactionObjMap.getString("Signature", ""));
    transactionResultObj.setTransaction(transactionDtoObj);
    TransactionFeeDto transactionFeeDtoObj = new TransactionFeeDto();
    transactionFeeDtoObj.setValue(new HashMap<String, Long>());
    LinkedHashMap transactionFeeObj = transactionResult
        .getLinkedHashMap("TransactionFee", new LinkedHashMap());
    MapEntry transactionFeeObjMap = Maps.cloneMapEntry(transactionFeeObj);
    LinkedHashMap<String, Integer> transactionFeeValueObjMap = transactionFeeObjMap
        .getLinkedHashMap("Value", new LinkedHashMap<String, Integer>());
    Iterator<Map.Entry<String, Integer>> transactionFeeValueObjSets = transactionFeeValueObjMap
        .entrySet().iterator();
    while (transactionFeeValueObjSets.hasNext()) {
      Map.Entry<String, Integer> tmp = transactionFeeValueObjSets.next();
      String key = tmp.getKey();
      Integer valueInteger = tmp.getValue();
      Long valueLong = valueInteger.longValue();
      transactionFeeDtoObj.getValue().put(key, valueLong);
    }
    transactionResultObj.setTransactionFee(transactionFeeDtoObj);
    List<LogEventDto> logEventDtoList = new ArrayList<LogEventDto>();
    List<LinkedHashMap> logsList = transactionResult.getArrayList("Logs", new ArrayList());
    for (LinkedHashMap logsObj : logsList) {
      LogEventDto logEventDtoObj = new LogEventDto();
      MapEntry logsObjMap = Maps.cloneMapEntry(logsObj);
      logEventDtoObj.setAddress(logsObjMap.getString("Address", ""));
      logEventDtoObj.setName(logsObjMap.getString("Name", ""));
      logEventDtoObj.setIndexed(new ArrayList<String>());
      logEventDtoObj.setNonIndexed(logsObjMap.getString("NonIndexed", ""));
      List<String> ndexedList = logsObjMap.getArrayList("Indexed", new ArrayList<String>());
      for (String ndexedStr : ndexedList) {
        logEventDtoObj.getIndexed().add(ndexedStr);
      }
      logEventDtoList.add(logEventDtoObj);
    }
    transactionResultObj.setLogs(logEventDtoList);
    return transactionResultObj;
  }

  /**
   * Get id of the chain.
   */
  public int getChainId() throws Exception {
    ChainstatusDto chainStatusDto = this.getChainStatus();
    String base58ChainId = chainStatusDto.getChainId();
    byte[] bytes = Base58.decode(base58ChainId);
    if (bytes.length < 4) {
      byte[] bs = new byte[4];
      for (int i = 0; i < 4; i++) {
        bs[i] = 0;
        if (bytes.length > (i)) {
          bs[i] = bytes[i];
        }
      }
      bytes = bs;
    }
    return BitConverter.toInt(bytes, 0);
  }
}
