package com.taiwado.taiwado;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.taiwado.taiwado.MainActivity.username;

public class CalenderActivity extends AppCompatActivity implements DateActivity.NewCaledarListener {
private static int[] holiday;
    HolidayRepo holidayRepo = new HolidayRepo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        DateActivity calendar = (DateActivity) findViewById(R.id.newCalendar);
        calendar.Listener = this;
        username = getIntent().getStringExtra("username");
        //holidayRepo.queryHoliday(username,"06");

    }
    public String getHoliday(int day){
        String objectId = null;

        return objectId;
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
}
