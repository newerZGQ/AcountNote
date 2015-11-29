package com.example.zgq.actionbartest.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 37902 on 2015/10/24.
 */
public class DateTools {
    public static final boolean SIMPLE_TIME = true;
    public static final boolean DETAIL_TIME = false;
    private static SimpleDateFormat format;
    public static String getDate(boolean flag) {

        if (flag == true) {
            format = new SimpleDateFormat("yyyyMM");

        }
        if (flag == false) {
            format = new SimpleDateFormat("yyyyMMddHH:mmss");

        }
        return format.format(new Date());
    }

}
