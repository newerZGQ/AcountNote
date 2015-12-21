package com.example.zgq.actionbartest.consumption;

/**
 * Created by 37902 on 2015/11/30.
 */
public abstract class Consumption {
     public String date;
     public boolean isCollected;
     public Consumption(){
     }
     public abstract boolean isSingleCon();
     public String getDate(){
          return date;
     }
}
