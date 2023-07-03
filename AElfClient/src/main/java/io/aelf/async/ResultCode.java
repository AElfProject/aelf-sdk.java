package io.aelf.async;

/**
 * Any ResultCode that below zero represents an issue that your code may not expect, such as network disconnection, SDK internal error, etc.
 * Those above zero (expect for SUCCESS=1) represent an issue that your code can prevent from, for example, parameter error.
 */
public interface ResultCode {
    /**
     * Everything works as expected.
     */
    int SUCCESS=1;
    /**
     * Some error happens when deals with your request.
     */
    int INTERNAL_ERROR=-1;
    /**
     * The request reaches the time limit to deal with.
     */
    int TIME_OUT=-2;
    /**
     * It seems that the network is disconnected, check it again.
     */
    int NETWORK_DISCONNECTED=-3;
    /**
     * The peer handled the network request with 403 or other error, check for the reason.
     */
    int PEER_REJECTED=-4;
    /**
     * It means your have error in your parameters, check them before calling APIs.
     */
    int PARAM_ERROR=2;
}
