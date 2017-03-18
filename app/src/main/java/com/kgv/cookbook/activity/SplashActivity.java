package com.kgv.cookbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.kgv.cookbook.CookbookApplication;
import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.service.LockService;

/**
 * Created by 陈可轩 on 2017/1/3 15:39
 * Email : 18627241899@163.com
 * Description : 闪屏页
 */
public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView;
    private boolean clicked = false;

    @Override
    protected boolean hasBottomMenu() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        checkNetwork();
        initUI();
        Intent intent = new Intent(this, LockService.class);
        startService(intent);
    }

    private void checkNetwork() {
        if (!AppUtils.isNetAvailable(CookbookApplication.getContext())){
            Toast.makeText(this,"当前网络不可用,请检查您的网络连接",Toast.LENGTH_LONG).show();
        }
    }

    private void initUI() {
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AlphaAnimation alpha = new AlphaAnimation(0.2f,1.0f);
        alpha.setDuration(5000);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.clearAnimation();
                //不点击则跳转至登录页
                if (!clicked){
                    jumpActivity(LoginActivity.class,true);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(alpha);
    }

    @Override
    public void onClick(View v) {
        clicked = true;
        imageView.clearAnimation();
        jumpActivity(AdActivity.class,true);

    }
}
