package com.kgv.cookbook.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kgv.cookbook.activity.LockActivity;
import com.kgv.cookbook.util.LogUtils;

/**
 * Created by 陈可轩 on 2017/3/1 17:14
 * Email : 18627241899@163.com
 * Description :
 */
public class LockBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.v("lock",action);
        if ("android.intent.action.SCREEN_ON".equals(action)) {
            Intent lockIntent = new Intent(context, LockActivity.class);
            lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(lockIntent);
        }
    }
}
