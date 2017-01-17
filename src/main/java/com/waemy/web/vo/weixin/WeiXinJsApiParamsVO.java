package com.waemy.web.vo.weixin;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 微信js接口所有参数VO
 * @since 2015年9月16日 下午3:57:33
 * @author wuling
 */
public class WeiXinJsApiParamsVO {
    
    private String jsapi_ticket = "";// js接口的ticket
    
    private int expiresIn;// js接口的ticket的存活时间
    
    private String timestamp;// 微信js接口生成签名的时间戳
    
    private String nonceStr;// 生成签名的随机字符串
    
    private String signature;// 微信js接口生成的签名
    
    public String getJsapi_ticket() {
        return jsapi_ticket;
    }
    
    public void setJsapi_ticket(String jsapi_ticket) {
        this.jsapi_ticket = jsapi_ticket;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getNonceStr() {
        return nonceStr;
    }
    
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
    
    public String getSignature() {
        return signature;
    }
    
    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    public int getExpiresIn() {
        return expiresIn;
    }
    
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
}
