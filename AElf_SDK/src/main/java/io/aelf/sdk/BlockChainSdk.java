package io.aelf.sdk;
import io.aelf.schemas.*;
import io.aelf.utils.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bitcoinj.core.Base58;

import java.text.SimpleDateFormat;
import java.util.*;


public class BlockChainSdk {
    private String aelfSdkUrl;
    private static final String WEBAPI_BLOCKHEIGHT="/api/blockChain/blockHeight";
    private static final String WEBAPI_BLOCK="/api/blockChain/block";
    private static final String WEBAPI_BLOCKBYHEIGHT="/api/blockChain/blockByHeight";
    private static final String WEBAPI_GETTRANSACTIONPOOLSTATUSASYNC="/api/blockChain/transactionPoolStatus";
    private static final String WEBAPI_GETCHAINSTATUS="/api/blockChain/chainStatus";
    private static final String WEBAPI_GETCONTRACTFILEDESCRIPTORSET="/api/blockChain/contractFileDescriptorSet";
    private static final String WEBAPI_GETTASKQUEUESTATUSASYNC="/api/blockChain/taskQueueStatus";
    private static final String WEBAPI_EXECUTETRANSACTIONASYNC="/api/blockChain/executeTransaction";
    private static final String WEBAPI_EXECUTERAWTRANSACTIONASYNC="/api/blockChain/executeRawTransaction";
    private static final String WEBAPI_CREATERAWTRANSACTIONASYNC="/api/blockChain/rawTransaction";
    private static final String WEBAPI_SENDRAWTRANSACTIONASYNC="/api/blockChain/sendRawTransaction";
    private static final String WEBAPI_SENDTRANSACTIONASYNC="/api/blockChain/sendTransaction";
    private static final String WEBAPI_GETTRANSACTIONRESULTASYNC="/api/blockChain/transactionResult";
    private static final String WEBAPI_GETTRANSACTIONRESULTASYNCS="/api/blockChain/transactionResults";
    private static final String WEBAPI_SENDTRANSACTIONSASYNC="/api/blockChain/sendTransactions";
    private static final String WEBAPI_GETMERKLEPATHBYTRANSACTIONIDASYNC="/api/blockChain/merklePathByTransactionId";

    /**
     * Object construction through the url path
     * @param url
     */
    public BlockChainSdk(String url){
        this.aelfSdkUrl=url;
    }
    private BlockChainSdk(){}

    /**
     * Get the height of the current chain.
     * webapi:/api/blockChain/blockHeight
     * @return
     */
    public long getBlockHeight() throws Exception{
        String chainContext = HttpClientUtilExt.sendGetRequest(this.aelfSdkUrl+WEBAPI_BLOCKHEIGHT,"UTF-8");
        return Long.parseLong(chainContext);
    }

    /**
     * Get information about a given block by block hash. Otionally with the list of its transactions.
     * webapi://api/blockChain/block?includeTransactions={includeTransactions}
     * @param blockHash
     * @param includeTransactions
     * @return
     */
    public BlockDto getBlockByHash(String blockHash, boolean includeTransactions) throws Exception {
        String chainContext = HttpClientUtilExt.sendGetRequest(this.aelfSdkUrl+WEBAPI_BLOCK+"?blockHash="+blockHash+"&includeTransactions="+includeTransactions,"UTF-8");
        MapEntry mapObjJson=JSONUtil.parseObject(chainContext);
        BlockDto blockDto=createBlockDto(mapObjJson,includeTransactions);
        return blockDto;
    }

    /**
     * Get information of a block by specified height. Optional whether to include transaction information.
     * webapi://api/blockChain/blockByHeight?includeTransactions={includeTransactions}
     * @param blockHeight
     * @param includeTransactions
     * @return
     */
    public BlockDto getBlockByHeight(long blockHeight, boolean includeTransactions) throws Exception {
        if(blockHeight==0){
            throw new RuntimeException("[20001]Not found");
        }
        String url=this.aelfSdkUrl+WEBAPI_BLOCKBYHEIGHT+"?blockHeight="+blockHeight+"&includeTransactions="+includeTransactions;
        String chainContext = HttpClientUtilExt.sendGetRequest(url,"UTF-8");
        MapEntry mapObjJson=JSONUtil.parseObject(chainContext);
        BlockDto blockDto=createBlockDto(mapObjJson, includeTransactions);
        return blockDto;
    }


