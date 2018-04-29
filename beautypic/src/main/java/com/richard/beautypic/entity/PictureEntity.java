package com.richard.beautypic.entity;

import java.io.Serializable;
import java.util.List;

/**
 * by Richard on 2017/9/17
 * desc:
 */
public class PictureEntity {

    /**
     * code : 0
     * msg : success
     * data : {"pageCount":62,"pictures":[{"id":41,"url":"http://oow561q5i.bkt.clouddn.com/04aa0e33258ca31719579b1afa1f776773254fb1.jpg","name":"04aa0e33258ca31719579b1afa1f776773254fb1"},{"id":42,"url":"http://oow561q5i.bkt.clouddn.com/04af1f9bc0441ba4ca84484b540d120552fdc996.jpg","name":"04af1f9bc0441ba4ca84484b540d120552fdc996"}]}
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
         * pageCount : 62
         * pictures : [{"id":41,"url":"http://oow561q5i.bkt.clouddn.com/04aa0e33258ca31719579b1afa1f776773254fb1.jpg","name":"04aa0e33258ca31719579b1afa1f776773254fb1"},{"id":42,"url":"http://oow561q5i.bkt.clouddn.com/04af1f9bc0441ba4ca84484b540d120552fdc996.jpg","name":"04af1f9bc0441ba4ca84484b540d120552fdc996"}]
         */

        private int pageCount;
        private List<PicturesBean> pictures;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<PicturesBean> getPictures() {
            return pictures;
        }

        public void setPictures(List<PicturesBean> pictures) {
            this.pictures = pictures;
        }

        public static class PicturesBean implements Serializable{
            /**
             * id : 41
             * url : http://oow561q5i.bkt.clouddn.com/04aa0e33258ca31719579b1afa1f776773254fb1.jpg
             * name : 04aa0e33258ca31719579b1afa1f776773254fb1
             */

            private String id;
            private String url;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
