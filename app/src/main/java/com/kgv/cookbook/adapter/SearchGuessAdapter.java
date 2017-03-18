package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.SearchResult;
import com.kgv.cookbook.holder.SearchGuessHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/1/3 9:46
 * Email : 18627241899@163.com
 * Description :
 */
public class SearchGuessAdapter extends FinalBaseAdapter<SearchResult.RecommendEntity> {


    public SearchGuessAdapter(List<SearchResult.RecommendEntity> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new SearchGuessHolder(parent);
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getId();
    }
}
