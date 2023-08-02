package io.aelf.network;

public interface NetworkConfig {
    String DEFAULT_CONTENT_TYPE = "application/json";
    String DEFAULT_VERSION = "1.0";
    String DEFAULT_ENCODE_CHARSET = "utf-8";
    Integer TIME_OUT_LIMIT = 15 * 1000;
    String CONTENT_TYPE_HEADER_NAME = "Content-Type";
}
