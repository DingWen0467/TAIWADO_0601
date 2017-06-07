package com.taiwado.taiwado;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tei on 2017/06/06.
 */

public class HolidayRepo {

    private HolidayInterface dbHelper;

    public HolidayRepo(Context context){
        dbHelper = new HolidayInterface(context);
    }

    public int Insert(HolidayData HolidayData) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HolidayData.KEY_ID, HolidayData.ID);
        values.put(HolidayData.KEY_UNAME,HolidayData.uname);
        values.put(HolidayData.KEY_TIMEIN,HolidayData.timein);
        values.put(HolidayData.KEY_TIMEOUT,HolidayData.timeout);
        values.put(HolidayData.KEY_DATE,HolidayData.date);
        values.put(HolidayData.KEY_HOLIDAYALL,HolidayData.holidayall);
        values.put(HolidayData.KEY_HOLIDAYAM,HolidayData.holidayam);
        values.put(HolidayData.KEY_HOLIDAYPM,HolidayData.holidaypm);

        long Holiday_ID = db.insert(HolidayData.TABLE, null, values);
        db.close();
        return (int)Holiday_ID;
    }

    public void Delete(String HolidayData_ID,String HolidayData_uname){

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(HolidayData.TABLE,HolidayData.KEY_ID+"=?", new String[]{String.valueOf(HolidayData_ID)});
        db.close();
    }

    public void update(HolidayData holidayData){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HolidayData.KEY_ID,holidayData.ID);
        values.put(HolidayData.KEY_UNAME,holidayData.uname);
        values.put(HolidayData.KEY_TIMEIN,holidayData.timein);
        values.put(HolidayData.KEY_TIMEOUT,holidayData.timeout);
        values.put(HolidayData.KEY_DATE,holidayData.date);
        values.put(HolidayData.KEY_HOLIDAYALL,holidayData.holidayall);
        values.put(HolidayData.KEY_HOLIDAYAM,holidayData.holidayam);
        values.put(HolidayData.KEY_HOLIDAYPM,holidayData.holidaypm);

        db.update(HolidayData.TABLE,values,HolidayData.KEY_ID+"=?",new String[]{String.valueOf(holidayData.ID)});
        db.close();
    }

    public ArrayList<HashMap<String,String>> getHolidayData(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();

        String selectQuery="SELECT "+
                HolidayData.KEY_ID+","+
                HolidayData.KEY_UNAME+","+
                HolidayData.KEY_TIMEIN+","+
                HolidayData.KEY_TIMEOUT+","+
                HolidayData.KEY_DATE+","+
                HolidayData.KEY_HOLIDAYALL+","+
                HolidayData.KEY_HOLIDAYAM+","+
                HolidayData.KEY_HOLIDAYAM+" FROM "+HolidayData.TABLE;

        ArrayList<HashMap<String,String>> HolidayDataList = new ArrayList<HashMap<String, String>>();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> Holiday=new HashMap<String,String>();
                Holiday.put("id",cursor.getString(cursor.getColumnIndex(HolidayData.KEY_ID)));

                HolidayDataList.add(Holiday);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return HolidayDataList;
    }

    public HolidayData getHolidayDataById(int Id){

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                HolidayData.KEY_ID + "," +
                HolidayData.KEY_UNAME + "," +
                HolidayData.KEY_TIMEIN + "," +
                HolidayData.KEY_TIMEOUT + "," +
                HolidayData.KEY_DATE + "," +
                HolidayData.KEY_HOLIDAYALL + "," +
                HolidayData.KEY_HOLIDAYAM + "," +
                HolidayData.KEY_HOLIDAYAM +
                " FROM " + HolidayData.TABLE
                + " WHERE " +
                HolidayData.KEY_ID + "=?";

        int iCount=0;
        HolidayData HolidayData = new HolidayData();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(Id)});
        if (cursor.moveToFirst()){
            do {
                HolidayData.ID =cursor.getInt(cursor.getColumnIndex(HolidayData.KEY_ID));
                HolidayData.uname =cursor.getString(cursor.getColumnIndex(HolidayData.KEY_UNAME));
                HolidayData.timein  =cursor.getString(cursor.getColumnIndex(HolidayData.KEY_TIMEIN));
                HolidayData.timeout  =cursor.getString(cursor.getColumnIndex(HolidayData.KEY_TIMEOUT));
                HolidayData.date  =cursor.getString(cursor.getColumnIndex(HolidayData.KEY_DATE));
                HolidayData.holidayall  =cursor.getString(cursor.getColumnIndex(HolidayData.KEY_HOLIDAYALL));
                HolidayData.holidayam  =cursor.getString(cursor.getColumnIndex(HolidayData.KEY_HOLIDAYAM));
                HolidayData.holidaypm =cursor.getString(cursor.getColumnIndex(HolidayData.KEY_HOLIDAYPM));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return HolidayData;
    }

    public HolidayData getHolidayDataByUname(String Uname,String Nowdate){

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                HolidayData.KEY_ID + "," +
                HolidayData.KEY_UNAME + "," +
                HolidayData.KEY_TIMEIN + "," +
                HolidayData.KEY_TIMEOUT + "," +
                HolidayData.KEY_DATE + "," +
                HolidayData.KEY_HOLIDAYALL + "," +
                HolidayData.KEY_HOLIDAYAM + "," +
                HolidayData.KEY_HOLIDAYAM +
                " FROM " + HolidayData.TABLE
                + " WHERE " +
                HolidayData.KEY_UNAME + "=?"
                + "AND" +
                HolidayData.KEY_DATE + "=?"
                ;

        int iCount=0;
        HolidayData HolidayData = new HolidayData();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{Uname});
        if (cursor.moveToFirst()){
            do {
                HolidayData.ID =cursor.getInt(cursor.getColumnIndex(HolidayData.KEY_ID));
                HolidayData.uname =cursor.getString(cursor.getColumnIndex(HolidayData.KEY_UNAME));
                HolidayData.timein  =cursor.getString(cursor.getColumnIndex(HolidayData.KEY_TIMEIN));
                HolidayData.timeout  =cursor.getString(cursor.getColumnIndex(HolidayData.KEY_TIMEOUT));
                HolidayData.date  =cursor.getString(cursor.getColumnIndex(HolidayData.KEY_DATE));
                HolidayData.holidayall  =cursor.getString(cursor.getColumnIndex(HolidayData.KEY_HOLIDAYALL));
                HolidayData.holidayam  =cursor.getString(cursor.getColumnIndex(HolidayData.KEY_HOLIDAYAM));
                HolidayData.holidaypm =cursor.getString(cursor.getColumnIndex(HolidayData.KEY_HOLIDAYPM));
            }while (cursor.moveToNext());
        }

        return HolidayData;
    }
}
