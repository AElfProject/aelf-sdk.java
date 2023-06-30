package io.aelf.utils;

import io.aelf.async.ResultCode;

public class AElfException extends RuntimeException {
    public final int resultCode;

    public AElfException(){
        this(ResultCode.INTERNAL_ERROR,"Internal Error.");
    }

    public AElfException(Throwable e){
        super(e);
        this.resultCode=ResultCode.INTERNAL_ERROR;
    }
    public AElfException(int resultCode,String message) {
        super(message);
        this.resultCode=resultCode;
    }
}