    /**
     * Get the current status of the block chain.
     * webapi:/api/blockChain/chainStatus
     * @return
     */
    public ChainstatusDto getChainStatus() throws Exception{
        String url=this.aelfSdkUrl+WEBAPI_GETCHAINSTATUS;
        String chainContext=HttpClientUtil.sendGetRequest(url,"UTF-8");
        MapEntry mapObjJson=JSONUtil.parseObject(chainContext);
        LinkedHashMap<String,Integer> branchesMap=mapObjJson.getLinkedHashMap("Branches",new LinkedHashMap());
        Iterator<Map.Entry<String, Integer>> branchesMapSet=branchesMap.entrySet().iterator();
        LinkedHashMap<String,String> notLinkedBlocksMap=mapObjJson.getLinkedHashMap("NotLinkedBlocks",new LinkedHashMap());
        Iterator<Map.Entry<String, String>> notLinkedBlocksSets=notLinkedBlocksMap.entrySet().iterator();

        ChainstatusDto chainstatusDto=new ChainstatusDto();
        chainstatusDto.setChainId(mapObjJson.getString("ChainId",""));
        chainstatusDto.setBranches(new HashMap<String, Long>());
        while(branchesMapSet.hasNext()){
            Map.Entry<String, Integer> tmp=branchesMapSet.next();
            String key=tmp.getKey();
            Integer valueInteger=tmp.getValue();
            Long value=valueInteger.longValue();
            chainstatusDto.getBranches().put(key,value);
        }
        chainstatusDto.setNotLinkedBlocks(new HashMap<String, String>());


        while(notLinkedBlocksSets.hasNext()){
            Map.Entry<String, String> tmp=notLinkedBlocksSets.next();
            String key=tmp.getKey();
            String value=tmp.getValue();
            chainstatusDto.getNotLinkedBlocks().put(key,value);
        }
        chainstatusDto.setLongestChainHeight(mapObjJson.getLong("LongestChainHeight",0));
        chainstatusDto.setLongestChainHash(mapObjJson.getString("LongestChainHash",""));
        chainstatusDto.setGenesisBlockHash(mapObjJson.getString("GenesisBlockHash",""));
        chainstatusDto.setGenesisContractAddress(mapObjJson.getString("GenesisContractAddress",""));
        chainstatusDto.setLastIrreversibleBlockHash(mapObjJson.getString("LastIrreversibleBlockHash",""));
        chainstatusDto.setLastIrreversibleBlockHeight(mapObjJson.getLong("LastIrreversibleBlockHeight",0));
        chainstatusDto.setBestChainHash(mapObjJson.getString("BestChainHash",""));
        chainstatusDto.setBestChainHeight(mapObjJson.getLong("BestChainHeight",0));
        return chainstatusDto;
    }
    /**
     * Get the protobuf definitions related to a contract
     * /api/blockChain/contractFileDescriptorSet
     * @param address
     * @return
     */
    public byte[] getContractFilCeDescriptorSet(String address) throws  Exception{
        String url=this.aelfSdkUrl+WEBAPI_GETCONTRACTFILEDESCRIPTORSET+"?address="+address;
        String chainContext = HttpClientUtilExt.sendGetRequest(url,"UTF-8");
        if(chainContext.startsWith("\"") && chainContext.endsWith("\"")){
            return chainContext.getBytes();
        }else{
            throw new RuntimeException("getContractFilCeDescriptorSet body Exception");
        }

    }
    /**
     * Gets the status information of the task queue
     * webapi:/api/blockChain/taskQueueStatus
     * @return
     */
    public List<TaskQueueInfoDto> getTaskQueueStatus() throws Exception{
        String responseBody=HttpClientUtilExt.sendGetRequest(this.aelfSdkUrl+WEBAPI_GETTASKQUEUESTATUSASYNC,"UTF-8");
        List<LinkedHashMap> responseBodyList=JSONUtil.parseObject(responseBody,List.class);
        List<TaskQueueInfoDto> listTaskQueueInfoDto=new ArrayList<TaskQueueInfoDto>();
        for(LinkedHashMap linkedHashMapObj:responseBodyList){
            TaskQueueInfoDto taskQueueInfoDto=new TaskQueueInfoDto();
            String sizeStr=StringUtil.toString(linkedHashMapObj.get("Size"));
            int size=sizeStr.length()==0?0:Integer.parseInt(sizeStr);
            taskQueueInfoDto.setName(StringUtil.toString(linkedHashMapObj.get("Name")));
            taskQueueInfoDto.setSize(size);
            listTaskQueueInfoDto.add(taskQueueInfoDto);
        }
        return listTaskQueueInfoDto;

    }

