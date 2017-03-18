package com.kgv.cookbook.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.HotShiPu;
import com.kgv.cookbook.holder.FoodWorldLeftHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/13 11:08
 * Email : 18627241899@163.com
 * Description : 主页-食谱->美食天下:左边食谱列表
 */
public class FoodWorldLeftAdapter extends FinalBaseAdapter<HotShiPu> {

    private boolean inEditing;
    private int currentSelectedPosition = 1005;

    public FoodWorldLeftAdapter(List<HotShiPu> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new FoodWorldLeftHolder(parent);
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder baseHolder) {
        FoodWorldLeftHolder holder = (FoodWorldLeftHolder) baseHolder;
        holder.tv_name.setSelected(position == currentSelectedPosition);
        holder.iv_edit.setVisibility(inEditing ? View.VISIBLE : View.INVISIBLE);
    }

    public boolean setCurrentSelectedPosition (int position) {
        if (currentSelectedPosition == 1005){//第一次点击,不重置
            currentSelectedPosition = position;
            notifyDataSetChanged();
            return false;
        }else{
            boolean needReset = position == currentSelectedPosition;
            currentSelectedPosition = needReset ? -1 : position;
            notifyDataSetChanged();
            return needReset;
        }
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getId();
    }

    public String getNameByPosition(int position){
        return mDatas.get(position).getTitle();
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

}
