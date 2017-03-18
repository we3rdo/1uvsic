package com.kgv.cookbook.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.fragment.health.NormalDayFragment;
import com.kgv.cookbook.fragment.health.PastWeekFragment;
import com.kgv.cookbook.fragment.health.TodayFragment;
import com.kgv.cookbook.fragment.health.YesterdayFragment;
import com.kgv.cookbook.ui.compactcalendarview.CompactCalendarView;
import com.kgv.cookbook.util.DateUtils;
import com.kgv.cookbook.util.HttpResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 陈可轩 on 2017/3/10 17:08
 * Email : 18627241899@163.com
 * Description :
 */
public class HealthActivity extends BaseActivity implements View.OnClickListener {

    /*  标题  */
    private LinearLayout llToday;
    private TextView tv_today, tv_date, tv_yesterday, tv_past, tv_future;
    private ImageView iv_triangle0, iv_triangle1;
    private String todayString;
    /*  日历  */
    private boolean firstCalendar = true;
    private boolean isShowCalendaring = false;
    private LinearLayout calendar;
    private TextView tv_month, tv_year;
    /*  receiver */
    private LocalBroadcastManager manager;
    private BroadcastReceiver needGoRecipeReceiver;
    /*  fragments */
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private int currentFragment;
    private TodayFragment todayFragment;
    private YesterdayFragment yesterdayFragment;
    private PastWeekFragment pastWeekFragment;
    private NormalDayFragment normalDayFragment;
    /*  normal day*/
    public String normalDayString;
    private LoginBroadcastReceiver loginReceiver;