    /**
     * Gets information about the current transaction pool.
     * webapi:/api/blockChain/transactionPoolStatus
     * @return
     */
    public TransactionPoolStatusOutput getTransactionPoolStatus() throws Exception{
        String url=this.aelfSdkUrl+WEBAPI_GETTRANSACTIONPOOLSTATUSASYNC;
        String responseBody=HttpClientUtilExt.sendGetRequest(url,"UTF-8");
        MapEntry responseBobyMap=JSONUtil.parseObject(responseBody);
        TransactionPoolStatusOutput poolStatusOp=new TransactionPoolStatusOutput();
        poolStatusOp.setQueued(responseBobyMap.getInteger("Queued"));
        poolStatusOp.setValidated(responseBobyMap.getInteger("Validated"));
        return poolStatusOp;
    }



    /**
     * Call a read-only method of a contract.
     * webapi:/api/blockChain/executeTransaction
     * @param input
     * @return
     */
    public String executeTransaction(ExecuteTransactionDto input) throws Exception{
        String url=this.aelfSdkUrl+WEBAPI_EXECUTETRANSACTIONASYNC;
        String responseBody=HttpClientUtilExt.sendPostRequest(url,JSONUtil.toJSONString(input));
        return responseBody;
    }

    /**
     * Creates an unsigned serialized transaction
     * webapi:/api/blockChain/rawTransaction
     * @param input
     * @return
     */
    public CreateRawTransactionOutput createRawTransaction(CreateRawTransactionInput input) throws Exception {
        String url=this.aelfSdkUrl+WEBAPI_CREATERAWTRANSACTIONASYNC;
        String responseBody=HttpClientUtilExt.sendPostRequest(url,JSONUtil.toJSONString(input));
        MapEntry responseBodyMap=JSONUtil.parseObject(responseBody);
        String rawTransaction=responseBodyMap.getString("RawTransaction","");
        CreateRawTransactionOutput createRawTransactionOutput=new CreateRawTransactionOutput();
        createRawTransactionOutput.setRawTransaction(rawTransaction);
        return createRawTransactionOutput;
    }

    /**
     * Call a method of a contract by given serialized strings .
     * webapi:/api/blockChain/executeRawTransaction
     * @param input
     * @return
     */
    public String  executeRawTransaction(ExecuteRawTransactionDto input) throws Exception {
        String url=this.aelfSdkUrl+WEBAPI_EXECUTERAWTRANSACTIONASYNC;
        String responseBody=HttpClientUtilExt.sendPostRequest(url,JSONUtil.toJSONString(input));
        return  responseBody;
    }



    /**
     * Broadcast a serialized transaction.
     * webapi:/api/blockChain/sendRawTransaction
     * @param input
     * @return
     */
    public SendRawTransactionOutput sendRawTransaction(SendRawTransactionInput input) throws Exception {
        String url=this.aelfSdkUrl+WEBAPI_SENDRAWTRANSACTIONASYNC;
        String responseBody=HttpClientUtilExt.sendPostRequest(url,JSONUtil.toJSONString(input));
        MapEntry responseBodyMap=JSONUtil.parseObject(responseBody);
        String TransactionId=responseBodyMap.getString("TransactionId","");

        TransactionDto transactionDtoObj=new TransactionDto();
        LinkedHashMap transactionObj=responseBodyMap.getLinkedHashMap("Transaction",new LinkedHashMap());
        MapEntry transactionObjMap=Maps.cloneMapEntry(transactionObj);
        transactionDtoObj.setFrom(transactionObjMap.getString("From",""));
        transactionDtoObj.setTo(transactionObjMap.getString("To",""));
        transactionDtoObj.setRefBlockNumber(transactionObjMap.getLong("RefBlockNumber",0));
        transactionDtoObj.setRefBlockPrefix(transactionObjMap.getString("RefBlockPrefix",""));
        transactionDtoObj.setMethodName(transactionObjMap.getString("MethodName",""));
        transactionDtoObj.setParams(transactionObjMap.getString("Params",""));
        transactionDtoObj.setSignature(transactionObjMap.getString("Signature",""));

        SendRawTransactionOutput sendRawTransactionOutput=new SendRawTransactionOutput();
        sendRawTransactionOutput.setTransactionId(TransactionId);
        sendRawTransactionOutput.setTransaction(transactionDtoObj);
        return sendRawTransactionOutput;
    }



