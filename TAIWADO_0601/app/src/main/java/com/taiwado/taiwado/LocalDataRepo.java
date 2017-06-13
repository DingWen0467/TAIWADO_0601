package com.taiwado.taiwado;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tei on 2017/06/12.
 */

public class LocalDataRepo {
    private LocalDataInterface dbHelper;

    public LocalDataRepo(Context context){
        dbHelper = new LocalDataInterface(context);
    }

    //ID,uname,date,month,day,holiday,holidayType
    public void insertData(LocalData localData){

        // 调用getWritableDatabase()方法创建或打开一个可以读的数据库
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        // 创建ContentValues对象
        ContentValues values1 = new ContentValues();
        // 向该对象中插入键值对
        values1.put(LocalData.KEY_ID, localData.ID);
        values1.put(LocalData.KEY_OBID,localData.objectID);
        values1.put(LocalData.KEY_UNAME,localData.uname);
        values1.put(LocalData.KEY_TIMEIN,localData.timein);
        values1.put(LocalData.KEY_TIMEOUT,localData.timeout);
        values1.put(LocalData.KEY_DAY,localData.day);
        values1.put(LocalData.KEY_DATE,localData.date);
        values1.put(LocalData.KEY_MONTH,localData.month);
        values1.put(LocalData.KEY_HOLIDAY,localData.holiday);
        values1.put(LocalData.KEY_HOLIDAYTYPE,localData.holidaytype);
        values1.put(LocalData.KEY_WORK,localData.work);

        // 调用insert()方法将数据插入到数据库当中
        db.insert(LocalData.TABLE, null, values1);

        //关闭数据库
        db.close();
        }

    public void updateHoliday(LocalData localData){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LocalData.KEY_TIMEIN,localData.timein);
        values.put(LocalData.KEY_TIMEOUT,localData.timeout);
        values.put(LocalData.KEY_HOLIDAY,localData.holiday);
        values.put(LocalData.KEY_HOLIDAYTYPE,localData.holidaytype);
        values.put(LocalData.KEY_WORK,localData.work);

        db.update(LocalData.TABLE,values,LocalData.KEY_ID+"=? and ",new String[] {String.valueOf(localData.ID)});
        db.close();
        }

    public LocalData getLocalDataByID(int id){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+
                LocalData.KEY_ID + "," +
                LocalData.KEY_OBID + "," +
                LocalData.KEY_UNAME + "," +
                LocalData.KEY_TIMEIN + "," +
                LocalData.KEY_TIMEOUT + "," +
                LocalData.KEY_DAY + "," +
                LocalData.KEY_MONTH + "," +
                LocalData.KEY_DATE + "," +
                LocalData.KEY_HOLIDAY + "," +
                LocalData.KEY_HOLIDAYTYPE + "," +
                LocalData.KEY_WORK +
                " FROM " + LocalData.TABLE
                + " WHERE " +
                LocalData.KEY_ID + "=? "
                ;

        LocalData localData = new LocalData();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()){
            do {
                localData.ID = cursor.getInt(cursor.getColumnIndex(LocalData.KEY_ID));
                localData.objectID = cursor.getString(cursor.getColumnIndex(LocalData.KEY_OBID));
                localData.uname = cursor.getString(cursor.getColumnIndex(LocalData.KEY_UNAME));
                localData.timein = cursor.getString(cursor.getColumnIndex(LocalData.KEY_TIMEIN));
                localData.timeout = cursor.getString(cursor.getColumnIndex(LocalData.KEY_TIMEOUT));
                localData.day = cursor.getInt(cursor.getColumnIndex(LocalData.KEY_DAY));
                localData.month = cursor.getString(cursor.getColumnIndex(LocalData.KEY_MONTH));
                localData.date = cursor.getString(cursor.getColumnIndex(LocalData.KEY_DATE));
                localData.holiday = cursor.getString(cursor.getColumnIndex(LocalData.KEY_HOLIDAY));
                localData.holidaytype = cursor.getString(cursor.getColumnIndex(LocalData.KEY_HOLIDAYTYPE));
                localData.work = cursor.getString(cursor.getColumnIndex(LocalData.KEY_WORK));

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return localData;
    }

    public ArrayList<HashMap<String, String>> getLocaldataList(String username , String month, String date){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+
                LocalData.KEY_ID + "," +
                LocalData.KEY_OBID + "," +
                LocalData.KEY_UNAME + "," +
                LocalData.KEY_TIMEIN + "," +
                LocalData.KEY_TIMEOUT + "," +
                LocalData.KEY_DAY + "," +
                LocalData.KEY_MONTH + "," +
                LocalData.KEY_DATE + "," +
                LocalData.KEY_HOLIDAY + "," +
                LocalData.KEY_HOLIDAYTYPE + "," +
                LocalData.KEY_WORK +
                " FROM " + LocalData.TABLE
                + " WHERE " +
                LocalData.KEY_UNAME + "=? and " +
                LocalData.KEY_DATE + "=? and " +
                LocalData.KEY_MONTH + "=? "
                ;

        ArrayList<HashMap<String,String>> localdatalist = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(username),String.valueOf(date),String.valueOf(month)});

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> hashData = new HashMap<String,String>();
                hashData.put("day", String.valueOf(cursor.getInt(cursor.getColumnIndex(LocalData.KEY_DAY))));
                hashData.put("holiday",cursor.getString(cursor.getColumnIndex(LocalData.KEY_HOLIDAY)));
                localdatalist.add(hashData);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return localdatalist;
    }
    }

