package com.kgv.cookbook.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kgv.cookbook.CookbookApplication;
import com.kgv.cookbook.bean.User;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.util.HttpUtils;

import java.lang.ref.WeakReference;

/**
 * Created by 陈可轩 on 2016/12/6 13:05
 * Email : 18627241899@163.com
 * Description : fragment基类,抽取所有共性。
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity baseActivity;
    protected HttpUtils httpUtils;
    protected InnerHandler handler;
    protected Context context;
    protected Gson gson;
    protected User mUser;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(getContentViewId(), container, false);
        httpUtils = HttpUtils.getInstance();
        gson = new Gson();
        mUser = CookbookApplication.getUser();
        handler = new InnerHandler(this);
        context = AppUtils.getContext();
        initialization(contentView, savedInstanceState);
        return contentView;
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        baseActivity = (BaseActivity) getActivity();
        initOnActivityCreated(savedInstanceState);
    }


    /**
     * 布局id
     */
    protected abstract int getContentViewId ();

    /**
     * 初始化
     */
    protected abstract void initialization (View contentView, Bundle savedInstanceState);


    protected void initOnActivityCreated (Bundle savedInstanceState) {

    }

    protected void tip (String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        baseActivity = null;
        handler.removeCallbacksAndMessages(null);
    }

    protected static class InnerHandler extends Handler {

        private WeakReference<BaseFragment> reference;

        public InnerHandler (BaseFragment baseFragment) {
            reference = new WeakReference<>(baseFragment);
        }

        @Override
        public void handleMessage (Message msg) {

            BaseFragment fragment = reference.get();

            if (fragment == null) {
                return;
            }

            switch (msg.what) {
                case 0:
                    fragment.handleMsg0(msg);
                    break;
                case 1:
                    fragment.handleMsg1(msg);
                    break;
                case 2:
                    fragment.handleMsg2(msg);
                    break;
                case 3:
                    fragment.handleMsg3(msg);
                    break;
                case 4:
                    fragment.handleMsg4(msg);
                    break;
                case 5:
                    fragment.handleMsg5(msg);
                    break;
                case 6:
                    fragment.handleMsg6(msg);
                    break;
                case 7:
                    fragment.handleMsg7(msg);
                    break;
                case 8:
                    fragment.handleMsg8(msg);
                    break;
                case 9:
                    fragment.handleMsg9(msg);
                    break;
                case 10:
                    fragment.handleMsg10(msg);
                    break;
            }
        }
    }

    protected void handleMsg0 (Message msg) {
    }

    protected void handleMsg1 (Message msg) {
    }

    protected void handleMsg2 (Message msg) {
    }

    protected void handleMsg3 (Message msg) {
    }

    protected void handleMsg4 (Message msg) {
    }

    protected void handleMsg5 (Message msg) {
    }

    protected void handleMsg6 (Message msg) {
    }

    protected void handleMsg7 (Message msg) {
    }

    protected void handleMsg8 (Message msg) {
    }

    protected void handleMsg9 (Message msg) {
    }

    protected void handleMsg10 (Message msg) {
    }
}

