package com.taiwado.taiwado.com.taiwado.taiwado.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by tei on 2017/06/29.
 */

public class SaleGruopRepo {
    private SaleGruopInterface dbHelper;
    
    public SaleGruopRepo(Context context){
        dbHelper = new SaleGruopInterface(context);
    }

    public int insertData(SaleGroupInfo saleGroupInfo){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SaleGroupInfo.KEY_ID,saleGroupInfo.getID());
        values.put(SaleGroupInfo.KEY_OBID,saleGroupInfo.getObjectId());
        values.put(SaleGroupInfo.KEY_JAN,saleGroupInfo.getJan());
        values.put(SaleGroupInfo.KEY_SHIRIZU,saleGroupInfo.getShirizu());
        values.put(SaleGroupInfo.KEY_PRESENT,saleGroupInfo.getPresent());
        values.put(SaleGroupInfo.KEY_PRESENT_CODE,saleGroupInfo.getPresent_code());
        values.put(SaleGroupInfo.KEY_BARASHI,saleGroupInfo.getBarashi());
        values.put(SaleGroupInfo.KEY_BARASHI_CODE,saleGroupInfo.getBarashi_code());
        values.put(SaleGroupInfo.KEY_COMMODITY,saleGroupInfo.getCommodity());
        values.put(SaleGroupInfo.KEY_CASH,saleGroupInfo.getCash());
        db.insert(SaleGroupInfo.TABLE,null,values);
        db.close();

