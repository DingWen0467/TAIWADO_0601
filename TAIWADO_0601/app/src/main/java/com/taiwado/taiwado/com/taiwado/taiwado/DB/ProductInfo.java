package com.taiwado.taiwado.com.taiwado.taiwado.DB;

import cn.bmob.v3.BmobObject;

/**
 * Created by tei on 2017/06/28.
 */

public class ProductInfo extends BmobObject {

    public static final String TABLE = "ProductInfo";

    public static final String KEY_ID = "ID";
    public static final String KEY_OBID = "objectID";
    public static final String KEY_JAN = "jan";
    public static final String KEY_SHIRIZU = "shirizu";
    public static final String KEY_COMMODITY = "commodity";
    public static final String KEY_CASH = "cash";
    public static final String KEY_COUNT = "count";

    public int ID;
    public String objectID;
    public String jan;
    public String shirizu;
    public String commodity;
    public int cash;
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
}