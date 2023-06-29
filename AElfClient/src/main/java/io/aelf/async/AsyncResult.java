package io.aelf.async;

import javax.annotation.Nullable;

public class AsyncResult<R>{
    public int resultCode;

    @Nullable
    public final R result;

    public AsyncResult(int resultCode){
        this(resultCode,null);
    }

    public AsyncResult(int resultCode,@Nullable R result){
        this.resultCode=resultCode;
        this.result=result;
    }

    public boolean isOk(){
        return this.resultCode==ResultCode.SUCCESS;
    }
}
