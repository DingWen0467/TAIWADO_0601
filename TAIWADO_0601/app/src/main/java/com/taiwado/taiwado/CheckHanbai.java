package com.taiwado.taiwado;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.taiwado.taiwado.BaseActivity.getYear;
import static com.taiwado.taiwado.MainActivity.username;
import static com.taiwado.taiwado.R.array.months;
import static com.taiwado.taiwado.R.array.shirizus;
import static com.taiwado.taiwado.R.array.tenpos;

public class CheckHanbai extends AppCompatActivity {
    private Spinner spinnerStore,spinnershirizu,spinnerdate;
    private static String date,jan,cash,count;
    public static final int RESULT_HAN = 200;
    private static String storename = null;
    private static String shirizu = null;
    //private static String objectid = null;
    private static String month = null;
    private static int StorePosition = 0;
    private SimpleAdapter adapterlist;
    private ListView listView;
    private ArrayList<HashMap<String,String>> HanbaiList;
    protected static final int Menu_Item1= Menu.FIRST;
    protected static final int Menu_Item2 = 101;
    HanbaiDataRepo repo = new HanbaiDataRepo(this);
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_hanbai);
        init();
        updateSpinner();
    }

    private void init(){
        listView = (ListView)findViewById(R.id.hanbai_list);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateSpinner(){
        spinnerStore = (Spinner)findViewById(R.id.search_tenpo);
        final String[] storeItems = getResources().getStringArray(tenpos);
        ArrayAdapter<String> adapterStore = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,storeItems);
        adapterStore.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStore.setAdapter(adapterStore);
        storename = (String)spinnerStore.getItemAtPosition(0);
        spinnerStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                storename = (String)spinnerStore.getItemAtPosition(position);
                StorePosition = position;
                getList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnershirizu = (Spinner)findViewById(R.id.search_shirizu);
        final String[] shirizuItems = getResources().getStringArray(shirizus);
        ArrayAdapter<String> adapterShirizu = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,shirizuItems);
        adapterShirizu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnershirizu.setAdapter(adapterShirizu);
        shirizu = (String)spinnershirizu.getItemAtPosition(0);
        spinnershirizu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shirizu = (String)spinnershirizu.getItemAtPosition(position);
                getList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerdate = (Spinner)findViewById(R.id.select_date);
        final String[] dateItems = getResources().getStringArray(months);
        ArrayAdapter<String> adapterDate = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,dateItems);
        adapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerdate.setAdapter(adapterDate);
        TrafficListActivity tr = new TrafficListActivity();
        int position = 0;
        position = tr.getPosition();
        spinnerdate.setSelection(position);
        month = (String)spinnerdate.getItemAtPosition(position);
        spinnerdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = (String)spinnerdate.getItemAtPosition(position);
                getList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getList();

    }
    ListView.OnCreateContextMenuListener MenuList = new ListView.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0,Menu_Item1,0,"変 更");
            menu.add(0,Menu_Item2,0,"削 除");
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getList(){
        HanbaiList =  repo.getHanbaiList(username,getYear(),month,storename,shirizu);
        adapterlist = new SimpleAdapter(this,HanbaiList,R.layout.hanbai_item,new String[]{"date","jan","cash","count"},new int[]{R.id.date,R.id.jan,R.id.cash,R.id.count});
        listView.setAdapter(adapterlist);
        listView.setOnCreateContextMenuListener(MenuList);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        HashMap<String,String> map = (HashMap<String,String>)HanbaiList.get(info.position);
        initVar();
        date = map.get("date").replaceAll(" ","");
        jan = map.get("jan").replaceAll(" ","");
        cash = map.get("cash").replaceAll(" ","");
        count = map.get("count").replaceAll(" ","");
        int HanbaiID = repo.getHanbaiID(username,date,jan,cash, Integer.parseInt(count),storename);
        String objectid = repo.getHanbaiObjectID(HanbaiID,username,date,jan,cash, Integer.parseInt(count),storename);

        switch (item.getItemId()) {
            case Menu_Item1:
                //Toast.makeText(TrafficListActivity.this,String.valueOf(item.getItemId()), Toast.LENGTH_LONG).show();

                Intent intentHanbai = new Intent(this,HanbaiList.class);
                intentHanbai.putExtra("HanbaiID",HanbaiID);
                intentHanbai.putExtra("storename",StorePosition);
                CheckHanbai.this.setResult(RESULT_HAN,intentHanbai);
                CheckHanbai.this.finish();
                return true;

            case Menu_Item2:
                deleteHanbaiData(HanbaiID,objectid);
                Toast.makeText(CheckHanbai.this," 削除した ! ",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteHanbaiData(int ID,String objectid){
        HanbaiData hanbaiData = new HanbaiData();
        hanbaiData.setObjectId(objectid);
        hanbaiData.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){

                }
            }
        });
        HanbaiDataRepo repo = new HanbaiDataRepo(this);
        repo.delete(ID);
        getList();

    }

    private void initVar(){
        date = null;
        jan = null;
        cash = null;
        count = null;
    }

    public void onBackPressed(){
        CloseAllActivity.getInstance().finishActivity(this);
    }
}
