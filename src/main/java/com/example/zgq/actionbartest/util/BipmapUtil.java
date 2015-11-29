package com.example.zgq.actionbartest.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.FileInputStream;

/**
 * Created by 37902 on 2015/11/27.
 */
public class BipmapUtil {
    public static Bitmap ZoomBipmap(FileInputStream fis,int factor){
        BitmapFactory.Options bitmapFactory = new BitmapFactory.Options();
        bitmapFactory.inJustDecodeBounds = true;
        bitmapFactory.inSampleSize = factor;
        bitmapFactory.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeStream(fis,null,bitmapFactory);
        return bitmap;
    }
//    private Bitmap getBitmapFromUri(Uri uri)
//    {
//        try {
//            // 读取uri所在的图片
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//            return bitmap;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
}
