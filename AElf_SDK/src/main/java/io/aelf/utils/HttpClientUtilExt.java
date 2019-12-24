package io.aelf.utils;

import io.aelf.sdk.BlcokChainSdk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * @author linhui linhui@tydic.com
 * @title: HttpClientUtilsExt
 * @description: TODO
 * @date 2019/12/1622:58
 */
public class HttpClientUtilExt {
    protected static final Logger logger = LogManager.getLogger(HttpClientUtilExt.class);
    public static String sendGetRequest(String reqURL, String decodeCharset)  throws Exception {
        logger.info("请求地址:"+reqURL);
        String chainContext=HttpClientUtil.sendGetRequest(reqURL,decodeCharset);
        if(StringUtil.toString(chainContext).length()>0 && chainContext.contains("@ERROR:@")){
            chainContext=chainContext.replace("@ERROR:@","");
            throw new RuntimeException(chainContext);
        }
        logger.info("返回参数:"+chainContext);
        return chainContext;
    }
    public static String sendDeleteRequest(String reqURL, String decodeCharset) {
        logger.info("请求地址:"+reqURL);
        String chainContext=HttpClientUtil.sendDeleteRequest(reqURL,decodeCharset);
        logger.info("返回参数:"+chainContext);
        return chainContext;
    }
    public static String sendPostRequest(String reqURL, String params) throws Exception{
        logger.info("请求地址:"+reqURL);
        String chainContext=HttpClientUtil.sendPostRequest(reqURL,params,"UTF-8","UTF-8","application/json;v=1.0");
        if(StringUtil.toString(chainContext).length()>0 && chainContext.contains("@ERROR:@")){
            chainContext=chainContext.replace("@ERROR:@","");
            throw new RuntimeException(chainContext);
        }
        logger.info("返回参数:"+chainContext);
        return chainContext;
    }
}
