package com.taiwado.taiwado;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static com.taiwado.taiwado.R.array.tenpos;

public class SearchListActivity extends AppCompatActivity {

    private Spinner spinner;
    private TableLayout searchTable;
    protected int position = 0;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        spinner =(Spinner)findViewById(R.id.search_tenpo);
        final String[] mItems = getResources().getStringArray(tenpos);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner .setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressWarnings("WrongConstant")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String[] tenpos = getResources().getStringArray(tenpos);
                Toast.makeText(SearchListActivity.this,"検索店舗：" +mItems[position], 2000).show();
                //tableLoad();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void doClickOk(View v){
        if (v.getId() == R.id.search_bar) {
            tableLoad();
        }

    }

    public void tableLoad(){
        int rows = 0;
        searchTable = (TableLayout)findViewById(R.id.search_table_body);

        if (rows != 0) {
            searchTable.removeAllViewsInLayout();
        }
        searchTable.setStretchAllColumns(true);

        for (int i = 0; i <4; i++){
            //TableRow tableRow = new TableRow(getBaseContext());
            TableRow tableRow = new TableRow(SearchListActivity.this);
            for (int j = 0; j < 3; j++){
                //TextView textView = new TextView(getBaseContext());
                TextView textView = new TextView(SearchListActivity.this);
                //textView.setBackgroundResource(R.drawable.shape);
                //textView.setPadding(1,1,1,1);
                textView.setText("(" +i+","+j+")");
                tableRow.addView(textView,j);
            }
            searchTable.addView(tableRow,new TableLayout.LayoutParams(MP,WC,1));
        }

    }
}
