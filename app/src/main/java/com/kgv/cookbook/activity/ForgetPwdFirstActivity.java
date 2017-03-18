package com.kgv.cookbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;
import com.kgv.cookbook.util.StringUtils;

import java.util.HashMap;

/**
 * Created by 陈可轩 on 2017/2/8 15:53
 * Email : 18627241899@163.com
 * Description : 忘记密码1
 */
public class ForgetPwdFirstActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private ImageView iv_tip_phone;
    private EditText et_phone;
    private EditText et_code;
    private TextView tv_getCode;
    private TextView tv_next;
    private String phone;
    private String code;
    private boolean phoneRight;
    private RelativeLayout rl_parent;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_forget_pwd_first;
    }

    @Override
    protected boolean hasBottomMenu() {
        return true;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        initUI();
        initListener();
    }

    private void initUI() {
        iv_tip_phone = (ImageView) findViewById(R.id.iv_tip_phone);
        iv_tip_phone = (ImageView) findViewById(R.id.iv_tip_phone);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        tv_getCode = (TextView) findViewById(R.id.tv_getCode);
        tv_next = (TextView) findViewById(R.id.tv_next);
        et_phone.setOnFocusChangeListener(this);
        et_code.setOnFocusChangeListener(this);
        rl_parent = (RelativeLayout) findViewById(R.id.rl_parent);
    }

    private void initListener() {
        tv_getCode.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        findViewById(R.id.parent).setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus){
            switch (v.getId()){
                case R.id.et_phone:
                    phone = et_phone.getText().toString().trim();
                    if (TextUtils.isEmpty(phone) || !StringUtils.isPhoneNumber(phone)){
                        iv_tip_phone.setImageResource(R.drawable.edit_tip_error);
                        phoneRight = false;
                    }else{
                        iv_tip_phone.setImageResource(R.drawable.edit_tip_right);
                        phoneRight = true;
                    }
                    break;
                case R.id.et_code:
                    code = et_code.getText().toString().trim();

                    break;
            }
        }
    }

    private void getCode() {
        findViewById(R.id.rl_parent).requestFocus();
        if (!phoneRight){
            tip("请输入正确的手机号码");
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("opt", "password");
        mHttpUtils.doPost(Urls.FORGET_GET_CODE, params, null);
        tip("验证码已发送至："+ phone);
        LogUtils.v("change",Urls.FORGET_GET_CODE);
    }

    private void checkCode() {
        findViewById(R.id.rl_parent).requestFocus();
        if(!phoneRight){
            tip("请输入手机号并获取验证码");
            return;
        }
        if (TextUtils.isEmpty(code) || !StringUtils.isNumber(code)){
            tip("验证码错误，请重新输入");
            return;
        }
        HashMap<String,String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("code", code);
        mHttpUtils.doPost(Urls.CHECK_CODE, params, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(0,str).sendToTarget();
            }

            @Override
            public void onError(String msg) {
                myHandler.obtainMessage(0,"JSON读取失败" + msg).sendToTarget();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_getCode:
                getCode();
                break;
            case R.id.tv_next:
                checkCode();
                break;
            case R.id.parent:
                rl_parent.requestFocus();
                hideKeyboard();
                break;
        }
    }

    @Override
    protected void handleMsg0(Message msg) {
        String response = (String) msg.obj;
        if ("1".equals(response)){
            Intent intent = new Intent(this,ForgetPwdSecondActivity.class);
            intent.putExtra(IntentKeys.FORGET_PWD_PHONE,phone);
            intent.putExtra(IntentKeys.FORGET_PWD_CODE,code);
            jumpActivity(intent,false);
        }else{
            tip(response);
        }
    }

    @Override
    protected void home() {

    }
}
