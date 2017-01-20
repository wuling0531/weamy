package com.waemy.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.waemy.web.vo.response.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waemy.service.IAPICallService;
import com.waemy.service.IWeixinRefService;
import com.waemy.utils.weixin.WaemyWxInterfaceUtil;
import com.waemy.web.vo.base.PageVO;
import com.waemy.web.vo.base.StateVO;
import com.waemy.web.vo.base.WeixinSnsapiUserinfoVO;
import com.waemy.web.vo.base.WeixinUserMiddleVO;
import com.waemy.web.vo.weixin.WeiXinJsApiParamsVO;

/**
 * 麦哆啦-页面请求controller类
 */
@Controller
@RequestMapping(value = "/wx/m")
public class WeixinServiceController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(WeixinServiceController.class);

    @Autowired
    private IWeixinRefService weixinRefService;

    @Autowired
    private IAPICallService apiCallService;

    /**
     * 麦芽广场页面
     */
    @RequestMapping(value = "/square")
    public String showSquarePage(String code, String state, PageVO pageVO, Model model) {
        logger.info("/square 微信授权-获取当前扫描的微信用户的openId,当前授权回调的code=" + code + ",state=" + state);
        String mappingPage = "portal/squareHome";// 广场首页
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        try {
            logger.info("当前session中存在的session_OpenId=" + openId);
            // 判断当前session中是否保存了OpenId
            if (StringUtils.isBlank(openId) || "null".equals(openId)) {
                logger.info("获取WeixinUserMiddleVO对象===");
                WeixinUserMiddleVO weixinUserMiddleVO = weixinRefService.getWeixinUserMiddleVOByCode(code);
                if (weixinUserMiddleVO != null && StringUtils.isNotBlank(weixinUserMiddleVO.getOpenid())) {
                    openId = weixinUserMiddleVO.getOpenid();
                    // 管理OpenId(针对于‘真假通公众号’)
                    this.getSession().setAttribute("currentOpenId", weixinUserMiddleVO.getOpenid());
                    // 2.根据openid,调用微信用户信息接口->获取获取微信用户详情(如头像、昵称、位置等)
                    WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = null;
                    weixinSnsapiUserinfoVO = weixinRefService.getSnsapiUserinfo(WaemyWxInterfaceUtil.getWaemyAccessToken(), weixinUserMiddleVO.getOpenid());
                    logger.info("获取微信详情，weixinSnsapiUserinfoVO=" + weixinSnsapiUserinfoVO);
                    // 3. 保存‘微信用户’详情信息
                    if (weixinSnsapiUserinfoVO != null && StringUtils.isNotBlank(weixinSnsapiUserinfoVO.getOpenid())) {
                        // 可以保存至数据库,暂时不做处理;只是返回前台页面...
                        model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
                        this.getSession().setAttribute("currentWxUserInfo", weixinSnsapiUserinfoVO);
                    }
                }
            } else {
                // 将session中保存的wxUserInfo
                WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = (WeixinSnsapiUserinfoVO) this.getSession().getAttribute("currentWxUserInfo");
                model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
            }

            // 开始处理页面
            if (pageVO.getPageNo() < 1) {
                pageVO.setPageNo(1);
            }
            // List<SquareSongDetailVO> songDetailVOs = apiCallService.getMusicList(pageVO);
            // model.addAttribute("songDetailVOs", songDetailVOs);
            List<SquareeSongDetailWithPaiseVO> detailWithPaiseVOs = apiCallService.getMusicWithPraiseList(pageVO);
            model.addAttribute("detailWithPaiseVOs", detailWithPaiseVOs);
            // 2.0用于获取用户经纬度接口的微信接口参数对象
            // 2.1 动态获取当前请求的URL(域名为固定的deadCoding)
            String requestPath = this.getRequest().getRequestURI();
            String queryParamsStr = this.getRequest().getQueryString();
            if (queryParamsStr != null) {
                queryParamsStr = "?" + queryParamsStr;
                // 判断请求参数是否为空
                if (queryParamsStr.indexOf("'#'hash") != -1) {
                    queryParamsStr = queryParamsStr.substring(0, queryParamsStr.indexOf("'#'hash"));
                }
                requestPath += queryParamsStr;
            }
            WeiXinJsApiParamsVO weiXinJsApiParamsVO = WaemyWxInterfaceUtil.getWXJSInterfaceParamVO(requestPath);
            logger.info("*****weiXinJsApiParamsVO=" + weiXinJsApiParamsVO);
            model.addAttribute("interfaceParamVO", weiXinJsApiParamsVO);
            model.addAttribute("pageVO", pageVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mappingPage;
    }

    /**
     * 广场页面，上拉加载下一页数据
     *
     * @return
     */
    @RequestMapping(value = "/nextSquareData")
    @ResponseBody
    public MoreSquareSongDetailVO getMoreSquareData(PageVO pageVO) {
        MoreSquareSongDetailVO moreSquareSongDetailVO = new MoreSquareSongDetailVO();
        // 开始处理页面
        if (pageVO.getPageNo() < 1) {
            pageVO.setPageNo(1);
        }
        List<SquareeSongDetailWithPaiseVO> detailWithPaiseVOs = apiCallService.getMusicWithPraiseList(pageVO);
        moreSquareSongDetailVO.setDetailWithPaiseVOs(detailWithPaiseVOs);
        moreSquareSongDetailVO.setCurrentPageNo(pageVO.getPageNo());
        moreSquareSongDetailVO.setNext(pageVO.isNextPage());
        return moreSquareSongDetailVO;
    }

    /**
     * 歌曲详情页面
     *
     * @return
     */
    @RequestMapping(value = "/mydetail")
    public String mySongDetailPage(String squareMusicId, Model model) {
        String pageMapping = "portal/my-details";
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        try {
            if (StringUtils.isNotBlank(openId) && !"null".equals(openId)) {// 判断当前session中是否保存了OpenId
                // 2.根据openid,调用微信用户信息接口->获取获取微信用户详情(如头像、昵称、位置等)
                WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = weixinRefService.getSnsapiUserinfo(WaemyWxInterfaceUtil.getWaemyAccessToken(), openId);
                logger.info("获取微信详情，weixinSnsapiUserinfoVO=" + weixinSnsapiUserinfoVO);
                // 3. 保存‘微信用户’详情信息
                if (weixinSnsapiUserinfoVO != null && StringUtils.isNotBlank(weixinSnsapiUserinfoVO.getOpenid())) {
                    model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // 开始处理数据
            // SongDetailVO songDetailVO = apiCallService.getSongDetails(squareId);
            // model.addAttribute("songDetailVO", songDetailVO);
            SongDetailWithPraiseVO songDetailWithPraiseVO = apiCallService.getSongDetailsWithPraise(squareMusicId);
            Date songCreate = new Date(songDetailWithPraiseVO.getSongDetailVO().getCreatTime());
            model.addAttribute("songCreate", songCreate);
            model.addAttribute("songDetailWithPraiseVO", songDetailWithPraiseVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageMapping;
    }

    /**
     * 麦哆咪-活动展示页面
     *
     * @return
     */
    @RequestMapping(value = "/activity")
    public String showActivity(Model model) {
        ActivityListDateVO activityListDataVO = apiCallService.getActivityDataList();
        model.addAttribute("activityListDataVO", activityListDataVO);
        return "portal/activity";
    }

    /**
     * '我的'已发布、未发布歌曲列表页面
     *
     * @return
     */
    @RequestMapping(value = "/mysong")
    public String showMySongPublished(String code, String state, PageVO pageVO, Model model) {
        String pageMapping = "portal/published";
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        // String openId = "aaaa";
        try {
            if (StringUtils.isBlank(openId) || "null".equals(openId)) {// 判断当前session中是否保存了OpenId
                WeixinUserMiddleVO weixinUserMiddleVO = weixinRefService.getWeixinUserMiddleVOByCode(code);
                if (weixinUserMiddleVO != null && StringUtils.isNotBlank(weixinUserMiddleVO.getOpenid())) {
                    openId = weixinUserMiddleVO.getOpenid();
                    // 管理OpenId(针对于‘真假通公众号’)
                    this.getSession().setAttribute("currentOpenId", weixinUserMiddleVO.getOpenid());
                    // 2.根据openid,调用微信用户信息接口->获取获取微信用户详情(如头像、昵称、位置等)
                    WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = weixinRefService.getSnsapiUserinfo(WaemyWxInterfaceUtil.getWaemyAccessToken(), weixinUserMiddleVO.getOpenid());
                    logger.info("获取微信详情，weixinSnsapiUserinfoVO=" + weixinSnsapiUserinfoVO);
                    // 3. 保存‘微信用户’详情信息
                    if (weixinSnsapiUserinfoVO != null && StringUtils.isNotBlank(weixinSnsapiUserinfoVO.getOpenid())) {
//                        // 可以保存至数据库,暂时不做处理;只是返回前台页面...
                        this.getSession().setAttribute("currentWxUserInfo", weixinSnsapiUserinfoVO);
                        model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
                    }
                }
            } else {
                // 将session中保存的wxUserInfo
                WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = (WeixinSnsapiUserinfoVO) this.getSession().getAttribute("currentWxUserInfo");
                model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
            }
            if (StringUtils.isBlank(openId)) {// 没有获取到open
                pageMapping = "error/404";
            } else {
                // 处理页面数据
                if (pageVO.getPageNo() < 1) {
                    pageVO.setPageNo(1);
                }
                // List<SquareSongDetailVO> songDetailVOs = apiCallService.getCurrentUserPublishedList(openId, pageVO);
                // model.addAttribute("songDetailVOs", songDetailVOs);
                List<SquareSongDetailWithPraiseVO> detailWithPraiseVOs = apiCallService.getCurrentUserPublishedWithPraiseList(openId, pageVO);
                model.addAttribute("detailWithPraiseVOs", detailWithPraiseVOs);
            }
            // 2.0用于获取用户经纬度接口的微信接口参数对象
            // 2.1 动态获取当前请求的URL(域名为固定的deadCoding)
            String requestPath = this.getRequest().getRequestURI();
            String queryParamsStr = this.getRequest().getQueryString();
            if (queryParamsStr != null) {
                queryParamsStr = "?" + queryParamsStr;
                // 判断请求参数是否为空
                if (queryParamsStr.indexOf("'#'hash") != -1) {
                    queryParamsStr = queryParamsStr.substring(0, queryParamsStr.indexOf("'#'hash"));
                }
                requestPath += queryParamsStr;
            }
            WeiXinJsApiParamsVO weiXinJsApiParamsVO = WaemyWxInterfaceUtil.getWXJSInterfaceParamVO(requestPath);
            logger.info("*****weiXinJsApiParamsVO=" + weiXinJsApiParamsVO);
            model.addAttribute("interfaceParamVO", weiXinJsApiParamsVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageMapping;
    }

    /**
     * 未发布的歌曲列表页面
     *
     * @return
     */
    @RequestMapping(value = "/mynotpublish")
    public String mysongNotPublished(String publichStatus, PageVO pageVO, Model model) {
        String pageMapping = "portal/notPublished";// publichStatus=1,未发布状态
        if ("2".equals(publichStatus)) {// 已编辑状态的歌曲列表
            pageMapping = "portal/editPublished";
        }
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
//        String openId = "oI5MLwn9aeihsx-HEZ971MTg_yoY";
        try {
            if (StringUtils.isBlank(openId)) {// 没有获取到open
                pageMapping = "error/404";
            } else {
                // 将session中保存的wxUserInfo
                WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = (WeixinSnsapiUserinfoVO) this.getSession().getAttribute("currentWxUserInfo");
                model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
                // 处理页面数据
                if (pageVO.getPageNo() < 1) {
                    pageVO.setPageNo(1);
                }
                List<SongNoPublishedDetailVO> songNoPublishedDetailVOs = apiCallService.getCurrentUserNotPublishedList(openId, publichStatus, pageVO);
                model.addAttribute("songNoPublishedDetailVOs", songNoPublishedDetailVOs);
            }
            // 2.0用于获取用户经纬度接口的微信接口参数对象
            // 2.1 动态获取当前请求的URL(域名为固定的deadCoding)
            String requestPath = this.getRequest().getRequestURI();
            String queryParamsStr = this.getRequest().getQueryString();
            if (queryParamsStr != null) {
                queryParamsStr = "?" + queryParamsStr;
                // 判断请求参数是否为空
                if (queryParamsStr.indexOf("'#'hash") != -1) {
                    queryParamsStr = queryParamsStr.substring(0, queryParamsStr.indexOf("'#'hash"));
                }
                requestPath += queryParamsStr;
            }
            WeiXinJsApiParamsVO weiXinJsApiParamsVO = WaemyWxInterfaceUtil.getWXJSInterfaceParamVO(requestPath);
            logger.info("*****weiXinJsApiParamsVO=" + weiXinJsApiParamsVO);
            model.addAttribute("interfaceParamVO", weiXinJsApiParamsVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageMapping;
    }

    /**
     * 删除歌曲
     *
     * @return
     */
    @RequestMapping(value = "/deleteSong")
    public String deleteSong(String musicId, String type) {
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        // String openId = "aaaa";
        apiCallService.deleteCurrentSong(openId, musicId);
        if ("1".equals(type)) {
            return "redirect:/wx/m/mynotpublish?publichStatus=1";// 跳转到未发布页面

        } else {// 2为已发布页面
            return "redirect:/wx/m/mynotpublish?publichStatus=2";// 跳转到未发布页面
        }
    }

    /**
     * 编辑页面
     *
     * @return
     */
    @RequestMapping(value = "/editSong")
    public String editSong(String baseMusicId, Model model) {
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        try {
            if (StringUtils.isNotBlank(openId) && !"null".equals(openId)) {// 判断当前session中是否保存了OpenId
                // 2.根据openid,调用微信用户信息接口->获取获取微信用户详情(如头像、昵称、位置等)
                WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = weixinRefService.getSnsapiUserinfo(WaemyWxInterfaceUtil.getWaemyAccessToken(), openId);
                logger.info("获取微信详情，weixinSnsapiUserinfoVO=" + weixinSnsapiUserinfoVO);
                // 3. 保存‘微信用户’详情信息
                if (weixinSnsapiUserinfoVO != null && StringUtils.isNotBlank(weixinSnsapiUserinfoVO.getOpenid())) {
                    model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("baseMusicId", baseMusicId);
        SongDetailVO songDetailVO = apiCallService.getSongDetails(baseMusicId);
        model.addAttribute("songDetailVO", songDetailVO);
        // 提交图片后的重定向url
        model.addAttribute("picRedirect", WaemyWxInterfaceUtil.CURRENT_DOMAIN + "/wx/m/respInfo");
        // model.addAttribute("picRedirect", "http://127.0.0.1:86//wx/m/respInfo");
        return "portal/editSong";
    }

    /**
     * 图片上传的response返回信息
     *
     * @return
     */
    @RequestMapping(value = "/respInfo")
    @ResponseBody
    public Map<String, String> picRedirectInfoPage(String data, String resCode) {
        Map<String, String> respData = new HashMap<String, String>();
        respData.put("data", data);
        respData.put("resCode", resCode);
        return respData;
    }

    /**
     * 编辑页面-编辑后提交<br/>
     * type=1,编辑提交；type=2，发布提交
     *
     * @return
     */
    @RequestMapping(value = "/editSongSubmit")
    public String editSongSubmit(String baseMusicId, String coverPicUrl, String boardStr, String subType) {
        //
        if ("1".equals(subType)) {// 编辑完提交
            apiCallService.editSongSubmit(baseMusicId, coverPicUrl, boardStr);
            return "redirect:/wx/m/mynotpublish?publichStatus=2";// 跳转到已编辑页面
        } else {// 直接发布
            String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
            // String openId = "aaaa";
            apiCallService.directReleaseSong(openId, baseMusicId, coverPicUrl, boardStr);
            return "redirect:/wx/m/mysong";// 跳转到已编辑页面
        }
    }

    /**
     * 已编辑页面的歌曲跳转到发布歌曲页面
     *
     * @return
     */
    @RequestMapping(value = "/forwardReleaseSong")
    public String fowardSongReleasePage(String squareId) {
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        // String openId = "oI5MLwnF-sZ072RetpsYaYki4N8c";
        apiCallService.releaseSong(openId, squareId);
        return "redirect:/wx/m/mysong";// 跳转到已发布页面
    }

    /**
     * 发布歌曲提交
     *
     * @return
     */
    @RequestMapping(value = "/releaseSong")
    public String releaseSong(String musicId) {
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        // String openId = "aaaa";
        apiCallService.releaseSong(openId, musicId);
        return "redirect:/wx/m/mysong";// 跳转到发布页面
    }

    /**
     * 播放次数增加接口
     *
     * @return
     */
    @RequestMapping(value = "/addplaytimes")
    @ResponseBody
    public StateVO addPlayTimes(String squareMusicId) {
        StateVO stateVO = new StateVO();
        try {
            apiCallService.addSongPlayedTimes(squareMusicId);
        } catch (Exception e) {
            stateVO.setCode(-1);//
            e.printStackTrace();
        }
        return stateVO;
    }

    /**
     * 点赞增加接口
     *
     * @return
     */
    @RequestMapping(value = "/addPraiseTimes")
    @ResponseBody
    public StateVO addPraiseTimes(String squareMusicId) {
        StateVO stateVO = new StateVO();
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        // String openId = "aaaa";
        try {
            apiCallService.addSongPraisedTimes(squareMusicId, openId);
        } catch (Exception e) {
            stateVO.setCode(-1);//
            e.printStackTrace();
        }
        return stateVO;
    }

    /**
     * 一键关注页面
     *
     * @return
     */
    @RequestMapping(value = "/followPublic")
    public String followWeChatPublic(String code, String state, String deviceId, Model model) {
        logger.info("/square 微信授权-获取当前扫描的微信用户的openId,当前授权回调的code=" + code + ",state=" + state);
        String mappingPage = "portal/follow";// 广场首页
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        try {
            // logger.info("‘真假通公众号’的服务页面请求；当前绑定的openId=" + openId);
            if (StringUtils.isNotBlank(code)) {
                if (StringUtils.isBlank(openId) || "null".equals(openId)) {// 判断当前session中是否保存了OpenId
                    WeixinUserMiddleVO weixinUserMiddleVO = weixinRefService.getWeixinUserMiddleVOByCode(code);
                    if (weixinUserMiddleVO != null && StringUtils.isNotBlank(weixinUserMiddleVO.getOpenid())) {
                        openId = weixinUserMiddleVO.getOpenid();
                        this.getSession().setAttribute("currentOpenId", openId);
//                        this.getSession().setAttribute("currentOpenId", weixinUserMiddleVO.getOpenid());
//                        // 管理OpenId(针对于‘真假通公众号’)
                    }
                }
            }
            apiCallService.addFollowsToWechat(openId, deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mappingPage;
    }

    /**
     * 微信充值页面
     *
     * @return
     */
    @RequestMapping(value = "/rechargePage")
    public String chargePage(String code, String state, Model model) {
        String pageMapping = "portal/rechargePage";
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        // String openId = "oI5MLwolykWwi_urX2SUFY09oKr8";
        try {
            if (StringUtils.isBlank(openId) || "null".equals(openId)) {// 判断当前session中是否保存了OpenId
                WeixinUserMiddleVO weixinUserMiddleVO = weixinRefService.getWeixinUserMiddleVOByCode(code);
                if (weixinUserMiddleVO != null && StringUtils.isNotBlank(weixinUserMiddleVO.getOpenid())) {
                    openId = weixinUserMiddleVO.getOpenid();
                    // 管理OpenId(针对于‘真假通公众号’)
                    this.getSession().setAttribute("currentOpenId", weixinUserMiddleVO.getOpenid());
                    // 2.根据openid,调用微信用户信息接口->获取获取微信用户详情(如头像、昵称、位置等)
                    WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = weixinRefService.getSnsapiUserinfo(WaemyWxInterfaceUtil.getWaemyAccessToken(), weixinUserMiddleVO.getOpenid());
                    logger.info("获取微信详情，weixinSnsapiUserinfoVO=" + weixinSnsapiUserinfoVO);
                    // 3. 保存‘微信用户’详情信息
                    if (weixinSnsapiUserinfoVO != null && StringUtils.isNotBlank(weixinSnsapiUserinfoVO.getOpenid())) {
                        model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
                        // 可以保存至数据库,暂时不做处理;只是返回前台页面...
                        this.getSession().setAttribute("currentWxUserInfo", weixinSnsapiUserinfoVO);
                    }
                }
            } else {
                // 将session中保存的wxUserInfo
                WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = (WeixinSnsapiUserinfoVO) this.getSession().getAttribute("currentWxUserInfo");
                model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
            }
            if (StringUtils.isBlank(openId)) {// 没有获取到open
                pageMapping = "error/404";
            } else {
                List<RechargeCommodityVO> commodityVOs = apiCallService.getRechargeCommodityList();
                model.addAttribute("commodityVOs", commodityVOs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageMapping;
    }

    /**
     * @return
     */
    @RequestMapping(value = "/recharge")
    public String toRecharge(HttpServletRequest request, String productId, String paytype, String code, String state, Model model) {
        logger.info("/square 微信授权-获取当前扫描的微信用户的openId,当前授权回调的code=" + code + ",state=" + state);
        String mappingPage = "portal/payToWX";// 广场首页
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        try {
            // logger.info("‘真假通公众号’的服务页面请求；当前绑定的openId=" + openId);
            if (StringUtils.isNotBlank(code)) {
                if (StringUtils.isBlank(openId) || "null".equals(openId)) {// 判断当前session中是否保存了OpenId
                    WeixinUserMiddleVO weixinUserMiddleVO = weixinRefService.getWeixinUserMiddleVOByCode(code);
                    if (weixinUserMiddleVO != null && StringUtils.isNotBlank(weixinUserMiddleVO.getOpenid())) {
                        openId = weixinUserMiddleVO.getOpenid();
                        // 管理OpenId(针对于‘真假通公众号’)
                        this.getSession().setAttribute("currentOpenId", weixinUserMiddleVO.getOpenid());
                    }
                }
            }
            // 获取当前项目的绝对路径
            String realPath = request.getSession().getServletContext().getRealPath("/");// 获取当前的详细路径
            String payQrcodeImg = apiCallService.createPayWxQrcodeImg(realPath, openId, productId, paytype);
            model.addAttribute("payQrcodeImg", payQrcodeImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mappingPage;
    }

    /**
     * 充值记录列表
     *
     * @return
     */
    @RequestMapping(value = "/rechargeRecord")
    public String chargeRecordPage(PageVO pageVO, String code, String state, Model model) {
        String pageMapping = "portal/rechargeRecord";
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        // String openId = "oI5MLwolykWwi_urX2SUFY09oKr8";
        // String openId = "oI5MLwolykWwi_urX2SUFY09oKr8";
        try {
            if (StringUtils.isBlank(openId) || "null".equals(openId)) {// 判断当前session中是否保存了OpenId
                WeixinUserMiddleVO weixinUserMiddleVO = weixinRefService.getWeixinUserMiddleVOByCode(code);
                if (weixinUserMiddleVO != null && StringUtils.isNotBlank(weixinUserMiddleVO.getOpenid())) {
                    openId = weixinUserMiddleVO.getOpenid();
                    // 管理OpenId(针对于‘真假通公众号’)
                    this.getSession().setAttribute("currentOpenId", weixinUserMiddleVO.getOpenid());
                    // 2.根据openid,调用微信用户信息接口->获取获取微信用户详情(如头像、昵称、位置等)
                    WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = weixinRefService.getSnsapiUserinfo(WaemyWxInterfaceUtil.getWaemyAccessToken(), weixinUserMiddleVO.getOpenid());
                    logger.info("获取微信详情，weixinSnsapiUserinfoVO=" + weixinSnsapiUserinfoVO);
//                    // 3. 保存‘微信用户’详情信息
                    if (weixinSnsapiUserinfoVO != null && StringUtils.isNotBlank(weixinSnsapiUserinfoVO.getOpenid())) {
                        // 可以保存至数据库,暂时不做处理;只是返回前台页面...
                        this.getSession().setAttribute("currentWxUserInfo", weixinSnsapiUserinfoVO);
                        model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
                    }
                }
            } else {
                // 将session中保存的wxUserInfo
                WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = (WeixinSnsapiUserinfoVO) this.getSession().getAttribute("currentWxUserInfo");
                model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
            }
            if (StringUtils.isBlank(openId)) {// 没有获取到open
                pageMapping = "error/404";
            } else {
                // 处理页面数据
                if (pageVO.getPageNo() < 1) {
                    pageVO.setPageNo(1);
                }
                List<RechargeDetailVO> rechargeDetailVOs = apiCallService.getMyAccountDetailList(openId, pageVO);
                model.addAttribute("rechargeDetailVOs", rechargeDetailVOs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageMapping;

    }

    /**
     * 麦币说明页
     *
     * @return
     */
    @RequestMapping(value = "/coinDesc")
    public String maiCoinDesc() {
        return "portal/coinDesc";
    }

    /**
     * 活动优惠券
     *
     * @return
     */
    @RequestMapping(value = "/myCoupon")
    public String myCoupon(PageVO pageVO, Integer status, String houseId, String code, String state, Model model) {
        String pageMapping = "portal/myCoupon";
        if (status != 1) {
            pageMapping = "portal/usedCoupon";
        }
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        // String openId = "oI5MLwolykWwi_urX2SUFY09oKr8";
        if (StringUtils.isNotBlank(houseId)) {
            model.addAttribute("houseId", houseId);
        }
        try {
            if (StringUtils.isBlank(openId) || "null".equals(openId)) {// 判断当前session中是否保存了OpenId
                WeixinUserMiddleVO weixinUserMiddleVO = weixinRefService.getWeixinUserMiddleVOByCode(code);
                if (weixinUserMiddleVO != null && StringUtils.isNotBlank(weixinUserMiddleVO.getOpenid())) {
                    openId = weixinUserMiddleVO.getOpenid();
                    // 管理OpenId(针对于‘真假通公众号’)
                    this.getSession().setAttribute("currentOpenId", weixinUserMiddleVO.getOpenid());
                    // 2.根据openid,调用微信用户信息接口->获取获取微信用户详情(如头像、昵称、位置等)
                    WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = null;
                    weixinSnsapiUserinfoVO = weixinRefService.getSnsapiUserinfo(WaemyWxInterfaceUtil.getWaemyAccessToken(), weixinUserMiddleVO.getOpenid());
                    logger.info("获取微信详情，weixinSnsapiUserinfoVO=" + weixinSnsapiUserinfoVO);
                    // 3. 保存‘微信用户’详情信息
                    if (weixinSnsapiUserinfoVO != null && StringUtils.isNotBlank(weixinSnsapiUserinfoVO.getOpenid())) {
                        // 可以保存至数据库,暂时不做处理;只是返回前台页面...
                        this.getSession().setAttribute("currentWxUserInfo", weixinSnsapiUserinfoVO);
                        model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
                    }
                }
            } else {
                // 将session中保存的wxUserInfo
                WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = (WeixinSnsapiUserinfoVO) this.getSession().getAttribute("currentWxUserInfo");
                model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
            }
            if (StringUtils.isBlank(openId)) {// 没有获取到open
                pageMapping = "error/404";
            } else {
                // 处理页面数据
                if (pageVO.getPageNo() < 1) {
                    pageVO.setPageNo(1);
                }
                List<CouponDetailVO> couponDetailVOs = apiCallService.getMyCouponDetailList(openId, status);
                model.addAttribute("couponDetailVOs", couponDetailVOs);
                // List<RechargeDetailVO> accountDetailVOs = apiCallService.getMyAccountDetailList(openId);
                // model.addAttribute("accountDetailVOs", accountDetailVOs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageMapping;

    }

    /**
     * @return
     */
    @RequestMapping(value = "/activateCoupon")
    public String activateCoupon(String couponNo, String houseId) {
        // 激活后，跳转到已使用页面
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        // String openId = "oI5MLwolykWwi_urX2SUFY09oKr8";
        try {
            if (StringUtils.isBlank(openId)) {// 没有获取到open
                return "error/404";
            } else {
                // 激活优惠券
                apiCallService.activiteCoupon(couponNo, openId, houseId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/wx/m/myCoupon?status=2";
    }

    /**
     * @return
     */
    @RequestMapping(value = "/activateCouponByAjax")
    @ResponseBody
    public StateVO activateCouponByAjax(String couponNo, String houseId) {
        StateVO stateVO = new StateVO();
        // 激活后，跳转到已使用页面
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
        // String openId = "oI5MLwolykWwi_urX2SUFY09oKr8";
        try {
            if (StringUtils.isBlank(openId)) {// 没有获取到open
                stateVO.setCode(-1);
                stateVO.setMsg("未获取到微信用户信息");
            } else {
                if(StringUtils.isNotBlank(houseId)){
                    // 激活优惠券
                    CommonRespNotDataVO respNotDataVO = apiCallService.activiteCoupon(couponNo, openId, houseId);
                    if(!"1".equals(respNotDataVO.getResCode())){
                        stateVO.setCode(-1);
                    }
                }else{
                    stateVO.setCode(-1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stateVO;
    }

    @RequestMapping(value = "/usedCoupon")
    public String usedCoupon(String status) {
        return "portal/usedCoupon";
    }

    @RequestMapping(value = "/myAccount")
    public String myAccount(String code, String state, Model model) {
        String pageMapping = "portal/myAccount";
        String openId = String.valueOf(this.getSession().getAttribute("currentOpenId"));// 获取当前session中保存的当前openId
//        String openId = "oI5MLwolykWwi_urX2SUFY09oKr8";
        try {
            if (StringUtils.isBlank(openId) || "null".equals(openId)) {// 判断当前session中是否保存了OpenId
                WeixinUserMiddleVO weixinUserMiddleVO = weixinRefService.getWeixinUserMiddleVOByCode(code);
                if (weixinUserMiddleVO != null && StringUtils.isNotBlank(weixinUserMiddleVO.getOpenid())) {
                    openId = weixinUserMiddleVO.getOpenid();
                    // 管理OpenId(针对于‘真假通公众号’)
                    this.getSession().setAttribute("currentOpenId", weixinUserMiddleVO.getOpenid());
                    // 2.根据openid,调用微信用户信息接口->获取获取微信用户详情(如头像、昵称、位置等)
                    WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = null;
                    weixinSnsapiUserinfoVO = weixinRefService.getSnsapiUserinfo(WaemyWxInterfaceUtil.getWaemyAccessToken(), weixinUserMiddleVO.getOpenid());
                    logger.info("获取微信详情，weixinSnsapiUserinfoVO=" + weixinSnsapiUserinfoVO);
                    // 3. 保存‘微信用户’详情信息
                    if (weixinSnsapiUserinfoVO != null && StringUtils.isNotBlank(weixinSnsapiUserinfoVO.getOpenid())) {
                        // 可以保存至数据库,暂时不做处理;只是返回前台页面...
                        this.getSession().setAttribute("currentWxUserInfo", weixinSnsapiUserinfoVO);
                        model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
                    }
                }
            } else {
                // 将session中保存的wxUserInfo
                WeixinSnsapiUserinfoVO weixinSnsapiUserinfoVO = (WeixinSnsapiUserinfoVO) this.getSession().getAttribute("currentWxUserInfo");
                model.addAttribute("wxUserInfo", weixinSnsapiUserinfoVO);
            }
            if (StringUtils.isBlank(openId)) {// 没有获取到open
                pageMapping = "error/404";
            } else {
                AccountInfoVO accountInfoVO = apiCallService.getMyAccountInfo(openId);
                model.addAttribute("accountInfoVO", accountInfoVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageMapping;

    }
}
