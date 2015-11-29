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
    public static ArrayList<Fragment> getFragmentList(){
        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        Cursor myCursor = db.query("goods",new String[]{"id as _id","price","lable","date","happiness","detail","imageId"},null,null,null,null,null);
        myCursor.moveToFirst();
        fragmentList.add(GoodsShowFragment.newInstance(myCursor.getInt(0)));
        while(myCursor.moveToNext())
             fragmentList.add(GoodsShowFragment.newInstance(myCursor.getInt(0)));
        return fragmentList;
    }
}
//    public List<Goods> loadGoods(){
//        List<Goods> list = new ArrayList<Goods>();
//        Cursor cursor = db.query("Goods",null,null,null,null,null,null);
//        if (cursor.moveToFirst()) {
//            do {
//                Goods goods = new Goods();
//                goods.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
//                goods.setLable(cursor.getString(cursor.getColumnIndex("lable")));
//                goods.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
//                goods.setDate(cursor.getString(cursor.getColumnIndex("date")));
//                goods.setHappines(cursor.getInt(cursor.getColumnIndex("happiness")));
//                goods.setImageId(cursor.getString(cursor.getColumnIndex("imageId")));
//                list.add(goods);
//            } while (cursor.moveToNext());
//        }
//        return list;
//    }
