package com.taiwado.taiwado;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by tei on 2017/06/07.
 */

public class HolidayRepo extends BaseActivity{

    private String username;
    private String date;
    private int day;
    private String holiday;
    private String holidayType;
    private String timeIn;
    private String timeOut;
    private String work;
    private String workType;

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
        this.date = date;
    }

    public int getDay(){
        return this.day;
    }
    public void setDay(int day){
        this.day = day;
    }

    public String getHoliday(){
        return this.holiday;
    }
    public void setHoliday(String holiday){
        this.holiday = holiday;
    }

    public String getHolidayType(){
        return this.holidayType;
    }
    public void setHolidayType(String holidayType){
        this.holidayType = holidayType;
    }

    public String getTimeIn(){
        return this.timeIn;
    }
    public void setTimeIn(String timeIn){
        this.timeIn = timeIn;
    }

    public String getTimeOut(){
        return timeOut;
    }
    public void setTimeOut(String timeOut){
        this.timeOut =timeOut;
    }

    public String getWork(){
        return work;
    }
    public void setWork(String work){
        this.work = work;
    }

    public String getWorkType(){
        return workType;
    }
    public void setWorkType(String workType){
        this.workType = workType;
    }



}
