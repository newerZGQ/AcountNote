package com.example.zgq.actionbartest.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.activity.GoodsEdit;
import com.example.zgq.actionbartest.util.BipmapUtil;
import com.example.zgq.actionbartest.consumption.SingleConsumption;
import com.example.zgq.actionbartest.util.ConsumptionGenerator;
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

    private Button editButton;

    private Bitmap bitmap;

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
        editButton = (Button) view.findViewById(R.id.edit);

        editButton.setOnClickListener(this);
        priceShow.setText(""+ singleConsumption.getPrice());
        detailShow.setText(singleConsumption.getDetail());

        String fileName = PathTools.getPath()+ "/" + singleConsumption.getImageId();
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
//    @Override
    public void onClick(View v) {
//                Log.d("click","---");
        Intent intent = new Intent(getActivity(), GoodsEdit.class);
        intent.putExtra("singleConsumption", singleConsumption);
        intent.putExtra("isInitial",false);
        startActivity(intent);
//        Log.d("click","---");

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
        view = inflater.inflate(R.layout.goods_fragment,container,false);
        goodsShow(singleConsumption);
        return  view;
    }
}
