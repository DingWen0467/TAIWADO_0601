package com.taiwado.taiwado;

import android.app.Activity;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseActivity extends Activity {

    public static String TAG = "bmob";
    protected ListView mListview;
    protected BaseAdapter mAdapter;

    private CompositeSubscription mCompositeSubscription;

    /**
     * 解决Subscription内存泄露问题
     * @param s
     */
    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    Toast mToast;

    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

    public void showToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), resId,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    public static void log(String msg) {
        Log.i(TAG,"===============================================================================");
        Log.i(TAG, msg);
    }

    public static void loge(Throwable e) {
        Log.i(TAG,"===============================================================================");
        if(e instanceof BmobException){
            Log.e(TAG, "错误码："+((BmobException)e).getErrorCode()+",错误描述："+((BmobException)e).getMessage());
        }else{
            Log.e(TAG, "错误描述："+e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
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
}
