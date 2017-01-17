package com.waemy.web.vo.response;

/**
 * @ClassName: RechargeCommodityVO
 * @Description: 充值商品
 */
public class RechargeCommodityVO {
    
    private String price;
    
    private String product_id;
    
    private String product_name;
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getProduct_id() {
        return product_id;
    }
    
    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
    
    public String getProduct_name() {
        return product_name;
    }
    
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    
}
