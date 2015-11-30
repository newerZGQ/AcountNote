package com.example.zgq.actionbartest.consumption_object;

import com.example.zgq.actionbartest.util.DateTools;

import java.io.Serializable;

/**
 * Created by 37902 on 2015/10/15.
 */
public class SingleConsumption extends Consumption implements Serializable{
    private double price;
    private String lable;
    private String date;
    private int happiness;
    private String detail;
    private String imageId;

    public SingleConsumption(){

    }

    public SingleConsumption(double price, String lable, String date, int happiness, String detail, String imageId){
        this.price = price;
        this.lable = lable;
        this.date = date;
        this.happiness = happiness;
        this.detail = detail;
        this.imageId = imageId;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getLable() {
        return lable;
    }
    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }


    public int getHappines() {
        return happiness;
    }
    public void setHappines(int happines) {
        this.happiness = happines;
    }

    public String getImageId() {
        return imageId;
    }
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return getLable();
    }

    public String getImageName(){
        return DateTools.getDate(DateTools.DETAIL_TIME);

    }
}

