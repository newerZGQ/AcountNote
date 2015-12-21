package com.example.zgq.actionbartest.activity;

import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.db.DataOperate;
import com.example.zgq.actionbartest.consumption.SingleConsumption;
import com.example.zgq.actionbartest.fragment.ConsumEditFrag;

/**
 * Created by 37902 on 2015/10/16.
 */
public class GoodsEdit extends AppCompatActivity{
    private FragmentManager fragmentManager;
    private Fragment consumEditFrag;

    private Toolbar toolbar;

    private Boolean isInitial;

    private SingleConsumption singleConsumption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consum_edit);
        toolbar = (Toolbar) findViewById(R.id.toolbar_goods_edit);
        setSupportActionBar(toolbar);

        //        提取homepage传过来的TAKE_PHOTO,判断是否打开相机
        isInitial = getIntent().getBooleanExtra("isInitial",true);
        if (isInitial == true) {
            singleConsumption = null;
        }
        if (isInitial == false) {
            singleConsumption = (SingleConsumption) getIntent().getSerializableExtra("singleConsumption");
        }

        fragmentManager = getSupportFragmentManager();
        consumEditFrag = fragmentManager.findFragmentById(R.id.consum_edit_frag);
        if(consumEditFrag == null){
            consumEditFrag = ConsumEditFrag.newInstance(this,singleConsumption,isInitial);
            fragmentManager.beginTransaction()
                    .add(R.id.consum_edit_frag, consumEditFrag)
                    .commit();
        }

    }


//       创建菜单
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_goods_edit, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        return super.onOptionsItemSelected(item);
//    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isInitial == true) {
                Intent intent = new Intent(this, HomePageActivity.class);
                startActivity(intent);
                return true;
            }else {
                Intent intent = new Intent(this, GoodsShow.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
