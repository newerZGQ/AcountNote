package com.example.zgq.actionbartest.util;

import com.example.zgq.actionbartest.consumption_object.Consumption;
import com.example.zgq.actionbartest.consumption_object.DayConsumption;
import com.example.zgq.actionbartest.consumption_object.SingleConsumption;

import java.util.ArrayList;

/**
 * Created by 37902 on 2015/11/30.
 */
public class ConsumptionGenerator {
    public ConsumptionGenerator(){
        super();
    }
    public static ArrayList<Consumption> monthConsumptionGenerator(){
        ArrayList<Consumption> monthConsumption = new ArrayList<>();
        for (int i = 0;i<300;i++){
            if (i%7 == 0) {
                DayConsumption dayConsumption = new DayConsumption();
                monthConsumption.add(dayConsumption);
            }
            SingleConsumption singleConsumption = new SingleConsumption(i,"cloth",null,5,null,null);

            monthConsumption.add(singleConsumption);
        }
        return monthConsumption;
    }
}
