package com.example.zgq.actionbartest.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;


import com.example.zgq.actionbartest.activity.GoodsShow;
import com.example.zgq.actionbartest.db.DataOperate;

import java.util.ArrayList;




/**
 * Created by 37902 on 2015/11/28.
 */
public class FragAdatper extends FragmentStatePagerAdapter {

    private ArrayList<GoodsShowFragment> fragmentList;

    public FragAdatper(FragmentManager fragmentManager){
        super(fragmentManager);
    }
    public FragAdatper(FragmentManager fragmentManager,ArrayList<GoodsShowFragment> fragmentList){
        super(fragmentManager);
        this.fragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public GoodsShowFragment getItem(int position) {
        return fragmentList.get(position);
    }
}
