package com.kgv.cookbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.adapter.SuggestAdapter;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.bean.SuggestNutrition;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;

/**
 * Created by 陈可轩 on 2017/2/23 16:11
 * Email : 18627241899@163.com
 * Description : 主页-健康管理->营养建议表
 */
public class SuggestActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private SuggestNutrition data;
    private String u;
    private String date;
    private ListView lv_real_breakfast;
    private ListView lv_real_lunch;
    private ListView lv_real_dinner;
    private TextView tv_real_sum, tv_real_standard_sum, tv_real_result;//实际摄入量、标准摄入量、结果
    private ListView lv_suggest_breakfast;
    private ListView lv_suggest_lunch;
    private ListView lv_suggest_dinner;
    private TextView tv_suggest_sum, tv_suggest_standard_sum, tv_suggest_result;
    private ImageView iv_status_real, iv_status_suggest;
    private SuggestAdapter realBAdapter;
    private SuggestAdapter realLAdapter;
    private SuggestAdapter realDAdapter;
    private SuggestAdapter suggestBAdapter;
    private SuggestAdapter suggestLAdapter;
    private SuggestAdapter suggestDAdapter;
    private TextView tv_result;

    @Override
    protected boolean hasBottomMenu() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_suggest;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        u = getIntent().getStringExtra(IntentKeys.UNIT);
        date = getIntent().getStringExtra(IntentKeys.DATE);
        initUI();
        initListener();
        getData();

    }

    private void initUI() {
        tv_result = (TextView) findViewById(R.id.tv_result);
        lv_real_breakfast = (ListView) findViewById(R.id.lv_real_breakfast);
        lv_real_lunch = (ListView) findViewById(R.id.lv_real_lunch);
        lv_real_dinner = (ListView) findViewById(R.id.lv_real_dinner);
        tv_real_sum = (TextView) findViewById(R.id.tv_real_sum);
        tv_real_standard_sum = (TextView) findViewById(R.id.tv_real_standard_sum);
        tv_real_result = (TextView) findViewById(R.id.tv_real_result);
        lv_suggest_breakfast = (ListView) findViewById(R.id.lv_suggest_breakfast);
        lv_suggest_lunch = (ListView) findViewById(R.id.lv_suggest_lunch);
        lv_suggest_dinner = (ListView) findViewById(R.id.lv_suggest_dinner);
        tv_suggest_sum = (TextView) findViewById(R.id.tv_suggest_sum);
        tv_suggest_standard_sum = (TextView) findViewById(R.id.tv_suggest_standard_sum);
        tv_suggest_result = (TextView) findViewById(R.id.tv_suggest_result);
        iv_status_real = (ImageView) findViewById(R.id.iv_status_real);
        iv_status_suggest = (ImageView) findViewById(R.id.iv_status_suggest);
    }

    private void initListener() {
        lv_real_breakfast.setOnItemClickListener(this);
        lv_real_lunch.setOnItemClickListener(this);
        lv_real_dinner.setOnItemClickListener(this);
        lv_suggest_breakfast.setOnItemClickListener(this);
        lv_suggest_lunch.setOnItemClickListener(this);
        lv_suggest_dinner.setOnItemClickListener(this);
    }

    private void getData() {
        String url = Urls.SUGGEST_NUTRITION_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/create_time/" + date + "/unit/";
        LogUtils.v("suggest", url + u);
        mHttpUtils.doGet(url + u, new HttpResponse<SuggestNutrition>(SuggestNutrition.class) {
            @Override
            public void onSuccess(SuggestNutrition bean) {
                myHandler.obtainMessage(0, bean).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    @Override
    protected void handleMsg0(Message msg) {
        data = (SuggestNutrition) msg.obj;
        bindingData();
    }

    private void bindingData() {
        String unit = data.getUint();
        if (data.getBlog_breakfast() != null) {
            realBAdapter = new SuggestAdapter(data.getBlog_breakfast(), unit);
            lv_real_breakfast.setAdapter(realBAdapter);
        }
        if (data.getBlog_lunch() != null) {
            realLAdapter = new SuggestAdapter(data.getBlog_lunch(), unit);
            lv_real_lunch.setAdapter(realLAdapter);
        }
        if (data.getBlog_dinner() != null) {
            realDAdapter = new SuggestAdapter(data.getBlog_dinner(), unit);
            lv_real_dinner.setAdapter(realDAdapter);
        }
        if (data.getBlog_cookbook() != 0) {
            tv_real_sum.setText(Double.toString(data.getBlog_cookbook()) + "(" + unit + ")");
        }
        if (!TextUtils.isEmpty(data.getBlog_status())) {
            iv_status_real.setImageResource(getStatusImg(data.getBlog_status()));
            tv_real_result.setText("结果：" + getStatusStr(data.getBlog_status()));
        }
        tv_real_standard_sum.setText("标准摄入量：" + data.getSuggest_l() + "~" + data.getSuggest_h() + "(" + unit + ")");
        suggestBAdapter = new SuggestAdapter(data.getHealth_breakfast(), unit);
        suggestLAdapter = new SuggestAdapter(data.getHealth_lunch(), unit);
        suggestDAdapter = new SuggestAdapter(data.getHealth_dinner(), unit);
        lv_suggest_breakfast.setAdapter(suggestBAdapter);
        lv_suggest_lunch.setAdapter(suggestLAdapter);
        lv_suggest_dinner.setAdapter(suggestDAdapter);
        tv_suggest_sum.setText(Double.toString(data.getHealth_cookbook()) + "(" + unit + ")");
        iv_status_suggest.setImageResource(getStatusImg(data.getHealth_status()));
        tv_suggest_standard_sum.setText("标准摄入量：" + data.getSuggest_l() + "~" + data.getSuggest_h() + "(" + unit + ")");
        tv_suggest_result.setText("结果：" + getStatusStr(data.getHealth_status()));
        tv_result.setText("结果：" + data.getSuggest());
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

    private String getStatusStr(String str) {
        if (">".equals(str)) {
            return "偏高";
        } else if ("<".equals(str)) {
            return "偏低";
        } else {
            return "正常";
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String spid = "";
        switch (parent.getId()) {
            case R.id.lv_real_breakfast:
                spid = realBAdapter.getItemIdByPosition(position);
                break;
            case R.id.lv_real_lunch:
                spid = realLAdapter.getItemIdByPosition(position);
                break;
            case R.id.lv_real_dinner:
                spid = realDAdapter.getItemIdByPosition(position);
                break;
            case R.id.lv_suggest_breakfast:
                spid = suggestBAdapter.getItemIdByPosition(position);
                break;
            case R.id.lv_suggest_lunch:
                spid = suggestLAdapter.getItemIdByPosition(position);
                break;
            case R.id.lv_suggest_dinner:
                spid = suggestDAdapter.getItemIdByPosition(position);
                break;
        }
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(IntentKeys.SHIPU_ID, spid);
        jumpActivity(intent, false);
    }
}