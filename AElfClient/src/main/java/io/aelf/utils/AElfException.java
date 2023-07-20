package io.aelf.utils;

import io.aelf.response.ErrorMessage;
import io.aelf.response.ResultCode;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;

public class AElfException extends RuntimeException {
    public int getResultCode() {
        return resultCode;
    }

    /**
     * an int value that shows the reason.
     * @see ResultCode
     */
    private int resultCode;

    public AElfException(Throwable e) {
        this(e, ResultCode.INTERNAL_ERROR);
    }

    public AElfException(Throwable e, int additionalCode) {
        this(e, ErrorMessage.getBasicErrorMsg(additionalCode));
        this.resultCode = additionalCode;
    }

    public AElfException(Throwable e, int additionalCode, String message, boolean isAdditional) {
        this(e, isAdditional
                ? concatAdditionalMsg(additionalCode, message)
                : message);
        this.resultCode = additionalCode;
    }

    private AElfException(Throwable e, String message) {
        super(message, e);
    }

    public AElfException() {
        this(ResultCode.INTERNAL_ERROR);
    }

    public AElfException(int resultCode) {
        this(resultCode, ErrorMessage.getBasicErrorMsg(resultCode), false);
    }

    public AElfException(int resultCode, String message) {
        this(resultCode, message, true);
    }

    public AElfException(int resultCode, String message, boolean isAdditional) {
        super(isAdditional
                ? concatAdditionalMsg(resultCode, message)
                : message);
        this.resultCode = resultCode;
    }

    @Nonnull
    @Contract(pure = true, value = "_, _ -> !null")
    private static String concatAdditionalMsg(int resultCode, @Nonnull String additionalMsg) {
        return ErrorMessage
                .getBasicErrorMsg(resultCode)
                .concat("\n info: ")
                .concat(additionalMsg);
    }
}
