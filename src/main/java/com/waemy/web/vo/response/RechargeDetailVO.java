package com.waemy.web.vo.response;

import java.util.Date;

public class RechargeDetailVO {
    
    private String account_id;
    
    private Date create_time;
    
    private int money;
    
    private String open_id;
    
    private String third_no;
    
    private int pay_type;
    
    private int type;
    
    private int status;
    
    public String getAccount_id() {
        return account_id;
    }
    
    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }
    
    public Date getCreate_time() {
        return create_time;
    }
    
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
    
    public int getMoney() {
        return money;
    }
    
    public void setMoney(int money) {
        this.money = money;
    }
    
    public String getOpen_id() {
        return open_id;
    }
    
    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }
    
    public String getThird_no() {
        return third_no;
    }
    
    public void setThird_no(String third_no) {
        this.third_no = third_no;
    }
    
    public int getPay_type() {
        return pay_type;
    }
    
    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
}
