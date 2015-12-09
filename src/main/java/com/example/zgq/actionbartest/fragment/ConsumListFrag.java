package com.example.zgq.actionbartest.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.activity.GoodsShow;
import com.example.zgq.actionbartest.activity.MainActivity;
import com.example.zgq.actionbartest.consumption.Consumption;
import com.example.zgq.actionbartest.db.DataOperate;
import com.example.zgq.actionbartest.myview.SwipeListView;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by 37902 on 2015/12/8.
 */
public class ConsumListFrag extends Fragment {
    private ArrayList<Consumption> consumptions;
    private SwipeListView mListView;
    private Context mContext;

    public ConsumListFrag() {
        super();
    }

    public ConsumListFrag getInstance() {
        return new ConsumListFrag();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consumlistfrag, null);
        mListView = (SwipeListView) view.findViewById(R.id.swipe_listview);
        SwipeAdapter adapter = new SwipeAdapter(getActivity(), mListView.getRightViewWidth(),consumptions);
        mListView.setAdapter(adapter);
        mListView.requestFocus();
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(consumptions.size() - 1);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (consumptions.get(position).isSingleCon()) {
                    Intent intent = new Intent(getActivity(), GoodsShow.class);
                    Consumption consumption = consumptions.get(position);
                    intent.putExtra("Consumption", (Serializable) consumption);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        consumptions = DataOperate.monthConsumptions;
    }
}

