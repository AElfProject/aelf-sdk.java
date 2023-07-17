package io.aelf.async;

@FunctionalInterface
public interface IFunctionPrimal<T> {
    T run() throws Exception;
}
