package io.aelf.network.interceptor;

import io.aelf.network.NetworkConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;

@SuppressWarnings("SameParameterValue")
public abstract class AbstractInterceptor implements Interceptor {
    /**
     * Create a new chain object that only the given header content is changed.
     *
     * @param chain      the Chain object
     * @param headerName the header name that is going to change
     * @param content    header content
     * @return a new Chain object
     */
    @Contract(pure = true, value = "_, _, _, -> _")
    protected Request singleHeaderReplacement(@Nonnull Chain chain, @Nonnull String headerName, String content) {
        return chain.request()
                .newBuilder()
                .addHeader(headerName, content)
                .build();
    }

    /**
     * Detect if one particular header content in a Chain object is blank.
     *
     * @param chain      the Chain object
     * @param headerName the header name that is going to detect
     * @return true if header content exists and is not blank
     */
    @Contract(pure = true, value = "_, _, -> _")
    protected boolean isHeaderContentBlank(@Nonnull Chain chain, @Nonnull String headerName) {
        return TextUtils.isBlank(chain.request().header(headerName));
    }

}
