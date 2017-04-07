package com.kgv.cookbook.fragment.health;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.AllMaterialActivity;
import com.kgv.cookbook.activity.FoodWorldActivity;
import com.kgv.cookbook.activity.RecipeActivity;
import com.kgv.cookbook.adapter.HealthAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.MenuAndBlogShiPu;
import com.kgv.cookbook.bean.Nutrition;
import com.kgv.cookbook.bean.NutritionCount;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.listener.IOnStringIdClickListener;
import com.kgv.cookbook.ui.ListView4ScrollView;
import com.kgv.cookbook.util.DateUtils;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2017/3/11 9:15
 * Email : 18627241899@163.com
 * Description :
 */
public class TodayFragment extends BaseFragment implements View.OnClickListener, IOnStringIdClickListener {

    private TextView tv_more_breakfast, tv_more_lunch, tv_more_dinner;
    private ImageView iv_more_breakfast, iv_more_lunch, iv_more_dinner;
    private ListView4ScrollView lv_breakfast, lv_lunch, lv_dinner;

    private ArrayList<Nutrition> breakfast = new ArrayList<>();
    private ArrayList<Nutrition> lunch = new ArrayList<>();
    private ArrayList<Nutrition> dinner = new ArrayList<>();
    private HealthAdapter breakfastAdapter;
    private HealthAdapter lunchAdapter;
    private HealthAdapter dinnerAdapter;
    private LocalBroadcastManager manager;

