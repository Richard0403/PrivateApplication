package com.richard.beautypic.entity;

import com.richard.beautypic.entity.bean.NovelBean;

import java.util.List;

/**
 * by Richard on 2017/9/24
 * desc:
 */
public class NovelListEntity {


    /**
     * code : 0
     * msg : success
     * data : {"pageCount":170,"novel":[{"id":9,"title":"<font color=\"#000\">偷香作者ansonlo<\/font>","time":0},{"id":10,"title":"<font color=\"#000\">我的家教经历<\/font>","time":0},{"id":11,"title":"<font color=\"#000\">学苑中的花<\/font>","time":0},{"id":12,"title":"<font color=\"#000\">嫖了班主任<\/font>","time":0},{"id":13,"title":"<font color=\"#000\">狂欢<\/font>","time":0},{"id":14,"title":"<font color=\"#000\">【我被干爹给肏了】【完】<\/font>","time":0},{"id":15,"title":"<font color=\"#000\">【我的高中生活】（２1）<\/font>","time":0},{"id":16,"title":"<font color=\"#000\">【两点之间】（41<\/font>","time":0}]}
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
         * pageCount : 170
         * novel : [{"id":9,"title":"<font color=\"#000\">偷香作者ansonlo<\/font>","time":0},{"id":10,"title":"<font color=\"#000\">我的家教经历<\/font>","time":0},{"id":11,"title":"<font color=\"#000\">学苑中的花<\/font>","time":0},{"id":12,"title":"<font color=\"#000\">嫖了班主任<\/font>","time":0},{"id":13,"title":"<font color=\"#000\">狂欢<\/font>","time":0},{"id":14,"title":"<font color=\"#000\">【我被干爹给肏了】【完】<\/font>","time":0},{"id":15,"title":"<font color=\"#000\">【我的高中生活】（２1）<\/font>","time":0},{"id":16,"title":"<font color=\"#000\">【两点之间】（41<\/font>","time":0}]
         */

        private int pageCount;
        private List<NovelBean> novel;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<NovelBean> getNovel() {
            return novel;
        }

        public void setNovel(List<NovelBean> novel) {
            this.novel = novel;
        }


    }
}
