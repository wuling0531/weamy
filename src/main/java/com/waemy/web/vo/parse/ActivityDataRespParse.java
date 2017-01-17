package com.waemy.web.vo.parse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.waemy.web.vo.response.ActivityDataVO;

public class ActivityDataRespParse extends BaseParser<ActivityDataVO> {
    
    private static Logger logger = LoggerFactory.getLogger(ActivityDataRespParse.class);
    
    @Override
    public ActivityDataVO parseJSON(String paramString) throws JSONException {// 返回的是list<Limitbuy>类型
        ActivityDataVO respVO = new ActivityDataVO();
        try {
            logger.info("开始解析服务器返回的  微信用户的全部接点 ActivityDataVO json =" + paramString);
            respVO = JSON.parseObject(paramString, ActivityDataVO.class);
            logger.info("成功将解析服务器返回的json  ActivityDataVO= " + respVO.toString());
        } catch (Exception e) {
            logger.error("解析服务器返回的json--------异常=" + e);
        }
        return respVO;
    }
    
}
