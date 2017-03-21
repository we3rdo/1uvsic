package com.kgv.cookbook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.AllMaterialActivity;
import com.kgv.cookbook.activity.FoodWorldActivity;
import com.kgv.cookbook.activity.RecipeActivity;
import com.kgv.cookbook.adapter.RecommendAdapter;
import com.kgv.cookbook.adapter.TabooAdapter;
import com.kgv.cookbook.adapter.TextAndSoftLineAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.base.BaseOnTouchListener;
import com.kgv.cookbook.bean.GroupBean;
import com.kgv.cookbook.bean.RecipeCategory;
import com.kgv.cookbook.bean.SearchResult;
import com.kgv.cookbook.bean.ShiPu;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.SpKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.listener.IAutoLoadListener;
import com.kgv.cookbook.listener.IOnSubClickListener;
import com.kgv.cookbook.listener.IRecommendable;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;
import com.kgv.cookbook.util.SpUtils;
import com.nineoldandroids.animation.Animator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/6 13:05
 * Email : 18627241899@163.com
 * Description : 主页-食谱->食材大全&美食天下-推荐页面(0)
 */
public class RecommendFragment extends BaseFragment implements View.OnClickListener, IOnSubClickListener {

    private GridView gv_content;
    private RecommendAdapter recommendAdapter;
    private TextAndSoftLineAdapter functionAdapter;
    private TextAndSoftLineAdapter sicknessAdapter;
    //功能调理
    private RelativeLayout fl_function;
    private GridView gv_function;
    private boolean isShowFunction = false;
    private boolean hasFunctionData = false;
    //疾病调理
    private RelativeLayout fl_sickness;
    private GridView gv_sickness;
    private boolean isShowSickness = false;
    private boolean hasSicknessData = false;
    private Gson gson;
    private int normalPage = 1;
    private LinearLayout ll_loadMore;
    private boolean normalLoadMore = true; //滑动时加载普通推荐数据
    /*  食材大全 */
    private AllMaterialActivity allMaterialActivity;
    private int mateGroupPage;
    private String mateGroupUrl;
    private LinearLayout ll_taboo;
    private ListView lv_taboo;
    private TextView tv_taboo_title;
    private TextView tvAllMateTip;
    private ImageView iv_reset;
    /*  美食天下 */
    private int recipeCatePage;
    private TextView tvFoodWorldTip;
    private String title;
    private String cateId;
    private FoodWorldActivity foodWorldActivity;
    private boolean noMoreRecipeCateData;


    @Override
    protected void initOnActivityCreated (Bundle savedInstanceState) {
        if (baseActivity.materialOrRecipe == 0) {
            init4AllMaterial();
        } else {
            init4FoodWorld();
        }
    }

