package com.taiwado.taiwado;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.taiwado.taiwado.com.taiwado.taiwado.DB.BarashiInfo;
import com.taiwado.taiwado.com.taiwado.taiwado.DB.BarashiRepo;
import com.taiwado.taiwado.com.taiwado.taiwado.DB.ProductInfo;
import com.taiwado.taiwado.com.taiwado.taiwado.DB.ProductRepo;
import com.taiwado.taiwado.com.taiwado.taiwado.DB.SaleGroupInfo;
import com.taiwado.taiwado.com.taiwado.taiwado.DB.SaleGruopRepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.taiwado.taiwado.CheckHanbai.RESULT_HAN;
import static com.taiwado.taiwado.MainActivity.username;
import static com.taiwado.taiwado.R.array.OutWarehouse;
import static com.taiwado.taiwado.R.array.tenpos;

public class HanbaiList extends BaseActivity {
    private Spinner spinnerStore,spinnerType;
    EditText editdate,editkakaku,editcount;
    TextView textCommodity,textShirizu,hanbaiDate,hannaiCount,hanbaiCash,present;
    ImageView left,right;
    Switch  aSwitch;
    AutoCompleteTextView autoText;
    TableLayout presentInfo;
    private ArrayList<HashMap<String,String>> barashiList;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private boolean presentYN = false;
    private int counts = 0;
    private int cash = 0;
    private static String storename = null;
    private static String typeHanbai = null;
    private static String shirizuname = null;
    private static boolean isUpdate = false;
    private static String ObjectID = null;
    private static int ID = 0;
    public static final int REQUEST_HAN = 1;
    private static String InputJAN = null;
    private SharedPreferences pref;
    private SharedPreferences.Editor edtior;


    private void init(){
        counts = 0;

        left = (ImageView)findViewById(R.id.left);
        right = (ImageView)findViewById(R.id.right);
        hanbaiDate = (TextView)findViewById(R.id.hanbai_date);
        hannaiCount = (TextView) findViewById(R.id.hanbai_counts);
        hanbaiCash = (TextView)findViewById(R.id.hanbai_cash);
        textCommodity = (TextView)findViewById(R.id.hanbai_commodity);
        textShirizu = (TextView)findViewById(R.id.hanbai_shirizu);
        hanbaiDate.setText(nowDate());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanbai_list);
        isUpdate = false;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        updateSpinner();
        StockNumRepo repo = new StockNumRepo(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (checkInfo()) {
                    if (isUpdate) {
                        Snackbar.make(view, "  販売件数を変更します！", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        updateHanbaiData();
                    } else {
                        Snackbar.make(view, "  販売件数を保存します！", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        insertLocalData();
                    }
                }
                updateCount();
            }
        });

