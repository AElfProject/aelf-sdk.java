package io.aelf.internal;

import io.aelf.utils.AElfException;

@FunctionalInterface
public interface IAsyncFunction<R> {
    AsyncResult<R> run() throws AElfException;
}
