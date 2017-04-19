package com.kgv.cookbook.activity;

import android.os.Bundle;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.config.VoiceHelper;

/**
 * Created by 陈可轩 on 2017/2/27 16:49
 * Email : 18627241899@163.com
 * Description : 主页-设置-语音设置
 */
public class VoiceSettingActivity extends BaseActivity {

    private VoiceHelper helper;

    @Override
    protected boolean hasBottomMenu() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_voice_setting;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        initUI();
    }

    private void initUI() {
                                                                                                                                                  int a = 1;

    }
}


