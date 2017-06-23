package com.taiwado.taiwado;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by tei on 2017/06/20.
 */

public class StockNumRepo {
    private StockNumInterface dbHelper;

    public StockNumRepo(Context context){
        dbHelper = new StockNumInterface(context);
    }

    public ArrayList<HashMap<String, String>> getStockNum(String JAN){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+
                StockNum.KEY_ID + "," +
                StockNum.KEY_JAN + "," +
                StockNum.KEY_EXITNUM + "," +
                StockNum.KEY_SHIRIZU + "," +
                StockNum.KEY_COMMODITY + "," +
                StockNum.KEY_STORENAME +
                " FROM " + StockNum.TABLE
                + " WHERE " +
                StockNum.KEY_JAN + "=? "
                ;

        ArrayList<HashMap<String,String>> StockNumlist = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(JAN)});
        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> hashData = new HashMap<String,String>();

                hashData.put("name",cursor.getString(cursor.getColumnIndex(StockNum.KEY_STORENAME)) + "  店");
                hashData.put("num",cursor.getString(cursor.getColumnIndex(StockNum.KEY_EXITNUM)) + "  件");

                StockNumlist.add(hashData);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return StockNumlist;
    }

    public int getDataByID(int ID){
        int id = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        StockNum stockNum = new StockNum();
        String selectQuery = "SELECT "+
                StockNum.KEY_ID + "," +
                StockNum.KEY_JAN + "," +
                StockNum.KEY_EXITNUM + "," +
                StockNum.KEY_SHIRIZU + "," +
                StockNum.KEY_COMMODITY + "," +
                StockNum.KEY_STORENAME +
                " FROM " + StockNum.TABLE
                + " WHERE " +
                StockNum.KEY_ID + "=? "
                ;
        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(ID)});
        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex(StockNum.KEY_ID));


            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return id;
    }

    public String getShirizu(String JAN){
        String str = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        StockNum stockNum = new StockNum();
        String selectQuery = "SELECT "+
                StockNum.KEY_ID + "," +
                StockNum.KEY_JAN + "," +
                StockNum.KEY_EXITNUM + "," +
                StockNum.KEY_SHIRIZU + "," +
                StockNum.KEY_COMMODITY + "," +
                StockNum.KEY_STORENAME +
                " FROM " + StockNum.TABLE
                + " WHERE " +
                StockNum.KEY_JAN + "=? "
                ;
        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(JAN)});
        if (cursor.moveToFirst()){
            do {
                str = cursor.getString(cursor.getColumnIndex(StockNum.KEY_SHIRIZU));


            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return str;
    }

    public String getCommodity(String JAN){
        String str = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        StockNum stockNum = new StockNum();
        String selectQuery = "SELECT "+
                StockNum.KEY_ID + "," +
                StockNum.KEY_JAN + "," +
                StockNum.KEY_EXITNUM + "," +
                StockNum.KEY_SHIRIZU + "," +
                StockNum.KEY_COMMODITY + "," +
                StockNum.KEY_STORENAME +
                " FROM " + StockNum.TABLE
                + " WHERE " +
                StockNum.KEY_JAN + "=? "
                ;
        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(JAN)});
        if (cursor.moveToFirst()){
            do {
                str = cursor.getString(cursor.getColumnIndex(StockNum.KEY_COMMODITY));


            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return str;
    }

    public int insertData(StockNum stockNum){

        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        // 创建ContentValues对象
        ContentValues values1 = new ContentValues();
        values1.put(StockNum.KEY_ID,stockNum.getID());
        values1.put(StockNum.KEY_EXITNUM,stockNum.getExitNum());
        values1.put(StockNum.KEY_STORENAME,stockNum.getStoreName());
        values1.put(StockNum.KEY_JAN,stockNum.getJan());
        values1.put(StockNum.KEY_SHIRIZU,stockNum.getShirizu());
        values1.put(StockNum.KEY_COMMODITY,stockNum.getCommodity());
        db.insert(StockNum.TABLE,null,values1);
        db.close();

        return stockNum.getID();
    }

    public void updateCount(StockNum stockNum){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StockNum.KEY_ID,stockNum.getID());
        values.put(StockNum.KEY_EXITNUM,stockNum.getExitNum());
        values.put(StockNum.KEY_STORENAME,stockNum.getStoreName());
        values.put(StockNum.KEY_JAN,stockNum.getJan());
        values.put(StockNum.KEY_SHIRIZU,stockNum.getShirizu());
        values.put(StockNum.KEY_COMMODITY,stockNum.getCommodity());
        db.update(StockNum.TABLE,values,StockNum.KEY_ID+"=?",new String[]{String.valueOf(stockNum.getID())});
        db.close();
    }

    public void queryObject(){
        final BmobQuery<StockNum> bmobQuery = new BmobQuery<StockNum>();
        bmobQuery.addWhereExists("jan");
        bmobQuery.findObjects(new FindListener<StockNum>() {
            @Override
            public void done(List<StockNum> list, BmobException e) {
                if (e == null){
                    for (StockNum stockNum : list){
                        if (getDataByID(stockNum.getID()) == 0){
                            insertData(stockNum);
                        }else {
                            updateCount(stockNum);
                        }
                    }
                }
            }
        });
    }
}
