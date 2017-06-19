package com.taiwado.taiwado;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tei on 2017/06/14.
 */

public class TrafficDataRepo {
    private TrafficDataInterface dbHelper;

    public TrafficDataRepo(Context context){
        dbHelper = new TrafficDataInterface(context);
    }

    //ID,uname,date,month,day,holiday,holidayType
    public int insertTrafficData(TrafficData trafficData){

        // 调用getWritableDatabase()方法创建或打开一个可以读的数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 创建ContentValues对象
        ContentValues values1 = new ContentValues();
        // 向该对象中插入键值对
        values1.put(TrafficData.KEY_ID, trafficData.ID);
        values1.put(TrafficData.KEY_OBID,trafficData.objectID);
        values1.put(TrafficData.KEY_UNAME,trafficData.uname);
        values1.put(TrafficData.KEY_BEGIN,trafficData.begin);
        values1.put(TrafficData.KEY_END,trafficData.end);
        values1.put(TrafficData.KEY_CASH,trafficData.cash);
        values1.put(TrafficData.KEY_DAY,trafficData.day);
        values1.put(TrafficData.KEY_MONTH,trafficData.month);
        values1.put(TrafficData.KEY_YEAR,trafficData.year);
        values1.put(TrafficData.KEY_DATE,trafficData.date);

        // 调用insert()方法将数据插入到数据库当中
        db.insert(TrafficData.TABLE, null, values1);

        //关闭数据库
        db.close();
        return (int)trafficData.ID;
    }

    public void updateTrafficData(TrafficData trafficData){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TrafficData.KEY_DAY,trafficData.day);
        values.put(TrafficData.KEY_BEGIN,trafficData.begin);
        values.put(TrafficData.KEY_END,trafficData.end);
        values.put(TrafficData.KEY_CASH,trafficData.cash);
        values.put(TrafficData.KEY_DATE,trafficData.date);

