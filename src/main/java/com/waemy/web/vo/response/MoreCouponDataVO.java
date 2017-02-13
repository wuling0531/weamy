package com.waemy.web.vo.response;

import java.util.List;

/**
 * Created by Zjt_WuLing on 2017/2/13.
 */
public class MoreCouponDataVO {
    private int currentPageNo;

    private boolean isNext = false;

    public List<CouponDetailVO> couponDetailVOs;

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public boolean isNext() {
        return isNext;
    }

    public void setNext(boolean next) {
        isNext = next;
    }

    public List<CouponDetailVO> getCouponDetailVOs() {
        return couponDetailVOs;
    }

    public void setCouponDetailVOs(List<CouponDetailVO> couponDetailVOs) {
        this.couponDetailVOs = couponDetailVOs;
    }
}
