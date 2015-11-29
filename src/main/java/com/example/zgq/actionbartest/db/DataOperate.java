package com.example.zgq.actionbartest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.db.GoodsDBHelper;
import com.example.zgq.actionbartest.fragment.GoodsShowFragment;
import com.example.zgq.actionbartest.util.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 37902 on 2015/10/21.
 */
public class DataOperate {
    //    数据库名
    public static final String DB_NAME = "goods_store.db";
    //    数据库版本
    public static final int VERSION = 1;

    private static DataOperate dataOperate;

    public static SQLiteDatabase db;

    public static int currentItemPosition;

    //    构造方法
    private DataOperate(Context context) {
        GoodsDBHelper dbHelper = new GoodsDBHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    //    获取DataOperate实例
    public synchronized static DataOperate getInstance(Context context) {
        if (dataOperate == null) {
            dataOperate = new DataOperate(context);
        }
        return dataOperate;
    }

    public static void saveGoods(Goods goods) {
        if (goods != null) {
            ContentValues values = new ContentValues();
            values.put("price", goods.getPrice());
            values.put("lable", goods.getLable());
            values.put("detail", goods.getDetail());
            values.put("date", goods.getDate());
            values.put("happiness", goods.getHappines());
            values.put("imageId", goods.getImageId());
            db.insert("goods", null, values);
        }
    }
    public static Goods getGoods(int id){
        Cursor myCursor = db.query("goods",new String[]{"id as _id","price","lable","date","happiness","detail","imageId"},null,null,null,null,null);
        myCursor.moveToLast();
        while (id != myCursor.getInt(0)){
            myCursor.moveToPrevious();
        }
        Goods goods = new Goods(myCursor.getInt(1),myCursor.getString(2),myCursor.getString(3),myCursor.getInt(4),myCursor.getString(5),myCursor.getString(6));
        myCursor.close();
        return goods;
    }

    public static long getCurrentNumber(){
        String sql = "SELECT COUNT(*) FROM " +"goods";
        SQLiteStatement statement = db.compileStatement(sql);
        long count = statement.simpleQueryForLong();
        Log.d("count", "" + count);
        return count;
    }
    public static ArrayList<Fragment> getFragmentList(int id){
        currentItemPosition = 0;
        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        Cursor myCursor = db.query("goods",new String[]{"id as _id","price","lable","date","happiness","detail","imageId"},null,null,null,null,null);
        myCursor.moveToFirst();
        fragmentList.add(GoodsShowFragment.newInstance(myCursor.getInt(0)));
        while(myCursor.moveToNext()) {
            int currentId = myCursor.getInt(0);
            fragmentList.add(GoodsShowFragment.newInstance(currentId));
            if (id >= currentId)
                currentItemPosition++;
        }
        return fragmentList;
    }
}
