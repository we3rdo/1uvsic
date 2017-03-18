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
import com.kgv.cookbook.adapter.FoodWorldLeftAdapter;
import com.kgv.cookbook.adapter.RAdapter;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.bean.AddToMenuShipu;
import com.kgv.cookbook.bean.HotShiPu;
import com.kgv.cookbook.bean.MenuAndBlogShiPu;
import com.kgv.cookbook.bean.ShiPu;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.fragment.GoCookingFragment;
import com.kgv.cookbook.fragment.RecipesFragment;
import com.kgv.cookbook.fragment.RecommendFragment;
import com.kgv.cookbook.listener.IOnMenuDeleteListener;
import com.kgv.cookbook.listener.IRecommendable;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2017/1/3 16:03
 * Email : 18627241899@163.com
 * Description : 主页-食谱->美食天下
 */
public class FoodWorldActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener, IOnMenuDeleteListener, IRecommendable {

    private ImageView iv_menu_switch;
    private EditText et_search;
    private ImageView iv_search;
    private GridView gv_left;
    private View v_line_left_1;
    private View v_line_left_2;
    private View v_line_right_1;
    private View v_line_right_2;
    public FoodWorldLeftAdapter leftAdapter;
    public ArrayList<String> ids;
    private TextView tv_left;
    private TextView tv_right;
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

    private FragmentManager fragmentManager;
    private RecommendFragment recommendFragment;
    private RecipesFragment recipesFragment;
    private GoCookingFragment goCookingFragment;
    private int currentFragment = 0;

    private LocalBroadcastManager localBroadcastManager;
    private LoginBroadcastReceiver receiver;
    private Runnable loadRunnable = new Runnable() {
        @Override
        public void run () {
            showFragment(0);
        }
    };
    private boolean isJump;


    @Override
    protected boolean hasBottomMenu () {
        return true;
    }

    @Override
    protected int getContentViewId () {
        return R.layout.activity_foodworld;
    }

