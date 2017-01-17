package com.waemy.web.vo.response;

import java.util.List;

public class RechargeListDataVO {
    
    private String offset;
    
    private String pageNum;
    
    private String tail;// 1当前最后一页 0不是
    
    private List<RechargeDetailVO> list;
    
    public List<RechargeDetailVO> getList() {
        return list;
    }
    
    public void setList(List<RechargeDetailVO> list) {
        this.list = list;
    }
    
    public String getOffset() {
        return offset;
    }
    
    public void setOffset(String offset) {
        this.offset = offset;
    }
    
    public String getPageNum() {
        return pageNum;
    }
    
    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }
    
    public String getTail() {
        return tail;
    }
    
    public void setTail(String tail) {
        this.tail = tail;
    }
    
}
