package com.example.zgq.actionbartest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.consumption.SingleConsumption;
import com.example.zgq.actionbartest.db.DataOperate;
import com.example.zgq.actionbartest.fragment.FragAdatper;
import com.example.zgq.actionbartest.fragment.GoodsShowFragment;
import com.example.zgq.actionbartest.fragment.HomeBottomFrag;
import com.example.zgq.actionbartest.fragment.ShowBottom;

import java.util.ArrayList;

/**
 * Created by 37902 on 2015/11/1.
 */
public class GoodsShow extends AppCompatActivity{
    private ArrayList<SingleConsumption> singleConsumptions;
    private SingleConsumption singleConsumption;
    private ArrayList<GoodsShowFragment> fragmentList;
    private ViewPager mViewPager;
    public String date;
    private int currentPosition;
    public android.support.v7.widget.Toolbar toolbar;
    private FragmentStatePagerAdapter adapter;
    private ImageButton edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_show);
        toolbar = (Toolbar) findViewById(R.id.showtoolbar);
        setSupportActionBar(toolbar);
        singleConsumptions = DataOperate.selectedMonth.getSingleConsumptions();
//        edit = (ImageButton) findViewById(R.id.show_edit);
//        Log.d(",,,,3333", "ininin");
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(",,,,33", "ininin");
//                int a = mViewPager.getCurrentItem();
//                Intent intent = new Intent(GoodsShow.this, GoodsEdit.class);
//                intent.putExtra("singleConsumption", DataOperate.selectedMonth.getSingleConsumptions().get(a));
//                intent.putExtra("isInitial", false);
//                startActivity(intent);
//            }
//        });
//        Log.d(",,,,333", "ininin");
    }

    @Override
    protected void onResume() {
        super.onResume();
        singleConsumptions = DataOperate.selectedMonth.getSingleConsumptions();
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
        adapter = new FragAdatper(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition);
    }
}
