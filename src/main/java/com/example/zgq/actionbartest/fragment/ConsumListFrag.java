package com.example.zgq.actionbartest.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.activity.GoodsShow;
import com.example.zgq.actionbartest.activity.MainActivity;
import com.example.zgq.actionbartest.consumption.Consumption;
import com.example.zgq.actionbartest.consumption.SingleConsumption;
import com.example.zgq.actionbartest.db.DataOperate;
import com.example.zgq.actionbartest.myview.RefreshLayout;
import com.example.zgq.actionbartest.myview.SwipeListView;
import com.example.zgq.actionbartest.util.DateTools;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by 37902 on 2015/12/8.
 */
public class ConsumListFrag extends Fragment {
    private SwipeAdapter swipeAdapter;
    private SwipeAdapter.IOnItemRightClickListener listener;
    private ArrayList<Consumption> consumptions;
    private RefreshLayout mRefreshLayout;
    private ListView mListView;
    private View footerLayout;
    private View headerLayout;
    private TextView monthConsum;
    private int rememPosition;
    private int year;
    private int month;
    private int[] months = {1,2,3,4,5,6,7,8,9};
    private static Context mContext;

    public ConsumListFrag() {
        super();

    }

    public static ConsumListFrag getInstance(Context Context) {
        mContext = Context;

        return new ConsumListFrag();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rememPosition = 0;
        consumptions = DataOperate.selectedMonth.getConsumptions();
        String date = DateTools.getDate(true);
        year = Integer.parseInt(date.substring(2, 4));
        month = Integer.parseInt(date.substring(4,6));
    }

    @Override
    public void onResume() {
        super.onResume();
        consumptions = DataOperate.selectedMonth.getConsumptions();
        swipeAdapter.arrayList = consumptions;
//        monthConsum.setText(""+DataOperate.selectedMonth.getMonthTotalOut());
        mListView.setAdapter(swipeAdapter);
        if (consumptions.size() == 0){
            footerLayout.setVisibility(View.INVISIBLE);
        }else{
            footerLayout.setVisibility(View.VISIBLE);
        }
        mListView.setSelection(rememPosition);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consumlistfrag, null);
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        mListView = (SwipeListView) view.findViewById(R.id.list);
        footerLayout = LayoutInflater.from(mContext).inflate(R.layout.refreshfooterlayout, null);
        headerLayout = LayoutInflater.from(mContext).inflate(R.layout.listview_header,null);
        monthConsum = (TextView) headerLayout.findViewById(R.id.monthConsum);
        monthConsum.setText(""+DataOperate.selectedMonth.getMonthTotalOut());
        mListView.addFooterView(footerLayout, null, false);
        mListView.addHeaderView(headerLayout, null, false);
        mRefreshLayout.setChildView(mListView);

        swipeAdapter = new SwipeAdapter(mContext,200,consumptions,new SwipeAdapter.IOnItemRightClickListener() {
            @Override
            public void onRightClick(View v, int position) {
                // TODO Auto-generated method stub
                Log.d("------", "-in");
                DataOperate.deleteSingle((SingleConsumption) DataOperate.selectedMonth.getConsumptions().get(position));
                onResume();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (consumptions.get(position-1).isSingleCon()) {
                    Intent intent = new Intent(getActivity(), GoodsShow.class);
                    Consumption consumption = consumptions.get(position-1);
                    intent.putExtra("Consumption", (Serializable) consumption);
                    startActivity(intent);
                }
            }
        });

        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("----","in");
                String sMonth;
                month = month- 1;
                if (month == 0){
                    month = 12;
                    year -=1;
                }
                sMonth=""+month;
                try {
                    if (isSingleNum(month)) {
                        sMonth = "0" + month;
                    }
                    DataOperate.changeData("c20" + year + sMonth);
                    getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentList).onResume();
                }catch (Exception e){
                    month +=1;
                    if (month == 13){
                        month =1;
                        year +=1;
                    }
                    Toast.makeText(getActivity(), "没有记录了", Toast.LENGTH_SHORT).show();
                }
                mRefreshLayout.setRefreshing(false);
                rememPosition = 0;
                onResume();
            }
        });


        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                Log.d("-----","load");
                footerLayout.findViewById(R.id.text_next).setVisibility(View.INVISIBLE);
                footerLayout.findViewById(R.id.load_progress_bar).setVisibility(View.VISIBLE);
                try {
                    Thread.sleep(500);
                }catch (Exception e){

                }
                String sMonth;
                month +=1;
                if (month == 13){
                    month =1;
                    year +=1;
                }
                sMonth = ""+month;
                try {
                    if (isSingleNum(month))
                        sMonth = "0"+month;
                    DataOperate.changeData("c20" + year + sMonth);
                    getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentList).onResume();
                }catch (Exception e){
                    month -=1;
                    if (month == 0){
                        month = 12;
                        year -=1;
                    }
                    Toast.makeText(getActivity(), "没有记录了", Toast.LENGTH_SHORT).show();
                }
                footerLayout.findViewById(R.id.load_progress_bar).setVisibility(View.INVISIBLE);
                footerLayout.findViewById(R.id.text_next).setVisibility(View.VISIBLE);
                rememPosition = mListView.getLastVisiblePosition();
                onResume();
            }
        });
        return view;
    }


    public boolean isSingleNum(int a){
        for (int b:months){
            if (b == a)
                return true;
        }
        return false;
    }
}

