package com.taiwado.taiwado.com.taiwado.taiwado.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tei on 2017/06/29.
 */

public class SaleGruopInterface extends SQLiteOpenHelper {

    //数据库版本号
    private static final int DATABASE_VERSION=3;
    //数据库名称
    private static final String DATABASE_NAME="TAIWADO629.db";

    public SaleGruopInterface(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // TODO 创建数据库后，对数据库的操作
        System.out.println("Create the Database and table");
        String CREATE_TABLE_SaleGroupInfo = "CREATE TABLE " + SaleGroupInfo.TABLE +"("
                +SaleGroupInfo.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +SaleGroupInfo.KEY_OBID+" TEXT, "
                +SaleGroupInfo.KEY_JAN+" TEXT, "
                +SaleGroupInfo.KEY_SHIRIZU+" TEXT, "
                +SaleGroupInfo.KEY_PRESENT+" TEXT, "
                +SaleGroupInfo.KEY_PRESENT_CODE+" TEXT, "
                +SaleGroupInfo.KEY_BARASHI+" TEXT, "
                +SaleGroupInfo.KEY_BARASHI_CODE+" TEXT, "
                +SaleGroupInfo.KEY_COMMODITY+" TEXT, "
                +SaleGroupInfo.KEY_CASH+" INTEGER)";
        db.execSQL(CREATE_TABLE_SaleGroupInfo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
