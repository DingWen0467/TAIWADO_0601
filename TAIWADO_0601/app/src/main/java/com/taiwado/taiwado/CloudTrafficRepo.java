package com.taiwado.taiwado;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by tei on 2017/06/14.
 */

public class CloudTrafficRepo extends BaseActivity {

    public void insertTraffic(int ID,String username, String from ,String to , int cash,int day,String month,String year,String date) {
        CloudTrafficData cloudTraffic = new CloudTrafficData();
        final String[] trafficObjectID = {null};

        cloudTraffic.setID(ID);
        cloudTraffic.setUname(username);
        cloudTraffic.setBegin(from);
        cloudTraffic.setEnd(to);
        cloudTraffic.setCash(cash);
        cloudTraffic.setDay(day);
        cloudTraffic.setMonth(month);
        cloudTraffic.setYear(year);
        cloudTraffic.setDate(date);

        cloudTraffic.save(new SaveListener<String>() {
            @Override
            public void done(String objectid, BmobException e) {
                if (e == null) {
                    trafficObjectID[0] = objectid;
                }
            }
        });
    }

    public  void queryHoliday(String username,String month,String year){

        final BmobQuery<CloudTrafficData> traffic = new BmobQuery<CloudTrafficData>();
        traffic.addWhereEqualTo("username", username);
        traffic.addWhereEqualTo("holiday","holiday");
        traffic.addWhereEqualTo("month",month);
        traffic.addWhereEqualTo("year",year);
        traffic.setLimit(50);
        traffic.order("createdAt");
        //先判断是否有缓存
        boolean isCache = traffic.hasCachedResult(CloudTrafficData.class);
        //if(isCache){
        //bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取。
        //}else{
        traffic.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
        //}

        traffic.findObjects(new FindListener<CloudTrafficData>() {

            @Override
            public void done(List<CloudTrafficData> object, BmobException e) {
                if(e==null){
                    int i = 0;
                    for (CloudTrafficData cloudTraffic : object) {

                    }
                }else{
                    loge(e);
                }
            }
        });
    }
}
