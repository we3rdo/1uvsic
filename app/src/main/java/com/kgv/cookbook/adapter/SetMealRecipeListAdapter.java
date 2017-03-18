package com.kgv.cookbook.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.SetMealDetailBean;
import com.kgv.cookbook.holder.SetMealRecipeListHolder;
import com.kgv.cookbook.listener.IOnSubClickListener;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/27 14:56
 * Email : 18627241899@163.com
 * Description :
 */
public class SetMealRecipeListAdapter extends FinalBaseAdapter<SetMealDetailBean> {

    private IOnSubClickListener listener;

    public SetMealRecipeListAdapter(List<SetMealDetailBean> datas, IOnSubClickListener listener) {
        super(datas);
        this.listener = listener;
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new SetMealRecipeListHolder(parent);
    }

    @Override
    protected void handleSubBusiness(final int position, BaseHolder holder) {
        SetMealRecipeListHolder recipeListHolder = (SetMealRecipeListHolder) holder;
        recipeListHolder.tv_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSubClick(position, 0);
            }
        });
        recipeListHolder.tv_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSubClick(position, 1);
            }
        });
        recipeListHolder.tv_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSubClick(position, 2);
            }
        });
    }

    public String getItemIdByPosition(int position) {
        return mDatas.get(position).getId();
    }

    public SetMealDetailBean getItemByPosition(int position) {
        return mDatas.get(position);
    }
}
