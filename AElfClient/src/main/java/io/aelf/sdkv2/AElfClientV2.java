package io.aelf.sdkv2;

import io.aelf.async.AsyncCaller;
import io.aelf.async.DefaultAsyncExecutor;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public class AElfClientV2 extends ClientAsync {
    public AElfClientV2(String url) {
        super(url);
    }

    public AElfClientV2(String url, String version) {
        super(url, version);
    }

    public AElfClientV2(String url, String userName, String password) {
        super(url, userName, password);
    }

    public AElfClientV2(String url, String version, String userName, String password) {
        super(url, version, userName, password);
    }

    public AElfClientV2(String url, String version, String userName, String password, @Nullable AsyncCaller caller) {
        super(url, version, userName, password, caller);
    }

    @Override
    protected AsyncCaller getCaller() {
        return new AsyncCaller(new DefaultAsyncExecutor());
    }
}
