package com.waemy.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.waemy.service.IWeixinRefService;
import com.waemy.utils.NetworkUtil;
import com.waemy.utils.weixin.WaemyWxInterfaceUtil;

/**
 * 微信端的访问接口Controller
 */
@Controller
@RequestMapping(value = "/")
public class WechatPortalController extends BaseController {
    
    private static Logger logger = LoggerFactory.getLogger(WechatPortalController.class);
    
    @Autowired
    private IWeixinRefService weixinRefService;
    
    /**
     * @param factoryId
     * @param c
     * @return
     */
    @RequestMapping(value = "/wx/square")
    public String squareHomeRedirect() {
        String pageMapping = "/error/404";
        try {
            String requestIp = NetworkUtil.getIpAddress(this.getRequest());
            logger.info("当前客户端网络请求Ip=" + requestIp);
            // 检查是否是微信扫描
            String userAgentStr = this.getRequest().getHeader("user-agent");
            logger.info("user-agent=" + userAgentStr);
            // 开始获取微信授权的请求链接
            String REDIRECT_URI = "";
            REDIRECT_URI = WaemyWxInterfaceUtil.CURRENT_DOMAIN + "/wx/m/square";
            String wexinReqeust = weixinRefService.getCodeRequest(REDIRECT_URI);
            pageMapping = "redirect:" + wexinReqeust;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageMapping;
    }
    
    /**
     * @return
     */
    @RequestMapping(value = "/wx/mysong")
    public String mysongPageRedirect() {
        String pageMapping = "/error/404";
        try {
            // String requestIp = NetworkUtil.getIpAddress(this.getRequest());
            // logger.info("当前客户端网络请求Ip=" + requestIp);
            // // 检查是否是微信扫描
            // String userAgentStr = this.getRequest().getHeader("user-agent");
            // logger.info("user-agent=" + userAgentStr);
            // 开始获取微信授权的请求链接
            String REDIRECT_URI = "";
            REDIRECT_URI = WaemyWxInterfaceUtil.CURRENT_DOMAIN + "/wx/m/mysong";
            String wexinReqeust = weixinRefService.getCodeRequest(REDIRECT_URI);
            pageMapping = "redirect:" + wexinReqeust;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageMapping;
    }
    
    @RequestMapping(value = "/wx/myCoupon")
    public String myCouponPageRedirect() {
        String pageMapping = "/error/404";
        try {
            // 开始获取微信授权的请求链接
            String REDIRECT_URI = "";
            REDIRECT_URI = WaemyWxInterfaceUtil.CURRENT_DOMAIN + "/wx/m/myCoupon?status=1";
            String wexinReqeust = weixinRefService.getCodeRequest(REDIRECT_URI);
            pageMapping = "redirect:" + wexinReqeust;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageMapping;
    }
    
    @RequestMapping(value = "/wx/myAccount")
    public String myAccountPageRedirect() {
        String pageMapping = "/error/404";
        try {
            // 开始获取微信授权的请求链接
            String REDIRECT_URI = "";
            REDIRECT_URI = WaemyWxInterfaceUtil.CURRENT_DOMAIN + "/wx/m/myAccount";
            String wexinReqeust = weixinRefService.getCodeRequest(REDIRECT_URI);
            pageMapping = "redirect:" + wexinReqeust;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageMapping;
    }
    
    @RequestMapping(value = "/wx/rechargeRecord")
    public String myRechargeRecordPageRedirect() {
        String pageMapping = "/error/404";
        try {
            // 开始获取微信授权的请求链接
            String REDIRECT_URI = "";
            REDIRECT_URI = WaemyWxInterfaceUtil.CURRENT_DOMAIN + "/wx/m/rechargeRecord";
            String wexinReqeust = weixinRefService.getCodeRequest(REDIRECT_URI);
            pageMapping = "redirect:" + wexinReqeust;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageMapping;
    }
    
    /**
     * 微信一键关注的微信授权跳转
     * @return
     */
    @RequestMapping(value = "wx/followMe")
    public String followWeChatPublicRedirect(String deviceId) {
        String pageMapping = "/error/404";
        try {
            String requestIp = NetworkUtil.getIpAddress(this.getRequest());
            logger.info("当前客户端网络请求Ip=" + requestIp);
            // 检查是否是微信扫描
            String userAgentStr = this.getRequest().getHeader("user-agent");
            logger.info("user-agent=" + userAgentStr);
            // 开始获取微信授权的请求链接
            String REDIRECT_URI = "";
            REDIRECT_URI = WaemyWxInterfaceUtil.CURRENT_DOMAIN + "/wx/m/followPublic?deviceId=" + deviceId;
            String wexinReqeust = weixinRefService.getCodeRequest(REDIRECT_URI);
            pageMapping = "redirect:" + wexinReqeust;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageMapping;
    }
}
