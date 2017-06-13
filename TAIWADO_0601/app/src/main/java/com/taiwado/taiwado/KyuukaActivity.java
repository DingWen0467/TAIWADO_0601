package com.taiwado.taiwado;

import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.taiwado.taiwado.MainActivity.ID;
import static com.taiwado.taiwado.MainActivity.ObjectId;
import static com.taiwado.taiwado.R.array.kyuuka;
import static com.taiwado.taiwado.R.array.time;

public class KyuukaActivity extends BaseActivity {

    private Spinner spinnerTimeIn;
    private Spinner spinnerTimeOut;
    private Spinner spinnerKyuuka;
    private static String username = null;
    private static String timeInValue = null;
    private static String timeOutValue = null;
    private static String holidayValue = null;
    private static String holidayID = null;
    private HolidayData holidayData = new HolidayData();
    private HolidayRepo holidayRepo = new HolidayRepo();
    private static boolean holiday = false;
    private SharedPreferences pref;
    private SharedPreferences.Editor edtior;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyuuka2);
        init();
        CloseAllActivity.getInstance().addActivity(this);
        Bmob.initialize(this, "396d004b9ddb44265f799ad3d9c7ea5d");
        username = getIntent().getStringExtra("username");
        String date = setDate();
        TextView textTitle = (TextView) findViewById(R.id.kyuuka_title);
        textTitle.setText("出勤予定-" + date);
        holidayRepo.queryObjects(username, setDay());

        updateSpinnertime();
        updateKyuuka();
        LocalDataRepo repo = new LocalDataRepo(this);
        LocalData localData = new LocalData();

        localData = repo.getLocalDataByID(MainActivity.ID);
        repo.getLocaldataList(username,nowDate(),getMonth());
    }
    private void init(){
        username = null;
        timeInValue = null;
        timeOutValue = null;
        holidayValue = null;
        holidayID = null;
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
        //LocalDataRepo repo = new LocalDataRepo(this);
       // LocalData localData = new LocalData();

        switch (v.getId()) {
            case R.id.kyuuka_ok:
                if (holidayID == null){
                    if (holidayRepo.getDay() == 0){
                        createHolidayData(username,setDate(),setDay());
                    }
                }
                if (holidayID != null || holidayRepo.getObjectId() != null){
                    if (holidayRepo.getObjectId() != null){
                        holidayID = holidayRepo.getObjectId();
                    }
                    holidayRepo.updateHolidayData(holidayID,setholiday(),holidayValue,timeInValue,timeOutValue,setwork(),setDay());
                    Toast.makeText(KyuukaActivity.this, username + " の休暇情報を更新しました。", Toast.LENGTH_SHORT).show();
                }

                break;

            default:
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (holidayID != null){

                createLocalData();
            }
            onBackPressed();
        }
        return false;
    }

    public String getAutoID(){

        String str = null;
        pref = getSharedPreferences("AUTOID",MODE_PRIVATE);
        edtior = pref.edit();
        str = pref.getString("AUTOID","");

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
        edtior.putString("AUTOID",ID);
        edtior.commit();

        return autoID;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createLocalData(){

        LocalDataRepo repo = new LocalDataRepo(this);
        LocalData localData = new LocalData();
        localData.ID = ID;
        localData.objectID = ObjectId;
        localData.uname = username;
        localData.day = setDay();
        localData.month = getMonth();
        localData.date = nowDate();
        localData.timein = timeInValue;
        localData.timeout = timeOutValue;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            localData.month = getMonth();
        }

        if (holidayValue == " "){
            localData.work = "work";
        }else {
            localData.holiday = "holiday";
        }
        if (holidayValue != null){
            localData.holidaytype = holidayValue;
        }

        repo.insertData(localData);

    }

    public String setwork(){
        String work = null;

        if (holidayValue == " "){
            work = "work";
        }else {
            work = " ";
        }
        return work;
    }

    public String setholiday(){
        String holiday = null;
        switch (holidayValue){
            case "全休":
                holiday = "holiday";

                break;
            case "前休":
                holiday = "holiday";

                break;
            case "後休":
                holiday = "holiday";

                break;
            case "":
                holiday = "";

                break;
            default:
                break;
        }
        return holiday;
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
        str = str.substring(8,10);
        day = Integer.valueOf(str).intValue();

        return day;
    }

    public String getMonth(){
        String str = null;
        String date = nowDate();
        if (getIntent().getStringExtra("main") != null){
            str = date;
        }else {
            str = getIntent().getStringExtra("date");
        }
        str = str.substring(5,7);

        return str;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createHolidayData(String username, String date,int day) {
        final String[] holidayobjectId = {null};
        String flag = null;

        holidayData.setUsername(username);
        holidayData.setDate(date);
        holidayData.setID(setAutoID());
        holidayData.setMonth(getMonth());
        holidayData.setDay(day);
        if (timeInValue != "") {
            holidayData.setTimeIn(timeInValue);
        }
        if (timeOutValue != "") {
            holidayData.setTimeOut(timeOutValue);
        }

        switch (holidayValue){
            case "全休":
                holidayData.setHoliday("holiday");
                holidayData.setHolidayType("全休");
                holidayData.setWork("");
                flag ="全休";
                break;
            case "前休":
                holidayData.setHoliday("holiday");
                holidayData.setHolidayType("前休");
                holidayData.setWork("");
                flag ="前休";
                break;
            case "後休":
                holidayData.setHoliday("holiday");
                holidayData.setHolidayType("後休");
                holidayData.setWork("");
                flag ="後休";
                break;
            case "":
                holidayData.setWork("work");
                holidayData.setHoliday("");
                holidayData.setHolidayType("");
                flag ="work";
                break;
            default:
                break;
        }

        holidayData.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    holidayobjectId[0] = holidayData.getObjectId();
                    holidayID = holidayobjectId[0];
                    ID = holidayData.getID();
                }
            }
        });
        if (flag == "work"){
            Toast.makeText(this,setDate() + "　仕事",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,setDate() + "　休暇",Toast.LENGTH_LONG).show();
        }
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
                holidayValue = (String)spinnerKyuuka.getItemAtPosition(position);
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
    @Override
    public void onBackPressed() {
        CloseAllActivity.getInstance().finishActivity(this);
    }

}
