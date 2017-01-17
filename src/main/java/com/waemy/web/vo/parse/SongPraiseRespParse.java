package com.waemy.web.vo.parse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.waemy.web.vo.response.SongPraiseDataVO;

public class SongPraiseRespParse extends BaseParser<SongPraiseDataVO> {
    
    private static Logger logger = LoggerFactory.getLogger(SongPraiseRespParse.class);
    
    @Override
    public SongPraiseDataVO parseJSON(String paramString) throws JSONException {// 返回的是list<Limitbuy>类型
        SongPraiseDataVO respVO = new SongPraiseDataVO();
        try {
            respVO = JSON.parseObject(paramString, SongPraiseDataVO.class);
            logger.info("成功将解析服务器返回的json  SongPraiseDataVO= " + respVO.toString());
        } catch (Exception e) {
            logger.error("解析服务器返回的json--------异常=" + e);
        }
        return respVO;
    }
    
}
