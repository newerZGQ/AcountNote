package com.example.zgq.actionbartest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.activity.GoodsEdit;

/**
 * Created by 37902 on 2015/12/6.
 */
public class HomeBottomFrag extends Fragment{
    private ImageButton takePhoto;
    public HomeBottomFrag(){
        super();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_bottom,container,false);
        takePhoto = (ImageButton) view.findViewById(R.id.take_photo);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoodsEdit.class);
                intent.putExtra("isInitial", true);
                startActivity(intent);
            }
        });
        return view;
    }
}
