package com.richard.stepcount.entity.find;

import java.util.List;

/**
 * By Richard on 2017/12/26.
 */

public class ArticleListEntity {

    /**
     * code : 0
     * msg : success
     * data : {"pageCount":1,"article":[{"id":3,"title":"test title","content":"testContent","user":{"id":25,"name":"richae","sex":0,"header":"http://q.qlogo.cn/qqapp/1106309587/C9115029DB5CAFC47E5D2045A1425607/100"},"updateTime":1514189955000,"praiseUsers":[],"status":0},{"id":2,"title":"test title","content":"testContent","user":{"id":25,"name":"richae","sex":0,"header":"http://q.qlogo.cn/qqapp/1106309587/C9115029DB5CAFC47E5D2045A1425607/100"},"updateTime":1513222049000,"praiseUsers":[],"status":0},{"id":1,"title":"test title","content":"testContent","user":{"id":25,"name":"richae","sex":0,"header":"http://q.qlogo.cn/qqapp/1106309587/C9115029DB5CAFC47E5D2045A1425607/100"},"updateTime":1513220408000,"praiseUsers":[{"id":25,"name":"richae","sex":0,"header":"http://q.qlogo.cn/qqapp/1106309587/C9115029DB5CAFC47E5D2045A1425607/100"}],"status":0}]}
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
         * pageCount : 1
         * article : [{"id":3,"title":"test title","content":"testContent","user":{"id":25,"name":"richae","sex":0,"header":"http://q.qlogo.cn/qqapp/1106309587/C9115029DB5CAFC47E5D2045A1425607/100"},"updateTime":1514189955000,"praiseUsers":[],"status":0},{"id":2,"title":"test title","content":"testContent","user":{"id":25,"name":"richae","sex":0,"header":"http://q.qlogo.cn/qqapp/1106309587/C9115029DB5CAFC47E5D2045A1425607/100"},"updateTime":1513222049000,"praiseUsers":[],"status":0},{"id":1,"title":"test title","content":"testContent","user":{"id":25,"name":"richae","sex":0,"header":"http://q.qlogo.cn/qqapp/1106309587/C9115029DB5CAFC47E5D2045A1425607/100"},"updateTime":1513220408000,"praiseUsers":[{"id":25,"name":"richae","sex":0,"header":"http://q.qlogo.cn/qqapp/1106309587/C9115029DB5CAFC47E5D2045A1425607/100"}],"status":0}]
         */

        private int pageCount;
        private List<ArticleBean> article;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<ArticleBean> getArticle() {
            return article;
        }

        public void setArticle(List<ArticleBean> article) {
            this.article = article;
        }

        public static class ArticleBean {
            /**
             * id : 3
             * title : test title
             * content : testContent
             * user : {"id":25,"name":"richae","sex":0,"header":"http://q.qlogo.cn/qqapp/1106309587/C9115029DB5CAFC47E5D2045A1425607/100"}
             * updateTime : 1514189955000
             * praiseUsers : []
             * status : 0
             */

            private String id;
            private String title;
            private String content;
            private UserBean user;
            private long updateTime;
            private int status;
            private List<UserBean> praiseUsers;

            public String getId() {
                return id;
            }

            public void setId(String id) {
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

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<UserBean> getPraiseUsers() {
                return praiseUsers;
            }

            public void setPraiseUsers(List<UserBean> praiseUsers) {
                this.praiseUsers = praiseUsers;
            }

            public static class UserBean {
                /**
                 * id : 25
                 * name : richae
                 * sex : 0
                 * header : http://q.qlogo.cn/qqapp/1106309587/C9115029DB5CAFC47E5D2045A1425607/100
                 */

                private String id;
                private String name;
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
    }
}
