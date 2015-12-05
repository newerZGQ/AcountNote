package com.example.zgq.actionbartest.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.zgq.actionbartest.fragment.GoodsListFragment;
import com.example.zgq.actionbartest.R;

public class HomePageActivity extends FragmentActivity {

//    点击拍照
    private ImageButton takePhoto;
//    报表按钮
    private ImageButton acount;
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    private Fragment fragment;

    FragmentManager fragmentManager;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
//   显示listview的方法
//    public void listViewShow(){
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle((CharSequence) (String.valueOf(Acount.getCONSUMPTION())));
//        setSupportActionBar(toolbar);
//
//        db =DataOperate.db;
//        listView = (ListView)findViewById(R.id.list_view_goods);
//        myCursor = db.query(DataOperate.currentTable,new String[]{"id as _id","price","lable","date"},null,null,null,null,null);
//        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.list_item,myCursor,new String[]{"_id","price","lable","date"},new int[]{R.id.id_edit,R.id.price_edit,R.id.lable_edit,R.id.date_edit},0);
//        SimpleCursorAdapter.ViewBinder viewBinder = new SimpleCursorAdapter.ViewBinder(){
//            @Override
//            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
//                if (cursor.getColumnIndex("price")==columnIndex){
//                    TextView textView = (TextView)view;
//                    double a = cursor.getDouble(columnIndex);
//                    if (Constant.getLittleConsume()>a) {
//                        textView.setTextSize(27);
//                    }else if (Constant.getLittleConsume()<a && a<Constant.getLargeConsume()){
//                        textView.setTextSize(27);
//                    }else {
//                        textView.setTextSize(27);
//                    }
//                    textView.setText("￥"+a);
//                    return true;
//                }
//                if (cursor.getColumnIndex("date")==columnIndex){
//                    TextView textView = (TextView)view;
//                    String s = cursor.getString(columnIndex);
//                    String s1 = s.substring(8,13);
//                    textView.setTextSize(15);
//                    textView.setText(s1);
//                    return true;
//                }
//                return false;
//            }
//        };
//        listAdapter.setViewBinder(viewBinder);
//        listView.setAdapter(listAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView idEdit = (TextView)view.findViewById(R.id.id_edit);
//
//                String itemId = (idEdit.getText()).toString();
//
//                int position1 = Integer.valueOf(itemId);
//
//                Intent intent = new Intent(HomePageActivity.this, GoodsShow.class);
//                intent.putExtra("id",position1);
//                startActivity(intent);
//            }
//        });
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);

        takePhoto = (ImageButton) findViewById(R.id.take_photo);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, GoodsEdit.class);
                intent.putExtra("isInitial", true);
                startActivity(intent);
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (fragment == null) {
            fragment = new GoodsListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }else{
            getSupportFragmentManager().findFragmentById(R.id.fragmentContainer).onResume();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}

