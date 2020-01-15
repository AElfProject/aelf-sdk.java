package io.aelf.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpUtilExt {

  protected static final Logger logger = LogManager.getLogger(HttpUtilExt.class);

  /**
   * HTTP GET Request help method.
   */
  public static String sendGet(String reqUrl,
      String decodeCharset, String version) throws Exception {
    logger.info("Request address:" + reqUrl);
    if (StringUtil.isBlank(version)) {
      version = "";
    } else {
      version = ";v=" + version;
    }
    String chainContext = ClientUtil.sendGet(reqUrl, decodeCharset, "application/json" + version);
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
  public static String sendDelete(String reqUrl, String decodeCharset, String version) {
    logger.info("Request address:" + reqUrl);
    if (StringUtil.isBlank(version)) {
      version = "";
    } else {
      version = ";v=" + version;
    }
    String chainContext = ClientUtil
        .sendDelete(reqUrl, decodeCharset, "application/json" + version);
    logger.info("Return parameters:" + chainContext);
    return chainContext;
  }

  /**
   * HTTP POST Request help method.
   */
  public static String sendPost(String reqUrl, String params, String version) throws Exception {
    if (StringUtil.isBlank(version)) {
      version = "";
    } else {
      version = ";v=" + version;
    }
    logger.info("Request address:" + reqUrl);
    String chainContext = ClientUtil
        .sendPost(reqUrl, params, "UTF-8", "UTF-8", "application/json" + version);
    if (StringUtil.toString(chainContext).length() > 0 && chainContext.contains("@ERROR:@")) {
      chainContext = chainContext.replace("@ERROR:@", "");
      throw new RuntimeException(chainContext);
    }
    logger.info("Return parameters:" + chainContext);
    return chainContext;
  }
}