    @Override
    protected void initialization (Bundle savedInstanceState) {
        isJump = getIntent().getBooleanExtra(IntentKeys.JUMP_FOOD_WORLD,false);
        materialOrRecipe = 1;
        ids = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        initUI();
        initListener();
        initReceiver();
        fillMenuData();
        getLeftDataFromNet(false);
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run () {
                myHandler.post(loadRunnable);
            }
        });
    }

    private void fillMenuData () {
        if (mUser.isExist()) {
            getMenuData(0);
            getMenuData(1);
            getMenuData(2);
        }
    }

    private void getMenuData (int type) {
        switch (type) {
            case 0:
                //早餐
                LogUtils.v("food", "早url : " + Urls.MENU_MORNING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/morning_menu/create_time/");
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
                LogUtils.v("food", "中url : " + Urls.MENU_NOONING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/nooning_menu/create_time/");
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
                LogUtils.v("food", "晚url : " + Urls.MENU_EVENING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/evening_menu/create_time/");
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

    private void initUI () {
        iv_menu_switch = (ImageView) findViewById(R.id.iv_menu_switch);
        v_line_left_1 = findViewById(R.id.v_line_left_1);
        v_line_left_2 = findViewById(R.id.v_line_left_2);
        v_line_right_1 = findViewById(R.id.v_line_right_1);
        v_line_right_2 = findViewById(R.id.v_line_right_2);
        et_search = (EditText) findViewById(R.id.et_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        gv_left = (GridView) findViewById(R.id.gv_left);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        ll_menu_content = (LinearLayout) findViewById(R.id.ll_menu_content);
        ll_menu = (LinearLayout) findViewById(R.id.ll_menu);
        tv_go_cooking = (TextView) findViewById(R.id.tv_go_cooking);
        lv_breakfast = (ListView) findViewById(R.id.lv_breakfast);
        lv_lunch = (ListView) findViewById(R.id.lv_lunch);
        lv_dinner = (ListView) findViewById(R.id.lv_dinner);
    }

    private void initListener () {
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        ll_menu.setOnClickListener(this);
        iv_search.setOnTouchListener(this);
        tv_go_cooking.setOnTouchListener(this);
        gv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                if (leftAdapter.isEditing()) {
                    //1.adapter删除数据 刷新UI
                    leftAdapter.deleteDataByPosition(position);
                    //2.发送定制请求
                    requestMadeSP(position, null);
                } else {
                    boolean reset = leftAdapter.setCurrentSelectedPosition(position);
                    if (reset){
                        recommendFragment.resetContentData();//恢复默认数据
                    }else{
                        refreshContent(leftAdapter.getItemIdByPosition(position), leftAdapter.getNameByPosition(position));
                    }
                }
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
        LogUtils.v("test", "food world 收到登录广播");
        fillMenuData();
        getLeftDataFromNet(false);
    }

    public void refreshContent (String id, String name) {
        if (currentFragment != 0) {
            showFragment(0);
        }
        recommendFragment.getCategory(id, name);
    }

    public void addShiPuToMade (String shipuId) {
        if (ids.contains(shipuId)) {
            tip("您已经定制了该食材");
            return;
        }
        //发送定制请求
        requestMadeSP(- 1, shipuId);
    }

    /**
     * 请求定制食谱
     * 如果delPosition为-1则addId有值
     * 如果addId为null则delPosition有值
     */
    private void requestMadeSP (int delPosition, String addId) {
        String data = "";
        if (TextUtils.isEmpty(addId)) {
            ids.remove(delPosition);
            for (int i = 0; i < ids.size(); i++) {
                if (i != ids.size() - 1) {
                    data += ids.get(i) + ",";
                } else {
                    data += ids.get(i);
                }
            }
            LogUtils.v("made sp ", "删除1个 当前食材id为:" + data);
        } else {
            ids.add(addId);
            for (int i = 0; i < ids.size(); i++) {
                if (i != ids.size() - 1) {
                    data += ids.get(i) + ",";
                } else {
                    data += ids.get(i);
                }
            }
            LogUtils.v("made sp ", "添加1个 当前食材id为:" + data);
        }
        mHttpUtils.doGet(Urls.MADE_SHIPU + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/data/" + data, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                //定制食谱成功后,重新请求定制列表,刷新UI,保持定制状态
                getLeftDataFromNet(true);
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    private void getLeftDataFromNet (final boolean keepEdit) {
        String url = mUser.isExist() ? Urls.HOT_SHIPU_CATE + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() : Urls.HOT_SHIPU_CATE;
        mHttpUtils.doGet(url, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                Gson gson = new Gson();
                List<HotShiPu> datas = gson.fromJson(
                        str,
                        new TypeToken<List<HotShiPu>>() {
                        }.getType());
                if (keepEdit) {
                    myHandler.obtainMessage(2, datas).sendToTarget();
                } else {
                    myHandler.obtainMessage(1, datas).sendToTarget();
                }
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    private void showMenu () {
        isMenuShowing = true;
        iv_menu_switch.setImageResource(R.drawable.menu_opened);
        ll_menu_content.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                ll_menu_content, "translationX",
                500, 450, 400, 350, 300, 250, 200, 150, 100, 50, 0);
        animator.setDuration(400);
        animator.start();
    }

    private void hideMenu () {
        isMenuShowing = false;
        iv_menu_switch.setImageResource(R.drawable.menu_closed);
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                ll_menu_content, "translationX",
                0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500);
        animator.setDuration(400);
        animator.start();
    }

    private void resetSP () {
        //1.发送请求恢复默认
        mHttpUtils.doGet(Urls.RESET_SHIPU + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword(), new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                //2.重新请求,刷新UI
                getLeftDataFromNet(false);
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.tv_left://定制食材
                if (! mUser.isExist()) {
                    showLoginDialog();
                    return;
                }
                lightLeft();
                if (currentFragment != 1) {
                    showFragment(1);
                }
                if (leftAdapter.isEditing()) {
                    hideLeft();
                    lightRight();
                    tv_right.setText("查看全部");
                } else {
                    lightLeft();
                    hideRight();
                    tv_right.setText("恢复默认");
                }
                leftAdapter.edit(! leftAdapter.isEditing());
                break;
            case R.id.tv_right: //查看全部
                if (currentFragment != 1) {
                    showFragment(1);
                }
                if (leftAdapter.isEditing()) {//恢复默认
                    tv_right.setText("查看全部");
                    resetSP();
                }
                hideLeft();
                lightRight();
                leftAdapter.edit(false);
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

    private void lightLeft () {
        v_line_left_1.setSelected(true);
        v_line_left_2.setSelected(true);
        tv_left.setSelected(true);
    }

    private void hideLeft () {
        v_line_left_1.setSelected(false);
        v_line_left_2.setSelected(false);
        tv_left.setSelected(false);
    }

    private void lightRight () {
        v_line_right_1.setSelected(true);
        v_line_right_2.setSelected(true);
        tv_right.setSelected(true);
    }

    private void hideRight () {
        v_line_right_1.setSelected(false);
        v_line_right_2.setSelected(false);
        tv_right.setSelected(false);
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

    public void showFragment (int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        hideFragments(transaction);

        switch (index) {
            case 0://推荐食谱
                if (leftAdapter != null) {
                    leftAdapter.edit(false);
                    v_line_left_1.setSelected(false);
                    v_line_left_2.setSelected(false);
                    v_line_right_1.setSelected(false);
                    v_line_right_2.setSelected(false);
                    tv_right.setText("查看全部");
                    tv_left.setSelected(false);
                    tv_right.setSelected(false);
                }
                currentFragment = 0;
                if (recommendFragment == null) {
                    recommendFragment = new RecommendFragment();
                    transaction.add(R.id.fl_right_content, recommendFragment);
                } else {
                    transaction.show(recommendFragment);
                }
                break;
            case 1://所有食谱分类
                currentFragment = 1;
                if (recipesFragment == null) {
                    recipesFragment = new RecipesFragment();
                    transaction.add(R.id.fl_right_content, recipesFragment);
                } else {
                    transaction.show(recipesFragment);
                }
                break;
            case 2://去制作
                currentFragment = 2;
                if (goCookingFragment == null) {
                    goCookingFragment = new GoCookingFragment();
                    transaction.add(R.id.fl_right_content, goCookingFragment);
                } else {
                    transaction.show(goCookingFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments (FragmentTransaction transaction) {
        if (recommendFragment != null) {
            transaction.hide(recommendFragment);
        }
        if (recipesFragment != null) {
            transaction.hide(recipesFragment);
        }
        if (goCookingFragment != null) {
            transaction.hide(goCookingFragment);
        }
    }

    private void goSearch () {
        String keyword = et_search.getText().toString().trim();
        if (TextUtils.isEmpty(keyword)) {
            tip("请输入食谱");
            return;
        }
        jumpActivity(new Intent(AppUtils.getContext(), SearchActivity.class)
                .putExtra(IntentKeys.SEARCH_KEYWORD, keyword), false);
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
            submitMenu2Blog();
        }
    }

    @Override
    public void addToMenu (ShiPu data, int type) {
        if (! mUser.isExist()) {
            showLoginDialog();
            return;
        }
        if (! isMenuShowing) {
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
                        + "/db_name/nooning_menu/cookbook_id/" + shiPu.getId() + "/act/add", new HttpResponse<String>(String.class) {
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
                        + "/db_name/evening_menu/cookbook_id/" + shiPu.getId() + "/act/add", new HttpResponse<String>(String.class) {
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
        NeedRefresh needRefresh = new NeedRefresh(String.class);
        switch (type) {
            case 0:
                mHttpUtils.doGet(Urls.MENU_MORNING_DELETE +
                        "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/morning_menu/id/" + shiPu.getDelId() + "/act/del", null);
                mHttpUtils.doGet(Urls.BLOG_MORNING_DELETE +
                        "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/morning_blog/id/" + shiPu.getDelId() + "/act/del", needRefresh);
                break;
            case 1:
                mHttpUtils.doGet(Urls.MENU_NOONING_DELETE +
                        "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/nooning_menu/id/" + shiPu.getDelId() + "/act/del", null);
                mHttpUtils.doGet(Urls.BLOG_NOONING_DELETE +
                        "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/nooning_blog/id/" + shiPu.getDelId() + "/act/del", needRefresh);
                break;
            case 2:
                mHttpUtils.doGet(Urls.MENU_EVENING_DELETE +
                        "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/evening_menu/id/" + shiPu.getDelId() + "/act/del", null);
                mHttpUtils.doGet(Urls.BLOG_EVENING_DELETE +
                        "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                        + "/db_name/evening_blog/id/" + shiPu.getDelId() + "/act/del", needRefresh);
                break;
        }
        if (isJump) {
            sendSubmit2BlogBroadcast();
        }
    }

    private void submitMenu2Blog () {
        mHttpUtils.doGet(Urls.MENU_SUBMIT_2_BLOG + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword(), new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String o) {
                myHandler.obtainMessage(6).sendToTarget();
            }

            @Override
            public void onError (String msg) {
                myHandler.obtainMessage(6).sendToTarget();
            }
        });
    }

    @Override
    protected void handleMsg1 (Message msg) {
        //不保持定制状态
        List<HotShiPu> datas = (List<HotShiPu>) msg.obj;
        ids = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            ids.add(datas.get(i).getId());
        }
        leftAdapter = new FoodWorldLeftAdapter(datas);
        gv_left.setAdapter(leftAdapter);
    }

    @Override
    protected void handleMsg2 (Message msg) {
        //保持定制状态
        List<HotShiPu> datas = (List<HotShiPu>) msg.obj;
        ids = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            ids.add(datas.get(i).getId());
        }
        leftAdapter = new FoodWorldLeftAdapter(datas);
        leftAdapter.edit(true);
        gv_left.setAdapter(leftAdapter);
        RAdapter fragmentAdapter = recipesFragment.adapter;
        fragmentAdapter.refreshFlags(ids);//刷新标记
    }

    @Override
    protected void handleMsg3 (Message msg) {
        //早餐菜单
        String json = (String) msg.obj;
        LogUtils.v("food", "早:" + json);
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
        LogUtils.v("food", "中:" + json);
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
        LogUtils.v("food", "晚:" + json);
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
    protected void handleMsg6 (Message msg) {
        if (isJump){
            sendSubmit2BlogBroadcast();
        }
        if (goCookingFragment != null) {
            goCookingFragment.refreshData();
        }
        showFragment(2);
    }

    @Override
    protected void handleMsg7 (Message msg) {
        if (goCookingFragment != null) {
            goCookingFragment.refreshData();
        }
    }
    private void sendSubmit2BlogBroadcast () {
        Intent intent = new Intent("com.kgv.cookbook.SUBMIT_TO_BLOG");
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onBackPressed () {
        super.onBackPressed();
        exit();
    }

    @Override
    protected void back () {
        if (currentFragment == 0) {
            exit();
        } else {
            if (leftAdapter.isEditing()) {
                leftAdapter.edit(false);
            }
            hideLeft();
            hideRight();
            showFragment(0);
        }
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        recommendFragment = null;
        recipesFragment = null;
        goCookingFragment = null;
        localBroadcastManager.unregisterReceiver(receiver);
    }

    private void exit () {
        this.finish();
        overridePendingTransition(R.anim.activity_in_left_2_right, R.anim.activity_out_left_2_right);
    }

    private class NeedRefresh extends HttpResponse<String> {

        public NeedRefresh (Class<String> stringClass) {
            super(stringClass);
        }

        @Override
        public void onSuccess (String str) {
            myHandler.obtainMessage(7).sendToTarget();
        }

        @Override
        public void onError (String msg) {
            myHandler.obtainMessage(7).sendToTarget();
        }
    }
}
