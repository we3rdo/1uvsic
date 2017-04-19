package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.NameAndValue;

/**
 *       Created by ckx on 2017/4/8.
 */
public class RecipeDetailMHolder extends BaseHolder<NameAndValue> {

    private TextView tv_name;
    private TextView tv_value;

    public RecipeDetailMHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_recipe_detail,null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_value = (TextView) view.findViewById(R.id.tv_value);
        return view;
    }

    @Override
    public void bindingData(NameAndValue data, ViewGroup parent) {
        tv_name.setText(data.getName());
        tv_value.setText(data.getValue());
    }
}
