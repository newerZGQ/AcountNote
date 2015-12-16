package com.example.zgq.actionbartest.consumption;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 37902 on 2015/11/30.
 */
public class MonthConsumption extends Consumption implements Serializable {
    public static String type= "month";
    private ArrayList<SingleConsumption> singleConsumptions;
    private ArrayList<DayConsumption> dayConsumptions;
    private ArrayList<Consumption> consumptions;
    private double monthTotalOut;
    private double monthTotalIn;
    private int happyiness;
    public MonthConsumption(ArrayList<SingleConsumption> singleConsumptions,ArrayList<DayConsumption> dayConsumptions, ArrayList<Consumption> consumptions, double monthTotalOut, double monthTotalIn, int happyiness) {
        this.dayConsumptions = dayConsumptions;
        this.consumptions = consumptions;
        this.monthTotalOut = monthTotalOut;
        this.monthTotalIn = monthTotalIn;
        this.happyiness = happyiness;
        this.singleConsumptions = singleConsumptions;
    }

    public ArrayList<SingleConsumption> getSingleConsumptions() {
        return singleConsumptions;
    }

    public ArrayList<DayConsumption> getDayConsumptions() {
        return dayConsumptions;
    }

    public ArrayList<Consumption> getConsumptions() {
        return consumptions;
    }

    public double getMonthTotalOut() {
        return monthTotalOut;
    }

    public double getMonthTotalIn() {
        return monthTotalIn;
    }

    public double getHappyiness() {
        return happyiness;
    }

    public void setSingleConsumptions(ArrayList<SingleConsumption> singleConsumptions) {
        this.singleConsumptions = singleConsumptions;
    }

    public void setDayConsumptions(ArrayList<DayConsumption> dayConsumptions) {
        this.dayConsumptions = dayConsumptions;
    }

    public void setConsumptions(ArrayList<Consumption> consumptions) {
        this.consumptions = consumptions;
    }

    public void setMonthTotalOut(double monthTotalOut) {
        this.monthTotalOut = monthTotalOut;
    }

    public void setMonthTotalIn(double monthTotalIn) {
        this.monthTotalIn = monthTotalIn;
    }

    public void setHappyiness(int happyiness) {
        this.happyiness = happyiness;
    }

    @Override
    public boolean isSingleCon() {
        return false;
    }

    @Override
    public String toString() {
        return "月";
    }
}
