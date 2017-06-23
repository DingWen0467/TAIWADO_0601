package com.taiwado.taiwado;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

import static com.taiwado.taiwado.R.array.months;
import static com.taiwado.taiwado.R.array.tenpos;

public class CheckHanbai extends AppCompatActivity {
    private Spinner spinnerStore,spinnershirizu,spinnerdate;
    private static String storename = null;
    private static String shirizu = null;
    private static String date = null;
    private ArrayList<HashMap<String,String>> HanbaiList;
    HanbaiDataRepo repo = new HanbaiDataRepo(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_hanbai);
        init();
        updateSpinner();
    }

    private void init(){

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

        spinnershirizu = (Spinner)findViewById(R.id.search_shirizu);
        final String[] shirizuItems = getResources().getStringArray(tenpos);
        ArrayAdapter<String> adapterShirizu = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,shirizuItems);
        adapterShirizu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnershirizu.setAdapter(adapterShirizu);
        shirizu = (String)spinnershirizu.getItemAtPosition(0);
        spinnershirizu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shirizu = (String)spinnershirizu.getItemAtPosition(position);
                String[] months = getResources().getStringArray(R.array.months);
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
        date = (String)spinnerdate.getItemAtPosition(0);
        spinnerdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                date = (String)spinnerdate.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getList(String selectMonth){

    }

}
