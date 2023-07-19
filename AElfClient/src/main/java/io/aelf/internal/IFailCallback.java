package io.aelf.internal;

@FunctionalInterface
public interface IFailCallback<F> {
    void onFail(AsyncResult<F> reason);
}
