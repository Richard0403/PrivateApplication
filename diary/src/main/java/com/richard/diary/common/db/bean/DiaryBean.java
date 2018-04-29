package com.richard.diary.common.db.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * By Richard on 2018/3/9.
 */
@DatabaseTable
public class DiaryBean {
    @DatabaseField(id = true)
    public int id;
    @DatabaseField
    public String userId;
    @DatabaseField
    public String title;
    @DatabaseField
    public String content;
    @DatabaseField()
    public long createTime;
    @DatabaseField
    public long updateTime;


    @Override
    public String toString() {
        return "DiaryBean{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
