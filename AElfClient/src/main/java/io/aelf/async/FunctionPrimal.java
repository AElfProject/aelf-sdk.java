package io.aelf.async;

@FunctionalInterface
public interface FunctionPrimal<T> {
    T run() throws Exception;
}
