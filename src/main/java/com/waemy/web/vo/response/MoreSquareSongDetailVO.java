package com.waemy.web.vo.response;

import java.util.List;

public class MoreSquareSongDetailVO {
    
    private List<SquareeSongDetailWithPaiseVO> detailWithPaiseVOs;

    private int currentPageNo;

    private boolean isNext = false;
    
    public List<SquareeSongDetailWithPaiseVO> getDetailWithPaiseVOs() {
        return detailWithPaiseVOs;
    }
    
    public void setDetailWithPaiseVOs(List<SquareeSongDetailWithPaiseVO> detailWithPaiseVOs) {
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
    
    public void setNext(boolean isNext) {
        this.isNext = isNext;
    }
    
}
