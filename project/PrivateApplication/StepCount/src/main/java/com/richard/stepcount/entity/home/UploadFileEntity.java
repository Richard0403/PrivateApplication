package com.richard.stepcount.entity.home;

import java.util.List;

/**
 * by Richard on 2017/9/8
 * desc:
 */
public class UploadFileEntity {


    /**
     * code : 0
     * msg : Success
     * data : ["http://oow561q5i.bkt.clouddn.com/2711f7ced4344278acf5a85c15e8b6f2.jpg"]
     */

    private int code;
    private String msg;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
