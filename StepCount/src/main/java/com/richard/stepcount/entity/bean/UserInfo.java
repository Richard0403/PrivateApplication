package com.richard.stepcount.entity.bean;

/**
 * by Richard on 2017/9/8
 * desc:
 */
public class UserInfo {
    /**
     * id : 0
     * name : richae
     * sex : 0
     * header : http://q.qlogo.cn/qqapp/1106309587/431B68A936EE7BDBCB3D18728B8D5E4A/100
     * token : eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyX25hbWUiOiI5ODVkNTA3OTY2ZXciLCJjcmVhdGVfZGF0ZSI6MTUxMzE1NDUxNzk3OCwiZXhwIjoxNTEzNzU5MzE3fQ.-AH-dS-8xFrysg94dxl7oVWNs70frr0Hza14pdL0U20O7-0N-cihavBlml5G82xUBPyFRKh8yRbwIvvaBQ8MDg
     */

    private String id;
    private String name;
    private int sex;
    private String header;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
