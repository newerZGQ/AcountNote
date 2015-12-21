package com.example.zgq.actionbartest.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.activity.GoodsEdit;
import com.example.zgq.actionbartest.db.DataOperate;
import com.example.zgq.actionbartest.util.BipmapUtil;
import com.example.zgq.actionbartest.consumption.SingleConsumption;
import com.example.zgq.actionbartest.util.PathTools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 37902 on 2015/11/28.
 */
public class GoodsShowFragment extends Fragment implements View.OnClickListener{


    private SingleConsumption singleConsumption;
    //    文件流
    private FileInputStream fis;

    private View view;
    //    pictureShow
    private ImageView pictureShow;
    //      price显示
    private TextView priceShow;
    //    date显示
    private TextView dateShow;
    //    lable显示
    private ImageView lableShow;
    //    happiness显示
    private ImageView happinessImage1;
    private ImageView happinessImage2;
    private ImageView happinessImage3;
    private ImageView happinessImage4;
    private ImageView happinessImage5;
    //    detail显示
    private TextView detailShow;

    private ImageButton editButton;

    private Bitmap bitmap;

    private Toolbar toolbar;

    public void goodsShow(SingleConsumption singleConsumption){
        pictureShow = (ImageView) view.findViewById(R.id.picture_show);
        priceShow = (TextView) view.findViewById(R.id.price_show);
        lableShow = (ImageView) view.findViewById(R.id.lable_show);
        happinessImage1 = (ImageView) view.findViewById(R.id.happiness_imageview1);
        happinessImage2 = (ImageView) view.findViewById(R.id.happiness_imageview2);
        happinessImage3 = (ImageView) view.findViewById(R.id.happiness_imageview3);
        happinessImage4 = (ImageView) view.findViewById(R.id.happiness_imageview4);
        happinessImage5 = (ImageView) view.findViewById(R.id.happiness_imageview5);
        detailShow = (TextView) view.findViewById(R.id.detail_show);
        editButton = (ImageButton) (getActivity().findViewById(R.id.show_edit));
        editButton.setOnClickListener(this);
        priceShow.setText(""+ singleConsumption.getPrice());
        detailShow.setText(singleConsumption.getDetail());

        String fileName = PathTools.getPath(singleConsumption.getDate().substring(0,6))+ "/" + singleConsumption.getImageId();
        try {
            fis = new FileInputStream(fileName);
            pictureShow.setImageBitmap(BipmapUtil.ZoomBipmap(fis, 6));
            fis.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
    @Override
    public void onClick(View v) {
        int a = ((ViewPager)getActivity().findViewById(R.id.pager)).getCurrentItem();
        Intent intent = new Intent(getActivity(), GoodsEdit.class);
        intent.putExtra("singleConsumption", DataOperate.selectedMonth.getSingleConsumptions().get(a));
        intent.putExtra("isInitial", false);
        startActivity(intent);

    }
    public GoodsShowFragment(){
        super();
    }
    public static GoodsShowFragment newInstance(SingleConsumption singleConsumption){
        Bundle args = new Bundle();
        args.putSerializable("singleConsumption", singleConsumption);
        GoodsShowFragment goodsShowFragment = new GoodsShowFragment();
        goodsShowFragment.setArguments(args);
        return goodsShowFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singleConsumption = (SingleConsumption) getArguments().getSerializable("singleConsumption");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.goodsshowfrag,container,false);
        goodsShow(singleConsumption);
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Toolbar)getActivity().findViewById(R.id.showtoolbar)).setTitle(singleConsumption.getDate().substring(0,8));
    }
}
