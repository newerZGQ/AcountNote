package com.example.zgq.actionbartest.consumption;

import java.io.Serializable;

/**
 * Created by 37902 on 2015/11/30.
 */
public class MonthConsumption extends Consumption implements Serializable {
    @Override
    public boolean isSingleCon() {
        return false;
    }

    @Override
    public String toString() {
        return "月";
    }
}
