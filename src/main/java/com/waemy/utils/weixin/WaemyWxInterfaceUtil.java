package com.waemy.utils.weixin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.waemy.utils.DateUtil;
import com.waemy.utils.NetUtil;
import com.waemy.utils.RandomStringUtil;
import com.waemy.web.vo.parse.AccessTokenRespParser;
import com.waemy.web.vo.request.RequestVo;
import com.waemy.web.vo.weixin.AccessToken;
import com.waemy.web.vo.weixin.DateByWxAccessTokenAndJsApiTicketVO;
import com.waemy.web.vo.weixin.JsApiTicket;
import com.waemy.web.vo.weixin.WeiXinJsApiParamsVO;

/**
 * @ClassName: WaemyWxInterfaceUtil
 * @Description: wx接口的请求工具类
 * @date 2016年12月2日
 */
public class WaemyWxInterfaceUtil {

    private static Logger logger = LoggerFactory.getLogger(WaemyWxInterfaceUtil.class);

    //    public final static String CURRENT_DOMAIN = "http://laihejifen.zhenjiatong.com";
    public final static String CURRENT_DOMAIN = "http://h5.waemy.com";

    public static String APP_ID = "wx1f8a269600c90461"; //

    public static String SECRET = "a03e0fadb3337ed59fbd8cecf1d7b904";

    public static String SCOPE = "snsapi_base";// snsapi_userinfo和snsapi_base

    // 获取access_token的接口地址（GET） 限200（次/天）
    private final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    private final static String js_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    // 提前1800秒获取新access_token
    private final static int beforeExpiresIn = 1800;

    // 每次获取accessToken的时间
    private static Map<String, DateByWxAccessTokenAndJsApiTicketVO> wxTokenGetPerDates = new HashMap<>();

