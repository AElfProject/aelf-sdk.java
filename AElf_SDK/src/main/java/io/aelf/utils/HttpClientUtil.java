package io.aelf.utils;

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

public class HttpClientUtil {

  protected static final Logger logger = LogManager.getLogger(HttpClientUtil.class);

  private HttpClientUtil() {
  }

  private static HttpClient setProxy(Integer... connectTimeout) {
    String proxySet = StringUtil.toString(System.getProperty("proxySet"));
    if (!"true".equals(proxySet)) {
      if (connectTimeout != null && connectTimeout.length > 0) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout[0]).build();
        HttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        return httpClient;
      } else {
        HttpClient httpClient = new DefaultHttpClient();
        return httpClient;
      }
    } else {
      String proxyType = System.getProperty("proxyType");
      String host = System.getProperty(proxyType + ".proxyHost");
      int port = Integer.parseInt(System.getProperty(proxyType + ".proxyPort"));
      HttpHost proxy = new HttpHost(host, port, proxyType);
      RequestConfig config = null;
      if (connectTimeout != null && connectTimeout.length > 0) {
        config = RequestConfig.custom().setConnectTimeout(connectTimeout[0]).setProxy(proxy)
            .build();
      } else {
        config = RequestConfig.custom().setProxy(proxy).build();
      }

      HttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
      return httpClient;
    }
  }

  /**
   * Http Get Request Util.
   * @param reqUrl not blank
   * @param decodeCharset not blank
   * @return str
   */
  public static String sendGetRequest(String reqUrl, String decodeCharset) {
    long responseLength = 0L;
    String responseContent = null;
    HttpClient httpClient = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet(reqUrl);

    try {
      setProxy();
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
      logger.info("Request address:" + httpGet.getURI());
      logger.info("Response status:" + response.getStatusLine());
      logger.info("Response length:" + responseLength);
      logger.info("Response content:" + responseContent);
    } catch (Exception ex) {
      responseContent = "@ERROR:@" + ex.getMessage();
      logger.info("sendGetRequest Exception:", ex);
    } finally {
      httpClient.getConnectionManager().shutdown();
    }

    return responseContent;
  }

  /**
   * Http Delete Request Util.
   * @param reqUrl not blank
   * @param decodeCharset not blank
   * @return
   */
  public static String sendDeleteRequest(String reqUrl, String decodeCharset) {
    long responseLength = 0L;
    String responseContent = null;
    HttpClient httpClient = new DefaultHttpClient();
    HttpDelete httpDelete = new HttpDelete(reqUrl);

    try {
      setProxy();
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

      logger.info("Request address:" + httpDelete.getURI());
      logger.info("Response status:" + response.getStatusLine());
      logger.info("Response length:" + responseLength);
      logger.info("Response content:" + responseContent);
    } catch (Exception ex) {
      logger.info("sendDeleteRequest Exception:", ex);
      responseContent = "@ERROR:@" + ex.getMessage();
    } finally {
      httpClient.getConnectionManager().shutdown();
    }

    return responseContent;
  }

  /**
   * Http Post Request Util.
   * @param reqUrl not blank
   * @param param not blank
   * @param encodeCharset not blank
   * @param decodeCharset not blank
   * @param contentType not blank
   * @return
   */
  public static String sendPostRequest(String reqUrl, String param, String encodeCharset,
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
      logger.info("sendPostRequest Exception:", ex);
      responseContent = "@ERROR:@" + ex.getMessage();
    } finally {
      httpClient.getConnectionManager().shutdown();
    }

    return responseContent;
  }
}
