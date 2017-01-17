package com.waemy.service.impl;

import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.waemy.service.IWeixinRefService;
import com.waemy.utils.NetUtil;
import com.waemy.utils.weixin.WaemyWxInterfaceUtil;
import com.waemy.web.vo.base.WeixinSnsapiUserinfoVO;
import com.waemy.web.vo.base.WeixinUserMiddleVO;
import com.waemy.web.vo.parse.WeixinSnsapiUserInfoRespParser;
import com.waemy.web.vo.parse.WeixinUserMiddleUserRespParser;
import com.waemy.web.vo.request.RequestVo;
import com.waemy.web.vo.weixin.WeixinSnsapiUserInfo;

/**
 * 微信扫描的相关业务处理
 */
@Component
public class WeixinRefServiceImpl implements IWeixinRefService {
    
    private static Logger logger = LoggerFactory.getLogger(WeixinRefServiceImpl.class);
    
    // @Autowired
    // private ICommonDao commonDao;
    //
    // @Autowired
    // private IQrCodeDao qrCodeDao;
    //
    // @Autowired
    // private ISmsQuartzDao smsQuartzDao;
    //
    // @Autowired
    // private IRecord3rdDao record3rdDao;
    //
    // @Autowired
    // private IOpenUserRefDao openUserRefDao;
    //
    // @Autowired
    // private IOfficialUserDao officialUserDao;
    //
    // @Autowired
    // private IFactoryBaseConfigDao factoryBaseConfigDao;
    //
    // @Autowired
    // private IUserFactoryBagLogDao userFactoryBagLogDao;
    //
    // @Autowired
    // private IFactoryRecordAlertDao factoryRecordAlertDao;
    //
    // @Autowired
    // private IWeixinSnsapiUserInfoDao weixinSnsapiUserInfoDao;
    //
    // @Autowired
    // private IFactoryWeixinMpConfigDao weixinMpConfigDao;
    //
    // @Autowired
    // private IRecordQrcodeDao recordQrcodeDao;
    //
    // @Autowired
    // private IRecordThirdLogDao recordThirdLogDao;
    //
    // @Autowired
    // private IRecordService recordService;
    //
    // @Autowired
    // private IFactoryService factoryService;
    //
    // @Autowired
    // private IClientUserService clientUserService;
    //
    // @Autowired
    // private ISmsService smsService;
    //
    // @Autowired
    // private IProductService productService;
    //
    // @Autowired
    // private IWeixinNoticeService weixinNoticeService;
    //
    /**
     * 以UTF-8字符集编码
     * @param str
     * @return
     */
    private static String urlEnodeUTF8(String str) {
        String result = str.trim();
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    @Override
    public String getCodeRequest(String REDIRECT_URI) {
        // 参数 是否必须 说明
        // appid 是 公众号的唯一标识
        // redirect_uri 是 授权后重定向的回调链接地址，请使用urlencode对链接进行处理
        // response_type 是 返回类型，请填写code
        // scope 是 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
        // state 否 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
        // #wechat_redirect 是 无论直接打开还是做页面302重定向时候，必须带此参数
        String GetCodeRequest = "";
        String appId = WaemyWxInterfaceUtil.APP_ID;
        GetCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
        GetCodeRequest = GetCodeRequest.replace("APPID", urlEnodeUTF8(appId));
        GetCodeRequest = GetCodeRequest.replace("REDIRECT_URI", urlEnodeUTF8(REDIRECT_URI));
        GetCodeRequest = GetCodeRequest.replace("SCOPE", WaemyWxInterfaceUtil.SCOPE);
        return GetCodeRequest;
        
    }
    
    // @Override
    // public String getCodeRequest(long factoryId, FactoryBaseConfig factoryBaseConfig, String REDIRECT_URI) {
    // // 参数 是否必须 说明
    // // appid 是 公众号的唯一标识
    // // redirect_uri 是 授权后重定向的回调链接地址，请使用urlencode对链接进行处理
    // // response_type 是 返回类型，请填写code
    // // scope 是 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
    // // state 否 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
    // // #wechat_redirect 是 无论直接打开还是做页面302重定向时候，必须带此参数
    // String SCOPE = factoryBaseConfig.getScope();
    // String appId = "";
    // String GetCodeRequest = "";
    // if ("default".equals(factoryBaseConfig.getAppBind3rd())) {
    // appId = factoryBaseConfig.getAppId();
    // GetCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    // GetCodeRequest = GetCodeRequest.replace("APPID", urlEnodeUTF8(appId));
    // GetCodeRequest = GetCodeRequest.replace("REDIRECT_URI", urlEnodeUTF8(REDIRECT_URI));
    // GetCodeRequest = GetCodeRequest.replace("SCOPE", SCOPE);
    // } else if ("weimob".equals(factoryBaseConfig.getAppBind3rd())) {
    // appId = factoryBaseConfig.getAppId3rd();
    // GetCodeRequest = "https://open.weimob.com/oauth2/openid/authorize?client_id=CLIENT_ID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE";
    // // 为丹泉酒业在微盟下的请求
    // GetCodeRequest = GetCodeRequest.replace("CLIENT_ID", appId);
    // GetCodeRequest = GetCodeRequest.replace("REDIRECT_URI", urlEnodeUTF8(REDIRECT_URI));
    // GetCodeRequest = GetCodeRequest.replace("SCOPE", SCOPE);
    // } else if ("youzan".equals(factoryBaseConfig.getAppBind3rd())) {// 绑定的有赞的三方
    // appId = factoryBaseConfig.getAppId3rd();
    // GetCodeRequest = "http://wap.koudaitong.com/v2/open/weixin/auth?app_id=APP_ID&timestamp=TIMESTAMP&redirect_url=REDIRECT_URL&scope=SCOPE&sign=SIGN&with_sign_keys=SIGN_KEYS";
    // String timeStamp = DateUtil.getCurrentDateStr("yyyy-MM-dd HH:mm:ss");
    // GetCodeRequest = GetCodeRequest.replace("APP_ID", appId);
    // GetCodeRequest = GetCodeRequest.replace("TIMESTAMP", timeStamp);
    // GetCodeRequest = GetCodeRequest.replace("REDIRECT_URL", urlEnodeUTF8(REDIRECT_URI));
    // GetCodeRequest = GetCodeRequest.replace("SCOPE", SCOPE);
    // GetCodeRequest = GetCodeRequest.replace("SIGN_KEYS", "1");
    // HashMap<String, String> parames = new HashMap<>();
    // parames.put("app_id", appId);
    // parames.put("timestamp", timeStamp);
    // parames.put("redirect_url", REDIRECT_URI);
    // parames.put("scope", SCOPE);
    // // parames.put("custom", "zjt");
    // parames.put("with_sign_keys", "1");
    // logger.info("使用有赞接口，生成签名sign时的parames=" + parames);
    // String singStr = YouZanApiSignUtil.sign(factoryBaseConfig.getSecret3rd(), parames);
    // GetCodeRequest = GetCodeRequest.replace("SIGN", singStr);
    // } else if ("zaokea".equals(factoryBaseConfig.getAppBind3rd())) {// 绑定的兆客的三方
    // appId = factoryBaseConfig.getAppId3rd();
    // GetCodeRequest = "http://zkb.zaokea.com/zkb_lf/app/themes/default/zkb/getWeChatCode.html?appid=APP_ID&redirect_uri=REDIRECT_URL&scope=SCOPE&state=STATE";
    // GetCodeRequest = GetCodeRequest.replace("APP_ID", appId);
    // GetCodeRequest = GetCodeRequest.replace("REDIRECT_URL", urlEnodeUTF8(REDIRECT_URI));
    // GetCodeRequest = GetCodeRequest.replace("SCOPE", SCOPE);
    // HashMap<String, String> parames = new HashMap<>();
    // parames.put("app_id", appId);
    // parames.put("redirect_url", REDIRECT_URI);
    // parames.put("scope", SCOPE);
    // logger.info("使用兆客(zaokea.com)接口， parames=" + parames);
    // } else {
    // logger.info("根据factoryBaseconfig获取微信的配置信息异常；=" + factoryBaseConfig.getAppBind3rd());
    // }
    // logger.info("factoryId=" + factoryId + ";SCOPE=" + SCOPE);
    // return GetCodeRequest;
    //
    // }
    //
    @Override
    public WeixinUserMiddleVO getWeixinUserMiddleVOByCode(String code) {
        WeixinUserMiddleVO weixinUserMiddleVO = null;
        if (StringUtils.isNotBlank(code)) {
            try {
                String httpUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
                httpUrl = httpUrl.replace("APPID", urlEnodeUTF8(WaemyWxInterfaceUtil.APP_ID));
                // httpUrl = httpUrl.replace("SECRET", urlEnodeUTF8(secret));
                // String httpUrl = "";
                String appId = WaemyWxInterfaceUtil.APP_ID, Secret = WaemyWxInterfaceUtil.SECRET;
                RequestVo requestVo = new RequestVo();
                // if ("default".equals(baseConfig.getAppBind3rd()) || "zaokea".equals(baseConfig.getAppBind3rd())) {
                // appId = baseConfig.getAppId();
                // Secret = baseConfig.getSecret();
                httpUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
                httpUrl = httpUrl.replace("APPID", urlEnodeUTF8(appId));
                httpUrl = httpUrl.replace("SECRET", urlEnodeUTF8(Secret));
                httpUrl = httpUrl.replace("CODE", urlEnodeUTF8(code));
                logger.info("第二次请求微信，需要取得用户openId，httpurl=" + httpUrl);
                requestVo.requestUrl = httpUrl;
                requestVo.jsonParser = new WeixinUserMiddleUserRespParser();
                weixinUserMiddleVO = (WeixinUserMiddleVO) NetUtil.post(requestVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return weixinUserMiddleVO;
    }
    
    @Override
    public WeixinSnsapiUserinfoVO getSnsapiUserinfo(String access_token, String openid) {
        WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = null;
        try {
            // String httpUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
            String httpUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
            httpUrl = httpUrl.replace("ACCESS_TOKEN", urlEnodeUTF8(access_token));
            httpUrl = httpUrl.replace("OPENID", urlEnodeUTF8(openid));
            logger.info("第三次请求微信，根据openId需要取得用户全部信息，httpurl=" + httpUrl);
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = httpUrl;
            requestVo.jsonParser = new WeixinSnsapiUserInfoRespParser();
            weixinSnsapiUserinfoVO = (WeixinSnsapiUserinfoVO) NetUtil.post(requestVo);
            logger.info("weixinSnsapiUserinfoVO  基本信息列=" + weixinSnsapiUserinfoVO.toString());
        } catch (Exception e) {
            logger.info("第三次请求微信，根据openId需要取得用户全部信息 异常" + e);
        }
        return weixinSnsapiUserinfoVO;
    }
    
    // @Override
    // public boolean checkValidToken(String access_token, String openid) {
    // boolean checkFlag = false;
    // WeixinCheckVO weixinCheckVO = null;
    // // https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
    // try {
    // String httpUrl = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
    // httpUrl = httpUrl.replace("OPENID", urlEnodeUTF8(openid));
    // httpUrl = httpUrl.replace("ACCESS_TOKEN", urlEnodeUTF8(access_token));
    // logger.info("刷新token，httpurl=" + httpUrl);
    // RequestVo requestVo = new RequestVo();
    // requestVo.requestUrl = httpUrl;
    // requestVo.jsonParser = new WeixinSnsapiUserInfoRespParser();
    // weixinCheckVO = (WeixinCheckVO) NetUtil.post(requestVo);
    //
    // logger.info("weixinCheckVO =" + weixinCheckVO.toString());
    // // { "errcode":0,"errmsg":"ok"}
    // if (weixinCheckVO.getErrcode().equals("0")) checkFlag = true;
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return checkFlag;
    // }
    //
    // @Override
    // public WeixinRefrshTokenVO refreshToken(String appid, String refresh_token) {
    // WeixinRefrshTokenVO weixinRefrshTokenVO = null;
    // // https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
    // try {
    // String httpUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    // httpUrl = httpUrl.replace("APPID", urlEnodeUTF8(appid));
    // httpUrl = httpUrl.replace("REFRESH_TOKEN", urlEnodeUTF8(refresh_token));
    // logger.info("刷新token，httpurl=" + httpUrl);
    // RequestVo requestVo = new RequestVo();
    // requestVo.requestUrl = httpUrl;
    // requestVo.jsonParser = new WeixinSnsapiUserInfoRespParser();
    // weixinRefrshTokenVO = (WeixinRefrshTokenVO) NetUtil.post(requestVo);
    //
    // logger.info("weixinRefrshTokenVO =" + weixinRefrshTokenVO.toString());
    //
    // } catch (Exception e) {}
    // return weixinRefrshTokenVO;
    // }
    //
    // @Override
    // public void recordScanUserInfo(String openid, String nickname, String sex, String province, String city, String country, String headimgurl, String unionid) {
    // try {
    // WeixinSnsapiUserInfo curent = this.weixinSnsapiUserInfoDao.findByOpenid(openid);
    // WeixinSnsapiUserInfo insert = new WeixinSnsapiUserInfo();
    // if (curent != null) {
    // insert.setId(curent.getId());
    // insert.setCreateDate(curent.getCreateDate());
    // } else
    // insert.setCreateDate(DateUtil.getCurrentDate());
    //
    // insert.setUpdateDate(DateUtil.getCurrentDate());
    // insert.setEnableFlag("Y");
    // insert.setCity(city);
    // insert.setCountry(country);
    // insert.setHeadimgurl(headimgurl);
    // insert.setNickname(nickname);
    // insert.setOpenid(openid);
    // insert.setProvince(province);
    // insert.setUnionid(unionid);
    // insert.setSex(sex);
    // this.weixinSnsapiUserInfoDao.save(insert);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    //
    // @Override
    // public void recordScanQrcodeInfo(Long factoryId, String scanUrl, String openid, String scanClient) {
    // try {
    // Record3rd record3rd = new Record3rd();
    // record3rd.setFactoryId(factoryId);
    // record3rd.setCodeValue(scanUrl);
    // record3rd.setOpenId(openid);
    // record3rd.setScanClient(scanClient);
    // record3rd.setCreateDate(DateUtil.getCurrentDate());
    // record3rd.setUpdateDate(DateUtil.getCurrentDate());
    // record3rd.setEnableFlag("Y");
    // record3rd.setTotal(1);
    // logger.info("保存一次扫描记录,record3rd=" + record3rd);
    // this.record3rdDao.save(record3rd);
    // // 开始处理短信报报警，估计时间会比较长,影响性能，需优化。
    // // todo 正常写完了微信扫描记录,开始检查扫描数量，是否超过报警数量，如果是，则往s_sms_quartx中写入短信提醒
    // // 需将相同码的数据，计算：s_record表中的 total值，s_record_third值之和。
    // // 正常事情都已做完，现在开始进行是不是需要写入报警
    // // 2014-04-11日新增功能： 新增：扫描短信报警功能 ,根据厂家开通的功能，进行扫描示写入
    // try {
    // logger.info("开始检测试是否需要报警短信提示");
    // FactoryRecordAlert factoryRecordAlert = factoryRecordAlertDao.findByFactoryId(factoryId);
    // logger.info("factoryRecordAlert=" + factoryRecordAlert);
    // if (factoryRecordAlert != null && scanClient.equalsIgnoreCase("weixin")) // 已开通扫描报警功能
    // {
    // String codeValue = scanUrl;
    // if (scanUrl != null && scanUrl.length() > 0 && scanUrl.indexOf("c=") != -1) {
    // int beginIndex = scanUrl.indexOf("c=") + 2; // 直接去掉下载链接此码值为二维码中的实际值，明码值，非密文
    // codeValue = scanUrl.substring(beginIndex);
    // // todo 得到 codeValue实际值了。
    //
    // long recordSum = 0, recordWeixinSum = 0;
    // String hql_record = "select sum(total) from Record where codeValue = '" + codeValue + "' and codeType = 'qrcode' ";
    // recordSum = this.commonDao.countTByHql(hql_record); // 真假通的扫描次数
    //
    // String hsq_weixin = "select count(DISTINCT openId) from Record3rd where scanClient = 'weixin' and  codeValue = '" + scanUrl + "' ";
    // recordWeixinSum = this.commonDao.countTByHql(hsq_weixin); // 微信的扫描次数
    //
    // recordSum = recordSum + recordWeixinSum;
    // if (factoryRecordAlert.getRecordSum() <= recordSum)// 如果报警数量小于实际扫描数量（同一用户，扫同一产品只算一次）
    // {
    // Record record = this.recordService.getRecordByCodeValue(codeValue);
    // if (record != null) {
    // QrCode qrCode = this.qrCodeDao.findByCodeValue(codeValue);
    // String goodsTitle = "";
    // ProductInfo productInfo = null;
    // if (qrCode != null && record != null) productInfo = this.productService.getProductInfoById(record.getProductId());
    // if (productInfo != null) goodsTitle = productInfo.getItemName();
    // logger.info("开始写入报警短信提示,请各路厂家用户前往真假通服务号进行后台账户绑定，才有可能收到预警提示");
    //
    // String title = "真假通提示", content = "";
    // logger.info("当前的record=" + record + ";record3rd.getUpdateDate()=" + record3rd.getUpdateDate() + ";qrCodeEntity=" + qrCode);
    // content = alertContent(record.getId(), recordSum, DateUtil.getDayStr(record3rd.getUpdateDate(), "yyyy-MM-dd HH:mm:ss"), factoryId, qrCode.getCodeNo(), goodsTitle,
    // openid);
    // logger.info("报警短信提示内容：content = " + content);
    // if (factoryRecordAlert.getAlertType() == 0) {
    // this.smsService.saveRecordSmsQuartz(title, content, factoryRecordAlert.getSendMdn());
    // if (factoryRecordAlert.getCopyMdn() != null && factoryRecordAlert.getCopyMdn().length() > 10) // 抄送
    // this.smsService.saveRecordSmsQuartz(title, content, factoryRecordAlert.getCopyMdn());
    //
    // }
    // if (factoryRecordAlert.getAlertType() == 3) {
    // try {
    // // * 预警内容：{{keyword1.DATA}}
    // // * 预警时间：{{keyword2.DATA}}
    // String firstValue = "报警短信提示";
    // String keyword1Value = "报警短信提示内容";
    // String keyword2Value = DateUtil.getCurrentDateStr();
    // String remarkValue = keyword1Value.toString() + content;
    // weixinNoticeService.factoryUserScanAlertNotice(factoryId, firstValue, keyword1Value, keyword2Value, remarkValue);
    //
    // } catch (Exception e2) {
    // logger.info("weixin的Notice服务发送异常=" + e2.getMessage());
    // e2.printStackTrace();
    // }
    // }
    // } else {
    // //
    // logger.info("record == null, todo ?");
    // }
    // }
    // }
    // }
    // } catch (Exception e1) {
    // e1.printStackTrace();
    // logger.info("报警短信提示异常=" + e1.getMessage());
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // logger.info("报警短信提示,新增三方扫描记录异常=" + e.getMessage());
    // }
    // }
    //
    // // 待正式上线后，估计为弃用此服务
    // // 已弃用.16-1-8
    // @Override
    // public SendWxRedpackRespVO sendRebPaper(long factoryId, String openId, Integer amount) {
    // SendWxRedpackRespVO sendWxRedpackRespVO = null;
    // String sign = "8AeIrERS0QMpA6pa";// 自定义的sign,请求的验证Sign;接口通讯标识
    // try {
    // String wxappid = "";
    // String appKey = "";
    // String mchid = "";
    // String actName = "";// “微信”服务通知中‘你参与"$actName"’
    // String sendName = "";
    // String remark = "";
    // String wishing = "";
    // // //服务通知中‘你参与"$actName",成功获得"$sendName", "$remark"’。
    // // 查询factoryBaseConfig
    // FactoryBaseConfig baseConfig = factoryBaseConfigDao.findByFactoryId(factoryId);
    // if (null != baseConfig) {//
    // wxappid = baseConfig.getAppId();//
    // appKey = baseConfig.getMchSecret();//
    // // appKey = baseConfig.getSecret();
    // mchid = baseConfig.getMchid();
    // actName = baseConfig.getActName();
    // sendName = baseConfig.getSendName();
    // remark = baseConfig.getRemark();
    // wishing = baseConfig.getWishing();
    // logger.info("进入发红包准备状态%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    // logger.info("factoryId=" + factoryId + ";appKey=" + appKey + ";mchid=" + mchid + ";wxappid=" + wxappid);
    // } else {
    // throw new RuntimeException("厂家后台的厂家配置信息没有维护");
    // }
    // try {
    // RequestVo requestVo = new RequestVo();
    // requestVo.requestUrl = "http://open.zhenjiatong.com/zjtapi/sendredpack.html";
    // // requestVo.requestUrl = "http://localhost:84/zjtapi/sendredpack";
    // HashMap<String, String> requestDataMap = new HashMap<String, String>();
    // requestDataMap.put("factoryid", String.valueOf(factoryId));
    // requestDataMap.put("sign", sign);
    // requestDataMap.put("wxappid", wxappid);
    // requestDataMap.put("appKey", appKey);
    // requestDataMap.put("mchid", mchid);
    // requestDataMap.put("actName", actName);
    // requestDataMap.put("remark", remark);
    // requestDataMap.put("sendName", sendName);
    // requestDataMap.put("wishing", wishing);
    // requestDataMap.put("openid", openId);
    // requestDataMap.put("amount", String.valueOf(amount));
    // requestVo.requestDataMap = requestDataMap;
    // logger.info("开始构建发红包数据  requestDataMap=" + requestDataMap.toString());
    // requestVo.jsonParser = new SendRedRespParser();
    // sendWxRedpackRespVO = (SendWxRedpackRespVO) NetUtil.post(requestVo);
    // logger.info("红包发送已完成 RRRRRRRRRRRRRRRRRRRRRRRRRRRR  sendWxRedpackRespVO=" + sendWxRedpackRespVO != null ? sendWxRedpackRespVO.toString() : null);
    // } catch (Exception e) {
    // e.printStackTrace();
    // logger.info("红包发送异常: " + e.getLocalizedMessage());
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return sendWxRedpackRespVO;
    // }
    //
    // @Override
    // public SendWxRedpackRespVO sendRebPaper(long factoryId, String openId, Integer amount, Long officialUserId, String mdn, String codeValue) {
    // // 此方法保证不返回null，同时保证stateVO不是null。
    // // stateVO.code == 0 success
    // // stateVO.code == -1 failure
    // // stateVO.code == -2 unknown
    //
    // // 初始化返回数据
    // StateVO stateVO = new StateVO();
    // stateVO.setCode(-1);
    // stateVO.setMsg("FAIL_ZJT_DEFAULT");
    // SendWxRedpackRespVO sendWxRedpackRespVO = new SendWxRedpackRespVO();
    // sendWxRedpackRespVO.setStateVO(stateVO);
    // sendWxRedpackRespVO.setMch_billno("");
    //
    // boolean hasError = false;
    // String noticeStr = "";
    //
    // String sign = "8AeIrERS0QMpA6pa";// 自定义的sign,请求的验证Sign;接口通讯标识
    // String wxappid = "";
    // String appKey = "";
    // String mchid = "";
    // String actName = "";// “微信”服务通知中‘你参与"$actName"’
    // String sendName = "";
    // String remark = "";
    // String wishing = "";
    //
    // logger.info("进入发红包准备状态%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    // // 查询factoryBaseConfig
    // FactoryBaseConfig baseConfig = null;
    // try {
    // baseConfig = factoryBaseConfigDao.findByFactoryId(factoryId);
    // } catch (Exception e) {
    // logger.info("查询factoryBaseConfig时异常： " + e);
    // e.printStackTrace();
    // }
    // if (null != baseConfig) {//
    // wxappid = baseConfig.getAppId();
    // // appKey = baseConfig.getSecret();
    // appKey = baseConfig.getMchSecret();
    // mchid = baseConfig.getMchid();
    // actName = baseConfig.getActName();
    // sendName = baseConfig.getSendName();
    // remark = baseConfig.getRemark();
    // wishing = baseConfig.getWishing();
    // logger.info("factoryId=" + factoryId + ";appKey=" + appKey + ";mchid=" + mchid + ";wxappid=" + wxappid);
    // } else {
    // stateVO.setCode(-1);
    // stateVO.setMsg("FAIL_ZJT_FACTORY_CONFIG");
    // hasError = true;
    // noticeStr = "厂家后台的厂家配置信息没有维护";
    // logger.info(noticeStr);
    // }
    //
    // // 发红包前，写入数据库记录,发完再来更新记录
    // UserFactoryBagLog userFactoryBagLog = null;
    // if (!hasError) {
    // userFactoryBagLog = new UserFactoryBagLog();
    // userFactoryBagLog.setAppId(wxappid);
    // userFactoryBagLog.setCodeValue(codeValue);
    // userFactoryBagLog.setMdn(mdn);
    // userFactoryBagLog.setFactoryId(factoryId);
    // userFactoryBagLog.setOfficialUserId(officialUserId);
    // userFactoryBagLog.setOpenId(openId);
    // userFactoryBagLog.setMchId(mchid);
    // userFactoryBagLog.setMchBillno("");
    // userFactoryBagLog.setSendListid("");
    // userFactoryBagLog.setPayNum(amount);
    // userFactoryBagLog.setTransno("");
    // userFactoryBagLog.setRecvFlag(0); // 等将来的定时刷状态
    // userFactoryBagLog.setSendFlag(0);
    // userFactoryBagLog.setTips(actName + wishing);
    // userFactoryBagLog.setUpdateDate(DateUtil.getCurrentDate());
    // userFactoryBagLog.setCreateDate(DateUtil.getCurrentDate());
    // userFactoryBagLog.setEnableFlag("Y");
    // try {
    // userFactoryBagLogDao.save(userFactoryBagLog);
    // } catch (Exception e) {
    // logger.info("新增微信红包发送记录时异常： " + e);
    // e.printStackTrace();
    // stateVO.setCode(-1);
    // stateVO.setMsg("FAIL_ZJT_INSERT_LOG");
    // hasError = true;
    // noticeStr = "新增微信红包发送记录时异常";
    // }
    // }
    //
    // // 调用红包发送服务
    // SendWxRedpackRespVO tmpSendWxRedpackRespVO = null;
    // if (!hasError) {
    // RequestVo requestVo = new RequestVo();
    // requestVo.requestUrl = "http://open.zhenjiatong.com/zjtapi/sendredpack.html";
    // // requestVo.requestUrl = "http://localhost:84/zjtapi/sendredpack";
    // HashMap<String, String> requestDataMap = new HashMap<String, String>();
    // requestDataMap.put("factoryid", String.valueOf(factoryId));
    // requestDataMap.put("sign", sign);
    // requestDataMap.put("wxappid", wxappid);
    // requestDataMap.put("appKey", appKey);
    // requestDataMap.put("mchid", mchid);
    // requestDataMap.put("actName", actName);
    // requestDataMap.put("remark", remark);
    // requestDataMap.put("sendName", sendName);
    // requestDataMap.put("wishing", wishing);
    // requestDataMap.put("openid", openId);
    // requestDataMap.put("amount", String.valueOf(amount));
    // requestVo.requestDataMap = requestDataMap;
    // logger.info("开始构建发红包数据  requestDataMap=" + requestDataMap.toString());
    // requestVo.jsonParser = new SendRedRespParser();
    //
    // tmpSendWxRedpackRespVO = (SendWxRedpackRespVO) NetUtil.post(requestVo);
    // logger.info("红包发送已完成 RRRRRRRRRRRRRRRRRRRRRRRRRRRR  tmpSendWxRedpackRespVO=" + tmpSendWxRedpackRespVO);
    // }
    //
    // // 根据调用结果处理，1.设置返回值，2.更新发放记录
    // logger.info("userFactoryBagLog=" + userFactoryBagLog);
    // if (null != tmpSendWxRedpackRespVO && null != tmpSendWxRedpackRespVO.getStateVO()) { // 正常调用服务
    // // 能执行到这里，userFactoryBagLog必定不是null
    // sendWxRedpackRespVO = tmpSendWxRedpackRespVO;
    // if (sendWxRedpackRespVO.getStateVO().getCode() == 0) { // 发送完成
    // userFactoryBagLog.setSendFlag(1);
    // userFactoryBagLog.setMchBillno(sendWxRedpackRespVO.getMch_billno());
    // userFactoryBagLog.setSendListid(sendWxRedpackRespVO.getSend_list());
    // if (sendWxRedpackRespVO.getSend_time() != null && !"null".equals(sendWxRedpackRespVO.getSend_time())) {
    // Date sendDate = DateUtil.getDateFromString(sendWxRedpackRespVO.getSend_time(), "yyyyMMddHHmmss");
    // userFactoryBagLog.setSendDate(sendDate);
    // }
    // userFactoryBagLog.setTransno("");
    // } else { // 发送失败，或者发送状态未知
    // userFactoryBagLog.setSendFlag(sendWxRedpackRespVO.getStateVO().getCode());
    // userFactoryBagLog.setMchBillno(sendWxRedpackRespVO.getMch_billno());
    // userFactoryBagLog.setTransno(sendWxRedpackRespVO.getStateVO().getMsg());// 此字段现在不用，保存错误代码
    //
    // hasError = true;
    // noticeStr = "调用接口发放微信红包异常";
    // logger.info(noticeStr);
    // }
    // } else { // httpclient抛异常、http返回状态不是200、返回内容无法解析
    // stateVO.setCode(-2);
    // stateVO.setMsg("UNKNOWN_3");
    //
    // if (null != userFactoryBagLog) {
    // userFactoryBagLog.setSendFlag(-2);
    // userFactoryBagLog.setMchBillno("");
    // userFactoryBagLog.setTransno("UNKNOWN_3");// 此字段现在不用，保存错误代码
    // }
    //
    // hasError = true;
    // noticeStr = "调用mp_pay服务失败";
    // logger.info(noticeStr);
    // }
    //
    // // 无论是否发放成功，都更新发放记录。
    // if (null != userFactoryBagLog) {
    // try {
    // userFactoryBagLogDao.save(userFactoryBagLog);
    // } catch (Exception e) {
    // logger.info("更新微信红包发送记录时异常： " + e);
    // e.printStackTrace();
    //
    // // 注：更新发放日志是否成功，不影响返回值
    //
    // hasError = true;
    // noticeStr = "更新微信红包发送记录时异常";
    // }
    // }
    //
    // // 发放正常，通知厂家管理员
    // /*
    // * 统一发送通知，此处不再通知 if (sendWxRedpackRespVO.getStateVO().getCode() == 0) { String firstValue = "红包领取通知"; String keyword1Value = userFactoryBagLog.getMdn() + " 已扫描你的二维码并领取红包1个"; String keyword2Value = String.valueOf(userFactoryBagLog.getPayNum() / 100) + "元"; String remarkValue = "领取时间：" +
    // * DateUtil.getCurrentDateStr() + ",多多鼓励消费者，刺激消费者，小码科技祝生意兴隆！"; weixinNoticeService.factoryBagSendNotice(factoryId, firstValue, keyword1Value, keyword2Value, remarkValue); }
    // */
    //
    // // 发放异常（含记录日志异常），通知系统管理员。不提前return就是为了发送通知。
    // if (hasError) {
    // if ((sendWxRedpackRespVO.getStateVO().getCode() == -1 && "SYSTEMERROR".equals(sendWxRedpackRespVO.getStateVO().getMsg())) || (sendWxRedpackRespVO.getStateVO().getCode() == -2)) {
    // String firstValue = "红包发送异常";
    // String keyword1Value = "红包发送异常 sendRebPaper";
    // String keyword2Value = DateUtil.getCurrentDateStr();
    // String remarkValue = "factoryId=" + factoryId + ",openId=" + openId + ",amount=" + amount + ",officialUserId=" + officialUserId + ",mdn=" + mdn + ",=codeValue=" + codeValue
    // + ",noticeStr=" + noticeStr;
    // weixinNoticeService.systemExceptionNotice(firstValue, keyword1Value, keyword2Value, remarkValue);
    // } else if (sendWxRedpackRespVO.getStateVO().getCode() == -1 && "NOTENOUGH".equals(sendWxRedpackRespVO.getStateVO().getMsg())) {
    // String firstValue = "红包发送异常";
    // String keyword1Value = "红包发送异常：余额不足， 厂家id=" + factoryId;
    // String keyword2Value = DateUtil.getCurrentDateStr();
    // String remarkValue = "factoryId=" + factoryId + ",openId=" + openId + ",amount=" + amount + ",officialUserId=" + officialUserId + ",mdn=" + mdn + ",=codeValue=" + codeValue
    // + ",noticeStr=" + noticeStr;
    // weixinNoticeService.systemExceptionNotice(firstValue, keyword1Value, keyword2Value, remarkValue);
    // }
    // }
    //
    // return sendWxRedpackRespVO;
    // }
    //
    // @Override
    // public List<OpenUserRef> findOpenUserRefByOfficialUsereId(Long officialUserId) {
    // List<OpenUserRef> openUserRefs = openUserRefDao.findByOfficialUserId(officialUserId.toString());
    // return openUserRefs;
    // }
    //
    // @Override
    // public OpenUserRef findOpenUserRefByOfficialUsereIdAndAppId(Long officialUserId, String appId) {
    // List<OpenUserRef> openUserRefs = openUserRefDao.findByOfficialUserIdAndAppId(officialUserId.toString(), appId);
    // if (null != openUserRefs && 0 != openUserRefs.size()) {
    // // 如果有多条，取最后一条
    // return openUserRefs.get(openUserRefs.size() - 1);
    // } else {
    // return null;
    // }
    // }
    //
    @Override
    public WeixinSnsapiUserInfo findByOpenid(String openid) {
        // return waemyDBInteface.findByOpenid(openid);
        // TODO 通过接口调养获取数据库中保存的微信用户信息；
        return null;
    }
    //
    // // @Override
    // // public OpenUserRef findOpenUserRefByOfficialUsereIdAndFactoryId(Long officialUserId, Long factoryId) {
    // // return openUserRefDao.findByOfficialUserIdAndFactoryId(officialUserId.toString(), factoryId.toString());
    // // }
    //
    // // @Override
    // // public OpenUserRef findOpenUserRefByOpenIdAndFactoryId(String openid, Long factoryId) {
    // // return openUserRefDao.findByOpenidAndFactoryId(openid, factoryId.toString());
    // // }
    //
    // @Override
    // public void saveOpenUser(OpenUserRef openUserRef) {
    // openUserRefDao.save(openUserRef);
    // }
    //
    // @Override
    // public WeixinSnsapiUserInfo findByUnionid(String unionid) {
    // return weixinSnsapiUserInfoDao.findByUnionid(unionid);
    // }
    //
    // @Override
    // public void updateOpenUserRef(OpenUserRef openUserRef) {
    // openUserRefDao.save(openUserRef);
    // }
    //
    // @Override
    // public String getWeiMobRequest(Long factoryId, String REDIRECT_URI) {
    // // appid 是 微盟公众号的唯一标识
    // // redirect_uri 是 授权后重定向的回调链接地址，请使用urlencode对链接进行处理
    // // response_type 是 返回类型，请填写code
    // // scope 是 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
    // // state 否 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
    // String GetCodeRequest =
    // // "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    // "https://open.weimob.com/oauth2/openid/authorize?client_id=CLIENT_ID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE";
    // String result = null;
    // // 为丹泉酒业在微盟下的请求
    // GetCodeRequest = GetCodeRequest.replace("CLIENT_ID", urlEnodeUTF8(danQWeimAppId));
    // GetCodeRequest = GetCodeRequest.replace("REDIRECT_URI", urlEnodeUTF8(REDIRECT_URI));
    // GetCodeRequest = GetCodeRequest.replace("SCOPE", SCOPE);
    // result = GetCodeRequest;
    // return result;
    // }
    //
    // @Override
    // public WeixinUserMiddleVO getWeiMengUserMiddleVOByCodeAndState(long factoryId, String code) {
    // WeixinUserMiddleVO weixinUserMiddleVO = null;
    // try {
    // String httpUrl = "https://open.weimob.com/oauth2/openid/access_token";
    // // "?grant_type=authorization_code&
    // // client_id=CLIENT_ID&
    // // client_secret=CLIENT_SECRET&code=CODE";
    // httpUrl = httpUrl.replace("CLIENT_ID", urlEnodeUTF8(danQWeimAppId));
    // httpUrl = httpUrl.replace("CLIENT_SECRET", urlEnodeUTF8(danQWeimSecret));
    // httpUrl = httpUrl.replace("CODE", urlEnodeUTF8(code));
    // logger.info("第二次请求微盟，需要取得用户openId，httpurl=" + httpUrl);
    // RequestVo requestVo = new RequestVo();
    // requestVo.requestUrl = httpUrl;
    // requestVo.jsonParser = new WeixinUserMiddleUserRespParser();
    // requestVo.requestDataMap = new HashMap<String, String>();
    // requestVo.requestDataMap.put("grant_type", "authorization_code");
    // requestVo.requestDataMap.put("client_id", danQWeimAppId);
    // requestVo.requestDataMap.put("client_secret", danQWeimSecret);
    // requestVo.requestDataMap.put("code", code);
    // weixinUserMiddleVO = (WeixinUserMiddleVO) NetUtil.post(requestVo);
    // logger.info("微盟：WeixinUserMiddleVO=" + weixinUserMiddleVO.toString());
    // } catch (Exception e) {
    // logger.info("exception====getWeiMengUserMiddleVOByCodeAndState " + e);
    // }
    // return weixinUserMiddleVO;
    // }
    //
    // @Override
    // public OpenUserRef findOpenUserRefByAppIdAndOpenId(String appId, String openid) {
    // return openUserRefDao.findByAppIdAndOpenid(appId, openid);
    // }
    //
    // @Override
    // public OpenUserRef findOpenUserRefByOpenId(String openid) {
    // return openUserRefDao.findByOpenid(openid);
    // }
    //
    // // userMobileBindIs == 0时，如果未找到，则创建一个officialUser和一个openUserRef
    // @Override
    // public OpenUserRef findOpenUserRefByAppIdAndOpenId(String appId, String openid, Byte userMobileBindIs, Long factoryId, Long youzanFansId) {
    // OpenUserRef openUserRef = openUserRefDao.findByAppIdAndOpenid(appId, openid);
    // if (null != openUserRef) {
    // return openUserRef;
    // }
    //
    // if (0 == userMobileBindIs) {
    // Date updateDate = DateUtil.getCurrentDate();
    //
    // // 先创建一个officialUser
    // OfficialUser officialUser = new OfficialUser();
    //
    // officialUser.setCreateDate(updateDate);
    // officialUser.setUpdateDate(updateDate);
    // officialUser.setEnableFlag("Y");
    // officialUser.setUserId(0);
    // officialUser.setUserName(openid);
    // officialUser.setMdn(openid); // 以openid为Mdn
    // officialUser.setPasswd("000000");
    // // officialUser.setNickName("");
    // officialUser.setStatus(0);
    // officialUser.setGoodMoneyValue(10);
    // officialUser.setLastLoginDate(updateDate);
    // // officialUser.setUserRank("");
    // // officialUser.setUserPicUrl("");
    // officialUser.setUserType((byte) 2);
    // officialUser.setOpenid(openid);
    // officialUser.setAppId(appId);
    // officialUser.setFactoryId(factoryId);
    //
    // officialUserDao.save(officialUser);
    //
    // // 再创建一个openUserRef
    // openUserRef = new OpenUserRef();
    //
    // openUserRef.setCreateDate(updateDate);
    // openUserRef.setUpdateDate(updateDate);
    // openUserRef.setEnableFlag("Y");
    // openUserRef.setOfficialUserId(officialUser.getId().toString());
    // openUserRef.setMdn(officialUser.getMdn());
    // openUserRef.setUnionid("");
    // openUserRef.setFactoryId(factoryId.toString());
    // openUserRef.setAppId(appId);
    // openUserRef.setOpenid(openid);
    // openUserRef.setYouzanFansId(youzanFansId);
    //
    // openUserRefDao.save(openUserRef);
    //
    // // 返回结果
    // return openUserRef;
    // }
    //
    // // 没找到，也不创建新的
    // return null;
    // }
    //
    // @Override
    // public int getQrCodeRecordSum(String codeValue) {
    // logger.info("开始查询codeValue=" + codeValue + "；的扫描次数");
    // String realCodeValue = "";
    // if (codeValue != null && codeValue.length() > 0 && codeValue.indexOf("c=") != -1) {
    // int beginIndex = codeValue.indexOf("c=") + 2; // 直接去掉下载链接此码值为二维码中的实际值，明码值，非密文
    // realCodeValue = codeValue.substring(beginIndex);
    // } else {
    // realCodeValue = codeValue;
    // }
    // long recordSum = 0;
    // String hql_record = "select sum(total) from Record where codeValue = '" + realCodeValue + "' and codeType = 'qrcode' ";
    // logger.info("查询record的扫描记录，sql=" + hql_record);
    // recordSum = this.commonDao.countTByHql(hql_record); // 真假通的扫描次数
    // String hsq_weixin = "select count(DISTINCT openId) from Record3rd where scanClient = 'weixin' and  codeValue = '" + codeValue + "' ";
    // logger.info("查询Record3rd的扫描记录，sql=" + hsq_weixin);
    // int recordWeixinSum = this.commonDao.countTByHql(hsq_weixin); // 微信的扫描次数
    // return (int) (recordSum + recordWeixinSum);
    // }
    //
    // @Override
    // public FactoryWeixinMpConfig findWeixinMpConfigByFactoryId(long factoryId) {
    // return weixinMpConfigDao.findByFactoryId(factoryId);
    // }
    //
    // @Override
    // public void recordScanQrcodeInfo(Long factoryId, String scanUrl, String openid, String scanClient, String requestIp) {
    // try {
    // Record3rd record3rd = new Record3rd();
    // record3rd.setCreateDate(DateUtil.getCurrentDate());
    // record3rd.setUpdateDate(DateUtil.getCurrentDate());
    // record3rd.setEnableFlag("Y");
    // record3rd.setFactoryId(factoryId);
    // record3rd.setCodeValue(scanUrl);
    // record3rd.setOpenId(openid);
    // record3rd.setScanClient(scanClient);
    // record3rd.setTotal(1);
    // record3rd.setIp(requestIp);
    // logger.info("保存一次扫描记录,record3rd=" + record3rd);
    // this.record3rdDao.save(record3rd);
    // } catch (Exception e) {
    // e.printStackTrace();
    // logger.info("报警短信提示,新增三方扫描记录异常=" + e.getMessage());
    // }
    // }
    //
    // @SuppressWarnings("unused")
    // private void handleNoticeByFactoryAlert(long factoryId, String fullCodeValue, String openid, String scanClient, String requestIp, String lastScanDateStr) {
    // logger.info("开始检测试扫描次数是否需要报警短信提示");
    // FactoryRecordAlert factoryRecordAlert = factoryRecordAlertDao.findByFactoryId(factoryId);
    // logger.info("factoryRecordAlert=" + factoryRecordAlert);
    // if (factoryRecordAlert != null && scanClient.equalsIgnoreCase("weixin")) { // 已开通扫描报警功能
    // if (fullCodeValue != null && fullCodeValue.length() > 0 && fullCodeValue.indexOf("c=") != -1) {
    // int beginIndex = fullCodeValue.indexOf("c=") + 2; // 直接去掉下载链接此码值为二维码中的实际值，明码值，非密文
    // // 得到 codeValue实际值了。
    // String realCodeValue = fullCodeValue.substring(beginIndex);
    // // 1.1 获取‘该二维码’在Record记录的扫描次数(即用‘真假通APP’扫描的次数)
    // String hql_record = "select sum(total) from Record where codeValue = '" + realCodeValue + "' and codeType = 'qrcode' ";
    // long recordSum = this.commonDao.countTByHql(hql_record); // 真假通的扫描次数
    // // 1.2 获取‘该二维码’在Record3rd记录的扫描次数(即用‘weixin’扫描的次数;一个openId算作一次扫描，其余的APP扫描统一算一次)
    // String hsq_weixin = "select count(DISTINCT openId) from Record3rd where scanClient = 'weixin' and  codeValue = '" + fullCodeValue + "' ";
    // long recordWeixinSum = this.commonDao.countTByHql(hsq_weixin); // 微信的扫描次数
    // long totalScanSum = recordSum + recordWeixinSum;
    // if (factoryRecordAlert.getRecordSum() <= totalScanSum) {
    // // 如果报警数量小于实际扫描数量（同一用户，扫同一产品只算一次）
    // QrCode qrCode = this.qrCodeDao.findByCodeValue(realCodeValue);
    // String goodsTitle = "";
    // ProductInfo productInfo = null;
    // if (qrCode != null) productInfo = this.productService.getProductInfoById(qrCode.getProductId());
    // if (productInfo != null) goodsTitle = productInfo.getItemName();
    // logger.info("开始写入报警短信提示,请各路厂家用户前往真假通服务号进行后台账户绑定，才有可能收到预警提示");
    // // lastScanDateStr的格式化格式‘yyyy-MM-dd HH:mm:ss’
    // String content = buildAlertContent(recordSum, lastScanDateStr, factoryId, qrCode.getCodeNo(), goodsTitle, openid);
    // logger.info("报警短信提示内容：content = " + content);
    // if (factoryRecordAlert.getAlertType() == 0) {
    // String smsTitle = "真假通提示";
    // this.smsService.saveRecordSmsQuartz(smsTitle, content, factoryRecordAlert.getSendMdn());
    // if (factoryRecordAlert.getCopyMdn() != null && factoryRecordAlert.getCopyMdn().length() > 10) // 抄送
    // this.smsService.saveRecordSmsQuartz(smsTitle, content, factoryRecordAlert.getCopyMdn());
    //
    // }
    // if (factoryRecordAlert.getAlertType() == 3) {
    // try {
    // String firstValue = "报警短信提示";
    // String keyword1Value = "报警短信提示内容";
    // String keyword2Value = DateUtil.getCurrentDateStr();
    // String remarkValue = keyword1Value.toString() + content;
    // weixinNoticeService.factoryUserScanAlertNotice(factoryId, firstValue, keyword1Value, keyword2Value, remarkValue);
    // } catch (Exception e2) {
    // logger.info("weixin的Notice服务发送异常=" + e2.getMessage());
    // e2.printStackTrace();
    // }
    // }
    // }
    // }
    // }
    // }
    //
    // /**
    // * 报警内容提示 用于微信
    // * @param recorId
    // * @param factoryId
    // * @param codeNo
    // * @return
    // */
    // private String buildAlertContent(long recordSum, String scanDate, long factoryId, String codeNo, String productName, String openid) {
    // String content = "";
    // try {
    // content = this.factoryService.getFactoryInfoById(factoryId).getShortName();
    // content += "," + productName + " 产品,编号为:" + codeNo + ",扫描次数已达到" + recordSum + "次了,最后扫描时间:" + scanDate;
    // // 取扫描用户的信息
    // try {
    // WeixinSnsapiUserInfo weixinSnsapiUserInfo = null;
    // if (openid.length() > 0) {
    // weixinSnsapiUserInfo = this.weixinSnsapiUserInfoDao.findByOpenid(openid);
    // if (weixinSnsapiUserInfo != null) {
    // if (weixinSnsapiUserInfo.getNickname() != null) content += ",微信用户 " + weixinSnsapiUserInfo.getNickname();
    // }
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return content;
    // }
    //
    // @Override
    // public void recordWxScanRunLogAndCountLog(long factoryId, long productId, String qrCodeValue, String openId, String appId, long officialUserId, String requestIp, String sessionId) {
    // if (StringUtils.isNotBlank(openId) && !"null".equals(openId)) {
    // Date nowDate = DateUtil.getCurrentDate();
    // String hql = "select count(*) from RecordThirdLog where codeValue =:qrCodeValue and openId = :openId";
    // Map<String, Object> params = new HashMap<>();
    // // params.put("factoryId", factoryId);
    // params.put("qrCodeValue", qrCodeValue);
    // params.put("openId", openId);
    // int preOpenIdCount = commonDao.countTByHql(hql, params);
    // logger.info("该二维码，qrCodeValue=" + qrCodeValue + "的扫描记录RecordThirdLog的次数=" + preOpenIdCount);
    // // 1.新增流水日志记录
    // RecordThirdLog recordThirdLog = new RecordThirdLog();
    // recordThirdLog.setCreateDate(nowDate);
    // recordThirdLog.setUpdateDate(nowDate);
    // recordThirdLog.setEnableFlag("Y");
    //
    // recordThirdLog.setCodeValue(qrCodeValue);
    // recordThirdLog.setProductId(productId);
    // recordThirdLog.setAppid(appId);
    // recordThirdLog.setFactoryId(factoryId);
    // recordThirdLog.setOpenid(openId);
    // recordThirdLog.setOfficialUserId(officialUserId);
    // recordThirdLog.setIp(requestIp);
    // recordThirdLog.setUpdateChannel(2);
    // recordThirdLog.setUpdateSession(sessionId);
    // this.recordThirdLogDao.save(recordThirdLog);
    // // 2. 【更新|新增】recordQrcode
    // // RecordQrcode recordQrcode = recordQrcodeDao.findByFactoryIdAndCodeValue(factoryId, qrCodeValue);
    // RecordQrcode recordQrcode = recordQrcodeDao.findByCodeValue(qrCodeValue);
    // if (recordQrcode != null && recordQrcode.getId() > 0) {
    // recordQrcode.setClientUserId(0L);
    // recordQrcode.setOfficialUserId(officialUserId);
    // recordQrcode.setFactoryId(factoryId);
    // recordQrcode.setProductId(productId);
    // recordQrcode.setAppid(appId);// factoryId不变，一般不会更改
    // recordQrcode.setOpenid(openId);
    // if (preOpenIdCount == 0) {// 当前openId没有扫描过，次数增加
    // recordQrcode.setScanCount(recordQrcode.getScanCount() + 1);
    // recordQrcode.setScanCountPlusFlag(1);
    // }
    // // else {
    // // recordQrcode.setScanCount(1);
    // // recordQrcode.setScanCountPlusFlag(1);
    // // }
    // recordQrcode.setIp(requestIp);
    // recordQrcode.setIpUpdateChannel(2);// "真假通APP";2-weixin
    // recordQrcode.setIpUpdateSession(sessionId);
    // recordQrcode.setIpUpdateDate(DateUtil.getCurrentDate());
    // recordQrcode.setLat("");
    // recordQrcode.setLng("");
    // recordQrcode.setLatLngUpdateSession(sessionId);// "真假通APP"
    // recordQrcode.setLatLngUpdateChannel(2);// "真假通APP";2-weixin
    // recordQrcode.setLatLngUpdateDate(DateUtil.getCurrentDate());
    // recordQrcode.setAlertStatus(9);// 0:未处理，2:已处理，9:微信扫描临时状态
    // recordQrcodeDao.save(recordQrcode);
    // } else {
    // recordQrcode = new RecordQrcode();
    // recordQrcode.setClientUserId(0L);
    // recordQrcode.setOfficialUserId(officialUserId);
    // recordQrcode.setCodeValue(qrCodeValue);
    // recordQrcode.setFactoryId(factoryId);
    // recordQrcode.setProductId(productId);
    // recordQrcode.setScanCount(1);
    // recordQrcode.setScanCountPlusFlag(1);
    // recordQrcode.setAppid(appId);
    // recordQrcode.setOpenid(openId);
    // recordQrcode.setIp(requestIp);
    // recordQrcode.setIpUpdateChannel(2);// "真假通APP";2-weixin
    // recordQrcode.setIpUpdateSession(sessionId);
    // recordQrcode.setIpUpdateDate(nowDate);
    // recordQrcode.setLat("");
    // recordQrcode.setLng("");
    // recordQrcode.setLatLngUpdateSession(sessionId);// "真假通APP"
    // recordQrcode.setLatLngUpdateChannel(2);// "真假通APP";2-weixin
    // recordQrcode.setLatLngUpdateDate(nowDate);
    // recordQrcode.setAlertStatus(9);// 0:未处理，2:已处理，9:微信扫描临时状态
    // recordQrcode.setAddressType(0);
    // recordQrcode.setProvince("");
    // recordQrcode.setCity("");
    // recordQrcode.setStreet("");
    // recordQrcode.setFormattedAddress("");
    // recordQrcode.setCreateDate(nowDate);
    // recordQrcode.setUpdateDate(nowDate);
    // recordQrcodeDao.save(recordQrcode);
    // }
    // }
    // }
    //
    // @Override
    // public void recordScanQrcodeLog(String qrcodeValue, long factoryId, long productId, String appId, String crtOpenId, long tmpOfficialUserId, String reqUserAgentValue, String requestIp,
    // String sessionId) {
    // try {
    // Record3rd record3rd = new Record3rd();
    // record3rd.setEnableFlag("Y");
    // record3rd.setCreateDate(DateUtil.getCurrentDate());
    // record3rd.setUpdateDate(DateUtil.getCurrentDate());
    // record3rd.setCodeValue(qrcodeValue);
    // record3rd.setScanClient(reqUserAgentValue);
    // record3rd.setFactoryId(factoryId);
    // record3rd.setAppid(appId);
    // record3rd.setOpenId(crtOpenId);
    // record3rd.setIp(requestIp);
    // record3rd.setUpdateSession(sessionId);
    // record3rd.setTotal(1);
    // logger.info("保存扫描记录,record3rd=" + record3rd);
    // this.record3rdDao.save(record3rd);
    // } catch (Exception e) {
    // e.printStackTrace();
    // logger.info("报警短信提示,新增三方扫描记录异常=" + e.getMessage());
    // }
    // }
    //
    // @Override
    // public int getRecordQrCodeForTotal(long factoryId, String qrCodeValue) {
    // int scanCount = 0;
    // RecordQrcode recordQrcode = recordQrcodeDao.findByFactoryIdAndCodeValue(factoryId, qrCodeValue);
    // logger.info("factoryId=" + factoryId + ";qrCodeValue=" + qrCodeValue);
    // logger.info("recordQrcode=" + recordQrcode);
    // if (recordQrcode != null) {
    // scanCount = recordQrcode.getScanCount();
    // }
    // return scanCount;
    // }
    //
    // @Override
    // public void createOpenUser(Long officialUserId, String mdn, long factoryId, String appId, String openid, String unionid) {
    // try {
    // if (factoryId > 0) {
    // OpenUserRef openUserRef = new OpenUserRef();
    // openUserRef.setEnableFlag("Y");
    // openUserRef.setCreateDate(DateUtil.getCurrentDate());
    // openUserRef.setUpdateDate(DateUtil.getCurrentDate());
    // openUserRef.setMdn(mdn);
    // openUserRef.setOfficialUserId(officialUserId.toString());
    // openUserRef.setOpenid(openid);
    // openUserRef.setFactoryId(String.valueOf(factoryId));
    // openUserRef.setUnionid(unionid);
    // openUserRef.setAppId(appId);
    // this.openUserRefDao.save(openUserRef);
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    //
    // @Override
    // public void createOpenUser(Long officialUserId, String mdn, long factoryId, String openid, String unionid) {
    // try {
    // String appId = "";
    // if (factoryId > 0) {
    // FactoryBaseConfig factoryBaseConfig = this.factoryService.getFactoryBaseConfigByFactoryId(factoryId);
    // if (factoryBaseConfig != null) appId = factoryBaseConfig.getAppId();
    // OpenUserRef openUserRef = new OpenUserRef();
    // openUserRef.setEnableFlag("Y");
    // openUserRef.setCreateDate(DateUtil.getCurrentDate());
    // openUserRef.setUpdateDate(DateUtil.getCurrentDate());
    // openUserRef.setMdn(mdn);
    // openUserRef.setOfficialUserId(officialUserId.toString());
    // openUserRef.setOpenid(openid);
    // openUserRef.setFactoryId(String.valueOf(factoryId));
    // openUserRef.setUnionid(unionid);
    // openUserRef.setAppId(appId);
    // this.openUserRefDao.save(openUserRef);
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    //
    // @Override
    // public void createOpenUserWithFansId(Long factoryId, String appId, Long officialUserId, String mdn, String openId, String fansId) {
    // try {
    // if (factoryId > 0) {
    // OpenUserRef openUserRef = new OpenUserRef();
    // openUserRef.setEnableFlag("Y");
    // openUserRef.setCreateDate(DateUtil.getCurrentDate());
    // openUserRef.setUpdateDate(DateUtil.getCurrentDate());
    //
    // openUserRef.setFactoryId(String.valueOf(factoryId));
    // openUserRef.setAppId(appId);
    //
    // openUserRef.setOfficialUserId(officialUserId.toString());
    // openUserRef.setMdn(mdn);
    // openUserRef.setOpenid(openId);
    // openUserRef.setYouzanFansId(Long.parseLong(fansId));
    // this.openUserRefDao.save(openUserRef);
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    //
    // @Override
    // public String getRedirectWechatResponseUrlToRepeat(String app_id, String toRedirectUrl, String zjtRedirectUrl, String scope) {
    // String GetCodeRequest = "";
    // GetCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    // GetCodeRequest = GetCodeRequest.replace("APPID", urlEnodeUTF8(app_id));
    // zjtRedirectUrl += "&againUrl=" + urlEnodeUTF8(toRedirectUrl);
    // GetCodeRequest = GetCodeRequest.replace("REDIRECT_URI", urlEnodeUTF8(zjtRedirectUrl));
    // GetCodeRequest = GetCodeRequest.replace("SCOPE", scope);
    // return GetCodeRequest;
    // }
    
}
