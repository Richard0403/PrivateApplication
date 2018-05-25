package com.richard.diary.http.entity.diary;

import com.richard.diary.http.entity.BaseEntity;
import com.richard.diary.http.entity.user.UserInfo;

/**
 * By Richard on 2018/4/27.
 */

public class DiaryDetailEntity extends BaseEntity {

    /**
     * data : {"id":3,"title":"滚滚滚不会","content":"GG侧耳","user":{"id":3,"name":"Richard0x1.62e7c2p40","age":null,"sex":1,"header":"http://oow561q5i.bkt.clouddn.com/13ce4e0fa45a4cbb98ac9a6a3815b1a4.jpg"},"picture":"http://oow561q5i.bkt.clouddn.com/random/47142ca871144857c2eee5e5f3b477b3.jpg","updateTime":1526356191000,"createTime":1526356191000,"status":0,"publicStatus":0,"diaryTag":{"id":1,"user":{"id":3,"name":"Richard0x1.62e7c2p40","age":null,"sex":1,"header":"http://oow561q5i.bkt.clouddn.com/13ce4e0fa45a4cbb98ac9a6a3815b1a4.jpg"},"name":"模具工","description":"姐就可口可乐了","picture":"http://oow561q5i.bkt.clouddn.com/5ebebcb7d38243c38ba0530ee4068f11.jpg","status":0,"updateTime":1526355019000,"createTime":1526355019000},"readNum":0,"shareNum":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * title : 滚滚滚不会
         * content : GG侧耳
         * user : {"id":3,"name":"Richard0x1.62e7c2p40","age":null,"sex":1,"header":"http://oow561q5i.bkt.clouddn.com/13ce4e0fa45a4cbb98ac9a6a3815b1a4.jpg"}
         * picture : http://oow561q5i.bkt.clouddn.com/random/47142ca871144857c2eee5e5f3b477b3.jpg
         * updateTime : 1526356191000
         * createTime : 1526356191000
         * status : 0
         * publicStatus : 0
         * diaryTag : {"id":1,"user":{"id":3,"name":"Richard0x1.62e7c2p40","age":null,"sex":1,"header":"http://oow561q5i.bkt.clouddn.com/13ce4e0fa45a4cbb98ac9a6a3815b1a4.jpg"},"name":"模具工","description":"姐就可口可乐了","picture":"http://oow561q5i.bkt.clouddn.com/5ebebcb7d38243c38ba0530ee4068f11.jpg","status":0,"updateTime":1526355019000,"createTime":1526355019000}
         * readNum : 0
         * shareNum : 0
         */

        private long id;
        private String title;
        private String content;
        private UserInfo.UserBean user;
        private String picture;
        private long updateTime;
        private long createTime;
        private int status;
        private int publicStatus;
        private DiaryTagBean diaryTag;
        private int readNum;
        private int shareNum;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public UserInfo.UserBean getUser() {
            return user;
        }

        public void setUser(UserInfo.UserBean user) {
            this.user = user;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPublicStatus() {
            return publicStatus;
        }

        public void setPublicStatus(int publicStatus) {
            this.publicStatus = publicStatus;
        }

        public DiaryTagBean getDiaryTag() {
            return diaryTag;
        }

        public void setDiaryTag(DiaryTagBean diaryTag) {
            this.diaryTag = diaryTag;
        }

        public int getReadNum() {
            return readNum;
        }

        public void setReadNum(int readNum) {
            this.readNum = readNum;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }


        public static class DiaryTagBean {
            /**
             * id : 1
             * user : {"id":3,"name":"Richard0x1.62e7c2p40","age":null,"sex":1,"header":"http://oow561q5i.bkt.clouddn.com/13ce4e0fa45a4cbb98ac9a6a3815b1a4.jpg"}
             * name : 模具工
             * description : 姐就可口可乐了
             * picture : http://oow561q5i.bkt.clouddn.com/5ebebcb7d38243c38ba0530ee4068f11.jpg
             * status : 0
             * updateTime : 1526355019000
             * createTime : 1526355019000
             */

            private long id;
            private UserInfo.UserBean user;
            private String name;
            private String description;
            private String picture;
            private int status;
            private long updateTime;
            private long createTime;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public UserInfo.UserBean getUser() {
                return user;
            }

            public void setUser(UserInfo.UserBean user) {
                this.user = user;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

        }
    }
}
