package io.aelf.network;

import okhttp3.Interceptor;
import okhttp3.Response;

import javax.annotation.Nonnull;
import java.io.IOException;

public class CommonHeaderInterceptor implements Interceptor {
    @Nonnull
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(
                chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", encodedContentType)
                        .build()
        );
    }

    public static void setContentType(String contentType) {
        CommonHeaderInterceptor.contentType = contentType;
    }

    public static void setEncodeCharSet(String encodeCharSet) {
        CommonHeaderInterceptor.encodeCharSet = encodeCharSet;
    }

    public static void setVersion(String version) {
        CommonHeaderInterceptor.version = version;
    }

    private static String contentType = DefaultNetworkConfig.DEFAULT_CONTENT_TYPE;
    private static String encodeCharSet = DefaultNetworkConfig.DEFAULT_ENCODE_CHARSET;
    private static String version = DefaultNetworkConfig.DEFAULT_VERSION;

    private static String encodedContentType = contentType;

    private void updateContentType() {
        encodedContentType = contentType +
                "; charset=" +
                encodeCharSet +
                "; v=" +
                version;
    }

}
