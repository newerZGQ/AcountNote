package com.example.zgq.actionbartest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.db.DataOperate;
import com.example.zgq.actionbartest.util.DateTools;

/**
 * Created by 37902 on 2015/12/6.
 */
public class HomeTopFrag extends Fragment implements View.OnClickListener{
    private ImageButton count;
    private ImageButton prevMonth;
    private ImageButton nextMonth;
    private TextView year_month;
    private TextView happiness;
    private TextView monthOut;
    private TextView monthOutNumber;
    private TextView monthIn;
    private TextView monthInNumber;
//    private String[] year ={"13年","14年","15年","16年","17年","18年","19年","20年","21年","22年","23年","24年","25年","26年","27年","28年","29年","30年","31年","33年","34年","35年","36年","37年","38年","39年","40年","41年","42年","43年","44年","45年","46年","47年","48年","49年","50年"};
//    private int[] year;
    private int year;
    private int month;

    private int[] months = {1,2,3,4,5,6,7,8,9};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String date = DateTools.getDate(true);
        year = Integer.parseInt(date.substring(2, 4));
        month = Integer.parseInt(date.substring(4,6));
        Log.d("-----1",""+month);
    }
    public boolean isSingleNum(int a){
        for (int b:months){
            if (b == a)
                return true;
        }
        return false;
    }
    @Override
    public void onClick(View v) {
        String sMonth;
        switch (v.getId()){
            case R.id.previous:
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
                    Toast.makeText(getActivity(), "别点了，没有记录了", Toast.LENGTH_SHORT).show();
                }
                onResume();
                Log.d("---",""+month);
                break;
            case R.id.next:
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
                    Toast.makeText(getActivity(), "别点了，没有记录了", Toast.LENGTH_SHORT).show();
                }
                onResume();
                break;
            case R.id.year_month:

                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_top,container,false);
        prevMonth = (ImageButton) view.findViewById(R.id.previous);
        nextMonth = (ImageButton) view.findViewById(R.id.next);
        year_month = (TextView) view.findViewById(R.id.year_month);
        happiness = (TextView) view.findViewById(R.id.month_happy_text);
//        monthOut = (TextView) view.findViewById(R.id.month_out);
//        monthOutNumber = (TextView) view.findViewById(R.id.month_out_number);
//        monthIn = (TextView) view.findViewById(R.id.month_in);
//        monthInNumber = (TextView) view.findViewById(R.id.month_in_number);

//        String date = DataOperate.singleConsumptions.get(0).getDate().substring(0, 6);
//        year_month.setText("  "+date.substring(2, 4) + "年 " + date.substring(4, 6) + "月"+"  ");

        prevMonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        year_month.setText("" + year + "年," + month + "月");
    }
}
