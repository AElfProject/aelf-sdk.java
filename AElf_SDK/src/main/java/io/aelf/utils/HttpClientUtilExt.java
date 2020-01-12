package io.aelf.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpClientUtilExt {

  protected static final Logger logger = LogManager.getLogger(HttpClientUtilExt.class);

  /**
   * HTTP GET Request help method.
   */
  public static String sendGetRequest(String reqUrl, String decodeCharset) throws Exception {
    logger.info("Request address:" + reqUrl);
    String chainContext = HttpClientUtil.sendGetRequest(reqUrl, decodeCharset);
    if (StringUtil.toString(chainContext).length() > 0 && chainContext.contains("@ERROR:@")) {
      chainContext = chainContext.replace("@ERROR:@", "");
      throw new RuntimeException(chainContext);
    }
    logger.info("Return parameters:" + chainContext);
    return chainContext;
  }

  /**
   * HTTP DELETE Request help method.
   */
  public static String sendDeleteRequest(String reqUrl, String decodeCharset) {
    logger.info("Request address:" + reqUrl);
    String chainContext = HttpClientUtil.sendDeleteRequest(reqUrl, decodeCharset);
    logger.info("Return parameters:" + chainContext);
    return chainContext;
  }

  /**
   * HTTP POST Request help method.
   */
  public static String sendPostRequest(String reqUrl, String params) throws Exception {
    logger.info("Request address:" + reqUrl);
    String chainContext = HttpClientUtil
        .sendPostRequest(reqUrl, params, "UTF-8", "UTF-8", "application/json;v=1.0");
    if (StringUtil.toString(chainContext).length() > 0 && chainContext.contains("@ERROR:@")) {
      chainContext = chainContext.replace("@ERROR:@", "");
      throw new RuntimeException(chainContext);
    }
    logger.info("Return parameters:" + chainContext);
    return chainContext;
  }
}
