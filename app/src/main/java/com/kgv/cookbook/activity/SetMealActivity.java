package com.kgv.cookbook.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kgv.cookbook.R;
import com.kgv.cookbook.adapter.AllMenuAdapter;
import com.kgv.cookbook.adapter.SetMealTitleAdapter;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.bean.AddToMenuShipu;
import com.kgv.cookbook.bean.MenuAndBlogShiPu;
import com.kgv.cookbook.bean.SetMealBean;
import com.kgv.cookbook.bean.SetMealDetailBean;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Strings;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.fragment.setmeal.RecipeCateFragment;
import com.kgv.cookbook.fragment.setmeal.RecipeListFragment;
import com.kgv.cookbook.fragment.setmeal.SetMealListFragment;
import com.kgv.cookbook.listener.IOnChooseCateListener;
import com.kgv.cookbook.listener.IOnMenuDeleteListener;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 陈可轩 on 2017/2/20 16:43
 * Email : 18627241899@163.com
 * Description : 主页-食谱->美味套餐
 */
public class SetMealActivity extends BaseActivity implements View.OnClickListener, IOnChooseCateListener, IOnMenuDeleteListener, View.OnTouchListener {

    private EditText et_search;
    private TextView tv_0;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private GridView gv_title;
    private SetMealTitleAdapter titleAdapter;
    private int currentFragment = - 1;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private SetMealListFragment listFragment;
    private RecipeCateFragment cateFragment;
    private RecipeListFragment recipeListFragment;
    public List<SetMealBean> setMealList;
    private HashMap<String, String> params = new HashMap<>();
    public String currentCateId;
    public SetMealBean currentSetMeal;
    //菜单
    private LinearLayout ll_menu_content;
    private ListView lv_breakfast;
    private ListView lv_lunch;
    private ListView lv_dinner;
    private AllMenuAdapter mAdapter;
    private AllMenuAdapter aAdapter;
    private AllMenuAdapter nAdapter;
    private ImageView iv_menu_switch;
    private boolean isMenuShowing = false;
    private LocalBroadcastManager localBroadcastManager;
    private LoginBroadcastReceiver receiver;

    public boolean isNormal = true;
    private int normalPage = 1;
    private int editPage = 1;
    private String paramsUrls;
    private boolean noMoreData = false;

    @Override
    protected boolean hasBottomMenu () {
        return true;
    }

    @Override
    protected int getContentViewId () {
        return R.layout.activity_set_meal;
    }

    @Override
    protected void initialization (Bundle savedInstanceState) {
        initUI();
        initListener();
        initReceiver();
        fillMenuData();
        getDefaultSetMeal(false);
    }

    private void fillMenuData () {
        if (mUser.isExist()) {
            getMenuData(0);
            getMenuData(1);
            getMenuData(2);
        }
    }

    private void initUI () {
        iv_menu_switch = (ImageView) findViewById(R.id.iv_menu_switch);
        lv_breakfast = (ListView) findViewById(R.id.lv_breakfast);
        lv_lunch = (ListView) findViewById(R.id.lv_lunch);
        lv_dinner = (ListView) findViewById(R.id.lv_dinner);
        ll_menu_content = (LinearLayout) findViewById(R.id.ll_menu_content);
        et_search = (EditText) findViewById(R.id.et_search);
        tv_0 = (TextView) findViewById(R.id.tv_0);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        gv_title = (GridView) findViewById(R.id.gv_title);
        titleAdapter = new SetMealTitleAdapter(Strings.getSetMealTitle());
        gv_title.setAdapter(titleAdapter);
    }

