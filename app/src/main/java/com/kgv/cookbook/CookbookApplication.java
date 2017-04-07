package com.kgv.cookbook;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.kgv.cookbook.bean.User;
import com.kgv.cookbook.db.dao.PhoneCodeDAO;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.ArrayList;

/**
 * Created by 陈可轩 on 2016/12/2 16:49
 * Email : 18627241899@163.com
 * Description : 程序入口。
 */
public class CookbookApplication extends Application {

    private static Context context;
    private static User user = new User();

    @Override
    public void onCreate() {
       super.onCreate();
        context = getApplicationContext();
        //初始化 科大讯飞
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=5850b061");
        //刷新手机验证码次数
        refreshGetCodeNumber();
        //初始化 fresco
        Fresco.initialize(context);
        //初始化 downloader
        FileDownloader.init(context);
    }

    public static Context getContext() {
        return context;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(String id,String username,String password) {
        user.setExist(true);
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
    }

    public static void setUser(boolean exist) {
        user.setExist(exist);
    }

    private void refreshGetCodeNumber() {
        PhoneCodeDAO dao = new PhoneCodeDAO(getApplicationContext());
        ArrayList<Long> times = dao.getAllTime();
        for (int i = 0; i < times.size(); i++) {
            if (System.currentTimeMillis() - times.get(i) > 24*60*60*1000){
                dao.refreshCodeNumber(times.get(i));
            }
        }
    }
}


