package com.dhcc.crop;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;

/**
 * Created by Lituo on 2017/4/19 0019. 19:12 .
 * Mail：tony1994_vip@163.com
 */

public class CropUtils {

    public static File sOutFile;
    /**
     * 相机缓存文件
     */
    public static File sCacheFile;
    /**
     * 缓存文件夹
     */
    private static File sCache;

    /**
     * 启动裁剪
     *
     * @param activity       上下文
     * @param sourceFilePath 需要裁剪图片的绝对路径
     * @return
     */

    public static String startUCrop(Activity activity, String sourceFilePath) {

        Uri sourceUri = Uri.fromFile(new File(sourceFilePath));

        File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        if (!outDir.exists()) {

            outDir.mkdirs();

        }

        sOutFile = new File(outDir, System.currentTimeMillis() + ".jpg");

//裁剪后图片的绝对路径

        String cameraScalePath = sOutFile.getAbsolutePath();

        Uri destinationUri = Uri.fromFile(sOutFile);

//初始化，第一个参数：需要裁剪的图片；第二个参数：裁剪后图片

        UCrop uCrop = UCrop.of(sourceUri, destinationUri);

//初始化UCrop配置

        UCrop.Options options = new UCrop.Options();

        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        //设置裁剪图片的图片质量
        options.setCompressionQuality(90);

//设置裁剪图片可操作的手势

        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
//是否隐藏底部容器，默认显示
        options.setHideBottomControls(true);
//设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
//设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
//是否能调整裁剪框
        options.setFreeStyleCropEnabled(true);

//UCrop配置
        uCrop.withOptions(options);
//设置裁剪图片的宽高比，比如16：9
        uCrop.withAspectRatio(1, 1);

//uCrop.useSourceImageAspectRatio();

//跳转裁剪页面

        uCrop.start(activity);

        return cameraScalePath;

    }

    /**
     * 获取相册的路径
     *
     * @param activity
     * @param uri
     * @return
     */

    public static String getPath(Activity activity, Uri uri) {

        String[] proj = {MediaStore.Images.Media.DATA};

        // 好像是android多媒体数据库的封装接口，具体的看Android文档
        Cursor cursor = activity.managedQuery(uri, proj, null, null, null);

        // 按我个人理解 这个是获得用户选择的图片的索引值
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst();
        // 最后根据索引值获取图片路径
        String path = cursor.getString(column_index);
        return path;
    }

    public static File getCameraFile() {
        File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        sCache = new File(outDir, "Cache");
        if (!sCache.exists()) {
            sCache.mkdirs();
        }
        sCacheFile = new File(sCache, System.currentTimeMillis()  + ".jpg");
        return sCacheFile;
    }

    public static void initCache() {
        deleteFile(sCache);
    }

    public static void deleteFile(File file) {


        if (!file.exists()) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }

    }
}
