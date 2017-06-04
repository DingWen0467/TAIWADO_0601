package com.taiwado.taiwado;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tei on 2017/05/31.
 */

public class HanbaiRepo {

    private DataInterface dbHelper;

    public HanbaiRepo(Context context){
        dbHelper = new DataInterface(context);
    }

    public void Insert(HanbaiData hanbaiData) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HanbaiData.KEY_ID, hanbaiData.ID);
        values.put(HanbaiData.KEY_CNAME,hanbaiData.cname);
        values.put(HanbaiData.KEY_SHIRIZU,hanbaiData.shirizu);
        values.put(HanbaiData.KEY_DATE,hanbaiData.date);
        values.put(HanbaiData.KEY_JAN,hanbaiData.jan);
        values.put(HanbaiData.KEY_SYOUHIN,hanbaiData.syouhin);
        values.put(HanbaiData.KEY_KAGAKU,hanbaiData.syouhin);
        values.put(HanbaiData.KEY_KENSUU,hanbaiData.kennsuu);

        db.insert(HanbaiData.TABLE, null, values);

    }

    public void Delete(String HanbaiData_ID){

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(HanbaiData.TABLE,HanbaiData.KEY_ID+"=?", new String[]{String.valueOf(HanbaiData_ID)});
        db.close();
    }

    public ArrayList<HashMap<String,String>> getHanbaiDataList(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();

        String selectQuery="SELECT "+
                HanbaiData.KEY_ID+","+
                HanbaiData.KEY_CNAME+","+
                HanbaiData.KEY_SHIRIZU+","+
                HanbaiData.KEY_DATE+","+
                HanbaiData.KEY_JAN+","+
                HanbaiData.KEY_SYOUHIN+","+
                HanbaiData.KEY_KAGAKU+","+
                HanbaiData.KEY_KENSUU+" FROM "+HanbaiData.TABLE;

        ArrayList<HashMap<String,String>> HanbaiDataList = new ArrayList<HashMap<String, String>>();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> Hanbai=new HashMap<String,String>();
                Hanbai.put("id",cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_ID)));

                HanbaiDataList.add(Hanbai);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return HanbaiDataList;
    }

    public HanbaiData getHanbaiDataById(String Id){

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                HanbaiData.KEY_ID + "," +
                HanbaiData.KEY_CNAME + "," +
                HanbaiData.KEY_SHIRIZU + "," +
                HanbaiData.KEY_DATE + "," +
                HanbaiData.KEY_JAN + "," +
                HanbaiData.KEY_SYOUHIN + "," +
                HanbaiData.KEY_KAGAKU + "," +
                HanbaiData.KEY_KENSUU +
                " FROM " + HanbaiData.TABLE
                + " WHERE " +
                HanbaiData.KEY_ID + "=?";

        int iCount=0;
        HanbaiData HanbaiData = new HanbaiData();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{Id});
        if (cursor.moveToFirst()){
            do {
                HanbaiData.ID =cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_ID));
                HanbaiData.cname =cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_CNAME));
                HanbaiData.shirizu  =cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_SHIRIZU));
                HanbaiData.date  =cursor.getInt(cursor.getColumnIndex(HanbaiData.KEY_DATE));
                HanbaiData.jan  =cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_JAN));
                HanbaiData.syouhin  =cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_SYOUHIN));
                HanbaiData.kagaku  =cursor.getString(cursor.getColumnIndex(HanbaiData.KEY_KAGAKU));
                HanbaiData.kennsuu =cursor.getInt(cursor.getColumnIndex(HanbaiData.KEY_KENSUU));
            }while (cursor.moveToNext());
        }

        return HanbaiData;
    }
}
