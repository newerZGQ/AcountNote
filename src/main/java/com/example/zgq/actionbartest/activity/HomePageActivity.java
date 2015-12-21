package com.example.zgq.actionbartest.activity;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.zgq.actionbartest.db.DataOperate;
import com.example.zgq.actionbartest.fragment.ConsumListFrag;
import com.example.zgq.actionbartest.R;
import com.example.zgq.actionbartest.fragment.CountFragment;
import com.example.zgq.actionbartest.fragment.HomeBottomFrag;
import com.example.zgq.actionbartest.util.DateTools;

public class HomePageActivity extends AppCompatActivity {
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    private Fragment fragmentList;
    private Fragment countFrag;
    private Fragment fragmentBottom;

    private android.support.v7.widget.Toolbar toolbar;

    FragmentManager fragmentManager;


    CharSequence title;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x1233){
                onResume();
            }
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
        toolbar = (Toolbar) findViewById(R.id.hometoolbar);
        toolbar.setTitle("主页");
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        fragmentList = fragmentManager.findFragmentById(R.id.fragmentList);
        fragmentBottom = fragmentManager.findFragmentById(R.id.fragmentBottom);
        countFrag = fragmentManager.findFragmentById(R.id.fragmentList);
        if (fragmentBottom == null){
            fragmentBottom  = HomeBottomFrag.newInstance(new HomeBottomFrag.ChangeListener() {
                @Override
                public void click(View view) {
                    switch(view.getId()){
                        case R.id.tohome:
                            fragmentManager.beginTransaction()
                                    .replace(R.id.fragmentList, fragmentList)
                                    .commit();
                            break;
                        case R.id.tocount:
                            if (countFrag == null){
                                countFrag = new CountFragment();
                            }
                            fragmentManager.beginTransaction()
                                    .replace(R.id.fragmentList, countFrag)
                                    .commit();
                            break;
                    }
                }
            });
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentBottom, fragmentBottom)
                    .commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fragmentList == null) {
            fragmentList = ConsumListFrag.getInstance(this,handler);
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentList, fragmentList)
                    .commit();
        }else{
            fragmentList.onResume();
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
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.date_item) {

            DatePickerDialog dp = new DatePickerDialog(HomePageActivity.this,android.R.style.Theme_Holo_Panel,new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    monthOfYear += 1;
                    ConsumListFrag fragment = (ConsumListFrag)getSupportFragmentManager().findFragmentById(R.id.fragmentList);
                    fragment.setDate(year,monthOfYear,dayOfMonth,true);

                    String mMonth = ""+monthOfYear;
                    if (0<monthOfYear && monthOfYear <10){
                        mMonth = "0"+monthOfYear;
                    }

                    String tableName = "c" + year+mMonth;
                    Log.d("--",tableName);
                    if (!DataOperate.isExits(tableName)){
                        DataOperate.creatTable(tableName,DataOperate.db);
                    }
                    DataOperate.changeData(tableName);
                    onResume();
                }
            }, Integer.parseInt(DateTools.getYear())-1,Integer.parseInt(DateTools.getMonth()),Integer.parseInt(DateTools.getDay()));
            dp.show();
        }
        return super.onOptionsItemSelected(item);
    }
}

