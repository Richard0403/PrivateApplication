package com.richard.diary.http.entity.diary;

import com.richard.diary.http.entity.BaseEntity;

import java.util.List;

/**
 * By Richard on 2018/4/27.
 */

public class UploadEntity extends BaseEntity {


    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
