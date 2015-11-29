package com.example.zgq.actionbartest.activity;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.db.DataOperate;
import com.example.zgq.actionbartest.fragment.FragAdatper;
import com.example.zgq.actionbartest.fragment.GoodsShowFragment;

import java.util.ArrayList;

/**
 * Created by 37902 on 2015/11/1.
 */
public class GoodsShow extends FragmentActivity {
    private ArrayList<Fragment> fragmentList;
    private ViewPager mViewPager;
    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("id", 0);

        fragmentList = DataOperate.getFragmentList();
        setContentView(R.layout.goods_show);
        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(new FragAdatper(getSupportFragmentManager(),fragmentList));
        mViewPager.setCurrentItem(id-1);
    }
}
