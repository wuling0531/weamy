package com.waemy.web.vo.parse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.waemy.web.vo.response.RechargeListDataRespVO;

public class RechargeListDataRespParse extends BaseParser<RechargeListDataRespVO> {
    
    private static Logger logger = LoggerFactory.getLogger(RechargeListDataRespParse.class);
    
    @Override
    public RechargeListDataRespVO parseJSON(String paramString) throws JSONException {// 返回的是list<Limitbuy>类型
        RechargeListDataRespVO respVO = new RechargeListDataRespVO();
        try {
            logger.info("开始解析服务器返回的  微信用户的全部接点 RechargeListDataRespVO json =" + paramString);
            respVO = JSON.parseObject(paramString, RechargeListDataRespVO.class);
            logger.info("成功将解析服务器返回的json  RechargeListDataRespVO= " + respVO.toString());
        } catch (Exception e) {
            logger.error("解析服务器返回的json--------异常=" + e);
        }
        return respVO;
    }
    
}
