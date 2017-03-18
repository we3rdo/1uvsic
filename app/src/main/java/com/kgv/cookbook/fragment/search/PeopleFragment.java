package com.kgv.cookbook.fragment.search;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.SearchActivity;
import com.kgv.cookbook.adapter.SearchPeopleAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.SearchResult;

/**
 * Created by 陈可轩 on 2017/1/2 11:44
 * Email : 18627241899@163.com
 * Description : 搜索页-人数(2)
 */
public class PeopleFragment extends BaseFragment {

    private GridView gv_people;
    private SearchActivity activity;
    private SearchPeopleAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_search_people;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        initUI(contentView);
    }

    private void initUI(View contentView) {
        gv_people = (GridView) contentView.findViewById(R.id.gv_people);
        gv_people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int people = (int) adapter.getItem(position);
                activity.conditionalSearch(people+"",null,null,null);
            }
        });
    }

    @Override
    protected void initOnActivityCreated(Bundle savedInstanceState) {
        activity = (SearchActivity) baseActivity;
        SearchResult bean = activity.bean;
        if (bean != null){
            adapter = new SearchPeopleAdapter(bean.getPeople_num());
            gv_people.setAdapter(adapter);
        }
    }

    public void refreshContent(){
        SearchResult bean = activity.bean;
        adapter.replaceDataSet(bean.getPeople_num());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
