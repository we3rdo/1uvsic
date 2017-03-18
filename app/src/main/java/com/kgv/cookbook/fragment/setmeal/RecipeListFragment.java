package com.kgv.cookbook.fragment.setmeal;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.reflect.TypeToken;
import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.SetMealActivity;
import com.kgv.cookbook.adapter.SetMealRecipeListAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.SetMealDetailBean;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.listener.IOnSubClickListener;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/27 14:32
 * Email : 18627241899@163.com
 * Description : 主页-食谱->美味套餐-食谱列表(2)
 */
public class RecipeListFragment extends BaseFragment implements IOnSubClickListener {

    private GridView gv_recipe;
    private SetMealRecipeListAdapter adapter;
    private SetMealActivity activity;

    @Override
    protected int getContentViewId () {
        return R.layout.fragment_set_meal_recipe_list;
    }

    @Override
    protected void initialization (View contentView, Bundle savedInstanceState) {
        initUI(contentView);
    }

    private void initUI (View contentView) {
        gv_recipe = (GridView) contentView.findViewById(R.id.gv_recipe);
        gv_recipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                activity.onGoRecipeActivity(adapter.getItemIdByPosition(position));
            }
        });
    }

    @Override
    protected void initOnActivityCreated (Bundle savedInstanceState) {
        activity = (SetMealActivity) baseActivity;
        if (activity.currentSetMeal != null) {
            getDetail(activity.currentSetMeal.getId());
        }
    }

    private void getDetail (String id) {
        LogUtils.v("setmeal","detailUrl = " + Urls.SET_MEAL_DETAIL + id);
        httpUtils.doGet(Urls.SET_MEAL_DETAIL + id, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess (String str) {
                handler.obtainMessage(0, str).sendToTarget();
            }

            @Override
            public void onError (String msg) {

            }
        });
    }


    @Override
    public void onSubClick (int position, int type) {
        activity.addToMenu(adapter.getItemByPosition(position), type);
    }

    public void refreshData () {
        SetMealActivity activity = (SetMealActivity) getActivity();
        String id = activity.currentSetMeal.getId();
        getDetail(id);
    }

    @Override
    protected void handleMsg0 (Message msg) {
        String str = (String) msg.obj;
        if (! TextUtils.isEmpty(str) || ! "[]".equals(str)) {
            List<SetMealDetailBean> list = gson.fromJson(str, new TypeToken<List<SetMealDetailBean>>() {}.getType());
            adapter = new SetMealRecipeListAdapter(list, this);
            gv_recipe.setAdapter(adapter);
        }
    }
}
