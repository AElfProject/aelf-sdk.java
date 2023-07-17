package io.aelf.async;

@FunctionalInterface
public interface IFailCallback<F> {
    void onFail(AsyncResult<F> reason);
}
