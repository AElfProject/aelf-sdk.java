package io.aelf.async;

import io.aelf.utils.AElfException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleAsyncCallerTest {
    private AsyncCaller caller;

    @Before
    public void init(){
        caller=new AsyncCaller(new TestAsyncExecutor());
    }

    @Test
    public void AsyncTest() throws AElfException {
        AsyncTestLooper<String> looper=new AsyncTestLooper<>(response->response!=null && response.isOk(),
                response-> response==null || !response.isOk());
        int size=100;
        looper.setDeterminedSize(size);
        for(int i=0;i<size;i++){
            int finalI = i;
            caller.asyncCall(()->{
                try{
                    Thread.sleep(200+ finalI);
                }catch (InterruptedException e){
                    return new AsyncResult<>(ResultCode.SUCCESS,"ERROR"+finalI);
                }
                return new AsyncResult<>(ResultCode.SUCCESS,"OK"+finalI);
            },
                    response-> {
                        System.out.println("It's thread "+finalI+" and the response is "+response.result);
                        Assert.assertEquals("OK"+finalI,response.result);
                        looper.putResultAtPosition(finalI,response);
                    },
                    fail->{
                        System.out.println("It's thread "+finalI+" and it failed.");
                        throw new AElfException(fail.resultCode,"Test Error");
                    }
            );
        }
        looper.loop();
    }
}

class TestAsyncExecutor extends AsyncExecutor {
    @Override
    public <T> void enqueueNewRequest(AsyncCommand<T> command) {
        new Thread(()->{
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
        }).start();
    }
}