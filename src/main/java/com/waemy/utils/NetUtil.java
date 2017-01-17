package com.waemy.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.waemy.web.vo.request.RequestVo;

/**
 * @author Mathew
 */
public class NetUtil {
    
    private static final int DEFAULT_RETRY_COUNT = 3;
    
    private static final int DEFAULT_RETRY_INTERVAL_MILLS = 30000;
    
    private static BasicHeader[] headers = new BasicHeader[10];
    
    private static Logger logger = LoggerFactory.getLogger(NetUtil.class);
    
    static {
        headers[0] = new BasicHeader("Appkey", "");
        headers[1] = new BasicHeader("Udid", "");
        headers[2] = new BasicHeader("Os", "");
        headers[3] = new BasicHeader("Osversion", "");
        headers[4] = new BasicHeader("Appversion", "");
        headers[5] = new BasicHeader("Sourceid", "");
        headers[6] = new BasicHeader("Ver", "");
        headers[7] = new BasicHeader("Userid", "");
        headers[8] = new BasicHeader("Usersession", "");
        headers[9] = new BasicHeader("Unique", "");
    }
    
    /**
     * @param vo
     * @return
     */
    public static Object get(RequestVo vo) {
        DefaultHttpClient client = new DefaultHttpClient();
        // 设置超时30s
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
        HttpGet get = new HttpGet(vo.requestUrl);
        get.setHeaders(headers);
        Object obj = null;
        try {
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info(NetUtil.class.getSimpleName(), result);
                try {
                    obj = vo.jsonParser.parseJSON(result);
                } catch (Exception e) {
                    logger.info(NetUtil.class.getSimpleName(), e.getLocalizedMessage(), e);
                }
                return obj;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Object getWithRetry(RequestVo vo, int retryCount, int retryIntervalMills) {
        int tmpCount = 0;
        Object obj = null;
        
        while (tmpCount < retryCount) {
            obj = get(vo);
            if (null != obj) {
                return obj;
            }
            
            logger.info("will retry, tmpCount=" + tmpCount + ",vo.requestUrl=" + vo.requestUrl);
            
            try {
                Thread.sleep(retryIntervalMills);
            } catch (Exception e) {
                logger.error("failed to sleep");
                e.printStackTrace();
            }
            
            tmpCount++;
        }
        
        return null;
    }
    
    public static Object getWithRetry(RequestVo vo) {
        return getWithRetry(vo, DEFAULT_RETRY_COUNT, DEFAULT_RETRY_INTERVAL_MILLS);
    }
    
    public static Object post(RequestVo vo) {
        DefaultHttpClient client = new DefaultHttpClient();
        // 设置超时30s
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
        HttpPost post = new HttpPost(vo.requestUrl);
        post.setHeaders(headers);
        Object obj = null;
        try {
            if (vo.requestDataMap != null) {
                HashMap<String, String> map = vo.requestDataMap;
                ArrayList<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    BasicNameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                    pairList.add(pair);
                }
                HttpEntity entity = new UrlEncodedFormEntity(pairList, "UTF-8");
                post.setEntity(entity);
            }
            logger.info("before execute post");
            HttpResponse response = client.execute(post);
            logger.info("after execute post:" + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                try {
                    obj = vo.jsonParser.parseJSON(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                    logger.info(NetUtil.class.getSimpleName() + e.getLocalizedMessage() + e);
                }
                return obj;
            }
        } catch (ClientProtocolException e) {
            logger.error(NetUtil.class.getSimpleName() + e.getLocalizedMessage() + e);
        } catch (IOException e) {
            logger.error(NetUtil.class.getSimpleName() + e.getLocalizedMessage() + e);
        }
        return null;
    }
    
    /**
     * @param vo
     * @return
     */
    public static Object getNoParser(RequestVo vo) {
        DefaultHttpClient client = new DefaultHttpClient();
        // 设置超时30s
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
        HttpGet get = new HttpGet(vo.requestUrl);
        get.setHeaders(headers);
        Object obj = null;
        try {
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info(NetUtil.class.getSimpleName(), result);
                try {
                    logger.info("请求返回结果", result);
                    obj = vo.jsonParser.parseJSON(result);
                } catch (Exception e) {
                    logger.info(NetUtil.class.getSimpleName(), e.getLocalizedMessage(), e);
                }
                return obj;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
