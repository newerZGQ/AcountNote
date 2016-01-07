package com.example.zgq.actionbartest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.zgq.actionbartest.consumption.Consumption;
import com.example.zgq.actionbartest.consumption.DayConsumption;
import com.example.zgq.actionbartest.consumption.MonthConsumption;
import com.example.zgq.actionbartest.util.CreateDir;
import com.example.zgq.actionbartest.util.DateTools;
import com.example.zgq.actionbartest.consumption.SingleConsumption;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by 37902 on 2015/10/21.
 */
public class DataOperate extends Observable{
    //    数据库名
    public static final String DB_NAME = "consumption.db";
    //    数据库版本
    public static final int VERSION = 1;

    private static DataOperate dataOperate;

    public static SQLiteDatabase db;

    public static GoodsDBHelper dbHelper;

    public static String currentTable = "c"+DateTools.getDate(true);
    //    构造方法
    //    获取DataOperate实例
    public static MonthConsumption selectedMonth;
//    public static MonthConsumption previousMonth;
//    public static MonthConsumption bufferMonth;

    public synchronized static DataOperate getInstance(Context context) {
        if (dataOperate == null) {
            dataOperate = new DataOperate(context);
        }
        return dataOperate;
    }
    private DataOperate(Context context) {
        dbHelper = new GoodsDBHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
        if (!isExits(currentTable)) {
            creatTable(currentTable, db);
        }
        if (!isExits("c201511")) {
            creatTable("c201511", db);
        }
    }

    public static void creatTable(String tableName,SQLiteDatabase db){
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
    public static boolean isExits(String table){
        boolean exits = false;
        String sql = "select * from sqlite_master where name="+"'"+table+"'";
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.getCount()!=0){
            exits = true;
        }
        return exits;
    }

    public static MonthConsumption getMonthConsum(String tableName){
        ArrayList<SingleConsumption> singleConsumptions = getSingleConsumptions(tableName);
        ArrayList<Consumption> consumptions =  getConsumptions(singleConsumptions);
        ArrayList<DayConsumption> dayConsumptions = new ArrayList<>();
        int happiness = 0;
        double monthConsum = 0;
        for (Consumption c: consumptions){
            if (c.isSingleCon()){
                SingleConsumption s = (SingleConsumption)c;
                happiness += s.getHappines();
                monthConsum += s.getPrice();
            }
            if (!c.isSingleCon()){
                dayConsumptions.add((DayConsumption)c);
            }
        }
        if (happiness==0){
        }else {
            happiness /= singleConsumptions.size();
        }
        return new MonthConsumption(singleConsumptions,dayConsumptions,consumptions,monthConsum,0,happiness);
    }

    public static MonthConsumption updateMonthConsum(MonthConsumption monthConsumption){
        ArrayList<SingleConsumption> singleConsumptions = monthConsumption.getSingleConsumptions();
        ArrayList<Consumption> consumptions = getConsumptions(singleConsumptions);
        ArrayList<DayConsumption> dayConsumptions = new ArrayList<>();
        int happiness = 0;
        double monthConsum = 0;
        for (Consumption c: consumptions){
            if (c.isSingleCon()){
                SingleConsumption s = (SingleConsumption)c;
                happiness += s.getHappines();
                monthConsum += s.getPrice();
            }
            if (!c.isSingleCon()){
                dayConsumptions.add((DayConsumption)c);
            }
        }
        if (happiness==0){
        }else {
            happiness /= singleConsumptions.size();
        }
        return new MonthConsumption(singleConsumptions,dayConsumptions,consumptions,monthConsum,0,happiness);
    }

//保存数据到数据库
    public static void saveGoods(SingleConsumption singleConsumption) {
        if (singleConsumption != null) {
            ContentValues values = new ContentValues();
            values.put("price", singleConsumption.getPrice());
            values.put("lable", singleConsumption.getLable());
            values.put("detail", singleConsumption.getDetail());
            values.put("date", singleConsumption.getDate());
            values.put("happiness", singleConsumption.getHappines());
            values.put("imageId", singleConsumption.getImageId());
            db.insert("c" + singleConsumption.getDate().substring(0, 6), null, values);
        }
    }

