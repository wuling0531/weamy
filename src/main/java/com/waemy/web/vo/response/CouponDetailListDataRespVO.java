package com.waemy.web.vo.response;

import java.util.List;

public class CouponDetailListDataRespVO {
    
    private List<CouponDetailVO> data;
    
    private String extram;
    
    private String msg;
    
    private String resCode;
    
    private String resStatus;
    
    public List<CouponDetailVO> getData() {
        return data;
    }
    
    public void setData(List<CouponDetailVO> data) {
        this.data = data;
    }
    
    public String getExtram() {
        return extram;
    }
    
    public void setExtram(String extram) {
        this.extram = extram;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String getResCode() {
        return resCode;
    }
    
    public void setResCode(String resCode) {
        this.resCode = resCode;
    }
    
    public String getResStatus() {
        return resStatus;
    }
    
    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }
    
}
