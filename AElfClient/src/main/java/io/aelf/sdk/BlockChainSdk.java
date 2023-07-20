package io.aelf.sdk;

import com.google.gson.JsonParser;
import io.aelf.network.RetrofitFactory;
import io.aelf.schemas.*;
import io.aelf.utils.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.http.util.TextUtils;
import org.bitcoinj.core.Base58;

@SuppressWarnings({"unchecked", "SpellCheckingInspection", "unused", "DataFlowIssue", "deprecation"})
public class BlockChainSdk {

    /**
     * Get the height of the current chain.
     *
     * @return block height
     */
    @AElfUrl(url = "wa://api/blockChain/blockHeight")
    public long getBlockHeight() throws Exception {
        String chainContext = RetrofitFactory.getAPIService()
                .getBlockHeight()
                .execute()
                .body();
        return chainContext != null
                ? Long.parseLong(chainContext)
                : -1;
    }

    /**
     * Get information of a block by given block hash.
     * <p>
     *
     * @param blockHash block hash
     * @return {@link BlockDto} block information
     */
    public BlockDto getBlockByHash(String blockHash) throws Exception {
        return this.getBlockByHash(blockHash, false);
    }

    /**
     * Get information about a given block by block hash.
     * <p>
     * Optional: whether with the list of its transactions or not.
     *
     * @param blockHash           block hash
     * @param includeTransactions whether to include transaction information
     * @return {@link BlockDto} block information
     */
    @AElfUrl(url = "wa://api/blockChain/block?blockHash={blockHash}&includeTransactions={includeTransactions}")
    public BlockDto getBlockByHash(String blockHash, boolean includeTransactions) throws Exception {
        String result = RetrofitFactory.networkResult(
                RetrofitFactory
                        .getAPIService()
                        .getBlockByHash(blockHash, includeTransactions)
        );
        MapEntry<String, ?> mapObjJson = JsonUtil.parseObject(result);
        return createBlockDto(mapObjJson, includeTransactions);
    }

    /**
     * Get information of a block by specified height.
     * <p>
     *
     * @param blockHeight block height
     * @return {@link BlockDto} block information
     */
    public BlockDto getBlockByHeight(long blockHeight) throws Exception {
        return this.getBlockByHeight(blockHeight, false);
    }

    /**
     * Get information of a block by specified height.
     * <p>
     * Optional: whether to include transaction information.
     *
     * @param blockHeight         block height
     * @param includeTransactions whether to include transaction information
     * @return {@link BlockDto} block information
     */
    @AElfUrl(url = "wa://api/blockChain/blockByHeight?blockHeight={blockHeight}&includeTransactions={includeTransactions}")
    public BlockDto getBlockByHeight(long blockHeight, boolean includeTransactions) throws Exception {
        if (blockHeight <= 0) {
            throw new RuntimeException("[20001]Not found");
        }
        String result = RetrofitFactory.getAPIService()
                .getBlockByHeight(blockHeight, includeTransactions)
                .execute()
                .body()
                .toString();
        MapEntry<String, ?> mapObjJson = JsonUtil.parseObject(result);
        return createBlockDto(mapObjJson, includeTransactions);
    }

