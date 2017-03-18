package com.kgv.cookbook.fragment.health;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.RecipeActivity;
import com.kgv.cookbook.activity.SuggestActivity;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.PastWeekBean;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.ui.PastWeekView;
import com.kgv.cookbook.util.DateUtils;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/3/11 15:05
 * Email : 18627241899@163.com
 * Description :
 */
public class PastWeekFragment extends BaseFragment implements PastWeekView.PastWeekViewListener {

    private PastWeekView pastWeekView;
    private String today;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_past_week;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        today = DateUtils.getToday();
        initUI(contentView);
        getPastWeekData();
    }

    private void initUI(View contentView) {
        pastWeekView = (PastWeekView) contentView.findViewById(R.id.pastWeekView);
        pastWeekView.setListener(this);
    }

    private void getPastWeekData() {
        httpUtils.doGet(Urls.BLOG_PAST_WEEK_NUTRITION_DATA+ "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/create_time/" + today, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                List<PastWeekBean> pastWeekBeans = gson.fromJson(str, new TypeToken<List<PastWeekBean>>() {
                }.getType());
                handler.obtainMessage(0, pastWeekBeans).sendToTarget();
            }

            @Override
            public void onError(String msg) {
                LogUtils.v("manager", "past week Error : " + msg);
            }
        });
    }

    @Override
    public void onRecipeNameClick(String id) {
        if (!"-1".equals(id)) {
            Intent i = new Intent(baseActivity, RecipeActivity.class);
            i.putExtra(IntentKeys.SHIPU_ID, id);
            baseActivity.startActivity(i);
            baseActivity.overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
        }
    }

    @Override
    public void onNutritionClick(String date, String unit) {
        Intent intent = new Intent(baseActivity, SuggestActivity.class);
        intent.putExtra(IntentKeys.DATE, date);
        intent.putExtra(IntentKeys.UNIT, unit);
        baseActivity.startActivity(intent);
        baseActivity.overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
    }

    @Override
    protected void handleMsg0(Message msg) {
        List<PastWeekBean> pastWeekBeans = (List<PastWeekBean>) msg.obj;
        pastWeekView.setDataSet(pastWeekBeans);
    }
}
