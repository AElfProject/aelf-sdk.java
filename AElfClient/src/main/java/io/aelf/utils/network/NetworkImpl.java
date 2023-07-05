package io.aelf.utils.network;

public interface NetworkImpl {
    String sendGet(String reqUrl);
    String sendGet(String reqUrl, String decodeCharset);
    String sendGet(String reqUrl, String decodeCharset, String contentType);
    String sendDelete(String reqUrl);
    String sendDelete(String reqUrl, String decodeCharset);
    String sendDelete(String reqUrl, String decodeCharset, String contentType);
    String sendDelete(String reqUrl, String decodeCharset, String contentType, String authBasic);
    String sendPost(String reqUrl, String param);
    String sendPost(String reqUrl, String param, String encodeCharset);
    String sendPost(String reqUrl, String param, String encodeCharset, String decodeCharset);
    String sendPost(String reqUrl, String param, String encodeCharset, String decodeCharset, String contentType);
    String sendPostWithAuth(String reqUrl, String param, String encodeCharset, String decodeCharset, String contentType, String authBasic);
}
