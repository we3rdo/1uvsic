package com.kgv.cookbook.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kgv.cookbook.R;
import com.kgv.cookbook.adapter.FamilyAgeAdapter;
import com.kgv.cookbook.adapter.FamilyContentAdapter;
import com.kgv.cookbook.adapter.FamilyHealthAdapter;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.bean.FamilyContent;
import com.kgv.cookbook.bean.RecipeCategory;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Strings;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.listener.IOnHealthItemClickListener;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;
import com.nineoldandroids.animation.Animator;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/1/11 16:35
 * Email : 18627241899@163.com
 * Description : 主页-设置-家庭设置
 */
public class FamilySettingActivity extends BaseActivity implements AdapterView.OnItemClickListener, IOnHealthItemClickListener {

    private TextView tv_tip;
    private ListView lv_age;
    private ListView lv_people;
    private GridView gv_health;
    private FamilyAgeAdapter ageAdapter;
    private FamilyAgeAdapter peopleAdapter;
    private List<RecipeCategory.ChildrenEntity> healthData;
    private ListView lv_content;
    private String currentPeopleId = "";
    private String currentHealthId = "";
    private FamilyHealthAdapter healthAdapter;
    private FamilyContentAdapter contentAdapter;
    private View frame_people;
    private View frame_health;
    private int inGuidance = -1;// 0:开始引导  1:设置人数完成 2:设置健康状况完成
    private boolean isJump;
    private boolean changed;

    @Override
    protected boolean hasBottomMenu () {
        return true;
    }

    @Override
    protected int getContentViewId () {
        return R.layout.activity_family_setting;
    }

    @Override
    protected void initialization (Bundle savedInstanceState) {
        initUI();
        initListener();
        getFunctionFromNet();
        isJump = getIntent().getBooleanExtra(IntentKeys.JUMP_SET_FAMILY,false);
        if (mUser.isExist()) {
            getFamilyContent(true);
        }
    }

    private void initUI () {
        frame_people = findViewById(R.id.frame_people);
        frame_health = findViewById(R.id.frame_health);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        lv_age = (ListView) findViewById(R.id.lv_age);
        lv_people = (ListView) findViewById(R.id.lv_people);
        gv_health = (GridView) findViewById(R.id.gv_health);
        lv_content = (ListView) findViewById(R.id.lv_content);

        ageAdapter = new FamilyAgeAdapter(Strings.getAge());
        lv_age.setAdapter(ageAdapter);
        peopleAdapter = new FamilyAgeAdapter(Strings.getPeople());
        lv_people.setAdapter(peopleAdapter);
    }

    private void initListener () {
        lv_age.setOnItemClickListener(this);
        lv_people.setOnItemClickListener(this);
        gv_health.setOnItemClickListener(this);
    }

    @Override
    public void onHealthItemDelete (String id) {
        mHttpUtils.doGet(Urls.FAMILY_DELETE + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                + "/id/" + id + "/act/del", null);
        changed = true;
        tv_tip.setVisibility(View.GONE);
        if (frame_health.getVisibility() == View.VISIBLE){
            frame_health.setVisibility(View.GONE);
            currentHealthId = "";
        }
        if (frame_people.getVisibility() == View.VISIBLE){
            frame_people.setVisibility(View.GONE);
            currentPeopleId = "";
        }
    }

    private void sendChangeBroadcast(){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent("com.kgv.cookbook.FAMILY_CHANGE");
        localBroadcastManager.sendBroadcast(intent);
    }

