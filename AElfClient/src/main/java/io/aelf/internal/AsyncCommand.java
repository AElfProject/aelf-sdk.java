package io.aelf.internal;

import javax.annotation.Nullable;

class AsyncCommand<T> implements IAsyncFunction<T>, ISuccessCallback<T>, IFailCallback<Void> {
    private final IAsyncFunction<T> function;
    @Nullable
    private final ISuccessCallback<T> successCallback;
    @Nullable
    private final IFailCallback<Void> failCallback;

    public AsyncCommand(IAsyncFunction<T> function) {
        this(function, null, null);
    }

    public AsyncCommand(IAsyncFunction<T> function, ISuccessCallback<T> successCallback) {
        this(function, successCallback, null);
    }

    public AsyncCommand(IAsyncFunction<T> function,
                        @Nullable ISuccessCallback<T> successCallback,
                        @Nullable IFailCallback<Void> failCallback) {
        this.function = function;
        this.successCallback = successCallback;
        this.failCallback = failCallback;
    }

    @Override
    public AsyncResult<T> run() {
        return this.function.run();
    }

    @Override
    public void onSuccess(AsyncResult<T> result) {
        if (this.successCallback != null) {
            this.successCallback.onSuccess(result);
        }
    }

    @Override
    public void onFail(AsyncResult<Void> reason) {
        if (this.failCallback != null) {
            this.failCallback.onFail(reason);
        }
    }

}