    /**
     * Broadcast a transaction
     * webapi:/api/blockChain/sendTransaction
     * @param input
     * @return
     */
    public SendTransactionOutput sendTransaction(SendTransactionInput input) throws Exception {
        String url=this.aelfSdkUrl+WEBAPI_SENDTRANSACTIONASYNC;
        String responseBody=HttpClientUtilExt.sendPostRequest(url,JSONUtil.toJSONString(input));
        MapEntry responseBodyMap=JSONUtil.parseObject(responseBody);
        String rawTransaction=responseBodyMap.getString("TransactionId","");
        SendTransactionOutput sendTransactionOutputObj=new SendTransactionOutput();
        sendTransactionOutputObj.setTransactionId(rawTransaction);
        return sendTransactionOutputObj;
    }

    /**
     * Broadcast volume transactions
     * webapi:/api/blockChain/sendTransactions
     * @param input
     * @return
     */
    public List<String> sendTransactions(SendTransactionsInput input) throws Exception{
        String url=this.aelfSdkUrl+WEBAPI_SENDTRANSACTIONSASYNC;
        String responseBody=HttpClientUtilExt.sendPostRequest(url,JSONUtil.toJSONString(input));
        List<String> listString=JSONUtil.parseObject(responseBody,List.class);
        return listString;
    }

    /**
     * Get the current status of a transaction
     * webapi:/api/blockChain/transactionResult
     * @param transactionId
     * @return
     */
    public TransactionResultDto getTransactionResult(String transactionId) throws Exception {
        String url=this.aelfSdkUrl+WEBAPI_GETTRANSACTIONRESULTASYNC+"?transactionId="+transactionId;
        String responseBody=HttpClientUtil.sendGetRequest(url,"UTF-8");
        MapEntry responseBobyMap=JSONUtil.parseObject(responseBody);
        return createTransactionResultDto(responseBobyMap);
    }

    /**
     * Get multiple transaction results.
     * webapi:/api/blockChain/transactionResults
     * @param blockHash
     * @param offset
     * @param limit
     * @return
     */
    public List<TransactionResultDto>  getTransactionResults(String blockHash, int offset,int limit) throws Exception{
        if (offset < 0) {
            throw new RuntimeException("Error.InvalidOffset");
        }
        if (limit <= 0 || limit > 100) {
            throw new RuntimeException("Error.InvalidLimit");
        }
        String url=this.aelfSdkUrl+WEBAPI_GETTRANSACTIONRESULTASYNCS+"?blockHash="+blockHash+"&offset="+offset+"&limit="+limit;
        String responseBody=HttpClientUtil.sendGetRequest(url,"UTF-8");
        List<LinkedHashMap> responseBobyList=JSONUtil.parseObject(responseBody,List.class);
        List<TransactionResultDto> transactionResultDtoList=new ArrayList<TransactionResultDto>();
        for(LinkedHashMap responseBodyObj:responseBobyList){
            MapEntry responseBodyObjMap=Maps.cloneMapEntry(responseBodyObj);
            transactionResultDtoList.add(createTransactionResultDto(responseBodyObjMap));
        }
        return transactionResultDtoList;
    }

    /**
     * Get merkle path of a transaction.
     * webapi:/api/blockChain/merklePathByTransactionId
     * @param transactionId
     * @return
     */
    public MerklePathDto getMerklePathByTransactionId(String transactionId) throws Exception{
        String url=this.aelfSdkUrl+WEBAPI_GETMERKLEPATHBYTRANSACTIONIDASYNC+"?transactionId="+transactionId;
        String responseBody=HttpClientUtil.sendGetRequest(url,"UTF-8");
        MapEntry responseBobyMap=JSONUtil.parseObject(responseBody);
        MerklePathDto merklePathDtoObj=new MerklePathDto();
        merklePathDtoObj.setMerklePathNodes(new ArrayList<MerklePathNodeDto>());

        ArrayList<LinkedHashMap> merklePathNodesList=responseBobyMap.getArrayList("MerklePathNodes",new ArrayList());
        for(LinkedHashMap merklePathNodesObj:merklePathNodesList){
            MapEntry merklePathNodesObjMap=Maps.cloneMapEntry(merklePathNodesObj);
            MerklePathNodeDto merklePathNodeDtoObj=new MerklePathNodeDto();
            merklePathNodeDtoObj.setHash(merklePathNodesObjMap.getString("Hash",""));
            merklePathNodeDtoObj.setLeftChildNode(merklePathNodesObjMap.getBoolean("IsLeftChildNode",false));
            merklePathDtoObj.getMerklePathNodes().add(merklePathNodeDtoObj);
        }
        return merklePathDtoObj;
    }



