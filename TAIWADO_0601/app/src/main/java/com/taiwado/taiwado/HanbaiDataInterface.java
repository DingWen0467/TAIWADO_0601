package com.taiwado.taiwado;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tei on 2017/06/22.
 */

public class HanbaiDataInterface  extends SQLiteOpenHelper{
    //数据库版本号
    private static final int DATABASE_VERSION=3;
    //数据库名称
    private static final String DATABASE_NAME="TAIWADO621.db";
    
    public HanbaiDataInterface(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 创建数据库后，对数据库的操作
        System.out.println("Create the Database and table");
        String CREATE_TABLE_HanbaiData = "CREATE TABLE " + HanbaiData.TABLE +"("
                +HanbaiData.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +HanbaiData.KEY_OBID+" TEXT, "
                +HanbaiData.KEY_UNAME+" TEXT, "
                +HanbaiData.KEY_DATE+" TEXT, "
                +HanbaiData.KEY_STORENAME+" TEXT, "
                +HanbaiData.KEY_JAN+" TEXT, "
                +HanbaiData.KEY_SHIRIZU+" TEXT, "
                +HanbaiData.KEY_COMMODITY+" TEXT, "
                +HanbaiData.KEY_YEAR+" TEXT, "
                +HanbaiData.KEY_MONTH+" TEXT, "
                +HanbaiData.KEY_DAY+" TEXT, "
                +HanbaiData.KEY_CASH+" INTEGER, "
                +HanbaiData.KEY_COUNT+" INTEGER, "
                +HanbaiData.KEY_TAKEN+" TEXT)";
        db.execSQL(CREATE_TABLE_HanbaiData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 更改数据库版本的操作
        //如果旧表存在，删除，所以数据将会消失
        db.execSQL("DROP TABLE IF EXISTS "+ HanbaiData.TABLE);

        //再次创建表
        onCreate(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        // TODO 每次成功打开数据库后首先被执行
    }
}
