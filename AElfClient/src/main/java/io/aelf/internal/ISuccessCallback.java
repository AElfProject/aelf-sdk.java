package io.aelf.internal;

@FunctionalInterface
public interface ISuccessCallback<T> {
    void onSuccess(AsyncResult<T> result);
}
