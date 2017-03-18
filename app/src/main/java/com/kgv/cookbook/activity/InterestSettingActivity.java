package com.kgv.cookbook.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.fragment.interest.InterestMaterialsFragment;
import com.kgv.cookbook.fragment.interest.InterestRecipesFragment;

/**
 * Created by 陈可轩 on 2017/2/11 9:49
 * Email : 18627241899@163.com
 * Description : 主页-设置-喜好设置
 */
public class InterestSettingActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_r;
    private TextView tv_m;
    private FragmentManager fragmentManager;
    private InterestRecipesFragment recipesFragment;
    private InterestMaterialsFragment materialsFragment;
    private LocalBroadcastManager localBroadcastManager;
    private LoginBroadcastReceiver receiver;

    @Override
    protected boolean hasBottomMenu() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_interest_setting;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        flag = 1;//判断食材大全/喜好设置
        fragmentManager = getSupportFragmentManager();
        initUI();
        initReceiver();
        showFragment(0);
    }

    private void initUI() {
        tv_r = (TextView) findViewById(R.id.tv_r);
        tv_m = (TextView) findViewById(R.id.tv_m);
        tv_m.setOnClickListener(this);
        tv_r.setOnClickListener(this);
    }

    private void initReceiver () {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter("com.kgv.cookbook.LOGIN");
        receiver = new LoginBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
    }

    public void showLDG(){
        showLoginDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_m:
                showFragment(1);
                break;
            case R.id.tv_r:
                showFragment(0);
                break;
        }
    }

    private void showFragment(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (recipesFragment != null) {
            transaction.hide(recipesFragment);
        }
        if (materialsFragment != null) {
            transaction.hide(materialsFragment);
        }
        if (position == 0) {
            if (recipesFragment == null) {
                recipesFragment = new InterestRecipesFragment();
                transaction.add(R.id.interestContent, recipesFragment);
            } else {
                transaction.show(recipesFragment);
            }
        } else {
            if (materialsFragment == null) {
                materialsFragment = new InterestMaterialsFragment();
                transaction.add(R.id.interestContent, materialsFragment);
            } else {
                transaction.show(materialsFragment);
            }
        }
        tv_r.setSelected(position == 0);
        tv_m.setSelected(position == 1);
        transaction.commit();
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(receiver);
    }
}
