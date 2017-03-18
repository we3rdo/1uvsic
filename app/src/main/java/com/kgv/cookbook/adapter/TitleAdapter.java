package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.NameAndId;
import com.kgv.cookbook.holder.TitleHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/11 10:16
 * Email : 18627241899@163.com
 * Description :
 */
public class TitleAdapter extends FinalBaseAdapter<NameAndId> {

    private int selected = 0;

    public TitleAdapter(List<NameAndId> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new TitleHolder(parent);
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder baseHolder) {
        TitleHolder holder = (TitleHolder) baseHolder;
        holder.tv_name.setSelected(position == selected);
        holder.line.setSelected(position == selected);
    }

    public String getItemIdByPosition(int position) {
        return mDatas.get(position).getId();
    }

    public void selectItem(int position) {
        selected = position;
        notifyDataSetChanged();
    }
}
