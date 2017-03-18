package com.kgv.cookbook.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 陈可轩 on 2017/3/1 12:14
 * Email : 18627241899@163.com
 * Description :
 */
public class StartBroadcastReceiver extends BroadcastReceiver {

    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
//        LogUtils.v("收到广播",ACTION);
//        if (intent.getAction().equals(ACTION)) {
//            Intent mainActivityIntent = new Intent(context, SplashActivity.class);
//            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mainActivityIntent);
//        }
    }
}
