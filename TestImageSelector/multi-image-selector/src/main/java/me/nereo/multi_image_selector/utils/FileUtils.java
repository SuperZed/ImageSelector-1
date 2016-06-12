package me.nereo.multi_image_selector.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 文件操作类
 * Created by Nereo on 2015/4/8.
 * Modified by Haoxunwang on 2016/1/25
 */
public class FileUtils {

    public static File createTmpFile(Context context) {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 已挂载
//            File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
//            String fileName = "Camera/IMG_"+timeStamp+"";
//            File tmpFile = new File(pic, fileName+".jpg");
//            return tmpFile;
            /**
             * fixbug：Meizu mx1等机子调起照相机而无法正常返回前一个Activity！！！
             *  FIXME: 2016/1/25 之前使用系统默认的图片保存目录作为照片保存路径无法访问，而将照片保存的文件路径修改为
             * 自定义的路径则返回正常，由于一些手机禁止了系统公共的默认路径的访问，所以为了兼容所有的手机，建议将要保存的文件保存到自定义的文件夹下。
             */

            File pic = new File(Environment.getExternalStorageDirectory() + "/gz_img/pictures");
            if (!pic.exists()) {
                pic.mkdirs();
            }
            File tmpFile = new File(pic, new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date()) + ".jpg");
            return tmpFile;
        } else {
            File cacheDir = context.getCacheDir();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "Camera/IMG" + timeStamp + "";
            File tmpFile = new File(cacheDir, fileName + ".jpg");
            return tmpFile;
        }

    }

    public static boolean isImage(String path){
        if(TextUtils.isEmpty(path))
            return false;
        Drawable drawable = Drawable.createFromPath(path);
        if(drawable == null){
            return false;
        }
        return true;
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        if(bitmap == null){
//            return false;
//        }
//        return true;
    }
}
