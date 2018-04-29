package com.richard.beautypic.entity.bean;

/**
 * by Richard on 2017/9/16
 * desc:
 */
public class RestWayBean {
    private int id;
    private String name;
    private int bgId;

    public RestWayBean(int id, String name, int bgId) {
        this.id = id;
        this.name = name;
        this.bgId = bgId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBgId() {
        return bgId;
    }

    public void setBgId(int bgId) {
        this.bgId = bgId;
    }
}