    /**
     * Get results of multiple transactions by specified blockHash and the offset.
     * webapi:/api/blockChain/transactionResults
     * @param blockHash
     * @return
     */
    public List<TransactionResultDto>  getTransactionResults(String blockHash) throws Exception{
        return this.getTransactionResults(blockHash,0,10);
    }


    /**
     *  Get information of a block by specified height. Optional whether to include transaction information.
     * @param blockHeight
     * @return
     * @throws Exception
     */
    public BlockDto getBlockByHeight(long blockHeight) throws Exception {
        return this.getBlockByHeight(blockHeight,false);
    }

    /**
     * Get information of a block by given block hash. Optional whether to include transaction information.
     * @param blockHash
     * @return
     * @throws Exception
     */
    public BlockDto getBlockByHash(String blockHash) throws Exception {
        return this.getBlockByHash(blockHash,false);
    }

    private BlockDto createBlockDto(MapEntry block, Boolean includeTransactions) throws Exception {
        if (block == null)
        {
            throw new RuntimeException("not found");
        }
        String heightStr=StringUtil.toString(block.getLinkedHashMap("Header").get("Height"));
        long height=heightStr.length()==0?0:Long.parseLong(heightStr);

        String bloomStr=StringUtil.toString(block.getLinkedHashMap("Header").get("Bloom"));
        bloomStr=bloomStr.length()==0? Base64.encodeBase64String(new byte[256]):bloomStr;

        String timeStr=StringUtil.toString(block.getLinkedHashMap("Header").get("Time"));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        BlockDto blockDto = new BlockDto();
        blockDto.setBlockHash(block.getString("BlockHash"));
        blockDto.setHeader(new BlockHeaderDto());
        blockDto.getHeader().setPreviousBlockHash(StringUtil.toString(block.getLinkedHashMap("Header").get("PreviousBlockHash")));
        blockDto.getHeader().setMerkleTreeRootOfTransactions(StringUtil.toString(block.getLinkedHashMap("Header").get("MerkleTreeRootOfTransactions")));
        blockDto.getHeader().setMerkleTreeRootOfWorldState(StringUtil.toString(block.getLinkedHashMap("Header").get("MerkleTreeRootOfWorldState")));
        blockDto.getHeader().setMerkleTreeRootOfTransactionState(StringUtil.toString(block.getLinkedHashMap("Header").get("MerkleTreeRootOfTransactionStatus")));
        blockDto.getHeader().setExtra(StringUtil.toString(block.getLinkedHashMap("Header").get("Extra")));
        blockDto.getHeader().setHeight(height);
        blockDto.getHeader().setTime(df.parse(timeStr));
        blockDto.getHeader().setChainId(StringUtil.toString(block.getLinkedHashMap("Header").get("ChainId")));
        blockDto.getHeader().setBloom(bloomStr);
        blockDto.getHeader().setSignerPubkey(StringUtil.toString(block.getLinkedHashMap("Header").get("SignerPubkey")));
        if (!includeTransactions) return blockDto;

        String transactionsCountStr=StringUtil.toString(block.getLinkedHashMap("Body").get("TransactionIds"));
        long transactionsCount=Long.parseLong(transactionsCountStr.length()==0?"0":transactionsCountStr);


        List<String> transactions=(List<String>)block.getLinkedHashMap("Body").get("TransactionIds");
        if(transactions==null){
            transactions=new ArrayList<>();
        }
        List txs = new ArrayList<String>();
        for (String transactionId:transactions)
        {
            txs.add(StringUtil.toString(transactionId));
        }
        blockDto.setBody(new BlockBodyDto());
        blockDto.getBody().setTransactionsCount(transactionsCount);
        blockDto.getBody().setTransactions(txs);
        return blockDto;
    }

