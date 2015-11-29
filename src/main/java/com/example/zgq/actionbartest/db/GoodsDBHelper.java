package com.example.zgq.actionbartest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 37902 on 2015/10/23.
 */
public class GoodsDBHelper extends SQLiteOpenHelper {
//    public String tableName
    public static final String CREATE_GOODS = "create table goods("
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

