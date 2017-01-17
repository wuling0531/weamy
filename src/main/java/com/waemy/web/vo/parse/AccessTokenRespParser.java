package com.waemy.web.vo.parse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.waemy.web.vo.weixin.AccessToken;

/**
 * 启动api协议回调的json对像 11、 客户端版本检测 关于中的检查新版本
 */
public class AccessTokenRespParser extends BaseParser<AccessToken> {
    
    private static Logger logger = LoggerFactory.getLogger(AccessTokenRespParser.class);
    
    @Override
    public AccessToken parseJSON(String paramString) throws JSONException {// 返回的是list<Limitbuy>类型
        AccessToken respVO = new AccessToken();
        try {
            logger.info("开始解析服务器返回的  微信用户的全部接点 AccessToken json =" + paramString);
            respVO = JSON.parseObject(paramString, AccessToken.class);
            logger.info("成功将解析服务器返回的json  AccessToken= " + respVO.toString());
        } catch (Exception e) {
            logger.error("解析服务器返回的json--------异常=" + e);
        }
        return respVO;
    }
}
