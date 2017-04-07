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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kgv.cookbook.R;
import com.kgv.cookbook.adapter.AllLeftAdapter;
import com.kgv.cookbook.adapter.AllMenuAdapter;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.bean.AddToMenuShipu;
import com.kgv.cookbook.bean.GroupBean;
import com.kgv.cookbook.bean.MenuAndBlogShiPu;
import com.kgv.cookbook.bean.ShiCai;
import com.kgv.cookbook.bean.ShiPu;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.fragment.GoCookingFragment;
import com.kgv.cookbook.fragment.MaterialsFragment;
import com.kgv.cookbook.fragment.RecommendFragment;
import com.kgv.cookbook.listener.IOnMenuDeleteListener;
import com.kgv.cookbook.listener.IRecommendable;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/3 17:50
 * Email : 18627241899@163.com
 * Description : 主页-食谱->食材大全
 */
public class AllMaterialActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener, IOnMenuDeleteListener, IRecommendable {

    private EditText et_search;
    private ImageView iv_menu_switch;
    private ImageView iv_search;
    private GridView gv_left;
    private TextView tv_left;
    private TextView tv_right;
    private View v_line_left_1;
    private View v_line_left_2;
    private View v_line_right_1;
    private View v_line_right_2;
    private LinearLayout ll_menu;
    private FragmentManager fragmentManager;
    private RecommendFragment recommendFragment;
    private MaterialsFragment materialsFragment;
    private GoCookingFragment gocookingFragment;
    private LinearLayout ll_menu_content;
    private boolean isMenuShowing = false;
    private TextView tv_go_cooking;
    private int currentFragment = 0;
    public AllLeftAdapter leftAdapter;
    public ArrayList<String> ids;
    private ListView lv_breakfast;
    private ListView lv_lunch;
    private ListView lv_dinner;
    private AllMenuAdapter mAdapter;
    private AllMenuAdapter aAdapter;
    private AllMenuAdapter nAdapter;
    private ArrayList<String> groupIds = new ArrayList<>();
    private ArrayList<String> groupNames = new ArrayList<>();
    private String groupUrl;
    private RelativeLayout rl_search_mate;
    private GridView gv_search_mate;
    private ImageView iv_search_delete;
    private boolean isShowSearchResult;
    private String keyword;
    private AllLeftAdapter searchAdapter;
    private Runnable loadRunnable = new Runnable() {
        @Override
        public void run () {
            showFragment(0);
        }
    };
    private LocalBroadcastManager localBroadcastManager;
    private LoginBroadcastReceiver receiver;
    private boolean isJump;

    @Override
    protected boolean hasBottomMenu () {
        return true;
    }

    @Override
    protected int getContentViewId () {
        return R.layout.activity_all_material;
    }

