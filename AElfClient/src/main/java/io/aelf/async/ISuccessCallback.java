package io.aelf.async;

@FunctionalInterface
public interface ISuccessCallback<T> {
    void onSuccess(AsyncResult<T> result);
}