    /**
     * Get the current status of the blockchain.
     *
     * @return {@link ChainstatusDto} chain status
     */
    @AElfUrl(url = "wa://api/blockChain/chainStatus")
    public ChainstatusDto getChainStatus() throws RuntimeException, IOException {
        String chainContext = RetrofitFactory.getAPIService()
                .getChainStatus()
                .execute()
                .body()
                .toString();
        MapEntry<String, ?> mapObjJson = JsonUtil.parseObject(chainContext);
        if (mapObjJson == null)
            throw new RuntimeException();
        LinkedHashMap<String, Integer> branchesMap = mapObjJson
                .getLinkedHashMap("Branches", new LinkedHashMap<>());
        Iterator<Map.Entry<String, Integer>> branchesMapSet = branchesMap.entrySet().iterator();
        LinkedHashMap<String, String> notLinkedBlocksMap = mapObjJson
                .getLinkedHashMap("NotLinkedBlocks", new LinkedHashMap<>());
        final Iterator<Map.Entry<String, String>> notLinkedBlocksSets = notLinkedBlocksMap.entrySet()
                .iterator();

        ChainstatusDto chainstatusDto = new ChainstatusDto()
                .setChainId(mapObjJson.getString("ChainId", ""))
                .setBranches(new HashMap<>());
        while (branchesMapSet.hasNext()) {
            Map.Entry<String, Integer> tmp = branchesMapSet.next();
            String key = tmp.getKey();
            Integer valueInteger = tmp.getValue();
            Long value = valueInteger.longValue();
            chainstatusDto.getBranches().put(key, value);
        }
        chainstatusDto.setNotLinkedBlocks(new HashMap<>());

        while (notLinkedBlocksSets.hasNext()) {
            Map.Entry<String, String> tmp = notLinkedBlocksSets.next();
            String key = tmp.getKey();
            String value = tmp.getValue();
            chainstatusDto.getNotLinkedBlocks().put(key, value);
        }
        chainstatusDto.setLongestChainHeight(mapObjJson.getLong("LongestChainHeight", 0))
                .setLongestChainHash(mapObjJson.getString("LongestChainHash", ""))
                .setGenesisBlockHash(mapObjJson.getString("GenesisBlockHash", ""))
                .setGenesisContractAddress(mapObjJson.getString("GenesisContractAddress", ""))
                .setLastIrreversibleBlockHash(mapObjJson.getString("LastIrreversibleBlockHash", ""))
                .setLastIrreversibleBlockHeight(mapObjJson.getLong("LastIrreversibleBlockHeight", 0))
                .setBestChainHash(mapObjJson.getString("BestChainHash", ""))
                .setBestChainHeight(mapObjJson.getLong("BestChainHeight", 0));
        return chainstatusDto;
    }

    /**
     * Get the protobuf definitions related to a contract
     *
     * @param address contract address
     * @return byte[] protobuf definitions
     */
    @AElfUrl(url = "wa://api/blockChain/contractFileDescriptorSet?address={address}")
    public byte[] getContractFileDescriptorSet(String address) throws Exception {
        String chainContext = RetrofitFactory.getAPIService()
                .getContractFileDescriptorSet(address)
                .execute()
                .body()
                .toString();
        if (!TextUtils.isBlank(chainContext)
                && chainContext.startsWith("\"")
                && chainContext.endsWith("\"")) {
            return chainContext.getBytes();
        } else {
            throw new RuntimeException("getContractFileDescriptorSet body Exception");
        }
    }

