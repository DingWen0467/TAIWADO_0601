package com.taiwado.taiwado;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import cn.bmob.v3.BmobRealTimeData;

public class SearchListActivity extends AppCompatActivity {
    private AutoCompleteTextView autoText;
    private SimpleAdapter adapterlist;
    private ListView listView;
    private ArrayList<HashMap<String, String>> exitNumList;
    private TextView shirizu;
    private TextView stockname;
    private static String text = null;
    private static ImageView button;
    StockNumRepo repo = new StockNumRepo(this);
    BmobRealTimeData commInfo = new BmobRealTimeData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        button = (ImageView) findViewById(R.id.search_bar);
        listView = (ListView)findViewById(R.id.stock_list);
        shirizu = (TextView)findViewById(R.id.stock_shirizu);
        stockname = (TextView)findViewById(R.id.stock_name);
        autoText = (AutoCompleteTextView)findViewById(R.id.jan_search);
        String [] autoString = getResources().getStringArray(R.array.JAN);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(SearchListActivity.this,android.R.layout.simple_dropdown_item_1line,autoString);
        autoText.setAdapter(adapter);
        repo.queryObject();
        autoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               text = String.valueOf(autoText.getText());
            }
        });
    }

    public void doClickOk(View v){
        if (v.getId() == R.id.search_bar) {
            exitNumList =  repo.getStockNum(text);
            adapterlist = new SimpleAdapter(this,exitNumList,R.layout.stock_item,new String[]{"name","num"},new int[]{R.id.name,R.id.num});
            listView.setAdapter(adapterlist);
            shirizu.setText(repo.getShirizu(text));
            stockname.setText(repo.getCommodity(text));
        }
    }
}
