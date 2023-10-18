package io.aelf.enums;

public enum TransferredEnum {

    TRANSFERRED("Transferred",0),

    CROSSCHAINTRANSFERRED("CrossChainTransferred",1),

    CROSSCHAINRECEIVED("CrossChainReceived",2);

    private String description;

    private int value;


    TransferredEnum(String description,int value) {
        this.value = value;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
