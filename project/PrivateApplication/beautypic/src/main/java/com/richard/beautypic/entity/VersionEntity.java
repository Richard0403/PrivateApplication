package com.richard.beautypic.entity;

/**
 * Created by Administrator on 2017/3/25.
 */

public class VersionEntity {


    /**
     * code : 0
     * msg : success
     * data : {"id":1,"size":3.5,"updateContent":"更新点:{n}1.增加了小说{n}2.增加了加密功能","url":"http://oow561q5i.bkt.clouddn.com/beautypic-release.apk","versionCode":2,"versionName":"1.0.0","force":true}
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
         * id : 1
         * size : 3.5
         * updateContent : 更新点:{n}1.增加了小说{n}2.增加了加密功能
         * url : http://oow561q5i.bkt.clouddn.com/beautypic-release.apk
         * versionCode : 2
         * versionName : 1.0.0
         * force : true
         */

        private String id;
        private String size;
        private String updateContent;
        private String url;
        private int versionCode;
        private String versionName;
        private boolean force;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getUpdateContent() {
            return updateContent;
        }

        public void setUpdateContent(String updateContent) {
            this.updateContent = updateContent;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public boolean isForce() {
            return force;
        }

        public void setForce(boolean force) {
            this.force = force;
        }
    }
}
