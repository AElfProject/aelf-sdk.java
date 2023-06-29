package io.aelf.async;

import javax.annotation.Nullable;

public class AsyncCaller {
    private final AsyncExecutor executor;

    public AsyncCaller(AsyncExecutor executor){
        this.executor=executor;
    }

    public <T> void asyncCall(AsyncFunction<T> function){
        this.asyncCall(function,null);
    }
    public <T> void asyncCall(AsyncFunction<T> function,@Nullable SuccessCallback<T> callback){
        this.asyncCall(function,callback,null);
    }

    public <T> void asyncCall(AsyncFunction<T> function,SuccessCallback<T> callback,FailCallback<Void> fail){
        executor.enqueueNewRequest(new AsyncCommand<>(function,callback,fail));
    }

}