    //设置人群
    @Override
    public void onPeopleClick (String id, int position) {
        if (frame_health.getVisibility() == View.VISIBLE) {
            hideAlpha(frame_health);
            contentAdapter.hideHealth();
        }
        if (inGuidance != -1){
            contentAdapter.clearNewIdByPeople();
            contentAdapter.clearNewIdByHealth();
            inGuidance = -1;
        }
        frame_people.setVisibility(View.VISIBLE);
        startAlpha(frame_people);
        currentPeopleId = id;
        contentAdapter.showPeople(position);
        tv_tip.setText("请点击设置该人群人数");
        tv_tip.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn)
                .duration(700)
                .playOn(tv_tip);
    }

    //设置健康状况
    @Override
    public void onHealthClick (String id, int position) {
        if (frame_people.getVisibility() == View.VISIBLE) {
            hideAlpha(frame_people);
            contentAdapter.hidePeople();
        }
        if (inGuidance != -1){
            contentAdapter.clearNewIdByPeople();
            contentAdapter.clearNewIdByHealth();
            inGuidance = -1;
        }
        frame_health.setVisibility(View.VISIBLE);
        startAlpha(frame_health);
        currentHealthId = id;
        contentAdapter.showHealth(position);
        tv_tip.setText("请点击设置该人群健康状况");
        tv_tip.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn)
                .duration(700)
                .playOn(tv_tip);
    }

    private void startAlpha (View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        animator.setDuration(1500);
        animator.start();
    }

    private void hideAlpha (final View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        animator.setDuration(1000);
        animator.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart (android.animation.Animator animation) {

            }

            @Override
            public void onAnimationEnd (android.animation.Animator animation) {
                view.clearAnimation();
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel (android.animation.Animator animation) {

            }

            @Override
            public void onAnimationRepeat (android.animation.Animator animation) {

            }
        });
        animator.start();
    }

    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_age:
                inGuidance = 0;//开始引导
                addToFamily(position);
                break;
            case R.id.lv_people:
                editPeople(position);
                break;
            case R.id.gv_health:
                editHealth(position);
                break;
        }
    }

    //添加家庭成员
    private void addToFamily (int position) {
        String ageId = ageAdapter.getItemIdByPosition(position);
        mHttpUtils.doGet(Urls.FAMILY_ADD + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                + "/member/" + ageId + "/act/add", new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                getFamilyContent(false);
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    //编辑指定人群人数
    private void editPeople (int position) {
        if (TextUtils.isEmpty(currentPeopleId)) {
            return;
        }
        if (frame_health.getVisibility() == View.VISIBLE) {
            hideAlpha(frame_health);
        }
        if (frame_people.getVisibility() == View.VISIBLE) {
            hideAlpha(frame_people);
        }
        if (inGuidance != 0){
            YoYo.with(Techniques.ZoomOut)
                    .duration(700)
                    .withListener(new SetGoneWithAnimation(tv_tip))
                    .playOn(tv_tip);
        }
        String peopleId = peopleAdapter.getItemIdByPosition(position);
        mHttpUtils.doGet(Urls.FAMILY_EDIT_PEOPLE
                + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                + "/id/" + currentPeopleId + "/field/num/val/" + peopleId + "/act/save", new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                if (inGuidance == 0){
                    inGuidance = 1;//设置人数完成
                }
                getFamilyContent(false);
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    //编辑指定人群健康状况
    private void editHealth (int position) {
        if (frame_health.getVisibility() == View.VISIBLE) {
            hideAlpha(frame_health);
        }
        if (frame_people.getVisibility() == View.VISIBLE) {
            hideAlpha(frame_people);
        }
        YoYo.with(Techniques.ZoomOut)
                .duration(700)
                .withListener(new SetGoneWithAnimation(tv_tip))
                .playOn(tv_tip);
        if (TextUtils.isEmpty(currentHealthId)) {
            return;
        }
        String healthId = healthAdapter.getItemIdByPosition(position);
        mHttpUtils.doGet(Urls.FAMILY_EDIT_HEALTH
                + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                + "/id/" + currentHealthId + "/field/health/val/" + healthId + "/act/save", new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                if (inGuidance == 1){
                    inGuidance = 2;//健康状况设置完成
                }
                getFamilyContent(false);
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    @Override
    protected void handleMsg0 (Message msg) {
        healthData = (List<RecipeCategory.ChildrenEntity>) msg.obj;
    }

    @Override
    protected void handleMsg1 (Message msg) {
        List<RecipeCategory.ChildrenEntity> list = (List<RecipeCategory.ChildrenEntity>) msg.obj;
        healthData.addAll(list);
        healthAdapter = new FamilyHealthAdapter(healthData);
        gv_health.setAdapter(healthAdapter);
    }

    @Override
    protected void handleMsg2 (Message msg) {
        LogUtils.v("abc","inGuidance = " + inGuidance);
        List<FamilyContent> datas = (List<FamilyContent>) msg.obj;
        if (inGuidance == 0) {
            currentPeopleId = contentAdapter.showPeopleByNewData(datas);
            frame_people.setVisibility(View.VISIBLE);
            startAlpha(frame_people);
            tv_tip.setText("请点击设置该人群人数");
            tv_tip.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.ZoomIn)
                    .duration(700)
                    .playOn(tv_tip);
        }else if(inGuidance == 1){
            contentAdapter.refreshData(datas);
            currentHealthId = contentAdapter.showHealthByNewData();
            frame_health.setVisibility(View.VISIBLE);
            startAlpha(frame_health);
            tv_tip.setText("请点击设置该人群健康状况");
        }else{
            inGuidance = -1;
            contentAdapter = new FamilyContentAdapter(datas, this);
            lv_content.setAdapter(contentAdapter);
        }
    }

    private void getFunctionFromNet () {
        mHttpUtils.doGet(Urls.FUNCTION_G, new HttpResponse<RecipeCategory>(RecipeCategory.class) {
            @Override
            public void onSuccess (RecipeCategory cBean) {
                List<RecipeCategory.ChildrenEntity> children = cBean.getChildren();
                myHandler.obtainMessage(0, children).sendToTarget();
                getSicknessFromNet();
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    private void getSicknessFromNet () {
        mHttpUtils.doGet(Urls.SICKNESS_G, new HttpResponse<RecipeCategory>(RecipeCategory.class) {
            @Override
            public void onSuccess (RecipeCategory cBean) {
                List<RecipeCategory.ChildrenEntity> children = cBean.getChildren();
                myHandler.obtainMessage(1, children).sendToTarget();
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    private void getFamilyContent (final boolean isFirst) {
        mHttpUtils.doGet(Urls.FAMILY_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword(), new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                if (!isFirst){
                    changed = true;
                }
                Gson gson = new Gson();
                List<FamilyContent> datas = gson.fromJson(str, new TypeToken<List<FamilyContent>>() {}.getType());
                myHandler.obtainMessage(2, datas).sendToTarget();
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    private class SetGoneWithAnimation implements Animator.AnimatorListener {

        private View view;

        public SetGoneWithAnimation (View view) {
            this.view = view;
        }

        @Override
        public void onAnimationStart (Animator animation) {

        }

        @Override
        public void onAnimationEnd (Animator animation) {
            view.clearAnimation();
            view.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationCancel (Animator animation) {

        }

        @Override
        public void onAnimationRepeat (Animator animation) {

        }
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        if (isJump && changed){
            sendChangeBroadcast();
        }
    }
}
