package com.kgv.cookbook.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kgv.cookbook.R;
import com.kgv.cookbook.adapter.AllMenuAdapter;
import com.kgv.cookbook.adapter.SearchGuessAdapter;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.bean.AddToMenuShipu;
import com.kgv.cookbook.bean.MenuAndBlogShiPu;
import com.kgv.cookbook.bean.SearchResult;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.fragment.search.EffectFragment;
import com.kgv.cookbook.fragment.search.GuessFragment;
import com.kgv.cookbook.fragment.search.GyFragment;
import com.kgv.cookbook.fragment.search.PeopleFragment;
import com.kgv.cookbook.fragment.search.TimeFragment;
import com.kgv.cookbook.listener.IOnMenuDeleteListener;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 陈可轩 on 2016/12/31 17:26
 * Email : 18627241899@163.com
 * Description : 搜索页面
 */
public class SearchActivity extends BaseActivity implements View.OnTouchListener, View.OnClickListener, IOnMenuDeleteListener {

    private FragmentManager fragmentManager;
    private EditText et_search;
    private ImageView iv_search;
    private TextView tv_gy;
    private TextView tv_time;
    private TextView tv_people;
    private TextView tv_effect;
    private LinearLayout ll_guess;
    private ListView lv_guess;
    private SearchGuessAdapter guessAdapter;
    private LinearLayout ll_menu_content;
    private ListView lv_breakfast;
    private ListView lv_lunch;
    private ListView lv_dinner;
    private AllMenuAdapter mAdapter;
    private AllMenuAdapter aAdapter;
    private AllMenuAdapter nAdapter;
    private TextView tv_go_cooking;
    private LinearLayout ll_menu;
    private boolean isMenuShowing = false;
    private GyFragment gyFragment;
    private TimeFragment timeFragment;
    private PeopleFragment peopleFragment;
    private EffectFragment effectFragment;
    private GuessFragment guessFragment;
    private int currentFragment = -1;
    private String keyword;
    public SearchResult bean;
    private HashMap<String, String> map;
    private String url;
    private boolean hasConditionalSearch = false;
    private ImageView iv_menu_switch;
    private int page = 1;
    private boolean noMoreData;
    private LocalBroadcastManager localBroadcastManager;
    private LoginBroadcastReceiver receiver;
    private String paramsUrl;

