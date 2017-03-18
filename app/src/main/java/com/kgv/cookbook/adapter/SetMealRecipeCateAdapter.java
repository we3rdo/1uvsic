package com.kgv.cookbook.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.RecipeCategory;
import com.kgv.cookbook.holder.SetMealRecipeCateHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/21 10:38
 * Email : 18627241899@163.com
 * Description :
 */
public class SetMealRecipeCateAdapter extends FinalBaseAdapter<RecipeCategory.ChildrenEntity> {

    private String selectiveId;

    public SetMealRecipeCateAdapter(List<RecipeCategory.ChildrenEntity> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new SetMealRecipeCateHolder(parent);
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder baseHolder) {
        SetMealRecipeCateHolder holder = (SetMealRecipeCateHolder) baseHolder;
        holder.iv_ok.setVisibility(getItemIdByPosition(position).equals(selectiveId) ? View.VISIBLE : View.GONE);
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getId();
    }

    public void refreshFlag(String id){
        this.selectiveId = id;
        notifyDataSetChanged();
    }



}
