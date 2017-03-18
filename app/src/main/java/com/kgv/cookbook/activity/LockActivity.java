package com.kgv.cookbook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.kgv.cookbook.R;

/**
 * Created by 陈可轩 on 2017/3/1 17:25
 * Email : 18627241899@163.com
 * Description :
 */
public class LockActivity extends Activity implements View.OnClickListener {

    private ImageView imageView;
    private boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_lock);
        initUI();
    }

    private void initUI() {
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        AlphaAnimation alpha = new AlphaAnimation(1.0f,1.0f);
        alpha.setDuration(3000);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.clearAnimation();
                if (!clicked){
                    finish();
                    overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
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
        Intent intent = new Intent(this,AdActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
    }
}
