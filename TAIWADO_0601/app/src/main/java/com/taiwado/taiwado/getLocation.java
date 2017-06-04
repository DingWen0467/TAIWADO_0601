package com.taiwado.taiwado;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by tei on 2017/05/24.
 */

public class getLocation extends Activity {
    String nowLocationName;
    private LocationManager locationManager;
    private static double latitude_static;
    private static double longitude_static;

    private String getNowLocationName() {

        getLocation();

        nowLocationName = getAddress(latitude_static,longitude_static);

        return nowLocationName;
    }

    void getLocation() {

        // 获取系统LocationManager服务
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            // 从GPS获取最近的定位信息
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //取得位置信息
            updateView(location);

        } else {
            LocationListener locationListener = new LocationListener() {

                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {

                }

                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {

                }

                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        Log.e("Map", "Location changed : Lat: "
                                + location.getLatitude() + " Lng: "
                                + location.getLongitude());
                    }
                }
            };
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, locationListener);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location != null){
                updateView(location);
            }
        }

    }

    private void updateView(Location location) {

        String address = null;
        if (location != null) {

            //实时的位置信息：经度：
            latitude_static = location.getLongitude();
            //纬度：
            longitude_static = location.getLatitude();

            //高度：
            //location.getAltitude();
            //速度：
            //location.getSpeed();
            //方向：
            //location.getBearing();
            //精度：
            //location.getAccuracy();
        }

    }

    private  String getAddress(double longitude
            , double latitude)
    {
        // 定义一个HttpClient，用于向指定地址发送请求

        HttpClient client = new DefaultHttpClient();
        // 向指定地址发送GET请求
        HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/"
                + "geocode/json?latlng="
                + latitude + "," + longitude
                + "&sensor=false®ion=cn");
        StringBuilder sb = new StringBuilder();
        try
        {
            // 执行请求
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            // 获取服务器响应的字符串
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1)
            {
                sb.append((char) b);
            }
            // 把服务器相应的字符串转换为JSONObject
            JSONObject jsonObj = new JSONObject(sb.toString());
            // 解析出响应结果中的地址数据
            return jsonObj.getJSONArray("results").getJSONObject(0)
                    .getString("formatted_address");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
