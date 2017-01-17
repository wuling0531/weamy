package com.waemy.web.vo.response;

public class SongPraiseDetailVO {
    
    private String create_time;
    
    private String open_id;
    
    private String music_id;
    
    private String portrait_url;
    
    private String praise_id;
    
    public String getMusic_id() {
        return music_id;
    }
    
    public void setMusic_id(String music_id) {
        this.music_id = music_id;
    }
    
    public String getCreate_time() {
        return create_time;
    }
    
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    
    public String getOpen_id() {
        return open_id;
    }
    
    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }
    
    public String getPortrait_url() {
        return portrait_url;
    }
    
    public void setPortrait_url(String portrait_url) {
        this.portrait_url = portrait_url;
    }
    
    public String getPraise_id() {
        return praise_id;
    }
    
    public void setPraise_id(String praise_id) {
        this.praise_id = praise_id;
    }
    
}
