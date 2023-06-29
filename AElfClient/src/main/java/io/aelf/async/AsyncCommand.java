package io.aelf.async;

import javax.annotation.Nullable;

class AsyncCommand<T> {
    public final AsyncFunction<T> function;
    @Nullable
    public final SuccessCallback<T> successCallback;
    @Nullable
    public final FailCallback<Void> failCallback;

    public AsyncCommand(AsyncFunction<T> function){
        this(function,null,null);
    }

    public AsyncCommand(AsyncFunction<T> function, SuccessCallback<T> successCallback){
        this(function,successCallback,null);
    }

    public AsyncCommand(AsyncFunction<T> function, @Nullable SuccessCallback<T> successCallback, @Nullable FailCallback<Void> failCallback){
        this.function=function;
        this.successCallback=successCallback;
        this.failCallback=failCallback;
    }

}
