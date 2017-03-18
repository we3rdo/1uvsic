package com.kgv.cookbook.activity;

import android.os.Bundle;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;

/**
 * Created by 陈可轩 on 2016/12/31 16:53
 * Email : 18627241899@163.com
 * Description : 用户协议页
 */
public class ProtocolActivity extends BaseActivity {

    @Override
    protected boolean hasBottomMenu() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_protocol;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {

    }


}
