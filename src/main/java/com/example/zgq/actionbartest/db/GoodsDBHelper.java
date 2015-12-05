package com.example.zgq.actionbartest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zgq.actionbartest.util.DateTools;

/**
 * Created by 37902 on 2015/10/23.
 */
public class GoodsDBHelper extends SQLiteOpenHelper {
//    String tableName = "consump"+DateTools.getDate(true);
    public void creatTable(String tableName,SQLiteDatabase db){
        String CREATE_CONSUMPTION = "create table "+tableName+" ("
                + "id integer primary key autoincrement, "
                + "price real, "
                + "lable text, "
                + "detail text, "
                + "date text, "
                + "happiness integer, "
                + "imageId text)";
        db.execSQL(CREATE_CONSUMPTION);
    }
//    public static String CREATE_CONSUMPTION = "create table goods ("
//            + "id integer primary key autoincrement, "
//            + "price real, "
//            + "lable text, "
//            + "detail text, "
//            + "date text, "
//            + "happiness integer, "
//            + "imageId text)";

//    private Context mContext;

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    public GoodsDBHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_CONSUMPTION);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }
}

