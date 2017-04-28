package com.kgv.cookbook.activity;

import android.app.Service;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.ui.DragFrameLayout;

/**
 * Created by 陈可轩 on 2017/1/19 18:10
 * Email : 18627241899@163.com
 * Description : 主页-娱乐->音乐
 */
public class MusicActivity extends BaseActivity {

    private WebView webView;
    private DragFrameLayout parent;
    private float downY;
    private float downX;

    @Override
    protected boolean hasBottomMenu() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_music;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        mAudioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        initUI();
        startLoadUrl();
    }

    private void initUI() {
        webView = (WebView) findViewById(R.id.webView);

        parent = (DragFrameLayout) findViewById(R.id.parent);
        parent.setDragImageListener(new DragFrameLayout.DragImageClickListener() {
            @Override
            public void onClick() {
                exit();
            }
        });
    }

    private void startLoadUrl() {
        webView.loadUrl("http://web.kugou.com/");
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);  // 设置可以支持缩放
        webView.getSettings().setUseWideViewPort(true);  //扩大比例的缩放
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);    //自适应屏幕
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downY = event.getRawY();
                        downX = event.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (downX < 250) {
                            float nowY = event.getRawY();
                            if (nowY > downY) {
                                if (nowY - downY > 50){
                                    downY += 50;
                                    volumeReduce();
                                    return true;
                                }
                            } else if (nowY < downY){
                                if (downY - nowY > 50){
                                    downY -= 50;
                                    volumeAdd();
                                    return true;
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            //当加载的url地址发生跳转后调用
            //防止启动系统自带的浏览器
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void exit() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
            overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
        }
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView();
    }

    public void destroyWebView() {
        parent.removeAllViews();
        webView.destroy();
    }
}
