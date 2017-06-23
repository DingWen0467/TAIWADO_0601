package com.taiwado.taiwado;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
            @Override
            public void onClick(View view) {
                if (checkInfo()){
                    if (Second){
                        Snackbar.make(view, "  販売件数を保存します！", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                if (isUpdate){

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
        hanbaiData.setCount(Integer.parseInt(String.valueOf(editcount.getText())));
        int ID = repo.insertHanbaiData(hanbaiData);
        String ObjectId = repo.CloudHanbaiData(hanbaiData,ID);
        repo.updateObjectID(ObjectId,ID);
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

        int id = item.getItemId();

        if (id == R.id.action_plus) {
            Intent intentInsertData = new Intent(HanbaiList.this,CheckHanbai.class);
            startActivity(intentInsertData);
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
}
