package com.kgv.cookbook.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.config.AppUtils;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/3 14:38
 * Email : 18627241899@163.com
 * Description : 食谱机主页
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView background;
    private LinearLayout ll_recipe;
    private LinearLayout ll_application;
    private LinearLayout ll_set_up;
    private LocalBroadcastManager localBroadcastManager;
    private LoginBroadcastReceiver receiver;
    private TextView tvA1,tvA2,tvB1,tvB2,tvC1,tvC2,tvD1,tvD2;

    @Override
    protected boolean hasBottomMenu() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {

        initUI();
        initReceiver();

        show(0);
//        mHelper.init(this, new BaseVoiceListener(), new BaseDialogListener());
//        myHandler.sendEmptyMessage(88);
//        findViewById(R.id.tv_speech).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mHelper.start4DiaLog();
//            }
//        });
    }

    private int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    private void initReceiver () {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter("com.kgv.cookbook.LOGIN");
        receiver = new LoginBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
    }

    private void initUI() {
        background = (ImageView) findViewById(R.id.background);
        ll_recipe = (LinearLayout) findViewById(R.id.ll_recipe);
        ll_application = (LinearLayout) findViewById(R.id.ll_application);
        ll_set_up = (LinearLayout) findViewById(R.id.ll_set_up);
        findViewById(R.id.fl_recipe).setOnClickListener(this);
        findViewById(R.id.fl_manager).setOnClickListener(this);
        findViewById(R.id.fl_application).setOnClickListener(this);
        findViewById(R.id.fl_setup).setOnClickListener(this);
        findViewById(R.id.fl_all_mate).setOnClickListener(this);
        findViewById(R.id.fl_food_world).setOnClickListener(this);
        findViewById(R.id.fl_week).setOnClickListener(this);
        findViewById(R.id.fl_set_meal).setOnClickListener(this);
        findViewById(R.id.fl_video).setOnClickListener(this);
        findViewById(R.id.fl_music).setOnClickListener(this);
        findViewById(R.id.fl_app).setOnClickListener(this);
        findViewById(R.id.fl_system).setOnClickListener(this);
        findViewById(R.id.fl_family).setOnClickListener(this);
        findViewById(R.id.fl_voice).setOnClickListener(this);
        findViewById(R.id.fl_interest).setOnClickListener(this);
        findViewById(R.id.fl_password).setOnClickListener(this);
        findViewById(R.id.bottom_back).setClickable(false);
        tvA1 = (TextView) findViewById(R.id.tvA1);
        tvA2 = (TextView) findViewById(R.id.tvA2);
        tvB1 = (TextView) findViewById(R.id.tvB1);
        tvB2 = (TextView) findViewById(R.id.tvB2);
        tvC1 = (TextView) findViewById(R.id.tvC1);
        tvC2 = (TextView) findViewById(R.id.tvC2);
        tvD1 = (TextView) findViewById(R.id.tvD1);
        tvD2 = (TextView) findViewById(R.id.tvD2);
    }

    @Override
    protected void subClassVoiceBusiness(List<String> result,String words) {
        if (result.contains("食谱") || "食谱".equals(words)) {
            show(0);
            background.setImageResource(R.drawable.main_bg_recipe_0);
        } else if (result.contains("应用") || "应用".equals(words)) {
            show(2);
            background.setImageResource(R.drawable.main_bg_app_0);
        } else if (result.contains("娱乐") || result.contains("视频") || "娱乐视频".equals(words)) {
            jumpActivity(MovieActivity.class, false);
            background.setImageResource(R.drawable.main_bg_app_0);
        } else if (result.contains("音乐") || "音乐".equals(words)) {
            jumpActivity(MusicActivity.class, false);
            background.setImageResource(R.drawable.main_bg_app_1);
        } else if (result.contains("商城") || "商城".equals(words)) {
            jumpActivity(AdActivity.class, false);
            background.setImageResource(R.drawable.main_bg_app_2);
        } else if (result.contains("注册") || "注册".equals(words) || "点击注册".equals(words)) {
            jumpActivity(RegisterActivity.class, false);
        } else if (result.contains("健康") || "管理".equals(words) || "健康".equals(words) || result.contains("管理") || "健康管理".equals(words)) {
            if (! mUser.isExist()) {
                showLoginDialog();
                return;
            }
            background.setImageResource(R.drawable.main_bg_health_manager);
            jumpActivity(HealthActivity.class, false);
        } else if (result.contains("食材") || "食材".equals(words) || "食材大全".equals(words)) {
            background.setImageResource(R.drawable.main_bg_recipe_0);
            jumpActivity(AllMaterialActivity.class, false);
        } else if (result.contains("美食") || "美食".equals(words) || "美食天下".equals(words)) {
            background.setImageResource(R.drawable.main_bg_recipe_1);
            jumpActivity(FoodWorldActivity.class, false);
        } else if (result.contains("一周") || "一周".equals(words) || result.contains("星期") || "一周食谱".equals(words)) {
            background.setImageResource(R.drawable.main_bg_recipe_2);
            if (! mUser.isExist()) {
                showLoginDialog();
                return;
            }
            jumpActivity(AWeekActivity.class, false);
        } else if (result.contains("美味") || "美味".equals(words) || "套餐".equals(words) || result.contains("套餐") || "美味套餐".equals(words)) {
            jumpActivity(SetMealActivity.class, false);
            background.setImageResource(R.drawable.main_bg_recipe_3);
        } else if (result.contains("设置") || "设置".equals(words)) {
            show(3);
            background.setImageResource(R.drawable.main_bg_set_0);
        } else if (result.contains("系统") || "系统".equals(words) || "系统设置".equals(words)) {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            jumpActivity(intent, false);
            background.setImageResource(R.drawable.main_bg_set_0);
        } else if (result.contains("家庭") || "家庭".equals(words) || "家庭设置".equals(words)) {
            if (! mUser.isExist()) {
                showLoginDialog();
                return;
            }
            background.setImageResource(R.drawable.main_bg_set_1);
            jumpActivity(FamilySettingActivity.class, false);
        } else if (result.contains("语音") || "语音".equals(words) || "语音设置".equals(words)) {
            //jumpActivity(VoiceSettingActivity.class,false);
            background.setImageResource(R.drawable.main_bg_set_2);
        } else if (result.contains("喜好") || "喜好".equals(words) || "喜好设置".equals(words)) {
            if (! mUser.isExist()) {
                showLoginDialog();
                return;
            }
            jumpActivity(InterestSettingActivity.class, false);
            background.setImageResource(R.drawable.main_bg_set_3);
        } else if (result.contains("密码") || "密码".equals(words) || "密码设置".equals(words)) {
            if (! mUser.isExist()) {
                showLoginDialog();
                return;
            }
            jumpActivity(ChangePwdActivity.class, false);
            background.setImageResource(R.drawable.main_bg_set_4);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_recipe:
                show(0);
                background.setImageResource(R.drawable.main_bg_recipe_0);
                break;
            case R.id.fl_manager:
                show(1);
                if (! mUser.isExist()) {
                    showLoginDialog();
                    return;
                }
                background.setImageResource(R.drawable.main_bg_health_manager);
                jumpActivity(HealthActivity.class, false);
                break;
            case R.id.fl_application:
                show(2);
                background.setImageResource(R.drawable.main_bg_app_0);
                break;
            case R.id.fl_setup:
                show(3);
                background.setImageResource(R.drawable.main_bg_set_0);
                break;
            case R.id.fl_all_mate:
                background.setImageResource(R.drawable.main_bg_recipe_0);
                jumpActivity(AllMaterialActivity.class, false);
                break;
            case R.id.fl_food_world:
                background.setImageResource(R.drawable.main_bg_recipe_1);
                jumpActivity(FoodWorldActivity.class, false);
                break;
            case R.id.fl_week:
                background.setImageResource(R.drawable.main_bg_recipe_2);
                if (! mUser.isExist()) {
                    showLoginDialog();
                    return;
                }
                jumpActivity(AWeekActivity.class, false);
                break;
            case R.id.fl_set_meal:
                jumpActivity(SetMealActivity.class, false);
                background.setImageResource(R.drawable.main_bg_recipe_3);
                break;
            case R.id.fl_video:
                jumpActivity(MovieActivity.class, false);
                background.setImageResource(R.drawable.main_bg_app_0);
                break;
            case R.id.fl_music:
                jumpActivity(MusicActivity.class, false);
                background.setImageResource(R.drawable.main_bg_app_1);
                break;
            case R.id.fl_app:
                jumpActivity(AdActivity.class, false);
                background.setImageResource(R.drawable.main_bg_app_2);
                break;
            case R.id.fl_system:
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                jumpActivity(intent, false);
                background.setImageResource(R.drawable.main_bg_set_0);
                break;
            case R.id.fl_family:
                if (! mUser.isExist()) {
                    showLoginDialog();
                    return;
                }
                jumpActivity(FamilySettingActivity.class, false);
                background.setImageResource(R.drawable.main_bg_set_1);
                break;
            case R.id.fl_voice:
                //jumpActivity(VoiceSettingActivity.class,false);
                background.setImageResource(R.drawable.main_bg_set_2);
                break;
            case R.id.fl_interest:
                if (! mUser.isExist()) {
                    showLoginDialog();
                    return;
                }
                jumpActivity(InterestSettingActivity.class, false);
                background.setImageResource(R.drawable.main_bg_set_3);
                break;
            case R.id.fl_password:
                if (! mUser.isExist()) {
                    showLoginDialog();
                    return;
                }
                jumpActivity(ChangePwdActivity.class, false);
                background.setImageResource(R.drawable.main_bg_set_4);
                break;
        }
    }

    public void show(int i) {
        ll_recipe.setVisibility(View.GONE);
        ll_application.setVisibility(View.GONE);
        ll_set_up.setVisibility(View.GONE);
        tvA1.setTextColor(AppUtils.getColor(R.color.main));
        tvA2.setTextColor(AppUtils.getColor(R.color.main));
        tvB1.setTextColor(AppUtils.getColor(R.color.main));
        tvB2.setTextColor(AppUtils.getColor(R.color.main));
        tvC1.setTextColor(AppUtils.getColor(R.color.main));
        tvC2.setTextColor(AppUtils.getColor(R.color.main));
        tvD1.setTextColor(AppUtils.getColor(R.color.main));
        tvD2.setTextColor(AppUtils.getColor(R.color.main));
        switch (i) {
            case 0:
                tvA1.setTextColor(AppUtils.getColor(R.color.main_s));
                tvA2.setTextColor(AppUtils.getColor(R.color.main_s));
                ll_recipe.setVisibility(View.VISIBLE);
                break;
            case 1:
                tvB1.setTextColor(AppUtils.getColor(R.color.main_s));
                tvB2.setTextColor(AppUtils.getColor(R.color.main_s));
                break;
            case 2:
                tvC1.setTextColor(AppUtils.getColor(R.color.main_s));
                tvC2.setTextColor(AppUtils.getColor(R.color.main_s));
                ll_application.setVisibility(View.VISIBLE);
                break;
            case 3:
                tvD1.setTextColor(AppUtils.getColor(R.color.main_s));
                tvD2.setTextColor(AppUtils.getColor(R.color.main_s));
                ll_set_up.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void back() {

    }

    @Override
    protected void home() {
        show(0);
        background.setImageResource(R.drawable.main_bg_recipe_0);
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(receiver);
    }
}
