package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.SuggestNutrition;
import com.kgv.cookbook.holder.SuggestHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/23 17:03
 * Email : 18627241899@163.com
 * Description :
 */
public class SuggestAdapter extends FinalBaseAdapter<SuggestNutrition.RecipeEntity> {

    private String unit;

    public SuggestAdapter(List<SuggestNutrition.RecipeEntity> datas,String unit) {
        super(datas);
        this.unit = unit;
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new SuggestHolder(parent);
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder baseHolder) {
        SuggestHolder holder = (SuggestHolder) baseHolder;
        holder.tv_name.setText(position + 1 + "." + mDatas.get(position).getName());
        holder.tv_value.setText(mDatas.get(position).getNutrition_val() + unit);
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getCookbook_id();
    }
}
