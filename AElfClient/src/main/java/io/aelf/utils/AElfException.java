package io.aelf.utils;

public class AElfException extends RuntimeException {
    public final int resultCode;
    public AElfException(int resultCode,String message) {
        super(message);
        this.resultCode=resultCode;
    }
}