    @Override
    protected boolean hasBottomMenu() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_health;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        todayString = DateUtils.getToday();
        initUI();
        initListener();
        registerReceiver();
        showFragment(0);
    }

    private void initUI() {
        /** 标题 */
        llToday = (LinearLayout) findViewById(R.id.llToday);
        tv_today = (TextView) findViewById(R.id.tv_today);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_yesterday = (TextView) findViewById(R.id.tv_yesterday);
        tv_past = (TextView) findViewById(R.id.tv_past);
        tv_future = (TextView) findViewById(R.id.tv_future);
        iv_triangle0 = (ImageView) findViewById(R.id.iv_triangle0);
        iv_triangle1 = (ImageView) findViewById(R.id.iv_triangle1);
        tv_date.setText(todayString);
        /** 标题结束 */
    }

    private void initListener() {
        tv_today.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        tv_yesterday.setOnClickListener(this);
        tv_past.setOnClickListener(this);
        tv_future.setOnClickListener(this);
    }

    private void registerReceiver() {
        manager = LocalBroadcastManager.getInstance(this);
        IntentFilter needGoRecipe = new IntentFilter();
        needGoRecipe.addAction("com.kgv.cookbook.JUMP_RECIPE");
        needGoRecipeReceiver = new NeedJumpRecipeReceiver();
        manager.registerReceiver(needGoRecipeReceiver, needGoRecipe);

        IntentFilter intentFilter = new IntentFilter("com.kgv.cookbook.LOGIN");
        loginReceiver = new LoginBroadcastReceiver();
        manager.registerReceiver(loginReceiver, intentFilter);
    }

    private void initCalendar() {
        final SimpleDateFormat year = new SimpleDateFormat("yyyy");
        final SimpleDateFormat month = new SimpleDateFormat("MM月");
        ViewStub stub = (ViewStub) findViewById(R.id.vs_calendar);
        calendar = (LinearLayout) stub.inflate();
        CompactCalendarView compactCalendarView = (CompactCalendarView) calendar.findViewById(R.id.compactCalendarView);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                calendar.setVisibility(View.INVISIBLE);
                isShowCalendaring = false;
                normalDayString = DateUtils.dateFormatter.format(dateClicked);
                if (normalDayFragment != null){
                    normalDayFragment.replaceData(normalDayString);
                }
                showFragment(3);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                tv_year.setText(year.format(firstDayOfNewMonth));
                tv_month.setText(month.format(firstDayOfNewMonth));
            }
        });
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setDayColumnNames(new String[]{"一", "二", "三", "四", "五", "六", "日"});
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        tv_month = (TextView) calendar.findViewById(R.id.tv_month);
        tv_year = (TextView) calendar.findViewById(R.id.tv_year);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_date:
                if (firstCalendar) {
                    initCalendar();
                    firstCalendar = false;
                }
                calendar.setVisibility(isShowCalendaring ? View.INVISIBLE : View.VISIBLE);
                isShowCalendaring = !isShowCalendaring;
                break;
            case R.id.tv_today:
                showFragment(0);
                break;
            case R.id.tv_yesterday:
                showFragment(1);
                break;
            case R.id.tv_past:
                showFragment(2);
                break;
            case R.id.tv_future:
                if (! mUser.isExist()) {
                    showLoginDialog();
                    return;
                }
                jumpActivity(AWeekActivity.class, false);
                break;
        }
    }

    private void showTitleColor(int position) {
        llToday.setBackgroundColor(AppUtils.getColor(R.color.coffee));
        tv_today.setSelected(false);
        tv_date.setSelected(false);
        tv_yesterday.setSelected(false);
        tv_past.setSelected(false);
        iv_triangle0.setImageResource(R.drawable.triangle0);
        iv_triangle1.setImageResource(R.drawable.triangle1);
        switch (position) {
            case 0:
                if (currentFragment != 0) {
                    llToday.setBackgroundColor(AppUtils.getColor(R.color.white));
                    tv_today.setSelected(true);
                    tv_date.setSelected(true);
                    iv_triangle0.setImageResource(R.drawable.triangle2);
                    iv_triangle1.setImageResource(R.drawable.triangle3);
                }
                break;
            case 1:
                if (currentFragment != 1) {
                    tv_yesterday.setSelected(true);
                }
                break;
            case 2:
                if (currentFragment != 2) {
                    tv_past.setSelected(true);
                }
                break;
        }
    }

    @Override
    protected void handleMsg0(Message msg) {
        String response = (String) msg.obj;
        if ("null".equals(response)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("提示")
                    .setMessage("尚未设置家庭成员，是否立即设置？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            HealthActivity.this.jumpActivity(FamilySettingActivity.class, false);
                        }
                    })
                    .show();
        }
    }

    private void checkHasFamilySetting() {
        mHttpUtils.doGet(Urls.FAMILY_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword(), new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(0, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private void showFragment(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        showTitleColor(index);
        switch (index) {
            case 0:
                currentFragment = 0;
                if (todayFragment == null) {
                    todayFragment = new TodayFragment();
                    transaction.add(R.id.content, todayFragment);
                } else {
                    transaction.show(todayFragment);
                }
                break;
            case 1:
                currentFragment = 1;
                if (yesterdayFragment == null) {
                    yesterdayFragment = new YesterdayFragment();
                    transaction.add(R.id.content, yesterdayFragment);
                } else {
                    transaction.show(yesterdayFragment);
                }
                break;
            case 2:
                currentFragment = 2;
                if (pastWeekFragment == null) {
                    pastWeekFragment = new PastWeekFragment();
                    transaction.add(R.id.content, pastWeekFragment);
                } else {
                    transaction.show(pastWeekFragment);
                }
                break;
            case 3:
                currentFragment = 3;
                if (normalDayFragment == null) {
                    normalDayFragment = new NormalDayFragment();
                    transaction.add(R.id.content, normalDayFragment);
                } else {
                    transaction.show(normalDayFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (todayFragment != null) {
            transaction.hide(todayFragment);
        }
        if (yesterdayFragment != null) {
            transaction.hide(yesterdayFragment);
        }
        if (pastWeekFragment != null) {
            transaction.hide(pastWeekFragment);
        }
        if (normalDayFragment != null) {
            transaction.hide(normalDayFragment);
        }
    }

    private class NeedJumpRecipeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String spid = intent.getStringExtra(IntentKeys.SHIPU_ID);
            Intent i = new Intent(HealthActivity.this, RecipeActivity.class);
            i.putExtra(IntentKeys.SHIPU_ID, spid);
            HealthActivity.this.jumpActivity(i, false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkHasFamilySetting();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterReceiver(needGoRecipeReceiver);
        manager.unregisterReceiver(loginReceiver);
    }
}
