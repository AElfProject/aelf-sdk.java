package io.aelf.utils;

import io.aelf.sdk.AElfClient;
import io.aelf.sdkv2.AElfClientV2;
import io.aelf.utils.network.NetworkConnector;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is deprecated since it is using raw {@link HttpClient}
 * which is not recommended now.<br/>
 * Do not use it, and it might be removed in the future update.<br/>
 * If somehow you are still using it from outside our SDK, consider refactoring your code to
 * the new form WebSDK {@link NetworkConnector} and use {@link AElfClientV2} instead of
 * deprecated {@link AElfClient}.
 * @see AElfClientV2
 * @see NetworkConnector
 */
@SuppressWarnings("unused")
@Deprecated
public class ClientUtil {

  protected static final Logger logger = LogManager.getLogger(ClientUtil.class);

  private ClientUtil() {
  }

  private static HttpClient setProxy(Integer... connectTimeout) {
    String proxySet = StringUtil.toString(System.getProperty("proxySet"));
    if (!"true".equals(proxySet)) {
      if (connectTimeout != null && connectTimeout.length > 0) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout[0]).build();
        return HttpClients.custom().setDefaultRequestConfig(config).build();
      } else {
        return new DefaultHttpClient();
      }
    } else {
      String proxyType = System.getProperty("proxyType");
      String host = System.getProperty(proxyType + ".proxyHost");
      int port = Integer.parseInt(System.getProperty(proxyType + ".proxyPort"));
      HttpHost proxy = new HttpHost(host, port, proxyType);
      RequestConfig config;
      if (connectTimeout != null && connectTimeout.length > 0) {
        config = RequestConfig.custom().setConnectTimeout(connectTimeout[0]).setProxy(proxy)
            .build();
      } else {
        config = RequestConfig.custom().setProxy(proxy).build();
      }

      return HttpClients.custom().setDefaultRequestConfig(config).build();
    }
  }

  /**
   * Http Get Request Util.
   *
   * @param reqUrl not blank
   * @param decodeCharset not blank
   * @return str
   */
  public static String sendGet(String reqUrl, String decodeCharset, String contentType) {
    long responseLength = 0L;
    String responseContent = null;
    HttpGet httpGet = new HttpGet(reqUrl);

    try( DefaultHttpClient httpClient = new DefaultHttpClient() ) {
      setProxy();
      if (StringUtils.isBlank(contentType)) {
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
      } else {
        httpGet.setHeader("Content-Type", contentType);
      }

      HttpResponse response = httpClient.execute(httpGet);
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        responseLength = entity.getContentLength();
        responseContent = EntityUtils
            .toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
        EntityUtils.consume(entity);
      }
      if (200 != response.getStatusLine().getStatusCode()) {
        responseContent = "@ERROR:@" + responseContent;
      }
      logger.debug("Request address:" + httpGet.getURI());
      logger.debug("Response status:" + response.getStatusLine());
      logger.debug("Response length:" + responseLength);
      logger.debug("Response content:" + responseContent);
    } catch (Exception ex) {
      responseContent = "@ERROR:@" + ex.getMessage();
      logger.debug("sendGet Exception:", ex);
    }

    return responseContent;
  }

  /**
   * Http Delete Request Util.
   *
   * @param reqUrl not blank
   * @param decodeCharset not blank
   */
  public static String sendDelete(String reqUrl, String decodeCharset, String contentType, String authBasic) {
    long responseLength = 0L;
    String responseContent = null;
    HttpDelete httpDelete = new HttpDelete(reqUrl);

    try(DefaultHttpClient httpClient = new DefaultHttpClient()) {
      setProxy();
      if (StringUtils.isBlank(contentType)) {
        httpDelete.setHeader("Content-Type", "application/x-www-form-urlencoded");
      } else {
        httpDelete.setHeader("Content-Type", contentType);
      }

      if (!StringUtils.isBlank(authBasic)) {
        httpDelete.setHeader("Authorization", authBasic);
      }

      HttpResponse response = httpClient.execute(httpDelete);
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        responseLength = entity.getContentLength();
        responseContent = EntityUtils
            .toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
        EntityUtils.consume(entity);
      }
      if (200 != response.getStatusLine().getStatusCode()) {
        responseContent = "@ERROR:@" + responseContent;
      }

      logger.debug("Request address:" + httpDelete.getURI());
      logger.debug("Response status:" + response.getStatusLine());
      logger.debug("Response length:" + responseLength);
      logger.debug("Response content:" + responseContent);
    } catch (Exception ex) {
      logger.error("sendDelete Exception:", ex);
      responseContent = "@ERROR:@" + ex.getMessage();
    }

    return responseContent;
  }

  /**
   * Http Post Request Util.
   *
   * @param reqUrl not blank
   * @param param not blank
   * @param encodeCharset not blank
   * @param decodeCharset not blank
   * @param contentType not blank
   */
  public static String sendPost(String reqUrl, String param, String encodeCharset,
      String decodeCharset, String contentType) {
    String responseContent = null;
    HttpClient httpClient = setProxy();
    HttpPost httpPost = new HttpPost(reqUrl);

    try {
      StringEntity myEntity = new StringEntity(param, encodeCharset);
      if (StringUtils.isBlank(contentType)) {
        myEntity.setContentType("application/x-www-form-urlencoded");
      } else {
        myEntity.setContentType(contentType);
      }

      httpPost.setEntity(myEntity);
      HttpResponse response = httpClient.execute(httpPost);
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        responseContent = EntityUtils
            .toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
        EntityUtils.consume(entity);
      }
      if (200 != response.getStatusLine().getStatusCode()) {
        responseContent = "@ERROR:@" + responseContent;
      }
    } catch (Exception ex) {
      logger.error("sendPost Exception:", ex);
      responseContent = "@ERROR:@" + ex.getMessage();
    } finally {
      httpClient.getConnectionManager().shutdown();
    }

    return responseContent;
  }

  public static String sendPostWithAuth(String reqUrl, String param, String encodeCharset,
                                        String decodeCharset, String contentType, String authBasic) {
    String responseContent = null;
    HttpClient httpClient = setProxy();
    HttpPost httpPost = new HttpPost(reqUrl);
    try {
      StringEntity myEntity = new StringEntity(param, encodeCharset);
      if (StringUtils.isBlank(contentType)) {
        myEntity.setContentType("application/x-www-form-urlencoded");
      } else {
        myEntity.setContentType(contentType);
      }

      if (StringUtils.isNotBlank(authBasic)) {
        httpPost.setHeader("Authorization", authBasic);
      }

      httpPost.setEntity(myEntity);

      System.out.print(httpPost.getFirstHeader("Content-Type"));
      HttpResponse response = httpClient.execute(httpPost);
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        responseContent = EntityUtils
                .toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
        EntityUtils.consume(entity);
      }
      if (200 != response.getStatusLine().getStatusCode()) {
        responseContent = "@ERROR:@" + responseContent;
      }
    } catch (Exception ex) {
      logger.error("sendPost Exception:", ex);
      responseContent = "@ERROR:@" + ex.getMessage();
    } finally {
      httpClient.getConnectionManager().shutdown();
    }

    return responseContent;
  }
}
