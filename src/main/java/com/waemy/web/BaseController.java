package com.waemy.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

/**
 */
public class BaseController {
    
    // protected static String CURRENT_HTTP = "http://waemy.com";
    
    // 统一管理session值。
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpSession session;
    
    public HttpServletRequest getRequest() {
        return request;
    }
    
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    public HttpSession getSession() {
        return session;
    }
    
    public void setSession(HttpSession session) {
        this.session = session;
    }
}
