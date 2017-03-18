package com.kgv.cookbook.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.ShiCai;
import com.kgv.cookbook.holder.AllLeftHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/13 11:08
 * Email : 18627241899@163.com
 * Description : 主页-食谱->食材大全:左边食材列表
 */
public class AllLeftAdapter extends FinalBaseAdapter<ShiCai> {

    private boolean inEditing;
    private ArrayList<String> selectIds = new ArrayList<>();

    public AllLeftAdapter(List<ShiCai> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new AllLeftHolder(parent);
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder baseHolder) {
        AllLeftHolder holder = (AllLeftHolder) baseHolder;
        holder.iv_edit.setVisibility(inEditing ? View.VISIBLE : View.INVISIBLE);
        holder.tv_name.setSelected(selectIds.contains(getItemIdByPosition(position)));
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getId();
    }

    //开始订制or取消定制
    public void edit(boolean isEdit){
        inEditing = isEdit;
        notifyDataSetChanged();
    }

    //是否正在定制食材
    public boolean isEditing(){
        return inEditing;
    }

    public boolean setSelectIds(String ids){
        boolean needReset = false;
        if (selectIds.contains(ids)){
            if (selectIds.size() == 1){
                needReset = true;
            }
            selectIds.remove(ids);
        }else{
            selectIds.add(ids);
        }
        notifyDataSetChanged();
        return needReset;
    }

    public void clearSelectIds(){
        selectIds.clear();
        notifyDataSetChanged();
    }

    public String getItemNameByPosition(int position) {
        return mDatas.get(position).getName();
    }
}
