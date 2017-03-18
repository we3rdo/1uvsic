package com.kgv.cookbook.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.kgv.cookbook.config.SpKeys;


/**
 * @author KEXUAN CHEN
 * @time 2016/8/11  19:06
 * @desc SharedPreferences工具类
 */
public class SpUtils {

    public static void saveString(Context context, String key, String value){
        SharedPreferences sp = context.getSharedPreferences(SpKeys.SP_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static boolean saveStringByCommit(Context context, String key, String value){
        SharedPreferences sp = context.getSharedPreferences(SpKeys.SP_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        return editor.commit();
    }

    public static String getString(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences(SpKeys.SP_FILE_NAME,Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }

    public static void saveInt(Context context, String key, int value){
        SharedPreferences sp = context.getSharedPreferences(SpKeys.SP_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public static int getInt(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences(SpKeys.SP_FILE_NAME,Context.MODE_PRIVATE);
        return sp.getInt(key,0);
    }


    public static void saveLong(Context context, String key, long value){
        SharedPreferences sp = context.getSharedPreferences(SpKeys.SP_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key,value);
        editor.apply();
    }

    public static long getLong(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences(SpKeys.SP_FILE_NAME,Context.MODE_PRIVATE);
        return sp.getLong(key,0);
    }

}