    public static WeiXinJsApiParamsVO getWXJSInterfaceParamVO(String pageRelateUrl) {
        String pageUrl = CURRENT_DOMAIN + pageRelateUrl;
        logger.info("当前请求的PageUrl=" + pageUrl);
        WeiXinJsApiParamsVO jsApiParamsVO = new WeiXinJsApiParamsVO();
        // JsApiTicket jsApiTicket = getZjtWeixinJsApiTicketVO();
        JsApiTicket jsApiTicket = getWxAccessTokenAndJsApiTicketVO(APP_ID, SECRET).getJsApiTicket();
        jsApiParamsVO.setExpiresIn(jsApiTicket.getExpires_in());
        jsApiParamsVO.setJsapi_ticket(jsApiTicket.getTicket());
        setAttrValuesToWeiXinJsApiParamsVO(jsApiParamsVO, pageUrl);
        return jsApiParamsVO;
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static void setAttrValuesToWeiXinJsApiParamsVO(WeiXinJsApiParamsVO jsApiParamsVO, String pageUrl) {
        // 1. 获取随机字符串
        String nonce_str = RandomStringUtil.getRandomString(16);
        jsApiParamsVO.setNonceStr(nonce_str);
        // 2. 获取时间戳，精确到秒
        String timestamp = create_timestamp();
        jsApiParamsVO.setTimestamp(timestamp);
        // 3. 根据sha-1算法获取签名signal
        String string1;
        String signature = "";
        // 注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsApiParamsVO.getJsapi_ticket() + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + pageUrl;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            jsApiParamsVO.setSignature(signature);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DateByWxAccessTokenAndJsApiTicketVO getWxAccessTokenAndJsApiTicketVO(String appid, String appsecret) {
        DateByWxAccessTokenAndJsApiTicketVO accessTokenAndJsApiTicketVO = wxTokenGetPerDates.get(appid);
        if (accessTokenAndJsApiTicketVO != null
                && DateUtil.getDateIntervalForSecond(new Date(), accessTokenAndJsApiTicketVO.getGetDate()) < accessTokenAndJsApiTicketVO.getAccessToken().getExpiresIn() - beforeExpiresIn) {
            return accessTokenAndJsApiTicketVO;
        } else {
            AccessToken accessToken = null;
            JsApiTicket jsApiTicket = null;
            JSONObject jsonObject = null;
            try {
                // 1. 选取token
                String requestTokenUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
                jsonObject = WeixinUtil.httpRequest(requestTokenUrl, "GET", null);
                if (null != jsonObject) {
                    String accessTokenStr = jsonObject.getString("access_token");
                    accessToken = new AccessToken();
                    accessToken.setToken(accessTokenStr);
                    accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
                    // 2. 获取apiTicket
                    String httpUrlTicket = js_ticket_url.replace("ACCESS_TOKEN", URLEncoder.encode(accessTokenStr, "UTF-8"));
                    jsonObject = WeixinUtil.httpRequest(httpUrlTicket, "GET", null);
                    if (null != jsonObject) {
                        jsApiTicket = new JsApiTicket();
                        jsApiTicket.setErrcode(jsonObject.getInt("errcode"));
                        jsApiTicket.setErrmsg(jsonObject.getString("errmsg"));
                        jsApiTicket.setExpires_in(jsonObject.getInt("expires_in"));
                        jsApiTicket.setTicket(jsonObject.getString("ticket"));
                    }
                }
                accessTokenAndJsApiTicketVO = new DateByWxAccessTokenAndJsApiTicketVO(new Date(), accessToken, jsApiTicket);
            } catch (Exception e) {
                e.printStackTrace();
                // 获取token或jsTicket失败
                logger.info("获取token或JsApiTicket时异常，=" + e.getLocalizedMessage());
            }
        }
        wxTokenGetPerDates.put(appid, accessTokenAndJsApiTicketVO);
        return accessTokenAndJsApiTicketVO;
    }

    // 一次重新请求，获取基础access_token
    public static AccessToken tmpGetWeixinAccessTokenByAppIdAndSecret(String appId, String secret) {
        logger.info("获取指定AppId=" + appId + "的accessToken");
        String httpUrl = "http://zhenjiatong.com/f/wxApi/accessTokenByAppId?appId=APP_ID&appsecret=APP_SECRET";
        httpUrl = httpUrl.replace("APP_ID", urlEnodeUTF8(appId));
        httpUrl = httpUrl.replace("APP_SECRET", urlEnodeUTF8(secret));
        RequestVo requestVo = new RequestVo();
        requestVo.requestUrl = httpUrl;
        requestVo.jsonParser = new AccessTokenRespParser();
        AccessToken accessToken = (AccessToken) NetUtil.get(requestVo);
        return accessToken;
    }

    // 临时请求获取jsTicket； 每次都重新获取， 不做缓存
    public static WeiXinJsApiParamsVO tmpGetJsApiTicket(String pageUrl) {
        WeiXinJsApiParamsVO jsApiParamsVO = new WeiXinJsApiParamsVO();
        // 1. 选取token
        String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        String requestTokenUrl = access_token_url.replace("APPID", APP_ID).replace("APPSECRET", SECRET);
        JSONObject jsonObject = WeixinUtil.httpRequest(requestTokenUrl, "GET", null);
        if (null != jsonObject) {
            try {
                AccessToken accessToken = new AccessToken();
                String accessTokenStr = jsonObject.getString("access_token");
                accessToken.setToken(accessTokenStr);
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
                logger.info("每次获取新的token=" + accessTokenStr);
                // 2. 获取apiTicket
                String httpUrlTicket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
                httpUrlTicket = httpUrlTicket.replace("ACCESS_TOKEN", URLEncoder.encode(accessTokenStr, "UTF-8"));
                jsonObject = WeixinUtil.httpRequest(httpUrlTicket, "GET", null);
                System.out.println(jsonObject.getString("ticket"));
                jsApiParamsVO.setJsapi_ticket(jsonObject.getString("ticket"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        setAttrValuesToWeiXinJsApiParamsVO(jsApiParamsVO, pageUrl);
        return jsApiParamsVO;

    }

    public static String urlEnodeUTF8(String str) {
        String result = str.trim();
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getWaemyAccessToken() {
        return getWxAccessTokenAndJsApiTicketVO(APP_ID, SECRET).getAccessToken().getToken();
    }
}
