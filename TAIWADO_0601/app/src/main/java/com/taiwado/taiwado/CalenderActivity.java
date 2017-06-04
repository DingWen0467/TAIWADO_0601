package com.taiwado.taiwado;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalenderActivity extends AppCompatActivity implements DateActivity.NewCaledarListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        DateActivity calendar = (DateActivity) findViewById(R.id.newCalendar);
        calendar.Listener = this;
    }

    @Override
    public View findViewById(@IdRes int id) {
        return super.findViewById(id);
    }
    public void onItemLongPress(Date day){
        DateFormat df = SimpleDateFormat.getDateInstance();
        Toast.makeText(this,df.format(day),Toast.LENGTH_LONG).show();
        Intent intentKyuuka = new Intent(CalenderActivity.this,Kyuuka.class);
        startActivity(intentKyuuka);
    }
}
