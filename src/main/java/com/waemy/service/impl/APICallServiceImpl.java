package com.waemy.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.waemy.web.vo.response.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.waemy.service.IAPICallService;
import com.waemy.utils.DateUtil;
import com.waemy.utils.NetUtil;
import com.waemy.utils.TwoDimensionCode;
import com.waemy.web.vo.base.PageVO;
import com.waemy.web.vo.parse.AccountInfoDataRespParse;
import com.waemy.web.vo.parse.ActivityDataRespParse;
import com.waemy.web.vo.parse.CommonRespNotDataParse;
import com.waemy.web.vo.parse.CouponListDataRespParse;
import com.waemy.web.vo.parse.MusicListDataRespParse;
import com.waemy.web.vo.parse.PayLinkInfoDataParse;
import com.waemy.web.vo.parse.RechargeCommodityListDataRespParse;
import com.waemy.web.vo.parse.RechargeListDataRespParse;
import com.waemy.web.vo.parse.SongDetailRespParse;
import com.waemy.web.vo.parse.SongNoPublishedListDataRespParse;
import com.waemy.web.vo.parse.SongPraiseRespParse;
import com.waemy.web.vo.request.RequestVo;

@Component
public class APICallServiceImpl implements IAPICallService {

    private static Logger logger = LoggerFactory.getLogger(APICallServiceImpl.class);

