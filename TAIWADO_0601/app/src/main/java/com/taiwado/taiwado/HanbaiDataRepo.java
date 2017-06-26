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
        values1.put(HanbaiData.KEY_JAN,hanbaiData.getJan());
        values1.put(HanbaiData.KEY_DATE,hanbaiData.getDate());
        values1.put(HanbaiData.KEY_STORENAME,hanbaiData.getStoreName());
        values1.put(HanbaiData.KEY_SHIRIZU,hanbaiData.getShirizu());
        values1.put(HanbaiData.KEY_COMMODITY,hanbaiData.getCommodity());
        values1.put(HanbaiData.KEY_CASH,hanbaiData.getCash());
        values1.put(HanbaiData.KEY_DAY,hanbaiData.getDay());
        values1.put(HanbaiData.KEY_MONTH,hanbaiData.getMonth());
        values1.put(HanbaiData.KEY_YEAR,hanbaiData.getYear());
        values1.put(HanbaiData.KEY_COUNT,hanbaiData.getCount());
        values1.put(HanbaiData.KEY_TAKEN,hanbaiData.getTaken());

        db.insert(HanbaiData.TABLE,null,values1);
        db.close();
        
        return hanbaiData.getID();
    }

    public String getMonthCash(String username, String year, String month,String commodity,String storename){
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
                HanbaiData.KEY_YEAR + "," +
                HanbaiData.KEY_TAKEN +
                " FROM " + HanbaiData.TABLE
                + " WHERE " +
                HanbaiData.KEY_UNAME + "=? and " +
                HanbaiData.KEY_YEAR + "=? and " +
                HanbaiData.KEY_MONTH + "=? and " +
                HanbaiData.KEY_COMMODITY + "=? and " +
                HanbaiData.KEY_STORENAME + "=? "
                ;

        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(username),String.valueOf(year),String.valueOf(month) ,String.valueOf(commodity),String.valueOf(storename)});
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

    public HanbaiData getByID(int ID) {
        HanbaiData hanbaiData= new HanbaiData();
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
                HanbaiData.KEY_YEAR + "," +
                HanbaiData.KEY_TAKEN +
                " FROM " + HanbaiData.TABLE
                + " WHERE " +
                HanbaiData.KEY_ID + "=? ";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(ID)});
        if (cursor.moveToFirst()) {
            do {
                hanbaiData.setID(cursor.getInt(cursor.getColumnIndex(HanbaiData.KEY_ID)));
                hanbaiData.setObjectID(cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_OBID)));
                hanbaiData.setDate(cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_DATE)));
                hanbaiData.setStoreName(cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_STORENAME)));
                hanbaiData.setJan(cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_JAN)));
                hanbaiData.setShirizu(cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_SHIRIZU)));
                hanbaiData.setCount(cursor.getInt(cursor.getColumnIndex(HanbaiData.KEY_COUNT)));
                hanbaiData.setCash(cursor.getInt(cursor.getColumnIndex(HanbaiData.KEY_CASH)));
                hanbaiData.setCommodity(cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_COMMODITY)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


        return hanbaiData;
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

        public void updateData(HanbaiData hanbaiData){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(HanbaiData.KEY_DATE,hanbaiData.getDate());
            values.put(HanbaiData.KEY_JAN,hanbaiData.getJan());
            values.put(HanbaiData.KEY_CASH,hanbaiData.getCash());
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
    
    public ArrayList<HashMap<String, String>> getHanbaiList(String username, String year ,String month, String storename,String shirizu){
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
                HanbaiData.KEY_YEAR + "," +
                HanbaiData.KEY_DATE +
                " FROM " + HanbaiData.TABLE
                + " WHERE " +
                HanbaiData.KEY_UNAME + "=? and " +
                HanbaiData.KEY_YEAR + "=? and " +
                HanbaiData.KEY_MONTH + "=? and " +
                HanbaiData.KEY_STORENAME + "=? and " +
                HanbaiData.KEY_SHIRIZU + "=? "
                ;

        ArrayList<HashMap<String,String>> hanbailist = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(username),String.valueOf(year),String.valueOf(month),String.valueOf(storename),String.valueOf(shirizu)});

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> hashData = new HashMap<String,String>();
                hashData.put("date",cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_DAY)));
                hashData.put("jan",cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_JAN)));
                hashData.put("cash", String.valueOf(cursor.getInt(cursor.getColumnIndex(HanbaiData.KEY_CASH))));
                hashData.put("count", String.valueOf(cursor.getInt(cursor.getColumnIndex(HanbaiData.KEY_COUNT))));
                hanbailist.add(hashData);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return hanbailist;
    }
    public int getHanbaiID(String username, String date, String jan,String cash,int count,String storename){
        int HanbaiID = 0;
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
                HanbaiData.KEY_YEAR + "," +
                HanbaiData.KEY_TAKEN +
                " FROM " + HanbaiData.TABLE
                + " WHERE " +
                HanbaiData.KEY_UNAME + "=? and " +
                HanbaiData.KEY_DAY + "=? and " +
                HanbaiData.KEY_JAN + "=? and " +
                HanbaiData.KEY_CASH + "=? and " +
                HanbaiData.KEY_COUNT + "=? and " +
                HanbaiData.KEY_STORENAME + "=? "
                ;

        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(username),String.valueOf(date),String.valueOf(jan) ,String.valueOf(cash),String.valueOf(count),String.valueOf(storename)});
        if(cursor.moveToFirst()){
            do{
                HanbaiID = cursor.getInt(cursor.getColumnIndex(HanbaiData.KEY_ID));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return HanbaiID;
    }
    public String getHanbaiObjectID(int id,String username, String date, String jan,String cash,int count,String storename){
        String  HanbaiObjectID = null;
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
                HanbaiData.KEY_YEAR + "," +
                HanbaiData.KEY_TAKEN +
                " FROM " + HanbaiData.TABLE
                + " WHERE " +
                HanbaiData.KEY_ID + "=? and " +
                HanbaiData.KEY_UNAME + "=? and " +
                HanbaiData.KEY_DAY + "=? and " +
                HanbaiData.KEY_JAN + "=? and " +
                HanbaiData.KEY_CASH + "=? and " +
                HanbaiData.KEY_COUNT + "=? and " +
                HanbaiData.KEY_STORENAME + "=? "
                ;

        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(id),String.valueOf(username),String.valueOf(date),String.valueOf(jan) ,String.valueOf(cash),String.valueOf(count),String.valueOf(storename)});
        if(cursor.moveToFirst()){
            do{
                HanbaiObjectID = cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_OBID));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return HanbaiObjectID;
    }
    public void delete(int ID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(HanbaiData.TABLE,HanbaiData.KEY_ID + "=?",new String[]{String.valueOf(ID)});
        db.close();
    }

    }
