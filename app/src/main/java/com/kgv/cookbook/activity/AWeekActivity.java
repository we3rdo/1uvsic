package com.kgv.cookbook.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kgv.cookbook.R;
import com.kgv.cookbook.adapter.WeekAdapter;
import com.kgv.cookbook.adapter.WeekLeftAdapter;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.bean.WeekBean;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/14 13:12
 * Email : 18627241899@163.com
 * Description : 主页-食谱->一周菜谱
 */
public class AWeekActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_breakfast, tv_lunch, tv_dinner;
    private GridView gv_breakfast, gv_lunch, gv_dinner;
    private WeekAdapter breakfastAdapter, lunchAdapter, dinnerAdapter;
    private ImageView iv_breakfast, iv_lunch, iv_dinner;
    private Gson gson = new Gson();
    private List<WeekBean> beans;
    private GridView gv_sc;
    private WeekLeftAdapter adapter;
    private LocalBroadcastManager manager;
    private MyReceiver receiver;


    @Override
    protected boolean hasBottomMenu () {
        return true;
    }

    @Override
    protected int getContentViewId () {
        return R.layout.activity_a_week;
    }

    @Override
    protected void initialization (Bundle savedInstanceState) {
        initUI();
        initListener();
        initReceiver();
        getWeekListData();
    }

    private void initUI () {
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_6 = (TextView) findViewById(R.id.tv_6);
        tv_7 = (TextView) findViewById(R.id.tv_7);
        gv_breakfast = (GridView) findViewById(R.id.gv_breakfast);
        gv_lunch = (GridView) findViewById(R.id.gv_lunch);
        gv_dinner = (GridView) findViewById(R.id.gv_dinner);
        tv_breakfast = (TextView) findViewById(R.id.tv_breakfast);
        tv_lunch = (TextView) findViewById(R.id.tv_lunch);
        tv_dinner = (TextView) findViewById(R.id.tv_dinner);
        iv_breakfast = (ImageView) findViewById(R.id.iv_breakfast);
        iv_lunch = (ImageView) findViewById(R.id.iv_lunch);
        iv_dinner = (ImageView) findViewById(R.id.iv_dinner);
        gv_sc = (GridView) findViewById(R.id.gv_sc);
    }

    private void initListener () {
        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
        tv_5.setOnClickListener(this);
        tv_6.setOnClickListener(this);
        tv_7.setOnClickListener(this);
        gv_breakfast.setOnItemClickListener(this);
        gv_lunch.setOnItemClickListener(this);
        gv_dinner.setOnItemClickListener(this);
        findViewById(R.id.tv_go_setting).setOnClickListener(this);
        findViewById(R.id.ll_breakfast_more).setOnClickListener(this);
        findViewById(R.id.ll_lunch_more).setOnClickListener(this);
        findViewById(R.id.ll_dinner_more).setOnClickListener(this);
        findViewById(R.id.tv_left).setOnClickListener(this);
        findViewById(R.id.tv_right).setOnClickListener(this);
    }

    private void initReceiver () {
        manager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.kgv.cookbook.FAMILY_CHANGE");
        receiver = new MyReceiver();
        manager.registerReceiver(receiver, intentFilter);
    }

    private void getWeekListData () {
        mHttpUtils.doGet(Urls.WEEK_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword(), new HttpResponse<String>(String.class) {
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
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                startEditScActivity();
                break;
            case R.id.tv_right:
                if (adapter != null) {
                    adapter.clearDatas();
                }
                getWeekListData();
                break;
            case R.id.tv_1:
                show(0);
                break;
            case R.id.tv_2:
                show(1);
                break;
            case R.id.tv_3:
                show(2);
                break;
            case R.id.tv_4:
                show(3);
                break;
            case R.id.tv_5:
                show(4);
                break;
            case R.id.tv_6:
                show(5);
                break;
            case R.id.tv_7:
                show(6);
                break;
            case R.id.tv_go_setting:
                Intent intent = new Intent(this, FamilySettingActivity.class);
                intent.putExtra(IntentKeys.JUMP_SET_FAMILY, true);
                jumpActivity(intent, false);
                break;
            case R.id.ll_breakfast_more:
                if (breakfastAdapter != null) {
                    breakfastAdapter.control(! breakfastAdapter.isOpening());
                    tv_breakfast.setText(breakfastAdapter.isOpening() ? "收起" : "更多");
                    iv_breakfast.setImageResource(breakfastAdapter.isOpening() ?
                            R.drawable.all_m_opened : R.drawable.all_m_closed);
                }
                break;
            case R.id.ll_lunch_more:
                if (lunchAdapter != null) {
                    lunchAdapter.control(! lunchAdapter.isOpening());
                    tv_lunch.setText(lunchAdapter.isOpening() ? "收起" : "更多");
                    iv_lunch.setImageResource(lunchAdapter.isOpening() ?
                            R.drawable.all_m_opened : R.drawable.all_m_closed);
                }
                break;
            case R.id.ll_dinner_more:
                if (dinnerAdapter != null) {
                    dinnerAdapter.control(! dinnerAdapter.isOpening());
                    tv_dinner.setText(dinnerAdapter.isOpening() ? "收起" : "更多");
                    iv_dinner.setImageResource(dinnerAdapter.isOpening() ?
                            R.drawable.all_m_opened : R.drawable.all_m_closed);
                }
                break;
        }
    }

    private void startEditScActivity () {
        Intent intent = new Intent(this, AWeekBranchActivity.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (data != null) {
            List<String> ids = (List<String>) data.getSerializableExtra(IntentKeys.SC_IDS);
            List<String> names = (List<String>) data.getSerializableExtra(IntentKeys.SC_NAMES);
            adapter = new WeekLeftAdapter(names);
            gv_sc.setAdapter(adapter);
            String str = splitParams(ids);
            getWeekListDataFromEdit(str);
        }
    }

    private void getWeekListDataFromEdit (String str) {
        String url = Urls.WEEK_LIST_EDIT + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/shicai/" + str;
        LogUtils.v(url);
        mHttpUtils.doGet(url, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                myHandler.obtainMessage(0, str).sendToTarget();
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    @NonNull
    private String splitParams (List<String> ids) {
        String str = "";
        for (int i = 0; i < ids.size(); i++) {
            if (i != ids.size() - 1) {
                str += ids.get(i) + ",";
            } else {
                str += ids.get(i);
            }
        }
        return str;
    }

    private void show (int position) {
        selectItem(position);
        if (beans != null) {
            WeekBean weekBean = beans.get(position);
            List<WeekBean.RecipeEntity> breakfast = weekBean.getMorning();
            List<WeekBean.RecipeEntity> lunch = weekBean.getNooning();
            List<WeekBean.RecipeEntity> dinner = weekBean.getEvening();
            breakfastAdapter = new WeekAdapter(breakfast);
            lunchAdapter = new WeekAdapter(lunch);
            dinnerAdapter = new WeekAdapter(dinner);
            gv_breakfast.setAdapter(breakfastAdapter);
            gv_lunch.setAdapter(lunchAdapter);
            gv_dinner.setAdapter(dinnerAdapter);
        }

    }

    private void selectItem (int position) {
        tv_1.setSelected(false);
        tv_2.setSelected(false);
        tv_3.setSelected(false);
        tv_4.setSelected(false);
        tv_5.setSelected(false);
        tv_6.setSelected(false);
        tv_7.setSelected(false);
        switch (position) {
            case 0:
                tv_1.setSelected(true);
                break;
            case 1:
                tv_2.setSelected(true);
                break;
            case 2:
                tv_3.setSelected(true);
                break;
            case 3:
                tv_4.setSelected(true);
                break;
            case 4:
                tv_5.setSelected(true);
                break;
            case 5:
                tv_6.setSelected(true);
                break;
            case 6:
                tv_7.setSelected(true);
                break;
        }
    }

    private void checkHasFamilySet () {
        mHttpUtils.doGet(Urls.FAMILY_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword(), new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                myHandler.obtainMessage(1, str).sendToTarget();
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    @Override
    protected void handleMsg0 (Message msg) {
        String response = (String) msg.obj;
        LogUtils.v(response);
        if (response.length() > 50) {
            beans = gson.fromJson(response, new TypeToken<List<WeekBean>>() {
            }.getType());
            setTitles();
            for (int i = 0; i < beans.size(); i++) {
                if (beans.get(i).getSelected() == 1) {
                    show(i);
                    return;
                }
            }
        } else {
            showNoFamilySettingDialog();
        }
    }

    @Override
    protected void handleMsg1 (Message msg) {
        String response = (String) msg.obj;
        if ("null".equals(response)) {
            showNoFamilySettingDialog();
        }
    }

    private void showNoFamilySettingDialog () {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示")
                .setMessage("尚未设置家庭成员，是否立即设置？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(AWeekActivity.this, FamilySettingActivity.class);
                        intent.putExtra(IntentKeys.JUMP_SET_FAMILY, true);
                        AWeekActivity.this.jumpActivity(intent, false);
                    }
                })
                .show();
    }

    private void setTitles () {
        tv_1.setText(change(beans.get(0).getDate()));
        tv_2.setText(change(beans.get(1).getDate()));
        tv_3.setText(change(beans.get(2).getDate()));
        tv_4.setText(change(beans.get(3).getDate()));
        tv_5.setText(change(beans.get(4).getDate()));
        tv_6.setText(change(beans.get(5).getDate()));
        tv_7.setText(change(beans.get(6).getDate()));
    }

    private String change (String day) {
        String str;
        if ("1".equals(day)) {
            str = "星期一";
        } else if ("2".equals(day)) {
            str = "星期二";
        } else if ("3".equals(day)) {
            str = "星期三";
        } else if ("4".equals(day)) {
            str = "星期四";
        } else if ("5".equals(day)) {
            str = "星期五";
        } else if ("6".equals(day)) {
            str = "星期六";
        } else {
            str = "星期日";
        }
        return str;
    }

    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        String idid = "";
        switch (parent.getId()) {
            case R.id.gv_breakfast:
                idid = breakfastAdapter.getItemIdByPosition(position);
                break;
            case R.id.gv_lunch:
                idid = lunchAdapter.getItemIdByPosition(position);
                break;
            case R.id.gv_dinner:
                idid = dinnerAdapter.getItemIdByPosition(position);
                break;
        }
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(IntentKeys.SHIPU_ID, idid);
        jumpActivity(intent, false);
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        manager.unregisterReceiver(receiver);
    }

    @Override
    protected void onResume () {
        super.onResume();
        checkHasFamilySet();
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive (Context context, Intent intent) {
            LogUtils.v("收到change广播");
            getWeekListData();
        }
    }
}
