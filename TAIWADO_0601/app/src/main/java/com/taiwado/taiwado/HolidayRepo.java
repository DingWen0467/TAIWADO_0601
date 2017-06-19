package com.taiwado.taiwado;

import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by tei on 2017/06/07.
 */

public class HolidayRepo extends BaseActivity{

    private String objectId;
    private String username;
    private String date;
    private int day;
    private int ID;
    private String holiday;
    private String holidayType;
    private String timeIn;
    private String timeOut;
    private String work;
    private String workType;

    public String getObjectId(){
        return this.objectId;
    }
    public void setObjectId(String objectId){
        this.objectId = objectId;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

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
        bmobQuery.addWhereEqualTo("day",day);
        bmobQuery.setLimit(2);
        bmobQuery.order("createdAt");
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取

        bmobQuery.findObjects(new FindListener<HolidayData>() {

            @Override
            public void done(List<HolidayData> object, BmobException e) {
                if(e==null){

                    for (HolidayData holidayData : object) {
                        setDay(holidayData.getDay());
                        setID(holidayData.getID());
                        setObjectId(holidayData.getObjectId());
                        setHoliday(holidayData.getHoliday());
                        setDate(holidayData.getDate());
                        setTimeIn(holidayData.getTimeIn());
                        setTimeOut(holidayData.getTimeOut());
                        setHolidayType(holidayData.getHolidayType());
                        setWork(holidayData.getWork());
                    }
                }else{
                    loge(e);
                }
            }
        });
    }

    public void updateHolidayData(String objectId,int ID,String holiday,String holidayType,String timeIn,String timeOut,String work,int day) {

        HolidayData holidayData = new HolidayData();
        holidayData.setID(ID);
        holidayData.setHoliday(holiday);
        holidayData.setHolidayType(holidayType);
        holidayData.setTimeIn(timeIn);
        holidayData.setTimeOut(timeOut);
        holidayData.setWork(work);
        holidayData.setDay(day);

        holidayData.update(objectId, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Log.i("bmob", "OK");
                    } else {
                        Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
    }

    public  void queryHoliday(String username,String month){

        final BmobQuery<HolidayData> holiday = new BmobQuery<HolidayData>();
        holiday.addWhereEqualTo("username", username);
        holiday.addWhereEqualTo("holiday","holiday");
        holiday.addWhereEqualTo("month",month);
        holiday.setLimit(50);
        holiday.order("createdAt");
        //先判断是否有缓存
        boolean isCache = holiday.hasCachedResult(HolidayData.class);
        //if(isCache){
        //bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取。
        //}else{
        holiday.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
        //}

        holiday.findObjects(new FindListener<HolidayData>() {

            @Override
            public void done(List<HolidayData> object, BmobException e) {
                if(e==null){
                    int i = 0;
                    for (HolidayData holidayData : object) {
                        setDay(holidayData.getDay());
                        setObjectId(holidayData.getObjectId());
                        //Kyuuka[i] = holidayData.getDay();
                        i++;
                    }
                }else{
                    loge(e);
                }
            }
        });
    }

}
