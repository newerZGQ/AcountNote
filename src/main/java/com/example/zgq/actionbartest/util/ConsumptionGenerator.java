package com.example.zgq.actionbartest.util;


import com.example.zgq.actionbartest.consumption.Consumption;
import com.example.zgq.actionbartest.consumption.DayConsumption;
import com.example.zgq.actionbartest.consumption.SingleConsumption;

import java.util.ArrayList;

/**
 * Created by 37902 on 2015/11/30.
 */
public class ConsumptionGenerator {
    public static ArrayList<Consumption> monthConsumptions = new ArrayList<>();
    public static ArrayList<SingleConsumption> singleConsumptions = new ArrayList<>();

    public ConsumptionGenerator() {
        super();
    }

    public static void setSingleConsumptions() {
        int a = 1000;
        ArrayList<SingleConsumption> month = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            SingleConsumption singleConsumption = new SingleConsumption(i, "cloth", "" + a, 5, null, null);
            month.add(singleConsumption);
            a += 10;
        }
        singleConsumptions = month;
    }
//    public static void setDayConsumptions(){
//        monthConsumptions = setDayConsumptions(singleConsumptions);
//    }
//    public static ArrayList<Consumption> setDayConsumptions(ArrayList<SingleConsumption> arrayList) {
//        ArrayList<SingleConsumption> partSingles = new ArrayList<>();
//        ArrayList<Consumption> dayConsumptions = new ArrayList<>();
//        String tmp = "0000";
//        SingleConsumption single;
//        for (int i = 0; i < arrayList.size() + 1; i++) {
//            if (i >= arrayList.size()) {
//                single = null;
//            } else {
//                single = arrayList.get(i);
//            }
//            if (i >= arrayList.size() || (!partSingles.isEmpty() && !(tmp.substring(0, 2).equals(single.getDate().substring(0, 2))))) {
//                double total = 0;
//                for (SingleConsumption partSingle : partSingles) {
//                    total += partSingle.getPrice();
//                }
//                String date = partSingles.get(0).getDate();
//                DayConsumption dayConsumption = new DayConsumption(total, date);
//                dayConsumptions.add(dayConsumption);
//                for (SingleConsumption partSingle : partSingles) {
//                    dayConsumptions.add(partSingle);
//
//                }
//                partSingles.clear();
//            }
//            if (i < arrayList.size()) {
//                partSingles.add(single);
//                tmp = single.getDate();
//            }
//        }
//        return dayConsumptions;
//    }
}
