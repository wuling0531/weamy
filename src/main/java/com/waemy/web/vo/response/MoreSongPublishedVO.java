package com.waemy.web.vo.response;

import java.util.List;

/**
 * Created by Zjt_WuLing on 2017/2/14.
 */
public class MoreSongPublishedVO {

    private List<SquareSongDetailWithPraiseVO> detailWithPaiseVOs;

    private int currentPageNo;

    private boolean isNext = false;

    public List<SquareSongDetailWithPraiseVO> getDetailWithPaiseVOs() {
        return detailWithPaiseVOs;
    }

    public void setDetailWithPaiseVOs(List<SquareSongDetailWithPraiseVO> detailWithPaiseVOs) {
        this.detailWithPaiseVOs = detailWithPaiseVOs;
    }

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
}
