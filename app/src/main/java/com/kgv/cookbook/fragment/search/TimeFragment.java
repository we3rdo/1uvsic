package com.kgv.cookbook.fragment.search;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.SearchActivity;
import com.kgv.cookbook.adapter.SearchTextAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.SearchResult;
import com.kgv.cookbook.util.LogUtils;

/**
 * Created by 陈可轩 on 2017/1/2 11:44
 * Email : 18627241899@163.com
 * Description : 搜索页-耗时(1)
 */
public class TimeFragment extends BaseFragment {

    private GridView gv_time;
    private SearchActivity activity;
    private SearchTextAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_search_time;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        initUI(contentView);
    }

    private void initUI(View contentView) {
        gv_time = (GridView) contentView.findViewById(R.id.gv_time);
        gv_time.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String haoshi = adapter.getItemIdByPosition(position);
                activity.conditionalSearch(null,null,haoshi,null);
            }
        });
    }

    @Override
    protected void initOnActivityCreated(Bundle savedInstanceState) {
        activity = (SearchActivity) baseActivity;
        SearchResult bean = activity.bean;
        if (bean != null){
            adapter = new SearchTextAdapter(bean.getTime_sort());
            gv_time.setAdapter(adapter);
        }
    }

    public void refreshContent(){
        SearchResult bean = activity.bean;
        adapter.replaceDataSet(bean.getTime_sort());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
