package com.kgv.cookbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.GridView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.adapter.WeekMAdapter;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.fragment.MaterialsFragment;

import java.util.ArrayList;

/**
 * Created by 陈可轩 on 2017/2/20 10:10
 * Email : 18627241899@163.com
 * Description : 主页-食谱->一周菜谱.定制食材
 */
public class AWeekBranchActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    private GridView gv_selective;

    @Override
    protected boolean hasBottomMenu() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_a_week_branch;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        flag = 2;
        initUI();
    }

    private void initUI() {
        gv_selective = (GridView) findViewById(R.id.gv_selective);

        findViewById(R.id.tv_ok).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        MaterialsFragment fragment = new MaterialsFragment();
        transaction.add(R.id.content, fragment);
        transaction.commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                Intent intent = new Intent();
                intent.putExtra(IntentKeys.SC_IDS, ids);
                intent.putExtra(IntentKeys.SC_NAMES, names);
                setResult(0, intent);
                break;
        }
        finish();
        overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
    }

    public void onCheck(String id, String name) {
        if (names.contains(name)) {
            names.remove(name);
        } else {
            names.add(name);
        }
        if (ids.contains(id)) {
            ids.remove(id);
        } else {
            ids.add(id);
        }
        WeekMAdapter adapter = new WeekMAdapter(names);
        gv_selective.setAdapter(adapter);
    }
}
