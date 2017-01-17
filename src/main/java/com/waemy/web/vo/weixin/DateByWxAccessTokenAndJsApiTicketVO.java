package com.waemy.web.vo.weixin;

import java.util.Date;

public class DateByWxAccessTokenAndJsApiTicketVO {
    
    public DateByWxAccessTokenAndJsApiTicketVO(Date getDate, AccessToken accessToken, JsApiTicket jsApiTicket) {
        super();
        this.getDate = getDate;
        this.accessToken = accessToken;
        this.jsApiTicket = jsApiTicket;
    }
    
    private Date getDate;
    
    private AccessToken accessToken;
    
    private JsApiTicket jsApiTicket;
    
    public Date getGetDate() {
        return getDate;
    }
    
    public void setGetDate(Date getDate) {
        this.getDate = getDate;
    }
    
    public AccessToken getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
    
    public JsApiTicket getJsApiTicket() {
        return jsApiTicket;
    }
    
    public void setJsApiTicket(JsApiTicket jsApiTicket) {
        this.jsApiTicket = jsApiTicket;
    }
    
}
