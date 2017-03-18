package com.kgv.cookbook.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.kgv.cookbook.bean.ShiPu;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.config.SpKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.util.FileUtils;
import com.kgv.cookbook.util.LogUtils;
import com.kgv.cookbook.util.Md5Helper;
import com.kgv.cookbook.util.SpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author KEXUAN CHEN
 * @time 2016/8/11  14:00
 * @desc 下载图片服务
 *
 *  1.通过bean对象获取所有图片的url地址
 *  2.使用MD5加密生成随机的图片文件名称
 *  3.判断是否下载过该图片(SD卡目录存在缓存)
 *  4.若没有缓存,开始下载,并在下载后缓存到本地SD卡文件夹
 */

public class DownLoadService extends IntentService {

    //必须实现空构造器
    public DownLoadService() {
        super("DownLoadService");
    }

    //服务进行时
    protected void onHandleIntent(Intent intent) {
        //获取activity传过来的JsonBean对象
        List<ShiPu> shiPus = (List<ShiPu>) intent.getSerializableExtra("download_img");
        for (ShiPu shiPu : shiPus){
            String imageUrl = Urls.BASE_IMG_URL + shiPu.getImage_thumb();
            if (!TextUtils.isEmpty(imageUrl)) {
                //通过MD5加密生成随机文件名
                String md5ImageName = Md5Helper.toMD5(imageUrl);
                //判断图片是否下载过(缓存)
                if (!hasDownLoad(md5ImageName)){
                    //没有缓存,开始下载图片
                    boolean success = downLoadImage(imageUrl, md5ImageName);
                    SpUtils.saveString(AppUtils.getContext(), SpKeys.DOWNLOAD_IMG_SUCCESS,
                            success ? "t" : "f");
                    LogUtils.v("ckx520","下载结果 : " + success);
                }
            }
        }
    }

    /**
     * 判断是否有缓存图片
     * @param md5ImageName
     * @return
     */
    private boolean hasDownLoad(String md5ImageName){
        //1. 判断是否安装SD卡
        if (!FileUtils.hasSDCard()){
            return false;
        }

        //2.判断缓存目录是否存在
        File cacheFile = new File(Environment.getExternalStorageDirectory(),
                "image_cache");
        if (!cacheFile.exists()){
            return false;
        }

        //3.判断图片文件是否存在
        File imageFile = new File(cacheFile,md5ImageName + ".jpg");
        if (imageFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            if (bitmap != null){
                bitmap.recycle();
                return true;
            }
        }
        return false;
    }

    /**
     * 下载图片
     * @param imageUrl  图片地址
     * @param md5ImageName  图片的MD5名称
     */
    private boolean downLoadImage(String imageUrl,String md5ImageName){
        try {
            URL url = new URL(imageUrl);
            URLConnection conn =  url.openConnection();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            options.inPurgeable = true;
            options.inInputShareable = true;
            Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream(),null,options);
            if (bitmap != null){
                //将图片缓存到本地
                return saveToSd(bitmap,md5ImageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 缓存图片
     * @param bitmap
     * @param md5ImageName
     */
    private boolean saveToSd(Bitmap bitmap, String md5ImageName) {

        File cacheFile = new File(Environment.getExternalStorageDirectory(),
                "image_cache");
        if (!cacheFile.exists()){
            //没有缓存目录,创建出来
            cacheFile.mkdirs();
        }

        File imageFile = new File(cacheFile,md5ImageName + ".jpg");
        if (imageFile.exists()){
            //已经有了该图片的缓存
            return true;
        }

        Log.v("LogUtils","图片路径 : " +imageFile.getAbsolutePath());
        //开始缓存图片
        boolean success = false;
        try {
            //获取输出流
            FileOutputStream out = new FileOutputStream(imageFile);
            //调用bitmap的打包方法将图片写到目标文件流
            success = bitmap.compress(Bitmap.CompressFormat.JPEG,80,out);
            out.flush();
            out.close();
            bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}
