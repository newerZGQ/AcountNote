package com.example.zgq.actionbartest.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.activity.GoodsShow;
import com.example.zgq.actionbartest.activity.HomePageActivity;
import com.example.zgq.actionbartest.activity.MainActivity;
import com.example.zgq.actionbartest.consumption.Consumption;
import com.example.zgq.actionbartest.consumption.MonthConsumption;
import com.example.zgq.actionbartest.consumption.SingleConsumption;
import com.example.zgq.actionbartest.db.DataOperate;
import com.example.zgq.actionbartest.db.GoodsDBHelper;
import com.example.zgq.actionbartest.myview.RefreshLayout;
import com.example.zgq.actionbartest.myview.SwipeListView;
import com.example.zgq.actionbartest.util.DateTools;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.logging.LogRecord;

/**
 * Created by 37902 on 2015/12/8.
 */
public class ConsumListFrag extends Fragment {
    private SwipeAdapter swipeAdapter;
    private SwipeAdapter.IOnItemRightClickListener listener;
    private ArrayList<Consumption> consumptions;
    private MonthConsumption monthConsumption;
    private RefreshLayout mRefreshLayout;
    private ListView mListView;
    private View footerLayout;
    private View headerLayout;
    private TextView monthConsum;
    private TextView dateMonth;
    private TextView dateYear;
    private int rememPosition;
    private int year;
    private int month;
    private int day;
    private boolean isDatePick = false;
    private int[] months = {1,2,3,4,5,6,7,8,9};
    private static Context mContext;
    private static Handler mHandler;

    public ConsumListFrag() {
        super();

    }

    public static ConsumListFrag getInstance(Context Context,Handler handler) {
        mContext = Context;
        mHandler = handler;
        return new ConsumListFrag();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rememPosition = 0;
        monthConsumption = DataOperate.selectedMonth;
        consumptions = monthConsumption.getConsumptions();
        String date = DateTools.getDate(true);
        year = Integer.parseInt(date.substring(0, 4));
        month = Integer.parseInt(date.substring(4,6));

    }
    @Override
    public void onResume() {
        super.onResume();
        monthConsumption = DataOperate.selectedMonth;
        consumptions = monthConsumption.getConsumptions();
        swipeAdapter.arrayList = consumptions;
        monthConsum.setText("" + monthConsumption.getMonthTotalOut());
        Log.d("-----",""+monthConsumption.getMonthTotalOut());
        dateMonth.setText("" + month);
        dateYear.setText("" + year);
        mListView.setAdapter(swipeAdapter);
        if (monthConsumption.getConsumptions().size() == 0){
            footerLayout.setVisibility(View.INVISIBLE);
        }else{
            footerLayout.setVisibility(View.VISIBLE);
        }
        if (isDatePick) {
            String mMonth = ""+month;
            String mDay = ""+day;
            if (month>0 && month < 10){
                mMonth = "0" + month;
            }
            if (day>0 && day < 10){
                mDay = "0" + day;
            }
            Log.d("-------","" + year + mMonth + mDay);
            rememPosition = DataOperate.findConsumByDate("" + year + mMonth + mDay,monthConsumption.getConsumptions());
            isDatePick = false;
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
        headerLayout = LayoutInflater.from(mContext).inflate(R.layout.listview_header,mListView,false);
        dateMonth = (TextView) headerLayout.findViewById(R.id.date_month);
        dateYear = (TextView) headerLayout.findViewById(R.id.date_year);

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
                DataOperate.deleteSingle((SingleConsumption) monthConsumption.getConsumptions().get(position));
                rememPosition = mListView.getFirstVisiblePosition();
                mHandler.sendEmptyMessage(0x1233);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (consumptions.get(position-1).isSingleCon()) {
                    rememPosition = mListView.getFirstVisiblePosition();
                    Intent intent = new Intent(getActivity(), GoodsShow.class);
                    Consumption consumption = consumptions.get(position-1);
                    Log.d("-",""+((SingleConsumption)consumption).getPrice());
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
                Log.d("----", "in");
                String sMonth;
                month = month - 1;
                if (month == 0) {
                    month = 12;
                    year -= 1;
                }
                sMonth = "" + month;
                try {
                    if (isSingleNum(month)) {
                        sMonth = "0" + month;
                    }
                    if (!DataOperate.isExits("c" + year + sMonth)) {
                        DataOperate.creatTable("c" + year + sMonth, DataOperate.db);
                    }
                    DataOperate.changeData("c" + year + sMonth);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "该月没有记录", Toast.LENGTH_SHORT).show();
                }
                mRefreshLayout.setRefreshing(false);
                rememPosition = 0;
                mHandler.sendEmptyMessage(0x1233);

            }
        });

        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                String sMonth;
                month +=1;
                if (month == 13){
                    month =1;
                    year +=1;
                }
                sMonth = ""+month;
                try {

                    if (isSingleNum(month)) {
                        sMonth = "0" + month;
                    }
                    if (!DataOperate.isExits("c" + year + sMonth)){
                        DataOperate.creatTable("c" + year + sMonth, DataOperate.db);
                    }
                    DataOperate.changeData("c" + year + sMonth);
                    rememPosition = 0;
                    mHandler.sendEmptyMessage(0x1233);
//                    getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentList).onResume();
                }catch (Exception e){
                    e.printStackTrace();
                }
                footerLayout.findViewById(R.id.load_progress_bar).setVisibility(View.INVISIBLE);
                footerLayout.findViewById(R.id.text_next).setVisibility(View.VISIBLE);
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
    public void setDate(int year,int month,int day,boolean isDatePick){
        this.year = year;
        this.month = month;
        this.day = day;
        this.isDatePick = isDatePick;
    }
}

