package com.taiwado.taiwado;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.taiwado.taiwado.MainActivity.username;

public class TrafficListActivity extends Activity {
    private Spinner spinner;
    private SimpleAdapter adapterlist;
    private ListView listView;
    private static String date,begin,end,cash;
    public static final int RESULT_TRA = 100;
    TrafficDataRepo repo = new TrafficDataRepo(this);
    private TextView textcash;
    protected static final int Menu_Item1= Menu.FIRST;
    protected static final int Menu_Item2 = 101;
    private static String selecctMonth = null;
    private ArrayList<HashMap<String, String>> trafficDataList;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle saveInstanceState) {
        int position = 0;
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_traffic_list);
        textcash = (TextView)findViewById(R.id.traffic_cash);
        listView = (ListView)findViewById(R.id.traffic_list);
        spinner =(Spinner)findViewById(R.id.traffic__month_spinner);
        String[] mItems = getResources().getStringArray(R.array.months);
        spinner.setPrompt("月選択");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner .setAdapter(adapter);
        position = getPosition();
        spinner.setSelection(position);
        selecctMonth = (String)spinner.getItemAtPosition(position);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressWarnings("WrongConstant")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] months = getResources().getStringArray(R.array.months);
                //Toast.makeText(TrafficListActivity.this,"選択：" +months[position], 2000).show();
                textcash.setText(repo.getMonthCash(username,months[position],getYear()));
                tableLoad(months[position]);
                selecctMonth = months[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        trafficDataList =  repo.getTrafficDataList(username,selecctMonth,getYear());
        adapterlist = new SimpleAdapter(this,trafficDataList,R.layout.traffic_item,new String[]{"date","begin","end","cash"},new int[]{R.id.date,R.id.begin,R.id.end,R.id.list_cash});
        listView.setAdapter(adapterlist);
        listView.setOnCreateContextMenuListener(MenuList);
    }

    ListView.OnCreateContextMenuListener MenuList = new ListView.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0,Menu_Item1,0,"変 更");
            menu.add(0,Menu_Item2,0,"削 除");
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void tableLoad(String selecctMonth){
        trafficDataList =  repo.getTrafficDataList(username,selecctMonth,getYear());
        adapterlist = new SimpleAdapter(this,trafficDataList,R.layout.traffic_item,new String[]{"date","begin","end","cash"},new int[]{R.id.date,R.id.begin,R.id.end,R.id.list_cash});
        listView.setAdapter(adapterlist);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        HashMap<String,String> map = (HashMap<String,String>)trafficDataList.get(info.position);
        initVar();
        date = map.get("date").substring(0,2);
        begin = map.get("begin").replaceAll(" ","");
        end = map.get("end").replaceAll(" ","");
        cash = map.get("cash").replaceAll(" ","");

        switch (item.getItemId()) {
            case Menu_Item1:
                //Toast.makeText(TrafficListActivity.this,String.valueOf(item.getItemId()), Toast.LENGTH_LONG).show();
                int localTrafficID = repo.getTrafficID(username,selecctMonth,getYear(),date,begin,end,cash);
                String CloudTrafficID = repo.getTrafficObjectID(username,selecctMonth,getYear(),date,begin,end,cash);
                Intent intent = new Intent(this,TrafficSaveActivity.class);
                intent.putExtra("localTrafficID",localTrafficID);
                intent.putExtra("CloudTrafficID",CloudTrafficID);
                intent.putExtra("date",date);
                intent.putExtra("begin",begin);
                intent.putExtra("end",end);
                intent.putExtra("cash",cash);
                TrafficListActivity.this.setResult(RESULT_TRA,intent);
                TrafficListActivity.this.finish();
                return true;

            case Menu_Item2:

                int localTrafficId = repo.getTrafficID(username,selecctMonth,getYear(),date,begin,end,cash);
                String cloudTrafficId = repo.getTrafficObjectID(username,selecctMonth,getYear(),date,begin,end,cash);
                deleteTrafficData(cloudTrafficId,localTrafficId);
                Toast.makeText(TrafficListActivity.this," DELETE ! ",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void deleteTrafficData(String objectid, int id){
        CloudTrafficData cloudTrafficData = new CloudTrafficData();
        cloudTrafficData.setObjectID(objectid);
        cloudTrafficData.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    Toast.makeText(TrafficListActivity.this," DELETE ! ",Toast.LENGTH_SHORT).show();
                }
            }
        });

        TrafficDataRepo repo = new TrafficDataRepo(this);
        repo.delete(id);
        trafficDataList =  repo.getTrafficDataList(username,selecctMonth,getYear());
        adapterlist = new SimpleAdapter(this,trafficDataList,R.layout.traffic_item,new String[]{"date","begin","end","cash"},new int[]{R.id.date,R.id.begin,R.id.end,R.id.list_cash});
        listView.setAdapter(adapterlist);
        listView.setOnCreateContextMenuListener(MenuList);
    }
    @Override
    public void onBackPressed() {

        CloseAllActivity.getInstance().finishActivity(this);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getYear(){
        String str = null;
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        str = String.valueOf(cal.get(Calendar.YEAR));

        return str;
    }
    public void initVar(){
        date = null;
        begin = null;
        end = null;
        cash = null;
    }
}