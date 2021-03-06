package com.kgv.cookbook.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.ui.DragFrameLayout;

/**
 * Created by 陈可轩 on 2017/1/19 18:34
 * Email : 18627241899@163.com
 * Description : 闪屏页->广告页
 */
public class AdActivity extends BaseActivity {

    private WebView webView;
    private DragFrameLayout parent;

    @Override
    protected boolean hasBottomMenu() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_ad;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        initUI();
        startLoadUrl();
    }

    private void initUI() {
        webView = (WebView) findViewById(R.id.webView);
        parent = (DragFrameLayout)findViewById(R.id.parent);
        parent.setDragImageListener(new DragFrameLayout.DragImageClickListener() {
            @Override
            public void onClick () {
                exit();
            }
        });
    }

    private void startLoadUrl(){
        //webView.loadUrl("https://mall.midea.com/");
        //webView.loadUrl("http://haierlzhe.tmall.com/");
        webView.loadUrl("http://meloburg.com/index.php?m=content&c=index&a=lists&catid=6");

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);  // 设置可以支持缩放
        webView.getSettings().setUseWideViewPort(true);  //扩大比例的缩放
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);    //自适应屏幕
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient(){
            //当加载的url地址发生跳转后调用
            //防止启动系统自带的浏览器
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void exit(){
        if (webView.canGoBack()){
            webView.goBack();
        }else{
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
        parent.removeAllViews();
        webView.destroy();
    }

}

