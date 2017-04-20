package com.waemy.web.vo.response;

import java.util.List;

/**
 * Created by Zjt_WuLing on 2017/2/14.
 */
public class MoreSongUnPublishedVO {

    private List<SongNoPublishedDetailVO> detailWithPaiseVOs;

    private int currentPageNo;

    private boolean isNext = false;

    public List<SongNoPublishedDetailVO> getDetailWithPaiseVOs() {
        return detailWithPaiseVOs;
    }

    public void setDetailWithPaiseVOs(List<SongNoPublishedDetailVO> detailWithPaiseVOs) {
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
