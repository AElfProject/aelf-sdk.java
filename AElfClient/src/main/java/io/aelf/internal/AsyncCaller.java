package io.aelf.internal;

import io.aelf.utils.AElfException;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class AsyncCaller {
    private final AbstractAsyncExecutor executor;

    public AsyncCaller(AbstractAsyncExecutor executor) {
        this.executor = executor;
    }

    public <T> void asyncCall(IAsyncFunction<T> function,
                              @Nullable ISuccessCallback<T> callback,
                              @Nullable IFailCallback<Void> fail) {
        executor.enqueueNewRequest(new AsyncCommand<>(function, callback, fail));
    }

    // Some methods throw Exception rather than AElfException, this method will
    // convert them.
    @NotNull
    @org.jetbrains.annotations.Contract(pure = true, value = "_ -> !null")
    public static <T> IAsyncFunction<T> convertFunction(@NotNull IFunctionPrimal<T> func) {
        return () -> {
            try {
                return new AsyncResult<>(func.run());
            } catch (Exception e) {
                throw new AElfException(e);
            }
        };
    }

}
