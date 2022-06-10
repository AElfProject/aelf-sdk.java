package io.aelf.utils;

import io.aelf.utils.Base58Ext;
import io.aelf.utils.ByteArrayHelper;
import io.aelf.protobuf.generated.Client;
import org.bitcoinj.core.Base58;
import com.google.protobuf.ByteString;

public class AddressHelper{
    public static String addressToBase58(Client.Address address) {
        return Base58Ext.encodeChecked(address.getValue().toByteArray());
    }

    public static Client.Address base58ToAddress(String address) {
        Client.Address.Builder addressObj = Client.Address.newBuilder();
        addressObj.setValue(ByteString.copyFrom(Base58.decodeChecked(address)));
        return addressObj.build();
    } 
} 
