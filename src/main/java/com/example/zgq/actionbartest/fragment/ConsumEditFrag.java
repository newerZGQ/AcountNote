package com.example.zgq.actionbartest.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.activity.GoodsShow;
import com.example.zgq.actionbartest.activity.HomePageActivity;
import com.example.zgq.actionbartest.consumption.SingleConsumption;
import com.example.zgq.actionbartest.db.DataOperate;
import com.example.zgq.actionbartest.util.BipmapUtil;
import com.example.zgq.actionbartest.util.DateTools;
import com.example.zgq.actionbartest.util.PathTools;
import com.example.zgq.actionbartest.util.ScreenDimen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by 37902 on 2015/12/21.
 */
public class ConsumEditFrag extends Fragment {
    private File file;

    private Boolean isInitial;

    private SingleConsumption singleConsumption;
    //    显示照片控件
    private ImageView pictureEdit;
    //    编辑价钱控件
    private TextView priceEdit;
    //    价钱,默认-1，判断为-1时不显示
    private double goodsPrice = -1;

    //    标签
    private String goodsLable = null;

    //    幸福度,默认值10，后面判断大于等于10的话不显示
    private int goodsHappiness = 10;
    //    编辑细节控件
    private TextView detailEdit;
    //    细节
    private String goodsDetail = null;
    //    拍出照片的名字
    private String imageId = null;
    //    拍出照片的时间
    private String date = null;

    private Bitmap bitmap;
    //    相机拍照后返回的照片数据
    private Uri imageUri;
    //    保存的照片完整路径
    private String fileName;
    //    输入文件流，用于读取照片
    private FileInputStream fis;
    private View view;
    private static Context context;


    public ConsumEditFrag() {
        super();
    }

    public static ConsumEditFrag newInstance(Context context1, SingleConsumption singleConsumption, boolean isInitial) {
        Bundle bundle = new Bundle();
        context = context1;
        bundle.putSerializable("singleConsumption", singleConsumption);
        bundle.putBoolean("isInitial", isInitial);
        ConsumEditFrag consumEditFrag = new ConsumEditFrag();
        consumEditFrag.setArguments(bundle);
        return consumEditFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singleConsumption = (SingleConsumption) getArguments().getSerializable("singleConsumption");
        isInitial = getArguments().getBoolean("isInitial");
        if (isInitial == true) {
            ((Toolbar) getActivity().findViewById(R.id.toolbar_goods_edit)).setTitle("新建消费记录");
            takePhoto();
        }else{
            ((Toolbar) getActivity().findViewById(R.id.toolbar_goods_edit)).setTitle("修改消费记录");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isInitial == true) {
            ((Toolbar) getActivity().findViewById(R.id.toolbar_goods_edit)).setTitle("新建消费记录");
        }else{
            ((Toolbar) getActivity().findViewById(R.id.toolbar_goods_edit)).setTitle("修改消费记录");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.goods_edit_frag, container, false);
        createView();
        if (isInitial == false) {
            setContent(singleConsumption);
        }
        return view;
    }

    public void createView() {
        //       实现各个组件
        pictureEdit = (ImageView) view.findViewById(R.id.goods_picture);
        priceEdit = (TextView) view.findViewById(R.id.price_edit);
        detailEdit = (TextView) view.findViewById(R.id.detail_edit);
        Button clothing = (Button) view.findViewById(R.id.clothing);
        Button food = (Button) view.findViewById(R.id.food);
        Button living = (Button) view.findViewById(R.id.living);
        Button transportation = (Button) view.findViewById(R.id.transportation);
        Button save = (Button) view.findViewById(R.id.save_button);
        ImageButton happinessImageButton1 = (ImageButton) view.findViewById(R.id.happiness_imagebutton1);
        ImageButton happinessImageButton2 = (ImageButton) view.findViewById(R.id.happiness_imagebutton2);
        ImageButton happinessImageButton3 = (ImageButton) view.findViewById(R.id.happiness_imagebutton3);
        ImageButton happinessImageButton4 = (ImageButton) view.findViewById(R.id.happiness_imagebutton4);
        ImageButton happinessImageButton5 = (ImageButton) view.findViewById(R.id.happiness_imagebutton5);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.clothing:
                        goodsLable = "衣";
                        break;
                    case R.id.food:
                        goodsLable = "食";
                        break;
                    case R.id.living:
                        goodsLable = "住";
                        break;
                    case R.id.transportation:
                        goodsLable = "行";
                        break;
                    case R.id.happiness_imagebutton1:
                        goodsHappiness = 1;
                        break;
                    case R.id.happiness_imagebutton2:
                        goodsHappiness = 2;
                        break;
                    case R.id.happiness_imagebutton3:
                        goodsHappiness = 3;
                        break;
                    case R.id.happiness_imagebutton4:
                        goodsHappiness = 4;
                        break;
                    case R.id.happiness_imagebutton5:
                        goodsHappiness = 5;
                        break;
                    case R.id.save_button:
                        if (isInitial == true) {
                            goodsPrice = Double.valueOf((priceEdit.getText().toString()));
                            goodsDetail = detailEdit.getText().toString();
                            SingleConsumption single = new SingleConsumption(goodsPrice, goodsLable, date, goodsHappiness, goodsDetail, imageId);
                            DataOperate.saveGoods(single);
                            DataOperate.addSingleCon(single);
                            Intent intent = new Intent(getActivity(), HomePageActivity.class);
                            startActivity(intent);
                        } else {
                            goodsPrice = Double.valueOf((priceEdit.getText().toString()));
                            goodsDetail = detailEdit.getText().toString();
                            SingleConsumption single = new SingleConsumption(goodsPrice, singleConsumption.getLable(), singleConsumption.getDate(), singleConsumption.getHappines(), goodsDetail, singleConsumption.getImageId());
                            DataOperate.changeSingle(singleConsumption, single);
                            Intent intent = new Intent(getActivity(), GoodsShow.class);
                            intent.putExtra("singleConsumption1", single);
                            startActivity(intent);
                        }
                        break;
                }
            }
        };
        clothing.setOnClickListener(listener);
        food.setOnClickListener(listener);
        living.setOnClickListener(listener);
        transportation.setOnClickListener(listener);
        save.setOnClickListener(listener);
        happinessImageButton1.setOnClickListener(listener);
        happinessImageButton2.setOnClickListener(listener);
        happinessImageButton3.setOnClickListener(listener);
        happinessImageButton4.setOnClickListener(listener);
        happinessImageButton5.setOnClickListener(listener);

        priceEdit.setCursorVisible(false);
    }

    public void setContent(SingleConsumption singleConsumption) {
        priceEdit.setText("" + singleConsumption.getPrice());
        detailEdit.setText(singleConsumption.getDetail());

        String fileName = PathTools.getPath(singleConsumption.getDate().substring(0, 6)) + "/" + singleConsumption.getImageId();
        try {
            fis = new FileInputStream(fileName);
            pictureEdit.setImageBitmap(BipmapUtil.ZoomBipmap(fis, 16));
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void takePhoto() {
        //        打开输出文件流并创建jpg文件
        date = DateTools.getDate(DateTools.DETAIL_TIME);
        imageId = date + ".jpg";
        file = new File(PathTools.getPath(DateTools.getDate(true)) + "/" + imageId);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageUri = Uri.fromFile(file);
        //         打开相机,拍照结果给imageUri
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
// 检测sd是否可用
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                return;
            }

            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(fis);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            bitmap = BipmapUtil.centerSquareScaleBitmap(bitmap, ScreenDimen.getScreenWidth(context));

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