        db.update(TrafficData.TABLE,values,TrafficData.KEY_ID+"=?",new String[] {String.valueOf(trafficData.ID)});
        db.close();
    }

    public void updateTrafficID(String trafficObjectID,int ID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TrafficData.KEY_OBID,trafficObjectID);

        db.update(TrafficData.TABLE,values,TrafficData.KEY_ID+"=?",new String[] {String.valueOf(ID)});
        db.close();
    }

    public TrafficData getTrafficDataByID(int id){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+
                TrafficData.KEY_ID + "," +
                TrafficData.KEY_OBID + "," +
                TrafficData.KEY_UNAME + "," +
                TrafficData.KEY_BEGIN + "," +
                TrafficData.KEY_END + "," +
                TrafficData.KEY_CASH + "," +
                TrafficData.KEY_DAY + "," +
                TrafficData.KEY_MONTH + "," +
                TrafficData.KEY_YEAR + "," +
                TrafficData.KEY_DATE + "," +
                " FROM " + TrafficData.TABLE
                + " WHERE " +
                TrafficData.KEY_ID + "=? "
                ;

        TrafficData trafficData = new TrafficData();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()){
            do {
                trafficData.ID = cursor.getInt(cursor.getColumnIndex(TrafficData.KEY_ID));
                trafficData.objectID = cursor.getString(cursor.getColumnIndex(TrafficData.KEY_OBID));
                trafficData.uname = cursor.getString(cursor.getColumnIndex(TrafficData.KEY_UNAME));
                trafficData.begin = cursor.getString(cursor.getColumnIndex(TrafficData.KEY_BEGIN));
                trafficData.end = cursor.getString(cursor.getColumnIndex(TrafficData.KEY_END));
                trafficData.cash = cursor.getInt(cursor.getColumnIndex(TrafficData.KEY_CASH));
                trafficData.day = cursor.getInt(cursor.getColumnIndex(TrafficData.KEY_DAY));
                trafficData.month = cursor.getString(cursor.getColumnIndex(TrafficData.KEY_MONTH));
                trafficData.year = cursor.getString(cursor.getColumnIndex(TrafficData.KEY_YEAR));
                trafficData.date = cursor.getString(cursor.getColumnIndex(TrafficData.KEY_DATE));;

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return trafficData;
    }

    public ArrayList<HashMap<String, String>> getTrafficDataList(String username, String month, String year){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+
                TrafficData.KEY_ID + "," +
                TrafficData.KEY_OBID + "," +
                TrafficData.KEY_UNAME + "," +
                TrafficData.KEY_BEGIN + "," +
                TrafficData.KEY_END + "," +
                TrafficData.KEY_CASH + "," +
                TrafficData.KEY_DAY + "," +
                TrafficData.KEY_MONTH + "," +
                TrafficData.KEY_YEAR + "," +
                TrafficData.KEY_DATE +
                " FROM " + TrafficData.TABLE
                + " WHERE " +
                TrafficData.KEY_UNAME + "=? and " +
                TrafficData.KEY_MONTH + "=? and " +
                TrafficData.KEY_YEAR + "=? "
                ;

        ArrayList<HashMap<String,String>> trafficDatalist = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(username),String.valueOf(month),String.valueOf(year)});
        int count = 40;
        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> hashData = new HashMap<String,String>();
                //hashData.put(cursor.getString(cursor.getColumnIndex(TrafficData.KEY_DAY)),cursor.getString(cursor.getColumnIndex(TrafficData.KEY_FROM)) + "--→" + cursor.getString(cursor.getColumnIndex(TrafficData.KEY_TO)) + "    " + cursor.getString(cursor.getColumnIndex(TrafficData.KEY_CASH)));
                //hashData.put(String.valueOf(count),cursor.getString(cursor.getColumnIndex(TrafficData.KEY_CASH)));
                hashData.put("date",cursor.getString(cursor.getColumnIndex(TrafficData.KEY_DAY)) + " 日");
                hashData.put("begin", "      " + cursor.getString(cursor.getColumnIndex(TrafficData.KEY_BEGIN)));
                hashData.put("end", "      " + cursor.getString(cursor.getColumnIndex(TrafficData.KEY_END)));
                hashData.put("cash", "   " + cursor.getString(cursor.getColumnIndex(TrafficData.KEY_CASH)));
                trafficDatalist.add(hashData);
                count ++;
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return trafficDatalist;
    }

    public String getMonthCash(String username, String month, String year){
        String monthCash = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+
                TrafficData.KEY_ID + "," +
                TrafficData.KEY_OBID + "," +
                TrafficData.KEY_UNAME + "," +
                TrafficData.KEY_BEGIN + "," +
                TrafficData.KEY_END + "," +
                TrafficData.KEY_CASH + "," +
                TrafficData.KEY_DAY + "," +
                TrafficData.KEY_MONTH + "," +
                TrafficData.KEY_YEAR + "," +
                TrafficData.KEY_DATE +
                " FROM " + TrafficData.TABLE
                + " WHERE " +
                TrafficData.KEY_UNAME + "=? and " +
                TrafficData.KEY_MONTH + "=? and " +
                TrafficData.KEY_YEAR + "=? "
                ;

        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(username),String.valueOf(month),String.valueOf(year)});
        int count = 0;
        if(cursor.moveToFirst()){
            do{
                count = cursor.getInt(cursor.getColumnIndex(TrafficData.KEY_CASH)) + count;
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        monthCash = String.valueOf(count);

        return monthCash;
    }

    public int getTrafficID(String username,String month,String year,String day,String begin,String end, String cash){
        int trafficid = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+
                TrafficData.KEY_ID + "," +
                TrafficData.KEY_OBID + "," +
                TrafficData.KEY_UNAME + "," +
                TrafficData.KEY_BEGIN + "," +
                TrafficData.KEY_END + "," +
                TrafficData.KEY_CASH + "," +
                TrafficData.KEY_DAY + "," +
                TrafficData.KEY_MONTH + "," +
                TrafficData.KEY_YEAR + "," +
                TrafficData.KEY_DATE +
                " FROM " + TrafficData.TABLE
                + " WHERE " +
                TrafficData.KEY_UNAME + "=? and " +
                TrafficData.KEY_DAY + "=? and " +
                TrafficData.KEY_MONTH + "=? and " +
                TrafficData.KEY_YEAR + "=? and " +
                TrafficData.KEY_BEGIN + "=? and " +
                TrafficData.KEY_END + "=? and " +
                TrafficData.KEY_CASH + "=? "
                ;

        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(username),String.valueOf(day),String.valueOf(month),String.valueOf(year),String.valueOf(begin),String.valueOf(end),String.valueOf(cash)});
        if(cursor.moveToFirst()){
            do{
                trafficid = cursor.getInt(cursor.getColumnIndex(TrafficData.KEY_ID));
            }while(cursor.moveToNext());
        }
        return trafficid;
    }

    public String getTrafficObjectID(String username,String month,String year,String day,String begin,String end, String cash){
        String trafficObjectid = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+
                TrafficData.KEY_ID + "," +
                TrafficData.KEY_OBID + "," +
                TrafficData.KEY_UNAME + "," +
                TrafficData.KEY_BEGIN + "," +
                TrafficData.KEY_END + "," +
                TrafficData.KEY_CASH + "," +
                TrafficData.KEY_DAY + "," +
                TrafficData.KEY_MONTH + "," +
                TrafficData.KEY_YEAR + "," +
                TrafficData.KEY_DATE +
                " FROM " + TrafficData.TABLE
                + " WHERE " +
                TrafficData.KEY_UNAME + "=? and " +
                TrafficData.KEY_DAY + "=? and " +
                TrafficData.KEY_MONTH + "=? and " +
                TrafficData.KEY_YEAR + "=? and " +
                TrafficData.KEY_BEGIN + "=? and " +
                TrafficData.KEY_END + "=? and " +
                TrafficData.KEY_CASH + "=? "
                ;

        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(username),String.valueOf(day),String.valueOf(month),String.valueOf(year),String.valueOf(begin),String.valueOf(end),String.valueOf(cash)});
        if(cursor.moveToFirst()){
            do{
                trafficObjectid = cursor.getString(cursor.getColumnIndex(TrafficData.KEY_OBID));
            }while(cursor.moveToNext());
        }
        return trafficObjectid;
    }

    public void delete(int ID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TrafficData.TABLE,TrafficData.KEY_ID+"=?", new String[]{String.valueOf(ID)});
        db.close();
    }
}