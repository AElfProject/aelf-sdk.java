package io.aelf.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultAsyncExecutor extends AsyncExecutor{
    private final ExecutorService service = Executors.newCachedThreadPool();
    @Override
    protected <T> void executeRequest(AsyncCommand<T> command) {
        this.service.execute(()->this.onNewRequest(command));
    }
}
