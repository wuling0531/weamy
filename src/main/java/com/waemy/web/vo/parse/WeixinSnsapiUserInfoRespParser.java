package com.waemy.web.vo.parse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.waemy.web.vo.base.WeixinSnsapiUserinfoVO;

/**
 * 启动api协议回调的json对像
 * 11、	客户端版本检测  关于中的检查新版本
 */
public class WeixinSnsapiUserInfoRespParser extends BaseParser<WeixinSnsapiUserinfoVO> {
    private static Logger logger = LoggerFactory.getLogger(WeixinSnsapiUserInfoRespParser.class);

    @Override
    public WeixinSnsapiUserinfoVO parseJSON(String paramString) throws JSONException {//返回的是list<Limitbuy>类型
        WeixinSnsapiUserinfoVO respVO = new WeixinSnsapiUserinfoVO();
        try
        {
            logger.info("开始解析服务器返回的  微信用户的全部接点 WeixinSnsapiUserinfoVO json ="+paramString);
            respVO = JSON.parseObject(paramString, WeixinSnsapiUserinfoVO.class);
            logger.info("成功将解析服务器返回的json  WeixinSnsapiUserinfoVO= "+respVO.toString());
        }catch (Exception e){
            logger.error("解析服务器返回的json--------异常="+e);
        }
        return respVO;
	}
}
