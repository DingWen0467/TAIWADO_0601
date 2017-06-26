package com.taiwado.taiwado;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.taiwado.taiwado.CheckHanbai.RESULT_HAN;
import static com.taiwado.taiwado.MainActivity.ObjectId;
import static com.taiwado.taiwado.MainActivity.username;
import static com.taiwado.taiwado.R.array.tenpos;

public class HanbaiList extends AppCompatActivity {
    private Spinner spinnerStore,spinnershirizu;
    EditText editdate,editjan,editkakaku,editcount;
    TextView textCommodity,textShirizu;
    private static boolean Second = false;
    private static String storename = null;
    private static String shirizuname = null;
    private static boolean isUpdate = false;
    private static String ObjectID = null;
    private static int ID = 0;
    public static final int REQUEST_HAN = 1;
    private SharedPreferences pref;
    private SharedPreferences.Editor edtior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanbai_list);
        isUpdate = false;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        updateSpinner();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (checkInfo()){
                    Snackbar.make(view, "  販売件数を保存します！", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                if (isUpdate){
                    updateHanbaiData();
                }else {
                    insertLocalData();
                }
            }
        });
        editjan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    InputMethodManager manager = (InputMethodManager) editjan.getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    manager.showSoftInput(editjan, 0);
                }else {
                    InputMethodManager manager = (InputMethodManager) editjan.getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    manager.showSoftInput(editjan, 0);
                    insertCommodity();
                }
            }
        });
    }

    public void insertCommodity(){
        StockNumRepo repo = new StockNumRepo(this);
        String commodity = repo.getCommodity(String.valueOf(editjan.getText()));
        String shirizu = repo.getShirizu(String.valueOf(editjan.getText()));
        textCommodity.setText(commodity);
        textShirizu.setText(shirizu);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insertLocalData(){

        HanbaiDataRepo repo = new HanbaiDataRepo(this);
        HanbaiData hanbaiData = new HanbaiData();
        hanbaiData.setID(getAutoID());
        hanbaiData.setUsername(username);
        hanbaiData.setJan(String.valueOf(editjan.getText()));
        hanbaiData.setDate(String.valueOf(editdate.getText()));
        hanbaiData.setStoreName(String.valueOf(storename));
        hanbaiData.setShirizu(String.valueOf(textShirizu.getText()));
        hanbaiData.setCommodity(String.valueOf(textCommodity.getText()));
        hanbaiData.setCash(Integer.parseInt(String.valueOf(editkakaku.getText())));
        String str = String.valueOf(editdate.getText());
        str = str.substring(str.length() - 2,str.length());
        hanbaiData.setDay(str + "日");
        hanbaiData.setMonth(getNowMonth());
        hanbaiData.setYear(BaseActivity.getYear());
        hanbaiData.setCount(Integer.parseInt(String.valueOf(editcount.getText())));
        ID = repo.insertHanbaiData(hanbaiData);
        CloudHanbaiData(hanbaiData,ID);
        isUpdate = true;
    }
    private void updateHanbaiObjectID(){
        HanbaiDataRepo repo = new HanbaiDataRepo(this);
        repo.updateObjectID(ObjectID,ID);
    }

    private void init(){
        editdate = (EditText)findViewById(R.id.edit_date);
        editjan = (EditText)findViewById(R.id.edit_JAN);
        editkakaku = (EditText)findViewById(R.id.edit_kakaku);
        editcount = (EditText)findViewById(R.id.edit_num);
        textCommodity = (TextView)findViewById(R.id.hanbai_commodity);
        textShirizu = (TextView)findViewById(R.id.hanbai_shirizu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_hanbailist,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        updateHanbaiObjectID();
        int id = item.getItemId();

        if (id == R.id.action_plus) {
            Intent intentInsertData = new Intent(HanbaiList.this,CheckHanbai.class);
            startActivityForResult(intentInsertData,REQUEST_HAN);
        }
        return super.onOptionsItemSelected(item);
    }

    public void doClick(View v){
        if (v.getId() == R.id.text_date){
            Intent intent = new Intent(HanbaiList.this,TimeActivity.class);
            startActivity(intent);

        }

    }

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean checkInfo(){
        boolean flag = false;
        Second = false;
        if (String.valueOf(storename).equals("新宿本店")&& String.valueOf(shirizuname).equals("桜花")) {
            Toast.makeText(HanbaiList.this,"選択した情報を再確認してください！",Toast.LENGTH_SHORT).show();
            Second = true;
        }

        if (String.valueOf(editdate.getText()).equals("")){
            Toast.makeText(HanbaiList.this,"日付を入力してください！",Toast.LENGTH_SHORT).show();
            flag = true;
        }

        if (String.valueOf(editjan.getText()).equals("")){
            Toast.makeText(HanbaiList.this,"JANコードを入力してください",Toast.LENGTH_SHORT).show();
            flag = true;
        }

        if (String.valueOf(editkakaku.getText()).equals("")){
            Toast.makeText(HanbaiList.this,"価格を入力してください",Toast.LENGTH_SHORT).show();
            flag = true;
        }

        if (String.valueOf(editcount.getText()).equals("")){
            Toast.makeText(HanbaiList.this,"件数を入力してください",Toast.LENGTH_SHORT).show();
            flag = true;
        }

        if (flag){
            return false;
        }else
        {
            return true;
        }
    }

    public String setAutoID(){

        String str = null;
        pref = getSharedPreferences("HanbaiID",MODE_PRIVATE);
        edtior = pref.edit();
        str = pref.getString("HanbaiID","");

        return str;
    }

    public int getAutoID(){
        int autoID = 0;
        String str =  setAutoID();

        if (str == ""){
            autoID = 1;
        }else {
            autoID = Integer.parseInt(str) +1;
        }

        String ID = String.valueOf(autoID);
        edtior.putString("HanbaiID",ID);
        edtior.commit();

        return autoID;
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
    public void onBackPressed(){
        updateHanbaiObjectID();
        CloseAllActivity.getInstance().finishActivity(this);
    }

    public void CloudHanbaiData(HanbaiData hanbaiData,int ID){
        final String[] objectId = {null};
        hanbaiData.setID(ID);
        hanbaiData.save(new SaveListener<String>() {
            @Override
            public void done(String objectid, BmobException e) {
                if (e == null){
                    objectId[0] = objectid;
                    ObjectID = objectId[0];
                }
            }
        });
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        isUpdate = false;
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_HAN && resultCode == RESULT_HAN){
            ObjectID = data.getExtras().getString("HanbaiObjectID");
            ID = data.getExtras().getInt("HanbaiID");
            getHanbaiData(ID);
            int position = data.getExtras().getInt("storename");
            spinnerStore.setSelection(position - 1);
            spinnerStore.setEnabled(false);
            isUpdate = true;
        }

    }
    private void getHanbaiData(int id){
        HanbaiDataRepo repo = new HanbaiDataRepo(this);
        HanbaiData hanbaiData = new HanbaiData();
        hanbaiData = repo.getByID(id);
        editdate.setText(String.valueOf(hanbaiData.getDate()));
        editjan.setText(String.valueOf(hanbaiData.getJan()));
        editkakaku.setText(String.valueOf(hanbaiData.getCash()));
        editcount.setText(String.valueOf(hanbaiData.getCount()));


    }

    private void updateHanbaiData(){
        HanbaiDataRepo repo = new HanbaiDataRepo(this);
        HanbaiData hanbaiData = new HanbaiData();
        hanbaiData.setID(ID);
        hanbaiData.setDate(String.valueOf(editdate.getText()));
        hanbaiData.setJan(String.valueOf(editjan.getText()));
        hanbaiData.setCash(Integer.parseInt(String.valueOf(editkakaku.getText())));
        hanbaiData.setCount(Integer.parseInt(String.valueOf(editcount.getText())));
        repo.updateData(hanbaiData);

        hanbaiData.update(ObjectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob", "OK");
                } else {
                    Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
