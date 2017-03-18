package com.kgv.cookbook.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.SearchResult;
import com.kgv.cookbook.holder.SearchResultHolder;
import com.kgv.cookbook.listener.IOnSubClickListener;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/1/2 16:52
 * Email : 18627241899@163.com
 * Description :
 */
public class SearchResultAdapter extends FinalBaseAdapter<SearchResult.ContentEntity> {

    private IOnSubClickListener listener;

    public SearchResultAdapter(List<SearchResult.ContentEntity> datas,IOnSubClickListener listener) {
        super(datas);
        this.listener = listener;
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new SearchResultHolder(parent);
    }

    @Override
    protected void handleSubBusiness(final int position, BaseHolder baseHolder) {
        SearchResultHolder holder = (SearchResultHolder) baseHolder;
        holder.tv_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSubClick(position,0);
            }
        });
        holder.tv_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSubClick(position,1);
            }
        });
        holder.tv_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSubClick(position,2);
            }
        });
    }


    public String getItemIdByPosition(int position) {
        return mDatas.get(position).getId();
    }

    public void replaceDataSet(List<SearchResult.ContentEntity> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void appendDataSet(List<SearchResult.ContentEntity> datas){
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }
}
