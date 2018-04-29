package com.richard.stepcount.db;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.List;

/**
 * Created by xf on 2016/1/31.
 */
public class DbUtils {

    private static LiteOrm liteOrm;
    private static DbUtils instance;

//    public static void createDb(Context _activity, String DB_NAME) {
//        DB_NAME = DB_NAME + ".db";
//        if (liteOrm == null) {
//            liteOrm = LiteOrm.newCascadeInstance(_activity, DB_NAME);
//            liteOrm.setDebugged(true);
//        }
//    }

    public static DbUtils getInstance(Context _activity){
        if(instance == null){
            instance = new DbUtils();
        }
        liteOrm = getLiteOrm(_activity);
        return instance;
    }

    private static LiteOrm getLiteOrm(Context _activity) {
        if (liteOrm == null) {
            liteOrm = LiteOrm.newCascadeInstance(_activity, Constant.dbName);
        }
        return liteOrm;
    }

    /**
     * 插入一条记录
     *
     * @param t
     */
    public  <T> void insert(T t) {
        liteOrm.save(t);
    }

    /**
     * 插入所有记录
     *
     * @param list
     */
    public  <T> void insertAll(List<T> list) {
        liteOrm.save(list);
    }

    /**
     * 查询所有
     *
     * @param cla
     * @return
     */
    public  <T> List<T> getQueryAll(Class<T> cla) {
        return liteOrm.query(cla);
    }

    /**
     * 查询  某字段 等于 Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public  <T> List<T> getQueryByWhere(Class<T> cla, String field, String[] value) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", value));
    }

    /**
     * 查询  某字段 等于 Value的值  可以指定从1-20，就是分页
     *
     * @param cla
     * @param field
     * @param value
     * @param start
     * @param length
     * @return
     */
    public  <T> List<T> getQueryByWhereLength(Class<T> cla, String field, String[] value, int start, int length) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", value).limit(start, length));
    }

    /**
     * 分页查询所有
     *
     * @param cla
     * @param start
     * @param length
     * @return
     */
    public  <T> List<T> getQueryByWhereLength(Class<T> cla, int start, int length) {
        return liteOrm.<T>query(new QueryBuilder(cla).limit(start, length));
    }

    /**
     * 删除所有 某字段等于 Vlaue的值
     * @param cla
     * @param field
     * @param value
     */
//        public static <T> void deleteWhere(Class<T> cla,String field,String [] value){
//            liteOrm.delete(cla, WhereBuilder.create().where(field + "=?", value));
//        }

    /**
     * 删除所有
     *
     * @param cla
     */
    public  <T> void deleteAll(Class<T> cla) {
        liteOrm.deleteAll(cla);
    }

    /**
     * 仅在以存在时更新
     *
     * @param t
     */
    public  <T> void update(T t) {
        liteOrm.update(t, ConflictAlgorithm.Replace);
    }


    public  <T> void updateALL(List<T> list) {
        liteOrm.update(list);
    }

    public  void closeDb(){
        liteOrm.close();
    }

}
