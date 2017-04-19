package com.kgv.cookbook.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.kgv.cookbook.CookbookApplication;
import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.bean.VersionBean;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.SpKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.service.LockService;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;
import com.kgv.cookbook.util.SpUtils;
import com.kgv.cookbook.util.StringUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
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
    private NumberProgressBar progressBar;
    private TextView tvSuccess;
    private TextView tvInstall;
    private TextView tvCancel;
    private AlertDialog alertDialog;
    private File file;
//    private TextView msg;
//    private String stringMsg = "";

    @Override
    protected boolean hasBottomMenu () {
        return false;
    }

    @Override
    protected int getContentViewId () {
        return R.layout.activity_login;

    }

    @Override
    protected void initialization (Bundle savedInstanceState) {
        if (!AppUtils.isNetAvailable(CookbookApplication.getContext())){
            Toast.makeText(this,"当前网络不可用,请检查您的网络连接",Toast.LENGTH_LONG).show();
        }
        isJump = getIntent().getBooleanExtra(IntentKeys.JUMP_LOGIN, false);
        initUI();
        Intent intent = new Intent(this, LockService.class);
        startService(intent);
        checkHasLocalUser();
        checkNeedUpdate();
        //        mHelper.init(this,new BaseVoiceListener(),new BaseDialogListener());
        //        myHandler.sendEmptyMessage(88);
    }

    private void checkNeedUpdate () {
        mHttpUtils.doGet(Urls.UPDATE_URL, new HttpResponse<VersionBean>(VersionBean.class) {
            @Override
            public void onSuccess (VersionBean bean) {
                myHandler.obtainMessage(1, bean).sendToTarget();
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    private void initUI () {
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

    private void checkHasLocalUser () {
        localUsername = SpUtils.getString(this, SpKeys.LOGIN_USERNAME);
        localPassword = SpUtils.getString(this, SpKeys.LOGIN_PASSWORD);
        LogUtils.v("login", " check localUsername = " + localUsername);
        LogUtils.v("login", " check localPassword = " + localPassword);
        if (! TextUtils.isEmpty(localUsername) && ! TextUtils.isEmpty(localPassword)) {
            tv_local_login.setText("快速登录：" + localUsername);
            tv_local_login.setVisibility(View.VISIBLE);
        } else {
            tv_local_login.setVisibility(View.GONE);
        }
    }

    @Override
    protected void subClassVoiceBusiness (List<String> result, String words) {
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
    public void onClick (View v) {
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
                if (isJump) {
                    sendLoginBroadcast();
                }
                localLogin();
                break;
            case R.id.parent:
                rl_parent.requestFocus();
                hideKeyboard();
                break;
        }
    }

    private void localLogin () {
        if (!AppUtils.isNetAvailable(this)){
            tip("网络异常，请检查网络连接");
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("username", localUsername);
        params.put("password", localPassword);
        LogUtils.v("login", " put localUsername = " + localUsername);
        LogUtils.v("login", " put localPassword = " + localPassword);
        mHttpUtils.doPost(Urls.LOGIN, params, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                myHandler.obtainMessage(2, str).sendToTarget();
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    private void sendLoginBroadcast () {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent("com.kgv.cookbook.LOGIN");
        localBroadcastManager.sendBroadcast(intent);
    }

    private void jump () {
        LogUtils.v("test", "isJump = " + isJump);
        if (isJump) {
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

    private void checkUserNameAndPwd () {
        if (!AppUtils.isNetAvailable(this)){
            tip("网络异常，请检查网络连接");
            return;
        }
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
            public void onSuccess (String str) {
                myHandler.obtainMessage(0, str).sendToTarget();
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    @Override
    protected void handleMsg0 (Message msg) {
        String json = (String) msg.obj;
        if (StringUtils.isNumber(json)) {
            if (isJump) {
                sendLoginBroadcast();
            }
            saveUser2sp(username, password);
            CookbookApplication.setUser(json, username, password);
            LogUtils.v("login", "login success ! username = " + username + " password = " + password);
            jumpActivity(MainActivity.class, true);
        } else {
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(findViewById(R.id.ll_input));
            tip("登录失败:" + json);
        }
    }

    @Override
    protected void handleMsg1 (Message msg) {
        VersionBean bean = (VersionBean) msg.obj;
        int oldCode = AppUtils.getVersionCode(this);
        int newCode = Integer.parseInt(bean.getVersion());
        if (newCode > oldCode) {
            showUpdateDiaLog(bean);
        }
    }

    @Override
    protected void handleMsg2(Message msg) {
        String json = (String) msg.obj;
        if (StringUtils.isNumber(json)) {
            if (isJump) {
                sendLoginBroadcast();
            }
            CookbookApplication.setUser(json, localUsername, localPassword);
            LogUtils.v("login", "local login success ! local username = " + localUsername + " local password = " + localPassword);
            jumpActivity(MainActivity.class, true);
        }else{
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(findViewById(R.id.ll_input));
            tip("登录失败:" + json);
        }
    }

    private void showUpdateDiaLog (final VersionBean bean) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("更新提示")
                .setMessage("检测到最新版本，是否进行升级？\nv" + bean.getVersion() + "\n" + bean.getDescribe())
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        downloadApk(bean.getLink());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void downloadApk (String url) {
        url = "http://" + url;

        LogUtils.v("abc","下载地址 = " + url);

        file = new File(Environment.getExternalStorageDirectory(), "NewApp.apk");
        showDownLoadDiaLog();
        FileDownloader
                .getImpl()
                .create(url)
                .setPath(file.getAbsolutePath())
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending (BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                        stringMsg = "pending";
//                        msg.setText(stringMsg);
                    }

                    @Override
                    protected void progress (BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        int d = (int) (((double) soFarBytes / (double) totalBytes) * 100);
                        progressBar.setProgress(d);
//                        stringMsg += " " + d;
//                        msg.setText(stringMsg);
                    }

                    @Override
                    protected void completed (BaseDownloadTask task) {
                        progressBar.setVisibility(View.GONE);
                        tvSuccess.setVisibility(View.VISIBLE);
                        tvInstall.setVisibility(View.VISIBLE);
                        tvCancel.setText("下次再说");
//                        stringMsg += " completed";
//                        msg.setText(stringMsg);
                    }

                    @Override
                    protected void paused (BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtils.v("paused", "paused");
//                        stringMsg += " paused";
//                        msg.setText(stringMsg);
                    }

                    @Override
                    protected void error (BaseDownloadTask task, Throwable e) {
                        LogUtils.v("error", "error");
//                        stringMsg += " error="+e.getLocalizedMessage();
//                        msg.setText(stringMsg);
                    }

                    @Override
                    protected void warn (BaseDownloadTask task) {
                        LogUtils.v("warn", "warn");
//                        stringMsg += " warn";
//                        msg.setText(stringMsg);
                    }
                }).start();

    }

    private void showDownLoadDiaLog () {
        DiaLogClickListener clickListener = new DiaLogClickListener();
        View contentView = View.inflate(this, R.layout.dialog_download, null);
        progressBar = (NumberProgressBar) contentView.findViewById(R.id.progressBar);
        tvSuccess = (TextView) contentView.findViewById(R.id.tvSuccess);
        tvInstall = (TextView) contentView.findViewById(R.id.tvInstall);
//        msg = (TextView) contentView.findViewById(R.id.msg);
        tvCancel = (TextView) contentView.findViewById(R.id.tvCancel);
        tvInstall.setOnClickListener(clickListener);
        tvCancel.setOnClickListener(clickListener);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        alertDialog = dialog.setTitle("正在下载").setView(contentView).show();

    }

    private void saveUser2sp (String username, String password) {
        SpUtils.saveString(this, SpKeys.LOGIN_USERNAME, username);
        SpUtils.saveString(this, SpKeys.LOGIN_PASSWORD, password);
    }

    private class DiaLogClickListener implements View.OnClickListener {

        @Override
        public void onClick (View v) {
            switch (v.getId()) {
                case R.id.tvInstall:
                    installApk();
                    break;
                case R.id.tvCancel:
                    alertDialog.dismiss();
                    break;
            }
        }
    }

    private void installApk () {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        //data : 文件安装的位置
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }

}
