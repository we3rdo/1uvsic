package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;

/**
 * Created by 陈可轩 on 2017/2/5 11:59
 * Email : 18627241899@163.com
 * Description :
 */
public class RecipeStepHolder extends BaseHolder<String> {

    public TextView tv_position;
    public TextView tv_name;

    public RecipeStepHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_recipe_step,null);
        tv_position = (TextView) view.findViewById(R.id.tv_position);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        return view;
    }

    @Override
    public void bindingData(String data, ViewGroup parent) {

    }
}
