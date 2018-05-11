package com.richard.diary.http.entity.diary;

import com.richard.diary.http.entity.BaseEntity;
import com.richard.diary.http.entity.user.UserInfo;

import java.io.Serializable;
import java.util.List;

/**
 * By Richard on 2018/4/27.
 */

public class DiaryTagEntity extends BaseEntity {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * name : 测验
         * id : 1
         * description : 测试
         * status : 1
         * diaryCount : 2
         * user : {"id":3,"name":"Richard0x1.62e7c2p40","age":null,"sex":1,"header":"http://oow561q5i.bkt.clouddn.com/13ce4e0fa45a4cbb98ac9a6a3815b1a4.jpg"}
         * updateTime : 1524724990000
         * createTime : 1524724980000
         * picture : null
         */

        private String name;
        private int id;
        private String description;
        private int status;
        private int diaryCount;
        private UserInfo.UserBean user;
        private long updateTime;
        private long createTime;
        private String picture;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getDiaryCount() {
            return diaryCount;
        }

        public void setDiaryCount(int diaryCount) {
            this.diaryCount = diaryCount;
        }

        public UserInfo.UserBean getUser() {
            return user;
        }

        public void setUser(UserInfo.UserBean user) {
            this.user = user;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }
}
