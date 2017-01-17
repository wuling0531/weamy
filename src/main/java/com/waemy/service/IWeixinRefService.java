package com.waemy.service;

import com.waemy.web.vo.base.WeixinSnsapiUserinfoVO;
import com.waemy.web.vo.base.WeixinUserMiddleVO;
import com.waemy.web.vo.weixin.WeixinSnsapiUserInfo;

/**
 * Created by Administrator on 2015/5/23.
 */
public interface IWeixinRefService {
    
    /**
     * 传入商家微信公众号的appId及appSecret等信息<br/>
     * 获取微信的“网页授权获取用户信息接口”的请求路径
     * @param factoryId
     * @param REDIRECT_URI
     * @return
     */
    public String getCodeRequest(String REDIRECT_URI);
    
    // // public String getCodeRequest(long factoryId, FactoryBaseConfig factoryBaseConfig, String REDIRECT_URI);
    //
    /**
     * 根据微信的“网页授权获取用户信息接口”获取临时变量code值<br/>
     * 根据code值获取微信用户的openId等信息
     * @param factoryId
     * @param code
     * @return
     */
    public WeixinUserMiddleVO getWeixinUserMiddleVOByCode(String code);
    
    //
    /**
     * 根据微信用户的openId及临时变量AccessToken获取微信用户的详情信息 <br/>
     * https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
     * @param access_token
     * @param openid
     * @return
     * @author wuling
     */
    public WeixinSnsapiUserinfoVO getSnsapiUserinfo(String access_token, String openid);
    
    //
    // /**
    // * 调用微信 检验授权凭证（access_token）是否有效===<br/>
    // * 根据微信公众号的“网页授权获取用户信息”->获取微信用户的openId和AccessToken<br/>
    // * 在这【1.如果是非静默授权，可以使用该accessToken和OpenId获取微信用户详情信息】,<br/>
    // * 可以调用该接口验证（accessToken和OpenId）是否有效
    // * @return
    // */
    // public boolean checkValidToken(String access_token, String openid);
    //
    // /**
    // * 刷新token 返回新的token
    // * @param appid
    // * @param
    // * @param refresh_token
    // * @return
    // */
    // public WeixinRefrshTokenVO refreshToken(String appid, String refresh_token);
    //
    // /**
    // * 保存微信用户的详情信息
    // * @param openid
    // * @param nickname
    // * @param sex
    // * @param province
    // * @param city
    // * @param country
    // * @param headimgurl
    // * @param unionid
    // */
    // void recordScanUserInfo(String openid, String nickname, String sex, String province, String city, String country, String headimgurl, String unionid);
    //
    // /**
    // * 保存微信扫描记录(即第三方扫描记录)<br/>
    // * --处理报警(‘扫描次数’大于厂家配置的‘报警次数’)，发送短信
    // * @param factoryId
    // * @param scanUrl
    // * @param openid
    // * @param scanClient
    // */
    // void recordScanQrcodeInfo(Long factoryId, String scanUrl, String openid, String scanClient);
    //
    // void recordScanQrcodeInfo(Long valueOf, String string, String openid, String string2, String requestIp);
    //
    // /**
    // * 红包发放服务类
    // * @param factoryId 厂家ID
    // * @param openId 微信用户ID w
    // * @param amount 红包金额
    // */
    // SendWxRedpackRespVO sendRebPaper(long factoryId, String openId, Integer amount);
    //
    // /**
    // * 红包发放服务类
    // * @param factoryId 厂家ID
    // * @param openId 微信用户ID w
    // * @param amount 红包金额
    // * @param mdn 手机号，可为空
    // * @param codeValue 当时扫的哪个码
    // */
    // SendWxRedpackRespVO sendRebPaper(long factoryId, String openId, Integer amount, Long officialUserId, String mdn, String codeValue);
    //
    /**
     * 根据openid获取麦哆咪数据库中以保存的微信用户信息
     * @param openid
     * @return
     * @author wuling
     */
    public WeixinSnsapiUserInfo findByOpenid(String openid);
    //
    // // public OpenUserRef findOpenUserRefByOpenIdAndFactoryId(String openid, Long factoryId);
    //
    // public WeixinSnsapiUserInfo findByUnionid(String unionid);
    //
    // /**
    // * 获取微盟的请求VO
    // * @param factoryId
    // * @param rEDIRECT_URI
    // * @return
    // * @author wuling
    // */
    // public String getWeiMobRequest(Long factoryId, String rEDIRECT_URI);
    //
    // public WeixinUserMiddleVO getWeiMengUserMiddleVOByCodeAndState(long factoryId, String code);
    //
    // public void createOpenUser(Long officialUserId, String mdn, long factoryId, String appId, String openid, String unionid);
    //
    // public int getQrCodeRecordSum(String qrCode);
    //
    // public void recordWxScanRunLogAndCountLog(long factoryId, long productId, String qrCode, String tmpOpenId, String appId, long officialUserId, String requestIp, String sessionId);
    //
    // public void recordScanQrcodeLog(String qrcodeValue, long factoryId, long productId, String appId, String crtOpenId, long tmpOfficialUserId, String reqUserAgentValue, String requestIp,
    // String sessionId);
    //
    // public int getRecordQrCodeForTotal(long factoryId, String qrCode);
    //
    // public void createOpenUserWithFansId(Long factoryId, String appId, Long id, String mdn, String openId, String fansId);
    //
    // public String getRedirectWechatResponseUrlToRepeat(String app_id, String redirect_url, String zjtRedirectUrl, String scope);
    
}
