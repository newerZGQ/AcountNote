package com.example.zgq.actionbartest.util;

import android.os.Environment;

/**
 * Created by 37902 on 2015/10/24.
 */
public class PathTools {

    public static String getPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/happybuy/photo/" + DateTools.getDate(DateTools.SIMPLE_TIME);
    }
}
