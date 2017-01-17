package com.waemy.web.vo.response;

import java.util.List;

public class MusicListDataVO {
    
    private String offset;
    
    private String pageNum;
    
    private String tail;// 1当前最后一页 0不是
    
    private List<SquareSongDetailVO> list;
    
    public String getOffset() {
        return offset;
    }
    
    public void setOffset(String offset) {
        this.offset = offset;
    }
    
    public List<SquareSongDetailVO> getList() {
        return list;
    }
    
    public void setList(List<SquareSongDetailVO> list) {
        this.list = list;
    }
    
    public String getTail() {
        return tail;
    }
    
    public void setTail(String tail) {
        this.tail = tail;
    }
    
    public String getPageNum() {
        return pageNum;
    }
    
    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }
    
}
