package com.kgv.cookbook.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.RecipeCategory;
import com.kgv.cookbook.holder.RHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/13 11:08
 * Email : 18627241899@163.com
 * Description :
 */
public class RAdapter extends FinalBaseAdapter<RecipeCategory.ChildrenEntity> {

    private ArrayList<String> ids;

    public RAdapter(List<RecipeCategory.ChildrenEntity> datas, ArrayList<String> ids) {
        super(datas);
        this.ids = ids;
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new RHolder(parent);
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder baseHolder) {
        RHolder holder = (RHolder) baseHolder;
        holder.iv_ok.setVisibility(ids.contains(getItemIdByPosition(position)) ? View.VISIBLE : View.GONE);
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getId();
    }

    public String getItemNameByPosition(int position){
        return mDatas.get(position).getTitle();
    }

    public void refreshFlags(ArrayList<String> ids){
        this.ids = ids;
        notifyDataSetChanged();
    }

    public void replaceData(List<RecipeCategory.ChildrenEntity> data){
        mDatas = data;
        notifyDataSetChanged();
    }

}
