package com.waemy.web.vo.base;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Administrator on 2015/12/29.
 */
public class WeixinBaseSessionVO {
    
    private Long factoryId = 0L;
    
    private Long productId = 0L;
    
    private String codeValue = "";
    
    private Long refActivityId = 0L;
    
    private Long refPartConditionId = 0L;
    
    private Long refPrzeItemId = 0L;
    
    private String openId = "";
    
    private String mdn = "";
    
    public Long getFactoryId() {
        return factoryId;
    }
    
    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public String getCodeValue() {
        return codeValue;
    }
    
    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
    
    public Long getRefActivityId() {
        return refActivityId;
    }
    
    public void setRefActivityId(Long refActivityId) {
        this.refActivityId = refActivityId;
    }
    
    public Long getRefPartConditionId() {
        return refPartConditionId;
    }
    
    public void setRefPartConditionId(Long refPartConditionId) {
        this.refPartConditionId = refPartConditionId;
    }
    
    public Long getRefPrzeItemId() {
        return refPrzeItemId;
    }
    
    public void setRefPrzeItemId(Long refPrzeItemId) {
        this.refPrzeItemId = refPrzeItemId;
    }
    
    public String getOpenId() {
        return openId;
    }
    
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    
    public String getMdn() {
        return mdn;
    }
    
    public void setMdn(String mdn) {
        this.mdn = mdn;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
