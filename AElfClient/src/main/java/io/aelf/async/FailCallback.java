package io.aelf.async;

@FunctionalInterface
public interface FailCallback<F> {
    void onFail(AsyncResult<F> reason);
}
