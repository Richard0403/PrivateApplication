package com.richard.stepcount.entity.home;

import com.richard.stepcount.entity.bean.UserInfo;

/**
 * by Richard on 2017/9/8
 * desc:
 */
public class UserEntity {


    /**
     * code : 0
     * msg : 欢迎归来
     * data : {"user":{"id":25,"name":"richae","sex":0,"header":"http://q.qlogo.cn/qqapp/1106309587/C9115029DB5CAFC47E5D2045A1425607/100"},"token":"eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyX25hbWUiOiI5ODVkNTA3OTY2ZXciLCJjcmVhdGVfZGF0ZSI6MTUxNDI3Mjc3ODIzMSwiZXhwIjoxNTE0ODc3NTc4fQ.GJ2rcpULPsoSyId30ZyPySGP5C9t03KJAvSKE-ZUIrQ4EiEJQoChcwV8nDgwvIK34-MhjwiKMrXNDkbHYOUsjw"}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user : {"id":25,"name":"richae","sex":0,"header":"http://q.qlogo.cn/qqapp/1106309587/C9115029DB5CAFC47E5D2045A1425607/100"}
         * token : eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyX25hbWUiOiI5ODVkNTA3OTY2ZXciLCJjcmVhdGVfZGF0ZSI6MTUxNDI3Mjc3ODIzMSwiZXhwIjoxNTE0ODc3NTc4fQ.GJ2rcpULPsoSyId30ZyPySGP5C9t03KJAvSKE-ZUIrQ4EiEJQoChcwV8nDgwvIK34-MhjwiKMrXNDkbHYOUsjw
         */

        private UserInfo user;
        private String token;

        public UserInfo getUser() {
            return user;
        }

        public void setUser(UserInfo user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }


    }
}
