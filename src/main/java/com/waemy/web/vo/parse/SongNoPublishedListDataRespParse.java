package com.waemy.web.vo.parse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.waemy.web.vo.response.SongNoPublishedListDataRespVO;

public class SongNoPublishedListDataRespParse extends BaseParser<SongNoPublishedListDataRespVO> {
    
    private static Logger logger = LoggerFactory.getLogger(SongNoPublishedListDataRespParse.class);
    
    @Override
    public SongNoPublishedListDataRespVO parseJSON(String paramString) throws JSONException {
        SongNoPublishedListDataRespVO respVO = new SongNoPublishedListDataRespVO();
        try {
            logger.info("开始解析服务器返回的  微信用户的全部接点 SongNoPublishedListDataRespVO json =" + paramString);
            respVO = JSON.parseObject(paramString, SongNoPublishedListDataRespVO.class);
            logger.info("成功将解析服务器返回的json  SongNoPublishedListDataRespVO= " + respVO.toString());
        } catch (Exception e) {
            logger.error("解析服务器返回的json--------异常=" + e);
        }
        return respVO;
    }
    
}
