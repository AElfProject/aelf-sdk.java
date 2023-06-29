package io.aelf.async;

import java.util.LinkedList;
import java.util.Queue;

// Its subClass will determine how to deal with the async calls
@SuppressWarnings("rawtypes")
public abstract class AsyncExecutor {
    protected Queue<AsyncCommand> workQueue=new LinkedList<>();
    public <T> void enqueueNewRequest(AsyncCommand<T> command){
        this.workQueue.offer(command);
    }
}
