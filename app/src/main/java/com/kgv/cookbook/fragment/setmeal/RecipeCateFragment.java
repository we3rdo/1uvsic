package com.kgv.cookbook.fragment.setmeal;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.SetMealActivity;
import com.kgv.cookbook.adapter.SetMealRecipeCateAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.RecipeCategory;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.config.SpKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.listener.IOnChooseCateListener;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.SpUtils;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/20 17:25
 * Email : 18627241899@163.com
 * Description : 主页-食谱->美味套餐-食谱列表分类(1)
 */
public class RecipeCateFragment extends BaseFragment{

    private GridView gv_recipe;
    private TextView tv_title;
    private String currentSelectiveId;
    private SetMealRecipeCateAdapter adapter;
    private IOnChooseCateListener listener;

    public void setListener(IOnChooseCateListener listener){
        this.listener = listener;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_set_meal_recipe;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        initUI(contentView);

    }

    private void initUI(View contentView) {
        gv_recipe = (GridView) contentView.findViewById(R.id.gv_recipe);
        tv_title = (TextView) contentView.findViewById(R.id.tv_title);
        gv_recipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSelectiveId = adapter.getItemIdByPosition(position);
                adapter.refreshFlag(currentSelectiveId);
                if (listener != null){
                    listener.onChooseCate(currentSelectiveId);
                }
            }
        });
    }

    @Override
    protected void initOnActivityCreated(Bundle savedInstanceState) {
        SetMealActivity activity = (SetMealActivity) getActivity();
        getDataFromCache(activity.currentCateId);
    }

    public void getDataFromCache(String id) {
        String str = SpUtils.getString(AppUtils.getContext(), SpKeys.FOODWORLD_R_LIST_JSON + id);
        if (TextUtils.isEmpty(str)) {
            getDataFromNet(id);
        } else {
            if (System.currentTimeMillis() - SpUtils.getLong(AppUtils.getContext(),
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

    @Override
    protected void handleMsg0(Message msg) {
        RecipeCategory cBean = (RecipeCategory) msg.obj;
        tv_title.setText(cBean.getTitle());
        List<RecipeCategory.ChildrenEntity> datas = cBean.getChildren();

        adapter = new SetMealRecipeCateAdapter(datas);
        gv_recipe.setAdapter(adapter);
        if (!TextUtils.isEmpty(currentSelectiveId)){
            adapter.refreshFlag(currentSelectiveId);
        }
    }
}
