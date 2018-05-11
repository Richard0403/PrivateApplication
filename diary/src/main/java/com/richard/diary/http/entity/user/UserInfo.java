package com.richard.diary.http.entity.user;

import java.io.Serializable;

/**
 * by Richard on 2017/9/8
 * desc:
 */
public class UserInfo {
    /**
     * user : {"id":1,"name":"richae0x1.62e798p40","sex":0,"header":"http://oow561q5i.bkt.clouddn.com/703de9f4b2264a16b9710402308f5849.jpg"}
     * token : eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyX25hbWUiOiI5ODVkNTA3OTY2ZXciLCJjcmVhdGVfZGF0ZSI6MTUyNDMwNDQyNzc4MSwiZXhwIjoxNTI0OTA5MjI3fQ.9vET5TbfQC9NTjHOKdB-rF3OlK07455koD3XBOuN6s9Ulb_sqh-aM3LS9nZR1bf4o-Z8ZOr8KFE4G3t11oSRJg
     */

    private UserBean user;
    private String token;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserBean implements Serializable{
        /**
         * id : 1
         * name : richae0x1.62e798p40
         * sex : 0
         * header : http://oow561q5i.bkt.clouddn.com/703de9f4b2264a16b9710402308f5849.jpg
         */

        private long id;
        private String name;
        private int sex;
        private String header;
        private int age;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
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
    }
}
