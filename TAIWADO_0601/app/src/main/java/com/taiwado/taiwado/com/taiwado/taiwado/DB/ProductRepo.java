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
 * Created by tei on 2017/06/28.
 */

public class ProductRepo {
    private ProductInterface dbHelper;
    public ProductRepo(Context context){
        dbHelper = new ProductInterface(context);
    }

    public int insertProductInfo(ProductInfo productInfo){
        // 调用getWritableDatabase()方法创建或打开一个可以读的数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 创建ContentValues对象
        ContentValues values1 = new ContentValues();
        // 向该对象中插入键值对
        values1.put(ProductInfo.KEY_ID,productInfo.getID());
        values1.put(ProductInfo.KEY_OBID,productInfo.getObjectID());
        values1.put(ProductInfo.KEY_JAN,productInfo.getJan());
        values1.put(ProductInfo.KEY_SHIRIZU,productInfo.getJan());
        values1.put(ProductInfo.KEY_COMMODITY,productInfo.getCommodity());
        values1.put(ProductInfo.KEY_COUNT,productInfo.getCash());
        values1.put(ProductInfo.KEY_CASH,productInfo.getCash());

        db.insert(ProductInfo.TABLE,null,values1);
        db.close();

        return productInfo.getID();
    }

    public ProductInfo getByID(int ID) {
        ProductInfo productInfo= new ProductInfo();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT " +
                ProductInfo.KEY_ID + "," +
                ProductInfo.KEY_OBID + "," +
                ProductInfo.KEY_JAN + "," +
                ProductInfo.KEY_SHIRIZU + "," +
                ProductInfo.KEY_COMMODITY + "," +
                ProductInfo.KEY_COUNT + "," +
                ProductInfo.KEY_CASH +
                " FROM " + ProductInfo.TABLE
                + " WHERE " +
                ProductInfo.KEY_ID + "=? ";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(ID)});
        if (cursor.moveToFirst()) {
            do {
                productInfo.setID(cursor.getInt(cursor.getColumnIndex(ProductInfo.KEY_ID)));
                productInfo.setObjectID(cursor.getString(cursor.getColumnIndex(ProductInfo.KEY_OBID)));
                productInfo.setJan(cursor.getString(cursor.getColumnIndex(ProductInfo.KEY_JAN)));
                productInfo.setShirizu(cursor.getString(cursor.getColumnIndex(ProductInfo.KEY_SHIRIZU)));
                productInfo.setCommodity(cursor.getString(cursor.getColumnIndex(ProductInfo.KEY_COMMODITY)));
                productInfo.setCash(cursor.getInt(cursor.getColumnIndex(ProductInfo.KEY_COUNT)));
                productInfo.setCash(cursor.getInt(cursor.getColumnIndex(ProductInfo.KEY_CASH)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return productInfo;
    }

    public int getID(String JAN) {
        int id = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT " +
                ProductInfo.KEY_ID + "," +
                ProductInfo.KEY_OBID + "," +
                ProductInfo.KEY_JAN + "," +
                ProductInfo.KEY_SHIRIZU + "," +
                ProductInfo.KEY_COMMODITY + "," +
                ProductInfo.KEY_COUNT + "," +
                ProductInfo.KEY_CASH +
                " FROM " + ProductInfo.TABLE
                + " WHERE " +
                ProductInfo.KEY_JAN + "=? ";

        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(JAN)});
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(ProductInfo.KEY_ID));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return id;
    }

    public int getDataById(int ID) {
        int id = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT " +
                ProductInfo.KEY_ID + "," +
                ProductInfo.KEY_OBID + "," +
                ProductInfo.KEY_JAN + "," +
                ProductInfo.KEY_SHIRIZU + "," +
                ProductInfo.KEY_COMMODITY + "," +
                ProductInfo.KEY_COUNT + "," +
                ProductInfo.KEY_CASH +
                " FROM " + ProductInfo.TABLE
                + " WHERE " +
                ProductInfo.KEY_ID + "=? ";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(ID)});
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(ProductInfo.KEY_ID));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return id;
    }

    public int insertData(ProductInfo productInfo){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProductInfo.KEY_ID,productInfo.getID());
        values.put(ProductInfo.KEY_OBID,productInfo.getObjectId());
        values.put(ProductInfo.KEY_JAN,productInfo.getJan());
        values.put(ProductInfo.KEY_SHIRIZU,productInfo.getShirizu());
        values.put(ProductInfo.KEY_COMMODITY,productInfo.getCommodity());
        values.put(ProductInfo.KEY_COUNT,productInfo.getCount());
        values.put(ProductInfo.KEY_CASH,productInfo.getCash());
        db.insert(ProductInfo.TABLE,null,values);

        db.close();

        return productInfo.getID();
    }

    public void updateData(ProductInfo productInfo){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProductInfo.KEY_ID,productInfo.getID());
        values.put(ProductInfo.KEY_OBID,productInfo.getObjectId());
        values.put(ProductInfo.KEY_JAN,productInfo.getJan());
        values.put(ProductInfo.KEY_SHIRIZU,productInfo.getShirizu());
        values.put(ProductInfo.KEY_COMMODITY,productInfo.getCommodity());
        values.put(ProductInfo.KEY_COUNT,productInfo.getCount());
        values.put(ProductInfo.KEY_CASH,productInfo.getCash());
        db.update(ProductInfo.TABLE,values,ProductInfo.KEY_ID+"=?",new String[]{String.valueOf(productInfo.getID())});

        db.close();
    }

    public void queryObject(){
        final BmobQuery<ProductInfo> bmobQuery = new BmobQuery<ProductInfo>();
        bmobQuery.addWhereExists("jan");
        bmobQuery.findObjects(new FindListener<ProductInfo>() {
            @Override
            public void done(List<ProductInfo> list, BmobException e) {
                if (e == null){
                    for (ProductInfo productInfo : list){
                        if (getDataById(productInfo.getID()) == 0){
                            insertData(productInfo);
                        }else {
                            updateData(productInfo);
                        }
                    }
                }
            }
        });
    }
}
