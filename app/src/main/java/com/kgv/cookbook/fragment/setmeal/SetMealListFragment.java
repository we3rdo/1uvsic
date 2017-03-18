package com.kgv.cookbook.fragment.setmeal;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.SetMealActivity;
import com.kgv.cookbook.adapter.SetMealListAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.SetMealBean;
import com.kgv.cookbook.listener.IAutoLoadListener;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/20 17:29
 * Email : 18627241899@163.com
 * Description : 主页-食谱->美味套餐-套餐列表(0)
 */
public class SetMealListFragment extends BaseFragment {

    private GridView gv_list;
    private SetMealListAdapter adapter;
    private SetMealActivity activity;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_set_meal_list;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        initUI(contentView);
    }

    private void initUI(View contentView) {
        gv_list = (GridView) contentView.findViewById(R.id.gv_list);
        gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (activity != null){
                    activity.onSetMealItemClick(adapter.getItemByPosition(position));
                }
            }
        });
        IAutoLoadListener autoLoadListener = new IAutoLoadListener(new IAutoLoadListener.AutoLoadCallBack() {
            @Override
            public void execute () {
                if (activity.isNormal){
                    activity.appendDefaultSetMeal();
                }else{
                    activity.appendHasParamsData();
                }
            }
        });
        gv_list.setOnScrollListener(autoLoadListener);
    }

    @Override
    protected void initOnActivityCreated(Bundle savedInstanceState) {
        activity = (SetMealActivity) getActivity();
        adapter = new SetMealListAdapter(activity.setMealList);
        gv_list.setAdapter(adapter);
    }

    public void replaceData(){
        SetMealActivity activity = (SetMealActivity) getActivity();
        adapter = new SetMealListAdapter(activity.setMealList);
        gv_list.setAdapter(adapter);
    }

    public void appendData (List<SetMealBean> data){
        adapter.appendData(data);
    }

}
