package com.example.zgq.actionbartest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.zgq.actionbartest.db.DataOperate;
import com.example.zgq.actionbartest.util.CreateDir;
import com.example.zgq.actionbartest.consumption_object.SingleConsumption;

import java.util.ArrayList;

public class MainActivity extends Activity {
    public ArrayList<ArrayList<SingleConsumption>> goods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        DataOperate.getInstance(this);
        CreateDir.createDir();
        Intent intent = new Intent(MainActivity.this,HomePageActivity.class);
        startActivity(intent);
        this.finish();
    }

//    dbHelper = new GoodsData(this, "GoodsStore.db", null, 1);
//
//    takePhoto.setOnClickListener(new View.OnClickListener() {
//     public void onClick(View v) {
//      dbHelper.getWritableDatabase();
//      }
//     });
}