package com.taiwado.taiwado;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.taiwado.taiwado.BaseActivity.loge;

/**
 * Created by tei on 2017/06/07.
 */

public class HolidayData extends BmobObject {

    private String username;
    private String  date;
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

    public  void queryObjects(String username,int day){
        final BmobQuery<HolidayData> bmobQuery = new BmobQuery<HolidayData>();
        bmobQuery.addWhereEqualTo("username", username);
        bmobQuery.addWhereEqualTo("holiday","holiday");
        bmobQuery.addWhereEqualTo("day",day);
        bmobQuery.setLimit(2);
        bmobQuery.order("createdAt");
        //先判断是否有缓存
        boolean isCache = bmobQuery.hasCachedResult(HolidayData.class);
        //if(isCache){
        //bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取。
        //}else{
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
        //}

        bmobQuery.findObjects(new FindListener<HolidayData>() {

            @Override
            public void done(List<HolidayData> object, BmobException e) {
                if(e==null){

                    for (HolidayData holidayData : object) {
                        setDay(holidayData.getDay());
                    }
                }else{
                    loge(e);
                }
            }
        });
    }

}