    private TextView tv0, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9;
    private ImageView iv0, iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9;
    private OnSubmit2BlogReceiver receiver;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_today;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        initUI(contentView);
        getToDayData();
    }

    @Override
    protected void initOnActivityCreated (Bundle savedInstanceState) {
        initReceiver();
    }

    private void initUI(View contentView) {
        tv_more_breakfast = (TextView) contentView.findViewById(R.id.tv_more_breakfast);
        tv_more_lunch = (TextView) contentView.findViewById(R.id.tv_more_lunch);
        tv_more_dinner = (TextView) contentView.findViewById(R.id.tv_more_dinner);
        iv_more_breakfast = (ImageView) contentView.findViewById(R.id.iv_more_breakfast);
        iv_more_lunch = (ImageView) contentView.findViewById(R.id.iv_more_lunch);
        iv_more_dinner = (ImageView) contentView.findViewById(R.id.iv_more_dinner);
        contentView.findViewById(R.id.ll_more_breakfast).setOnClickListener(this);
        contentView.findViewById(R.id.ll_more_lunch).setOnClickListener(this);
        contentView.findViewById(R.id.ll_more_dinner).setOnClickListener(this);
        lv_breakfast = (ListView4ScrollView) contentView.findViewById(R.id.lv_breakfast);
        lv_lunch = (ListView4ScrollView) contentView.findViewById(R.id.lv_lunch);
        lv_dinner = (ListView4ScrollView) contentView.findViewById(R.id.lv_dinner);
        tv0 = (TextView) contentView.findViewById(R.id.tv0);
        tv1 = (TextView) contentView.findViewById(R.id.tv1);
        tv2 = (TextView) contentView.findViewById(R.id.tv2);
        tv3 = (TextView) contentView.findViewById(R.id.tv3);
        tv4 = (TextView) contentView.findViewById(R.id.tv4);
        tv5 = (TextView) contentView.findViewById(R.id.tv5);
        tv6 = (TextView) contentView.findViewById(R.id.tv6);
        tv7 = (TextView) contentView.findViewById(R.id.tv7);
        tv8 = (TextView) contentView.findViewById(R.id.tv8);
        tv9 = (TextView) contentView.findViewById(R.id.tv9);
        iv0 = (ImageView) contentView.findViewById(R.id.iv0);
        iv1 = (ImageView) contentView.findViewById(R.id.iv1);
        iv2 = (ImageView) contentView.findViewById(R.id.iv2);
        iv3 = (ImageView) contentView.findViewById(R.id.iv3);
        iv4 = (ImageView) contentView.findViewById(R.id.iv4);
        iv5 = (ImageView) contentView.findViewById(R.id.iv5);
        iv6 = (ImageView) contentView.findViewById(R.id.iv6);
        iv7 = (ImageView) contentView.findViewById(R.id.iv7);
        iv8 = (ImageView) contentView.findViewById(R.id.iv8);
        iv9 = (ImageView) contentView.findViewById(R.id.iv9);
        contentView.findViewById(R.id.ll_add_material).setOnClickListener(this);
        contentView.findViewById(R.id.ll_add_recipe).setOnClickListener(this);
    }

    private void initReceiver () {
        manager = LocalBroadcastManager.getInstance(getActivity());
        receiver = new OnSubmit2BlogReceiver();
        IntentFilter intentFilter = new IntentFilter("com.kgv.cookbook.SUBMIT_TO_BLOG");
        manager.registerReceiver(receiver,intentFilter);
    }

    private void getToDayData() {
        getBlogMorningData();
        getBlogNooningData();
        getBlogEveningData();
        getTodayCount();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_more_breakfast:
                if (breakfastAdapter.isHasMore()) {
                    tv_more_breakfast.setText(breakfastAdapter.isOpening() ? "更多" : "收起");
                    iv_more_breakfast.setImageResource(breakfastAdapter.isOpening() ? R.drawable.all_m_closed : R.drawable.all_m_opened);
                    breakfastAdapter.control(!breakfastAdapter.isOpening());
                }
                break;
            case R.id.ll_more_lunch:
                if (lunchAdapter.isHasMore()) {
                    tv_more_lunch.setText(lunchAdapter.isOpening() ? "更多" : "收起");
                    iv_more_lunch.setImageResource(lunchAdapter.isOpening() ? R.drawable.all_m_closed : R.drawable.all_m_opened);
                    lunchAdapter.control(!lunchAdapter.isOpening());
                }
                break;
            case R.id.ll_more_dinner:
                if (dinnerAdapter.isHasMore()) {
                    tv_more_dinner.setText(dinnerAdapter.isOpening() ? "更多" : "收起");
                    iv_more_dinner.setImageResource(dinnerAdapter.isOpening() ? R.drawable.all_m_closed : R.drawable.all_m_opened);
                    dinnerAdapter.control(!dinnerAdapter.isOpening());
                }
                break;
            case R.id.ll_add_material:
                Intent intent1 = new Intent(baseActivity, AllMaterialActivity.class);
                intent1.putExtra(IntentKeys.JUMP_ALL_MATE,true);
                baseActivity.startActivity(intent1);
                baseActivity.overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
                break;
            case R.id.ll_add_recipe:
                Intent intent2 = new Intent(baseActivity, FoodWorldActivity.class);
                intent2.putExtra(IntentKeys.JUMP_FOOD_WORLD,true);
                baseActivity.startActivity(intent2);
                baseActivity.overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
                break;
        }
    }

    @Override
    public void onStringIdClick(String id) {
        Intent intent = new Intent(baseActivity, RecipeActivity.class);
        intent.putExtra(IntentKeys.SHIPU_ID, id);
        baseActivity.startActivity(intent);
        baseActivity.overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
    }

    private void getBlogMorningData() {
        String url = Urls.BLOG_MORNING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/morning_blog/create_time/";
        LogUtils.v("url","早url = " + url);
        httpUtils.doGet(url, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                handler.obtainMessage(0, str).sendToTarget();

            }

            @Override
            public void onError(String msg) {
                handler.obtainMessage(0, "null").sendToTarget();
            }
        });
    }

    private void getBlogNooningData() {
        httpUtils.doGet(Urls.BLOG_NOONING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/nooning_blog/create_time/", new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                handler.obtainMessage(1, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {
                handler.obtainMessage(1, "null").sendToTarget();
            }
        });
    }

    private void getBlogEveningData() {
        httpUtils.doGet(Urls.BLOG_EVENING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/evening_blog/create_time/", new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                handler.obtainMessage(2, str).sendToTarget();
            }

            @Override
            public void onError(String msg) {
                handler.obtainMessage(2, "null").sendToTarget();
            }
        });
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
                breakfast.add(nutrition);
            }
        }
        //添加至3条
        ArrayList<Nutrition> data = fillData(breakfast);
        breakfastAdapter = new HealthAdapter(data, this, false);
        lv_breakfast.setAdapter(breakfastAdapter);
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
                lunch.add(nutrition);
            }
        }
        ArrayList<Nutrition> data = fillData(lunch);
        lunchAdapter = new HealthAdapter(data, this, true);
        lv_lunch.setAdapter(lunchAdapter);
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
                dinner.add(nutrition);
            }
        }
        ArrayList<Nutrition> data = fillData(dinner);
        dinnerAdapter = new HealthAdapter(data, this, false);
        lv_dinner.setAdapter(dinnerAdapter);
    }

    @Override
    protected void handleMsg3(Message msg) {
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
    protected void handleMsg4(Message msg) {
        LogUtils.v("handleMsg4");
    }

    private void getTodayCount() {
        httpUtils.doGet(Urls.DAY_NUTRITION_COUNT + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/create_time/" + DateUtils.getToday(),
                new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                try{
                    NutritionCount bean = gson.fromJson(str,NutritionCount.class);
                    handler.obtainMessage(3, bean).sendToTarget();
                }catch (Exception e){
                    e.printStackTrace();
                    handler.obtainMessage(4).sendToTarget();
                }
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private ArrayList<Nutrition> fillData(ArrayList<Nutrition> list) {
        Nutrition nutrition = new Nutrition("empty", new ArrayList<String>());
        switch (list.size()) {
            case 0:
                list.add(nutrition);
                list.add(nutrition);
                list.add(nutrition);
                break;
            case 1:
                list.add(nutrition);
                list.add(nutrition);
                break;
            case 2:
                list.add(nutrition);
                break;
        }
        return list;
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

    @Override
    public void onDestroy () {
        super.onDestroy();
        manager.unregisterReceiver(receiver);
    }

    private class OnSubmit2BlogReceiver extends BroadcastReceiver{

        @Override
        public void onReceive (Context context, Intent intent) {
            breakfast.clear();
            lunch.clear();
            dinner.clear();
            getToDayData();
        }
    }
}
