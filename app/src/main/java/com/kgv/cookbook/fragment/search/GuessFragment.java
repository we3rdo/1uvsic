package com.kgv.cookbook.fragment.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.RecipeActivity;
import com.kgv.cookbook.activity.SearchActivity;
import com.kgv.cookbook.adapter.SearchResultAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.SearchResult;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.listener.IAutoLoadListener;
import com.kgv.cookbook.listener.IOnSubClickListener;

/**
 * Created by 陈可轩 on 2017/1/2 11:44
 * Email : 18627241899@163.com
 * Description : 搜索页-猜你想找(4)[搜索结果]
 */
public class GuessFragment extends BaseFragment implements IOnSubClickListener {

    private GridView gv_guess;
    private SearchResultAdapter adapter;
    private SearchActivity activity;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_search_guess;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        initUI(contentView);
    }

    private void initUI(View contentView) {
        gv_guess = (GridView) contentView.findViewById(R.id.gv_guess);
        IAutoLoadListener listener = new IAutoLoadListener(new IAutoLoadListener.AutoLoadCallBack() {
            @Override
            public void execute() {
                activity.onListScroll2Bottom();
            }
        });
        gv_guess.setOnScrollListener(listener);
    }

    @Override
    protected void initOnActivityCreated(Bundle savedInstanceState) {
        activity = (SearchActivity) baseActivity;
        SearchResult bean = activity.bean;
        if (bean != null){
            adapter = new SearchResultAdapter(bean.getContent(),this);
            gv_guess.setAdapter(adapter);
            gv_guess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String spid = adapter.getItemIdByPosition(position);
                    Intent intent = new Intent(getContext(), RecipeActivity.class);
                    intent.putExtra(IntentKeys.SHIPU_ID,spid);
                    getActivity().startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.activity_in_right_2_left,R.anim.activity_out_left_2_right);
                }
            });
        }

    }

    public void refreshContent(){
        adapter.replaceDataSet(activity.bean.getContent());
    }

    public void appendData(){
        adapter.appendDataSet(activity.bean.getContent());
    }

    @Override
    public void onSubClick(int position, int type) {
        adapter.getItem(position);
        ((SearchActivity) baseActivity).addToMenu((SearchResult.ContentEntity) adapter.getItem(position),type);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
