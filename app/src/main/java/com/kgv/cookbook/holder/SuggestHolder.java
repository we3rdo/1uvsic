package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.SuggestNutrition;

/**
 * Created by 陈可轩 on 2017/2/23 17:04
 * Email : 18627241899@163.com
 * Description :
 */
public class SuggestHolder extends BaseHolder<SuggestNutrition.RecipeEntity> {

    public TextView tv_name;
    public TextView tv_value;

    public SuggestHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_suggest,null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_value = (TextView) view.findViewById(R.id.tv_value);
        return view;
    }

    @Override
    public void bindingData(SuggestNutrition.RecipeEntity data, ViewGroup parent) {
    }
}
