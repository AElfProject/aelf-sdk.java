package io.aelf.async;

import javax.annotation.Nullable;

class AsyncCommand<T> {
    public final IAsyncFunction<T> function;
    @Nullable
    public final ISuccessCallback<T> successCallback;
    @Nullable
    public final IFailCallback<Void> failCallback;

    public AsyncCommand(IAsyncFunction<T> function) {
        this(function, null, null);
    }

    public AsyncCommand(IAsyncFunction<T> function, ISuccessCallback<T> successCallback) {
        this(function, successCallback, null);
    }

    public AsyncCommand(IAsyncFunction<T> function, @Nullable ISuccessCallback<T> successCallback, @Nullable IFailCallback<Void> failCallback) {
        this.function = function;
        this.successCallback = successCallback;
        this.failCallback = failCallback;
    }

}
