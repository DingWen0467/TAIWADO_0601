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
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by tei on 2017/06/22.
 */

public class HanbaiDataRepo {
    private HanbaiDataInterface dbHelper;

    public HanbaiDataRepo(Context context){
        dbHelper = new HanbaiDataInterface(context);
    }
    public int insertHanbaiData(HanbaiData hanbaiData){
        // 调用getWritableDatabase()方法创建或打开一个可以读的数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 创建ContentValues对象
        ContentValues values1 = new ContentValues();
        // 向该对象中插入键值对
        values1.put(HanbaiData.KEY_ID,hanbaiData.getID());
        values1.put(HanbaiData.KEY_OBID,hanbaiData.getObjectID());
        values1.put(HanbaiData.KEY_UNAME,hanbaiData.getUsername());
        values1.put(HanbaiData.KEY_DATE,hanbaiData.getDate());
        values1.put(HanbaiData.KEY_STORENAME,hanbaiData.getStoreName());
        values1.put(HanbaiData.KEY_SHIRIZU,hanbaiData.getShirizu());
        values1.put(HanbaiData.KEY_COMMODITY,hanbaiData.getCommodity());
        values1.put(HanbaiData.KEY_CASH,hanbaiData.getCash());
        values1.put(HanbaiData.KEY_DAY,hanbaiData.getDay());
        values1.put(HanbaiData.KEY_MONTH,hanbaiData.getMonth());
        values1.put(HanbaiData.KEY_COUNT,hanbaiData.getCount());
        values1.put(HanbaiData.KEY_TAKEN,hanbaiData.getTaken());

        db.insert(HanbaiData.TABLE,null,values1);
        db.close();
        
        return hanbaiData.getID();
    }

