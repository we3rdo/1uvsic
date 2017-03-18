package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.SearchResult;

/**
 * Created by 陈可轩 on 2017/1/3 9:47
 * Email : 18627241899@163.com
 * Description :
 */
public class SearchGuessHolder extends BaseHolder<SearchResult.RecommendEntity> {

    private TextView tv_name;

    public SearchGuessHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_search_guess,null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        return view;
    }

    @Override
    public void bindingData(SearchResult.RecommendEntity data, ViewGroup parent) {
        tv_name.setText(data.getName());
    }
}
