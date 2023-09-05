package io.aelf.internal;

@FunctionalInterface
public interface IFunctionPrimal<T> {
    T run() throws Exception;
}
