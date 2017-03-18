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
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;

import java.util.HashMap;

/**
 * Created by 陈可轩 on 2017/2/13 10:28
 * Email : 18627241899@163.com
 * Description : 主页-设置-修改密码
 */
public class ChangePwdActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private RelativeLayout rl_parent;
    private EditText et_old_pwd;
    private EditText et_password;
    private EditText et_new_pwd_r;
    private ImageView iv_old_pwd;
    private ImageView iv_new_pwd;
    private ImageView iv_new_pwd_r;
    private String oldPwd;
    private String newPwd;
    private String newPwdR;
    private boolean oldRight = false;
    private boolean newRight = false;
    private boolean newRRight = false;

    @Override
    protected boolean hasBottomMenu() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        initUI();
    }

    private void initUI() {
        rl_parent = (RelativeLayout) findViewById(R.id.rl_parent);
        et_old_pwd = (EditText) findViewById(R.id.et_old_pwd);
        et_password = (EditText) findViewById(R.id.et_password);
        et_new_pwd_r = (EditText) findViewById(R.id.et_new_pwd_r);
        et_old_pwd.setOnFocusChangeListener(this);
        et_password.setOnFocusChangeListener(this);
        et_new_pwd_r.setOnFocusChangeListener(this);
        iv_old_pwd = (ImageView) findViewById(R.id.iv_old_pwd);
        iv_new_pwd = (ImageView) findViewById(R.id.iv_new_pwd);
        iv_new_pwd_r = (ImageView) findViewById(R.id.iv_new_pwd_r);
        findViewById(R.id.tv_ok).setOnClickListener(this);
        findViewById(R.id.parent).setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            switch (v.getId()) {
                case R.id.et_old_pwd:
                    oldPwd = et_old_pwd.getText().toString().trim();
                    if (TextUtils.isEmpty(oldPwd)) {
                        iv_old_pwd.setImageResource(R.drawable.edit_tip_error);
                        iv_old_pwd.setVisibility(View.VISIBLE);
                        return;
                    }
                    iv_old_pwd.setImageResource(oldPwd.equals(mUser.getPassword()) ?
                            R.drawable.edit_tip_right :
                            R.drawable.edit_tip_error);
                    oldRight = oldPwd.equals(mUser.getPassword());
                    iv_old_pwd.setVisibility(View.VISIBLE);
                    break;
                case R.id.et_password:
                    newPwd = et_password.getText().toString().trim();
                    if (TextUtils.isEmpty(newPwd)) {
                        iv_new_pwd.setImageResource(R.drawable.edit_tip_error);
                        iv_new_pwd.setVisibility(View.VISIBLE);
                        return;
                    }
                    if (!TextUtils.isEmpty(newPwdR)) {
                        iv_new_pwd_r.setImageResource(newPwdR.equals(newPwd) ?
                                R.drawable.edit_tip_right :
                                R.drawable.edit_tip_error);
                    }
                    iv_new_pwd.setImageResource(R.drawable.edit_tip_right);
                    newRight = true;
                    iv_new_pwd.setVisibility(View.VISIBLE);
                    break;
                case R.id.et_new_pwd_r:
                    newPwdR = et_new_pwd_r.getText().toString().trim();
                    if (TextUtils.isEmpty(newPwdR)) {
                        iv_new_pwd_r.setImageResource(R.drawable.edit_tip_error);
                        iv_new_pwd_r.setVisibility(View.VISIBLE);
                        return;
                    }
                    iv_new_pwd_r.setImageResource(newPwdR.equals(newPwd) ?
                            R.drawable.edit_tip_right :
                            R.drawable.edit_tip_error);
                    newRRight = newPwdR.equals(newPwd);
                    iv_new_pwd_r.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                changePwd();
                break;
            case R.id.parent:
                rl_parent.requestFocus();
                hideKeyboard();
                break;
        }
    }

    private void changePwd() {
        rl_parent.requestFocus();
        LogUtils.v("changePwd", oldPwd + "    " + newPwd + "      " + newPwdR);
        if (TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(newPwdR)) {
            tip("请输入原密码/新密码");
            return;
        }
        if (!newPwdR.equals(newPwd)) {
            tip("两次新密码不符");
            return;
        }
        if (!oldRight || !newRight || !newRRight) {
            tip("请检查密码是否输入正确");
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("username", mUser.getUsername());
        params.put("old_password", oldPwd);
        params.put("password", newPwd);
        params.put("repassword", newPwdR);
        mHttpUtils.doPost(Urls.CHANGE_PASSWORD, params, new HttpResponse<String>(String.class) {
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
        if("1".equals(response)){
            tip("修改成功!");
            back();
        }else{
            tip("修改失败：" + response);
        }
    }
}
