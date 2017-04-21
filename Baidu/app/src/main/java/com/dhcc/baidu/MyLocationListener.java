package com.dhcc.baidu;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

/**
 * Created by Lituo on 2017/4/14 0014. 09:08 .
 * Mail：tony1994_vip@163.com
 */

public class MyLocationListener implements BDLocationListener {
    @Override
    public void onReceiveLocation(BDLocation location) {
        Log.e("MyLocation", "=========" + location.getCity());
        Log.e("MyLocation", "=========" + location.getAddrStr());
        Log.e("MyLocation", "=========" + location.getProvince());
    }


    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }
}
