package com.waemy.web.vo.response;

public class AccountInfoVO {
    
    private long player_id;
    
    private String open_id;
    
    private String nickname;
    
    private String headimgurl;
    
    private String count;
    
    private String sum;
    
    public long getPlayer_id() {
        return player_id;
    }
    
    public void setPlayer_id(long player_id) {
        this.player_id = player_id;
    }
    
    public String getOpen_id() {
        return open_id;
    }
    
    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getHeadimgurl() {
        return headimgurl;
    }
    
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
    
    public String getCount() {
        return count;
    }
    
    public void setCount(String count) {
        this.count = count;
    }
    
    public String getSum() {
        return sum;
    }
    
    public void setSum(String sum) {
        this.sum = sum;
    }
    
}
