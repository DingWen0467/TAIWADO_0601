package com.taiwado.taiwado;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class TimeActivity extends AppCompatActivity {

    private static final int REQUEST_TIME = 2;
    private static int timecount = 0;
    private static String TimeObjectID = null;
    private static String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        Bmob.initialize(this, "396d004b9ddb44265f799ad3d9c7ea5d");
        username = getIntent().getStringExtra("username");
    }

    @SuppressWarnings("NewApi")
    public void doClickOk(View v) {

        switch (v.getId()) {
            case R.id.button_timerecord:
                TextView text_timeinfo = (TextView) findViewById(R.id.text_timeinfo);
                text_timeinfo.setText(nowTime());
                timeRecord();
                Intent intent = new Intent();
                intent.putExtra("timerecord",setTimeInfo());
                intent.putExtra("timeaddinfo",setTimeAdd());
                TimeActivity.this.setResult(REQUEST_TIME, intent);
                TimeActivity.this.finish();
                break;

            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void timeRecord() {
        if (timecount == 0) {
            createTimeRecord(getIntent().getStringExtra("username"), nowTime(), nowDate());

            timecount++;

        } else {
            updateTimeRecord(username, nowTime());
        }

    }

    private void createTimeRecord(final String username, String time, String date) {
        final String[] TimeObjectID = {null};
        final TimeRecord tp1 = new TimeRecord();
        tp1.setUsername(username);
        tp1.setTimeIn(time);
        tp1.setTimeDate(date);
        tp1.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    TimeObjectID[0] = tp1.getObjectId();
                    setLocalObjectID(username, TimeObjectID[0]);
                }
            }
        });
    }

    private void setLocalObjectID(String username, String TimeReObjectID) {
        SharedPreferences TR;
        SharedPreferences.Editor edtior = null;
        TimeObjectID = TimeReObjectID;
        TR = getSharedPreferences("TimeReObjectID", MODE_PRIVATE);
        edtior.putString(username, TimeReObjectID);
        edtior.commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateTimeRecord(String username, String time) {
        TimeRecord timeRecord = new TimeRecord();
        //判断出勤，退勤
        if (TimeObjectID == null) {
            timeRecord.setTimeIn(time);
            timeRecord.setTimeDate(nowDate());
        } else {
            timeRecord.setTimeOut(time);
            timeRecord.setTimeDate(nowDate());
        }
        //更新DB  userInfo
        timeRecord.update(TimeObjectID, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob", "OK");
                } else {
                    Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.N)
    public String nowTime() {
        String str = null;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        str = formatter.format(curDate);
        return str;
    }

    public String nowDate() {
        //取得当前系统日期
        String nowDate = null;
        SimpleDateFormat formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            formatter = new SimpleDateFormat("yyyy年MM月dd日");
        }
        Date curDate = new Date(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            nowDate = formatter.format(curDate);
        }
        return nowDate;
    }
    public String setTimeInfo(){
        String strTime = null;

        if (TimeObjectID == null){
            strTime = "　出勤時間：" + nowTime();

        }else {
            strTime = "　退勤時間：" + nowTime();

        }
        return strTime;
    }
    public String setTimeAdd (){
        String strAddInfo = null;

        if (TimeObjectID == null){

            strAddInfo = "　出勤地点：" + getAddress();

        }else {

            strAddInfo = "　退勤地点：" + getAddress();

        }

        return strAddInfo;
    }


    public String getAddress() {
        String Address = null;
        getLocation locationActivity = new getLocation();
        Address = locationActivity.nowLocationName;
        if (Address == null) {
            Address = "Failed to get Address!";
        } else {
            setAddress(Address);
        }
        return Address;
    }

    public void setAddress(String address) {
        UserInfo userInfo = new UserInfo();
        if (TimeObjectID == null) {
            userInfo.setTimeINAddress(address);
        } else {
            userInfo.setTimeINAddress(address);
        }

    }
}
