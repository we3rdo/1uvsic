package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.SetMealBean;
import com.kgv.cookbook.holder.SetMealListHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/21 9:46
 * Email : 18627241899@163.com
 * Description :
 */
public class SetMealListAdapter extends FinalBaseAdapter<SetMealBean> {

    public SetMealListAdapter(List<SetMealBean> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new SetMealListHolder(parent);
    }

    public SetMealBean getItemByPosition(int position){
        return mDatas.get(position);
    }

    public void appendData (List<SetMealBean> data) {
        mDatas.addAll(data);
        notifyDataSetChanged();
    }
}
