package com.dhcc.test.crop;

import android.app.Activity;
import android.net.Uri;

/**
 * Created by Lituo on 2017/4/19 0019. 17:21 .
 * Mail：tony1994_vip@163.com
 */

public interface CropHandler {

    void onPhotoCropped(Uri uri);

    void onCropCancel();

    void onCropFailed(String message);

    CropParams getCropParams();

    Activity getContext();
}
