package com.dingshuwang.event;

/**
 * @author tx
 * @date 2018/6/15
 */
public class IsbnCodeEvent {
    private String code;

    public IsbnCodeEvent(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
