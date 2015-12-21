package com.example.zgq.actionbartest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.activity.GoodsEdit;
import com.example.zgq.actionbartest.db.DataOperate;

/**
 * Created by 37902 on 2015/12/20.
 */
public class ShowBottom extends Fragment{
    private ImageButton edit;
    public ShowBottom(){
        super();
    }
    public static ShowBottom newInstance(){
        return new ShowBottom();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consumshow_bottom,container,false);
        edit = (ImageButton) view.findViewById(R.id.show_edit);
        Log.d(",,,,3","ininin");
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(",,,,33","ininin");
                int a = ((ViewPager)getActivity().findViewById(R.id.pager)).getCurrentItem();
                Intent intent = new Intent(getActivity(), GoodsEdit.class);
                intent.putExtra("singleConsumption", DataOperate.selectedMonth.getSingleConsumptions().get(a));
                intent.putExtra("isInitial", false);
                startActivity(intent);
            }
        });
        return view;
    }
}
