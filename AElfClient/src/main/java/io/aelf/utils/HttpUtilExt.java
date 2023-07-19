package io.aelf.utils;

import io.aelf.network.APIService;
import io.aelf.network.NetworkConnector;
import io.aelf.network.RetrofitFactory;
import org.apache.http.util.TextUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Retrofit;

/**
 * Since the network dependency used by
 * {@link io.aelf.sdk.AElfClient} is {@link Retrofit} now,
 * This class is only for compatibility.
 * <p>
 * If somehow, you are still using this class outside our SDK,
 * consider to use {@link APIService} and see its usage.
 */
@Deprecated
public class HttpUtilExt {

  protected static final Logger logger = LogManager.getLogger(HttpUtilExt.class);

  /**
   * HTTP GET Request help method.
   * @deprecated Use {@link RetrofitFactory} instead.
   */
  @Deprecated
  public static String sendGet(String reqUrl,
      String decodeCharset, String version) {
    logger.debug("Request address:".concat(reqUrl));
    if (TextUtils.isBlank(version)) {
      version = "";
    } else {
      version = ";v=" + version;
    }
    String chainContext = NetworkConnector.getIns().get(reqUrl, decodeCharset, "application/json" + version);
    if (StringUtil.toString(chainContext).length() > 0 && chainContext.contains("@ERROR:@")) {
      chainContext = chainContext.replace("@ERROR:@", "");
      throw new RuntimeException(chainContext);
    }
    logger.debug("Return parameters:".concat(chainContext));
    return chainContext;
  }

  /**
   * HTTP DELETE Request help method.
   * @deprecated Use {@link RetrofitFactory} instead.
   */
  @Deprecated
  public static String sendDelete(String reqUrl, String decodeCharset, String version, String basicAuth) {
    logger.debug("Request address:".concat(reqUrl));
    if (TextUtils.isBlank(version)) {
      version = "";
    } else {
      version = ";v=" + version;
    }
    String chainContext = NetworkConnector.getIns()
        .delete(reqUrl, decodeCharset, "application/json" + version, basicAuth);
    logger.debug("Return parameters:".concat(chainContext));
    return chainContext;
  }

  /**
   * HTTP POST Request help method.
   * @deprecated Use {@link RetrofitFactory} instead.
   */
  @Deprecated
  public static String sendPost(String reqUrl, String params, String version) {
    if (TextUtils.isBlank(version)) {
      version = "";
    } else {
      version = ";v=" + version;
    }
    logger.debug("Request address:".concat(reqUrl));
    String chainContext = NetworkConnector.getIns()
        .post(reqUrl, params, "UTF-8", "UTF-8", "application/json" + version);
    if (StringUtil.toString(chainContext).length() > 0 && chainContext.contains("@ERROR:@")) {
      chainContext = chainContext.replace("@ERROR:@", "");
      throw new RuntimeException(chainContext);
    }
    logger.debug("Return parameters:".concat(chainContext));
    return chainContext;
  }

  /**
   * HTTP POST Request help method.
   * @deprecated Use {@link RetrofitFactory} instead.
   */
  @Deprecated
  public static String sendPostWithAuth(String reqUrl, String params, String version, String authBasic) {
    if (TextUtils.isBlank(version)) {
      version = "";
    } else {
      version = ";v=" + version;
    }
    logger.debug("Request address:".concat(reqUrl));
    String chainContext = NetworkConnector.getIns()
            .post(reqUrl, params, "UTF-8", "UTF-8", "application/json" + version, authBasic);
    if (StringUtil.toString(chainContext).length() > 0 && chainContext.contains("@ERROR:@")) {
      chainContext = chainContext.replace("@ERROR:@", "");
      throw new RuntimeException(chainContext);
    }
    logger.debug("Return parameters:".concat(chainContext));
    return chainContext;
  }
}
