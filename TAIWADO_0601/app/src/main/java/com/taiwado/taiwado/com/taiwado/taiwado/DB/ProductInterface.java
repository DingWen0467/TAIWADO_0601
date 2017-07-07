package com.taiwado.taiwado.com.taiwado.taiwado.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tei on 2017/06/28.
 */

public class ProductInterface extends SQLiteOpenHelper {
    //数据库版本号
    private static final int DATABASE_VERSION=3;
    //数据库名称
    private static final String DATABASE_NAME="TAIWADO628.db";

    public ProductInterface(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        System.out.println("Create the Database and table");
        String CREATE_TABLE_ProductInfo = "CREATE TABLE " + ProductInfo.TABLE +"("
                +ProductInfo.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +ProductInfo.KEY_OBID+" TEXT, "
                +ProductInfo.KEY_JAN+" TEXT, "
                +ProductInfo.KEY_SHIRIZU+" TEXT, "
                +ProductInfo.KEY_COMMODITY+" TEXT, "
                +ProductInfo.KEY_COUNT+" INTEGER, "
                +ProductInfo.KEY_CASH+" INTEGER)";
        db.execSQL(CREATE_TABLE_ProductInfo);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ ProductInfo.TABLE);

        //再次创建表
        onCreate(db);
    }
}
