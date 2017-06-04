package com.taiwado.taiwado;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_STOP = 0;
    public static final int REQUEST_TIME = 2;
    public static String username = null;
    public static String ObjectId = null;
    public static String TimeReObjectID = null;
    public static int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = getIntent().getStringExtra("username");
        ObjectId = getIntent().getStringExtra("ObjectId");
        TextView Main_username = (TextView)findViewById(R.id.text_username);
        Main_username.setText(username);
        TextView text_Now = (TextView)findViewById(R.id.textNow);
        text_Now.setText(getNowMonth());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_search) {
            Intent intentSearchList = new Intent(MainActivity.this,SearchListActivity.class);
            startActivity(intentSearchList);
        }

        return super.onOptionsItemSelected(item);
    }

    public void doClickOk(View v) {
        switch (v.getId()) {
            case R.id.button_Time:
                Intent intentTime = new Intent(this, TimeActivity.class);
                intentTime.putExtra("username",username);
                startActivityForResult(intentTime,REQUEST_TIME);
                break;

            case R.id.button_Shifuto:
                Intent intentDate = new Intent(this, CalenderActivity.class);
                startActivity(intentDate);
                break;

            case R.id.button_Tsukinhi:
                Intent intentTsukinhi = new Intent(this,TrafficListActivity.class);
                startActivity(intentTsukinhi);
                break;

            case R.id.button_Uriage:
                Intent intentUriage = new Intent(this,HanbaiList.class);
                startActivity(intentUriage);
                break;

            case R.id.button_Zaikurakensu:
                Intent intentSearchList = new Intent(MainActivity.this,SearchListActivity.class);
                startActivity(intentSearchList);
                break;

            default:
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String strTime,strAddInfo = null;

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_TIME:
                if (requestCode == REQUEST_TIME) {

                    strTime = data.getExtras().getString("timerecord");
                    strAddInfo = data.getExtras().getString("timeaddinfo");

                    TextView text_timeIn = (TextView)findViewById(R.id.textTimeIn);
                    TextView text_timeOut = (TextView)findViewById(R.id.textTimeOut);
                    TextView text_AddIn = (TextView)findViewById(R.id.textAddIn);
                    TextView text_AddOut = (TextView)findViewById(R.id.textAddOut);
                    if (count == 0){
                        text_timeIn.setText(strTime);
                        text_AddIn.setText(strAddInfo);
                        count++;
                    }else {
                        text_timeOut.setText(strTime);
                        text_AddOut.setText(strAddInfo);
                    }

                    break;
                }

            default:

                break;
        }
    }

    public String getNowMonth() {
        //取得当前系统日期
        String month = null;
        SimpleDateFormat formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            formatter = new SimpleDateFormat("yyyy年MM月dd日");
        }
        Date curDate = new Date(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            month = formatter.format(curDate);
        }

        return month;
    }

}