    @Override
    protected int getContentViewId () {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initialization (View contentView, Bundle savedInstanceState) {
        gson = new Gson();
        initUI(contentView);
        //请求推荐食谱
        getRecommendFromNet(normalPage, false);
    }

    private void initUI (View contentView) {
        ll_loadMore = (LinearLayout) contentView.findViewById(R.id.ll_loadMore);
        gv_content = (GridView) contentView.findViewById(R.id.gv_content);
        IAutoLoadListener listener = new IAutoLoadListener(new IAutoLoadListener.AutoLoadCallBack() {
            @Override
            public void execute () {
                if (normalLoadMore) {
                    normalPage += 1;
                    getRecommendFromNet(normalPage, true);
                } else {
                    if (foodWorldActivity == null) {
                        //食材大全
                        if (! TextUtils.isEmpty(mateGroupUrl)) {
                            mateGroupPage += 1;
                            appendGroupData();
                        }
                    } else {
                        //美食天下
                        recipeCatePage += 1;
                        appendCategoryData();
                    }
                }
            }
        });
        gv_content.setOnScrollListener(listener);
        gv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                String spid = recommendAdapter.getItemIdByPosition(position);
                Intent intent = new Intent(getContext(), RecipeActivity.class);
                intent.putExtra(IntentKeys.SHIPU_ID, spid);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
            }
        });
    }

    private void init4AllMaterial () {
        allMaterialActivity = (AllMaterialActivity) baseActivity;
        iv_reset = (ImageView) baseActivity.findViewById(R.id.iv_reset);
        tvAllMateTip = (TextView) baseActivity.findViewById(R.id.tv_tip);
        ll_taboo = (LinearLayout) baseActivity.findViewById(R.id.ll_taboo);
        ll_taboo.setVisibility(View.VISIBLE);
        tv_taboo_title = (TextView) baseActivity.findViewById(R.id.tv_taboo_title);
        lv_taboo = (ListView) baseActivity.findViewById(R.id.lv_taboo);
        iv_reset.setOnTouchListener(new BaseOnTouchListener() {
            @Override
            protected void onViewTouch (View v) {
                if (lv_taboo.getVisibility() == View.VISIBLE){
                    lv_taboo.setVisibility(View.GONE);
                }
                if (tv_taboo_title.getVisibility() == View.VISIBLE){
                    tv_taboo_title.setVisibility(View.GONE);
                }
                allMaterialActivity.clearGroupData();
                resetContentData();
            }
        });
    }

    private void init4FoodWorld () {
        foodWorldActivity = (FoodWorldActivity) baseActivity;
        tvFoodWorldTip = (TextView) baseActivity.findViewById(R.id.tvTip);
        LinearLayout ll_recipe_cate = (LinearLayout) baseActivity.findViewById(R.id.ll_recipe_cate);
        ll_recipe_cate.setVisibility(View.VISIBLE);
        baseActivity.findViewById(R.id.tv_function).setOnClickListener(this);
        baseActivity.findViewById(R.id.tv_sickness).setOnClickListener(this);
        fl_function = (RelativeLayout) baseActivity.findViewById(R.id.fl_function);
        gv_function = (GridView) baseActivity.findViewById(R.id.gv_function);
        gv_function.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                String cid = functionAdapter.getItemIdByPosition(position);
                getCategory(cid, functionAdapter.getItemNameByPosition(position));
                hideFunction();
            }
        });
        fl_sickness = (RelativeLayout) baseActivity.findViewById(R.id.fl_sickness);
        gv_sickness = (GridView) baseActivity.findViewById(R.id.gv_sickness);
        gv_sickness.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                String cid = sicknessAdapter.getItemIdByPosition(position);
                getCategory(cid, sicknessAdapter.getItemNameByPosition(position));
                hideSickness();
            }
        });
    }

    //重置数据
    public void resetContentData () {
        //开启加载更多推荐数据
        normalLoadMore = true;
        normalPage = 1;
        //食谱分类&组合食材 初始化为第一页
        recipeCatePage = 1;
        mateGroupPage = 1;
        getRecommendFromNet(normalPage, false);
        if (foodWorldActivity != null) {
            YoYo.with(Techniques.FadeOut)
                    .duration(700)
                    .withListener(new HideViewOnAnimEnd(tvFoodWorldTip))
                    .playOn(tvFoodWorldTip);
        }else{
            tvAllMateTip.setVisibility(View.GONE);
            iv_reset.setVisibility(View.GONE);
            if (lv_taboo.getVisibility() == View.VISIBLE){
                lv_taboo.setVisibility(View.GONE);
            }
            if (tv_taboo_title.getVisibility() == View.VISIBLE){
                tv_taboo_title.setVisibility(View.GONE);
            }
        }
    }

    //食材大全 追加组合食材数据
    private void appendGroupData () {
        LogUtils.v("recommend", "追加组合食材数据 : " + mateGroupUrl + mateGroupPage);
        httpUtils.doGet(mateGroupUrl + mateGroupPage, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                handler.obtainMessage(6, str).sendToTarget();
            }

            @Override
            public void onError (String msg) {

            }
        });
    }


    //美食天下 请求食谱分类
    public void getCategory (String cid, String name) {
        title = name;
        cateId = cid;//每次点击保存分类id
        recipeCatePage = 1;//每次点击重置分类页数
        normalLoadMore = false;//取消滑动加载推荐数据
        LogUtils.v("recommend 请求食谱分类", Urls.RECIPE_CATEGORY_CONTENT + cid + "/p/" + recipeCatePage);
        httpUtils.doGet(Urls.RECIPE_CATEGORY_CONTENT + cid + "/p/" + recipeCatePage, new HttpResponse<SearchResult>(SearchResult.class) {
            @Override
            public void onSuccess (SearchResult bean) {
                List<SearchResult.ContentEntity> contents = bean.getContent();
                ArrayList<ShiPu> datas = new ArrayList<>();
                for (int i = 0; i < contents.size(); i++) {
                    datas.add(new ShiPu(contents.get(i).getId(), contents.get(i).getImage_thumb(),
                            contents.get(i).getImage(), contents.get(i).getName()));
                }
                handler.obtainMessage(3, datas).sendToTarget();
            }

            @Override
            public void onError (String msg) {
                handler.obtainMessage(4).sendToTarget();
            }
        });
    }

    //美食天下 追加食谱分类
    private void appendCategoryData () {
        if (! noMoreRecipeCateData) {
            ll_loadMore.setVisibility(View.VISIBLE);
        }
        LogUtils.v("recommend 追加食谱分类", Urls.RECIPE_CATEGORY_CONTENT + cateId + "/p/" + recipeCatePage);
        httpUtils.doGet(Urls.RECIPE_CATEGORY_CONTENT + cateId + "/p/" + recipeCatePage, new HttpResponse<SearchResult>(SearchResult.class) {
            @Override
            public void onSuccess (SearchResult bean) {
                List<SearchResult.ContentEntity> contents = bean.getContent();
                ArrayList<ShiPu> datas = new ArrayList<>();
                for (int i = 0; i < contents.size(); i++) {
                    datas.add(new ShiPu(contents.get(i).getId(), contents.get(i).getImage_thumb(),
                            contents.get(i).getImage(), contents.get(i).getName()));
                }
                handler.obtainMessage(7, datas).sendToTarget();
            }

            @Override
            public void onError (String msg) {
                handler.obtainMessage(8).sendToTarget();
            }
        });
    }


    //请求推荐数据
    private void getRecommendFromNet (final int page, final boolean append) {
        if (append) {
            ll_loadMore.setVisibility(View.VISIBLE);
        }
        LogUtils.v("recommend", "推荐数据 : " + Urls.RECOMMEND_RECIPE + page);
        httpUtils.doGet(Urls.RECOMMEND_RECIPE + page, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                SpUtils.saveString(AppUtils.getContext(), SpKeys.ALL_RECOMMEND_JSON + page, str);
                SpUtils.saveLong(AppUtils.getContext(), SpKeys.ALL_RECOMMEND_TIME + page, System.currentTimeMillis());
                List<ShiPu> datas = gson.fromJson(str, new TypeToken<List<ShiPu>>() {
                }.getType());
                if (append) {
                    handler.obtainMessage(5, datas).sendToTarget();
                } else {
                    handler.obtainMessage(2, datas).sendToTarget();
                }
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    public void refreshData4GroupMaterial (List<ShiPu> recipes, String url) {
        normalLoadMore = false;//取消加载推荐数据
        mateGroupPage = 1;
        recommendAdapter.replaceDatas(recipes);
        mateGroupUrl = url;
    }

    public void setAllMateTitleTip (String text) {
        tvAllMateTip.setText(text);
        tvAllMateTip.setVisibility(View.VISIBLE);
        iv_reset.setVisibility(View.VISIBLE);
    }

    public void setTaboos (List<GroupBean.TabooEntity> taboos) {
        ArrayList<String> tips = new ArrayList<>();
        for (int i = 0; i < taboos.size(); i++) {
            List<GroupBean.TabooEntity.SubTabooEntity> subTaboos = taboos.get(i).getTaboo();
            if (subTaboos != null && subTaboos.size() > 0) {
                String p = taboos.get(i).getName();
                for (int j = 0; j < subTaboos.size(); j++) {
                    String k = subTaboos.get(j).getName();
                    String des = subTaboos.get(j).getEffect();
                    StringBuilder sb = new StringBuilder("      " + p)
                            .append("与")
                            .append(k)
                            .append("不宜组合食用，易引起")
                            .append(des)
                            .append("等不适。");
                    tips.add(sb.toString());
                }
            }
        }
        if (tips.size() > 0) {
            lv_taboo.setVisibility(View.VISIBLE);
            tv_taboo_title.setVisibility(View.VISIBLE);
            lv_taboo.setAdapter(new TabooAdapter(tips));
            YoYo.with(Techniques.BounceIn)
                    .duration(700)
                    .playOn(ll_taboo);
        } else {
            lv_taboo.setVisibility(View.GONE);
            tv_taboo_title.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.tv_function:
                if (! hasFunctionData) {
                    //请求功能调理数据
                    getFunction();
                    hasFunctionData = true;
                }
                if (isShowSickness) {
                    hideSickness();
                }
                if (isShowFunction) {
                    hideFunction();
                } else {
                    showFunction();
                }
                break;
            case R.id.tv_sickness:
                if (! hasSicknessData) {
                    //请求疾病调理数据
                    getSickness();
                    hasSicknessData = true;
                }
                if (isShowFunction) {
                    hideFunction();
                }
                if (isShowSickness) {
                    hideSickness();
                } else {
                    showSickness();
                }
                break;
        }
    }

    @Override   //点击加入菜单
    public void onSubClick (int position, int type) {
        ((IRecommendable) baseActivity).addToMenu((ShiPu) recommendAdapter.getItem(position), type);
    }

    /**
     * 处理功能调理数据
     */
    @Override
    protected void handleMsg0 (Message msg) {
        List<RecipeCategory.ChildrenEntity> children = (List<RecipeCategory.ChildrenEntity>) msg.obj;
        functionAdapter = new TextAndSoftLineAdapter(children);
        gv_function.setAdapter(functionAdapter);
    }

    /**
     * 处理疾病调理数据
     */
    @Override
    protected void handleMsg1 (Message msg) {
        List<RecipeCategory.ChildrenEntity> children = (List<RecipeCategory.ChildrenEntity>) msg.obj;
        sicknessAdapter = new TextAndSoftLineAdapter(children);
        gv_sickness.setAdapter(sicknessAdapter);
    }

    /**
     * 处理推荐食谱
     */
    @Override   //初次加载推荐数据
    protected void handleMsg2 (Message msg) {
        List<ShiPu> datas = (List<ShiPu>) msg.obj;
        recommendAdapter = new RecommendAdapter(datas, this);
        gv_content.setAdapter(recommendAdapter);
        /**
         *  下载推荐食谱图片
         */
        //        Intent intent = new Intent(getContext(), DownLoadService.class);
        //        intent.putExtra("download_img", (Serializable) datas);
        //        getActivity().startService(intent);

        //        if(allMaterialActivity != null){
        //            allMaterialActivity.startListen();
        //        }
    }

    @Override   //追加普通推荐数据
    protected void handleMsg5 (Message msg) {
        ll_loadMore.setVisibility(View.GONE);
        List<ShiPu> datas = (List<ShiPu>) msg.obj;
        recommendAdapter.appendData(datas);
    }


    @Override   //美食天下 有分类数据
    protected void handleMsg3 (Message msg) {
        recommendAdapter.replaceDatas((List<ShiPu>) msg.obj);
        String name = "当前分类：" + title;
        tvFoodWorldTip.setText(changeColor(name));
        if (tvFoodWorldTip.getVisibility() != View.VISIBLE) {
            tvFoodWorldTip.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.FadeIn)
                    .duration(700)
                    .playOn(tvFoodWorldTip);
        }

    }

    @Override   //美食天下 无该分类数据
    protected void handleMsg4 (Message msg) {
        tip("抱歉，暂无“" + title + "”分类食谱");
        foodWorldActivity.leftAdapter.setCurrentSelectedPosition(1005);
        normalLoadMore = true;
    }

    @Override   //美食天下 追加分类数据
    protected void handleMsg7 (Message msg) {
        ll_loadMore.setVisibility(View.GONE);
        recommendAdapter.appendData((List<ShiPu>) msg.obj);
    }

    @Override
    protected void handleMsg8 (Message msg) {
        ll_loadMore.setVisibility(View.GONE);
        if (! noMoreRecipeCateData) {
            tip("没有更多数据了");
        }
        noMoreRecipeCateData = true;
    }

    @Override
    protected void handleMsg6 (Message msg) {
        String response = (String) msg.obj;
        if (! TextUtils.isEmpty(response)) {
            GroupBean bean = gson.fromJson(response, GroupBean.class);
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
                recommendAdapter.appendData(recipes);
            } else {
                tip("没有更多数据了");
            }
        } else {
            tip("没有更多数据了");
        }
    }

    private void showFunction () {
        isShowFunction = true;
        AlphaAnimation alpha = new AlphaAnimation(0.2f, 1.0f);
        alpha.setDuration(400);
        fl_function.setVisibility(View.VISIBLE);
        fl_function.startAnimation(alpha);
    }

    private void hideFunction () {
        isShowFunction = false;
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(400);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart (Animation animation) {

            }

            @Override
            public void onAnimationEnd (Animation animation) {
                fl_function.clearAnimation();
                fl_function.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat (Animation animation) {

            }
        });
        fl_function.startAnimation(alpha);
    }

    private void showSickness () {
        isShowSickness = true;
        AlphaAnimation alpha = new AlphaAnimation(0.2f, 1.0f);
        alpha.setDuration(400);
        fl_sickness.setVisibility(View.VISIBLE);
        fl_sickness.startAnimation(alpha);
    }

    private void hideSickness () {
        isShowSickness = false;
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(400);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart (Animation animation) {

            }

            @Override
            public void onAnimationEnd (Animation animation) {
                fl_sickness.clearAnimation();
                fl_sickness.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat (Animation animation) {

            }
        });
        fl_sickness.startAnimation(alpha);
    }

    @NonNull
    private SpannableStringBuilder changeColor (String name) {
        SpannableStringBuilder builder = new SpannableStringBuilder(name);
        ForegroundColorSpan blackSpan = new ForegroundColorSpan(AppUtils.getColor(R.color.bg_color_black));
        ForegroundColorSpan coffeeSpan = new ForegroundColorSpan(AppUtils.getColor(R.color.a));
        builder.setSpan(blackSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(coffeeSpan, 5, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    private void getFunction () {
        String json = SpUtils.getString(getContext(), SpKeys.ALL_FUNCTION_JSON);
        if (TextUtils.isEmpty(json)) {
            getFunctionFromNet();
        } else {
            if (System.currentTimeMillis() - SpUtils.getLong(getContext(), SpKeys.ALL_FUNCTION_TIME) > SpKeys.OUT_TIME) {
                getFunctionFromNet();
            } else {
                RecipeCategory cBean = gson.fromJson(json, RecipeCategory.class);
                List<RecipeCategory.ChildrenEntity> children = cBean.getChildren();
                handler.obtainMessage(0, children).sendToTarget();
            }
        }
    }

    private void getFunctionFromNet () {
        httpUtils.doGet(Urls.FUNCTION_G, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                SpUtils.saveString(AppUtils.getContext(), SpKeys.ALL_FUNCTION_JSON, str);
                SpUtils.saveLong(AppUtils.getContext(), SpKeys.ALL_FUNCTION_TIME, System.currentTimeMillis());
                RecipeCategory cBean = gson.fromJson(str, RecipeCategory.class);
                List<RecipeCategory.ChildrenEntity> children = cBean.getChildren();
                handler.obtainMessage(0, children).sendToTarget();
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    private void getSickness () {
        String json = SpUtils.getString(getContext(), SpKeys.ALL_SICKNESS_JSON);
        if (TextUtils.isEmpty(json)) {
            getSicknessFromNet();
        } else {
            if (System.currentTimeMillis() - SpUtils.getLong(getContext(), SpKeys.ALL_SICKNESS_TIME) > SpKeys.OUT_TIME) {
                getSicknessFromNet();
            } else {
                RecipeCategory cBean = gson.fromJson(json, RecipeCategory.class);
                List<RecipeCategory.ChildrenEntity> children = cBean.getChildren();
                handler.obtainMessage(1, children).sendToTarget();
            }
        }
    }

    private void getSicknessFromNet () {
        httpUtils.doGet(Urls.SICKNESS_G, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                SpUtils.saveString(AppUtils.getContext(), SpKeys.ALL_SICKNESS_JSON, str);
                SpUtils.saveLong(AppUtils.getContext(), SpKeys.ALL_SICKNESS_TIME, System.currentTimeMillis());
                RecipeCategory cBean = gson.fromJson(str, RecipeCategory.class);
                List<RecipeCategory.ChildrenEntity> children = cBean.getChildren();
                handler.obtainMessage(1, children).sendToTarget();
            }

            @Override
            public void onError (String msg) {

            }
        });
    }

    private void getRecommend (int page, boolean append) {
        LogUtils.v("listener", "第" + page + "页");
        String json = SpUtils.getString(getContext(), SpKeys.ALL_RECOMMEND_JSON + page);
        if (TextUtils.isEmpty(json)) {
            getRecommendFromNet(page, append);
        } else {
            if (System.currentTimeMillis() - SpUtils.getLong(getContext(), SpKeys.ALL_RECOMMEND_TIME + page) > SpKeys.OUT_TIME) {
                getRecommendFromNet(page, append);
            } else {
                List<ShiPu> datas = gson.fromJson(json, new TypeToken<List<ShiPu>>() {
                }.getType());
                if (append) {
                    handler.obtainMessage(5, datas).sendToTarget();
                } else {
                    handler.obtainMessage(2, datas).sendToTarget();
                }
            }
        }
    }

    private class HideViewOnAnimEnd implements Animator.AnimatorListener {

        private View view;

        public HideViewOnAnimEnd (View view) {
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
}
