package com.taiwado.taiwado;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import static com.taiwado.taiwado.R.array.kyuuka;
import static com.taiwado.taiwado.R.array.time;

public class KyuukaActivity extends AppCompatActivity {

    private Spinner spinnerTimeIn;
    private Spinner spinnerTimeOut;
    private Spinner spinnerKyuuka;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyuuka2);
        TextView textTitle = (TextView)findViewById(R.id.kyuuka_title);
        textTitle.setText("出勤予定-" + nowDate());
        updateSpinnertime();
        updateKyuuka();
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
                updateKyuuka();
                spinnerKyuuka.setEnabled(true);
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

    public void doClickOk(View v) {
        switch (v.getId()) {
            case R.id.kyuuka_ok:

                break;

            default:
                break;
        }

    }

    public void doClickCanccel(View view) {
        Toast.makeText(KyuukaActivity.this, "取消", Toast.LENGTH_SHORT).show();
    }

    public void updateSpinnertime(){
        spinnerTimeIn =(Spinner)findViewById(R.id.search_timeIn);
        final String[] timeInItems = getResources().getStringArray(time);
        ArrayAdapter<String> adapterIn=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, timeInItems);
        //Formatを設定
        adapterIn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeIn .setAdapter(adapterIn);
        spinnerTimeIn.setSelection(4);
        final int selectedTimeIn = spinnerTimeIn.getSelectedItemPosition();
        spinnerTimeIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressWarnings("WrongConstant")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String timeInValue = (String)spinnerTimeIn.getItemAtPosition(position);
                //Toast.makeText(KyuukaActivity.this,"検索店舗：" +timeInItems[position], 2000).show();

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
        final int selectedTimeOut = spinnerTimeOut.getSelectedItemPosition();
        spinnerTimeOut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void updateKyuuka(){
        spinnerKyuuka = (Spinner)findViewById(R.id.search_kyuuka);
        final  String[] timeKyuukaItems = getResources().getStringArray(kyuuka);
        ArrayAdapter<String> adapterKyuuka = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,timeKyuukaItems);
        adapterKyuuka.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKyuuka.setAdapter(adapterKyuuka);
        spinnerKyuuka.setSelection(1);
        final int selectedKyuuka = spinnerKyuuka.getSelectedItemPosition();
        spinnerKyuuka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
