package com.kgv.cookbook.base;

import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.kgv.cookbook.CookbookApplication;
import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.FamilySettingActivity;
import com.kgv.cookbook.activity.LoginActivity;
import com.kgv.cookbook.activity.MainActivity;
import com.kgv.cookbook.activity.SearchActivity;
import com.kgv.cookbook.bean.User;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.VoiceHelper;
import com.kgv.cookbook.util.HttpUtils;
import com.kgv.cookbook.util.LogUtils;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Created by 陈可轩 on 2016/11/25 11:28
 * Email : 18627241899@163.com
 * Description : activity基类,抽取所有共性。
 */
public abstract class BaseActivity extends FragmentActivity {

    protected BaseClickListener mClickListener;
    protected BaseTouchListener mTouchListener;
    protected AudioManager mAudioManager;
    protected InnerHandler myHandler;
    protected HttpUtils mHttpUtils;
    protected User mUser;
    private FrameLayout content;
    private EditText et;
    private boolean inSearch = false;
    protected int flag = -1;
    public int materialOrRecipe = -1;
    protected VoiceHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.v("nba", "setContentView       start : " + System.currentTimeMillis());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentViewId());
        getWindow().setBackgroundDrawable(null);
        LogUtils.v("nba", "setContentView       end : " + System.currentTimeMillis());
        mUser = CookbookApplication.getUser();
        mHttpUtils = HttpUtils.getInstance();
        myHandler = new InnerHandler(this);
        mHelper = VoiceHelper.getInstance();
        if (hasBottomMenu()) {
            initBottomMenu();
            mAudioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        }
        initialization(savedInstanceState);
        LogUtils.v("nba", " activity init       end : " + System.currentTimeMillis());
    }

    /**
     * 布局文件id
     */
    protected abstract int getContentViewId();

    /**
     * 初始化
     */
    protected abstract void initialization(Bundle savedInstanceState);

    /**
     * 是否有底部菜单
     */
    protected abstract boolean hasBottomMenu();

    private void initBottomMenu() {
        mClickListener = new BaseClickListener();
        findViewById(R.id.bottom_search).setOnClickListener(mClickListener);
        findViewById(R.id.bottom_down).setOnClickListener(mClickListener);
        findViewById(R.id.bottom_home).setOnClickListener(mClickListener);
        findViewById(R.id.bottom_up).setOnClickListener(mClickListener);
        findViewById(R.id.bottom_back).setOnClickListener(mClickListener);
        /*   search   */
        mTouchListener = new BaseTouchListener();
        content = (FrameLayout) findViewById(R.id.fl_search_content);
        et = (EditText) findViewById(R.id.base_et_search);
        findViewById(R.id.base_tv_go).setOnTouchListener(mTouchListener);
    }

    protected void tip(String text) {
        Toast.makeText(CookbookApplication.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    protected void hideKeyboard(){
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void jumpActivity(Class cls, boolean finish) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        if (finish) {
            finish();
        }
        overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
    }

    protected void jumpActivity(Intent intent, boolean finish) {
        startActivity(intent);
        if (finish) {
            finish();
        }
        overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
    }

    private void volumeReduce() {
        mAudioManager.adjustStreamVolume(
                AudioManager.STREAM_MUSIC,
                AudioManager.ADJUST_LOWER,
                AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
    }

    private void volumeAdd() {
        mAudioManager.adjustStreamVolume(
                AudioManager.STREAM_MUSIC,
                AudioManager.ADJUST_RAISE,
                AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
    }

    protected void back() {
        this.finish();
        overridePendingTransition(R.anim.activity_in_left_2_right, R.anim.activity_out_left_2_right);
    }

    protected void home() {
        jumpActivity(MainActivity.class, true);
    }

    private void search() {
        if (inSearch) {
            inSearch = false;
            ObjectAnimator animator = ObjectAnimator.ofFloat(content, "translationY",
                    -5, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 95);
            animator.setDuration(400);
            animator.start();
        } else {
            inSearch = true;
            content.setVisibility(View.VISIBLE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(content, "translationY",
                    95, 90, 80, 70, 60, 50, 40, 30, 20, 10, 0, -5);
            animator.setDuration(400);
            animator.start();
        }
    }

    protected void showSetFamilyDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示")
                .setMessage("尚未设置家庭成员，是否立即设置？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(BaseActivity.this, FamilySettingActivity.class);
                        intent.putExtra(IntentKeys.JUMP_SET_FAMILY, true);
                        BaseActivity.this.jumpActivity(intent, false);
                    }
                })
                .show();
    }

    protected void showLoginDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示")
                .setMessage("该功能需登录使用，是否立即登录？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(BaseActivity.this,LoginActivity.class);
                        intent.putExtra(IntentKeys.JUMP_LOGIN,true);
                        jumpActivity(intent,false);
                    }
                })
                .show();
    }

    public class BaseVoiceListener implements RecognizerListener {

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
        }

        @Override
        public void onBeginOfSpeech() {
            LogUtils.v("voice", "onBeginOfSpeech");
        }

        @Override
        public void onEndOfSpeech() {
            LogUtils.v("voice", "onEndOfSpeech");
            myHandler.sendEmptyMessage(88);
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            List<String> result = mHelper.parseResult4List(recognizerResult.getResultString());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.size(); i++) {
                sb.append(result.get(i));
            }
            String words = sb.toString();
            LogUtils.v("voice", words);
            if (hasBottomMenu()) {
                if ("搜索".equals(words) || result.contains("搜索")) {
                    search();
                    return;
                } else if ("音量减".equals(words) || result.contains("减")) {
                    volumeReduce();
                    return;
                } else if ("音量加".equals(words) || result.contains("加")) {
                    volumeAdd();
                    return;
                } else if ("主页".equals(words) || result.contains("主页")) {
                    home();
                    return;
                } else if ("返回".equals(words) || result.contains("返回")) {
                    back();
                    return;
                }
            }
            subClassVoiceBusiness(result,words);
        }

        @Override
        public void onError(SpeechError speechError) {
            LogUtils.v("voice", "onError = " + speechError.getErrorDescription());
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    }

    public class BaseDialogListener implements RecognizerDialogListener {

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            List<String> result = mHelper.parseResult4List(recognizerResult.getResultString());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.size(); i++) {
                sb.append(result.get(i));
            }
            String words = sb.toString();
            LogUtils.v("voice", words);
            if (hasBottomMenu()) {
                if ("搜索".equals(words) || result.contains("搜索")) {
                    search();
                    return;
                } else if ("音量减".equals(words) || result.contains("减")) {
                    volumeReduce();
                    return;
                } else if ("音量加".equals(words) || result.contains("加")) {
                    volumeAdd();
                    return;
                } else if ("主页".equals(words) || result.contains("主页")) {
                    home();
                    return;
                } else if ("返回".equals(words) || result.contains("返回")) {
                    back();
                    return;
                }
            }
            subClassVoiceBusiness(result,words);
        }

        @Override
        public void onError(SpeechError speechError) {
            LogUtils.v("voice", "onError = " + speechError.getErrorDescription());
        }
    }

    protected void subClassVoiceBusiness(List<String> result,String words) {
    }

    protected class BaseClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bottom_down:
                    volumeReduce();
                    break;
                case R.id.bottom_up:
                    volumeAdd();
                    break;
                case R.id.bottom_back:
                    back();
                    break;
                case R.id.bottom_home:
                    home();
                    break;
                case R.id.bottom_search:
                    search();
                    break;
            }
        }
    }

    protected class BaseTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setAlpha(0.4f);
                    break;
                case MotionEvent.ACTION_UP:
                    v.setAlpha(1.0f);
                    String keyword = et.getText().toString().trim();
                    if (TextUtils.isEmpty(keyword)) {
                        tip("请输入食材或食谱");
                        return true;
                    }
                    AppUtils.hideKeyboard(et);
                    jumpActivity(new Intent(AppUtils.getContext(), SearchActivity.class)
                            .putExtra(IntentKeys.SEARCH_KEYWORD, keyword), false);
                    break;
            }
            return true;
        }
    }

    protected static class InnerHandler extends Handler {

        private WeakReference<BaseActivity> reference;

        public InnerHandler(BaseActivity baseActivity) {
            reference = new WeakReference<>(baseActivity);
        }

        @Override
        public void handleMessage(Message msg) {

            BaseActivity activity = reference.get();

            if (activity == null) {
                return;
            }

            switch (msg.what) {
                case 0:
                    activity.handleMsg0(msg);
                    break;
                case 1:
                    activity.handleMsg1(msg);
                    break;
                case 2:
                    activity.handleMsg2(msg);
                    break;
                case 3:
                    activity.handleMsg3(msg);
                    break;
                case 4:
                    activity.handleMsg4(msg);
                    break;
                case 5:
                    activity.handleMsg5(msg);
                    break;
                case 6:
                    activity.handleMsg6(msg);
                    break;
                case 7:
                    activity.handleMsg7(msg);
                    break;
                case 8:
                    activity.handleMsg8(msg);
                    break;
                case 9:
                    activity.handleMsg9(msg);
                    break;
                case 10:
                    activity.handleMsg10(msg);
                    break;
                case 11:
                    activity.handleMsg11(msg);
                    break;
                case 12:
                    activity.handleMsg12(msg);
                    break;
                case 88:
                    activity.handleMsg88();
                    break;
            }
        }
    }

    private void handleMsg88() {
        mHelper.startRecording();
    }

    protected void handleMsg0(Message msg) {
    }

    protected void handleMsg1(Message msg) {
    }

    protected void handleMsg2(Message msg) {
    }

    protected void handleMsg3(Message msg) {
    }

    protected void handleMsg4(Message msg) {
    }

    protected void handleMsg5(Message msg) {
    }

    protected void handleMsg6(Message msg) {
    }

    protected void handleMsg7(Message msg) {
    }

    protected void handleMsg8(Message msg) {
    }

    protected void handleMsg9(Message msg) {
    }

    protected void handleMsg10(Message msg) {
    }

    protected void handleMsg11(Message msg) {
    }

    protected void handleMsg12(Message msg) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);
    }

    public class LoginBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive (Context context, Intent intent) {
            mUser = CookbookApplication.getUser();
            onLoginReceiver();
        }
    }

    protected void onLoginReceiver () {
        LogUtils.v("test", "base 收到登录广播");
    }
}
