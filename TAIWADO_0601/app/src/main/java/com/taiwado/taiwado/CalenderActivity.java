package com.taiwado.taiwado;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.taiwado.taiwado.BaseActivity.loge;
import static com.taiwado.taiwado.MainActivity.username;

public class CalenderActivity extends AppCompatActivity implements DateActivity.NewCaledarListener {
private static int[] holiday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        DateActivity calendar = (DateActivity) findViewById(R.id.newCalendar);
        calendar.Listener = this;
        username = getIntent().getStringExtra("username");
    }

    @Override
    public View findViewById(@IdRes int id) {
        return super.findViewById(id);
    }
    public void onItemLongPress(Date day){
        DateFormat df = SimpleDateFormat.getDateInstance();
        Toast.makeText(this,df.format(day),Toast.LENGTH_LONG).show();
        Intent intentKyuuka = new Intent(CalenderActivity.this,KyuukaActivity.class);
        String date = (String) df.format(day);
        intentKyuuka.putExtra("date",date);
        intentKyuuka.putExtra("username",username);
        intentKyuuka.putExtra("calendar","calendar");
        startActivity(intentKyuuka);
    }
    private void setHoliday(String username){
        int i = 0;
        for (i = 0; i < 30; i++){
            if (queryObjects(username,i) != 0){
                holiday[i] = i;
            }
        }
        Log.v("debug", String.valueOf(holiday[5]));

    }

    private  int queryObjects(String username,int day){
        final int[] n = {0};
        BmobQuery<HolidayData> bmobQuery = new BmobQuery<HolidayData>();
        bmobQuery.addWhereEqualTo("username", username);
        bmobQuery.addWhereEqualTo("holiday","holiday");
        bmobQuery.addWhereEqualTo("day",day);
        bmobQuery.setLimit(2);
        bmobQuery.order("createdAt");
        //先判断是否有缓存
        boolean isCache = bmobQuery.hasCachedResult(HolidayData.class);
        //if(isCache){
        //bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取。
        //}else{
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
        //}

        bmobQuery.findObjects(new FindListener<HolidayData>() {

            @Override
            public void done(List<HolidayData> object, BmobException e) {
                if(e==null){

                    for (HolidayData holidayData : object) {
                        n[0] = holidayData.getDay();
                    }

                }else{
                    loge(e);
                }
            }
        });
        return n[0];
    }

}
