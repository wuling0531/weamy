package com.waemy.web.vo.response;

import java.util.List;

public class SongDetailWithPraiseVO {
    
    private SongDetailVO songDetailVO;
    
    private List<SongPraiseDetailVO> praiseDetailVOs;
    
    private String praiseNum;//
    
    public SongDetailVO getSongDetailVO() {
        return songDetailVO;
    }
    
    public void setSongDetailVO(SongDetailVO songDetailVO) {
        this.songDetailVO = songDetailVO;
    }
    
    public List<SongPraiseDetailVO> getPraiseDetailVOs() {
        return praiseDetailVOs;
    }
    
    public void setPraiseDetailVOs(List<SongPraiseDetailVO> praiseDetailVOs) {
        this.praiseDetailVOs = praiseDetailVOs;
    }
    
    public String getPraiseNum() {
        return praiseNum;
    }
    
    public void setPraiseNum(String praiseNum) {
        this.praiseNum = praiseNum;
    }
    
}
