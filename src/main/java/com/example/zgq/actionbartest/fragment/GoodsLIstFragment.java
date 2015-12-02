package com.example.zgq.actionbartest.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.consumption.Consumption;
import com.example.zgq.actionbartest.consumption.DayConsumption;
import com.example.zgq.actionbartest.consumption.SingleConsumption;
import com.example.zgq.actionbartest.util.ConsumptionGenerator;

import java.util.ArrayList;

/**
 * Created by 37902 on 2015/11/30.
 */
public class GoodsLIstFragment extends ListFragment{
    private ArrayList<Consumption> monthConsumption;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        monthConsumption = ConsumptionGenerator.monthConsumptionGenerator();
        ConsumptionListAdapter goodsListAdapter = new ConsumptionListAdapter(monthConsumption);
        setListAdapter(goodsListAdapter);
    }

    public class ConsumptionListAdapter extends ArrayAdapter<Consumption>{
        public ConsumptionListAdapter(ArrayList<Consumption> arrayLists){
            super(getActivity(),0,arrayLists);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            if (convertView == null){
//                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment_test,null);
//            }
//            LinearLayout linearLayout = new LinearLayout(getContext());
//            ViewGroup viewGroup = new ViewGroup(getContext()) {
//                @Override
//                protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//                }
//            };
//            viewGroup.addView(linearLayout);

            Consumption consumption = monthConsumption.get(position);
            if (consumption.toString().equals("single")) {
                SingleConsumption singleConsumption = (SingleConsumption)consumption;
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment_test,null);
                TextView price = (TextView) convertView.findViewById(R.id.price);
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                price.setText("6666");
//                linearLayout.addView(price,layoutParams);

            }else{
                DayConsumption dayConsumption = (DayConsumption)consumption;
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment,null);
                TextView date = (TextView)convertView.findViewById(R.id.day_date);
                date.setText("1206");
            }
            return convertView;
        }
    }
}