    @Override
    public List<SquareSongDetailVO> getMusicList(PageVO pageVO) {
        List<SquareSongDetailVO> songDetailVOs = new ArrayList<>();
        MusicListDataRespVO respVO = null;
        String musicListUrl = "http://101.201.41.109/maemy/api/square/music/list/";
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = musicListUrl;
            requestVo.jsonParser = new MusicListDataRespParse();
            requestVo.requestDataMap = new HashMap<>();
            requestVo.requestDataMap.put("offset", String.valueOf(pageVO.getPageNo()));
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            respVO = (MusicListDataRespVO) NetUtil.post(requestVo);
            // model.addAttribute("mallProListRespVO", respVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (respVO.getData() != null && respVO.getData().getList() != null && respVO.getData().getList().size() > 0) {
            songDetailVOs = respVO.getData().getList();
            if ("0".equals(respVO.getData().getTail())) {// 不是最后一页
                pageVO.setNextPage(true);//
            }
        }
        return songDetailVOs;
    }

    @Override
    public List<SquareeSongDetailWithPaiseVO> getMusicWithPraiseList(PageVO pageVO) {
        List<SquareeSongDetailWithPaiseVO> detailWithPaiseVOs = new ArrayList<>();
        MusicListDataRespVO respVO = null;
        String musicListUrl = "http://101.201.41.109/maemy/api/square/music/list/";
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = musicListUrl;
            requestVo.jsonParser = new MusicListDataRespParse();
            requestVo.requestDataMap = new HashMap<>();
            requestVo.requestDataMap.put("offset", String.valueOf(pageVO.getPageNo()));
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            respVO = (MusicListDataRespVO)(NetUtil.post(requestVo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (respVO.getData() != null && respVO.getData().getList() != null && respVO.getData().getList().size() > 0) {
            if ("0".equals(respVO.getData().getTail())) {// 不是最后一页
                pageVO.setNextPage(true);//
            } else {
                pageVO.setNextPage(false);//
            }
            SquareeSongDetailWithPaiseVO detailWithPaiseVO = null;
            RequestVo praiseReqVo = new RequestVo();
            SongPraiseDataVO praiseDataVO = null;
            for (SquareSongDetailVO detailVO : respVO.getData().getList()) {
                detailWithPaiseVO = new SquareeSongDetailWithPaiseVO();
                detailWithPaiseVO.setSongDetailVO(detailVO);
//                String praiseListUrl = "http://101.201.41.109/maemy/api/praise/list/music/" + detailVO.getSquare_id();
                String praiseListUrl = "http://101.201.41.109/maemy/api/praise/list/music/" + detailVO.getMusic_id();
                praiseReqVo.requestUrl = praiseListUrl;
                praiseReqVo.jsonParser = new SongPraiseRespParse();
                logger.info("开始请求：requestUrl_get=" + praiseReqVo.requestUrl);
                praiseDataVO = (SongPraiseDataVO) NetUtil.post(praiseReqVo);
                if (praiseDataVO != null) {
                    detailWithPaiseVO.setPraiseDetailVOs(praiseDataVO.getData().getPraiseList());
                    logger.info("当前返回的prziseNum = "+praiseDataVO.getData().getPraiseNum());
                    detailWithPaiseVO.setPraiseNum(praiseDataVO.getData().getPraiseNum());
                }
                detailWithPaiseVOs.add(detailWithPaiseVO);
            }
        }
        return detailWithPaiseVOs;
    }

    @Override
    public List<SquareSongDetailVO> getCurrentUserPublishedList(String openId, PageVO pageVO) {
        List<SquareSongDetailVO> songDetailVOs = new ArrayList<>();
        MusicListDataRespVO respVO = null;
        String musicListUrl = "http://101.201.41.109/maemy/api/square/music/list/user/" + openId;
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = musicListUrl;
            requestVo.jsonParser = new MusicListDataRespParse();
            requestVo.requestDataMap = new HashMap<>();
            requestVo.requestDataMap.put("offset", String.valueOf(pageVO.getPageNo()));
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            respVO = (MusicListDataRespVO) NetUtil.post(requestVo);
            // model.addAttribute("mallProListRespVO", respVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (respVO.getData() != null && respVO.getData().getList() != null && respVO.getData().getList().size() > 0) {
            songDetailVOs = respVO.getData().getList();
            if ("0".equals(respVO.getData().getTail())) {// 不是最后一页
                pageVO.setNextPage(true);//
            }
        }
        return songDetailVOs;
    }

    @Override
    public List<SquareSongDetailWithPraiseVO> getCurrentUserPublishedWithPraiseList(String openId, PageVO pageVO) {
        List<SquareSongDetailWithPraiseVO> detailWithPraiseVOs = new ArrayList<>();
        // List<SquareSongDetailVO> songDetailVOs = new ArrayList<>();
        MusicListDataRespVO respVO = null;
        String musicListUrl = "http://101.201.41.109/maemy/api/square/music/list/user/" + openId;
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = musicListUrl;
            requestVo.jsonParser = new MusicListDataRespParse();
            requestVo.requestDataMap = new HashMap<>();
            requestVo.requestDataMap.put("offset", String.valueOf(pageVO.getPageNo()));
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            respVO = (MusicListDataRespVO) NetUtil.post(requestVo);
            // model.addAttribute("mallProListRespVO", respVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (respVO.getData() != null && respVO.getData().getList() != null && respVO.getData().getList().size() > 0) {
            if ("0".equals(respVO.getData().getTail())) {// 不是最后一页
                pageVO.setNextPage(true);//
            }
            // songDetailVOs = respVO.getData().getList();
            SquareSongDetailWithPraiseVO detailWithPraiseVO = null;
            RequestVo praiseReqVo = new RequestVo();
            SongPraiseDataVO praiseDataVO = null;
            for (SquareSongDetailVO songDetailVO : respVO.getData().getList()) {
                detailWithPraiseVO = new SquareSongDetailWithPraiseVO();
                detailWithPraiseVO.setSongDetailVO(songDetailVO);
                String praiseListUrl = "http://101.201.41.109/maemy/api/praise/list/music/" + songDetailVO.getSquare_id();
                praiseReqVo.requestUrl = praiseListUrl;
                praiseReqVo.jsonParser = new SongPraiseRespParse();
                logger.info("开始请求：requestUrl_get=" + praiseReqVo.requestUrl);
                praiseDataVO = (SongPraiseDataVO) NetUtil.post(praiseReqVo);
                if (praiseDataVO != null) {
                    detailWithPraiseVO.setPraiseDetailVOs(praiseDataVO.getData().getPraiseList());
                    detailWithPraiseVO.setPraiseNum(praiseDataVO.getData().getPraiseNum());
                }
                detailWithPraiseVOs.add(detailWithPraiseVO);
            }
        }
        return detailWithPraiseVOs;
    }

    /**
     * publichStatus：1未发布 2已编辑
     */
    @Override
    public List<SongNoPublishedDetailVO> getCurrentUserNotPublishedList(String openId, String publichStatus, PageVO pageVO) {
        List<SongNoPublishedDetailVO> songNoPublishedDetailVOs = new ArrayList<>();
        SongNoPublishedListDataRespVO respVO = null;
        String musicListUrl = "http://101.201.41.109/maemy/api/music/list/user/" + openId + "/status/" + publichStatus;
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = musicListUrl;
            requestVo.jsonParser = new SongNoPublishedListDataRespParse();
            requestVo.requestDataMap = new HashMap<>();
            requestVo.requestDataMap.put("offset", String.valueOf(pageVO.getPageNo()));
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            respVO = (SongNoPublishedListDataRespVO) NetUtil.post(requestVo);
            // model.addAttribute("mallProListRespVO", respVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (respVO.getData() != null && respVO.getData().getList() != null && respVO.getData().getList().size() > 0) {
            songNoPublishedDetailVOs = respVO.getData().getList();
            if ("0".equals(respVO.getData().getTail())) {// 不是最后一页
                pageVO.setNextPage(true);//
            }
        }
        return songNoPublishedDetailVOs;
    }

    @Override
    public void deleteCurrentSong(String openId, String squareId) {
        String deleteMusicUrl = "http://101.201.41.109/maemy/api/music/user/" + openId + "/music/" + squareId + "/delete";
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = deleteMusicUrl;
            requestVo.jsonParser = new CommonRespNotDataParse();
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            NetUtil.get(requestVo);
            // model.addAttribute("mallProListRespVO", respVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseSong(String openId, String squareId) {
        String releaseSongUrl = "http://101.201.41.109/maemy/api/music/user/" + openId + "/music/" + squareId + "/release";
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = releaseSongUrl;
            requestVo.jsonParser = new CommonRespNotDataParse();
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            NetUtil.get(requestVo);
            // model.addAttribute("mallProListRespVO", respVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSongPlayedTimes(String squareId) {
        String addSongPlayedUrl = "http://101.201.41.109/maemy/api/music/music/" + squareId + "/add/playtimes";
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = addSongPlayedUrl;
            requestVo.requestDataMap = new HashMap<>();
            requestVo.jsonParser = new CommonRespNotDataParse();
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            NetUtil.get(requestVo);
            // model.addAttribute("mallProListRespVO", respVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSongPraisedTimes(String squareId, String openId) {
        logger.info("add-praise-time,squareId=" + squareId + ";openId=" + openId);
        String addSongPraisedUrl = "http://101.201.41.109/maemy/api/praise/push/music/" + squareId + "/user/" + openId;
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = addSongPraisedUrl;
            requestVo.jsonParser = new CommonRespNotDataParse();
            requestVo.requestDataMap = new HashMap<>();
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            NetUtil.get(requestVo);
            // model.addAttribute("mallProListRespVO", respVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SongDetailVO getSongDetails(String squareId) {
        SongDetailVO detailVO = new SongDetailVO();
        SongDetailDataVO respVO = null;
        String getSongDetailUrl = "http://101.201.41.109/maemy/api/music/detail/" + squareId;
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = getSongDetailUrl;
            requestVo.jsonParser = new SongDetailRespParse();
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            respVO = (SongDetailDataVO) NetUtil.post(requestVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (respVO != null) {
            detailVO = respVO.getData();
        }
        return detailVO;
    }

    @Override
    public SongDetailWithPraiseVO getSongDetailsWithPraise(String squareMusicId) {
        SongDetailWithPraiseVO detailWithPraiseVO = new SongDetailWithPraiseVO();
        SongDetailDataVO respVO = null;
        String getSongDetailUrl = "http://101.201.41.109/maemy/api/music/detail/" + squareMusicId;
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = getSongDetailUrl;
            requestVo.jsonParser = new SongDetailRespParse();
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            respVO = (SongDetailDataVO) NetUtil.post(requestVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (respVO != null && respVO.getData() != null) {
            detailWithPraiseVO.setSongDetailVO(respVO.getData());
            SongPraiseDataVO praiseDataVO = null;
            String praiseListUrl = "http://101.201.41.109/maemy/api/praise/list/music/" + respVO.getData().getId();
            RequestVo praiseReqVo = new RequestVo();
            praiseReqVo.requestUrl = praiseListUrl;
            praiseReqVo.jsonParser = new SongPraiseRespParse();
            logger.info("开始请求：requestUrl_get=" + praiseReqVo.requestUrl);
            praiseDataVO = (SongPraiseDataVO) NetUtil.post(praiseReqVo);
            logger.info(praiseDataVO.getData().getPraiseList() + "=========");
            if (praiseDataVO != null) {
                detailWithPraiseVO.setPraiseDetailVOs(praiseDataVO.getData().getPraiseList());
                detailWithPraiseVO.setPraiseNum(praiseDataVO.getData().getPraiseNum());
            }
        }
        return detailWithPraiseVO;
    }

    @Override
    public void addFollowsToWechat(String openId, String deviceId) {
        String addFollowstUrl = "http://101.201.41.109/maemy/api/player/add/" + openId + "/device/" + deviceId;
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = addFollowstUrl;
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            NetUtil.get(requestVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ActivityListDateVO getActivityDataList() {
        ActivityListDateVO activityListDateVO = new ActivityListDateVO();
        ActivityDataVO respVO = null;
        String activityListUrl = "http://101.201.41.109/maemy/api/active/list";
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = activityListUrl;
            requestVo.jsonParser = new ActivityDataRespParse();
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            respVO = (ActivityDataVO) NetUtil.post(requestVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (respVO != null) {
            activityListDateVO = respVO.getData();
        }
        return activityListDateVO;
    }

    @Override
    public void editSongSubmit(String musicId, String coverPicUrl, String board) {
//        String editSongSubmitUrl = "http://101.201.41.109/maemy/api/music/" + musicId + "/update/board/" + board + "/coverUrl/" + coverUrl;
        String editSongSubmitUrl = "http://101.201.41.109/maemy/api/music/music/" + musicId + "/update/board/coverUrl";
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = editSongSubmitUrl;
            requestVo.requestDataMap = new HashMap<>();
            requestVo.requestDataMap.put("board", board);
            requestVo.requestDataMap.put("coverUrl",coverPicUrl);
            requestVo.jsonParser = new CommonRespNotDataParse();
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            NetUtil.post(requestVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void directReleaseSong(String openId, String baseMusicId, String coverPicUrl, String boardStr) {
        // String releaseSongUrl = "http://101.201.41.109/maemy/api/music/user/" + openId + "/music/" + baseMusicId + "/release?board=" + boardStr + "&coverUrl" + coverPicUrl;
//        String releaseSongUrl = "http://101.201.41.109/maemy/api/music/" + baseMusicId + "/update/board/coverUrl";
        String releaseSongUrl = "http://101.201.41.109/maemy/api/music/user/+openId+/music/"+ baseMusicId + "/release";
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = releaseSongUrl;
            requestVo.requestDataMap = new HashMap<>();
            requestVo.requestDataMap.put("board", boardStr);
            requestVo.requestDataMap.put("coverUrl", coverPicUrl);
            requestVo.jsonParser = new CommonRespNotDataParse();
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            NetUtil.post(requestVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<RechargeDetailVO> getMyAccountDetailList(String openId, PageVO pageVO) {
        List<RechargeDetailVO> rechargeDetailVOs = new ArrayList<>();
        RechargeListDataRespVO respVO = null;
        String accountListUrl = "http://101.201.41.109/maemy/api/account//list/user/" + openId + "?offset=" + pageVO.getPageNo();
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = accountListUrl;
            requestVo.jsonParser = new RechargeListDataRespParse();
            requestVo.requestDataMap = new HashMap<>();
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            respVO = (RechargeListDataRespVO) NetUtil.post(requestVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // if (respVO != null) {
        // accountDetailVOs = respVO.getData();
        // }
        if (respVO.getData() != null && respVO.getData().getList() != null && respVO.getData().getList().size() > 0) {
            rechargeDetailVOs = respVO.getData().getList();
            if ("0".equals(respVO.getData().getTail())) {// 不是最后一页
                pageVO.setNextPage(true);//
            }
        }
        return rechargeDetailVOs;
    }

    @Override
    public List<CouponDetailVO> getMyCouponDetailList(String openId, int status) {
        List<CouponDetailVO> couponDetailVOs = new ArrayList<>();
        CouponDetailListDataRespVO respVO = null;
        String couponListUrl = "http://101.201.41.109/maemy/api/coupon/list/user/" + openId + "/status/" + status;
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = couponListUrl;
            requestVo.jsonParser = new CouponListDataRespParse();
            requestVo.requestDataMap = new HashMap<>();
            requestVo.requestDataMap.put("offset", "1");
            requestVo.requestDataMap.put("pageNum", "10");
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            respVO = (CouponDetailListDataRespVO) NetUtil.get(requestVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (respVO != null) {
            couponDetailVOs = respVO.getData();
        }
        return couponDetailVOs;
    }

    @Override
    public List<RechargeCommodityVO> getRechargeCommodityList() {
        List<RechargeCommodityVO> commodityVOs = new ArrayList<>();
        RechargeCommodityListRespVO respVO = null;
        String commodityListUrl = "http://101.201.41.109/maemy/api/product/list/status/2";
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = commodityListUrl;
            requestVo.jsonParser = new RechargeCommodityListDataRespParse();
            requestVo.requestDataMap = new HashMap<>();
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            respVO = (RechargeCommodityListRespVO) NetUtil.post(requestVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (respVO != null) {
            commodityVOs = respVO.getData();
        }
        // if (respVO.getData() != null && respVO.getData().getList() != null && respVO.getData().getList().size() > 0) {
        // rechargeDetailVOs = respVO.getData().getList();
        // if ("0".equals(respVO.getData().getTail())) {// 不是最后一页
        // pageVO.setNextPage(true);//
        // }
        // }
        return commodityVOs;
    }

    @Override
    public AccountInfoVO getMyAccountInfo(String openId) {
        AccountInfoVO accountInfoVO = null;
        AccountInfoDataVO respVO = null;
        String accountInfoUrl = "http://101.201.41.109/maemy/api/player/my/" + openId + "/status/1";
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = accountInfoUrl;
            requestVo.jsonParser = new AccountInfoDataRespParse();
            requestVo.requestDataMap = new HashMap<>();
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            respVO = (AccountInfoDataVO) NetUtil.post(requestVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (respVO != null) {
            accountInfoVO = respVO.getData();
        }
        return accountInfoVO;
    }

    @Override
    public String createPayWxQrcodeImg(String realPath, String openId, String productId, String paytype) {
        String qrcodeImgUrl = null;
        PayLinkInfoDataVO respVO = null;
        String payLinkInfoUrl = "http://101.201.41.109/maemy/api/account/recharge/user/" + openId + "/product/" + productId + "/type/1";// 最后为类型；现只支持微信充值；type/paytype
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = payLinkInfoUrl;
            requestVo.jsonParser = new PayLinkInfoDataParse();
            requestVo.requestDataMap = new HashMap<>();
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
            respVO = (PayLinkInfoDataVO) NetUtil.post(requestVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (respVO != null) {
            PayLinkInfoVO linkInfoVO = respVO.getData();
            String wxPayLink = linkInfoVO.getPay_url();
            String savePath = "/static/payQrcode/";
            String folderYMD = DateUtil.getCurrentDateStr("yyyyMMdd");
            String demoImgDirPath = realPath + savePath + folderYMD;
            try {
                File tempDir = new File(demoImgDirPath);//
                FileUtils.forceMkdir(tempDir); // 新增目录[文件保存路径+日期目录]
            } catch (Exception e2) {
                logger.error("新建文件夹失败：" + e2.getMessage());
            }

            // 1.3：生成产品的qrcode图片
            try {
                String tmpImgFileName = UUID.randomUUID() + ".png";// 二维码图片
                // 统一规范-生成的二维码图片内容
                String encoderContent = wxPayLink;
                TwoDimensionCode handler = new TwoDimensionCode();
                handler.encoderQRCode(encoderContent, demoImgDirPath + File.separator + tmpImgFileName, "png");
                logger.info("生成一张二维码图片至" + tmpImgFileName);
                qrcodeImgUrl = savePath + folderYMD + File.separator + tmpImgFileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
            // qrcodeImgUrl = WaemyWxInterfaceUtil.CURRENT_DOMAIN + qrcodeImgUrl;
        }
        return qrcodeImgUrl;
    }

    @Override
    public CommonRespNotDataVO activiteCoupon(String couponNo, String openId, String houseId) {
        CommonRespNotDataVO respNotDataVO = null;
        String activiteCouponUrl = "http://101.201.41.109/maemy/api/coupon/use/" + couponNo + "/user/" + openId + "/house/" + houseId;
        try {
            RequestVo requestVo = new RequestVo();
            requestVo.requestUrl = activiteCouponUrl;
            requestVo.jsonParser = new CommonRespNotDataParse();
            logger.info("开始请求：requestUrl_get=" + requestVo.requestUrl);
              respNotDataVO = (CommonRespNotDataVO) NetUtil.get(requestVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respNotDataVO;
    }
}
