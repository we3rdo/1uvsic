package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.SearchResult;
import com.kgv.cookbook.holder.SearchTextHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/1/2 15:59
 * Email : 18627241899@163.com
 * Description :
 */
public class SearchTextAdapter extends FinalBaseAdapter<SearchResult.NameAndIdEntity> {

    public SearchTextAdapter(List<SearchResult.NameAndIdEntity> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new SearchTextHolder(parent);
    }

    public void replaceDataSet(List<SearchResult.NameAndIdEntity> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void appendDataSet(List<SearchResult.NameAndIdEntity> datas){
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getId();
    }
}
