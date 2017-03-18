package com.kgv.cookbook.fragment.interest;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kgv.cookbook.CookbookApplication;
import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.InterestSettingActivity;
import com.kgv.cookbook.adapter.RAdapter;
import com.kgv.cookbook.adapter.TitleAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.InterestFlag;
import com.kgv.cookbook.bean.RecipeCategory;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.config.SpKeys;
import com.kgv.cookbook.config.Strings;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/11 11:47
 * Email : 18627241899@163.com
 * Description : 主页-设置->喜好设置-食谱分类(0)
 */
public class InterestRecipesFragment extends BaseFragment {

    private TextView tv_title;
    private GridView gv_content;
    public RAdapter adapter;
    private ArrayList<String> dislikes;
    private TitleAdapter titleAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_interest_recipes;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        initUI(contentView);
        show("3");
    }

    private void initUI(View contentView) {
        tv_title = (TextView) contentView.findViewById(R.id.tv_title);
        gv_content = (GridView) contentView.findViewById(R.id.gv_content);
        GridView gv_title = (GridView) contentView.findViewById(R.id.gv_title);
        titleAdapter = new TitleAdapter(Strings.getRecipes());
        gv_title.setAdapter(titleAdapter);
        gv_title.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                titleAdapter.selectItem(position);
                show(titleAdapter.getItemIdByPosition(position));
            }
        });
    }

    @Override
    protected void initOnActivityCreated (Bundle savedInstanceState) {
        final InterestSettingActivity activity = (InterestSettingActivity) baseActivity;
        gv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (! CookbookApplication.getUser().isExist()) {
                    activity.showLDG();
                    return;
                }
                String recipeId = adapter.getItemIdByPosition(position);
                String data = splitParams(recipeId);
                httpUtils.doGet(Urls.INTEREST_DISLIKE_RECIPES_SET +
                        "username/" + mUser.getUsername() +
                        "/password/" + mUser.getPassword() +
                        "/field_name/like_cookbook_category/data/" + data, new HttpResponse<String>(String.class) {
                    @Override
                    public void onSuccess(String str) {
                        //重新请求dislike
                        getDislikeRecipes();
                    }

                    @Override
                    public void onError(String msg) {

                    }
                });
            }
        });
    }

    @NonNull
    private String splitParams(String recipeId) {
        String data = "";
        if (dislikes.contains(recipeId)) {
            dislikes.remove(recipeId);
        } else {
            dislikes.add(recipeId);
        }
        for (int i = 0; i < dislikes.size(); i++) {
            if (i != dislikes.size() - 1) {
                data += dislikes.get(i) + ",";
            } else {
                data += dislikes.get(i);
            }
        }
        return data;
    }

    private void show(String id) {
        String str = SpUtils.getString(getContext(), SpKeys.FOODWORLD_R_LIST_JSON + id);
        if (TextUtils.isEmpty(str)) {
            getDataFromNet(id);
        } else {
            if (System.currentTimeMillis() - SpUtils.getLong(getContext(),
                    SpKeys.FOODWORLD_R_LIST_TIME + id) > SpKeys.OUT_TIME) {
                getDataFromNet(id);
            } else {
                RecipeCategory cBean = gson.fromJson(str, RecipeCategory.class);
                handler.obtainMessage(0, cBean).sendToTarget();
            }
        }
    }

    private void getDataFromNet(final String id) {
        httpUtils.doGet(Urls.SHIPU_CATE_BASE_URL + id, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                SpUtils.saveString(AppUtils.getContext(), SpKeys.FOODWORLD_R_LIST_JSON + id, str);
                SpUtils.saveLong(AppUtils.getContext(), SpKeys.FOODWORLD_R_LIST_TIME + id, System.currentTimeMillis());
                RecipeCategory cBean = gson.fromJson(str, RecipeCategory.class);
                handler.obtainMessage(0, cBean).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private void getDislikeRecipes() {
        httpUtils.doGet(Urls.INTEREST_DISLIKE_RECIPES_LIST
                + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                + "/field_name/like_cookbook_category/", new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                if (!"[]".equals(str)) {
                    List<InterestFlag> flags = gson.fromJson(str, new TypeToken<List<InterestFlag>>() {
                    }.getType());
                    handler.obtainMessage(1, flags).sendToTarget();
                }
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    @Override
    protected void handleMsg0(Message msg) {
        RecipeCategory cBean = (RecipeCategory) msg.obj;
        tv_title.setText(cBean.getTitle());
        List<RecipeCategory.ChildrenEntity> datas = cBean.getChildren();
        adapter = new RAdapter(datas, new ArrayList<String>());
        gv_content.setAdapter(adapter);
        getDislikeRecipes();
    }

    @Override
    protected void handleMsg1(Message msg) {
        List<InterestFlag> flags = (List<InterestFlag>) msg.obj;
        dislikes = new ArrayList<>();
        for (int i = 0; i < flags.size(); i++) {
            dislikes.add(flags.get(i).getId());
        }
        adapter.refreshFlags(dislikes);
    }
}
