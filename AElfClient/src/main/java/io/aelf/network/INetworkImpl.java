package io.aelf.network;

public interface INetworkImpl {
    String get(String reqUrl);

    String get(String reqUrl, String decodeCharset);

    String get(String reqUrl, String decodeCharset, String contentType);

    String delete(String reqUrl);

    String delete(String reqUrl, String decodeCharset);

    String delete(String reqUrl, String decodeCharset, String contentType);

    String delete(String reqUrl, String decodeCharset, String contentType, String authBasic);

    String post(String reqUrl, String param);

    String post(String reqUrl, String param, String encodeCharset);

    String post(String reqUrl, String param, String encodeCharset, String decodeCharset);

    String post(String reqUrl, String param, String encodeCharset, String decodeCharset, String contentType);

    String post(String reqUrl, String param, String encodeCharset, String decodeCharset, String contentType, String authBasic);
}
