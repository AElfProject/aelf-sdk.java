package io.aelf.internal.sdkv2;

import io.aelf.internal.AsyncCaller;
import io.aelf.internal.DefaultAsyncExecutor;
import retrofit2.Retrofit;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public class AElfClientV2 extends AElfClientAsync {
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

    public AElfClientV2(String url, String version, String userName, String password, @Nullable Retrofit.Builder builder, @Nullable AsyncCaller caller) {
        super(url, version, userName, password, builder, caller);
    }

    @Override
    protected AsyncCaller getCaller() {
        return new AsyncCaller(new DefaultAsyncExecutor());
    }
}
