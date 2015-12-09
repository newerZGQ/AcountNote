package com.example.zgq.actionbartest.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.WindowManager;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.consumption.SingleConsumption;
import com.example.zgq.actionbartest.db.DataOperate;
import com.example.zgq.actionbartest.fragment.FragAdatper;
import com.example.zgq.actionbartest.fragment.GoodsListFragment;
import com.example.zgq.actionbartest.fragment.GoodsShowFragment;
import com.example.zgq.actionbartest.util.ConsumptionGenerator;

import java.util.ArrayList;

/**
 * Created by 37902 on 2015/11/1.
 */
public class GoodsShow extends FragmentActivity {
    private ArrayList<SingleConsumption> singleConsumptions;
    private SingleConsumption singleConsumption;
    private SingleConsumption singleConsumption1;
    private ArrayList<GoodsShowFragment> fragmentList;
    private ViewPager mViewPager;
    public String date;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        singleConsumptions = DataOperate.singleConsumptions;
        fragmentList = new ArrayList<>();
        for (SingleConsumption single:singleConsumptions){
            GoodsShowFragment fragment = GoodsShowFragment.newInstance(single);
            fragmentList.add(fragment);
        }
        if (singleConsumption == null) {
            singleConsumption = (SingleConsumption) getIntent().getSerializableExtra("Consumption");
            currentPosition = singleConsumptions.indexOf(singleConsumption);
        }
        setContentView(R.layout.goods_show);
        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(new FragAdatper(getSupportFragmentManager(), fragmentList));
        mViewPager.setCurrentItem(currentPosition);
    }
}
