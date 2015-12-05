package com.example.zgq.actionbartest.consumption;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 37902 on 2015/11/30.
 */
public class DayConsumption extends Consumption implements Serializable {
    public static boolean isSingleCon = false;
    private double dayTotal;
    private String dayDate;
    public DayConsumption(double dayTotal,String dayDate){
        super();
        this.dayTotal = dayTotal;
        this.dayDate = dayDate;
    }
    public double getDayTotal(){
        return dayTotal;
    }
    public double getDayConsumption(ArrayList<SingleConsumption> arrayList){
        double dayConsumption = 0;
        for (SingleConsumption s : arrayList){
            dayConsumption += s.getPrice();
        }
        return dayConsumption;
    }

    @Override
    public boolean isSingleCon() {
        return false;
    }
}
