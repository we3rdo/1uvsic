package com.kgv.cookbook.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.bean.MenuAndBlogShiPu;
import com.kgv.cookbook.bean.Nutrition;
import com.kgv.cookbook.bean.NutritionCount;
import com.kgv.cookbook.bean.PastWeekBean;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.ui.NutritionView;
import com.kgv.cookbook.ui.PastWeekView;
import com.kgv.cookbook.ui.compactcalendarview.CompactCalendarView;
import com.kgv.cookbook.util.DateUtils;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/19 11:16
 * Email : 18627241899@163.com
 * Description : 主页-健康管理
 */
public class HealthManagerActivity extends BaseActivity implements View.OnClickListener, PastWeekView.PastWeekViewListener {

    private TextView tv_date, tv_today, tv_yesterday, tv_past, tv_future;
    private TextView tv_month, tv_year;
    private ImageView iv_triangle0, iv_triangle1;
    private boolean isShowCalendaring = false;
    private LinearLayout calendar;
    private boolean firstCalendar = true;
    private SimpleDateFormat year = new SimpleDateFormat("yyyy");
    private SimpleDateFormat month = new SimpleDateFormat("MM月");
    private Gson gson = new Gson();
    private ArrayList<Nutrition> todayBreakfastN = new ArrayList<>();
    private ArrayList<Nutrition> todayLunchN = new ArrayList<>();
    private ArrayList<Nutrition> todayDinnerN = new ArrayList<>();
    private ArrayList<Nutrition> yesterdayBreakfastN = new ArrayList<>();
    private ArrayList<Nutrition> yesterdayLunchN = new ArrayList<>();
    private ArrayList<Nutrition> yesterdayDinnerN = new ArrayList<>();
    private ArrayList<Nutrition> normalBreakfastN;
    private ArrayList<Nutrition> normalLunchN;
    private ArrayList<Nutrition> normalDinnerN;
    private NutritionView nutritionView;
    private boolean hasFamilySet;
    private TextView tv0, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9;
    private ImageView iv0, iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9;
    private final int TODAY = 0;
    private final int YESTERDAY = 1;
    private final int PAST = 2;
    private final int NORMAL = 3;
    private int currentType = TODAY;
    private String yesterday;
    private LinearLayout ll_append;
    private String today;
    private LinearLayout llToday;
    private LocalBroadcastManager manager;
    private BroadcastReceiver needGoRecipeReceiver;
    private boolean hasYesterdayData = false;
    private boolean firstPastWeek = true;
    private Runnable loadRunnable = new Runnable() {
        @Override
        public void run() {
            getBlogMorningData();
            getBlogNooningData();
            getBlogEveningData();
            getTodayCount();
        }
    };
    private PastWeekView pastWeekView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_health_manager;
    }

    @Override
    protected boolean hasBottomMenu() {
        return true;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        today = DateUtils.getToday();
        yesterday = DateUtils.getYesterday();
        initUI();
        initListener();
        registerReceiver();
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                myHandler.post(loadRunnable);
            }
        });
    }

    private void initUI() {
        iv_triangle0 = (ImageView) findViewById(R.id.iv_triangle0);
        iv_triangle1 = (ImageView) findViewById(R.id.iv_triangle1);
        tv0 = (TextView) findViewById(R.id.tv0);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
        tv8 = (TextView) findViewById(R.id.tv8);
        tv9 = (TextView) findViewById(R.id.tv9);
        iv0 = (ImageView) findViewById(R.id.iv0);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
        iv5 = (ImageView) findViewById(R.id.iv5);
        iv6 = (ImageView) findViewById(R.id.iv6);
        iv7 = (ImageView) findViewById(R.id.iv7);
        iv8 = (ImageView) findViewById(R.id.iv8);
        iv9 = (ImageView) findViewById(R.id.iv9);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setText(today);
        tv_today = (TextView) findViewById(R.id.tv_today);
        tv_yesterday = (TextView) findViewById(R.id.tv_yesterday);
        tv_past = (TextView) findViewById(R.id.tv_past);
        tv_future = (TextView) findViewById(R.id.tv_future);
        ll_append = (LinearLayout) findViewById(R.id.ll_append);
        nutritionView = (NutritionView) findViewById(R.id.nutritionView);
        llToday = (LinearLayout) findViewById(R.id.llToday);
        llToday.setBackgroundColor(AppUtils.getColor(R.color.white));
        tv_today.setSelected(true);
        tv_date.setSelected(true);
    }

    private void initListener() {
        tv_date.setOnClickListener(this);
        tv_today.setOnClickListener(this);
        tv_yesterday.setOnClickListener(this);
        tv_past.setOnClickListener(this);
        tv_future.setOnClickListener(this);
        findViewById(R.id.ll_add_material).setOnClickListener(this);
        findViewById(R.id.ll_add_recipe).setOnClickListener(this);
        findViewById(R.id.parent).setOnClickListener(this);
    }

    private void registerReceiver() {
        manager = LocalBroadcastManager.getInstance(this);
        IntentFilter needGoRecipe = new IntentFilter();
        needGoRecipe.addAction("com.kgv.cookbook.JUMP_RECIPE");
        needGoRecipeReceiver = new NeedJumpRecipeReceiver();
        manager.registerReceiver(needGoRecipeReceiver, needGoRecipe);
    }

    private void initCalendar() {
        ViewStub stub = (ViewStub) findViewById(R.id.vs_calendar);
        calendar = (LinearLayout) stub.inflate();
        CompactCalendarView compactCalendarView = (CompactCalendarView) calendar.findViewById(R.id.compactCalendarView);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                String date = DateUtils.dateFormatter.format(dateClicked);
                showNormal(date);
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
                changeColor(0);
                showToday();
                currentType = TODAY;
                break;
            case R.id.tv_yesterday:
                changeColor(1);
                showYesterday();
                currentType = YESTERDAY;
                break;
            case R.id.tv_past:
                changeColor(2);
                showPast();
                currentType = PAST;
                break;
            case R.id.tv_future:
                if (! mUser.isExist()) {
                    showLoginDialog();
                    return;
                }
                jumpActivity(AWeekActivity.class, false);
                break;
            case R.id.ll_add_material:
                jumpActivity(AllMaterialActivity.class, false);
                break;
            case R.id.ll_add_recipe:
                jumpActivity(FoodWorldActivity.class, false);
                break;
            case R.id.parent:
                break;
        }
    }

    private void changeColor(int position) {
        llToday.setBackgroundColor(AppUtils.getColor(R.color.coffee));
        tv_today.setSelected(false);
        tv_date.setSelected(false);
        tv_yesterday.setSelected(false);
        tv_past.setSelected(false);
        iv_triangle0.setImageResource(R.drawable.triangle0);
        iv_triangle1.setImageResource(R.drawable.triangle1);
        switch (position) {
            case 0:
                if (currentType != TODAY) {
                    llToday.setBackgroundColor(AppUtils.getColor(R.color.white));
                    tv_today.setSelected(true);
                    tv_date.setSelected(true);
                    iv_triangle0.setImageResource(R.drawable.triangle2);
                    iv_triangle1.setImageResource(R.drawable.triangle3);
                }
                break;
            case 1:
                if (currentType != YESTERDAY) {
                    tv_yesterday.setSelected(true);
                }
                break;
            case 2:
                if (currentType != PAST) {
                    tv_past.setSelected(true);
                }
                break;
        }
    }


    private void showNormal(String date) {
        changeColor(-1);
        currentType = NORMAL;
        tv_date.setText(date);
        ll_append.setVisibility(View.GONE);
        if (!firstPastWeek) {
            pastWeekView.setVisibility(View.GONE);
        }
        nutritionView.setVisibility(View.VISIBLE);
        nutritionView.clearData();
        nutritionView.setTitle(date+"日营养信息");
        calendar.setVisibility(View.INVISIBLE);
        isShowCalendaring = false;

        getNormalData(date);
    }

    private void showPast() {
        if (currentType != PAST) {
            ll_append.setVisibility(View.GONE);
            nutritionView.setVisibility(View.GONE);
            if (firstPastWeek) {
                ViewStub vs = (ViewStub) findViewById(R.id.vs_past_week);
                pastWeekView = (PastWeekView) vs.inflate();
                pastWeekView.setListener(this);
                getPastWeekData();
                firstPastWeek = false;
            } else {
                pastWeekView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void showYesterday() {
        ll_append.setVisibility(View.GONE);
        if (!firstPastWeek) {
            pastWeekView.setVisibility(View.GONE);
        }
        nutritionView.setVisibility(View.VISIBLE);
        nutritionView.clearData();
        nutritionView.setTitle("昨日营养信息");
        if (currentType != YESTERDAY) {
            if (!hasYesterdayData) {
                hasYesterdayData = true;
                getYesterdayMorning();
                getYesterdayNooning();
                getYesterdayEvening();
            } else {
                nutritionView.setDataSet(yesterdayBreakfastN, yesterdayLunchN, yesterdayDinnerN);
            }
        }
    }

    private void showToday() {
        ll_append.setVisibility(View.VISIBLE);
        if (!firstPastWeek) {
            pastWeekView.setVisibility(View.GONE);
        }
        nutritionView.setVisibility(View.VISIBLE);
        nutritionView.clearData();
        nutritionView.setTitle("今日营养信息");
        if (currentType != TODAY) {
            nutritionView.setDataSet(todayBreakfastN, todayLunchN, todayDinnerN);
        }
    }

    @Override
    protected void handleMsg0(Message msg) {
        String json = (String) msg.obj;
        if (!"null".equals(json)) {
            List<MenuAndBlogShiPu> todayMorning = gson.fromJson(json, new TypeToken<List<MenuAndBlogShiPu>>() {
            }.getType());
            for (int i = 0; i < todayMorning.size(); i++) {
                ArrayList<String> d = new ArrayList<>();
                d.add(todayMorning.get(i).getName());
                List<MenuAndBlogShiPu.NutritionEntity> ns = todayMorning.get(i).getNutrition();
                for (int j = 0; j < ns.size(); j++) {
                    d.add(Double.toString(ns.get(j).getVal()));
                }
                Nutrition nutrition = new Nutrition(todayMorning.get(i).getCookbook_id(), d);
                todayBreakfastN.add(nutrition);
            }
            nutritionView.initBreakfastData(todayBreakfastN);
        }
    }

    @Override
    protected void handleMsg1(Message msg) {
        String json = (String) msg.obj;
        if (!"null".equals(json)) {
            List<MenuAndBlogShiPu> todayNooning = gson.fromJson(json, new TypeToken<List<MenuAndBlogShiPu>>() {
            }.getType());
            for (int i = 0; i < todayNooning.size(); i++) {
                ArrayList<String> d = new ArrayList<>();
                d.add(todayNooning.get(i).getName());
                List<MenuAndBlogShiPu.NutritionEntity> ns = todayNooning.get(i).getNutrition();
                for (int j = 0; j < ns.size(); j++) {
                    d.add(Double.toString(ns.get(j).getVal()));
                }
                Nutrition nutrition = new Nutrition(todayNooning.get(i).getCookbook_id(), d);
                todayLunchN.add(nutrition);
            }
            nutritionView.initLunchData(todayLunchN);
        }
    }

    @Override
    protected void handleMsg2(Message msg) {
        String json = (String) msg.obj;
        if (!"null".equals(json)) {
            List<MenuAndBlogShiPu> todayEvening = gson.fromJson(json, new TypeToken<List<MenuAndBlogShiPu>>() {
            }.getType());
            for (int i = 0; i < todayEvening.size(); i++) {
                ArrayList<String> d = new ArrayList<>();
                d.add(todayEvening.get(i).getName());
                List<MenuAndBlogShiPu.NutritionEntity> ns = todayEvening.get(i).getNutrition();
                for (int j = 0; j < ns.size(); j++) {
                    d.add(Double.toString(ns.get(j).getVal()));
                }
                Nutrition nutrition = new Nutrition(todayEvening.get(i).getCookbook_id(), d);
                todayDinnerN.add(nutrition);
            }
            nutritionView.initDinnerData(todayDinnerN);
        }
    }

    @Override
    protected void handleMsg3(Message msg) {
        String response = (String) msg.obj;
        hasFamilySet = !"null".equals(response);
        if (!hasFamilySet) {
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
                            HealthManagerActivity.this.jumpActivity(FamilySettingActivity.class,false);
                        }
                    })
                    .show();
        }
    }

    @Override
    protected void handleMsg4(Message msg) {
        String json = (String) msg.obj;
        if (!"null".equals(json)) {
            List<MenuAndBlogShiPu> yesterdayMorning = gson.fromJson(json, new TypeToken<List<MenuAndBlogShiPu>>() {
            }.getType());
            for (int i = 0; i < yesterdayMorning.size(); i++) {
                ArrayList<String> d = new ArrayList<>();
                d.add(yesterdayMorning.get(i).getName());
                List<MenuAndBlogShiPu.NutritionEntity> ns = yesterdayMorning.get(i).getNutrition();
                for (int j = 0; j < ns.size(); j++) {
                    d.add(Double.toString(ns.get(j).getVal()));
                }
                Nutrition nutrition = new Nutrition(yesterdayMorning.get(i).getCookbook_id(), d);
                yesterdayBreakfastN.add(nutrition);
            }
            nutritionView.setBreakfastData(yesterdayBreakfastN);
        }
    }

    @Override
    protected void handleMsg5(Message msg) {
        String json = (String) msg.obj;
        if (!"null".equals(json)) {
            List<MenuAndBlogShiPu> yesterdayNooning = gson.fromJson(json, new TypeToken<List<MenuAndBlogShiPu>>() {
            }.getType());
            for (int i = 0; i < yesterdayNooning.size(); i++) {
                ArrayList<String> d = new ArrayList<>();
                d.add(yesterdayNooning.get(i).getName());
                List<MenuAndBlogShiPu.NutritionEntity> ns = yesterdayNooning.get(i).getNutrition();
                for (int j = 0; j < ns.size(); j++) {
                    d.add(Double.toString(ns.get(j).getVal()));
                }
                Nutrition nutrition = new Nutrition(yesterdayNooning.get(i).getCookbook_id(), d);
                yesterdayLunchN.add(nutrition);
            }
            nutritionView.setLunchData(yesterdayLunchN);
        }
    }

    @Override
    protected void handleMsg6(Message msg) {
        String json = (String) msg.obj;
        if (!"null".equals(json)) {
            List<MenuAndBlogShiPu> yesterdayEvening = gson.fromJson(json, new TypeToken<List<MenuAndBlogShiPu>>() {
            }.getType());
            for (int i = 0; i < yesterdayEvening.size(); i++) {
                ArrayList<String> d = new ArrayList<>();
                d.add(yesterdayEvening.get(i).getName());
                List<MenuAndBlogShiPu.NutritionEntity> ns = yesterdayEvening.get(i).getNutrition();
                for (int j = 0; j < ns.size(); j++) {
                    d.add(Double.toString(ns.get(j).getVal()));
                }
                Nutrition nutrition = new Nutrition(yesterdayEvening.get(i).getCookbook_id(), d);
                yesterdayDinnerN.add(nutrition);
            }
            nutritionView.setDinnerData(yesterdayDinnerN);
        }
    }

    @Override
    protected void handleMsg7(Message msg) {
        List<PastWeekBean> pastWeekBeans = (List<PastWeekBean>) msg.obj;
        pastWeekView.setDataSet(pastWeekBeans);
    }

    @Override
    protected void handleMsg8(Message msg) {
        NutritionCount bean = (NutritionCount) msg.obj;
        NutritionCount.NutritionEntity nutrition = bean.getNutrition();
        if (nutrition != null && nutrition.getRe_liang() != null) {
            tv0.setText(Double.toString(nutrition.getRe_liang().getVal()));
            tv1.setText(Double.toString(nutrition.getTan_shui().getVal()));
            tv2.setText(Double.toString(nutrition.getZhi_fang().getVal()));
            tv3.setText(Double.toString(nutrition.getDan_bai().getVal()));
            tv4.setText(Double.toString(nutrition.getDan_gu().getVal()));
            tv5.setText(Double.toString(nutrition.getMei().getVal()));
            tv6.setText(Double.toString(nutrition.getGai().getVal()));
            tv7.setText(Double.toString(nutrition.getTie().getVal()));
            tv8.setText(Double.toString(nutrition.getXin().getVal()));
            tv9.setText(Double.toString(nutrition.getXi().getVal()));
            iv0.setImageResource(getStatusImg(nutrition.getRe_liang().getStatus()));
            iv1.setImageResource(getStatusImg(nutrition.getTan_shui().getStatus()));
            iv2.setImageResource(getStatusImg(nutrition.getZhi_fang().getStatus()));
            iv3.setImageResource(getStatusImg(nutrition.getDan_bai().getStatus()));
            iv4.setImageResource(getStatusImg(nutrition.getDan_gu().getStatus()));
            iv5.setImageResource(getStatusImg(nutrition.getMei().getStatus()));
            iv6.setImageResource(getStatusImg(nutrition.getGai().getStatus()));
            iv7.setImageResource(getStatusImg(nutrition.getTie().getStatus()));
            iv8.setImageResource(getStatusImg(nutrition.getXin().getStatus()));
            iv9.setImageResource(getStatusImg(nutrition.getXi().getStatus()));
        }
    }

    @Override
    protected void handleMsg9(Message msg) {
        String json = (String) msg.obj;
        if (!"null".equals(json)) {
            List<MenuAndBlogShiPu> normalDayMorning = gson.fromJson(json, new TypeToken<List<MenuAndBlogShiPu>>() {
            }.getType());
            normalBreakfastN = new ArrayList<>();
            for (int i = 0; i < normalDayMorning.size(); i++) {
                ArrayList<String> d = new ArrayList<>();
                d.add(normalDayMorning.get(i).getName());
                List<MenuAndBlogShiPu.NutritionEntity> ns = normalDayMorning.get(i).getNutrition();
                for (int j = 0; j < ns.size(); j++) {
                    d.add(Double.toString(ns.get(j).getVal()));
                }
                Nutrition nutrition = new Nutrition(normalDayMorning.get(i).getCookbook_id(), d);
                normalBreakfastN.add(nutrition);
            }
            nutritionView.setBreakfastData(normalBreakfastN);
        }
    }

    @Override
    protected void handleMsg10(Message msg) {
        String json = (String) msg.obj;
        if (!"null".equals(json)) {
            List<MenuAndBlogShiPu> normalDayNooning = gson.fromJson(json, new TypeToken<List<MenuAndBlogShiPu>>() {
            }.getType());
            normalLunchN = new ArrayList<>();
            for (int i = 0; i < normalDayNooning.size(); i++) {
                ArrayList<String> d = new ArrayList<>();
                d.add(normalDayNooning.get(i).getName());
                List<MenuAndBlogShiPu.NutritionEntity> ns = normalDayNooning.get(i).getNutrition();
                for (int j = 0; j < ns.size(); j++) {
                    d.add(Double.toString(ns.get(j).getVal()));
                }
                Nutrition nutrition = new Nutrition(normalDayNooning.get(i).getCookbook_id(), d);
                normalLunchN.add(nutrition);
            }
            nutritionView.setLunchData(normalLunchN);
        }
    }

    @Override
    protected void handleMsg11(Message msg) {
        String json = (String) msg.obj;
        if (!"null".equals(json)) {
            List<MenuAndBlogShiPu> normalDayEvenning = gson.fromJson(json, new TypeToken<List<MenuAndBlogShiPu>>() {
            }.getType());
            normalDinnerN = new ArrayList<>();
            for (int i = 0; i < normalDayEvenning.size(); i++) {
                ArrayList<String> d = new ArrayList<>();
                d.add(normalDayEvenning.get(i).getName());
                List<MenuAndBlogShiPu.NutritionEntity> ns = normalDayEvenning.get(i).getNutrition();
                for (int j = 0; j < ns.size(); j++) {
                    d.add(Double.toString(ns.get(j).getVal()));
                }
                Nutrition nutrition = new Nutrition(normalDayEvenning.get(i).getCookbook_id(), d);
                normalDinnerN.add(nutrition);
            }
            nutritionView.setDinnerData(normalDinnerN);
        }
    }

    private int getStatusImg(String str) {
        if (">".equals(str)) {
            return R.drawable.health_nutrition_up;
        } else if ("<".equals(str)) {
            return R.drawable.health_nutrition_down;
        } else {
            return R.drawable.health_nutrition_normal;
        }
    }


    private void checkHasFamilySet() {
        mHttpUtils.doGet(Urls.FAMILY_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword(), new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(3, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private void getBlogMorningData() {
        mHttpUtils.doGet(Urls.BLOG_MORNING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/morning_blog/create_time/", new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(0, str).sendToTarget();

            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private void getBlogNooningData() {
        mHttpUtils.doGet(Urls.BLOG_NOONING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/nooning_blog/create_time/", new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(1, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private void getBlogEveningData() {
        mHttpUtils.doGet(Urls.BLOG_EVENING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/evening_blog/create_time/", new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(2, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private void getTodayCount() {
        mHttpUtils.doGet(Urls.DAY_NUTRITION_COUNT + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/create_time/" + DateUtils.getToday(), new HttpResponse<NutritionCount>(NutritionCount.class) {
            @Override
            public void onSuccess(NutritionCount bean) {
                myHandler.obtainMessage(8, bean).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private void getYesterdayMorning() {
        mHttpUtils.doGet(Urls.BLOG_MORNING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/morning_blog/create_time/" + yesterday, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(4, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    private void getYesterdayNooning() {
        mHttpUtils.doGet(Urls.BLOG_NOONING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/nooning_blog/create_time/" + yesterday, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(5, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private void getYesterdayEvening() {
        mHttpUtils.doGet(Urls.BLOG_EVENING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/evening_blog/create_time/" + yesterday, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(6, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private void getPastWeekData() {
        mHttpUtils.doGet(Urls.BLOG_PAST_WEEK_NUTRITION_DATA+ "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/create_time/" + today, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                List<PastWeekBean> pastWeekBeans = gson.fromJson(str, new TypeToken<List<PastWeekBean>>() {
                }.getType());
                myHandler.obtainMessage(7, pastWeekBeans).sendToTarget();
            }

            @Override
            public void onError(String msg) {
                LogUtils.v("manager", "past week Error : " + msg);
            }
        });
    }

    private void getNormalData(String date) {
        getNormalDayBreakfast(date);
        getNormalDayLunch(date);
        getNormalDayDinner(date);
    }

    private void getNormalDayBreakfast(String date) {
        mHttpUtils.doGet(Urls.BLOG_MORNING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/morning_blog/create_time/" + date, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(9, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {
                LogUtils.v("onError",msg);
            }
        });
    }

    private void getNormalDayLunch(String date) {
        mHttpUtils.doGet(Urls.BLOG_NOONING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/nooning_blog/create_time/" + date, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(10, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {
                LogUtils.v("onError",msg);
            }
        });
    }

    private void getNormalDayDinner(String date) {
        mHttpUtils.doGet(Urls.BLOG_EVENING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/evening_blog/create_time/" + date, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                myHandler.obtainMessage(11, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {
                LogUtils.v("onError",msg);
            }
        });
    }

    @Override
    public void onRecipeNameClick(String id) {
        if (!"-1".equals(id)) {
            Intent i = new Intent(this, RecipeActivity.class);
            i.putExtra(IntentKeys.SHIPU_ID, id);
            jumpActivity(i, false);
        }
    }

    @Override
    public void onNutritionClick(String date, String unit) {
        Intent intent = new Intent(this, SuggestActivity.class);
        intent.putExtra(IntentKeys.DATE, date);
        intent.putExtra(IntentKeys.UNIT, unit);
        jumpActivity(intent, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterReceiver(needGoRecipeReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkHasFamilySet();
    }

    private class NeedJumpRecipeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String spid = intent.getStringExtra(IntentKeys.SHIPU_ID);
            Intent i = new Intent(HealthManagerActivity.this, RecipeActivity.class);
            i.putExtra(IntentKeys.SHIPU_ID, spid);
            HealthManagerActivity.this.jumpActivity(i, false);
        }
    }
}
