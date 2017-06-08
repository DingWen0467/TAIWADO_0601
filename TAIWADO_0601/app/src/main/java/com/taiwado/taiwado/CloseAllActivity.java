package com.taiwado.taiwado;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tei on 2017/06/05.
 */

public class CloseAllActivity extends Application {
    private List<Activity> activityList = new LinkedList<Activity>();
    private static CloseAllActivity instance;// 创建的静态对象，避免每次使用的实例化
    private CloseAllActivity() {
        // 空构造函数
    }
    // 实例化一次
    public synchronized static CloseAllActivity getInstance() {
        if (instance == null) {
            instance = new CloseAllActivity();
        }
        return instance;
    }
    /**
     *
     * @param activity
     *            将当前activity添加到activity集合中
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    public void exit() {
        try {
            for (Activity activity : activityList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            Log.e("xiamo", "关闭activity错误：" + e.toString());
        } finally {
            System.exit(0);
        }
    }
    public void finishActivity(Activity activity){
        if(activity!=null){
            activityList.remove(activity);
            activity.finish();
            activity=null;
        }
    }
}
