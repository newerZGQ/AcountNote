package com.example.zgq.actionbartest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.activity.GoodsShow;
import com.example.zgq.actionbartest.consumption.Consumption;
import com.example.zgq.actionbartest.consumption.DayConsumption;
import com.example.zgq.actionbartest.consumption.SingleConsumption;
import com.example.zgq.actionbartest.db.DataOperate;

import java.util.ArrayList;


/**
 * Created by 37902 on 2015/11/30.
 */
public class GoodsListFragment extends ListFragment{
    public static ArrayList<Consumption> monthConsumptions;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        monthConsumptions = DataOperate.monthConsumptions;
        ConsumptionListAdapter consumptionListAdapter = new ConsumptionListAdapter(monthConsumptions);
        setListAdapter(consumptionListAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Consumption consumption = (Consumption) (getListAdapter()).getItem(position);
        if (consumption.isSingleCon()) {
            Intent intent = new Intent(getActivity(), GoodsShow.class);
            intent.putExtra("singleConsumption",(SingleConsumption)consumption);
            startActivity(intent);
        }
    }

    public class ConsumptionListAdapter extends ArrayAdapter<Consumption> {
        public ArrayList<Consumption> consumptions;
        public ConsumptionListAdapter(ArrayList<Consumption> arrayLists) {
            super(getActivity(), 0, arrayLists);
            consumptions = arrayLists;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Consumption consumption = consumptions.get(position);
            if (consumption.isSingleCon()) {
                SingleConsumption singleConsumption = (SingleConsumption) consumption;
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment_single, null);
                TextView price = (TextView) convertView.findViewById(R.id.price);
                price.setText("" + singleConsumption.getPrice());

            } else {
                DayConsumption dayConsumption = (DayConsumption) consumption;
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment_day, null);
                TextView date = (TextView) convertView.findViewById(R.id.day_date);
                date.setText(""+dayConsumption.getDayTotal());
            }
            return convertView;
        }
    }
}
