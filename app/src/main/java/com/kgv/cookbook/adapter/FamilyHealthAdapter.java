package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.RecipeCategory;
import com.kgv.cookbook.holder.FamilyHealthHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/1/12 11:11
 * Email : 18627241899@163.com
 * Description :
 */
public class FamilyHealthAdapter extends FinalBaseAdapter<RecipeCategory.ChildrenEntity> {

    public FamilyHealthAdapter(List<RecipeCategory.ChildrenEntity> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new FamilyHealthHolder(parent);
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getId();
    }
}
