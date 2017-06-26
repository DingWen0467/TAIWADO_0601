package com.taiwado.taiwado;

import cn.bmob.v3.BmobObject;

/**
 * Created by tei on 2017/06/22.
 */

public class HanbaiData extends BmobObject {
    public static final String TABLE = "HanbaiData";

    //表的各域名

    public static final String KEY_ID = "ID";
    public static final String KEY_OBID = "objectID";
    public static final String KEY_UNAME = "uname";
    public static final String KEY_STORENAME = "storeName";
    public static final String KEY_JAN = "jan";
    public static final String KEY_SHIRIZU = "shirizu";
    public static final String KEY_COMMODITY = "commodity";
    public static final String KEY_CASH = "cash";
    public static final String KEY_DATE = "date";
    public static final String KEY_YEAR = "year";
    public static final String KEY_MONTH = "month";
    public static final String KEY_DAY = "day";
    public static final String KEY_COUNT = "count";
    public static final String KEY_TAKEN = "taken";



    //属性

    public int ID;
    public String objectID;
    public String username;
    public String date;
    public String storeName;
    public String jan;
    public String shirizu;
    public String commodity;
    public int cash;
    public int count;
    public String year;
    public String month;
    public String day;
    public String taken;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getJan() {
        return jan;
    }

    public void setJan(String jan) {
        this.jan = jan;
    }

    public String getShirizu() {
        return shirizu;
    }

    public void setShirizu(String shirizu) {
        this.shirizu = shirizu;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTaken() {
        return taken;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }
}
