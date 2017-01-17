package com.waemy.web.vo.response;

import java.util.List;

public class ActivityListDateVO {
    
    private List<ActivityArticleVO> articleMap;
    
    private List<ActivityBaseMusicInfoVO> baseMusicInfoMap;
    
    public List<ActivityArticleVO> getArticleMap() {
        return articleMap;
    }
    
    public void setArticleMap(List<ActivityArticleVO> articleMap) {
        this.articleMap = articleMap;
    }
    
    public List<ActivityBaseMusicInfoVO> getBaseMusicInfoMap() {
        return baseMusicInfoMap;
    }
    
    public void setBaseMusicInfoMap(List<ActivityBaseMusicInfoVO> baseMusicInfoMap) {
        this.baseMusicInfoMap = baseMusicInfoMap;
    }
    
}
