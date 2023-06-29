package io.aelf.async;

import io.aelf.utils.AElfException;

@FunctionalInterface
public interface AsyncFunction<R> {
    AsyncResult<R> run() throws AElfException;
}
