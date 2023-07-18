package io.aelf.internal;

import io.aelf.network.NetworkConfig;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DefaultAsyncExecutor extends AbstractAsyncExecutor {

    interface ExecutorConfig {
        int CORE_THREAD = 3;
        int MAX_THREAD = 10;
    }

    private final ThreadPoolExecutor service = new ThreadPoolExecutor(
            ExecutorConfig.CORE_THREAD,
            ExecutorConfig.MAX_THREAD,
            NetworkConfig.TIME_OUT_LIMIT * 2,
            TimeUnit.MILLISECONDS,
            new SynchronousQueue<>()
    );

    @Override
    protected <T> void executeRequest(AsyncCommand<T> command) {
        this.service.execute(() -> this.onNewRequest(command));
    }
}
