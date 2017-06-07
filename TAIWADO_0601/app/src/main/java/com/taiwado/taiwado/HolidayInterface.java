package com.taiwado.taiwado;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tei on 2017/06/06.
 */

public class HolidayInterface extends SQLiteOpenHelper {

    //数据库版本号
    private static final int DATABASE_VERSION=4;

    //数据库名称
    private static final String DATABASE_NAME="HolidayData.db";

    public HolidayInterface(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 创建数据库后，对数据库的操作
        String CREATE_TABLE_HolidayData = "CREATE TABLE " + HolidayData.TABLE +"("
                +HolidayData.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +HolidayData.KEY_UNAME+" TEXT, "
                +HolidayData.KEY_TIMEIN+" TEXT, "
                +HolidayData.KEY_TIMEOUT+" TEXT, "
                +HolidayData.KEY_DATE+" TEXT, "
                +HolidayData.KEY_HOLIDAYALL+" TEXT, "
                +HolidayData.KEY_HOLIDAYAM+" TEXT, "
                +HolidayData.KEY_HOLIDAYPM+" TEXT)";
        db.execSQL(CREATE_TABLE_HolidayData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 更改数据库版本的操作
        //如果旧表存在，删除，所以数据将会消失
        db.execSQL("DROP TABLE IF EXISTS "+ HolidayData.TABLE);

        //再次创建表
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        // TODO 每次成功打开数据库后首先被执行
    }

}
