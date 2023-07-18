package io.aelf.internal;

import io.aelf.response.ResultCode;
import io.aelf.utils.AElfException;

// Its subClass will determine how to deal with the internal calls
public abstract class AbstractAsyncExecutor {
    public <T> void enqueueNewRequest(AsyncCommand<T> command) {
        this.executeRequest(command);
    }

    protected abstract <T> void executeRequest(AsyncCommand<T> command);

    protected <T> void onNewRequest(AsyncCommand<T> command) {
        try {
            AsyncResult<T> result = command.run();
            if (result == null) {
                throw new AElfException(ResultCode.INTERNAL_ERROR, "AsyncResult is null");
            } else if (!result.isOk()) {
                throw new AElfException(result.resultCode, "AsyncResult provides a code that shows a failure");
            } else {
                command.onSuccess(result);
            }
        } catch (AElfException e) {
            command.onFail(new VoidResult(e.getResultCode()));
            throw e;
        }
    }
}
