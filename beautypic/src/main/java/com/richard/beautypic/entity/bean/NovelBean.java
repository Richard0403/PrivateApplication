package com.richard.beautypic.entity.bean;

/**
 * by Richard on 2017/9/24
 * desc:
 */
public class NovelBean {
    /**
     * id : 9
     * title : <font color="#000">偷香作者ansonlo</font>
     * time : 0
     */

    private String id;
    private String title;
    private String content;
    private long time;

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
