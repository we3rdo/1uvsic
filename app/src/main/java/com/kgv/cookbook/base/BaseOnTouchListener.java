package com.kgv.cookbook.base;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 陈可轩 on 2017/3/6 9:37
 * Email : 18627241899@163.com
 * Description :
 */
public abstract class BaseOnTouchListener implements View.OnTouchListener {


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(0.4f);
                break;
            case MotionEvent.ACTION_UP:
                v.setAlpha(1.0f);
                onViewTouch(v);
                break;
        }
        return true;
    }

    protected abstract void onViewTouch(View v);


}
