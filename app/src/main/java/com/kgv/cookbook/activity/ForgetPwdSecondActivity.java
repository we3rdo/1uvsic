package com.kgv.cookbook.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.util.HttpResponse;

import java.util.HashMap;

/**
 * Created by 陈可轩 on 2017/2/9 9:48
 * Email : 18627241899@163.com
 * Description : 忘记密码2
 */
public class ForgetPwdSecondActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText et_pwd;
    private EditText et_re_pwd;
    private ImageView iv_tip_pwd;
    private ImageView iv_tip_re_pwd;
    private String phone;
    private String code;
    private String pwd;
    private String confirmPwd;
    private boolean pwdRight, confirmRight;
    private RelativeLayout rl_parent;

    @Override
    protected boolean hasBottomMenu() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_forget_pwd_second;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        phone = getIntent().getStringExtra(IntentKeys.FORGET_PWD_PHONE);
        code = getIntent().getStringExtra(IntentKeys.FORGET_PWD_CODE);
        initUI();
    }

    private void initUI() {
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        rl_parent = (RelativeLayout) findViewById(R.id.rl_parent);
        et_re_pwd = (EditText) findViewById(R.id.et_re_pwd);
        iv_tip_pwd = (ImageView) findViewById(R.id.iv_tip_pwd);
        iv_tip_re_pwd = (ImageView) findViewById(R.id.iv_tip_re_pwd);
        findViewById(R.id.tv_ok).setOnClickListener(this);
        et_pwd.setOnFocusChangeListener(this);
        et_re_pwd.setOnFocusChangeListener(this);
        findViewById(R.id.parent).setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            switch (v.getId()) {
                case R.id.et_pwd:
                    pwd = et_pwd.getText().toString().trim();
                    if (TextUtils.isEmpty(pwd)) {
                        iv_tip_pwd.setImageResource(R.drawable.edit_tip_error);
                        pwdRight = false;
                    } else {
                        if (!TextUtils.isEmpty(confirmPwd)) {
                            if (!pwd.equals(confirmPwd)) {
                                confirmRight = false;
                                iv_tip_re_pwd.setImageResource(R.drawable.edit_tip_error);
                            } else {
                                confirmRight = true;
                                iv_tip_re_pwd.setImageResource(R.drawable.edit_tip_right);
                            }
                        }
                        iv_tip_pwd.setImageResource(R.drawable.edit_tip_right);
                        pwdRight = true;
                    }
                    break;
                case R.id.et_re_pwd:
                    confirmPwd = et_re_pwd.getText().toString().trim();
                    if (TextUtils.isEmpty(confirmPwd)) {
                        confirmRight = false;
                        iv_tip_re_pwd.setImageResource(R.drawable.edit_tip_error);
                    } else {
                        if (confirmPwd.equals(pwd)) {
                            confirmRight = true;
                            iv_tip_re_pwd.setImageResource(R.drawable.edit_tip_right);
                        } else {
                            confirmRight = false;
                            iv_tip_re_pwd.setImageResource(R.drawable.edit_tip_error);
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_ok:
                ok();
                break;
            case R.id.parent:
                rl_parent.requestFocus();
                hideKeyboard();
                break;
        }
    }

    private void ok() {
        findViewById(R.id.rl_parent).requestFocus();
        if (!pwdRight) {
            tip("请输入正确密码");
            return;
        }
        if (!confirmRight) {
            tip("请检查两次密码是否相同");
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("code", code);
        params.put("password", pwd);
        params.put("repassword", confirmPwd);
        mHttpUtils.doPost(Urls.FORGET_PWD, params, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(0, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {
                myHandler.obtainMessage(0, msg).sendToTarget();
            }
        });

    }

    @Override
    protected void handleMsg0(Message msg) {
        String response = (String) msg.obj;
        if ("1".equals(response)) {
            tip("重置密码成功!");
            jumpActivity(LoginActivity.class, true);
        } else {
            tip(response + "，请重试");
        }
    }

    @Override
    protected void home() {

    }
}
