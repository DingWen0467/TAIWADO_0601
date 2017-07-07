package com.taiwado.taiwado.com.taiwado.taiwado.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tei on 2017/06/29.
 */

public class BarashiInterface extends SQLiteOpenHelper {
    //数据库版本号
    private static final int DATABASE_VERSION=3;
    //数据库名称
    private static final String DATABASE_NAME="TAIWADO630.db";

    public BarashiInterface(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Create the Database and table");
        String CREATE_TABLE_BarashiInfo = "CREATE TABLE " + BarashiInfo.TABLE +"("
                +BarashiInfo.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +BarashiInfo.KEY_OBID+" TEXT, "
                +BarashiInfo.KEY_JAN+" TEXT, "
                +BarashiInfo.KEY_BARASHI_CODE+" TEXT, "
                +BarashiInfo.KEY_BARASHI_COMMODITY+" TEXT, "
                +BarashiInfo.KEY_COUNT+" INTEGER)";
        db.execSQL(CREATE_TABLE_BarashiInfo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 更改数据库版本的操作
        //如果旧表存在，删除，所以数据将会消失
        db.execSQL("DROP TABLE IF EXISTS "+ BarashiInfo.TABLE);

        //再次创建表
        onCreate(db);
    }
}
