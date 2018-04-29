package com.richard.beautypic.entity;

/**
 * by Richard on 2017/9/8
 * desc:
 */
public class UserEntity {


    /**
     * code : 0
     * msg : success
     * data : {"id":5,"name":"Richard1504839092538","qqOpenId":"431B68A936EE7BDBCB3D18728B8D5E4A","token":"580849b0e9af4a389a88959b6362ae7d","sex":0,"header":"http://q.qlogo.cn/qqapp/1106309587/431B68A936EE7BDBCB3D18728B8D5E4A/100","articles":[]}
     */

    private int code;
    private String msg;
    private UserInfo data;

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

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

}
