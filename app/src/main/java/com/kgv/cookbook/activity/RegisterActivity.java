package com.kgv.cookbook.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.db.dao.PhoneCodeDAO;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.StringUtils;

import java.util.HashMap;

/**
 * Created by 陈可轩 on 2016/12/3 9:49
 * Email : 18627241899@163.com
 * Description : 注册页
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText et_username, et_phone_number, et_password, et_confirm_pwd, et_code;
    private ImageView iv_tip_username, iv_tip_phone_number, iv_tip_password, iv_tip_confirm_pwd, iv_agree;
    private TextView tv_get_code, tv_protocol;
    private RelativeLayout rl_parent;
    private boolean agree;
    //失去焦点时获取
    private String username;
    private String phoneNumber;
    private String password;
    private String confirmPwd;
    //当前文本是否合法
    private boolean usernameRight = false;
    private boolean phoneNumberRight = false;
    private boolean passwordRight = false;
    private boolean confirmPwdRight = false;
    private int s = 60;                         //倒计时
    private boolean isSending = false;          //是否正在发送
    private PhoneCodeDAO dao;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        dao = new PhoneCodeDAO(this);
        initUI();
        initListener();
    }

    @Override
    protected boolean hasBottomMenu() {
        return false;
    }

    private void initUI() {
        rl_parent = (RelativeLayout) findViewById(R.id.rl_parent);
        et_username = (EditText) findViewById(R.id.et_username);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_pwd = (EditText) findViewById(R.id.et_confirm_pwd);
        et_code = (EditText) findViewById(R.id.et_code);
        iv_tip_username = (ImageView) findViewById(R.id.iv_tip_username);
        iv_tip_phone_number = (ImageView) findViewById(R.id.iv_tip_phone_number);
        iv_tip_password = (ImageView) findViewById(R.id.iv_tip_password);
        iv_tip_confirm_pwd = (ImageView) findViewById(R.id.iv_tip_confirm_pwd);
        iv_agree = (ImageView) findViewById(R.id.iv_agree);
        tv_get_code = (TextView) findViewById(R.id.tv_get_code);
        tv_protocol = (TextView) findViewById(R.id.tv_protocol);
    }

    private void initListener() {
        et_username.setOnFocusChangeListener(this);
        et_phone_number.setOnFocusChangeListener(this);
        et_password.setOnFocusChangeListener(this);
        et_confirm_pwd.setOnFocusChangeListener(this);
        findViewById(R.id.tv_register).setOnClickListener(this);
        findViewById(R.id.tv_login).setOnClickListener(this);
        findViewById(R.id.parent).setOnClickListener(this);
        tv_get_code.setOnClickListener(this);
        iv_agree.setOnClickListener(this);
        tv_protocol.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tv_protocol.setAlpha(0.3f);
                        break;
                    case MotionEvent.ACTION_UP:
                        tv_protocol.setAlpha(1.0f);
                        //jumpActivity(ProtocolActivity.class,false);
                        //TODO
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_code:
                checkPhoneNumberAndGetCode();
                break;
            case R.id.iv_agree:
                iv_agree.setImageResource(agree ? R.drawable.reg_cb_normal : R.drawable.reg_cb_checked);
                agree = !agree;
                break;
            case R.id.tv_register:
                checkAndRegister();
                break;
            case R.id.tv_login:
                back();
                break;
            case R.id.parent:
                rl_parent.requestFocus();
                hideKeyboard();
                break;
        }
    }

    /**
     * 动态判断四个输入框在失去焦点时,输入框内文本是否合法
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            switch (v.getId()) {
                case R.id.et_username:
                    username = et_username.getText().toString().trim();
                    if (!TextUtils.isEmpty(username)) {
                        checkUsernameIsExist(username);
                    }else{
                        iv_tip_username.setImageResource(R.drawable.edit_tip_error);
                        usernameRight = false;
                    }
                    iv_tip_username.setVisibility(View.VISIBLE);
                    break;
                case R.id.et_phone_number:
                    phoneNumber = et_phone_number.getText().toString().trim();
                    if (!TextUtils.isEmpty(phoneNumber) && StringUtils.isPhoneNumber(phoneNumber)) {
                        checkPhoneNumberIsExist(phoneNumber);
                    } else {
                        iv_tip_phone_number.setImageResource(R.drawable.edit_tip_error);
                        phoneNumberRight = false;
                    }
                    iv_tip_phone_number.setVisibility(View.VISIBLE);
                    break;
                case R.id.et_password:
                    password = et_password.getText().toString().trim();
                    if (!TextUtils.isEmpty(password) && password.length() >= 6) {
                        iv_tip_password.setImageResource(R.drawable.edit_tip_right);
                        passwordRight = true;
                    } else {
                        iv_tip_password.setImageResource(R.drawable.edit_tip_error);
                        passwordRight = false;
                    }
                    iv_tip_password.setVisibility(View.VISIBLE);
                    //重新输入密码后,检查确认密码是否相同
                    if (!TextUtils.isEmpty(confirmPwd) && confirmPwd.equals(password)) {
                        confirmPwdRight = true;
                        iv_tip_confirm_pwd.setImageResource(R.drawable.edit_tip_right);
                    } else {
                        confirmPwdRight = false;
                        iv_tip_confirm_pwd.setImageResource(R.drawable.edit_tip_error);
                    }
                    break;
                case R.id.et_confirm_pwd:
                    confirmPwd = et_confirm_pwd.getText().toString().trim();
                    if (!TextUtils.isEmpty(confirmPwd) && confirmPwd.equals(password)) {
                        confirmPwdRight = true;
                        iv_tip_confirm_pwd.setImageResource(R.drawable.edit_tip_right);
                    } else {
                        confirmPwdRight = false;
                        iv_tip_confirm_pwd.setImageResource(R.drawable.edit_tip_error);
                    }
                    iv_tip_confirm_pwd.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    /**
     * 获取验证码
     * 检查当前手机号码合法性并请求发送验证码
     */
    private void checkPhoneNumberAndGetCode() {
        rl_parent.requestFocus();//让输入框失去焦点

        //检查手机号是否合法
        if (phoneNumberRight) {
            //保存当前手机号&次数
            if (!dao.isExist(phoneNumber)){
                dao.addNewPhone(phoneNumber);
            }

            //当前次数
            int codeNumber = dao.getCodeNumber(phoneNumber);

            if (codeNumber == 0) {
                tip("每个手机号每天只能接收3次验证码");
                return;
            }

            if (!isSending) {
                //每次请求发送 次数-1
                codeNumber -= 1;
                dao.updateCodeNumber(phoneNumber,codeNumber);

                /** 请求验证码 **/
                HashMap<String, String> params = new HashMap<>();
                params.put("mobile", phoneNumber);
                params.put("opt", "register");
                mHttpUtils.doPost(Urls.REGISTER_GET_CODE, params, null);

                //点击重新发送
                tv_get_code.setText(s + "秒后可重试 ");
                myHandler.sendEmptyMessage(2);
                tip("验证码已发送至" + phoneNumber);
                isSending = true;
            }
        } else {
            tip("请输入正确的手机号码");
        }

    }

    /**
     *  检查手机号是否已存在
     */
    private void checkPhoneNumberIsExist(String phoneNumber) {
        HashMap<String,String> params = new HashMap<>();
        params.put("mobile",phoneNumber);
        mHttpUtils.doPost(Urls.CHECK_PHONE_NUMBER, params, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(3,str).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 注册
     * 首先让所有输入框失去焦点获取文本,然后判断文本合法性
     */
    private void checkAndRegister() {
        rl_parent.requestFocus();//让输入框失去焦点
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(username) || !usernameRight) {
            tip("请检查用户名");
            return;
        }
        if (TextUtils.isEmpty(phoneNumber) || !phoneNumberRight) {
            tip("请检查手机号");
            return;
        }
        if (TextUtils.isEmpty(password) || !passwordRight) {
            tip("请检查密码");
            return;
        }
        if (TextUtils.isEmpty(confirmPwd) || !confirmPwdRight) {
            tip("请检查确认密码");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            tip("验证码不能为空");
            return;
        }
        if (!agree) {
            tip("您尚未同意《皮米用户服务协议》");
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", phoneNumber);
        params.put("code", code);
        params.put("user_name", username);
        params.put("password", password);
        mHttpUtils.doPost(Urls.REGISTER, params, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(1, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 检查用户名是否存在
     */
    private void checkUsernameIsExist(String username) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        mHttpUtils.doPost(Urls.CHECK_USERNAME, params, new HttpResponse<String>(String.class) {
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
        if ("0".equals(json)) {
            usernameRight = false;
            iv_tip_username.setImageResource(R.drawable.edit_tip_error);
        } else if ("1".equals(json)) {
            iv_tip_username.setImageResource(R.drawable.edit_tip_right);
            usernameRight = true;
        }
    }

    @Override
    protected void handleMsg1(Message msg) {
        String json = (String) msg.obj;
        if (StringUtils.isNumber(json)) {
            tip("注册成功!");
            back();
        } else {
            tip("注册失败:" + json);
        }
    }

    @Override
    protected void handleMsg2(Message msg) {
        myHandler.removeMessages(6);
        if (s == 0) {
            tv_get_code.setText("重新发送");
            s = 60;
            isSending = false;
        } else {
            tv_get_code.setText(s + "秒后可重试");
            s--;
            myHandler.sendEmptyMessageDelayed(2, 1000);
        }
    }

    @Override
    protected void handleMsg3(Message msg) {
        String json = (String) msg.obj;
        if ("0".equals(json)){
            iv_tip_phone_number.setImageResource(R.drawable.edit_tip_error);
            phoneNumberRight = false;
        }else if("1".equals(json)){
            iv_tip_phone_number.setImageResource(R.drawable.edit_tip_right);
            phoneNumberRight = true;
        }
    }
}
