package com.waemy.web.vo.response;

import java.util.List;

/**
 * Created by Zjt_WuLing on 2017/2/13.
 */
public class MoreRechargeDetailVOs {
    private int currentPageNo;

    private boolean isNext = false;

    public List<RechargeDetailVO> rechargeDetailVOs;

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

    public List<RechargeDetailVO> getRechargeDetailVOs() {
        return rechargeDetailVOs;
    }

    public void setRechargeDetailVOs(List<RechargeDetailVO> rechargeDetailVOs) {
        this.rechargeDetailVOs = rechargeDetailVOs;
    }
}
