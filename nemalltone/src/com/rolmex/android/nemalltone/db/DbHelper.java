package com.rolmex.android.nemalltone.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
    
    private static DbHelper mInstance = null;
    /** 数据库名称**/
    private static final String DATABASE_NAME = "rolmex.db";
    
    /** 数据库版本号 **/
    private static final int DATABASE_VERSION = 1;
    
    /**
     * 创建数据库 快捷方式 表
     * _id 自增长ID, item_name 名字，item_image 图片 item_class 类名 item_count 点击次数
     * 
     */
    
    private static final String ITEM_TABLE_CREATE = "create table item_table ("+
    "_id INTEGER PRIMARY KEY AUTOINCREMENT, item_name TEXT, item_image INTEGER, item_class TEXT, item_count INTEGER);";
    
    public static DbHelper getInstance(Context context){
        if(mInstance ==null){
            synchronized(DbHelper.class){
                if(mInstance ==null){
                    mInstance = new DbHelper(context);
                }
            }
        }
        return mInstance;
    }
    
    /**
     * 
     * @param context
     * @param name
     * @param factory
     * @param version
     */

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(ITEM_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        
    }
    public boolean deleteDatabase(Context context){
        return context.deleteDatabase(DATABASE_NAME);
    }

}
