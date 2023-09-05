package io.aelf.network.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@SuppressWarnings("SameParameterValue")
public abstract class AbstractInterceptor implements Interceptor {
    /**
     * Create a new chain object that only the given header content is changed.
     *
     * @param builder    the Chain object builder
     * @param headerName the header name that is going to change
     * @param content    header content
     * @return a new Chain object
     */
    @Contract(pure = true, value = "_, _, _, -> _")
    protected Request.Builder checkAndReplaceHeader(@NotNull Request.Builder builder, @Nonnull String headerName, String content) {
        if(isHeaderContentBlank(builder, headerName)) {
            builder.addHeader(headerName, content);
        }
        return builder;
    }

    /**
     * Detect if one particular header content in a Chain object is blank.
     *
     * @param builder    the Chain object builder
     * @param headerName the header name that is going to detect
     * @return true if header content exists and is not blank
     */
    @Contract(pure = true, value = "_, _, -> _")
    protected boolean isHeaderContentBlank(@NotNull Request.Builder builder, @Nonnull String headerName) {
        return TextUtils.isBlank(builder.build().header(headerName));
    }

}
