package com.kgv.cookbook.fragment.search;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.SearchActivity;
import com.kgv.cookbook.adapter.SearchTextAdapter;
import com.kgv.cookbook.base.BaseFragment;

/**
 * Created by 陈可轩 on 2017/1/2 11:44
 * Email : 18627241899@163.com
 * Description : 搜索页-工艺(0)
 */
public class GyFragment extends BaseFragment {

    private GridView gv_gy;
    private SearchTextAdapter adapter;
    private SearchActivity activity;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_search_gy;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        initUI(contentView);
    }

    private void initUI(View contentView) {
        gv_gy = (GridView) contentView.findViewById(R.id.gv_gy);
        gv_gy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String gongyi = adapter.getItemIdByPosition(position);
                activity.conditionalSearch(null,gongyi,null,null);
            }
        });
    }

    @Override
    protected void initOnActivityCreated(Bundle savedInstanceState) {
        activity = (SearchActivity) baseActivity;
        if (activity.bean != null){
            adapter = new SearchTextAdapter(activity.bean.getGongyi_sort());
            gv_gy.setAdapter(adapter);
        }
    }

    public void refreshContent(){
        adapter.replaceDataSet(activity.bean.getGongyi_sort());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
