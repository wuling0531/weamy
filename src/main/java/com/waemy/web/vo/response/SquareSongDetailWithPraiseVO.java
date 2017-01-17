package com.waemy.web.vo.response;

import java.util.List;

public class SquareSongDetailWithPraiseVO {
    
    private SquareSongDetailVO songDetailVO;
    
    private List<SongPraiseDetailVO> praiseDetailVOs;
    
    private String praiseNum;//
    
    public SquareSongDetailVO getSongDetailVO() {
        return songDetailVO;
    }
    
    public void setSongDetailVO(SquareSongDetailVO songDetailVO) {
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