    public String CloudHanbaiData(HanbaiData hanbaiData,int ID){
        final String[] objectId = {null};
        hanbaiData.setID(ID);
        hanbaiData.save(new SaveListener<String>() {
            @Override
            public void done(String objectid, BmobException e) {
                if (e == null){
                    objectId[0] = objectid;
                }
            }
        });
        return String.valueOf(objectId[0]);
    }
    public String getMonthCash(String username, String date, String storename,String jan){
        String monthCash = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+
                HanbaiData.KEY_ID + "," +
                HanbaiData.KEY_OBID + "," +
                HanbaiData.KEY_UNAME + "," +
                HanbaiData.KEY_DATE + "," +
                HanbaiData.KEY_STORENAME + "," +
                HanbaiData.KEY_JAN + "," +
                HanbaiData.KEY_SHIRIZU + "," +
                HanbaiData.KEY_COMMODITY + "," +
                HanbaiData.KEY_CASH + "," +
                HanbaiData.KEY_COUNT + "," +
                HanbaiData.KEY_DAY + "," +
                HanbaiData.KEY_MONTH + "," +
                HanbaiData.KEY_TAKEN +
                " FROM " + HanbaiData.TABLE
                + " WHERE " +
                HanbaiData.KEY_UNAME + "=? and " +
                HanbaiData.KEY_DATE + "=? and " +
                HanbaiData.KEY_JAN + "=? and " +
                HanbaiData.KEY_STORENAME + "=? "
                ;

        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(username),String.valueOf(date),String.valueOf(storename) ,String.valueOf(jan)});
        int count = 0;
        if(cursor.moveToFirst()){
            do{
                count = cursor.getInt(cursor.getColumnIndex(HanbaiData.KEY_CASH)) + count;
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        monthCash = String.valueOf(count);

        return monthCash;
    }

    public String getByID(int ID) {
        String monthCash = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT " +
                HanbaiData.KEY_ID + "," +
                HanbaiData.KEY_OBID + "," +
                HanbaiData.KEY_UNAME + "," +
                HanbaiData.KEY_DATE + "," +
                HanbaiData.KEY_STORENAME + "," +
                HanbaiData.KEY_JAN + "," +
                HanbaiData.KEY_SHIRIZU + "," +
                HanbaiData.KEY_COMMODITY + "," +
                HanbaiData.KEY_CASH + "," +
                HanbaiData.KEY_COUNT + "," +
                HanbaiData.KEY_DAY + "," +
                HanbaiData.KEY_MONTH + "," +
                HanbaiData.KEY_TAKEN +
                " FROM " + HanbaiData.TABLE
                + " WHERE " +
                HanbaiData.KEY_UNAME + "=? and " +
                HanbaiData.KEY_DATE + "=? and " +
                HanbaiData.KEY_JAN + "=? and " +
                HanbaiData.KEY_STORENAME + "=? ";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(ID)});
        int count = 0;
        if (cursor.moveToFirst()) {
            do {
                count = cursor.getInt(cursor.getColumnIndex(HanbaiData.KEY_CASH)) + count;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        monthCash = String.valueOf(count);

        return monthCash;
    }
    public void queryObject(){
        final BmobQuery<HanbaiData> bmobQuery = new BmobQuery<HanbaiData>();
        bmobQuery.addWhereExists("jan");
        bmobQuery.findObjects(new FindListener<HanbaiData>() {
            @Override
            public void done(List<HanbaiData> list, BmobException e) {
                if (e == null){
                    for (HanbaiData hanbaiData : list){

                    }
                }
            }
        });
        }

        public void updateCount(HanbaiData hanbaiData){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(HanbaiData.KEY_COUNT,hanbaiData.getCount());
            db.update(HanbaiData.TABLE,values,HanbaiData.KEY_ID+"=?",new String[]{String.valueOf(hanbaiData.getID())});
            db.close();
        }

    public void updateObjectID(String objectId,int ID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HanbaiData.KEY_OBID,objectId);
        db.update(HanbaiData.TABLE,values,HanbaiData.KEY_ID+"=?",new String[]{String.valueOf(ID)});
        db.close();
    }
    
    public ArrayList<HashMap<String, String>> getHanbaiList(String username, String date ,String month, String storename,String shirizu){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+
                HanbaiData.KEY_ID + "," +
                HanbaiData.KEY_OBID + "," +
                HanbaiData.KEY_UNAME + "," +
                HanbaiData.KEY_STORENAME + "," +
                HanbaiData.KEY_CASH + "," +
                HanbaiData.KEY_JAN + "," +
                HanbaiData.KEY_SHIRIZU + "," +
                HanbaiData.KEY_COMMODITY + "," +
                HanbaiData.KEY_COUNT + "," +
                HanbaiData.KEY_TAKEN + "," +
                HanbaiData.KEY_DAY + "," +
                HanbaiData.KEY_MONTH + "," +
                HanbaiData.KEY_DATE +
                " FROM " + HanbaiData.TABLE
                + " WHERE " +
                HanbaiData.KEY_UNAME + "=? and " +
                HanbaiData.KEY_DATE + "=? and " +
                HanbaiData.KEY_MONTH + "=? and " +
                HanbaiData.KEY_STORENAME + "=? and " +
                HanbaiData.KEY_SHIRIZU + "=? "
                ;

        ArrayList<HashMap<String,String>> hanbailist = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(username),String.valueOf(date),String.valueOf(month),String.valueOf(storename),String.valueOf(shirizu)});
        int count = 40;
        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> hashData = new HashMap<String,String>();
                //hashData.put(cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_DAY)),cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_FROM)) + "--→" + cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_TO)) + "    " + cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_CASH)));
                //hashData.put(String.valueOf(count),cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_CASH)));
                hashData.put("date",cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_DAY)));
                hashData.put("jan",cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_JAN)));
                hashData.put("name",cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_COMMODITY)));
                hashData.put("count", String.valueOf(cursor.getInt(cursor.getColumnIndex(HanbaiData.KEY_COUNT))));
                hanbailist.add(hashData);
                count ++;
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return hanbailist;
    }
    
    }
