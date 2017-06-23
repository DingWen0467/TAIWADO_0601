package com.taiwado.taiwado;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.taiwado.taiwado.MainActivity.username;
import static com.taiwado.taiwado.R.array.days;
import static com.taiwado.taiwado.R.array.months;
import static com.taiwado.taiwado.TrafficListActivity.RESULT_TRA;

public class TrafficSaveActivity extends AppCompatActivity {
    private Spinner spinnerMonth;
    private Spinner spinnerDay;
    private static boolean isUpdate = false;
    public static final int REQUEST_TRA = 1;
    private static int trafficID = 0;
    public static String trafficObjectID = null;
    private static String From = null;
    private static String To = null;
    private static int Cash = 0;
    private static String day = null;
    private static String month = null;
    private SharedPreferences pref;
    private SharedPreferences.Editor edtior;
    EditText editDate,edit_From,edit_To,edit_Cash;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_save);
        isUpdate = false;
        Toolbar toolbar = (Toolbar) findViewById(R.id.traffic_toolbar);
        setSupportActionBar(toolbar);
        init();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.traffic_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "  通勤費を保存します！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (isUpdate){
                    updateTrafficData();
                }else {
                    CloudTrafficInsert();
                }
            }
        });
        updateSpinnerMonth();
    }

    public void init(){
        editDate = (EditText)findViewById(R.id.edit_date);
        edit_From = (EditText)findViewById(R.id.edit_From);
        edit_To = (EditText)findViewById(R.id.edit_To);
        edit_Cash= (EditText)findViewById(R.id.edit_Cash);
        trafficID = 0;
        trafficObjectID = null;
    }

    public void updateTrafficID (){
        TrafficDataRepo repo = new TrafficDataRepo(this);
        if (trafficObjectID != null && trafficID != 0){
            repo.updateTrafficID(trafficObjectID,trafficID);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_trafficsave, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        updateTrafficID();
        Intent intenttraffic = new Intent(TrafficSaveActivity.this,TrafficListActivity.class);
        startActivityForResult(intenttraffic,REQUEST_TRA);

        return super.onOptionsItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    public boolean onContextItemSelected(MenuItem item){
        return super.onContextItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateSpinnerMonth(){
        int monthPosition;
        monthPosition = getMonth();

        spinnerMonth = (Spinner)findViewById(R.id.search_Month);
        final String[] monthItems = getResources().getStringArray(months);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,monthItems);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapterMonth);
        spinnerMonth.setSelection(monthPosition);
        month = (String)spinnerMonth.getItemAtPosition(monthPosition);
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressWarnings("WrongConstant")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = (String)spinnerMonth.getItemAtPosition(position);
                String edDate = null;
                edDate = getYear() + " 年 " + month +" 月 " + day +" 日 ";
                editDate.setText(edDate);
                //Toast.makeText(TrafficSaveActivity.this,"検索店舗：" +monthItems[position], 2000).show();
                }
                //selectedTimeIn[0] = position;
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        int dayPosition;
        dayPosition = getDay() - 1;
        spinnerDay = (Spinner)findViewById(R.id.search_Day);
        final String[] dayItems = getResources().getStringArray(days);
        ArrayAdapter<String> adapterDay = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dayItems);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapterDay);
        spinnerDay.setSelection(dayPosition);
        day = (String)spinnerDay.getItemAtPosition(dayPosition);
        final int[] selectedDay= {spinnerDay.getSelectedItemPosition()};
        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = (String)spinnerDay.getItemAtPosition(position);
                String edDate = null;
                edDate = getYear() + " 年 " + month +" 月 " + day +" 日 ";
                editDate.setText(edDate);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void CloudTrafficInsert(){

        From = edit_From.getText().toString().trim();
        To = edit_To.getText().toString().trim();
        Cash = Integer.parseInt(edit_Cash.getText().toString().trim());
        trafficID = insertTrafficData();
        insertTraffic(trafficID,username,From,To,Cash, Integer.parseInt(day),month,getYear(),nowDate());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int insertTrafficData(){
        TrafficDataRepo repo = new TrafficDataRepo(this);
        TrafficData trafficData = new TrafficData();
        trafficData.ID = setAutoID();
        trafficData.objectID = trafficObjectID;
        trafficData.uname = username;
        trafficData.begin = edit_From.getText().toString().trim();
        trafficData.end = edit_To.getText().toString().trim();
        trafficData.cash = Integer.parseInt(edit_Cash.getText().toString().trim());
        trafficData.day  = Integer.parseInt(day);
        trafficData.month = month;
        trafficData.year = getYear();
        trafficData.date = nowDate();

        return repo.insertTrafficData(trafficData);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateTrafficData(){
        TrafficDataRepo repo = new TrafficDataRepo(this);
        TrafficData trafficData = new TrafficData();
        trafficData.ID = trafficID;
        trafficData.begin = edit_From.getText().toString().trim();
        String begin = edit_From.getText().toString().trim();
        trafficData.end = edit_To.getText().toString().trim();
        String end = edit_To.getText().toString().trim();
        trafficData.cash = Integer.parseInt(edit_Cash.getText().toString().trim());
        int cash = Integer.parseInt(edit_Cash.getText().toString().trim());
        trafficData.day  = Integer.parseInt(day);

        trafficData.date = nowDate();
        repo.updateTrafficData(trafficData);

        CloudTrafficRepo cloudTrafficRepo = new CloudTrafficRepo();
       cloudTrafficRepo.updateCloudTraffic(trafficObjectID,Integer.parseInt(day),begin,end,cash,nowDate());
    }

    @Override
    public void onBackPressed() {
        updateTrafficID();
        CloseAllActivity.getInstance().finishActivity(this);
    }

    public String nowDate() {
        //取得当前系统日期
        String nowDate = null;
        SimpleDateFormat formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            formatter = new SimpleDateFormat("yyyyMMdd");
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int getMonth(){
        int month = 0;
        Date curDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(curDate);
        month = c.get(java.util.Calendar.MONTH);

        return month;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int getDay(){
        int Day = 0;
        Date curDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(curDate);
        Day = c.get(java.util.Calendar.DAY_OF_MONTH);

        return Day;
    }
    public String getAutoID(){

        String str = null;
        pref = getSharedPreferences("TrafficATID",MODE_PRIVATE);
        edtior = pref.edit();
        str = pref.getString("TrafficATID","");

        return str;
    }

    public int setAutoID(){
        int autoID = 0;
        String str =  getAutoID();

        if (str == ""){
            autoID = 1;
        }else {
            autoID = Integer.parseInt(str) +1;
        }

        String ID = String.valueOf(autoID);
        edtior.putString("TrafficATID",ID);
        edtior.commit();

        return autoID;
    }

    public void insertTraffic(int ID,String username, String from ,String to , int cash,int day,String month,String year,String date) {
        CloudTrafficData cloudTraffic = new CloudTrafficData();
        final String[] trafficObjectId = new String[1];

        cloudTraffic.setID(ID);
        cloudTraffic.setUname(username);
        cloudTraffic.setBegin(from);
        cloudTraffic.setEnd(to);
        cloudTraffic.setCash(cash);
        cloudTraffic.setDay(day);
        cloudTraffic.setMonth(month);
        cloudTraffic.setYear(year);
        cloudTraffic.setDate(date);

        cloudTraffic.save(new SaveListener<String>() {
            @Override
            public void done(String objectid, BmobException e) {
                if (e == null) {
                    trafficObjectId[0] = objectid;
                    trafficObjectID = trafficObjectId[0];
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TRA && resultCode == RESULT_TRA) {
            trafficObjectID = data.getExtras().getString("CloudTrafficID");
            trafficID = data.getExtras().getInt("localTrafficID");
            editDate.setText(data.getExtras().getString("date") + "日");
            int position = Integer.parseInt(data.getExtras().getString("date"));
            edit_From.setText(data.getExtras().getString("begin"));
            edit_To.setText(data.getExtras().getString("end"));
            edit_Cash.setText(data.getExtras().getString("cash"));
            spinnerMonth.setEnabled(false);
            spinnerDay.setSelection(position -1);
            isUpdate = true;
        }
    }
}
