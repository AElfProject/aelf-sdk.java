package io.aelf.network;

import io.aelf.response.ResultCode;
import io.aelf.network.interceptor.CommonHeaderInterceptor;
import io.aelf.utils.AElfException;
import okhttp3.*;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class NetworkConnector implements INetworkImpl {
    private static final NetworkConnector connector;

    static {
        connector = new NetworkConnector();
    }

    private NetworkConnector() {
    }

    public static NetworkConnector getIns() {
        return connector;
    }

    public OkHttpClient getClient() {
        return client;
    }

    protected final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(NetworkConfig.TIME_OUT_LIMIT, TimeUnit.MILLISECONDS)
            .writeTimeout(NetworkConfig.TIME_OUT_LIMIT, TimeUnit.MILLISECONDS)
            .readTimeout(NetworkConfig.TIME_OUT_LIMIT, TimeUnit.MILLISECONDS)
            .callTimeout(NetworkConfig.TIME_OUT_LIMIT, TimeUnit.MILLISECONDS)
            .addInterceptor(new CommonHeaderInterceptor())
            .build();


    @Contract(pure = true)
    private String stringEncode(@NotNull String res, @Nullable String decodeCharset) throws NullPointerException {
        return !TextUtils.isBlank(decodeCharset) && !"UTF-8".equalsIgnoreCase(decodeCharset) ?
                new String(res.getBytes(StandardCharsets.UTF_8), Charset.forName(decodeCharset)) :
                res;
    }

    @Nonnull
    @Contract(pure = true, value = "_ , _ -> !null")
    protected String getContentType(String contentType, String encodeCharSet) {
        return (TextUtils.isBlank(contentType) ?
                NetworkConfig.DEFAULT_CONTENT_TYPE :
                contentType)
                + "; charset=" +
                (TextUtils.isBlank(encodeCharSet) ?
                        NetworkConfig.DEFAULT_ENCODE_CHARSET :
                        encodeCharSet);
    }

    protected String startNetworkAndGetResult(Request.Builder builder, @Nullable String decodeCharset) throws AElfException {
        try (Response response = client.newCall(builder.build()).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new AElfException(ResultCode.INTERNAL_ERROR, "Network failure");
            }
            String res = response.body().string();
            return this.stringEncode(res, decodeCharset);
        } catch (IOException | NullPointerException e) {
            throw new AElfException(e);
        }
    }


    @Override
    public String get(String reqUrl) {
        return this.get(reqUrl, null);
    }

    @Override
    public String get(String reqUrl, @Nullable String decodeCharset) {
        return this.get(reqUrl, decodeCharset, null);
    }

    @Override
    public String get(String reqUrl, @Nullable String decodeCharset, @Nullable String contentType) throws AElfException {
        Request.Builder request = new Request.Builder()
                .url(reqUrl)
                .addHeader("Content-Type",
                        this.getContentType(contentType, null));
        return this.startNetworkAndGetResult(request, decodeCharset);
    }

    @Override
    public String delete(String reqUrl) {
        return this.delete(reqUrl, null);
    }

    @Override
    public String delete(String reqUrl, @Nullable String decodeCharset) {
        return this.delete(reqUrl, decodeCharset, null);
    }

    @Override
    public String delete(String reqUrl, @Nullable String decodeCharset, @Nullable String contentType) {
        return this.delete(reqUrl, decodeCharset, contentType, null);
    }

    @Override
    public String delete(String reqUrl, @Nullable String decodeCharset, @Nullable String contentType, @Nullable String authBasic) {
        String mContentType = this.getContentType(contentType, null);
        Request.Builder request = new Request.Builder()
                .url(reqUrl)
                .addHeader("Content-Type", mContentType)
                .delete();
        if (!TextUtils.isBlank(authBasic)) {
            request.addHeader("Authorization", authBasic);
        }
        return this.startNetworkAndGetResult(request, decodeCharset);
    }

    @Override
    public String post(String reqUrl, String param) {
        return this.post(reqUrl, param, null);
    }

    @Override
    public String post(String reqUrl, String param, @Nullable String encodeCharset) {
        return this.post(reqUrl, param, encodeCharset, null);
    }

    @Override
    public String post(String reqUrl, String param, @Nullable String encodeCharset,
                       @Nullable String decodeCharset) {
        return this.post(reqUrl, param, encodeCharset, decodeCharset, null);
    }

    @Override
    public String post(String reqUrl, String param, @Nullable String encodeCharset,
                       @Nullable String decodeCharset, @Nullable String contentType) {
        return this.post(reqUrl, param, encodeCharset, decodeCharset, contentType, null);
    }

    @Override
    public String post(String reqUrl, String param, @Nullable String encodeCharset,
                       @Nullable String decodeCharset, @Nullable String contentType,
                       @Nullable String authBasic) throws AElfException {
        String mContentType = this.getContentType(contentType, encodeCharset);
        Request.Builder request = new Request.Builder()
                .url(reqUrl)
                .addHeader("Content-Type", mContentType)
                .post(RequestBody.create(MediaType.parse(mContentType), param));
        if (!TextUtils.isBlank(authBasic)) {
            request.addHeader("Authorization", authBasic);
        }
        return this.startNetworkAndGetResult(request, decodeCharset);
    }
}
