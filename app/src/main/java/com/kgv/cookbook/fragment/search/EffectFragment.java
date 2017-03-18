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
 * Description : 搜索页-功效(3)
 */
public class EffectFragment extends BaseFragment {

    private GridView gv_effect;
    private SearchActivity activity;
    private SearchTextAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_search_effect;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        initUI(contentView);
    }

    private void initUI(View contentView) {
        gv_effect = (GridView) contentView.findViewById(R.id.gv_effect);
        gv_effect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String effect = adapter.getItemIdByPosition(position);
                activity.conditionalSearch(null,null,null,effect);
            }
        });
    }

    @Override
    protected void initOnActivityCreated(Bundle savedInstanceState) {
        activity = (SearchActivity) baseActivity;
        if (activity.bean != null){
            adapter = new SearchTextAdapter(activity.bean.getEffect_sort());
            gv_effect.setAdapter(adapter);
        }
    }

    public void refreshContent(){
        adapter.replaceDataSet(activity.bean.getEffect_sort());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
