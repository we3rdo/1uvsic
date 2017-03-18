package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.holder.WeekLeftHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/20 13:46
 * Email : 18627241899@163.com
 * Description :
 */
public class WeekLeftAdapter extends FinalBaseAdapter<String> {

    public WeekLeftAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new WeekLeftHolder(parent);
    }

}