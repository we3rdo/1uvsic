package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.NameAndValue;
import com.kgv.cookbook.holder.RecipeDetailMHolder;

import java.util.List;

/**
 *  Created by ckx on 2017/4/8.
 */
public class RecipeDetailMAdapter extends FinalBaseAdapter<NameAndValue> {

    public RecipeDetailMAdapter(List<NameAndValue> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new RecipeDetailMHolder(parent);
    }
}
