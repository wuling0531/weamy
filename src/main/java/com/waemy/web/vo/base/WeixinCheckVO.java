package com.waemy.web.vo.base;

/**
 * Created by Administrator on 2015/5/23.
 */
public class WeixinCheckVO {
    
    // { "errcode":40003,"errmsg":"invalid openid"}
    
    private String errcode;
    
    private String errmsg;
    
    public String getErrcode() {
        return errcode;
    }
    
    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }
    
    public String getErrmsg() {
        return errmsg;
    }
    
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
    
    @Override
    public String toString() {
        return "WeixinCheckVO{" + "errcode='" + errcode + '\'' + ", errmsg='" + errmsg + '\'' + '}';
    }
}
