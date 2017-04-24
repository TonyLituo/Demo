package com.dhcc.baidu;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;


public class MainActivity extends AppCompatActivity {

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    Button mButton;
    Button mButton1;
    TextView mTextView;
    private static final int LOCATION_CODE_SDCARD = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*占位符*/
        mTextView = (TextView) this.findViewById(R.id.tv);
        String data = getResources().getString(R.string.data);
        data = String.format(data, 100, 10.3, "2017-07-01");
        mTextView.setText(data);

        mButton = (Button) this.findViewById(R.id.btn);
        mButton1 = (Button) this.findViewById(R.id.over);
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPermissions.requestPermissions(MainActivity.this,
                        LOCATION_CODE_SDCARD,
                        Manifest.permission.READ_PHONE_STATE,// 1、获取手机状态：
                        Manifest.permission.ACCESS_COARSE_LOCATION,//2、获取位置信息：
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE, //3、读写SD卡：
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        });
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.stop();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @PermissionGrant(LOCATION_CODE_SDCARD)
    public void requestSdcardSuccess() {
        setLocation();
        Toast.makeText(this, "GRANT ACCESS LOCATION!", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(LOCATION_CODE_SDCARD)
    public void requestSdcardFailed() {
        Toast.makeText(this, "DENY ACCESS LOCATION!", Toast.LENGTH_SHORT).show();
    }


    private void setLocation() {
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式(高精度，低功耗，仅设备)
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选设置 返回的定位结果坐标系
        option.setCoorType("bd09ll");
        int span = 2000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.unRegisterLocationListener(myListener);
    }
}
