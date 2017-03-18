package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.holder.TabooHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/25 14:26
 * Email : 18627241899@163.com
 * Description :
 */
public class TabooAdapter extends FinalBaseAdapter<String>{

    public TabooAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new TabooHolder(parent);
    }
}
