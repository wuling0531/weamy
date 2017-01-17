package com.waemy.web.vo.parse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.waemy.web.vo.response.SongDetailDataVO;

public class SongDetailRespParse extends BaseParser<SongDetailDataVO> {
    
    private static Logger logger = LoggerFactory.getLogger(SongDetailRespParse.class);
    
    @Override
    public SongDetailDataVO parseJSON(String paramString) throws JSONException {// 返回的是list<Limitbuy>类型
        SongDetailDataVO respVO = new SongDetailDataVO();
        try {
            logger.info("开始解析服务器返回的  微信用户的全部接点 SongDetailDataVO json =" + paramString);
            respVO = JSON.parseObject(paramString, SongDetailDataVO.class);
            logger.info("成功将解析服务器返回的json  SongDetailDataVO= " + respVO.toString());
        } catch (Exception e) {
            logger.error("解析服务器返回的json--------异常=" + e);
        }
        return respVO;
    }
    
}
