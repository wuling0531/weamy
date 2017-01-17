package com.waemy.web.vo.parse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.waemy.web.vo.weixin.JsApiTicket;

public class JsApiTicketRespParser extends BaseParser<JsApiTicket> {
    
    private static Logger logger = LoggerFactory.getLogger(JsApiTicketRespParser.class);
    
    @Override
    public JsApiTicket parseJSON(String paramString) throws JSONException {// 返回的是list<Limitbuy>类型
        JsApiTicket respVO = new JsApiTicket();
        try {
            logger.info("开始解析服务器返回的  当前的JsApiTicket json =" + paramString);
            respVO = JSON.parseObject(paramString, JsApiTicket.class);
            logger.info("成功将解析服务器返回的json  JsApiTicket= " + respVO.toString());
        } catch (Exception e) {
            logger.error("解析服务器返回的json--------异常=" + e);
        }
        return respVO;
    }
    
}
