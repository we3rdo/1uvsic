package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.NameAndId;
import com.kgv.cookbook.holder.FamilyAgeHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/1/11 17:51
 * Email : 18627241899@163.com
 * Description :
 */
public class FamilyAgeAdapter extends FinalBaseAdapter<NameAndId> {

    public FamilyAgeAdapter(List<NameAndId> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new FamilyAgeHolder(parent);
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getId();
    }

}
