package com.taiwado.taiwado.com.taiwado.taiwado.DB;

import cn.bmob.v3.BmobObject;

/**
 * Created by tei on 2017/06/29.
 */

public class SaleGroupInfo extends BmobObject {
    public static final String TABLE = "SaleGruopInfo";

    //表的各域名
    public static final String KEY_ID = "ID";
    public static final String KEY_OBID = "objectID";
    public static final String KEY_JAN = "jan";
    public static final String KEY_SHIRIZU = "shirizu";
    public static final String KEY_PRESENT = "present";
    public static final String KEY_PRESENT_CODE = "present_code";
    public static final String KEY_BARASHI = "barashi";
    public static final String KEY_BARASHI_CODE = "barashi_code";
    public static final String KEY_COMMODITY = "commodity";
    public static final String KEY_CASH = "cash";

    //属性
    public int ID;
    public String objectID;
    public String jan;
    public String shirizu;
    public String present;
    public String present_code;
    public String barashi;
    public String barashi_code;
    public String commodity;
    public int cash;

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

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getPresent_code() {
        return present_code;
    }

    public void setPresent_code(String present_code) {
        this.present_code = present_code;
    }

    public String getBarashi() {
        return barashi;
    }

    public void setBarashi(String barashi) {
        this.barashi = barashi;
    }

    public String getBarashi_code() {
        return barashi_code;
    }

    public void setBarashi_code(String barashi_code) {
        this.barashi_code = barashi_code;
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
}