        autoText = (AutoCompleteTextView)findViewById(R.id.jan_search);
        String [] autoString = getResources().getStringArray(R.array.JAN);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(HanbaiList.this,android.R.layout.simple_dropdown_item_1line,autoString);
        autoText.setAdapter(adapter);
        repo.queryObject();
        autoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputJAN = String.valueOf(autoText.getText());

            }

        });
    }

    public void insertCommodity(){
        ProductRepo repo = new ProductRepo(this);
        ProductInfo info = new ProductInfo();

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insertLocalData(){

        HanbaiDataRepo repo = new HanbaiDataRepo(this);
        HanbaiData hanbaiData = new HanbaiData();
        hanbaiData.setID(getAutoID());
        hanbaiData.setUsername(username);
        hanbaiData.setJan(InputJAN);
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
        int cash = 0;
        int count = 0;

        cash = getProductinfo(String.valueOf(InputJAN));

        if (v.getId() == R.id.left){
            count = Integer.parseInt(String.valueOf(hannaiCount.getText()));
            count = count - 1;
            hannaiCount.setText(String.valueOf(count));

            cash = cash * count;
            hanbaiCash.setText("総計：" + String.valueOf(cash) + "円");
        }

        if (v.getId() == R.id.right){
            if (!InputJAN.equals(" ")){
                count = Integer.parseInt(String.valueOf(hannaiCount.getText()));
                count = count + 1;
                hannaiCount.setText(String.valueOf(count));
                cash = cash * count;
                hanbaiCash.setText("総計：" + String.valueOf(cash) + "円");
            }
        }
    }

    private void updateSpinner(){
        spinnerStore = (Spinner)findViewById(R.id.search_tenpo);
        final String[] storeItems = getResources().getStringArray(tenpos);
        ArrayAdapter<String> adapterStore = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,storeItems);
        adapterStore.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStore.setAdapter(adapterStore);
        storename = (String)spinnerStore.getItemAtPosition(1);
        spinnerStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                storename = (String)spinnerStore.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerType = (Spinner)findViewById(R.id.search_type);
        final String[] typeItems = getResources().getStringArray(OutWarehouse);
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,typeItems);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);
        typeHanbai = (String)spinnerType.getItemAtPosition(0);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeHanbai = (String)spinnerType.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean checkInfo(){
        boolean flag = false;

        if (String.valueOf(storename).equals("新宿本店")&& String.valueOf(shirizuname).equals("桜花")) {
            Toast.makeText(HanbaiList.this,"選択した情報を再確認してください！",Toast.LENGTH_SHORT).show();

        }

        if (String.valueOf(editdate.getText()).equals("")){
            Toast.makeText(HanbaiList.this,"日付を入力してください！",Toast.LENGTH_SHORT).show();
            flag = true;
        }

        if (InputJAN.equals("")){
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
            //getHanbaiData(ID);
            int position = data.getExtras().getInt("storename");
            spinnerStore.setSelection(position - 1);
            spinnerStore.setEnabled(false);
            isUpdate = true;
        }

    }
    /*private void getHanbaiData(int id){
        HanbaiDataRepo repo = new HanbaiDataRepo(this);
        HanbaiData hanbaiData = new HanbaiData();
        hanbaiData = repo.getByID(id);
        editdate.setText(String.valueOf(hanbaiData.getDate()));
        //editjan.setText(String.valueOf(hanbaiData.getJan()));

        editkakaku.setText(String.valueOf(hanbaiData.getCash()));
        editcount.setText(String.valueOf(hanbaiData.getCount()));


    }*/

    private void updateCount(){
        int id  = 0;
        int countAfter = 0;
        int countBefore = 0;
        int count = 0;
        StockNumRepo repo = new StockNumRepo(this);
        StockNum stockNum = new StockNum();
        id = repo.getID(storename,InputJAN);
        stockNum = repo.getByID(id);
        countBefore = stockNum.getExitNum();
        countAfter = Integer.valueOf(String.valueOf(editcount.getText()));
        count = countBefore - countAfter;
        if (count > 0){
            stockNum.setExitNum(countBefore - countAfter);
        }else {
            stockNum.setExitNum(0);
        }
        repo.updateCount(stockNum);
        updateCloud(stockNum,count);
    }

    private void updateCloud(StockNum stockNum,int count){
        stockNum.setObjectId(stockNum.getObjectID());
        stockNum.setExitNum(count);
        stockNum.update(new UpdateListener() {
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

    private void updateHanbaiData(){
        HanbaiDataRepo repo = new HanbaiDataRepo(this);
        HanbaiData hanbaiData = new HanbaiData();
        hanbaiData.setID(ID);
        hanbaiData.setDate(String.valueOf(editdate.getText()));
        hanbaiData.setJan(InputJAN);
        hanbaiData.setCash(Integer.parseInt(String.valueOf(editkakaku.getText())));
        hanbaiData.setCount(Integer.parseInt(String.valueOf(editcount.getText())));
        repo.updateData(hanbaiData);

        hanbaiData.update(ObjectID, new UpdateListener() {
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

    private void tableLoad(SaleGroupInfo saleGroupInfo){
        int rows = 0;
        String key = null;
        String values = null;
        BarashiRepo barashiRepo = new BarashiRepo(this);
        BarashiInfo barashiInfo = new BarashiInfo();

        presentInfo = (TableLayout)findViewById(R.id.hanbai_table_body);

        if (rows != 0){
            presentInfo.removeAllViewsInLayout();
        }
        presentInfo.setStretchAllColumns(true);

        if (saleGroupInfo.getBarashi().equals("Y")){
            barashiList = barashiRepo.getBarashiList(saleGroupInfo.getBarashi_code());
            for (HashMap<String,String> map : barashiList)

                for (Map.Entry<String, String> entry : map.entrySet()){
                    key = entry.getKey();
                    values = entry.getValue();
                    if (!key.equals("")){
                        //setTableValue(productRepo.getByID(productRepo.getID(String.valueOf(productRepo.getID(entry.getValue())))));
                        getProductId(values);
                    }
                }
            }

        if (saleGroupInfo.getPresent().equals("Y")){

        }

    }

    private void getProductId(String jan){
        ProductInfo productInfo = new ProductInfo();
        ProductRepo productRepo = new ProductRepo(this);
        int id = 0;
        id = productRepo.getID(jan);
        productInfo = productRepo.getByID(id);
        setTableValue(productInfo);

    }

    private void setTableValue(ProductInfo productInfo){
        TableRow tableRow1 = new TableRow(HanbaiList.this);

        TextView textView = new TextView(HanbaiList.this);
        textView.setText("セット：");
        textView.setPadding(1,1,1,1);
        tableRow1.addView(textView,0);
        presentInfo.addView(tableRow1,new TableLayout.LayoutParams(MP,WC,1));

        TableRow tableRow = new TableRow(HanbaiList.this);

        TextView textView1 = new TextView(HanbaiList.this);
        textView1.setText(productInfo.getJan());
        textView1.setPadding(1,1,1,1);
        tableRow.addView(textView1,0);

        TextView textView2 = new TextView(HanbaiList.this);
        textView2.setText(productInfo.getCommodity());
        textView2.setPadding(1,1,1,1);
        tableRow.addView(textView2,1);

        TextView textView3 = new TextView(HanbaiList.this);
        textView3.setText(String.valueOf(productInfo.getCash()));
        textView3.setPadding(1,1,1,1);
        tableRow.addView(textView3,2);

        presentInfo.addView(tableRow,new TableLayout.LayoutParams(MP,WC,1));


    }

    public int getProductinfo(String Jan){
        int Cash = 0;


        SaleGruopRepo saleGruopRepo = new SaleGruopRepo(this);
        BarashiRepo barashiRepo = new BarashiRepo(this);
        SaleGroupInfo saleGroupInfo = new SaleGroupInfo();
        BarashiInfo barashiInfo = new BarashiInfo();

        saleGroupInfo = saleGruopRepo.getById(saleGruopRepo.getSaleID(Jan));
        textShirizu.setText(saleGroupInfo.getShirizu());
        textCommodity.setText(saleGroupInfo.getCommodity());
        Cash = saleGroupInfo.getCash();
        //if (saleGroupInfo.getBarashi().equals("Y")){
        //    barashiList = barashiRepo.getBarashiList(saleGroupInfo.getBarashi_code());
        //}
        tableLoad(saleGroupInfo);

        return Cash;
    }

}
