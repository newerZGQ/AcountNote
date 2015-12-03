package com.example.zgq.actionbartest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.zgq.actionbartest.fragment.GoodsShowFragment;
import com.example.zgq.actionbartest.util.DateTools;
import com.example.zgq.actionbartest.consumption.SingleConsumption;

import java.util.ArrayList;

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

    public static String currentTable = "goods"+DateTools.getDate(true);

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

    public static void saveGoods(SingleConsumption singleConsumption) {
        if (singleConsumption != null) {
            ContentValues values = new ContentValues();
            values.put("price", singleConsumption.getPrice());
            values.put("lable", singleConsumption.getLable());
            values.put("detail", singleConsumption.getDetail());
            values.put("date", singleConsumption.getDate());
            values.put("happiness", singleConsumption.getHappines());
            values.put("imageId", singleConsumption.getImageId());
            db.insert(currentTable, null, values);
        }
    }
    public static SingleConsumption getGoods(int id){
        Cursor myCursor = db.query(currentTable,new String[]{"id as _id","price","lable","date","happiness","detail","imageId"},null,null,null,null,null);
        myCursor.moveToLast();
        while (id != myCursor.getInt(0)){
            myCursor.moveToPrevious();
        }
        SingleConsumption singleConsumption = new SingleConsumption(myCursor.getInt(1),myCursor.getString(2),myCursor.getString(3),myCursor.getInt(4),myCursor.getString(5),myCursor.getString(6));
        myCursor.close();
        return singleConsumption;
    }

    public static long getCurrentNumber(){
        String sql = "SELECT COUNT(*) FROM " +currentTable;
        SQLiteStatement statement = db.compileStatement(sql);
        long count = statement.simpleQueryForLong();
        Log.d("count", "" + count);
        return count;
    }
//    public static ArrayList<Fragment> getFragmentList(int id){
//        currentItemPosition = 0;
//        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
//        Cursor myCursor = db.query(currentTable,new String[]{"id as _id","price","lable","date","happiness","detail","imageId"},null,null,null,null,null);
//        myCursor.moveToFirst();
//        fragmentList.add(GoodsShowFragment.newInstance(myCursor.getInt(0)));
//        while(myCursor.moveToNext()) {
//            int currentId = myCursor.getInt(0);
//            fragmentList.add(GoodsShowFragment.newInstance(currentId));
//            if (id >= currentId)
//                currentItemPosition++;
//        }
//        myCursor.close();
//        return fragmentList;
//    }
    public static double getCONSUMPTION(String tableName) {
        double consumption = 0;
        Cursor myCursor = db.query(tableName, new String[]{"id as _id", "price", "lable", "date", "happiness", "detail", "imageId"}, null, null, null, null, null);
        if (myCursor.moveToFirst()) {
            consumption = Double.parseDouble(myCursor.getString(1));
            while (myCursor.moveToNext()) {
                consumption += Double.parseDouble(myCursor.getString(1));
            }
        }
        return consumption;
    }
}
