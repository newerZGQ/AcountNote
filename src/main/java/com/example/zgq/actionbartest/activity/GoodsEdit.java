package com.example.zgq.actionbartest.activity;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.db.DataOperate;
import com.example.zgq.actionbartest.util.BipmapUtil;
import com.example.zgq.actionbartest.util.DateTools;
import com.example.zgq.actionbartest.consumption.SingleConsumption;
import com.example.zgq.actionbartest.util.PathTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 37902 on 2015/10/16.
 */
public class GoodsEdit extends AppCompatActivity{

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


//    相机拍照后返回的照片数据
    private Uri imageUri;
//    保存的照片完整路径
    private String fileName;
//    输入文件流，用于读取照片
    private  FileInputStream fis;

    public void createView(){
        //        加入toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_goods_edit);
        toolbar.setTitle("编辑");
        toolbar.showOverflowMenu();
        setSupportActionBar(toolbar);
        //       实现各个组件
        pictureEdit = (ImageView) findViewById(R.id.goods_picture);
        priceEdit = (TextView) findViewById(R.id.price_edit);
        detailEdit = (TextView) findViewById(R.id.detail_edit);
        Button clothing = (Button) findViewById(R.id.clothing);
        Button food = (Button) findViewById(R.id.food);
        Button living = (Button) findViewById(R.id.living);
        Button transportation = (Button) findViewById(R.id.transportation);
        ImageButton happinessImageButton1 = (ImageButton) findViewById(R.id.happiness_imagebutton1);
        ImageButton happinessImageButton2 = (ImageButton) findViewById(R.id.happiness_imagebutton2);
        ImageButton happinessImageButton3 = (ImageButton) findViewById(R.id.happiness_imagebutton3);
        ImageButton happinessImageButton4 = (ImageButton) findViewById(R.id.happiness_imagebutton4);
        ImageButton happinessImageButton5 = (ImageButton) findViewById(R.id.happiness_imagebutton5);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.clothing :
                        goodsLable = "衣";
                        break;
                    case R.id.food :
                        goodsLable = "食";
                        break;
                    case R.id.living :
                        goodsLable = "住";
                        break;
                    case R.id.transportation :
                        goodsLable = "行";
                        break;
                    case R.id.happiness_imagebutton1 :
                        goodsHappiness = 1;
                        break;
                    case R.id.happiness_imagebutton2 :
                        goodsHappiness = 2;
                        break;
                    case R.id.happiness_imagebutton3 :
                        goodsHappiness = 3;
                        break;
                    case R.id.happiness_imagebutton4 :
                        goodsHappiness = 4;
                        break;
                    case R.id.happiness_imagebutton5 :
                        goodsHappiness = 5;
                        break;
                }
            }
        };
        clothing.setOnClickListener(listener);
        food.setOnClickListener(listener);
        living.setOnClickListener(listener);
        transportation.setOnClickListener(listener);
        happinessImageButton1.setOnClickListener(listener);
        happinessImageButton2.setOnClickListener(listener);
        happinessImageButton3.setOnClickListener(listener);
        happinessImageButton4.setOnClickListener(listener);
        happinessImageButton5.setOnClickListener(listener);

        priceEdit.setCursorVisible(false);

    }

    public void takePhoto(){

//        打开输出文件流并创建jpg文件
            date = DateTools.getDate(DateTools.DETAIL_TIME);
            imageId = date + ".jpg";
            File outputImage = new File(PathTools.getPath() + "/" + imageId);
//            Log.d("path", PathTools.getPath() + imageId);
            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageUri = Uri.fromFile(outputImage);
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),imageUri);
//         打开相机,拍照结果给imageUri
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, 1);
        }
    public void setContent(SingleConsumption singleConsumption){
        priceEdit.setText(""+ singleConsumption.getPrice());
        detailEdit.setText(singleConsumption.getDetail());

        String fileName = PathTools.getPath()+ "/" + singleConsumption.getImageId();
        try {
            fis = new FileInputStream(fileName);
            pictureEdit.setImageBitmap(BipmapUtil.ZoomBipmap(fis, 16));
            fis.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_edit);

        createView();
        //        提取homepage传过来的TAKE_PHOTO,判断是否打开相机
        isInitial = getIntent().getBooleanExtra("isInitial",true);
        if (isInitial == true) {
            takePhoto();
        }

        if (isInitial == false) {
            singleConsumption = (SingleConsumption) getIntent().getSerializableExtra("singleConsumption");
            setContent(singleConsumption);
        }
    }
//       创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_goods_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_button) {
            if (isInitial == true) {
                goodsPrice = Double.valueOf((priceEdit.getText().toString()));
                goodsDetail = detailEdit.getText().toString();
                SingleConsumption single = new SingleConsumption(goodsPrice, goodsLable, date, goodsHappiness, goodsDetail, imageId);
                DataOperate.saveGoods(single);
                DataOperate.addSingleCon(single);
                Intent intent = new Intent(this, HomePageActivity.class);
                startActivity(intent);
                return true;
            }else {
                goodsPrice = Double.valueOf((priceEdit.getText().toString()));
                goodsDetail = detailEdit.getText().toString();
                SingleConsumption single = new SingleConsumption(goodsPrice, this.singleConsumption.getLable(), this.singleConsumption.getDate(), this.singleConsumption.getHappines(), goodsDetail, this.singleConsumption.getImageId());
                DataOperate.changeSingle(singleConsumption,single);
                Intent intent = new Intent(this, GoodsShow.class);
                intent.putExtra("singleConsumption1",single);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
//    相机返回后执行显示照片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        fileName = PathTools.getPath()+ "/" + imageId;
        try {
            fis = new FileInputStream(fileName);
            pictureEdit.setImageBitmap(BipmapUtil.ZoomBipmap(fis, 16));
            fis.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isInitial == true) {
                Intent intent = new Intent(this, HomePageActivity.class);
                startActivity(intent);
                return true;
            }else {
                Intent intent = new Intent(this, GoodsShow.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