    private TransactionResultDto createTransactionResultDto(MapEntry transactionResult) throws Exception{
        TransactionResultDto transactionResultObj=new TransactionResultDto();

        transactionResultObj.setTransactionId(transactionResult.getString("TransactionId",""));
        transactionResultObj.setStatus(transactionResult.getString("Status",""));
        transactionResultObj.setBloom(transactionResult.getString("Bloom",""));
        transactionResultObj.setBlockNumber( transactionResult.getLong("BlockNumber",0));
        transactionResultObj.setBlockHash(transactionResult.getString("BlockHash",""));
        transactionResultObj.setReturnValue(transactionResult.getString("ReturnValue",""));
        transactionResultObj.setReadableReturnValue(transactionResult.getString("ReadableReturnValue",""));
        transactionResultObj.setError(transactionResult.getString("Error",""));


        TransactionDto transactionDtoObj=new TransactionDto();
        LinkedHashMap transactionObj=transactionResult.getLinkedHashMap("Transaction",new LinkedHashMap());
        MapEntry transactionObjMap=Maps.cloneMapEntry(transactionObj);
        transactionDtoObj.setFrom(transactionObjMap.getString("From",""));
        transactionDtoObj.setTo(transactionObjMap.getString("To",""));
        transactionDtoObj.setRefBlockNumber(transactionObjMap.getLong("RefBlockNumber",0));
        transactionDtoObj.setRefBlockPrefix(transactionObjMap.getString("RefBlockPrefix",""));
        transactionDtoObj.setMethodName(transactionObjMap.getString("MethodName",""));
        transactionDtoObj.setParams(transactionObjMap.getString("Params",""));
        transactionDtoObj.setSignature(transactionObjMap.getString("Signature",""));
        transactionResultObj.setTransaction(transactionDtoObj);

        TransactionFeeDto transactionFeeDtoObj=new TransactionFeeDto();
        transactionFeeDtoObj.setValue(new HashMap<String,Long>());
        LinkedHashMap transactionFeeObj=transactionResult.getLinkedHashMap("TransactionFee",new LinkedHashMap());
        MapEntry transactionFeeObjMap=Maps.cloneMapEntry(transactionFeeObj);
        LinkedHashMap<String,Integer> transactionFeeValueObjMap= transactionFeeObjMap.getLinkedHashMap("Value",new LinkedHashMap<String,Integer> ());
        Iterator<Map.Entry<String, Integer>> transactionFeeValueObjSets=transactionFeeValueObjMap.entrySet().iterator();
        while(transactionFeeValueObjSets.hasNext()){
            Map.Entry<String, Integer> tmp=transactionFeeValueObjSets.next();
            String key=tmp.getKey();
            Integer valueInteger=tmp.getValue();
            Long valueLong=valueInteger.longValue();
            transactionFeeDtoObj.getValue().put(key,valueLong);
        }
        transactionResultObj.setTransactionFee(transactionFeeDtoObj);


        List<LogEventDto> logEventDtoList=new ArrayList<LogEventDto>();
        List<LinkedHashMap> logsList=transactionResult.getArrayList("Logs",new ArrayList());
        for(LinkedHashMap logsObj:logsList){
            LogEventDto logEventDtoObj=new LogEventDto();
            MapEntry logsObjMap=Maps.cloneMapEntry(logsObj);
            logEventDtoObj.setAddress(logsObjMap.getString("Address",""));
            logEventDtoObj.setName(logsObjMap.getString("Name",""));
            logEventDtoObj.setIndexed(new ArrayList<String>());
            logEventDtoObj.setNonIndexed(logsObjMap.getString("NonIndexed",""));

            List<String> ndexedList=logsObjMap.getArrayList("Indexed",new ArrayList<String>());
            for(String ndexedStr:ndexedList){
                logEventDtoObj.getIndexed().add(ndexedStr);
            }
            logEventDtoList.add(logEventDtoObj);
        }
        transactionResultObj.setLogs(logEventDtoList);
        return transactionResultObj;
    }

    /**
     *  Get id of the chain.
     * @return
     * @throws Exception
     */
    public int getChainIdAsync() throws Exception{
        ChainstatusDto chainStatusDto = this.getChainStatus();
        String base58ChainId = chainStatusDto.getChainId();
        byte[] bytes= Base58.decode(base58ChainId);
        if(bytes.length<4){
            byte[] bs=new byte[4];
            for(int i=0;i<4;i++){
                bs[i]=0;
                if(bytes.length>(i)){
                    bs[i]=bytes[i];
                }
            }
            bytes=bs;
        }
        int chainId = BitConverter.toInt(bytes,0);
        return chainId;
    }
}