    @Override
    protected void initialization (Bundle savedInstanceState) {
        isJump = getIntent().getBooleanExtra(IntentKeys.JUMP_ALL_MATE, false);
        flag = 0;
        materialOrRecipe = 0;
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

    private void initUI () {
        rl_search_mate = (RelativeLayout) findViewById(R.id.rl_search_mate);
        gv_search_mate = (GridView) findViewById(R.id.gv_search_mate);
        iv_search_delete = (ImageView) findViewById(R.id.iv_search_delete);
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
        //findViewById(R.id.ll_left).setOnTouchListener();
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        ll_menu.setOnClickListener(this);
        iv_search.setOnTouchListener(this);
        iv_search_delete.setOnTouchListener(this);
        tv_go_cooking.setOnTouchListener(this);
        gv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                if (leftAdapter.isEditing()) {
                    //1.adapter删除数据 刷新UI
                    leftAdapter.deleteDataByPosition(position);
                    //2.发送定制请求
                    requestMadeSC(position, null, true);
                } else {
                    String scId = leftAdapter.getItemIdByPosition(position);
                    String name = leftAdapter.getItemNameByPosition(position);
                    boolean needReset = leftAdapter.setSelectIds(scId);
                    if (needReset) {
                        groupNames.clear();
                        groupIds.clear();
                        recommendFragment.resetContentData();
                    } else {
                        getGroupMateData(scId, name);
                    }
                }
            }
        });
        gv_search_mate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                hideSearchResult();
                if (! mUser.isExist()) {
                    showLoginDialog();
                    return;
                }
                String scid = searchAdapter.getItemIdByPosition(position);
                requestMadeSC(- 1, scid, false);
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
        LogUtils.v("test", "all mate 收到登录广播");
        fillMenuData();
        getLeftDataFromNet(false);
        clearGroupData();
        recommendFragment.resetContentData();
    }

    public void clearGroupData () {
        groupNames.clear();
        groupIds.clear();
        leftAdapter.clearSelectIds();
    }

    public void startListen () {
        mHelper.init(this, new BaseVoiceListener(), new BaseDialogListener());
        myHandler.sendEmptyMessage(88);
    }

    @Override
    protected void subClassVoiceBusiness (List<String> result, String words) {
        if ("搜索食材".equals(words)) {
            searchMaterial();
        }
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
                LogUtils.v("uuuu","url = " + Urls.MENU_MORNING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/morning_menu/create_time/");
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


    public void getGroupMateData (String scId, String name) {
        if (groupIds.contains(scId)) {
            if (groupIds.size() > 1)
                groupIds.remove(scId);
            if (groupNames.size() > 1)
                groupNames.remove(name);
        } else {
            groupIds.add(scId);
            groupNames.add(name);
        }
        String data = "";
        for (int i = 0; i < groupIds.size(); i++) {
            if (i != groupIds.size() - 1) {
                data += groupIds.get(i) + "_";
            } else {
                data += groupIds.get(i);
            }
        }
        groupUrl = Urls.GROUP_MARERIAL.replace("-", data);
        LogUtils.v("hahaha", "组合食材url : " + groupUrl + 1);
        mHttpUtils.doGet(groupUrl + 1, new HttpResponse<GroupBean>(GroupBean.class) {
            @Override
            public void onSuccess (GroupBean bean) {
                myHandler.obtainMessage(7, bean).sendToTarget();
            }

            @Override
            public void onError (String msg) {
                myHandler.obtainMessage(11).sendToTarget();
            }
        });
    }

    /**
     * 请求定制食材
     * 如果delPosition为-1则addId有值
     * 如果addId为null则delPosition有值
     */

    private void requestMadeSC (int delPosition, String addId, final boolean keepEdit) {
        String data = "";
        if (delPosition != - 1) {
            ids.remove(delPosition);
        } else {
            ids.add(addId);
        }
        for (int i = 0; i < ids.size(); i++) {
            if (i != ids.size() - 1) {
                data += ids.get(i) + ",";
            } else {
                data += ids.get(i);
            }
        }
        mHttpUtils.doGet(Urls.MADE_SHICAI + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/data/" + data, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                //2.重新请求 刷新UI 保持定制状态
                getLeftDataFromNet(keepEdit);
            }

            @Override
            public void onError (String msg) {
            }
        });
    }

    public void addShiCaiToMade (String shicaiID) {
        if (ids.contains(shicaiID)) {
            tip("您已经定制了该食材");
            return;
        }
        //1.发送定制请求
        requestMadeSC(- 1, shicaiID, true);
    }

    private void getLeftDataFromNet (final boolean keepEdit) {
        String url = mUser.isExist() ? Urls.HOT_SHICAI + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() : Urls.HOT_SHICAI;
        mHttpUtils.doGet(url, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                Gson gson = new Gson();
                List<ShiCai> datas = gson.fromJson(
                        str,
                        new TypeToken<List<ShiCai>>() {
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

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.tv_left://定制食材
                if (! mUser.isExist()) {
                    showLoginDialog();
                    return;
                }
                lightLeft();    //高亮左按钮
                clearGroupData(); //清除组合食材
                recommendFragment.resetContentData();//刷新数据
                if (currentFragment != 1) {
                    showFragment(1);
                }
                if (leftAdapter.isEditing()) {  //取消定制
                    hideLeft();
                    lightRight();
                    tv_right.setText("查看全部");
                } else {            //开始定制
                    lightLeft();
                    hideRight();
                    tv_right.setText("恢复默认");
                }
                leftAdapter.edit(! leftAdapter.isEditing());
                break;
            case R.id.tv_right://查看全部
                if (currentFragment != 1) {
                    showFragment(1);
                }
                if (leftAdapter.isEditing()) {//恢复默认
                    tv_right.setText("查看全部");
                    resetSC();
                }
                hideLeft();
                lightRight();
                leftAdapter.edit(false);
                break;
            case R.id.ll_menu:
                //右侧菜单
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

    private void resetSC () {
        //1.发送请求恢复默认
        mHttpUtils.doGet(Urls.RESET_SHICAI + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword(), new HttpResponse<String>(String.class) {
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
    public boolean onTouch (View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(0.4f);
                break;
            case MotionEvent.ACTION_UP:
                v.setAlpha(1.0f);
                switch (v.getId()) {
                    case R.id.iv_search:
                        searchMaterial();
                        break;
                    case R.id.tv_go_cooking:
                        goCooking();
                        break;
                    case R.id.iv_search_delete:
                        hideSearchResult();
                        break;
                }
                break;
        }
        return true;
    }

    private void searchMaterial () {
        AppUtils.hideKeyboard(et_search);
        if (isShowSearchResult) {
            searchAgain();
        } else {
            goSearch();
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

    private void goSearch () {
        keyword = et_search.getText().toString().trim();
        if (TextUtils.isEmpty(keyword)) {
            tip("请输入食材");
            return;
        }
        mHttpUtils.doGet(Urls.SEARCH_SHICAI + keyword, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                if (! TextUtils.isEmpty(str) && ! "null".equals(str)) {
                    Gson gson = new Gson();
                    List<ShiCai> datas = gson.fromJson(str, new TypeToken<List<ShiCai>>() {
                    }.getType());
                    myHandler.obtainMessage(0, datas).sendToTarget();
                } else {
                    myHandler.obtainMessage(9, keyword).sendToTarget();
                }
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    private void searchAgain () {
        String again = et_search.getText().toString().trim();
        if (TextUtils.isEmpty(again)) {
            tip("请输入食材");
            return;
        }
        if (! again.equals(keyword)) {
            keyword = again;
            mHttpUtils.doGet(Urls.SEARCH_SHICAI + keyword, new HttpResponse<String>(String.class) {
                @Override
                public void onSuccess (String str) {
                    if (! TextUtils.isEmpty(str) && ! "null".equals(str)) {
                        Gson gson = new Gson();
                        List<ShiCai> datas = gson.fromJson(str, new TypeToken<List<ShiCai>>() {
                        }.getType());
                        myHandler.obtainMessage(8, datas).sendToTarget();
                    } else {
                        myHandler.obtainMessage(9, keyword).sendToTarget();
                    }

                }

                @Override
                public void onError (String msg) {

                }
            });
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
            submitMenu2Blog();
        }
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

    private void showFragment (int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        hideFragments(transaction);

        switch (index) {
            case 0://推荐食谱
                if (leftAdapter != null) {
                    leftAdapter.edit(false);
                    tv_right.setText("查看全部");
                }
                currentFragment = 0;
                if (recommendFragment == null) {
                    recommendFragment = new RecommendFragment();
                    transaction.add(R.id.fl_right_content, recommendFragment);
                } else {
                    transaction.show(recommendFragment);
                }
                break;
            case 1://所有食材分类
                currentFragment = 1;
                if (materialsFragment == null) {
                    materialsFragment = new MaterialsFragment();
                    transaction.add(R.id.fl_right_content, materialsFragment);
                } else {
                    transaction.show(materialsFragment);
                }
                break;
            case 2://去制作
                currentFragment = 2;
                if (gocookingFragment == null) {
                    gocookingFragment = new GoCookingFragment();
                    transaction.add(R.id.fl_right_content, gocookingFragment);
                } else {
                    transaction.show(gocookingFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments (FragmentTransaction transaction) {
        if (recommendFragment != null) {
            transaction.hide(recommendFragment);
        }
        if (materialsFragment != null) {
            transaction.hide(materialsFragment);
        }
        if (gocookingFragment != null) {
            transaction.hide(gocookingFragment);
        }
    }

    /**
     * 添加食谱到菜单 type:0早 1中 2晚
     */
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

    private void showSearchResult () {
        isShowSearchResult = true;
        ObjectAnimator animator = ObjectAnimator.ofFloat(rl_search_mate, "translationY",
                - 600, - 500, - 400, - 300, - 200, - 100, 0);
        animator.setDuration(500);
        animator.start();
    }

    private void hideSearchResult () {
        isShowSearchResult = false;
        ObjectAnimator animator = ObjectAnimator.ofFloat(rl_search_mate, "translationY",
                0, - 100, - 200, - 300, - 400, - 500, - 600);
        animator.setDuration(500);
        animator.start();
    }

    @Override
    protected void handleMsg0 (Message msg) {
        List<ShiCai> datas = (List<ShiCai>) msg.obj;
        searchAdapter = new AllLeftAdapter(datas);
        gv_search_mate.setAdapter(searchAdapter);
        rl_search_mate.setVisibility(View.VISIBLE);
        showSearchResult();
    }

    @Override
    protected void handleMsg1 (Message msg) {
        //填充左边数据,不保持定制状态
        List<ShiCai> datas = (List<ShiCai>) msg.obj;
        ids = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            ids.add(datas.get(i).getId());
        }
        leftAdapter = new AllLeftAdapter(datas);
        gv_left.setAdapter(leftAdapter);
    }

    @Override
    protected void handleMsg2 (Message msg) {
        //填充左边数据,保持定制状态
        List<ShiCai> datas = (List<ShiCai>) msg.obj;
        ids = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            ids.add(datas.get(i).getId());
        }
        leftAdapter = new AllLeftAdapter(datas);
        leftAdapter.edit(true);
        gv_left.setAdapter(leftAdapter);
        materialsFragment.refreshAllMateFlags(ids);
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
    protected void handleMsg6 (Message msg) {
        if (isJump) {
            sendSubmit2BlogBroadcast();
        }
        if (gocookingFragment != null) {
            gocookingFragment.refreshData();
        }
        showFragment(2);
    }

    @Override
    protected void handleMsg7 (Message msg) {
        String name = getGroupNames();
        GroupBean bean = (GroupBean) msg.obj;
        if (currentFragment != 0) {
            showFragment(0);
            hideRight();
        }
        List<GroupBean.ContentEntity> contents = bean.getContent();
        if (contents != null && contents.size() > 0) {
            ArrayList<ShiPu> recipes = new ArrayList<>();
            for (int i = 0; i < contents.size(); i++) {
                ShiPu shipu = new ShiPu(
                        contents.get(i).getId(),
                        contents.get(i).getImage_thumb(),
                        contents.get(i).getImage(),
                        contents.get(i).getName());
                recipes.add(shipu);
            }
            String title = TextUtils.isEmpty(name) ? groupNames.get(0) + "：" : name + "  组合结果：";
            recommendFragment.setAllMateTitleTip(title);
            recommendFragment.refreshData4GroupMaterial(recipes, groupUrl);
        } else {
            tip("暂无" + name + "相关食谱");
            groupNames.clear();
        }
        List<GroupBean.TabooEntity> taboos = bean.getTaboo();
        if (taboos != null && taboos.size() > 0) {
            recommendFragment.setTaboos(taboos);
        }
    }

    @Override
    protected void handleMsg11 (Message msg) {
        String name = getGroupNames();
        String tip = TextUtils.isEmpty(name) ? groupNames.get(0) : name;
        tip("暂无“" + tip + "”相关食谱");
        groupNames.clear();
    }

    @Override
    protected void handleMsg8 (Message msg) {
        List<ShiCai> datas = (List<ShiCai>) msg.obj;
        searchAdapter = new AllLeftAdapter(datas);
        gv_search_mate.setAdapter(searchAdapter);
    }

    @Override
    protected void handleMsg9 (Message msg) {
        String keyword = (String) msg.obj;
        tip("暂无“" + keyword + "”相关食材");
    }

    @Override
    protected void handleMsg10 (Message msg) {
        if (gocookingFragment != null) {
            gocookingFragment.refreshData();
        }
    }

    private void sendSubmit2BlogBroadcast () {
        Intent intent = new Intent("com.kgv.cookbook.SUBMIT_TO_BLOG");
        localBroadcastManager.sendBroadcast(intent);
    }

    private String getGroupNames () {
        String str = "";
        if (groupNames.size() == 1) {
            return str;
        }
        for (int i = 0; i < groupNames.size(); i++) {
            if (i == groupNames.size() - 1) {
                str += "“" + groupNames.get(i) + "”";
            } else {
                str += "“" + groupNames.get(i) + "”、";
            }
        }
        return str;
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
    public void onBackPressed () {
        super.onBackPressed();
        back();
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
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
            myHandler.obtainMessage(10).sendToTarget();
        }

        @Override
        public void onError (String msg) {
            myHandler.obtainMessage(10).sendToTarget();
        }
    }

}
