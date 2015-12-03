package com.example.zgq.actionbartest.util;

import android.util.Log;

import com.example.zgq.actionbartest.consumption.Consumption;
import com.example.zgq.actionbartest.consumption.DayConsumption;
import com.example.zgq.actionbartest.consumption.SingleConsumption;

import java.util.ArrayList;

/**
 * Created by 37902 on 2015/11/30.
 */
public class ConsumptionGenerator {
    public static ArrayList<Consumption> monthConsumptions;
    public static ArrayList<SingleConsumption> singleConsumptions;
    public static int currentPosition;
    public ConsumptionGenerator() {
        super();
    }

    public static void setMonthConsumptions() {
        int a = 0;
        ArrayList<Consumption> month = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i % 7 == 0) {
                DayConsumption dayConsumption = new DayConsumption();
                month.add(dayConsumption);
            } else {
                SingleConsumption singleConsumption = new SingleConsumption(i, "cloth", ""+a, 5, null, null);
                month.add(singleConsumption);
            }
            a++;
        }
        monthConsumptions = month;
    }

    public static void setSingleConsumptions(ArrayList<Consumption> arrayList) {
        ArrayList<SingleConsumption> single = new ArrayList<>();
        for (Consumption consumption : arrayList) {
            if (consumption.isSingleCon()) {
                SingleConsumption singleConsumption = (SingleConsumption) consumption;
                single.add(singleConsumption);
            }
        }
        singleConsumptions = single;
    }
//    public static SingleConsumption getSingleConsumptionByDate(ArrayList<SingleConsumption> arrayList, String date) {
//        currentPosition = -1;
//        for (SingleConsumption singleConsumption : arrayList) {
//            currentPosition++;
//            Log.d(" ",singleConsumption.toString());
//            if (singleConsumption.getDate().equals(date)) {
//                return singleConsumption;
//            }
//        }
//        return null;
//    }
}
