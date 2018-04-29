package com.richard.diary.common.db.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.richard.diary.common.db.DBHelper;
import com.richard.diary.common.db.bean.DiaryBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * By Richard on 2018/3/9.
 */

public class DiaryDao {
    private DBHelper dbHelper;
    private Dao<DiaryBean, Integer> diaryDao;

    /**
     * 构造方法
     * 获得数据库帮助类实例，通过传入Class对象得到相应的Dao
     * @param context
     */
    public DiaryDao(Context context) {
        try {
            dbHelper = DBHelper.getHelper(context);
            diaryDao = dbHelper.getDao(DiaryBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一条记录
     * @param diaryBean
     */
    public void add(DiaryBean diaryBean) {
        try {
            diaryDao.create(diaryBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一条记录
     * @param diaryBean
     */
    public void delete(DiaryBean diaryBean) {
        try {
            diaryDao.delete(diaryBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新一条记录
     * @param theme
     */
    public void update(DiaryBean theme) {
        try {
            diaryDao.update(theme);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询一条记录
     * @param id
     * @return
     */
    public DiaryBean queryForId(int id) {
        DiaryBean diaryBean = null;
        try {
            diaryBean = diaryDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diaryBean;
    }


    /**
     * 查询所有记录
     * @return
     */
    public List<DiaryBean> queryForAll() {
        List<DiaryBean> themes = new ArrayList<DiaryBean>();
        try {
            themes = diaryDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }

}
