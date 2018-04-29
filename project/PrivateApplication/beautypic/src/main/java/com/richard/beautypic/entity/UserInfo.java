package com.richard.beautypic.entity;

/**
 * by Richard on 2017/9/8
 * desc:
 */
public class UserInfo {
    /**
     * id : 5
     * name : Richard1504839092538
     * qqOpenId : 431B68A936EE7BDBCB3D18728B8D5E4A
     * token : 580849b0e9af4a389a88959b6362ae7d
     * sex : 0
     * header : http://q.qlogo.cn/qqapp/1106309587/431B68A936EE7BDBCB3D18728B8D5E4A/100
     * articles : []
     */


    private String id;
    private String name;
    private String qqOpenId;
    private String token;
    private int sex;
    private String header;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQqOpenId() {
        return qqOpenId;
    }

    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
