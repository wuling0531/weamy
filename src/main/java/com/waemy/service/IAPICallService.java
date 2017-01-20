package com.waemy.service;

import java.util.List;

import com.waemy.web.vo.base.PageVO;
import com.waemy.web.vo.response.*;

/**
 * @ClassName: IDataInterfaceService
 * @Description: 外部数据接口的处理类
 * @date 2016年12月2日
 */
public interface IAPICallService {

    List<SquareSongDetailVO> getMusicList(PageVO pageVO);

    List<SquareSongDetailVO> getCurrentUserPublishedList(String openId, PageVO pageVO);

    List<SongNoPublishedDetailVO> getCurrentUserNotPublishedList(String openId, String publichStatus, PageVO pageVO);

    void deleteCurrentSong(String openId, String squareId);

    void releaseSong(String openId, String squareId);

    void addSongPlayedTimes(String squareId);

    void addSongPraisedTimes(String squareId, String openId);

    SongDetailVO getSongDetails(String squareId);

    void addFollowsToWechat(String openId, String deviceId);

    ActivityListDateVO getActivityDataList();

    List<SquareeSongDetailWithPaiseVO> getMusicWithPraiseList(PageVO pageVO);

    SongDetailWithPraiseVO getSongDetailsWithPraise(String squareMusicId);

    List<SquareSongDetailWithPraiseVO> getCurrentUserPublishedWithPraiseList(String openId, PageVO pageVO);

    void editSongSubmit(String musicId, String coverUrl, String board);

    void directReleaseSong(String openId, String baseMusicId, String coverPicUrl, String boardStr);

    List<RechargeDetailVO> getMyAccountDetailList(String openId, PageVO pageVO);

    List<CouponDetailVO> getMyCouponDetailList(String openId, int status);

    List<RechargeCommodityVO> getRechargeCommodityList();

    AccountInfoVO getMyAccountInfo(String openId);

    String createPayWxQrcodeImg(String realPath, String openId, String productId, String paytype);

    CommonRespNotDataVO activiteCoupon(String couponNo, String openId, String houseId);

}
