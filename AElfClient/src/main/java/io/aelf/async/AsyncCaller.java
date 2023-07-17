package io.aelf.async;

import javax.annotation.Nullable;

public class AsyncCaller {
    private final AbstractAsyncExecutor executor;

    public AsyncCaller(AbstractAsyncExecutor executor) {
        this.executor = executor;
    }

    public <T> void asyncCall(IAsyncFunction<T> function) {
        this.asyncCall(function, null);
    }

    public <T> void asyncCall(IAsyncFunction<T> function, @Nullable ISuccessCallback<T> callback) {
        this.asyncCall(function, callback, null);
    }

    public <T> void asyncCall(IAsyncFunction<T> function, ISuccessCallback<T> callback, IFailCallback<Void> fail) {
        executor.enqueueNewRequest(new AsyncCommand<>(function, callback, fail));
    }

}