    public static void deleteSingle(SingleConsumption singleConsumption){
        if (singleConsumption != null){
            db.delete("c" + singleConsumption.getDate().substring(0, 6), "date=?", new String[]{singleConsumption.getDate()});
        }
        selectedMonth.getSingleConsumptions().remove(selectedMonth.getSingleConsumptions().indexOf(singleConsumption));
//        singleConsumptions.remove(singleConsumptions.indexOf(singleConsumption));
//        selectedMonth.setConsumptions(getConsumptions(selectedMonth.getSingleConsumptions()));
        selectedMonth = updateMonthConsum(selectedMonth);
    }

    public static void changeData(String tableName){
        selectedMonth = getMonthConsum(tableName);
//        setSingleConsumptions(tableName);
//        setDayConsumptions();
    }
    public static void setSingleConsumptions() {
//        String[] date = {"2015110108:0102","2015110109:0102","2015110110:0102","2015110111:0102","2015110112:0102","2015110113:0102","2015110114:0102","2015110115:0102","2015110116:0102","2015110117:0102",
//                "2015110207:0102","2015110208:0102","2015110209:0102","2015110210:0102","2015110211:0102","2015110212:0102","2015110213:0102","2015110214:0102","2015110215:0102","2015110216:0102","2015110217:0102",
//                "2015110307:0102","2015110308:0102","2015110309:0102","2015110310:0102","2015110311:0102","2015110312:0102","2015110313:0102","2015110314:0102","2015110315:0102","2015110316:0102","2015110317:0102",
//                "2015110407:0102","2015110408:0102","2015110409:0102","2015110410:0102","2015110411:0102","2015110412:0102","2015110413:0102","2015110414:0102","2015110415:0102","2015110416:0102","2015110417:0102",
//                "2015120107:0102","2015120108:0102","2015120109:0102","2015120110:0102","2015120111:0102","2015120112:0102","2015120113:0102","2015120114:0102","2015120115:0102","2015120116:0102","2015120117:0102",
//                "2015120207:0102","2015120208:0102","2015120209:0102","2015120210:0102","2015120211:0102","2015120212:0102","2015120213:0102","2015120214:0102","2015120215:0102","2015120216:0102","2015120217:0102",
//                "2015120307:0102","2015120308:0102","2015120309:0102","2015120310:0102","2015120311:0102","2015120312:0102","2015120313:0102","2015120314:0102","2015120315:0102","2015120316:0102","2015120317:0102",
//                "2015120407:0102","2015120408:0102","2015120409:0102","2015120410:0102","2015120411:0102","2015120412:0102","2015120413:0102","2015120414:0102","2015120415:0102","2015120416:0102","2015120417:0102",
//                };
//        ArrayList<SingleConsumption> month = new ArrayList<>();
//        for (int i = 0; i<date.length; i++) {
//            SingleConsumption singleConsumption = new SingleConsumption(i, "cloth", date[i], 5, null, null);
//            saveGoods(singleConsumption);
//        }

//        singleConsumptions = getSingleConsumptions(tableName);
    }
    public static ArrayList<SingleConsumption> getSingleConsumptions(String tableName){
        ArrayList<SingleConsumption> singleConsumptions = new ArrayList<SingleConsumption>();
        Cursor myCursor = db.query(tableName,new String[]{"id as _id","price","lable","date","happiness","detail","imageId"},null,null,null,null,null);
        myCursor.moveToFirst();
        try {
            SingleConsumption single = new SingleConsumption(myCursor.getInt(1), myCursor.getString(2), myCursor.getString(3), myCursor.getInt(4), myCursor.getString(5), myCursor.getString(6));
            singleConsumptions.add(single);
            while (myCursor.moveToNext()) {
                SingleConsumption single1 = new SingleConsumption(myCursor.getInt(1), myCursor.getString(2), myCursor.getString(3), myCursor.getInt(4), myCursor.getString(5), myCursor.getString(6));
                singleConsumptions.add(single1);
            }
            myCursor.close();
        }catch (RuntimeException e){

        }
        return singleConsumptions;
    }


