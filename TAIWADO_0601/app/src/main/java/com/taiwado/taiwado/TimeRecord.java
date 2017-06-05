package com.taiwado.taiwado;

import cn.bmob.v3.BmobObject;

/**
 * Created by tei on 2017/05/26.
 */

public class TimeRecord extends BmobObject {

    private String username;
    private String TimeIn;
    private String TimeOut;
    private String TimeInAddress;
    private String TimeOutAddress;
    private  String TimeDate;

    public String getUsername(String username) {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimeIn() {
        return TimeIn;
    }
    public void setTimeIn(String timeIn) {
        this.TimeIn = timeIn;
    }

    public String getTimeOut() {
        return TimeOut;
    }
    public void setTimeOut(String timeOut) {
        this.TimeOut = timeOut;
    }

    public String TimeInAddress() {
        return TimeInAddress;
    }
    public void TimeInAddress(String timeInAddress) {
        this.TimeInAddress = timeInAddress;
    }

    public String TimeOutAddress(){
        return TimeOutAddress;
    }
    public void TimeOutAddress(String timeOutAddress){
        this.TimeOutAddress = timeOutAddress;
    }

    public  String getTimeDate() {
        return TimeDate;
    }
    public void setTimeDate(String timeDate) {
        this.TimeDate = timeDate;
    }

}
