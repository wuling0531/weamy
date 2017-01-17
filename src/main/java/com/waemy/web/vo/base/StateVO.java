package com.waemy.web.vo.base;

/**
 * 状态VO
 * @author xueyanbeijing Date: 11-10-5 Time: 下午5:20
 */
public class StateVO {
    
    private int code = 0; // 返回状态码 , 默认: 0-成功;1-有版本升级;2-注册成功
    
    private String msg = "操作成功"; // 返回提示消息 , 默认: OK/HasNewVersion/RegSuccess
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    @Override
    public String toString() {
        return "StateVO{" + "code=" + code + ", msg='" + msg + '\'' + '}';
    }
}