        return saleGroupInfo.getID();
    }

    public void updateData(SaleGroupInfo saleGroupInfo){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SaleGroupInfo.KEY_ID,saleGroupInfo.getID());
        values.put(SaleGroupInfo.KEY_OBID,saleGroupInfo.getObjectId());
        values.put(SaleGroupInfo.KEY_JAN,saleGroupInfo.getJan());
        values.put(SaleGroupInfo.KEY_SHIRIZU,saleGroupInfo.getShirizu());
        values.put(SaleGroupInfo.KEY_PRESENT,saleGroupInfo.getPresent());
        values.put(SaleGroupInfo.KEY_PRESENT_CODE,saleGroupInfo.getPresent_code());
        values.put(SaleGroupInfo.KEY_BARASHI,saleGroupInfo.getBarashi());
        values.put(SaleGroupInfo.KEY_BARASHI_CODE,saleGroupInfo.getBarashi_code());
        values.put(SaleGroupInfo.KEY_COMMODITY,saleGroupInfo.getCommodity());
        values.put(SaleGroupInfo.KEY_CASH,saleGroupInfo.getCash());
        db.update(SaleGroupInfo.TABLE,values,SaleGroupInfo.KEY_ID+"=?",new String[]{String.valueOf(saleGroupInfo.getID())});
        db.close();

    }

    public int getSaleID(String jan){
        int saleID = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+
                SaleGroupInfo.KEY_ID + "," +
                SaleGroupInfo.KEY_OBID + "," +
                SaleGroupInfo.KEY_JAN + "," +
                SaleGroupInfo.KEY_PRESENT + "," +
                SaleGroupInfo.KEY_PRESENT_CODE + "," +
                SaleGroupInfo.KEY_BARASHI + "," +
                SaleGroupInfo.KEY_BARASHI_CODE + "," +
                SaleGroupInfo.KEY_BARASHI_CODE + "," +
                SaleGroupInfo.KEY_COMMODITY + "," +
                SaleGroupInfo.KEY_CASH +
                " FROM " + SaleGroupInfo.TABLE
                + " WHERE " +

                SaleGroupInfo.KEY_JAN + "=? "
                ;

        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(jan)});

        if(cursor.moveToFirst()){
            do{
                saleID = cursor.getInt(cursor.getColumnIndex(SaleGroupInfo.KEY_ID));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return saleID;
    }

    public int getDataById(int ID){
        int saleID = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+
                SaleGroupInfo.KEY_ID + "," +
                SaleGroupInfo.KEY_OBID + "," +
                SaleGroupInfo.KEY_JAN + "," +
                SaleGroupInfo.KEY_PRESENT + "," +
                SaleGroupInfo.KEY_PRESENT_CODE + "," +
                SaleGroupInfo.KEY_BARASHI + "," +
                SaleGroupInfo.KEY_BARASHI_CODE + "," +
                SaleGroupInfo.KEY_BARASHI_CODE + "," +
                SaleGroupInfo.KEY_COMMODITY + "," +
                SaleGroupInfo.KEY_CASH +
                " FROM " + SaleGroupInfo.TABLE
                + " WHERE " +

                SaleGroupInfo.KEY_JAN + "=? "
                ;

        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(ID)});

        if(cursor.moveToFirst()){
            do{
                saleID = cursor.getInt(cursor.getColumnIndex(SaleGroupInfo.KEY_ID));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return saleID;
    }

    public SaleGroupInfo getById(int ID){
        SaleGroupInfo info = new SaleGroupInfo();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+
                SaleGroupInfo.KEY_ID + "," +
                SaleGroupInfo.KEY_OBID + "," +
                SaleGroupInfo.KEY_JAN + "," +
                SaleGroupInfo.KEY_SHIRIZU + "," +
                SaleGroupInfo.KEY_PRESENT + "," +
                SaleGroupInfo.KEY_PRESENT_CODE + "," +
                SaleGroupInfo.KEY_BARASHI + "," +
                SaleGroupInfo.KEY_BARASHI_CODE + "," +
                SaleGroupInfo.KEY_COMMODITY + "," +
                SaleGroupInfo.KEY_CASH +
                " FROM " + SaleGroupInfo.TABLE
                + " WHERE " +

                SaleGroupInfo.KEY_ID + "=? "
                ;

        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(ID)});

        if(cursor.moveToFirst()){
            do{
                info.setID(cursor.getInt(cursor.getColumnIndex(SaleGroupInfo.KEY_ID)));
                info.setObjectId(cursor.getString(cursor.getColumnIndex(SaleGroupInfo.KEY_OBID)));
                info.setJan(cursor.getString(cursor.getColumnIndex(SaleGroupInfo.KEY_JAN)));
                info.setShirizu(cursor.getString(cursor.getColumnIndex(SaleGroupInfo.KEY_SHIRIZU)));
                info.setPresent(cursor.getString(cursor.getColumnIndex(SaleGroupInfo.KEY_PRESENT)));
                info.setPresent_code(cursor.getString(cursor.getColumnIndex(SaleGroupInfo.KEY_PRESENT_CODE)));
                info.setBarashi(cursor.getString(cursor.getColumnIndex(SaleGroupInfo.KEY_BARASHI)));
                info.setBarashi_code(cursor.getString(cursor.getColumnIndex(SaleGroupInfo.KEY_BARASHI_CODE)));
                info.setCommodity(cursor.getString(cursor.getColumnIndex(SaleGroupInfo.KEY_COMMODITY)));
                info.setCash(cursor.getInt(cursor.getColumnIndex(SaleGroupInfo.KEY_CASH)));

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return info;
    }

    public void queryObjet(){
        final BmobQuery<SaleGroupInfo> bmobQuery = new BmobQuery<SaleGroupInfo>();
        bmobQuery.addWhereExists("jan");
        bmobQuery.findObjects(new FindListener<SaleGroupInfo>() {
            @Override
            public void done(List<SaleGroupInfo> list, BmobException e) {
                if (e == null){
                    for (SaleGroupInfo saleGroupInfo : list){
                        if (getDataById(saleGroupInfo.getID()) == 0){
                            insertData(saleGroupInfo);
                        }else {
                            updateData(saleGroupInfo);
                        }
                    }
                }
            }
        });
    }
}
