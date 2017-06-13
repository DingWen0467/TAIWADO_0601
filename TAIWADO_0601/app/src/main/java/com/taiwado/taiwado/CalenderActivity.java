package com.taiwado.taiwado;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.taiwado.taiwado.MainActivity.ObjectId;
import static com.taiwado.taiwado.MainActivity.username;

public class CalenderActivity extends AppCompatActivity implements DateActivity.NewCaledarListener {
    public static boolean isHoliday = false;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        DateActivity calendar = (DateActivity) findViewById(R.id.newCalendar);
        calendar.Listener = this;
        username = getIntent().getStringExtra("username");

        LocalDataRepo repo = new LocalDataRepo(this);
        LocalData localData = new LocalData();
        //localData = repo.getLocalDataByID(ObjectId,getMonth(),getDay());
        //checkWork(localData);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkWork(LocalData localData){
        if (localData.objectID == ObjectId && localData.day == getDay() && localData.month == getMonth()){
            if (localData.holiday == null){
                isHoliday = false;
            }else {
                isHoliday = true;
            }
        }
    }

    @Override
    public View findViewById(@IdRes int id) {
        return super.findViewById(id);
    }
    public void onItemLongPress(Date day){
        DateFormat df = SimpleDateFormat.getDateInstance();
        Toast.makeText(this,df.format(day),Toast.LENGTH_LONG).show();
        Intent intentKyuuka = new Intent(CalenderActivity.this,KyuukaActivity.class);
        String date = nowDate(day);

        intentKyuuka.putExtra("date",date);
        intentKyuuka.putExtra("username",username);
        intentKyuuka.putExtra("calendar","calendar");
        startActivity(intentKyuuka);
    }

    public String nowDate(Date day) {
        //取得当前系统日期
        String nowDate = null;
        android.icu.text.SimpleDateFormat formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            formatter = new android.icu.text.SimpleDateFormat("yyyy年MM月dd日");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            nowDate = formatter.format(day);
        }
        return nowDate;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public int getDay(){
        int day = 0;
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        day = cal.get(Calendar.DAY_OF_MONTH);

        return day;
    }
    public String getMonth(){
        String str = null;
        String date = nowDate();
        str = date;
        str = str.substring(5,7);

        return str;
    }
    public String nowDate() {
        //取得当前系统日期
        String nowDate = null;
        android.icu.text.SimpleDateFormat formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            formatter = new android.icu.text.SimpleDateFormat("yyyy年MM月dd日");
        }
        Date curDate = new Date(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            nowDate = formatter.format(curDate);
        }
        return nowDate;
    }
}
