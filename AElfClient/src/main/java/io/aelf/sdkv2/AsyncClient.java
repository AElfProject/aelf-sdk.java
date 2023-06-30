package io.aelf.sdkv2;

import io.aelf.async.*;
import io.aelf.async.Void;
import io.aelf.sdk.AElfClient;
import io.aelf.utils.AElfException;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public abstract class AsyncClient extends AElfClient {
    @Nonnull
    private final AsyncCaller caller;
    public AsyncClient(String url) {
        this(url,null);
    }

    public AsyncClient(String url, String version) {
        this(url, version,null,null);
    }

    public AsyncClient(String url, String userName, String password) {
        this(url,null,userName,password);
    }

    public AsyncClient(String url, String version, String userName, String password) {
        this(url,version,userName,password,new AsyncCaller(new DefaultAsyncExecutor()));
    }

    public AsyncClient(String url, String version, String userName, String password, @Nonnull AsyncCaller caller) {
        super(url, version, userName, password);
        this.caller=caller;
    }

    // Some methods throw Exception rather than AElfException, this method will convert them.
    protected <T> AsyncFunction<T> convertFunction(FunctionPrimal<T> func){
        return ()-> {
            try {
                return new AsyncResult<>(func.run());
            } catch (Exception e) {
                throw new AElfException(e);
            }
        };
    }

    public void getBlockHeightAsync(SuccessCallback<Long> callback, FailCallback<Void> onFail){
        this.caller.asyncCall(convertFunction(this::getBlockHeight),callback,onFail);
    }

}

@FunctionalInterface
interface FunctionPrimal<T> {
    T run() throws Exception;
}
