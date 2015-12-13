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
import android.widget.Toast;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.activity.GoodsShow;
import com.example.zgq.actionbartest.activity.MainActivity;
import com.example.zgq.actionbartest.consumption.Consumption;
import com.example.zgq.actionbartest.db.DataOperate;
import com.example.zgq.actionbartest.myview.RefreshLayout;
import com.example.zgq.actionbartest.myview.SwipeListView;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by 37902 on 2015/12/8.
 */
public class ConsumListFrag extends Fragment {
    private ArrayList<Consumption> consumptions;
    private RefreshLayout mRefreshLayout;
    private ListView mListView;
    private View footerLayout;
    private View headerLayout;
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
        consumptions = DataOperate.monthConsumptions;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consumlistfrag, null);
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        mListView = (SwipeListView) view.findViewById(R.id.list);
        footerLayout = LayoutInflater.from(mContext).inflate(R.layout.refreshfooterlayout, null);
        headerLayout = LayoutInflater.from(mContext).inflate(R.layout.listview_header,null);
        mListView.addFooterView(footerLayout,null,false);
        mListView.addHeaderView(headerLayout,null,false);
        mRefreshLayout.setChildView(mListView);
//        BaseAdapter adapter = new RefreshAdapter(mContext, consumptions);
        SwipeAdapter swipeAdapter = new SwipeAdapter(mContext,200,consumptions);
        mListView.setAdapter(swipeAdapter);
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
                // start to refresh
            }
        });
        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                // start to load
            }
        });
        return view;
    }
}





//    }

//        mListView.requestFocus();
//        mListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
//        mListView.setSelection(consumptions.size() - 1);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (consumptions.get(position).isSingleCon()) {
//                    Intent intent = new Intent(getActivity(), GoodsShow.class);
//                    Consumption consumption = consumptions.get(position);
//                    intent.putExtra("Consumption", (Serializable) consumption);
//                    startActivity(intent);
//                }
//            }
//        });
//        mListView.setOnRefreshListener(new SwipeListView.OnRefreshListener() {
//
//
//            @Override
//            public void onRefresh() {
//                mListView.postDelayed(new Runnable() {
//
//
//
//                    @Override
//                    public void run() {
//                        mListView.onRefreshComplete();
//                    }
//                }, 2000);
//            }
//        });
//        return view;
//    }
//
//
//        }
//    }
//
//}

