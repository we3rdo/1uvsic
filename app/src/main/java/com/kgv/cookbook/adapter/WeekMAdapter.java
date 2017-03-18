package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.holder.WeekMHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/20 10:44
 * Email : 18627241899@163.com
 * Description :
 */
public class WeekMAdapter extends FinalBaseAdapter<String> {

    public WeekMAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new WeekMHolder(parent);
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder baseHolder) {
        WeekMHolder holder = (WeekMHolder) baseHolder;
        holder.tv_name.setText(position + 1 + "." + mDatas.get(position));
    }
}
