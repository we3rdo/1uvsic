package com.kgv.cookbook.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kgv.cookbook.receiver.LockBroadcastReceiver;
import com.kgv.cookbook.util.LogUtils;

/**
 * Created by 陈可轩 on 2017/3/1 17:09
 * Email : 18627241899@163.com
 * Description :
 */
public class LockService extends Service {

    private Intent startIntent;
    private LockBroadcastReceiver receiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        startIntent = intent;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        receiver = new LockBroadcastReceiver();
        registerReceiver(receiver, filter);
        LogUtils.v("lock","服务启动了");
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        if (startIntent != null) {
            startService(startIntent);
        }
    }
}
