package io.aelf.sdk;
import io.aelf.schemas.ChainstatusDto;
import io.aelf.schemas.TransactionDto;
import io.aelf.utils.Base58;
import io.aelf.utils.secp256k1.Bouncycastle_Secp256k1;
import io.aelf.utils.ByteArrayHelper;
import io.aelf.utils.Sha256;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author linhui linhui@tydic.com
 * @title: AelfSdk
 * @description: TODO
 * @date 2019/12/1512:01
 */
public class AelfSdk {
    private String aelfSdkUrl;
    private BlockChainSdk blcokChainSdk;
    private NetSdk netSdk;
    /**
     * Object construction through the url path
     * @param url
     */
    public AelfSdk(String url){
        this.aelfSdkUrl=url;
    }
    private AelfSdk(){}

    /**
     * Get the instance object of BlcokChainSdk
     * @return
     */
    public BlockChainSdk getBlockChainSdkObj(){
        if(blcokChainSdk==null){
            blcokChainSdk=new BlockChainSdk(this.aelfSdkUrl);
        }
        return blcokChainSdk;
    }
    /**
     * Get the instance object of getNetSdkObj
     * @return
     */
    public NetSdk getNetSdkObj(){
        if(netSdk==null){
            netSdk=new NetSdk(this.aelfSdkUrl);
        }
        return netSdk;
    }

    /**
     * Build a transaction from the input parameters.
     * @param from
     * @param to
     * @param methodName
     * @param params
     * @return
     * @throws Exception
     */
    public TransactionDto generateTransaction(String from, String to, String methodName, String params) throws Exception {
        ChainstatusDto chainStatus = this.getBlockChainSdkObj().getChainStatusAsync();
        Base64 base64 = new Base64();
        TransactionDto transaction = new TransactionDto();
        transaction.setFrom(from);
        transaction.setTo(to);
        transaction.setMethodName(methodName);
        transaction.setParams(params);
        transaction.setRefBlockNumber(chainStatus.getBestChainHeight());
        byte[] refBlockPrefix= ByteArrayHelper.hexToByteArray(chainStatus.getBestChainHash());
        refBlockPrefix= Arrays.copyOf(refBlockPrefix,4);
        transaction.setRefBlockPrefix(base64.encodeAsString(refBlockPrefix));
        return transaction;
    }

    public TransactionDto signTransaction(String privateKeyHex,TransactionDto transaction){
        //String sha256= Sha256.getSHA256ForBytes(transaction.toByteArray());
        //byte[] transactionData=ByteString.copyFrom(sha256.getBytes()).toByteArray();
        //SignWithPrivateKey
        return null;
    }


    /**
     * Get the address of genesis contract.
     * @return address
     * @throws Exception
     */
    public String getGenesisContractAddressAsync() throws Exception{
        ChainstatusDto chainstatusDto=this.getBlockChainSdkObj().getChainStatusAsync();
        return chainstatusDto.getGenesisContractAddress();
    }

    public String getAddressFromPrivateKey(String privateKey){
        byte[] pubKey=Bouncycastle_Secp256k1.publicKeyFromPrivate(new BigInteger(privateKey,16)).toByteArray();
        pubKey=Sha256.getBytesSHA256(Sha256.getBytesSHA256(pubKey));
        return Base58.encode(pubKey);
    }

    public String GetSignatureWithToHex(String privateKey, byte[] txData) throws Exception {
        byte[] recSig = new byte[65];

        BigInteger[] sig = Bouncycastle_Secp256k1.sig(txData,ByteArrayHelper.hexToByteArray(privateKey),recSig);
        String signature = sig[0].toString(16)+ sig[1].toString(16);
        //如果长度不够,则前面补0
        if (signature.length() != 128) {
            signature = StringUtils.leftPad(sig[0].toString(16),64,'0') + StringUtils.leftPad(sig[1].toString(16),64,'0');
        }
        signature=signature+"01";
        return signature;
    }

    public boolean isConnected(){
        try{
            ChainstatusDto chainStatusDto=this.getBlockChainSdkObj().getChainStatusAsync();
            return chainStatusDto!=null;
        }catch(Exception ex){
            return false;
        }

    }




}
