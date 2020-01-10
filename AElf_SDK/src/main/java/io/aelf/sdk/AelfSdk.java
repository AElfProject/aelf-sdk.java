package io.aelf.sdk;
import com.google.protobuf.ByteString;
import com.google.protobuf.StringValue;
import io.aelf.proto.Core;
import io.aelf.schemas.ChainstatusDto;
import io.aelf.schemas.ExecuteTransactionDto;
import io.aelf.utils.*;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
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
    public Core.Transaction.Builder generateTransaction(String from, String to, String methodName, byte [] params) throws Exception {
        ChainstatusDto chainStatus = this.getBlockChainSdkObj().getChainStatus();
        Core.Hash.Builder hash=Core.Hash.newBuilder();
        Core.Transaction.Builder transaction = Core.Transaction.newBuilder();
        Core.Address.Builder addressForm = Core.Address.newBuilder();
        Core.Address.Builder addressTo = Core.Address.newBuilder();
        addressForm.setValue(ByteString.copyFrom(Base58.decodeChecked(from)));
        addressTo.setValue(ByteString.copyFrom(Base58.decodeChecked(to)));
        Core.Address addressFormObj=addressForm.build();
        Core.Address addressToObj=addressTo.build();
        transaction.setFrom(addressFormObj);
        transaction.setTo(addressToObj);
        transaction.setMethodName(methodName);
        hash.setValue(ByteString.copyFrom(params));
        Core.Hash hashObj=hash.build();
        transaction.setParams(hashObj.toByteString());
        transaction.setRefBlockNumber(chainStatus.getBestChainHeight());
        byte[] refBlockPrefix= ByteArrayHelper.hexToByteArray(chainStatus.getBestChainHash());
        refBlockPrefix= Arrays.copyOf(refBlockPrefix,4);
        transaction.setRefBlockPrefix(ByteString.copyFrom(refBlockPrefix));
        return transaction;
    }

    /**
     * Sign a transaction using private key.
     * @param privateKeyHex
     * @param transaction
     * @return
     * @throws Exception
     */
    public String signTransaction(String privateKeyHex, Core.Transaction transaction) throws Exception {

        byte[] transactionData=Sha256.getBytesSHA256(transaction.toByteArray());
        return this.GetSignatureWithPrivateKey(privateKeyHex,transactionData);
    }


    /**
     * Get the address of genesis contract.
     * @return address
     * @throws Exception
     */
    public String getGenesisContractAddressAsync() throws Exception{
        ChainstatusDto chainstatusDto=this.getBlockChainSdkObj().getChainStatus();
        return chainstatusDto.getGenesisContractAddress();
    }

    /**
     * Get the account address through the public key.
     * @param pubKey
     * @return
     */
    public String getAddressFromPubKey(String pubKey)
    {
        byte [] publicKey = ByteArrayHelper.hexToByteArray(pubKey);
        byte[] hashTwice = Sha256Hash.hashTwice(publicKey);
        String address = Base58Ext.encodeChecked(hashTwice);
        return address;
    }

    /**
     * Convert the Address to the displayed string：symbol_base58-string_base58-string-chain-id
     * @param privateKey
     * @param address
     * @return
     * @throws Exception
     */
    public String getFormattedAddress(String privateKey,String address) throws Exception {
        String chainIdString=this.getBlockChainSdkObj().getChainStatus().getChainId();
        String fromAddress=this.getAddressFromPrivateKey(privateKey);
        String toAddress = this.getContractAddressByName(privateKey,Sha256.getBytesSHA256("AElf.ContractNames.Token"));
        String methodName = "GetPrimaryTokenSymbol";
        byte[] bytes=new byte[0];
        Core.Transaction.Builder transaction=this.generateTransaction(fromAddress,toAddress,methodName,bytes);
        String signature=this.signTransaction(privateKey,transaction.build());
        transaction.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
        Core.Transaction transactionObj=transaction.build();
        ExecuteTransactionDto executeTransactionDto=new ExecuteTransactionDto();
        executeTransactionDto.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
        String response=this.blcokChainSdk.executeTransaction(executeTransactionDto);
        StringValue symbol = StringValue.parseFrom(ByteArrayHelper.hexToByteArray(response));
        return symbol.getValue()+"_"+address+"_"+chainIdString;
    }

    /**
     * Get address of a contract by given contractNameHash
     * @param privateKey
     * @param contractNameHash
     * @return
     * @throws Exception
     */
    public String getContractAddressByName(String privateKey,byte[] contractNameHash) throws Exception {
        String from= this.getAddressFromPrivateKey(privateKey);
        String to=this.getGenesisContractAddressAsync();
        String methodName = "GetContractAddressByName";
        Core.Transaction.Builder transaction=this.generateTransaction(from,to,methodName,contractNameHash);
        String signature=this.signTransaction(privateKey,transaction.build());
        transaction.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
        Core.Transaction transactionObj=transaction.build();
        ExecuteTransactionDto executeTransactionDto=new ExecuteTransactionDto();
        executeTransactionDto.setRawTransaction(Hex.toHexString(transactionObj.toByteArray()));
        String response=this.blcokChainSdk.executeTransaction(executeTransactionDto);
        byte[] byteArray = ByteArrayHelper.hexToByteArray(response);
        String base58Str=Base58Ext.encodeChecked(Core.Address.getDefaultInstance().parseFrom(byteArray).getValue().toByteArray());
        return base58Str;

    }

    /**
     * Get address of a contract by given contractNameHash.
     * @param privateKey
     * @return
     */
    public String getAddressFromPrivateKey(String privateKey){
        org.bitcoinj.core.ECKey aelfKey=org.bitcoinj.core.ECKey.fromPrivate(new BigInteger(privateKey,16)).decompress();
        byte[] publicKey = aelfKey.getPubKey();
        byte[] hashTwice = Sha256Hash.hashTwice(publicKey);
        String address = Base58Ext.encodeChecked(hashTwice);
        return address;
    }

    public String GetSignatureWithPrivateKey(String privateKey, byte[] txData) throws Exception {

        BigInteger privKey = new BigInteger(
                privateKey, 16);
        BigInteger pubKey = Sign.publicKeyFromPrivate(privKey);
        ECKeyPair keyPair = new ECKeyPair(privKey, pubKey);
        Sign.SignatureData signature =
                Sign.signMessage(txData, keyPair, false);
        String signatureStr= Hex.toHexString(signature.getR())+Hex.toHexString(signature.getS());
        String res = StringUtil.toString(signature.getV()-27);
        if(res.length()==1){
            res="0"+res;
        }
        signatureStr=signatureStr+(res);
        return signatureStr;


    }

    /**
     * Verify whether this sdk successfully connects the chain.
     * @return IsConnected or not
     */
    public boolean isConnected(){
        try{
            ChainstatusDto chainStatusDto=this.getBlockChainSdkObj().getChainStatus();
            return chainStatusDto!=null;
        }catch(Exception ex){
            return false;
        }

    }




}
