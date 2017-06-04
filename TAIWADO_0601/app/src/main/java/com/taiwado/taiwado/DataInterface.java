package com.taiwado.taiwado;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tei on 2017/05/31.
 */

public class DataInterface extends SQLiteOpenHelper {

    //数据库版本号
    private static final int DATABASE_VERSION=4;

    //数据库名称
    private static final String DATABASE_NAME="HannbaiData.db";

    public DataInterface(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 创建数据库后，对数据库的操作
        String CREATE_TABLE_HANBAIDATA = "CREATE TABLE " + HanbaiData.TABLE +"("
                +HanbaiData.KEY_ID+"INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +HanbaiData.KEY_CNAME+"TEXT"
                +HanbaiData.KEY_SHIRIZU+"TEXT"
                +HanbaiData.KEY_DATE+"TEXT"
                +HanbaiData.KEY_JAN+"TEXT"
                +HanbaiData.KEY_SYOUHIN+"TEXT"
                +HanbaiData.KEY_KAGAKU+"TEXT"
                +HanbaiData.KEY_KENSUU+"TEXT";
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
