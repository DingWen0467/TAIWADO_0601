package com.taiwado.taiwado.com.taiwado.taiwado.DB;

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
 * Created by tei on 2017/06/29.
 */

public class BarashiRepo {
    private BarashiInterface dbHelper;

    public BarashiRepo(Context context){
        dbHelper = new BarashiInterface(context);
    }

    public ArrayList<HashMap<String,String>> getBarashiList(String barashicode){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+
                BarashiInfo.KEY_ID + "," +
                BarashiInfo.KEY_OBID + "," +
                BarashiInfo.KEY_JAN + "," +
                BarashiInfo.KEY_BARASHI_CODE + "," +
                BarashiInfo.KEY_BARASHI_COMMODITY + "," +
                BarashiInfo.KEY_COUNT +
                " FROM " + BarashiInfo.TABLE
                + " WHERE " +

                BarashiInfo.KEY_BARASHI_CODE + "=? "
                ;

        ArrayList<HashMap<String,String>> barashiList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(barashicode)});

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> hashData = new HashMap<String,String>();
                hashData.put("barashicode",cursor.getString(cursor.getColumnIndex(BarashiInfo.KEY_JAN)));
                barashiList.add(hashData);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return barashiList;

    }

    public int insertData(BarashiInfo barashiInfo){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BarashiInfo.KEY_ID,barashiInfo.getID());
        values.put(BarashiInfo.KEY_OBID,barashiInfo.getObjectId());
        values.put(BarashiInfo.KEY_JAN,barashiInfo.getJan());
        values.put(BarashiInfo.KEY_BARASHI_CODE,barashiInfo.getBarashi_code());
        values.put(BarashiInfo.KEY_BARASHI_COMMODITY,barashiInfo.getBarashi_commodity());
        values.put(BarashiInfo.KEY_COUNT,barashiInfo.getCount());
        db.insert(BarashiInfo.TABLE,null,values);
        db.close();

        return barashiInfo.getID();
    }

    public void updateData(BarashiInfo barashiInfo){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BarashiInfo.KEY_ID,barashiInfo.getID());
        values.put(BarashiInfo.KEY_OBID,barashiInfo.getObjectId());
        values.put(BarashiInfo.KEY_JAN,barashiInfo.getJan());
        values.put(BarashiInfo.KEY_BARASHI_CODE,barashiInfo.getBarashi_code());
        values.put(BarashiInfo.KEY_BARASHI_COMMODITY,barashiInfo.getBarashi_commodity());
        values.put(BarashiInfo.KEY_COUNT,barashiInfo.getCount());
        db.update(BarashiInfo.TABLE,values,BarashiInfo.KEY_ID+"=?",new String[]{String.valueOf(barashiInfo.getID())});
        db.close();
    }

    public int getDataById(int ID){
        int id = 0;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        BarashiInfo barashiInfo = new BarashiInfo();
        String selectQuery = "SELECT "+
                BarashiInfo.KEY_ID + "," +
                BarashiInfo.KEY_OBID + "," +
                BarashiInfo.KEY_JAN + "," +
                BarashiInfo.KEY_BARASHI_CODE + "," +
                BarashiInfo.KEY_BARASHI_COMMODITY + "," +
                BarashiInfo.KEY_COUNT +
                " FROM " + BarashiInfo.TABLE
                + " WHERE " +

                BarashiInfo.KEY_ID + "=? "
                ;
        ArrayList<HashMap<String,String>> barashiList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(ID)});

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex(BarashiInfo.KEY_ID));

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return id;
    }

    public void queryObject(){
        final BmobQuery<BarashiInfo> bmobQuery = new BmobQuery<BarashiInfo>();
        bmobQuery.addWhereExists("jan");
        bmobQuery.findObjects(new FindListener<BarashiInfo>() {
            @Override
            public void done(List<BarashiInfo> list, BmobException e) {
                if (e == null){
                    for (BarashiInfo barashiInfo : list){
                        if (getDataById(barashiInfo.getID()) == 0){
                            insertData(barashiInfo);
                        }else {
                            updateData(barashiInfo);
                        }
                    }
                }
            }
        });
    }
}
