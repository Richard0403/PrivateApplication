package com.richard.stepcount.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class BaseEntity implements Serializable {
    /**
     * {
     * "code": 0,
     * "msg": "用户动态删除成功!"
     * }
     */
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
