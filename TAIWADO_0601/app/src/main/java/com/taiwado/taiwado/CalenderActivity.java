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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.taiwado.taiwado.MainActivity.username;

public class CalenderActivity extends AppCompatActivity implements DateActivity.NewCaledarListener {
    public static boolean isHoliday = false;
    public static String[] Kyuuka = new String[50];
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        DateActivity calendar = (DateActivity) findViewById(R.id.newCalendar);
        calendar.Listener = this;
        init();
        LocalDataRepo repo = new LocalDataRepo(this);

        ArrayList<HashMap<String, String>> localdatalist =  repo.getLocaldataList(username,getMonth(),getYear());
        for (HashMap<String,String> map : localdatalist)
            for (Map.Entry<String, String> entry : map.entrySet())
                if (entry.getKey() != " ") {
                    //Kyuuka[Integer.parseInt(entry.getKey())] = null;
                    if (!entry.getValue().isEmpty()&& !entry.getValue().equals(" ")){
                        Kyuuka[Integer.parseInt(entry.getKey())] = entry.getValue();
                    }
                }
    }

    public void init(){
        for (int i = 0; i <50; i++){
            Kyuuka[i] = null;
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
        CalenderActivity.this.finish();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getYear(){
        String str = null;
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        str = String.valueOf(cal.get(Calendar.YEAR));

        return str;
    }

}