    @Override
    protected boolean hasBottomMenu() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        map = new HashMap<>();
        fragmentManager = getSupportFragmentManager();
        keyword = getIntent().getStringExtra(IntentKeys.SEARCH_KEYWORD);
        url = Urls.SEARCH_COOKBOOK.replace("-", keyword);
        initUI();
        initListener();
        initReceiver();
        fillMenuData();
        getSearchResult(false, url + page, false);
    }

    private void fillMenuData () {
        if (mUser.isExist()) {
            getMenuData(0);
            getMenuData(1);
            getMenuData(2);
        }
    }

    private void initUI() {
        et_search = (EditText) findViewById(R.id.et_search);
        et_search.setText(keyword);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        tv_gy = (TextView) findViewById(R.id.tv_gy);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_people = (TextView) findViewById(R.id.tv_people);
        tv_effect = (TextView) findViewById(R.id.tv_effect);
        lv_guess = (ListView) findViewById(R.id.lv_guess);
        ll_guess = (LinearLayout) findViewById(R.id.ll_guess);
        ll_menu_content = (LinearLayout) findViewById(R.id.ll_menu_content);
        lv_breakfast = (ListView) findViewById(R.id.lv_breakfast);
        lv_lunch = (ListView) findViewById(R.id.lv_lunch);
        lv_dinner = (ListView) findViewById(R.id.lv_dinner);
        tv_go_cooking = (TextView) findViewById(R.id.tv_go_cooking);
        ll_menu = (LinearLayout) findViewById(R.id.ll_menu);
        iv_menu_switch = (ImageView) findViewById(R.id.iv_menu_switch);
    }

    private void initListener() {
        iv_search.setOnTouchListener(this);
        tv_gy.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        tv_people.setOnClickListener(this);
        tv_effect.setOnClickListener(this);
        ll_guess.setOnClickListener(this);
        tv_go_cooking.setOnTouchListener(this);
        ll_menu.setOnClickListener(this);
        lv_guess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cpid = guessAdapter.getItemIdByPosition(position);
                Intent intent = new Intent(SearchActivity.this, RecipeActivity.class);
                intent.putExtra(IntentKeys.SHIPU_ID, cpid);
                jumpActivity(intent, false);
            }
        });
    }

    private void initReceiver () {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter("com.kgv.cookbook.LOGIN");
        receiver = new LoginBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onLoginReceiver () {
        fillMenuData();
    }

    public void onListScroll2Bottom() {
        if (!noMoreData){
            page += 1;
            if(hasConditionalSearch){
                getSearchResult(true,url + page + paramsUrl,false);
            }else{
                getSearchResult(true, url + page, false);
            }
        }
    }

    // append or refresh.
    private void getSearchResult(final boolean append, String url, final boolean refresh) {
        LogUtils.v("search", url);
        mHttpUtils.doGet(url, new HttpResponse<SearchResult>(SearchResult.class) {
            @Override
            public void onSuccess(SearchResult bean) {
                if (append) {
                    myHandler.obtainMessage(9, bean).sendToTarget();
                    return;
                }
                if (refresh) {
                    myHandler.obtainMessage(1, bean).sendToTarget();
                    return;
                }
                myHandler.obtainMessage(0, bean).sendToTarget();
            }

            @Override
            public void onError(String msg) {
                if (append){
                    myHandler.obtainMessage(10).sendToTarget();
                }
                myHandler.obtainMessage(8).sendToTarget();
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(0.4f);
                break;
            case MotionEvent.ACTION_UP:
                v.setAlpha(1.0f);
                switch (v.getId()) {
                    case R.id.iv_search:
                        goSearch();
                        break;
                    case R.id.tv_go_cooking:
                        goCooking();
                        break;
                }
                break;
        }
        return true;
    }

    public void conditionalSearch(String people, String gongyi, String haoshi, String effect) {
        hasConditionalSearch = true;
        page = 1;
        noMoreData = false;
        LogUtils.v("search","time = " + haoshi);
        paramsUrl = getParams(people, gongyi, haoshi, effect);
        LogUtils.v("search","paramsUrl = " + paramsUrl);
        getSearchResult(false, url + page + paramsUrl, true);
    }


    private String getParams(String people, String gongyi, String haoshi, String effect) {
        if (!TextUtils.isEmpty(people)) {
            map.put("/peoplenum/", people);
        }
        if (!TextUtils.isEmpty(gongyi)) {
            map.put("/gongyi/", gongyi);
        }
        if (!TextUtils.isEmpty(haoshi)) {
            map.put("/haoshi/", haoshi);
        }
        if (!TextUtils.isEmpty(effect)) {
            map.put("/effect/", effect);
        }
        Iterator iterator = map.entrySet().iterator();
        String paramsUrl = "";
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            paramsUrl += key + value;
        }
        return paramsUrl;
    }

    //重新开始搜索
    private void goSearch() {
        keyword = et_search.getText().toString().trim();
        if (TextUtils.isEmpty(keyword)) {
            tip("请输入搜索内容");
            return;
        }
        if (hasConditionalSearch) {
            map.clear();
        }
        hasConditionalSearch = false;
        AppUtils.hideKeyboard(et_search);
        page = 1;
        noMoreData = false;
        url = Urls.SEARCH_COOKBOOK.replace("-",keyword);
        getSearchResult(false, url + page, true);
    }

    private void goCooking() {
        if (! mUser.isExist()) {
            showLoginDialog();
            return;
        }
        if (mAdapter.mDatas.size() == 0 && aAdapter.mDatas.size() == 0
                && nAdapter.mDatas.size() == 0) {
            tip("请至少选择一道食谱");
        } else {
            hideMenu();
            mAdapter.clearDatas();
            aAdapter.clearDatas();
            nAdapter.clearDatas();
            submitMenu2Blog();
        }
    }

    private void submitMenu2Blog() {
        mHttpUtils.doGet(Urls.MENU_SUBMIT_2_BLOG + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword(), new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String o) {
                myHandler.obtainMessage(6).sendToTarget();
            }

            @Override
            public void onError(String msg) {
                myHandler.obtainMessage(6).sendToTarget();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_gy:
                showFragment(0);
                break;
            case R.id.tv_time:
                showFragment(1);
                break;
            case R.id.tv_people:
                showFragment(2);
                break;
            case R.id.tv_effect:
                showFragment(3);
                break;
            case R.id.ll_guess:
                showFragment(4);
                break;
            case R.id.ll_menu:
                if (isMenuShowing) {
                    hideMenu();
                } else {
                    showMenu();
                }
                break;
        }
    }

    private void showMenu() {
        isMenuShowing = true;
        ll_menu_content.setVisibility(View.VISIBLE);
        iv_menu_switch.setImageResource(R.drawable.menu_opened);
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                ll_menu_content, "translationX",
                450, 400, 350, 300, 250, 200, 150, 100, 50, 0);
        animator.setDuration(400);
        animator.start();
    }

    private void hideMenu() {
        isMenuShowing = false;
        iv_menu_switch.setImageResource(R.drawable.menu_closed);
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                ll_menu_content, "translationX",
                0, 50, 100, 150, 200, 250, 300, 350, 400, 450);
        animator.setDuration(400);
        animator.start();
    }

    /**
     * 添加食谱到菜单 type:0早 1中 2晚
     */
    public void addToMenu(SearchResult.ContentEntity data, int type) {
        if (! mUser.isExist()) {
            showLoginDialog();
            return;
        }
        if (!isMenuShowing) {
            showMenu();
        }
        AddToMenuShipu shiPu = new AddToMenuShipu(data.getId(), data.getImage(),
                data.getName(), data.getImage_thumb(), data.getDescription(), type);

        if (mAdapter.hasMenu(shiPu)) {
            tip("该食谱已加入早餐");
            return;
        }
        if (aAdapter.hasMenu(shiPu)) {
            tip("该食谱已加入午餐");
            return;
        }
        if (nAdapter.hasMenu(shiPu)) {
            tip("该食谱已加入晚餐");
            return;
        }
        switch (type) {
            case 0:
                mHttpUtils.doGet(Urls.MENU_MORNING_ADD + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/morning_menu/cookbook_id/" + shiPu.getId() + "/act/add", new HttpResponse<String>(String.class) {
                    @Override
                    public void onSuccess(String str) {
                        getMenuData(0);
                    }

                    @Override
                    public void onError(String msg) {

                    }
                });
                break;
            case 1:
                mHttpUtils.doGet(Urls.MENU_NOONING_ADD + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/nooning_menu/cookbook_id/" + shiPu.getId() + "/act/add", new HttpResponse<String>(String.class) {
                    @Override
                    public void onSuccess(String str) {
                        getMenuData(1);
                    }

                    @Override
                    public void onError(String msg) {

                    }
                });

                break;
            case 2:
                mHttpUtils.doGet(Urls.MENU_EVENING_ADD + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/evening_menu/cookbook_id/" + shiPu.getId() + "/act/add", new HttpResponse<String>(String.class) {
                    @Override
                    public void onSuccess(String str) {
                        getMenuData(2);
                    }

                    @Override
                    public void onError(String msg) {

                    }
                });
                break;
        }
    }

    @Override
    public void onMenuDelete(AddToMenuShipu shiPu, int type) {
        switch (type) {
            case 0:
                mHttpUtils.doGet(Urls.MENU_MORNING_DELETE +
                        "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/morning_menu/id/" + shiPu.getDelId() + "/act/del", null);
                mHttpUtils.doGet(Urls.BLOG_MORNING_DELETE +
                        "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/morning_blog/id/" + shiPu.getDelId() + "/act/del", null);
                break;
            case 1:
                mHttpUtils.doGet(Urls.MENU_NOONING_DELETE +
                        "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/nooning_menu/id/" + shiPu.getDelId() + "/act/del", null);
                mHttpUtils.doGet(Urls.BLOG_NOONING_DELETE +
                        "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/nooning_blog/id/" + shiPu.getDelId() + "/act/del", null);
                break;
            case 2:
                mHttpUtils.doGet(Urls.MENU_EVENING_DELETE +
                        "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/evening_menu/id/" + shiPu.getDelId() + "/act/del", null);
                mHttpUtils.doGet(Urls.BLOG_EVENING_DELETE +
                        "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/evening_blog/id/" + shiPu.getDelId() + "/act/del", null);
                break;
        }
    }

    private void showFragment(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (position) {
            case 0:
                currentFragment = 0;
                tv_gy.setSelected(true);
                if (gyFragment == null) {
                    gyFragment = new GyFragment();
                    transaction.add(R.id.fl_content, gyFragment);
                } else {
                    transaction.show(gyFragment);
                }
                break;
            case 1:
                currentFragment = 1;
                tv_time.setSelected(true);
                if (timeFragment == null) {
                    timeFragment = new TimeFragment();
                    transaction.add(R.id.fl_content, timeFragment);
                } else {
                    transaction.show(timeFragment);
                }
                break;
            case 2:
                currentFragment = 2;
                tv_people.setSelected(true);
                if (peopleFragment == null) {
                    peopleFragment = new PeopleFragment();
                    transaction.add(R.id.fl_content, peopleFragment);
                } else {
                    transaction.show(peopleFragment);
                }
                break;
            case 3:
                currentFragment = 3;
                tv_effect.setSelected(true);
                if (effectFragment == null) {
                    effectFragment = new EffectFragment();
                    transaction.add(R.id.fl_content, effectFragment);
                } else {
                    transaction.show(effectFragment);
                }
                break;
            case 4:
                currentFragment = 4;
                ll_guess.setBackgroundColor(AppUtils.getColor(R.color.white));
                if (guessFragment == null) {
                    guessFragment = new GuessFragment();
                    transaction.add(R.id.fl_content, guessFragment);
                } else {
                    transaction.show(guessFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (gyFragment != null) {
            transaction.hide(gyFragment);
        }
        if (timeFragment != null) {
            transaction.hide(timeFragment);
        }
        if (peopleFragment != null) {
            transaction.hide(peopleFragment);
        }
        if (effectFragment != null) {
            transaction.hide(effectFragment);
        }
        if (guessFragment != null) {
            transaction.hide(guessFragment);
        }
        tv_gy.setSelected(false);
        tv_time.setSelected(false);
        tv_people.setSelected(false);
        tv_effect.setSelected(false);
        ll_guess.setBackground(new ColorDrawable());
    }

    @Override
    protected void handleMsg0(Message msg) {
        bean = (SearchResult) msg.obj;
        guessAdapter = new SearchGuessAdapter(bean.getRecommend());
        lv_guess.setAdapter(guessAdapter);
        showFragment(4);
    }

    @Override
    protected void handleMsg1(Message msg) {
        LogUtils.v("search","刷新");
        bean = (SearchResult) msg.obj;
        guessAdapter = new SearchGuessAdapter(bean.getRecommend());
        lv_guess.setAdapter(guessAdapter);
        //优先刷新列表页
        guessFragment.refreshContent();
        if (currentFragment != 4) {
            showFragment(4);
        }
        if (gyFragment != null) {
            gyFragment.refreshContent();
        }
        if (timeFragment != null) {
            timeFragment.refreshContent();
        }
        if (peopleFragment != null) {
            peopleFragment.refreshContent();
        }
        if (effectFragment != null) {
            effectFragment.refreshContent();
        }
    }

    @Override
    protected void handleMsg3(Message msg) {
        //早餐菜单
        String json = (String) msg.obj;
        ArrayList<AddToMenuShipu> datas = new ArrayList<>();
        if (!"null".equals(json)) {
            Gson gson = new Gson();
            List<MenuAndBlogShiPu> menuShiPus = gson.fromJson(json, new TypeToken<List<MenuAndBlogShiPu>>() {
            }.getType());
            for (MenuAndBlogShiPu msp : menuShiPus) {
                datas.add(new AddToMenuShipu(msp.getCookbook_id(), msp.getImage(), msp.getName(),
                        msp.getImage_thumb(), msp.getDescription(), msp.getId(), 0));
            }
        }
        mAdapter = new AllMenuAdapter(datas, this);
        lv_breakfast.setAdapter(mAdapter);
    }

    @Override
    protected void handleMsg4(Message msg) {
        //午餐菜单
        String json = (String) msg.obj;
        ArrayList<AddToMenuShipu> datas = new ArrayList<>();
        if (!"null".equals(json)) {
            Gson gson = new Gson();
            List<MenuAndBlogShiPu> menuShiPus = gson.fromJson(json, new TypeToken<List<MenuAndBlogShiPu>>() {
            }.getType());
            for (MenuAndBlogShiPu msp : menuShiPus) {
                datas.add(new AddToMenuShipu(msp.getCookbook_id(), msp.getImage(), msp.getName(),
                        msp.getImage_thumb(), msp.getDescription(), msp.getId(), 1));
            }
        }
        aAdapter = new AllMenuAdapter(datas, this);
        lv_lunch.setAdapter(aAdapter);
    }

    @Override
    protected void handleMsg5(Message msg) {
        //晚餐菜单
        String json = (String) msg.obj;
        ArrayList<AddToMenuShipu> datas = new ArrayList<>();
        if (!"null".equals(json)) {
            Gson gson = new Gson();
            List<MenuAndBlogShiPu> menuShiPus = gson.fromJson(json, new TypeToken<List<MenuAndBlogShiPu>>() {
            }.getType());
            for (MenuAndBlogShiPu msp : menuShiPus) {
                datas.add(new AddToMenuShipu(msp.getCookbook_id(), msp.getImage(), msp.getName(),
                        msp.getImage_thumb(), msp.getDescription(), msp.getId(), 2));
            }
        }
        nAdapter = new AllMenuAdapter(datas, this);
        lv_dinner.setAdapter(nAdapter);
    }

    @Override
    protected void handleMsg6(Message msg) {
        tip("食谱已添加至日志");
    }

    @Override
    protected void handleMsg8(Message msg) {
        tip("未搜索到相关食谱");
    }

    @Override
    protected void handleMsg9(Message msg) {
        LogUtils.v("search","追加");
        bean = (SearchResult) msg.obj;
        guessFragment.appendData();
    }

    @Override
    protected void handleMsg10(Message msg) {
        noMoreData = true;
        tip("没有更多数据了");
    }

    private void getMenuData(int type) {
        switch (type) {
            case 0:
                //早餐
                mHttpUtils.doGet(Urls.MENU_MORNING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/morning_menu/create_time/", new HttpResponse<String>(String.class) {
                    @Override
                    public void onSuccess(String str) {
                        myHandler.obtainMessage(3, str).sendToTarget();
                    }

                    @Override
                    public void onError(String msg) {

                    }
                });
                break;
            case 1:
                //午餐
                mHttpUtils.doGet(Urls.MENU_NOONING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/nooning_menu/create_time/", new HttpResponse<String>(String.class) {
                    @Override
                    public void onSuccess(String str) {
                        myHandler.obtainMessage(4, str).sendToTarget();
                    }

                    @Override
                    public void onError(String msg) {
                    }
                });
                break;
            case 2:
                //晚餐
                mHttpUtils.doGet(Urls.MENU_EVENING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/evening_menu/create_time/", new HttpResponse<String>(String.class) {
                    @Override
                    public void onSuccess(String str) {
                        myHandler.obtainMessage(5, str).sendToTarget();
                    }

                    @Override
                    public void onError(String msg) {
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(receiver);
    }
}

