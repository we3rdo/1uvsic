package com.kgv.cookbook.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.NameAndId;
import com.kgv.cookbook.holder.SetMealTitleHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/20 17:06
 * Email : 18627241899@163.com
 * Description :
 */
public class SetMealTitleAdapter extends FinalBaseAdapter<NameAndId> {

    private int onlyShowPosition = -1;
    private int currentSelectivePosition = -1;

    public SetMealTitleAdapter(List<NameAndId> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new SetMealTitleHolder(parent);
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder baseHolder) {
        SetMealTitleHolder holder = (SetMealTitleHolder) baseHolder;
        holder.tv_name.setSelected(position == currentSelectivePosition);
        holder.line.setSelected(position == currentSelectivePosition);
        holder.father.setVisibility(onlyShowPosition == -1 ? View.VISIBLE : View.GONE);
        if (position == onlyShowPosition){
            holder.father.setVisibility(View.VISIBLE);
        }
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getId();
    }

    public void setCurrentSelectivePosition(int currentSelectivePosition) {
        this.currentSelectivePosition = currentSelectivePosition;
        notifyDataSetChanged();
    }

    public int getCurrentSelectivePosition() {
        return currentSelectivePosition;
    }

    public void showOnly(int position) {
        onlyShowPosition = position;
        currentSelectivePosition = position;
        notifyDataSetChanged();
    }

    public void showAll(){
        onlyShowPosition = -1;
        notifyDataSetChanged();
    }
}
