package com.taiwado.taiwado;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class InsertData extends AppCompatActivity {


    private static final String DATABASE_NAME = "Taiwado.db";
    private static final int DATABASE_VERSION = 4;
    private static final String TABLE_NAME = "HanbaiList";
    private String ID = null;
    private EditText editDate;
    private EditText editJAN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        editDate = (EditText)findViewById(R.id.edit_date);
        editJAN = (EditText)findViewById(R.id.edit_JAN);
    }

    public void onClickOK(View v){
        switch (v.getId()) {
            case R.id.data_insert:


                break;

            case R.id.data_cancle:

                break;

            default:
                break;
        }
    }

}
