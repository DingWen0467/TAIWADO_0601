package com.taiwado.taiwado;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.idescout.sql.SqlScoutServer;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity {

    public static String nameDB = null;
    public static String passDB = null;

    public static String Bmob_AppId = "396d004b9ddb44265f799ad3d9c7ea5d";
    EditText etUserName, etUserPass;
    CheckBox CheckSave;
    SharedPreferences pref;
    SharedPreferences.Editor edtior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SqlScoutServer.create(this, getPackageName());
        CloseAllActivity.getInstance().addActivity(this);
        init();
        CheckSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edtior.putString("CheckInfo","check");
                    edtior.commit();

                }else {
                    edtior.putString("CheckInfo","notcheck");
                    edtior.commit();
                }
            }
        });
    }

    public void init() {
        Bmob.initialize(this,"396d004b9ddb44265f799ad3d9c7ea5d");
        BmobConfig config = new BmobConfig.Builder(this)
                .setApplicationId("396d004b9ddb44265f799ad3d9c7ea5d")
                .setConnectTimeout(30)
                .setUploadBlockSize(1024*1024)
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);

        etUserName = (EditText) findViewById(R.id.etuserName);
        etUserPass = (EditText) findViewById(R.id.etuserpass);
        CheckSave = (CheckBox) findViewById(R.id.chkSaveName);
        //CheckSave.setChecked(true);
        pref = getSharedPreferences("UserInfo", MODE_PRIVATE);
        edtior = pref.edit();
        nameDB = pref.getString("userName", "");
        passDB = pref.getString("passWord", "");
        String CheckFlag = pref.getString("CheckInfo", "");

       switch (CheckFlag){
            case "notcheck":
                CheckSave.setChecked(false);
                etUserName.setText(null);
                etUserPass.setText(null);
                break;

           case "check":
               CheckSave.setChecked(true);
               etUserName.setText(nameDB);
               etUserPass.setText(passDB);
               etUserName.setSelection(etUserName.getText().length());
               etUserPass.setSelection(etUserPass.getText().length());
               break;

           default:
               break;

        }
    }

    public void doClickOk(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                String name = etUserName.getText().toString().trim();
                String pass = etUserPass.getText().toString().trim();

                if (CheckSave.isChecked()) {

                    queryUserByObjectId(name,pass);

                }else{
                    edtior.remove("userName");
                    edtior.remove("passWord");
                    edtior.commit();

                    queryUserByObjectId(name,pass);
                }
                break;

            default:
                break;
        }

    }

    private void queryUserByObjectId(final String name, final String pass) {
        final String[] str = {null};
        BmobQuery<UserInfo> bmobQuery = new BmobQuery<UserInfo>();
        bmobQuery.addWhereEqualTo("userName",name);
        bmobQuery.addWhereEqualTo("password",pass);
        bmobQuery.setLimit(10);
        bmobQuery.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if (e == null) {
                    for (UserInfo userInfo : list) {
                        str[0] = userInfo.getObjectId();
                    }

                    if ((String)str[0] != null) {
                        edtior.putString("userName", name);
                        edtior.putString("passWord", pass);
                        edtior.putString("ObjectId",str[0]);
                        //etUserName.setText(name);
                        //etUserPass.setText(pass);
                        edtior.commit();
                        StartApp(name,str[0]);
                    }else{
                        etUserName.setText(null);
                        etUserPass.setText(null);
                        Toast.makeText(LoginActivity.this,"用戸不存在！",Toast.LENGTH_LONG).show();
                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }

            }
        });

    }
    private void StartApp(String username,String ObjectId){

        Intent intentMain = new Intent(LoginActivity.this,MainActivity.class);
        intentMain.putExtra("username",username);
        intentMain.putExtra("ObjectId",ObjectId);
        startActivity(intentMain);
        LoginActivity.this.finish();
        //Intent intent = new Intent();
        //intent.putExtra("result",username);
    }

    public void doClickCanccel(View view) {
        Toast.makeText(LoginActivity.this, "Cancel", Toast.LENGTH_SHORT).show();

    }
}