    public static ArrayList<Consumption> getConsumptions(ArrayList<SingleConsumption> arrayList) {
        ArrayList<SingleConsumption> partSingles = new ArrayList<>();
        ArrayList<Consumption> dayConsumptions = new ArrayList<>();
        String tmp = "0000";
        SingleConsumption single;
        for (int i = 0; i < arrayList.size() + 1; i++) {
            if (i >= arrayList.size()) {
                single = null;
            } else {
                single = arrayList.get(i);
            }
            if (i >= arrayList.size() || (!partSingles.isEmpty() && !(tmp.substring(0,8).equals(single.getDate().substring(0,8))))) {
                double total = 0;
                for (SingleConsumption partSingle : partSingles) {
                    total += partSingle.getPrice();
                }
                try {
                    String date = partSingles.get(0).getDate().substring(0,8);
                    Log.d("---",date);
                    DayConsumption dayConsumption = new DayConsumption(total,date,partSingles);
                    dayConsumptions.add(dayConsumption);
                    for (SingleConsumption partSingle : partSingles) {
                        dayConsumptions.add(partSingle);
                    }
                }catch (Exception e){

                }
                partSingles.clear();
            }
            if (i < arrayList.size()) {
                partSingles.add(single);
                tmp = single.getDate();
            }
        }
        return dayConsumptions;
    }
    public static boolean addSingleCon(SingleConsumption single){
        selectedMonth.getSingleConsumptions().add(single);
        selectedMonth = updateMonthConsum(selectedMonth);
//        setConsumptions();
        return true;
    }
    public static void setSingleCon(SingleConsumption oldSingle,SingleConsumption newSingle){
        selectedMonth.getSingleConsumptions().set(selectedMonth.getSingleConsumptions().indexOf(oldSingle), newSingle);
        selectedMonth = updateMonthConsum(selectedMonth);
    }
    public static void initialData(Context context){
        getInstance(context);
        CreateDir.createDir();
        setSingleConsumptions();
        selectedMonth = getMonthConsum("c" + DateTools.getDate(true));
    }

    public static void changeSingle(SingleConsumption older,SingleConsumption newer){
        if (selectedMonth.getSingleConsumptions().contains(older)) {
            selectedMonth.getSingleConsumptions().set(selectedMonth.getSingleConsumptions().indexOf(older), newer);
            selectedMonth = updateMonthConsum(selectedMonth);
            updateSingleData(getSingleInData(older),newer);
        }
    }
    public static void updateSingleData(int id,SingleConsumption singleConsumption){
        ContentValues values = new ContentValues();
        values.put("price", singleConsumption.getPrice());
        values.put("lable", singleConsumption.getLable());
        values.put("detail", singleConsumption.getDetail());
        values.put("date", singleConsumption.getDate());
        values.put("happiness", singleConsumption.getHappines());
        values.put("imageId", singleConsumption.getImageId());
        db.update("c"+singleConsumption.getDate().substring(0,6),values,"id=?",new String[]{""+id});
    }
    public static int getSingleInData(SingleConsumption single){
        Cursor myCursor = db.query("c"+single.getDate().substring(0,6),new String[]{"id as _id","price","lable","date","happiness","detail","imageId"},null,null,null,null,null);
        myCursor.moveToLast();
        while(!myCursor.getString(3).equals(single.getDate())){
            myCursor.moveToPrevious();
        }
        return myCursor.getInt(0);
    }
    public static int findConsumByDate(String date,ArrayList<Consumption> arrayList){
        if (arrayList.size() > 0) {
            for (Consumption c : arrayList) {
                if (!c.isSingleCon()) {
                    Log.d("----", "000");
                    DayConsumption d = (DayConsumption)c;
                    Log.d("----", date);
                    String s = d.getDayDate();
                    Log.d("----", s);
                    Log.d("----", date);
                    if (s.equals(date)) {
                        return arrayList.indexOf(d);
                    }
                }
            }
        }
        return 0;
    }
}
