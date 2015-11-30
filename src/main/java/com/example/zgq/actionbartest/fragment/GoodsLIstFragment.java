package com.example.zgq.actionbartest.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.consumption_object.Consumption;
import com.example.zgq.actionbartest.consumption_object.SingleConsumption;
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
            if (convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment,null);
            }

            Consumption consumption = monthConsumption.get(position);
            TextView t1 = (TextView)convertView.findViewById(R.id.t1);
            t1.setText(consumption.toString());


            return convertView;
        }
    }
}
