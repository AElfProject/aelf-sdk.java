package io.aelf.sdk;

import io.aelf.schemas.ChainstatusDto;
import io.aelf.schemas.TransactionDto;
import io.aelf.utils.ByteArrayHelper;

import java.util.Arrays;

/**
 * @author linhui linhui@tydic.com
 * @title: AelfSdk
 * @description: TODO
 * @date 2019/12/1512:01
 */
public class AelfSdk {
    private String aelfSdkUrl;
    private BlcokChainSdk blcokChainSdk;
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
    public BlcokChainSdk getBlcokChainSdkObj(){
        if(blcokChainSdk==null){
            blcokChainSdk=new BlcokChainSdk(this.aelfSdkUrl);
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
        ChainstatusDto chainStatus = this.getBlcokChainSdkObj().getChainStatusAsync();
        TransactionDto transaction = new TransactionDto();
        transaction.setFrom(from);
        transaction.setTo(to);
        transaction.setMethodName(methodName);
        transaction.setParams(params);
        transaction.setRefBlockNumber(chainStatus.getBestChainHeight());
        byte[] refBlockPrefix= ByteArrayHelper.hexToByteArray(chainStatus.getBestChainHash());
        refBlockPrefix= Arrays.copyOf(refBlockPrefix,4);
        transaction.setRefBlockPrefix(new String(refBlockPrefix));
        return transaction;
    }

    /**
     * Get the address of genesis contract.
     * @return address
     * @throws Exception
     */
    public String getGenesisContractAddressAsync() throws Exception{
        ChainstatusDto chainstatusDto=this.getBlcokChainSdkObj().getChainStatusAsync();
        return chainstatusDto.getGenesisContractAddress();
    }

    /**
     * Get the account address through the private key.
     * @param privateKeyHex
     * @return
     */
    public String getAddressFromPrivateKey(String privateKeyHex){
        GetAElfKeyPair(privateKeyHex);
        return "";
    }

    public String GetAElfKeyPair(String privateKeyHex){
        byte[] privateKey = ByteArrayHelper.hexToByteArray(privateKeyHex);
        return "";
    }

}
