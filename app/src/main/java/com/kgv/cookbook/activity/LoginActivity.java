package com.kgv.cookbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.kgv.cookbook.CookbookApplication;
import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.SpKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.service.LockService;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;
import com.kgv.cookbook.util.SpUtils;
import com.kgv.cookbook.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/3 9:49
 * Email : 18627241899@163.com
 * Description : 应用入口,登录页面。
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_username;
    private EditText et_password;
    private RelativeLayout rl_parent;
    private String username;
    private String password;
    private TextView tv_local_login;
    private String localUsername;
    private String localPassword;
    private boolean isJump;

    @Override
    protected boolean hasBottomMenu() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;

    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        isJump = getIntent().getBooleanExtra(IntentKeys.JUMP_LOGIN,false);
        initUI();
        Intent intent = new Intent(this, LockService.class);
        startService(intent);
        checkHasLocalUser();

        //        mHelper.init(this,new BaseVoiceListener(),new BaseDialogListener());
        //        myHandler.sendEmptyMessage(88);
    }

    private void initUI() {
        rl_parent = (RelativeLayout) findViewById(R.id.rl_parent);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        findViewById(R.id.tv_login).setOnClickListener(this);
        findViewById(R.id.tv_jump).setOnClickListener(this);
        findViewById(R.id.tv_forget).setOnClickListener(this);
        findViewById(R.id.tv_register).setOnClickListener(this);
        findViewById(R.id.parent).setOnClickListener(this);
        tv_local_login = (TextView) findViewById(R.id.tv_local_login);
        tv_local_login.setOnClickListener(this);
    }

    private void checkHasLocalUser() {
        localUsername = SpUtils.getString(this, SpKeys.LOGIN_USERNAME);
        localPassword = SpUtils.getString(this, SpKeys.LOGIN_PASSWORD);
        LogUtils.v("sp", "localUsername = " + localUsername);
        LogUtils.v("sp", "localPassword = " + localPassword);
        if (!TextUtils.isEmpty(localUsername) && !TextUtils.isEmpty(localPassword)) {
            tv_local_login.setText("快速登录：" + localUsername);
            tv_local_login.setVisibility(View.VISIBLE);
        } else {
            tv_local_login.setVisibility(View.GONE);
        }
    }

    @Override
    protected void subClassVoiceBusiness(List<String> result, String words) {
        if (result.contains("跳过") || "跳过".equals(words) || result.contains("跳") || result.contains("过")) {
            jump();
        } else if (result.contains("登录") || "登录".equals(words)) {
            checkUserNameAndPwd();
        } else if (result.contains("注册") || "点击注册".equals(words)) {
            jumpActivity(RegisterActivity.class, false);
        } else if ("忘记密码".equals(words)) {
            jumpActivity(ForgetPwdFirstActivity.class, false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                checkUserNameAndPwd();
                break;
            case R.id.tv_jump:
                jump();
                break;
            case R.id.tv_forget:
                jumpActivity(ForgetPwdFirstActivity.class, false);
                break;
            case R.id.tv_register:
                jumpActivity(RegisterActivity.class, false);
                break;
            case R.id.tv_local_login:
                if (isJump){
                    sendLoginBroadcast();
                }
                CookbookApplication.setUser("local", localUsername, localPassword);
                LogUtils.v("User local", localUsername + " " + localPassword);
                jumpActivity(MainActivity.class, true);
                break;
            case R.id.parent:
                rl_parent.requestFocus();
                hideKeyboard();
                break;
        }
    }

    private void sendLoginBroadcast(){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent("com.kgv.cookbook.LOGIN");
        localBroadcastManager.sendBroadcast(intent);
    }

    private void jump() {
        LogUtils.v("test","isJump = " + isJump);
        if (isJump){
            sendLoginBroadcast();
        }
//        if (AppUtils.DEBUG) {
//            CookbookApplication.setUser("1", "123", "chenke");
//            saveUser2sp("123", "chenke");
//        } else {
//            CookbookApplication.setUser(false);
//        }
        CookbookApplication.setUser(false);
        jumpActivity(MainActivity.class, true);
    }

    private void checkUserNameAndPwd() {
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            tip("用户名或账号不能为空");
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(findViewById(R.id.ll_input));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            tip("密码不能为空");
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(findViewById(R.id.ll_input));
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        mHttpUtils.doPost(Urls.LOGIN, params, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(0, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    @Override
    protected void handleMsg0(Message msg) {
        String json = (String) msg.obj;
        if (StringUtils.isNumber(json)) {
            if (isJump){
                sendLoginBroadcast();
            }
            saveUser2sp(username, password);
            CookbookApplication.setUser(json, username, password);
            LogUtils.v("User", username + " " + password);
            jumpActivity(MainActivity.class, true);
        } else {
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(findViewById(R.id.ll_input));
            tip("登录失败:" + json);
        }
    }

    private void saveUser2sp(String username, String password) {
        SpUtils.saveStringByCommit(this, SpKeys.LOGIN_USERNAME, username);
        SpUtils.saveStringByCommit(this, SpKeys.LOGIN_PASSWORD, password);
    }

}
