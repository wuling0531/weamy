package com.waemy.web.vo.request;

import java.util.HashMap;

import com.waemy.web.vo.parse.BaseParser;

public class RequestVo {
    
    public String requestUrl;
    
    public HashMap<String, String> requestDataMap;
    
    public BaseParser<?> jsonParser;
}
