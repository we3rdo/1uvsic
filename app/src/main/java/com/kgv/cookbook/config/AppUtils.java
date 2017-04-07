package com.kgv.cookbook.config;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.kgv.cookbook.CookbookApplication;
import com.kgv.cookbook.util.LogUtils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by 陈可轩 on 2016/12/7 11:12
 * Email : 18627241899@163.com
 * Description : 常用资源帮助类。
 */
public class AppUtils {

    public static final boolean DEBUG = true;

    /**
     *  获取上下文
     */
    public static Context getContext(){
        return CookbookApplication.getContext();
    }

    /**
     *  获取资源文件夹
     */
    public static Resources getResources(){
        return getContext().getResources();
    }

    /**
     *  获取String.xml中的String
     */
    public static String getString(int resId){
        return getResources().getString(resId);
    }

    /**
     *  获取String.xml中的StringArray
     */
    public static String[] getStringArray(int resId){
        return getResources().getStringArray(resId);
    }

    /**
     *  获取Color.xml中的color
     */
    public static int getColor(int resId){
        return getResources().getColor(resId);
    }

    /**
     *  获取包名
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     *  强制隐藏软键盘
     */
    public static void hideKeyboard(EditText et){
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    /**
     *  字符串判空
     */
    public static boolean isNullString(String str){
        return !"".equals(str.trim()) && !"null".equals(str) && str.trim().length() > 0;
    }

    /**
     *  获取屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    /**
     *  获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 根据手机的分辨率从 px 的单位 转成为 dp
     */
    public static int px2Dip(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px
     */
    public static int dip2px(float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     *  获取版本名称
     */
    public static String getVersionName(Context context){

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  获取版本号
     */
    public static int getVersionCode(Context context){

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     检查网络是否可用
     */
    public static boolean isNetAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isAvailable());
    }

    /**
     *  是否为wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     *  关流
     */
    public static void closeIO(Closeable io){
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                LogUtils.e(e);
            }
        }
    }

    /**
     *  关流
     */
    public static void closeIO(Closeable before,Closeable after){
        if (before != null) {
            try {
                before.close();
            } catch (IOException e) {
                LogUtils.e(e);
            } finally {
                if (after != null){
                    try {
                        after.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
