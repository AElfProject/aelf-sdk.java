package io.aelf.response;

import org.jetbrains.annotations.Contract;

/**
 * @see ResultCode
 */
public interface ErrorMessage {
    String SUCCESS = "success.";
    String INTERNAL_ERROR = "there's some issues inside SDK's code," +
            " check the position where this Exception was thrown.";
    String TIME_OUT = "network request timeout reached.";
    String NETWORK_DISCONNECTED = "it seems that you have trouble in network connection.";
    String PEER_REJECTED = "the peer rejected the connection request.";
    String PARAM_ERROR = "you have trouble in your params, check them again.";

    @Contract(pure = true, value = "_ -> !null")
    static String getBasicErrorMsg(int resultCode) {
        switch (resultCode) {
            case ResultCode.SUCCESS: {
                return SUCCESS;
            }
            case ResultCode.PARAM_ERROR: {
                return PARAM_ERROR;
            }
            case ResultCode.NETWORK_DISCONNECTED: {
                return NETWORK_DISCONNECTED;
            }
            case ResultCode.TIME_OUT: {
                return TIME_OUT;
            }
            case ResultCode.PEER_REJECTED: {
                return PEER_REJECTED;
            }
            case ResultCode.INTERNAL_ERROR:
            default: {
                return INTERNAL_ERROR;
            }
        }
    }
}
