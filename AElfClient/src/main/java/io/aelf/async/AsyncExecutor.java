package io.aelf.async;

import io.aelf.utils.AElfException;

// Its subClass will determine how to deal with the async calls
public abstract class AsyncExecutor {
    public <T> void enqueueNewRequest(AsyncCommand<T> command){
        this.executeRequest(command);
    }
    protected abstract <T> void executeRequest(AsyncCommand<T> command);
    protected <T> void onNewRequest(AsyncCommand<T> command){
        try{
            AsyncResult<T> result=command.function.run();
            if(result==null){
                throw new AElfException(ResultCode.INTERNAL_ERROR,"AsyncResult is null");
            }else if(!result.isOk()){
                throw new AElfException(result.resultCode,"AsyncResult provides a code that shows a failure");
            }else{
                if(command.successCallback!=null){
                    command.successCallback.onSuccess(result);
                }
            }
        }catch(AElfException e){
            e.printStackTrace();
            if(command.failCallback!=null){
                command.failCallback.onFail(new VoidResult(e.resultCode));
            }
            throw e;
        }
    }
}
