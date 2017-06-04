package com.taiwado.taiwado;

import cn.bmob.v3.BmobObject;

/**
 * Created by tei on 2017/05/23.
 */

public class UserInfo extends BmobObject{
    private String userName;
    private String password;
    private String timeIn;
    private String timeOut;
    private String timeINAddress;
    private String timeOutAddress;
    private  String timeDate;
    private String userNum;


    public String getUserNum() {
        return userNum;
    }
    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getUsername() {
        return userName;
    }
    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimeIn() {
        return timeIn;
    }
    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }
    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getTimeOutAddress() {
        return timeOutAddress;
    }
    public void setTimeOutAddress(String timeOutAddress) {
        this.timeOutAddress = timeOutAddress;
    }

    public String getTimeINAddress() {
        return timeINAddress;
    }
    public void setTimeINAddress(String timeAddress) {
        this.timeINAddress = timeAddress;
    }

    public  String getTimeDate() {
        return timeDate;
    }
    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }


}
