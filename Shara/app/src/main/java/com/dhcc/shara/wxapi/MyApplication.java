package com.dhcc.shara.wxapi;

import android.app.Application;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Lituo on 2017/4/17 0017. 14:36 .
 * Mail：tony1994_vip@163.com
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ShareSDK.initSDK(this);
    }
}
