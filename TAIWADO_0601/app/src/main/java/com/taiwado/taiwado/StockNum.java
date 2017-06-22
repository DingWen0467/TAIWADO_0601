package com.taiwado.taiwado;

/**
 * Created by tei on 2017/06/20.
 */

public class StockNum {
    public static final String TABLE = "StockNum";

    //表的各域名
    public static final String KEY_ID = "ID";
    public static final String KEY_EXITNUM = "exitNum";
    public static final String KEY_STORENAME = "storeName";
    public static final String KEY_JAN = "jan";
    public static final String KEY_SHIRIZU = "shirizu";
    public static final String KEY_COMMODITY = "commodity";
    //属性
    public int ID;
    public int exitNum;
    public String storeName;
    public String jan;
    public String shirizu;
    public String commodity;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getExitNum() {
        return exitNum;
    }

    public void setExitNum(int exitNum) {
        this.exitNum = exitNum;
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

    public StockNum(int id, int exitNum, String storeName, String jan) {

    }
    public StockNum(){

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
}
