package com.taiwado.taiwado;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.taiwado.taiwado.R.array.kyuuka;
import static com.taiwado.taiwado.R.array.time;

public class KyuukaActivity extends AppCompatActivity {

    private Spinner spinnerTimeIn;
    private Spinner spinnerTimeOut;
    private Spinner spinnerKyuuka;
    private static String username = null;
    private static String timeInValue = null;
    private static String timeOutValue = null;
    private static String holidayValue = null;
    private static String holidayID = null;
    private HolidayData holidayData = new HolidayData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyuuka2);
        username = getIntent().getStringExtra("username");
        String date = setDate();
        TextView textTitle = (TextView)findViewById(R.id.kyuuka_title);
        textTitle.setText("出勤予定-" + date);
        updateSpinnertime();
        updateKyuuka();
    }
    private String setDate(){
        String str = null;
        String date = nowDate();
        if (getIntent().getStringExtra("main") != null){
            str = date;
        }else {
            str = getIntent().getStringExtra("date");
        }

        return str;
    }

    public void doClick(View view){
        switch (view.getId()){
            case R.id.text_timeIn:
                updateSpinnertime();
                spinnerTimeIn.setEnabled(true);
                spinnerTimeOut.setEnabled(true);
                spinnerKyuuka.setSelection(0);
                spinnerKyuuka.setEnabled(false);
                //Toast.makeText(KyuukaActivity.this, "ok", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_timeOut:
                updateSpinnertime();
                spinnerTimeIn.setEnabled(true);
                spinnerTimeOut.setEnabled(true);
                spinnerKyuuka.setSelection(0);
                spinnerKyuuka.setEnabled(false);
                //Toast.makeText(KyuukaActivity.this, "ok", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_kyuuka:
                spinnerKyuuka.setEnabled(true);
                updateKyuuka();
                spinnerTimeIn.setSelection(0);
                spinnerTimeOut.setSelection(0);
                spinnerTimeIn.setEnabled(false);
                spinnerTimeOut.setEnabled(false);
                //Toast.makeText(KyuukaActivity.this, "ok", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void doClickOk(View v) {
        switch (v.getId()) {
            case R.id.kyuuka_ok:
                createHolidayData(username,setDate(),setDay());

                break;

            default:
                break;
        }

    }
    public int setDay(){
        int day = 0;
        String str = null;
        String strDay = null;
        String date = nowDate();
        if (getIntent().getStringExtra("main") != null){
            str = date;
        }else {
            str = getIntent().getStringExtra("date");
        }
        str = str.substring(9,10);
        day = Integer.valueOf(str).intValue();

        return day;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createHolidayData(String username, String date,int day) {
        final String[] holidayobjectId = {null};
        //day = (Integer) date.substring(1,1);
        holidayData.setUsername(username);
        holidayData.setDate(date);
        holidayData.setDay(day);
        if (timeInValue != null) {
            holidayData.setTimeIn(timeInValue);
        }
        if (timeOutValue != null) {
            holidayData.setTimeOut(timeOutValue);
        }
        if (holidayValue != null) {
            holidayData.setHoliday("holiday");
            holidayData.setHolidayType(holidayValue);
        } else {
            holidayData.setWork("work");
        }
        holidayData.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    holidayobjectId[0] = holidayData.getObjectId();
                    holidayID = holidayobjectId[0];
                }
            }
        });
    }

    public void doClickCanccel(View view) {
        Toast.makeText(KyuukaActivity.this, "取消", Toast.LENGTH_SHORT).show();
    }

    private void updateSpinnertime(){
        spinnerTimeIn =(Spinner)findViewById(R.id.search_timeIn);
        final String[] timeInItems = getResources().getStringArray(time);
        ArrayAdapter<String> adapterIn=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, timeInItems);
        //Formatを設定
        adapterIn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeIn .setAdapter(adapterIn);
        spinnerTimeIn.setSelection(4);
        final int[] selectedTimeIn = {spinnerTimeIn.getSelectedItemPosition()};
        spinnerTimeIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressWarnings("WrongConstant")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeInValue = (String)spinnerTimeIn.getItemAtPosition(position);
                //Toast.makeText(KyuukaActivity.this,"検索店舗：" +timeInItems[position], 2000).show();
                if (spinnerKyuuka.getSelectedItemPosition() == 1) {
                    if (selectedTimeIn[0] != position && position != 0) {
                        spinnerKyuuka.setSelection(0);
                        spinnerKyuuka.setEnabled(false);
                    }
                }
                //selectedTimeIn[0] = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTimeOut = (Spinner)findViewById(R.id.search_timeOut);
        final  String[] timeOutItems = getResources().getStringArray(time);
        ArrayAdapter<String> adapterOut = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,timeOutItems);
        adapterOut.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeOut.setAdapter(adapterOut);
        spinnerTimeOut.setSelection(13);
        final int[] selectedTimeOut = {spinnerTimeOut.getSelectedItemPosition()};
        spinnerTimeOut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeOutValue = (String)spinnerTimeIn.getItemAtPosition(position);
                if (selectedTimeOut[0] != position && position != 0){
                    spinnerKyuuka.setSelection(0);
                    spinnerKyuuka.setEnabled(false);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updateKyuuka(){
        spinnerKyuuka = (Spinner)findViewById(R.id.search_kyuuka);
        final  String[] timeKyuukaItems = getResources().getStringArray(kyuuka);
        ArrayAdapter<String> adapterKyuuka = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,timeKyuukaItems);
        adapterKyuuka.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKyuuka.setAdapter(adapterKyuuka);
        spinnerKyuuka.setSelection(0);
        final int[] selectedKyuuka = {spinnerKyuuka.getSelectedItemPosition()};
        spinnerKyuuka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                holidayValue = (String)spinnerTimeIn.getItemAtPosition(position);
                if (selectedKyuuka[0] != position && position == 1){
                    spinnerTimeIn.setSelection(0);
                    spinnerTimeOut.setSelection(0);
                    spinnerTimeIn.setEnabled(false);
                    spinnerTimeOut.setEnabled(false);
                }else {
                    spinnerTimeIn.setSelection(4);
                    spinnerTimeOut.setSelection(13);
                    spinnerTimeIn.setEnabled(true);
                    spinnerTimeOut.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
}
