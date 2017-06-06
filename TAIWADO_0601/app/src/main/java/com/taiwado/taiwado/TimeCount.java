package com.taiwado.taiwado;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by tei on 2017/06/05.
 */

public class TimeCount extends BaseActivity{

    private String TimeObjectID;
    private String TimeIn;
    private String AddIn;
    private static String objectId = null;

    public String getTimeObjectID() {
        return this.TimeObjectID;
    }
    public void setTimeObjectID(String TimeObjectID) {
        this.TimeObjectID = TimeObjectID;
    }

    public String getAddIn(){
        return this.AddIn;
    }
    public void setAddIn(String AddIn){
        this.AddIn = AddIn;
    }

    public String getTimeIn(){
        return this.TimeIn;
    }
    public void setTimeIn(String TimeIn){
        this.TimeIn = TimeIn;
    }

    public void queryObjects(String username){
        final BmobQuery<TimeRecord> bmobQuery	 = new BmobQuery<TimeRecord>();
        bmobQuery.addWhereEqualTo("username", username);
        bmobQuery.addWhereEqualTo("TimeDate",nowDate());
        bmobQuery.setLimit(2);
        bmobQuery.order("createdAt");
        //先判断是否有缓存
        boolean isCache = bmobQuery.hasCachedResult(TimeRecord.class);
        //if(isCache){
            //bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取。
        //}else{
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
        //}

		bmobQuery.findObjects(new FindListener<TimeRecord>() {

			@Override
			public void done(List<TimeRecord> object, BmobException e) {
				if(e==null){

					for (TimeRecord timeReccord : object) {
                        String timeIn = timeReccord.getTimeIn();
                        setTimeIn(timeIn);
                        String objectId = timeReccord.getObjectId();
                        setTimeObjectID(objectId);
                        String AddIn = timeReccord.TimeInAddress();
                        setAddIn(AddIn);
					}
				}else{
					loge(e);
				}
			}
		});
    }

    public void queryObjectsByTable(String username){
        BmobQuery query = new BmobQuery("TimeRecord");
        query.addWhereEqualTo("username",username);
        query.setLimit(2);
        query.order("createdAt");
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null){
                    log(jsonArray.toString());
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createTimeRecord(final String username) {
        final String[] objectId = {null};
        final TimeRecord tp1 = new TimeRecord();
        String time = null;
        time = nowTime();
        setTimeIn(time);
        tp1.setUsername(username);
        tp1.setTimeIn(time);
        tp1.setTimeDate(nowDate());
        tp1.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    objectId[0] = tp1.getObjectId();
                    setTimeObjectID(objectId[0]);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateTimeRecord(String ObjectId) {
        TimeRecord timeRecord = new TimeRecord();
        timeRecord.setTimeOut(nowTime());
        //timeRecord.setTimeDate(nowDate());
        //更新DB  userInfo
        timeRecord.update(ObjectId, new UpdateListener() {
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

    public String nowDate() {
        //取得当前系统日期
        String nowDate = null;
        SimpleDateFormat formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            formatter = new SimpleDateFormat("yyyy年MM月dd日");
        }
        Date curDate = new Date(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            nowDate = formatter.format(curDate);
        }
        return nowDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String nowTime() {
        String str = null;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        str = formatter.format(curDate);
        return str;
    }

    public String getAddress() {
        String Address = null;
        getLocation locationActivity = new getLocation();
        Address = locationActivity.nowLocationName;
        if (Address == null) {
            Address = "Failed to get Address!";
        } else {
            setAddress(Address);
        }
        return Address;
    }

    public void setAddress(String address) {
        UserInfo userInfo = new UserInfo();
        if (TimeObjectID == null) {
            userInfo.setTimeINAddress(address);
        } else {
            userInfo.setTimeINAddress(address);
        }

    }
}
