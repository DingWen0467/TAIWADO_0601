package com.taiwado.taiwado.com.taiwado.taiwado.DB;

import cn.bmob.v3.BmobObject;

/**
 * Created by tei on 2017/06/29.
 */

public class BarashiInfo extends BmobObject {
    public static final String TABLE = "BarashiInfo";
    //表的各域名
    public static final String KEY_ID = "ID";
    public static final String KEY_OBID = "objectID";
    public static final String KEY_JAN = "jan";
    public static final String KEY_BARASHI_CODE = "barashi_code";
    public static final String KEY_BARASHI_COMMODITY = "barashi_commodity";
    public static final String KEY_COUNT = "count";

    //属性
    public int ID;
    public String objectID;
    public String jan;
    public String barashi_code;
    public String barashi_commodity;
    public int count;

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

    public String getJan() {
        return jan;
    }

    public void setJan(String jan) {
        this.jan = jan;
    }

    public String getBarashi_code() {
        return barashi_code;
    }

    public void setBarashi_code(String barashi_code) {
        this.barashi_code = barashi_code;
    }

    public String getBarashi_commodity() {
        return barashi_commodity;
    }

    public void setBarashi_commodity(String barashi_commodity) {
        this.barashi_commodity = barashi_commodity;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
