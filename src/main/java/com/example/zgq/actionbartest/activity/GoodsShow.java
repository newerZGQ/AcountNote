package com.example.zgq.actionbartest.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.consumption.SingleConsumption;
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
    private ArrayList<GoodsShowFragment> fragmentList;
    private ViewPager mViewPager;
    public String date;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        fragmentList = new ArrayList<>();
        singleConsumption = (SingleConsumption) getIntent().getSerializableExtra("singleConsumption");
        singleConsumptions = ConsumptionGenerator.singleConsumptions;
        for (SingleConsumption single:singleConsumptions){
//            int a= 0;
//            if (!(single.getPrice()==singleConsumption.getPrice())){
//                a +=1;
//                Log.d("----------------",""+a);
//            }else{
//                currentPosition = a;
//            }
            GoodsShowFragment fragment = GoodsShowFragment.newInstance(single);
            fragmentList.add(fragment);
        }
        Log.d("-----------------",""+currentPosition);
        currentPosition = singleConsumptions.indexOf(singleConsumption);
        Boolean b = singleConsumptions.contains(singleConsumption);
        if (b){
            Log.d("-----------------","true");
        }
        setContentView(R.layout.goods_show);
        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(new FragAdatper(getSupportFragmentManager(), fragmentList));
        Log.d("-----------------",""+currentPosition);
        mViewPager.setCurrentItem(currentPosition);
    }

    @Override
    protected void onResume() {
        super.onResume();
        singleConsumptions = ConsumptionGenerator.singleConsumptions;
    }
}
