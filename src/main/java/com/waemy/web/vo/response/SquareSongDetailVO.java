package com.waemy.web.vo.response;

/**
 * @ClassName: SquareSongDetailVO
 * @Description: 广场页面的歌曲详情
 * @date 2016年12月2日
 */
public class SquareSongDetailVO {
    
    private String sautus; // 状态：0未审核 1审核
    
    private String cover_url; // 封面URL
    
    private String create_time; // 发布时间
    
    private String open_id; // 用户openId
    
    private String user_name; // 用户名
    
    private String square_id; // 主键ID
    
    private String music_id;
    
    private String music_name; // 音乐名称
    
    private String portrait_url; // 用户头像封面
    
    private String boardLen; // 心情文字长度
    
    private String board; // 心情文字
    
    private String play_times; // 播放次数
    
    private String relative; // 相对时间 1分钟前 1天前等
    
    public String getSautus() {
        return sautus;
    }
    
    public void setSautus(String sautus) {
        this.sautus = sautus;
    }
    
    public String getCover_url() {
        return cover_url;
    }
    
    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
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
    
    public String getUser_name() {
        return user_name;
    }
    
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    
    public String getSquare_id() {
        return square_id;
    }
    
    public void setSquare_id(String square_id) {
        this.square_id = square_id;
    }
    
    public String getMusic_id() {
        return music_id;
    }
    
    public void setMusic_id(String music_id) {
        this.music_id = music_id;
    }
    
    public String getMusic_name() {
        return music_name;
    }
    
    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }
    
    public String getPortrait_url() {
        return portrait_url;
    }
    
    public void setPortrait_url(String portrait_url) {
        this.portrait_url = portrait_url;
    }
    
    public String getBoardLen() {
        return boardLen;
    }
    
    public void setBoardLen(String boardLen) {
        this.boardLen = boardLen;
    }
    
    public String getBoard() {
        return board;
    }
    
    public void setBoard(String board) {
        this.board = board;
    }
    
    public String getPlay_times() {
        return play_times;
    }
    
    public void setPlay_times(String play_times) {
        this.play_times = play_times;
    }
    
    public String getRelative() {
        return relative;
    }
    
    public void setRelative(String relative) {
        this.relative = relative;
    }
    
}
