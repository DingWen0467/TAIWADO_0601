package com.taiwado.taiwado;

import android.app.Activity;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Date;

public class TrafficListActivity extends Activity {
    private Spinner spinner;
    private TableLayout trafficTable;

    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        int position = 0;
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_traffic_list);

        spinner =(Spinner)findViewById(R.id.traffic__month_spinner);
        String[] mItems = getResources().getStringArray(R.array.months);
        spinner.setPrompt("月選択");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner .setAdapter(adapter);
        position = getPosition();
        spinner.setSelection(position);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressWarnings("WrongConstant")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] months = getResources().getStringArray(R.array.months);
                //Toast.makeText(TrafficListActivity.this,"選択：" +months[position], 2000).show();
                tableLoad(months[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void tableLoad(String selecctMonth){

        String nowMonth = null;
        int rows = 0;

        nowMonth = getNowMonth();
        if (nowMonth == selecctMonth) {
            rows = getRows(nowMonth);
            TextView table_month = (TextView)findViewById(R.id.table_header_month);
            table_month.setText(nowMonth);
        }
        else {
            rows = getRows(selecctMonth);
            TextView table_month = (TextView)findViewById(R.id.table_header_month);
            table_month.setText(selecctMonth);
        }

        trafficTable = (TableLayout)findViewById(R.id.traffic_table_body);

        if (rows != 0) {
            trafficTable.removeAllViewsInLayout();
        }
        trafficTable.setStretchAllColumns(true);

        for (int i = 0; i <rows; i++){
            //TableRow tableRow = new TableRow(getBaseContext());
            TableRow tableRow = new TableRow(TrafficListActivity.this);
            for (int j = 0; j < 4; j++){
                //TextView textView = new TextView(getBaseContext());
                TextView textView = new TextView(TrafficListActivity.this);
                //textView.setBackgroundResource(R.drawable.shape);
                //textView.setPadding(1,1,1,1);
                textView.setText("(" +i+","+j+")");
                tableRow.addView(textView,j);
            }
            trafficTable.addView(tableRow,new TableLayout.LayoutParams(MP,WC,1));
        }

    }
    public String getNowMonth() {
        //取得当前系统日期
        String month = null;
        SimpleDateFormat formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            formatter = new SimpleDateFormat("MM月");
        }
        Date curDate = new Date(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            month = formatter.format(curDate);
        }

        return month;
    }

    private int getRows(String month) {
        int rows = 0;
        if (month == null) {
            month = getNowMonth();
        }

        switch (month) {
            case"02月":
                rows = 29;
                break;
            case "01月":case "03月":case "05月":case "07月":case "08月":case "10月":case "12月":
                rows = 31;
                break;
            case "04月":case "06月":case "09月":case "11月":
                rows = 30;
                break;
            default:
                break;
        }
        return rows;
    }
    private int getPosition(){
        int position = 0;

        String month = getNowMonth();

        switch (month) {
            case"01月":
                position = 0;
                break;
            case"02月":
                position = 1;
                break;
            case"03月":
                position = 2;
                break;
            case"04月":
                position = 3;
                break;
            case"05月":
                position = 4;
                break;
            case"06月":
                position = 5;
                break;
            case"07月":
                position = 6;
                break;
            case"08月":
                position = 7;
                break;
            case"09月":
                position = 8;
                break;
            case"10月":
                position = 9;
                break;
            case"11月":
                position = 10;
                break;
            case"12月":
                position = 11;
                break;
            default:
                break;
        }
        return  position;
    }
}