package com.waemy.web.vo.response;

import java.util.List;

public class SongPraiseListDataVO {
    
    private List<SongPraiseDetailVO> praiseList;
    
    private String praiseNum;
    
    public List<SongPraiseDetailVO> getPraiseList() {
        return praiseList;
    }
    
    public void setPraiseList(List<SongPraiseDetailVO> praiseList) {
        this.praiseList = praiseList;
    }
    
    public String getPraiseNum() {
        return praiseNum;
    }
    
    public void setPraiseNum(String praiseNum) {
        this.praiseNum = praiseNum;
    }
    
}
