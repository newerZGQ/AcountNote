package com.example.zgq.actionbartest.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.consumption.Consumption;
import com.example.zgq.actionbartest.consumption.DayConsumption;
import com.example.zgq.actionbartest.consumption.SingleConsumption;

import java.util.ArrayList;

/**
 * Created by 37902 on 2015/12/13.
 */
public class RefreshAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<Consumption> arrayList;


    public RefreshAdapter(Context context, ArrayList<Consumption> arrayList) {
        super();
        this.arrayList = arrayList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewSingleHolder itemSingle;
        ViewDayHolder itemDay;

        final int thisPosition = position;
        Consumption consumption = arrayList.get(thisPosition);
        if (!consumption.isSingleCon()) {
            DayConsumption dayConsumption = (DayConsumption) consumption;
            if (convertView == null || convertView.getHeight() == 160) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_fragment_day,parent,false);
                itemDay = new ViewDayHolder();
                itemDay.dayTotal = (TextView) convertView.findViewById(R.id.day_total);
                itemDay.dayDate = (TextView) convertView.findViewById(R.id.day_date);
                convertView.setTag(itemDay);
            }else{
                itemDay = (ViewDayHolder) convertView.getTag();
            }
            itemDay.dayTotal.setText("" + dayConsumption.getDayTotal());
            itemDay.dayDate.setText(dayConsumption.getDayDate().substring(4,8));
            return convertView;

        }else{
            final SingleConsumption single = (SingleConsumption) consumption;
            if (convertView == null || convertView.getHeight() == 180 ) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
                itemSingle = new ViewSingleHolder();
                itemSingle.item_left = (View) convertView.findViewById(R.id.item_left);
//                itemSingle.item_right = (View) convertView.findViewById(R.id.item_right);
                itemSingle.item_left_price = (TextView) convertView.findViewById(R.id.item_left_price);
                itemSingle.item_left_label = (TextView) convertView.findViewById(R.id.item_left_lable);
                itemSingle.item_left_date = (TextView) convertView.findViewById(R.id.item_left_date);
//                itemSingle.item_right_txt = (TextView) convertView.findViewById(R.id.item_right_txt);
                convertView.setTag(itemSingle);
            } else {// 有直接获得ViewHolder
                itemSingle = (ViewSingleHolder) convertView.getTag();
            }
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            itemSingle.item_left.setLayoutParams(lp1);
//            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(30, LinearLayout.LayoutParams.MATCH_PARENT);
//            itemSingle.item_right.setLayoutParams(lp2);
            itemSingle.item_left_price.setText(""+single.getPrice());
            itemSingle.item_left_label.setText(single.getLable());
            itemSingle.item_left_date.setText(single.getDate().substring(8,13));
            return convertView;
        }
    }
    private class ViewSingleHolder {
        View item_left;


        View item_right;


        TextView item_left_price;

        TextView item_left_date;

        TextView item_left_label;

        TextView item_right_txt;
    }
    private class ViewDayHolder {
        TextView dayTotal;
        TextView dayDate;
    }
}
