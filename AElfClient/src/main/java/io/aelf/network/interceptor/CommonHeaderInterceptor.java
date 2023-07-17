package io.aelf.network.interceptor;

import io.aelf.network.NetworkConfig;
import okhttp3.Interceptor;
import okhttp3.Response;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

public class CommonHeaderInterceptor extends AbstractInterceptor {
    @Nonnull
    @Override
    public Response intercept(@Nonnull Chain chain) throws IOException {
        if (TextUtils.isBlank(encodedContentType)) {
            updateEntireContentType();
        }
        return chain.proceed(
                isHeaderContentBlank(chain, NetworkConfig.CONTENT_TYPE_HEADER_NAME) ?
                        this.singleHeaderReplacement(chain, NetworkConfig.CONTENT_TYPE_HEADER_NAME, encodedContentType)
                        : chain.request()
        );
    }

    private static String encodedContentType;

    private synchronized static void updateEntireContentType() {
        updateEntireContentType(null, null, null);
    }

    @Contract(pure = true, value = "_, _, _ -> _")
    public synchronized static void updateEntireContentType(@Nullable String contentType, @Nullable String encodeCharSet, @Nullable String version) {
        encodedContentType = stringOrDefault(contentType, NetworkConfig.DEFAULT_CONTENT_TYPE)
                .concat(";charset=")
                .concat(stringOrDefault(encodeCharSet, NetworkConfig.DEFAULT_ENCODE_CHARSET))
                .concat(";v=")
                .concat(stringOrDefault(version, NetworkConfig.DEFAULT_VERSION));
    }

    @Contract(pure = true, value = "_, _ -> _")
    private static String stringOrDefault(String input, @Nonnull String defaultStr) {
        return TextUtils.isBlank(input) ? defaultStr : input;
    }

}
