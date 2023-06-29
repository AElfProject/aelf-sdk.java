package io.aelf.async;

@FunctionalInterface
public interface SuccessCallback<T> {
    void onSuccess(AsyncResult<T> result);
}
