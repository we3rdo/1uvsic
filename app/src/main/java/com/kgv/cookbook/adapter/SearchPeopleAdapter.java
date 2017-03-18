package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.holder.SearchPeopleHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/1/2 16:37
 * Email : 18627241899@163.com
 * Description :
 */
public class SearchPeopleAdapter extends FinalBaseAdapter<Integer> {

    public SearchPeopleAdapter(List<Integer> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new SearchPeopleHolder(parent);
    }

    public void replaceDataSet(List<Integer> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }
}
