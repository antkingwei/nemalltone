package com.rolmex.android.nemalltone.db.dao;

import com.rolmex.android.nemalltone.db.DbHelper;
import com.rolmex.android.nemalltone.model.ItemBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ItemTableDao {
    
    private DbHelper mDbHelper;
    
    private SQLiteDatabase mDb = null;
    
    private Context mContext;
    //_id 自增长ID, item_name 名字，item_image 图片 item_class 类名 item_count 点击次数
    
    private final static String TABLE_NAME = "item_table";
    
    private final static String ITEM_NAME = "item_name";
    
    private final static String ITEM_IMAGE = "item_image";
    
    private final static String ITEM_CLASS = "item_class";
    
    private final static String ITEM_COUNT = "item_count";
    
    public ItemTableDao(Context context){
        this.mContext = context;
        mDbHelper = DbHelper.getInstance(context);
        mDb = mDbHelper.getReadableDatabase();
    }
    
    private SQLiteDatabase getDb(){
        if(mDb == null){
            if(mDbHelper ==null){
                mDbHelper = DbHelper.getInstance(mContext);
            }
            mDb = mDbHelper.getReadableDatabase();
        }
        if(!mDb.isOpen())
            mDbHelper.onOpen(mDb);
        return mDb;
    }
    
    private int getItemCount(int item_image,String item_class){
        try{
        Cursor cursor = getDb().query(TABLE_NAME, new String[]{ITEM_COUNT}, ITEM_IMAGE+"=? and "+ITEM_CLASS+"=?", new String[]{item_image+"",item_class}, null, null,null);
        if(cursor!=null && cursor.moveToFirst()){
            return cursor.getInt(0);
        }else{
            return 0;
        }
        }catch(Exception e){
            return 0;
        }
    }
    public void insertIfNot(ItemBean bean){
        int item_count = getItemCount(bean.item_image,bean.item_class);
        ContentValues values = new ContentValues();
        item_count++;
        values.put(ITEM_NAME, bean.item_name);
        values.put(ITEM_IMAGE, bean.item_image);
        values.put(ITEM_CLASS, bean.item_class);
        values.put(ITEM_COUNT, item_count);
        
        if(item_count==1){
            getDb().insert(TABLE_NAME, null, values);
            Log.e("antking", "insert");
        }else{
            getDb().update(TABLE_NAME, values, ITEM_IMAGE+"=? AND "+ITEM_CLASS+"=?", new String[]{bean.item_image+"",bean.item_class});
            Log.e("antking", "update");
        }
    }
    public List<ItemBean> getItemList(){
        List<ItemBean> list = new ArrayList<ItemBean>();
        try{
            Cursor cursor = getDb().query(TABLE_NAME, null, null, null, null, null, new String(ITEM_COUNT+" DESC"));
            while(cursor.moveToNext()){
                ItemBean bean = new ItemBean();
                bean.item_name = cursor.getString(cursor.getColumnIndex(ITEM_NAME));
                bean.item_image = cursor.getInt(cursor.getColumnIndex(ITEM_IMAGE));
                bean.item_class = cursor.getString(cursor.getColumnIndex(ITEM_CLASS));
                list.add(bean);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            
        }
        return list;
    }

}
