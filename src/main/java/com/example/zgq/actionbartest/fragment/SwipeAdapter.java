package com.example.zgq.actionbartest.fragment;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.activity.GoodsShow;
import com.example.zgq.actionbartest.consumption.Consumption;
import com.example.zgq.actionbartest.consumption.DayConsumption;
import com.example.zgq.actionbartest.consumption.SingleConsumption;

import java.util.ArrayList;


public class SwipeAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context mContext = null;

    public ArrayList<Consumption> arrayList;
    /**
     *
     */
    private int mRightWidth = 0;


    /**
     * 单击事件监听器
     */
    private IOnItemRightClickListener mListener = null;


    public interface IOnItemRightClickListener {
        void onRightClick(View v, int position);
    }


    /**
//     * @param mainActivity
     */
    public SwipeAdapter(Context ctx, int rightWidth, ArrayList<Consumption> arrayList,IOnItemRightClickListener mListener) {
        mContext = ctx;
        mRightWidth = rightWidth;
        this.arrayList = arrayList;
        this.mListener = mListener;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewSingleHolder itemSingle;
        ViewDayHolder itemDay;

        final int thisPosition = position;
        Consumption consumption = arrayList.get(thisPosition);
        if (!consumption.isSingleCon()) {
            DayConsumption dayConsumption = (DayConsumption) consumption;
            if (convertView == null || convertView.getHeight() == 220) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_fragment_day,parent,false);
                itemDay = new ViewDayHolder();
                itemDay.dayTotal = (TextView) convertView.findViewById(R.id.day_total);
                itemDay.dayDate = (TextView) convertView.findViewById(R.id.day_date);
//                itemDay.dayRight = (TextView) convertView.findViewById(R.id.day_item_right_txt);
                convertView.setTag(itemDay);
            }else{
                itemDay = (ViewDayHolder) convertView.getTag();
            }
            itemDay.dayTotal.setText("" + dayConsumption.getDayTotal());
            itemDay.dayDate.setText(dayConsumption.getDayDate().substring(4,8));
//            itemDay.dayRight.setText("delete");
            return convertView;

        }else{
            final SingleConsumption single = (SingleConsumption) consumption;
            if (convertView == null || convertView.getHeight() == 240 ) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
                itemSingle = new ViewSingleHolder();
                itemSingle.item_left = (View) convertView.findViewById(R.id.item_left);
                itemSingle.item_right = (View) convertView.findViewById(R.id.item_right);
                itemSingle.item_left_price = (TextView) convertView.findViewById(R.id.item_left_price);
                itemSingle.item_left_label = (TextView) convertView.findViewById(R.id.item_left_lable);
                itemSingle.item_left_date = (TextView) convertView.findViewById(R.id.item_left_date);
                itemSingle.item_right_button = (Button) convertView.findViewById(R.id.item_right_button);
                convertView.setTag(itemSingle);
            } else {// 有直接获得ViewHolder
                itemSingle = (ViewSingleHolder) convertView.getTag();
            }
            LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            itemSingle.item_left.setLayoutParams(lp1);
            LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
            itemSingle.item_right.setLayoutParams(lp2);
            itemSingle.item_left_price.setText("" + single.getPrice());
            itemSingle.item_left_label.setText(single.getLable());
            itemSingle.item_left_date.setText(single.getDate().substring(8, 13));
//            itemSingle.item_right_txt.setText("delete");
            itemSingle.item_right.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onRightClick(v, thisPosition);
                    }
                }
            });
//            itemSingle.item_left.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, GoodsShow.class);
//                    intent.putExtra("singleConsumption", single);
//                    Log.d("------1", "ssss");
//                    mContext.startActivity(intent);
//                    Log.d("------2", "ssss");
//                }
//            });
            return convertView;
        }
    }


    private class ViewSingleHolder {
        View item_left;


        View item_right;


        TextView item_left_price;

        TextView item_left_date;

        TextView item_left_label;

        Button item_right_button;
    }
    private class ViewDayHolder {
        TextView dayTotal;
        TextView dayDate;
//        TextView dayRight;
    }
}
