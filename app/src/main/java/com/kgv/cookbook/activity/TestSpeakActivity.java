package com.kgv.cookbook.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iflytek.cloud.SpeechError;
import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;

import java.util.List;

/**
 *       Created by ckx on 2017/4/28.
 */
public class TestSpeakActivity extends BaseActivity {

    private TextView content;

    @Override
    protected boolean hasBottomMenu() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_test_speak;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        initUI();
        initObj();
        content.setText("检测到语音输入：\n");
    }

    private void initUI() {
        content = (TextView) findViewById(R.id.content);
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.start4DiaLog();
            }
        });
    }

    private void initObj() {
        mHelper.init(this, new BaseVoiceListener(), new BaseDialogListener());
    }

    @Override
    protected void subClassVoiceBusiness(List<String> result, String words) {
        content.setText(content.getText() + "  " + words);
    }

    @Override
    protected void subClassVoiceError(SpeechError speechError) {
        String str = speechError.getLocalizedMessage();
        content.setText("错误：\n" + str);
    }
}