    private void initListener () {
        tv_0.setOnClickListener(this);
        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        gv_title.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                if (position == titleAdapter.getCurrentSelectivePosition()) {
                    titleAdapter.setCurrentSelectivePosition(- 1);
                    removePeople();
                } else {
                    titleAdapter.setCurrentSelectivePosition(position);
                    String peopleId = titleAdapter.getItemIdByPosition(position);
                    //1.选择人数
                    getHasParamsData(peopleId, null, null);
                }
            }
        });
        findViewById(R.id.ll_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (isMenuShowing) {
                    hideMenu();
                } else {
                    showMenu();
                }
            }
        });
        findViewById(R.id.tv_go_cooking).setOnTouchListener(this);
        findViewById(R.id.iv_search).setOnTouchListener(this);
        findViewById(R.id.tvReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                isNormal = true;
                editPage = 1;
                normalPage = 1;
                removeCateId();
                removePeople();
                removeKeyword();
                et_search.setText("");
                currentCateId = "";
                hideLeftTitles();
                titleAdapter.showAll();
                titleAdapter.setCurrentSelectivePosition(-1);
                getDefaultSetMeal(false);
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

    public void onSetMealItemClick (SetMealBean bean) {
        currentSetMeal = bean;
        if (recipeListFragment != null) {
            recipeListFragment.refreshData();
        }
        showFragment(2);
    }

    public void onGoRecipeActivity (String id) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(IntentKeys.SHIPU_ID, id);
        jumpActivity(intent, false);
    }

    @Override
    public boolean onTouch (View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(0.4f);
                break;
            case MotionEvent.ACTION_UP:
                v.setAlpha(1.0f);
                switch (v.getId()) {
                    case R.id.iv_search:
                        AppUtils.hideKeyboard(et_search);
                        String keyword = et_search.getText().toString().trim();
                        if (TextUtils.isEmpty(keyword)) {
                            tip("请输入食材或食谱");
                            return true;
                        }
                        //3.选择关键字
                        getHasParamsData(null, null, keyword);
                        break;
                    case R.id.tv_go_cooking:
                        goCooking();
                        break;
                }
                break;
        }
        return true;
    }

    @Override
    public void onClick (View v) {
        hideLeftTitles();
        v.setSelected(true);
        switch (v.getId()) {
            case R.id.tv_0:
                currentCateId = "2";//中华菜系
                break;
            case R.id.tv_1:
                currentCateId = "3";//外国菜
                break;
            case R.id.tv_2:
                currentCateId = "9";//节日
                break;
            case R.id.tv_3:
                currentCateId = "10";//场景
                break;
        }
        if (cateFragment == null) {
            showFragment(1);
        } else {
            if (currentFragment != 1) {
                showFragment(1);
            } else {
                cateFragment.getDataFromCache(currentCateId);
            }
        }
    }

    private void hideLeftTitles () {
        tv_0.setSelected(false);
        tv_1.setSelected(false);
        tv_2.setSelected(false);
        tv_3.setSelected(false);
    }

    @Override
    public void onChooseCate (String id) {
        String peopleId = null;
        if ("65".equals(id) || "57".equals(id) || "73".equals(id)) {
            titleAdapter.showOnly(1);
            peopleId = "2";
        } else if ("64".equals(id)) {
            titleAdapter.showOnly(0);
            peopleId = "1";
        } else {
            titleAdapter.showOnly(- 1);
        }
        //1.选择食谱分类id
        getHasParamsData(peopleId, id, null);
    }

    private void removePeople () {
        params.remove("/people_num_id/");
    }

    private void removeCateId () {
        params.remove("/cid/");
    }

    private void removeKeyword () {
        params.remove("/title/");
    }

    private void showFragment (int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (position == 0) {
            currentFragment = 0;
            if (listFragment == null) {
                listFragment = new SetMealListFragment();
                transaction.add(R.id.content, listFragment);
            } else {
                transaction.show(listFragment);
            }
        } else if (position == 1) {
            currentFragment = 1;
            if (cateFragment == null) {
                cateFragment = new RecipeCateFragment();
                cateFragment.setListener(this);
                transaction.add(R.id.content, cateFragment);
            } else {
                transaction.show(cateFragment);
            }
        } else if (position == 2) {
            currentFragment = 2;
            if (recipeListFragment == null) {
                recipeListFragment = new RecipeListFragment();
                transaction.add(R.id.content, recipeListFragment);
            } else {
                transaction.show(recipeListFragment);
            }
        }
        transaction.commit();
    }

    private void hideFragments (FragmentTransaction transaction) {
        if (listFragment != null) {
            transaction.hide(listFragment);
        }
        if (cateFragment != null) {
            transaction.hide(cateFragment);
        }
        if (recipeListFragment != null) {
            transaction.hide(recipeListFragment);
        }
    }

    private String getParams (String people, String cateId, String keyword) {
        if (! TextUtils.isEmpty(people)) {
            params.put("/people_num_id/", people);
        }
        if (! TextUtils.isEmpty(cateId)) {
            params.put("/cid/", cateId);
        }
        if (! TextUtils.isEmpty(keyword)) {
            params.put("/title/", keyword);
        }
        Iterator iterator = params.entrySet().iterator();
        String paramsUrl = "";
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            paramsUrl += key + value;
        }
        return paramsUrl;
    }

    //获取筛选套餐
    private void getHasParamsData (String people, String cateId, String keyword) {
        removePeople();  //  去掉情人节/七夕/单身自带peopleId
        isNormal = false;
        normalPage = 1;//重置默认数据页数
        editPage = 1; //重置筛选页数
        if (! TextUtils.isEmpty(keyword)) {
            removeCateId();//search时去掉分类id
            cateId = "";
            hideLeftTitles();   //去掉分类高亮
            currentCateId = "";
            titleAdapter.setCurrentSelectivePosition(- 1);//去掉人数高亮
        }
        if (! TextUtils.isEmpty(cateId)) {// 分类时去掉keyword
            removeKeyword();
            et_search.setText("");
        }
        paramsUrls = getParams(people, cateId, keyword);
        LogUtils.v("set meal", "筛选：" + Urls.SET_MEAL_GET_LIST + "/p/" + editPage + paramsUrls);
        mHttpUtils.doGet(Urls.SET_MEAL_GET_LIST + "/p/" + editPage + paramsUrls, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                LogUtils.v("set meal", "筛选结果 = " + str);
                if (! "null".equals(str) && ! "[]".equals(str) && ! TextUtils.isEmpty(str)) {
                    Gson gson = new Gson();
                    List<SetMealBean> list = gson.fromJson(
                            str,
                            new TypeToken<List<SetMealBean>>() {
                            }.getType());
                    myHandler.obtainMessage(0, list).sendToTarget();
                }
            }

            @Override
            public void onError (String msg) {
                myHandler.obtainMessage(1).sendToTarget();
            }
        });
    }

    //追加筛选数据
    public void appendHasParamsData () {
        editPage += 1;
        String url = Urls.SET_MEAL_GET_LIST + "/p/" + editPage + paramsUrls;
        LogUtils.v("set meal", "追加筛选：" + url);
        mHttpUtils.doGet(url, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                if (! "null".equals(str) && ! "[]".equals(str) && ! TextUtils.isEmpty(str)) {
                    Gson gson = new Gson();
                    List<SetMealBean> list = gson.fromJson(
                            str, new TypeToken<List<SetMealBean>>() {}.getType());
                    myHandler.obtainMessage(6, list).sendToTarget();
                }
            }

            @Override
            public void onError (String msg) {
                myHandler.obtainMessage(7).sendToTarget();
            }
        });
    }

    //获取默认数据
    private void getDefaultSetMeal (final boolean isAppend) {
        isNormal = true;
        editPage = 1;
        LogUtils.v("set meal", "初始：" + Urls.SET_MEAL_GET_LIST + "/p/" + normalPage);
        mHttpUtils.doGet(Urls.SET_MEAL_GET_LIST + "/p/" + normalPage, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                if (! "null".equals(str) && ! "[]".equals(str) && ! TextUtils.isEmpty(str)) {
                    Gson gson = new Gson();
                    List<SetMealBean> list = gson.fromJson(
                            str,
                            new TypeToken<List<SetMealBean>>() {
                            }.getType());
                    if (isAppend) {
                        myHandler.obtainMessage(6, list).sendToTarget();
                    } else {
                        myHandler.obtainMessage(0, list).sendToTarget();
                    }
                }
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    //追加默认数据
    public void appendDefaultSetMeal () {
        normalPage += 1;
        getDefaultSetMeal(true);
    }


    @Override
    protected void handleMsg0 (Message msg) {
        setMealList = (List<SetMealBean>) msg.obj;
        if (listFragment == null) {
            showFragment(0);
        } else {
            if (currentFragment != 0) {
                showFragment(0);
            }
            listFragment.replaceData();
        }
    }

    @Override
    protected void handleMsg6 (Message msg) {
        setMealList = (List<SetMealBean>) msg.obj;
        listFragment.appendData(setMealList);
    }

    @Override
    protected void handleMsg1 (Message msg) {
        tip("暂无相关套餐");
    }

    @Override
    protected void handleMsg2 (Message msg) {
        tip("食谱已添加至日志");
    }

    @Override
    protected void handleMsg3 (Message msg) {
        //早餐菜单
        String json = (String) msg.obj;
        ArrayList<AddToMenuShipu> datas = new ArrayList<>();
        if (! "null".equals(json)) {
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
    protected void handleMsg4 (Message msg) {
        //午餐菜单
        String json = (String) msg.obj;
        ArrayList<AddToMenuShipu> datas = new ArrayList<>();
        if (! "null".equals(json)) {
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
    protected void handleMsg5 (Message msg) {
        //晚餐菜单
        String json = (String) msg.obj;
        ArrayList<AddToMenuShipu> datas = new ArrayList<>();
        if (! "null".equals(json)) {
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
    protected void handleMsg7 (Message msg) {
        if (! noMoreData) {
            tip("没有更多数据了");
        }
        noMoreData = true;
    }

    private void showMenu () {
        isMenuShowing = true;
        iv_menu_switch.setImageResource(R.drawable.menu_opened);
        ll_menu_content.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                ll_menu_content, "translationX",
                500, 450, 400, 350, 300, 250, 200, 150, 100, 50, 0);
        animator.setDuration(600);
        animator.start();
    }

    private void hideMenu () {
        isMenuShowing = false;
        iv_menu_switch.setImageResource(R.drawable.menu_closed);
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                ll_menu_content, "translationX",
                0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500);
        animator.setDuration(600);
        animator.start();
    }

    private void getMenuData (int type) {
        switch (type) {
            case 0:
                //早餐
                mHttpUtils.doGet(Urls.MENU_MORNING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/morning_menu/create_time/", new HttpResponse<String>(String.class) {
                    @Override
                    public void onSuccess (String str) {
                        myHandler.obtainMessage(3, str).sendToTarget();
                    }

                    @Override
                    public void onError (String msg) {

                    }
                });
                break;
            case 1:
                //午餐
                mHttpUtils.doGet(Urls.MENU_NOONING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/nooning_menu/create_time/", new HttpResponse<String>(String.class) {
                    @Override
                    public void onSuccess (String str) {
                        myHandler.obtainMessage(4, str).sendToTarget();
                    }

                    @Override
                    public void onError (String msg) {
                    }
                });
                break;
            case 2:
                //晚餐
                mHttpUtils.doGet(Urls.MENU_EVENING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/evening_menu/create_time/", new HttpResponse<String>(String.class) {
                    @Override
                    public void onSuccess (String str) {
                        myHandler.obtainMessage(5, str).sendToTarget();
                    }

                    @Override
                    public void onError (String msg) {
                    }
                });
                break;
        }
    }

    public void addToMenu (SetMealDetailBean bean, int type) {
        if (! mUser.isExist()) {
            showLoginDialog();
            return;
        }
        if (! isMenuShowing) {
            showMenu();
        }
        AddToMenuShipu sp = new AddToMenuShipu(bean.getId(), bean.getImage(), bean.getName(), bean.getImage_thumb(), "", type);
        if (mAdapter.hasMenu(sp)) {
            tip("该食谱已加入早餐");
            return;
        }
        if (aAdapter.hasMenu(sp)) {
            tip("该食谱已加入午餐");
            return;
        }
        if (nAdapter.hasMenu(sp)) {
            tip("该食谱已加入晚餐");
            return;
        }
        switch (type) {
            case 0:
                String url = Urls.MENU_MORNING_ADD + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/morning_menu/cookbook_id/" + sp.getId() + "/act/add";
                mHttpUtils.doGet(url, new HttpResponse<String>(String.class) {
                    @Override
                    public void onSuccess (String str) {
                        getMenuData(0);
                    }

                    @Override
                    public void onError (String msg) {

                    }
                });
                break;
            case 1:
                mHttpUtils.doGet(Urls.MENU_NOONING_ADD + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/nooning_menu/cookbook_id/" + sp.getId() + "/act/add", new HttpResponse<String>(String.class) {
                    @Override
                    public void onSuccess (String str) {
                        getMenuData(1);
                    }

                    @Override
                    public void onError (String msg) {

                    }
                });

                break;
            case 2:
                mHttpUtils.doGet(Urls.MENU_EVENING_ADD + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/evening_menu/cookbook_id/" + sp.getId() + "/act/add", new HttpResponse<String>(String.class) {
                    @Override
                    public void onSuccess (String str) {
                        getMenuData(2);
                    }

                    @Override
                    public void onError (String msg) {

                    }
                });
                break;
        }
    }

    @Override
    public void onMenuDelete (AddToMenuShipu shiPu, int type) {
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

    private void goCooking () {
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

    private void submitMenu2Blog () {
        mHttpUtils.doGet(Urls.MENU_SUBMIT_2_BLOG + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword(), new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String o) {
                myHandler.obtainMessage(2).sendToTarget();
            }

            @Override
            public void onError (String msg) {
                myHandler.obtainMessage(2).sendToTarget();
            }
        });
    }

    @Override
    protected void back () {
        if (currentFragment != 0) {
            showFragment(0);
        } else {
            finish();
            overridePendingTransition(R.anim.activity_in_left_2_right, R.anim.activity_out_left_2_right);
        }
    }

    @Override
    public void onBackPressed () {
        super.onBackPressed();
        back();
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(receiver);
    }

}
