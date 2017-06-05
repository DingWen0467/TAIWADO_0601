package com.taiwado.taiwado;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class KyuukaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyuuka2);
        EditText editText = (EditText)findViewById(R.id.etu_memo);
    }
}
