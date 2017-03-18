package com.kgv.cookbook.fragment.health;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.RecipeActivity;
import com.kgv.cookbook.adapter.HealthAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.MenuAndBlogShiPu;
import com.kgv.cookbook.bean.Nutrition;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.listener.IOnStringIdClickListener;
import com.kgv.cookbook.ui.ListView4ScrollView;
import com.kgv.cookbook.util.DateUtils;
import com.kgv.cookbook.util.HttpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2017/3/11 14:48
 * Email : 18627241899@163.com
 * Description :
 */
public class YesterdayFragment extends BaseFragment implements IOnStringIdClickListener, View.OnClickListener {

    private String yesterday;
    private TextView tv_more_breakfast, tv_more_lunch, tv_more_dinner;
    private ImageView iv_more_breakfast, iv_more_lunch, iv_more_dinner;
    private ListView4ScrollView lv_breakfast, lv_lunch, lv_dinner;

    private ArrayList<Nutrition> breakfast = new ArrayList<>();
    private ArrayList<Nutrition> lunch = new ArrayList<>();
    private ArrayList<Nutrition> dinner = new ArrayList<>();
    private HealthAdapter breakfastAdapter;
    private HealthAdapter lunchAdapter;
    private HealthAdapter dinnerAdapter;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_yesterday;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        yesterday = DateUtils.getYesterday();
        initUI(contentView);
        getYesterdayData();
    }

    private void initUI(View contentView) {
        ((TextView) contentView.findViewById(R.id.tv_type)).setText("昨日营养数据");
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
    }

    private void getYesterdayData() {
        getYesterdayMorning();
        getYesterdayNooning();
        getYesterdayEvening();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        }
    }

    @Override
    public void onStringIdClick(String id) {
        Intent intent = new Intent(baseActivity, RecipeActivity.class);
        intent.putExtra(IntentKeys.SHIPU_ID, id);
        baseActivity.startActivity(intent);
        baseActivity.overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
    }

    private void getYesterdayMorning() {
        httpUtils.doGet(Urls.BLOG_MORNING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/morning_blog/create_time/" + yesterday, new HttpResponse<String>(String.class) {
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

    private void getYesterdayNooning() {
        httpUtils.doGet(Urls.BLOG_NOONING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/nooning_blog/create_time/" + yesterday, new HttpResponse<String>(String.class) {
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

    private void getYesterdayEvening() {
        httpUtils.doGet(Urls.BLOG_EVENING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/evening_blog/create_time/" + yesterday, new HttpResponse<String>(String.class) {
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
            //添加至3条
            ArrayList<Nutrition> data = fillData(breakfast);
            breakfastAdapter = new HealthAdapter(data, this, false);
            lv_breakfast.setAdapter(breakfastAdapter);
        }
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
            ArrayList<Nutrition> data = fillData(lunch);
            lunchAdapter = new HealthAdapter(data, this, true);
            lv_lunch.setAdapter(lunchAdapter);
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

}
