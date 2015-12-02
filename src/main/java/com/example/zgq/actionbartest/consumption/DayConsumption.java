package com.example.zgq.actionbartest.consumption;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 37902 on 2015/11/30.
 */
public class DayConsumption extends Consumption implements Serializable {
    public DayConsumption(){
        super();
    }

    public double getDayConsumption(ArrayList<SingleConsumption> arrayList){
        double dayConsumption = 0;
        for (SingleConsumption s : arrayList){
            dayConsumption += s.getPrice();
        }
        return dayConsumption;
    }

    @Override
    public String toString() {
        return "Day";
    }
}