    /**
     * Get the status information of the task queue.
     *
     * @return {@link TaskQueueInfoDto} task queue information
     */
    @AElfUrl(url = "wa://api/blockChain/taskQueueStatus")
    public List<TaskQueueInfoDto> getTaskQueueStatus() throws Exception {
        String responseBody = RetrofitFactory.getAPIService()
                .getTaskQueueStatus()
                .execute()
                .body()
                .toString();
        List<LinkedHashMap<String, ?>> responseBodyList = JsonUtil.parseObject(responseBody, List.class);
        List<TaskQueueInfoDto> listTaskQueueInfoDto = new ArrayList<>();
        for (LinkedHashMap<String, ?> linkedHashMapObj : responseBodyList) {
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
     * Get the information about the current transaction pool.
     *
     * @return {@link TransactionPoolStatusOutput} transaction pool status
     */
    @AElfUrl(url = "wa://api/blockChain/transactionPoolStatus")
    public TransactionPoolStatusOutput getTransactionPoolStatus() throws Exception {
        String responseBody = RetrofitFactory.getAPIService()
                .getTransactionPoolStatus()
                .execute()
                .body()
                .toString();
        MapEntry<String, ?> responseBodyMap = JsonUtil.parseObject(responseBody);
        if (responseBodyMap == null)
            throw new RuntimeException();
        return new TransactionPoolStatusOutput()
                .setQueued(responseBodyMap.getInteger("Queued"))
                .setValidated(responseBodyMap.getInteger("Validated"));
    }

    /**
     * Call a read-only method of a contract.
     *
     * @param input {@link TransactionWrapper} input
     * @return {@link String} output
     */
    @AElfUrl(url = "wa://api/blockChain/executeTransaction")
    public String executeTransaction(TransactionWrapper input) throws Exception {
        return RetrofitFactory.getAPIService()
                .executeTransaction(JsonParser.parseString(JsonUtil.toJsonString(input)))
                .execute()
                .body();
    }

    /**
     * Creates an unsigned serialized transaction.
     *
     * @param input {@link CreateRawTransactionInput} input
     * @return {@link CreateRawTransactionOutput} output
     */
    @AElfUrl(url = "wa://api/blockChain/rawTransaction")
    public CreateRawTransactionOutput createRawTransaction(CreateRawTransactionInput input)
            throws Exception {
        String responseBody = RetrofitFactory.getAPIService()
                .createRawTransaction(JsonParser.parseString(JsonUtil.toJsonString(input)))
                .execute()
                .body()
                .toString();
        MapEntry<String, ?> responseBodyMap = JsonUtil.parseObject(responseBody);
        if (responseBodyMap == null)
            throw new RuntimeException();
        String rawTransaction = responseBodyMap.getString("RawTransaction", "");
        CreateRawTransactionOutput createRawTransactionOutput = new CreateRawTransactionOutput();
        createRawTransactionOutput.setRawTransaction(rawTransaction);
        return createRawTransactionOutput;
    }

    /**
     * Call a method of a contract by given serialized string.
     *
     * @param input {@link ExecuteRawTransactionDto} input
     * @return {@link String} output
     */
    @AElfUrl(url = "wa://api/blockChain/executeRawTransaction")
    public String executeRawTransaction(ExecuteRawTransactionDto input) throws Exception {
        return RetrofitFactory.getAPIService()
                .executeRawTransaction(JsonParser.parseString(JsonUtil.toJsonString(input)))
                .execute()
                .body()
                .toString();
    }

    /**
     * Broadcast a serialized transaction.
     *
     * @param input {@link SendRawTransactionInput} input
     * @return {@link SendRawTransactionOutput} output
     */
    @AElfUrl(url = "wa://api/blockChain/sendRawTransaction")
    public SendRawTransactionOutput sendRawTransaction(SendRawTransactionInput input)
            throws Exception {
        String responseBody = RetrofitFactory.getAPIService()
                .sendRawTransaction(JsonParser.parseString(JsonUtil.toJsonString(input)))
                .execute()
                .body()
                .toString();
        MapEntry<String, ?> responseBodyMap = JsonUtil.parseObject(responseBody);
        if (responseBodyMap == null)
            throw new RuntimeException();
        final String transactionId = responseBodyMap.getString("TransactionId", "");

        TransactionDto transactionDtoObj = new TransactionDto();
        LinkedHashMap<String, ?> transactionObj = responseBodyMap
                .getLinkedHashMap("Transaction", new LinkedHashMap<>());
        MapEntry<String, ?> transactionObjMap = Maps.cloneMapEntry(transactionObj);
        transactionDtoObj.setFrom(transactionObjMap.getString("From", ""))
                .setTo(transactionObjMap.getString("To", ""))
                .setRefBlockNumber(transactionObjMap.getLong("RefBlockNumber", 0))
                .setRefBlockPrefix(transactionObjMap.getString("RefBlockPrefix", ""))
                .setMethodName(transactionObjMap.getString("MethodName", ""))
                .setParams(transactionObjMap.getString("Params", ""))
                .setSignature(transactionObjMap.getString("Signature", ""));

        SendRawTransactionOutput sendRawTransactionOutput = new SendRawTransactionOutput();
        sendRawTransactionOutput.setTransactionId(transactionId);
        sendRawTransactionOutput.setTransaction(transactionDtoObj);
        return sendRawTransactionOutput;
    }

    /**
     * Broadcast a transaction.
     *
     * @param input {@link TransactionWrapper} input
     * @return {@link SendTransactionOutput} output
     */
    @AElfUrl(url = "wa://api/blockChain/sendTransaction")
    public SendTransactionOutput sendTransaction(TransactionWrapper input) throws Exception {
        String responseBody = RetrofitFactory.getAPIService()
                .sendTransaction(JsonParser.parseString(JsonUtil.toJsonString(input)))
                .execute()
                .body()
                .toString();
        MapEntry<String, ?> responseBodyMap = JsonUtil.parseObject(responseBody);
        if (responseBodyMap == null)
            throw new RuntimeException();
        String rawTransaction = responseBodyMap.getString("TransactionId", "");
        SendTransactionOutput sendTransactionOutputObj = new SendTransactionOutput();
        sendTransactionOutputObj.setTransactionId(rawTransaction);
        return sendTransactionOutputObj;
    }

    /**
     * Broadcast volume transactions.
     *
     * @param input {@link SendTransactionsInput} input
     * @return {@link List} output
     */
    @AElfUrl(url = "wa://api/blockChain/sendTransactions")
    public List<String> sendTransactions(SendTransactionsInput input) throws Exception {
        return RetrofitFactory.getAPIService()
                .sendTransactions(JsonParser.parseString(JsonUtil.toJsonString(input)))
                .execute()
                .body();
    }

    /**
     * Get the current status of a transaction.
     *
     * @param transactionId {@link String} transactionId
     * @return {@link TransactionResultDto} output
     */
    @AElfUrl(url = "wa://api/blockChain/transactionResult")
    public TransactionResultDto getTransactionResult(String transactionId) throws Exception {
        String responseBody = RetrofitFactory.getAPIService()
                .getTransactionResult(transactionId)
                .execute()
                .body()
                .toString();
        MapEntry<String, ?> responseBodyMap = JsonUtil.parseObject(responseBody);
        if (responseBodyMap == null)
            throw new RuntimeException();
        return createTransactionResultDto(responseBodyMap);
    }

    /**
     * Get the results of multiple transactions.
     *
     * @param blockHash {@link String} blockHash
     * @return {@link List} output
     */
    @AElfUrl(url = "wa://api/blockChain/transactionResults")
    public List<TransactionResultDto> getTransactionResults(String blockHash) throws Exception {
        return this.getTransactionResults(blockHash, 0, 10);
    }

    /**
     * Get multiple transaction results by specified blockHash and the offset.
     *
     * @param blockHash {@link String} blockHash
     * @param offset    {@link Integer} offset
     * @param limit     {@link Integer} limit
     * @return {@link List} output
     */
    @AElfUrl(url = "wa://api/blockChain/transactionResults")
    public List<TransactionResultDto> getTransactionResults(String blockHash, int offset, int limit)
            throws Exception {
        if (offset < 0) {
            throw new RuntimeException("Error.InvalidOffset");
        }
        if (limit <= 0 || limit > 100) {
            throw new RuntimeException("Error.InvalidLimit");
        }
        String responseBody = RetrofitFactory.getAPIService()
                .getTransactionResults(blockHash, offset, limit)
                .execute()
                .body()
                .toString();
        List<LinkedHashMap<String, ?>> responseBobyList = JsonUtil.parseObject(responseBody, List.class);
        List<TransactionResultDto> transactionResultDtoList = new ArrayList<>();
        for (LinkedHashMap<String, ?> responseBodyObj : responseBobyList) {
            MapEntry<String, ?> responseBodyObjMap = Maps.cloneMapEntry(responseBodyObj);
            transactionResultDtoList.add(createTransactionResultDto(responseBodyObjMap));
        }
        return transactionResultDtoList;
    }

    /**
     * Get merkle tree's path of a transaction.
     *
     * @param transactionId {@link String} transactionId
     * @return {@link MerklePathDto} output
     */
    @AElfUrl(url = "wa://api/blockChain/merklePathByTransactionId")
    public MerklePathDto getMerklePathByTransactionId(String transactionId) throws Exception {
        String responseBody = RetrofitFactory.getAPIService()
                .getMerklePathByTransactionId(transactionId)
                .execute()
                .body()
                .toString();
        MapEntry<String, ?> responseBodyMap = JsonUtil.parseObject(responseBody);
        MerklePathDto merklePathDtoObj = new MerklePathDto();
        merklePathDtoObj.setMerklePathNodes(new ArrayList<>());
        if (responseBodyMap == null)
            throw new RuntimeException();

        ArrayList<LinkedHashMap<String, ?>> merklePathNodesList = responseBodyMap
                .getArrayList("MerklePathNodes", new ArrayList<>());
        for (LinkedHashMap<String, ?> merklePathNodesObj : merklePathNodesList) {
            MapEntry<String, ?> merklePathNodesObjMap = Maps.cloneMapEntry(merklePathNodesObj);
            MerklePathNodeDto merklePathNodeDtoObj = new MerklePathNodeDto()
                    .setHash(merklePathNodesObjMap.getString("Hash", ""))
                    .setLeftChildNode(merklePathNodesObjMap.getBoolean("IsLeftChildNode", false));
            merklePathDtoObj.getMerklePathNodes().add(merklePathNodeDtoObj);
        }
        return merklePathDtoObj;
    }

    /**
     * Calculate the transaction fee.
     *
     * @param input {@link CalculateTransactionFeeInput} input
     * @return {@link CalculateTransactionFeeOutput} output
     */
    @AElfUrl(url = "wa://api/blockChain/calculateTransactionFee")
    public CalculateTransactionFeeOutput calculateTransactionFee(CalculateTransactionFeeInput input) throws Exception {
        return RetrofitFactory.getAPIService()
                .calculateTransactionFee(JsonParser.parseString(JsonUtil.toJsonString(input)))
                .execute()
                .body();
    }

    private BlockDto createBlockDto(MapEntry<String, ?> block, Boolean includeTransactions) throws ParseException {
        if (block == null) {
            throw new RuntimeException("not found");
        }
        String heightStr = StringUtil.toString(block.getLinkedHashMap("Header").get("Height"));
        final long height = heightStr.length() == 0 ? 0 : Long.parseLong(heightStr);
        String bloomStr = StringUtil.toString(block.getLinkedHashMap("Header").get("Bloom"));
        bloomStr = bloomStr.length() == 0 ? Base64.getEncoder().encodeToString(new byte[256]) : bloomStr;
        final String timeStr = StringUtil.toString(block.getLinkedHashMap("Header").get("Time"));
        SimpleDateFormat df;
        if (timeStr.length() == 20) {
            df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        } else {
            df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        }
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        BlockDto blockDto = new BlockDto();
        blockDto.setBlockHash(block.getString("BlockHash"));
        blockDto.setHeader(new BlockHeaderDto());
        blockDto.getHeader().setPreviousBlockHash(
                        StringUtil.toString(block.getLinkedHashMap("Header").get("PreviousBlockHash")))
                .setMerkleTreeRootOfTransactions(
                        StringUtil.toString(block.getLinkedHashMap("Header").get("MerkleTreeRootOfTransactions")))
                .setMerkleTreeRootOfWorldState(
                        StringUtil.toString(block.getLinkedHashMap("Header").get("MerkleTreeRootOfWorldState")))
                .setMerkleTreeRootOfTransactionState(
                        StringUtil.toString(block.getLinkedHashMap("Header").get("MerkleTreeRootOfTransactionState")))
                .setExtra(StringUtil.toString(block.getLinkedHashMap("Header").get("Extra"))).setHeight(height)
                .setTime(df.parse(timeStr))
                .setChainId(StringUtil.toString(block.getLinkedHashMap("Header").get("ChainId")))
                .setBloom(bloomStr)
                .setSignerPubkey(StringUtil.toString(block.getLinkedHashMap("Header").get("SignerPubkey")));
        if (!includeTransactions) {
            return blockDto;
        }

        List<String> transactions = (List<String>) block.getLinkedHashMap("Body").get("Transactions");
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        List<String> txs = new ArrayList<>();
        for (String transactionId : transactions) {
            txs.add(StringUtil.toString(transactionId));
        }
        blockDto.setBody(new BlockBodyDto());
        blockDto.getBody().setTransactionsCount(transactions.size());
        blockDto.getBody().setTransactions(txs);
        return blockDto;
    }

    private TransactionResultDto createTransactionResultDto(MapEntry<String, ?> transactionResult) {
        TransactionResultDto transactionResultObj = new TransactionResultDto()
                .setTransactionId(transactionResult.getString("TransactionId", ""))
                .setStatus(transactionResult.getString("Status", ""))
                .setBloom(transactionResult.getString("Bloom", ""))
                .setBlockNumber(transactionResult.getLong("BlockNumber", 0))
                .setBlockHash(transactionResult.getString("BlockHash", ""))
                .setReturnValue(transactionResult.getString("ReturnValue", ""))
                .setError(transactionResult.getString("Error", ""));
        TransactionDto transactionDtoObj = new TransactionDto();
        LinkedHashMap<String, ?> transactionObj = transactionResult
                .getLinkedHashMap("Transaction", new LinkedHashMap<>());
        MapEntry<String, ?> transactionObjMap = Maps.cloneMapEntry(transactionObj);
        transactionDtoObj.setFrom(transactionObjMap.getString("From", ""))
                .setTo(transactionObjMap.getString("To", ""))
                .setRefBlockNumber(transactionObjMap.getLong("RefBlockNumber", 0))
                .setRefBlockPrefix(transactionObjMap.getString("RefBlockPrefix", ""))
                .setMethodName(transactionObjMap.getString("MethodName", ""))
                .setParams(transactionObjMap.getString("Params", ""))
                .setSignature(transactionObjMap.getString("Signature", ""));
        transactionResultObj.setTransaction(transactionDtoObj);
        TransactionFeeDto transactionFeeDtoObj = new TransactionFeeDto();
        transactionFeeDtoObj.setValue(new HashMap<>());
        LinkedHashMap<String, ?> transactionFeeObj = transactionResult
                .getLinkedHashMap("TransactionFee", new LinkedHashMap<>());
        MapEntry<String, ?> transactionFeeObjMap = Maps.cloneMapEntry(transactionFeeObj);
        LinkedHashMap<String, Integer> transactionFeeValueObjMap = transactionFeeObjMap
                .getLinkedHashMap("Value", new LinkedHashMap<String, Integer>());
        for (Map.Entry<String, Integer> tmp : transactionFeeValueObjMap
                .entrySet()) {
            String key = tmp.getKey();
            Integer valueInteger = tmp.getValue();
            Long valueLong = valueInteger.longValue();
            transactionFeeDtoObj.getValue().put(key, valueLong);
        }
        transactionResultObj.setTransactionFee(transactionFeeDtoObj);
        List<LogEventDto> logEventDtoList = new ArrayList<>();
        List<LinkedHashMap<String, ?>> logsList = transactionResult.getArrayList("Logs", new ArrayList<>());
        for (LinkedHashMap<String, ?> logsObj : logsList) {
            MapEntry<String, ?> logsObjMap = Maps.cloneMapEntry(logsObj);
            LogEventDto logEventDtoObj = new LogEventDto()
                    .setAddress(logsObjMap.getString("Address", ""))
                    .setName(logsObjMap.getString("Name", ""))
                    .setIndexed(new ArrayList<>())
                    .setNonIndexed(logsObjMap.getString("NonIndexed", ""));
            List<String> indexedList = logsObjMap.getArrayList("Indexed", new ArrayList<String>());
            for (String indexedStr : indexedList) {
                logEventDtoObj.getIndexed().add(indexedStr);
            }
            logEventDtoList.add(logEventDtoObj);
        }
        transactionResultObj.setLogs(logEventDtoList);
        return transactionResultObj;
    }

    /**
     * Get id of the chain.
     *
     * @return {@link Integer} id
     */
    public int getChainId() throws IOException {
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
