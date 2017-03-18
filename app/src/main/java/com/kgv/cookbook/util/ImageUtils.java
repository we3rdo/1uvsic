package com.kgv.cookbook.util;

import android.os.Environment;

import java.io.File;

/**
 * @author KEXUAN CHEN
 * @time 2016/8/11  19:25
 * @desc 图片工具类
 */
public class ImageUtils {

    /**
     * 通过传入的加密图片文件名返回该图片的bitmap
     * @param md5ImageName
     * @return
     */
    public static File getImageByName(String md5ImageName){
        //1.判断是否安装SD卡
        File sdFile = Environment.getExternalStorageDirectory();
        if (sdFile == null || !sdFile.exists()){
            return null;
        }

        //2.判断缓存目录是否存在
        File cacheFile = new File(sdFile, "image_cache");
        if (!cacheFile.exists()){
            return null;
        }

        //3.判断图片文件是否存在
        File imageFile = new File(cacheFile,md5ImageName + ".jpg");
        if (imageFile.exists()){
            return imageFile;
        }

        return null;

    }
}
