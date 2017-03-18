package com.kgv.cookbook.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.RecipeCategory;
import com.kgv.cookbook.holder.TextAndSoftLineHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/13 11:08
 * Email : 18627241899@163.com
 * Description :
 */
public class TextAndSoftLineAdapter extends FinalBaseAdapter<RecipeCategory.ChildrenEntity> {

    public TextAndSoftLineAdapter(List<RecipeCategory.ChildrenEntity> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new TextAndSoftLineHolder(parent);
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getId();
    }

    public String getItemNameByPosition(int position){
        return mDatas.get(position).getTitle();
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder baseHolder) {
        TextAndSoftLineHolder holder = (TextAndSoftLineHolder) baseHolder;
        holder.vv.setVisibility(position == 3 ? View.INVISIBLE : View.VISIBLE);
    }
}
