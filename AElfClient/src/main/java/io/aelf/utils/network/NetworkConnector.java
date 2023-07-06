package io.aelf.utils.network;

import io.aelf.async.ResultCode;
import io.aelf.utils.AElfException;
import io.aelf.utils.StringUtil;
import okhttp3.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class NetworkConnector implements NetworkImpl {
    private static final NetworkConnector connector;

    static {
        connector = new NetworkConnector();
    }

    private NetworkConnector() {
    }

    public static NetworkConnector getIns() {
        return connector;
    }

    protected final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(3 * 1000, TimeUnit.MILLISECONDS)
            .writeTimeout(3 * 1000, TimeUnit.MILLISECONDS)
            .readTimeout(5 * 1000, TimeUnit.MILLISECONDS)
            .callTimeout(10 * 1000, TimeUnit.MILLISECONDS)
            .build();


    @Contract(pure = true)
    private String stringEncode(@NotNull String res, @Nullable String decodeCharset) throws NullPointerException {
        return !StringUtil.isBlank(decodeCharset) && !"UTF-8".equals(decodeCharset) ?
                new String(res.getBytes(StandardCharsets.UTF_8), Charset.forName(decodeCharset)) :
                res;
    }

    @Nonnull
    @Contract(pure = true, value = "_ , _ -> !null")
    protected String getContentType(String contentType, String encodeCharSet) {
        String defaultContentType = "application/x-www-form-urlencoded";
        String defaultEncodeCharSet = "charset=utf-8";
        return (StringUtil.isBlank(contentType) ?
                defaultContentType :
                contentType) + "; "
                +
                (StringUtil.isBlank(encodeCharSet) ?
                        defaultEncodeCharSet :
                        "charset=" + encodeCharSet);
    }

    protected String newWorkExecutor(Request.Builder builder, @Nullable String decodeCharset) throws AElfException {
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
    public String sendGet(String reqUrl) {
        return this.sendGet(reqUrl, null);
    }

    @Override
    public String sendGet(String reqUrl, @Nullable String decodeCharset) {
        return this.sendGet(reqUrl, decodeCharset, null);
    }

    @Override
    public String sendGet(String reqUrl, @Nullable String decodeCharset, @Nullable String contentType) throws AElfException {
        Request.Builder request = new Request.Builder()
                .url(reqUrl)
                .addHeader("Content-Type",
                        this.getContentType(contentType, null));
        return this.newWorkExecutor(request, decodeCharset);
    }

    @Override
    public String sendDelete(String reqUrl) {
        return this.sendDelete(reqUrl, null);
    }

    @Override
    public String sendDelete(String reqUrl, @Nullable String decodeCharset) {
        return this.sendDelete(reqUrl, decodeCharset, null);
    }

    @Override
    public String sendDelete(String reqUrl, @Nullable String decodeCharset, @Nullable String contentType) {
        return this.sendDelete(reqUrl, decodeCharset, contentType, null);
    }

    @Override
    public String sendDelete(String reqUrl, @Nullable String decodeCharset, @Nullable String contentType, @Nullable String authBasic) {
        String mContentType = this.getContentType(contentType, null);
        Request.Builder request = new Request.Builder()
                .url(reqUrl)
                .addHeader("Content-Type", mContentType)
                .delete();
        if (!StringUtil.isBlank(authBasic)) {
            request.addHeader("Authorization", authBasic);
        }
        return this.newWorkExecutor(request, decodeCharset);
    }

    @Override
    public String sendPost(String reqUrl, String param) {
        return this.sendPost(reqUrl, param, null);
    }

    @Override
    public String sendPost(String reqUrl, String param, @Nullable String encodeCharset) {
        return this.sendPost(reqUrl, param, encodeCharset, null);
    }

    @Override
    public String sendPost(String reqUrl, String param, @Nullable String encodeCharset,
                           @Nullable String decodeCharset) {
        return this.sendPost(reqUrl, param, encodeCharset, decodeCharset, null);
    }

    @Override
    public String sendPost(String reqUrl, String param, @Nullable String encodeCharset,
                           @Nullable String decodeCharset, @Nullable String contentType) {
        return this.sendPostWithAuth(reqUrl, param, encodeCharset, decodeCharset, contentType, null);
    }

    @Override
    public String sendPostWithAuth(String reqUrl, String param, @Nullable String encodeCharset,
                                   @Nullable String decodeCharset, @Nullable String contentType,
                                   @Nullable String authBasic) throws AElfException {
        String mContentType = this.getContentType(contentType, encodeCharset);
        Request.Builder request = new Request.Builder()
                .url(reqUrl)
                .addHeader("Content-Type", mContentType)
                .post(RequestBody.create(MediaType.parse(mContentType), param));
        if (!StringUtil.isBlank(authBasic)) {
            request.addHeader("Authorization", authBasic);
        }
        return this.newWorkExecutor(request, decodeCharset);
    }
}
