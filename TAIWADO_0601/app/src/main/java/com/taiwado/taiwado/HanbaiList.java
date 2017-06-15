package com.taiwado.taiwado;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HanbaiList extends AppCompatActivity {

    private TableLayout listTable;
    protected int position = 0;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanbai_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.hanbai_toolbar);
        setSupportActionBar(toolbar);


    }

    public void doClickOk(View v){
        if (v.getId() == R.id.button_list) {
            tableLoad();
        }

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
            Intent intentInsertData = new Intent(HanbaiList.this,InsertData.class);
            startActivity(intentInsertData);
        }
        return super.onOptionsItemSelected(item);
    }

    public void tableLoad(){
        int rows = 0;
        listTable = (TableLayout)findViewById(R.id.hanbai_list);

        if (rows != 0) {
            listTable.removeAllViewsInLayout();
        }
        listTable.setStretchAllColumns(true);

        for (int i = 0; i <4; i++){
            //TableRow tableRow = new TableRow(getBaseContext());
            TableRow tableRow = new TableRow(HanbaiList.this);
            for (int j = 0; j < 4; j++){
                //TextView textView = new TextView(getBaseContext());
                TextView textView = new TextView(HanbaiList.this);
                //textView.setBackgroundResource(R.drawable.shape);
                //textView.setPadding(1,1,1,1);
                textView.setText("(" +i+","+j+")");
                tableRow.addView(textView,j);
            }
            listTable.addView(tableRow,new TableLayout.LayoutParams(MP,WC,1));
        }

    }
}
