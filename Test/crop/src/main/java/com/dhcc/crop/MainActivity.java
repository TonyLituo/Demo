package com.dhcc.crop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.yalantis.ucrop.UCrop;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    ImageView mImageView;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private final int IMAGE_CODE = 127;

    private final int CAMERA_CODE = 128;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) this.findViewById(R.id.ic_img);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPhoto();
            }
        });
    }


    private void setPhoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setItems(new String[]{"打开相机拍照", "从相册中选择"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Log.e("====", "打开相机拍照");
                                Intent intent = new Intent();
                                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                                File file = CropUtils.getCameraFile();
                                Uri imageUri = Uri.fromFile(file);
                                Log.e("dadada", "uri.p===" + imageUri.getPath());
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(intent, CAMERA_CODE);
                                break;
                            case 1:
                                Log.e("====", "从相册中选择");
                                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                                getAlbum.setType(IMAGE_UNSPECIFIED);
                                startActivityForResult(getAlbum, IMAGE_CODE);
                                break;

                        }
                    }
                });
        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口

//        ContentResolver resolver = getContentResolver();

        if (requestCode == IMAGE_CODE) {

            if (null != data) {
                String path = CropUtils.getPath(MainActivity.this, data.getData());
                CropUtils.startUCrop(MainActivity.this, path);
            }

        }
        if (requestCode == CAMERA_CODE) {

            CropUtils.startUCrop(MainActivity.this, (CropUtils.sCacheFile.getAbsolutePath()));
//            CropUtils.initCache();
            Log.e("相机", "sdadadsa");
        }

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            Log.e("FinalUri", "====" + resultUri.getPath());
            Log.e("sdaas", "裁剪成功");
            Bitmap bitmap = BitmapFactory.decodeFile(CropUtils.sOutFile.getAbsolutePath());

            mImageView.setImageBitmap(ThumbnailUtils.extractThumbnail(bitmap, 400, 400));

//  mImageView.setImageBitmap(ThumbnailUtils.extractThumbnail(bm, 400, 400));  //使用系统的一个工具类，参数列表为 Bitmap Width,Height  这里使用压缩后显示，否则在华为手机上ImageView 没有显示

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            Log.e("sdaas", "失败");
        }
    }
}
