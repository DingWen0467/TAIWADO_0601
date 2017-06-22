package com.taiwado.taiwado;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tei on 2017/06/20.
 */

public class StockNumInterface extends SQLiteOpenHelper {
    //数据库版本号
    private static final int DATABASE_VERSION=3;
    //数据库名称
    private static final String DATABASE_NAME="TAIWADO620.db";
    
    public StockNumInterface(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 创建数据库后，对数据库的操作
        System.out.println("Create the Database and table");
        String CREATE_TABLE_StockNum = "CREATE TABLE " + StockNum.TABLE +"("
                +StockNum.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +StockNum.KEY_JAN+" TEXT, "
                +StockNum.KEY_EXITNUM+" INTEGER, "
                +StockNum.KEY_SHIRIZU+" TEXT, "
                +StockNum.KEY_COMMODITY+" TEXT, "
                +StockNum.KEY_STORENAME+" TEXT)";
        db.execSQL(CREATE_TABLE_StockNum);
    }
    //数据库升级时调用
    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade（）方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 更改数据库版本的操作
        //如果旧表存在，删除，所以数据将会消失
        db.execSQL("DROP TABLE IF EXISTS "+ StockNum.TABLE);

        //再次创建表
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        // TODO 每次成功打开数据库后首先被执行
    }
}
