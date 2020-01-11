package io.aelf.utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;
public class HttpClientUtilExt {
    protected static final Logger logger = LogManager.getLogger(HttpClientUtilExt.class);

    /**
     * HTTP GET Request help method
     * @param reqURL
     * @param decodeCharset
     * @return
     * @throws Exception
     */
    public static String sendGetRequest(String reqURL, String decodeCharset)  throws Exception {
        logger.info("Request address:"+reqURL);
        String chainContext=HttpClientUtil.sendGetRequest(reqURL,decodeCharset);
        if(StringUtil.toString(chainContext).length()>0 && chainContext.contains("@ERROR:@")){
            chainContext=chainContext.replace("@ERROR:@","");
            throw new RuntimeException(chainContext);
        }
        logger.info("Return parameters:"+chainContext);
        return chainContext;
    }

    /**
     * HTTP DELETE Request help method
     * @param reqURL
     * @param decodeCharset
     * @return
     */
    public static String sendDeleteRequest(String reqURL, String decodeCharset) {
        logger.info("Request address:"+reqURL);
        String chainContext=HttpClientUtil.sendDeleteRequest(reqURL,decodeCharset);
        logger.info("Return parameters:"+chainContext);
        return chainContext;
    }

    /**
     * HTTP POST Request help method
     * @param reqURL
     * @param params
     * @return
     * @throws Exception
     */
    public static String sendPostRequest(String reqURL, String params) throws Exception{
        logger.info("Request address:"+reqURL);
        String chainContext=HttpClientUtil.sendPostRequest(reqURL,params,"UTF-8","UTF-8","application/json;v=1.0");
        if(StringUtil.toString(chainContext).length()>0 && chainContext.contains("@ERROR:@")){
            chainContext=chainContext.replace("@ERROR:@","");
            throw new RuntimeException(chainContext);
        }
        logger.info("Return parameters:"+chainContext);
        return chainContext;
    }
}
