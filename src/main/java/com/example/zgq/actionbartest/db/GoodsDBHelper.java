package com.example.zgq.actionbartest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zgq.actionbartest.util.DateTools;

/**
 * Created by 37902 on 2015/10/23.
 */
public class GoodsDBHelper extends SQLiteOpenHelper {
//    String month = DateTools.getDate(true);
    public static String tableName = "goods"+DateTools.getDate(true);
    public static final String CREATE_GOODS = "create table " + tableName +"("
            + "id integer primary key autoincrement, "
            + "price real, "
            + "lable text, "
            + "detail text, "
            + "date text, "
            + "happiness integer, "
            + "imageId text)";

//    private Context mContext;

    public GoodsDBHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
//        mContext = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GOODS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}

