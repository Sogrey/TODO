package org.sogrey.sogreyframe.db;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.List;

/**
 * 数据库管理
 * Created by Sogrey on 2016/11/9.
 */

public class DBHelper {

    private static LiteOrm liteOrm;
    private static DBHelper mInstance;

    private DBHelper(Context context,String dbName) {
        liteOrm=LiteOrm.newCascadeInstance(context,dbName);
    }

    public static DBHelper getInstance(Context context,String dbName) {
        DBHelper inst=mInstance;
        if (inst==null) {
            synchronized (DBHelper.class) {
                inst=mInstance;
                if (inst==null) {
                    inst=new DBHelper(context.getApplicationContext(),dbName);
                    mInstance=inst;
                }
            }
        }
        return inst;
    }


    /**
     * 插入一条记录
     *
     * @param t
     */
    public <T> long insert(T t) {
        return liteOrm.save(t);
    }

    /**
     * 插入所有记录
     *
     * @param list
     */
    public <T> void insertAll(List<T> list) {
        liteOrm.save(list);
    }

    /**
     * 查询所有
     *
     * @param cla
     *
     * @return
     */
    public <T> List<T> getQueryAll(Class<T> cla) {
        return liteOrm.query(cla);
    }

    /**
     * 查询  某字段 等于 Value的值
     *
     * @param cla
     * @param fields
     * @param values
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getQueryByWhere(Class<T> cla,String[] fields,String[] values) {
        QueryBuilder queryBuilder = new QueryBuilder(cla);
        if(fields.length==values.length){
            for(int i=0;i<fields.length;i++){
                queryBuilder.where(fields[i]+"=?",values[i]);
                if(i<fields.length-1){
                    queryBuilder.whereAppendAnd();
                }
            }
        }
        return liteOrm.<T>query(queryBuilder);
    }

    /**
     * 查询  某字段 等于 Value的值  可以指定从1-20，就是分页
     *
     * @param cla
     * @param fields
     * @param values
     * @param start
     * @param length
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getQueryByWhereLength(
            Class<T> cla,String[] fields,String[] values,
            int start,int length
    ) {
        QueryBuilder queryBuilder = new QueryBuilder(cla);
        if(fields.length==values.length){
            for(int i=0;i<fields.length;i++){
                queryBuilder.where(fields[i]+"=?",values[i]);
                if(i<fields.length-1){
                    queryBuilder.whereAppendAnd();
                }
            }
        }
        return liteOrm.<T>query(queryBuilder.limit(start,length));
    }

    /**
     * 删除一个数据
     *
     * @param t
     * @param <T>
     */
    public <T> void delete(T t) {
        liteOrm.delete(t);
    }

    /**
     * 删除一个表
     *
     * @param cla
     * @param <T>
     */
    public <T> void delete(Class<T> cla) {
        liteOrm.delete(cla);
    }

    /**
     * 删除集合中的数据
     *
     * @param list
     * @param <T>
     */
    public <T> void deleteList(List<T> list) {
        liteOrm.delete(list);
    }

    /**
     * 清空表
     * @param cla
     * @param <T>
     */
    public <T> void deleteAll(Class<T> cla) {
        liteOrm.deleteAll(cla);
    }

    /**
     * 删除数据库
     */
    public void deleteDatabase() {
        liteOrm.deleteDatabase();
    }

}
