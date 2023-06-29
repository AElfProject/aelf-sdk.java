package io.aelf.async;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class AsyncTestLooper<R> {

    private final ResultChecker<AsyncResult<R>> checker;
    private final AssertFailure<AsyncResult<R>> failer;
    private int maxWaitingTime=5000;
    public AsyncTestLooper(ResultChecker<AsyncResult<R>> checker){
        this(checker, null, 0);
    }

    public AsyncTestLooper(ResultChecker<AsyncResult<R>> checker, AssertFailure<AsyncResult<R>> failer){
        this(checker, failer,0);
    }

    public AsyncTestLooper(ResultChecker<AsyncResult<R>> checker, AssertFailure<AsyncResult<R>> failer, int maxWaitingTime){
        this.checker=checker;
        this.failer = failer;
        this.maxWaitingTime=Integer.max(maxWaitingTime,this.maxWaitingTime);
    }

    // Set a determined size, which prevents the looper ends when some of the thread succeed with results while the other threads do not.
    public void setDeterminedSize(int size){
        for(int i=0;i<size;i++){
            this.initHookAtPosition(i);
        }
    }
    private final Map<Integer,AsyncResult<R>> map=new HashMap<>();

    public synchronized void initHookAtPosition(int position){
        map.put(position,null);
    }

    public synchronized void putResultAtPosition(int position,AsyncResult<R> result){
        map.put(position,result);
    }

    public void loop() throws RuntimeException {
        final long start=System.currentTimeMillis();
        while(true){
            try{
                Thread.sleep(10);
            }catch (InterruptedException e){
                throw new RuntimeException("Test Interrupted.");
            }
            if(System.currentTimeMillis()-start>this.maxWaitingTime){
                throw new RuntimeException("Test Timeout reached:"+this.maxWaitingTime+"ms.");
            }
            if(this.checkIsOk()){
                System.out.println("Async Test works well.");
                return;
            }
        }
    }

    public synchronized boolean checkIsOk() throws RuntimeException {
        if(map.isEmpty()) return false;
        for(@Nullable AsyncResult<R> item : map.values()){
            if(item==null || item.result==null || !checker.isOk(item)) return false;
            if(failer!=null && failer.isFailure(item)) throw new RuntimeException("Async Test Failed.");
        }
        return true;
    }
}

interface ResultChecker<R>{
    boolean isOk(@Nullable R item);
}

interface AssertFailure<R>{
    boolean isFailure(@Nullable R item);
}
