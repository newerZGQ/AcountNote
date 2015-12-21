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
    private ArrayList<SingleConsumption> singleConsumptions;
    public DayConsumption(double dayTotal,String dayDate,ArrayList<SingleConsumption> singleConsumptions){
        super();
        this.dayTotal = dayTotal;
        this.dayDate = dayDate;
        this.singleConsumptions = singleConsumptions;
    }

    public double getDayTotal(){
        return dayTotal;
    }
    public String getDayDate(){
        return dayDate;
    }
    public ArrayList<SingleConsumption> getSingleConsumptions(){
        return singleConsumptions;
    }
    @Override
    public boolean isSingleCon() {
        return false;
    }
}
