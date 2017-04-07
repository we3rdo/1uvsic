package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.WeekBean;
import com.kgv.cookbook.holder.WeekHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/15 9:59
 * Email : 18627241899@163.com
 * Description :
 */
public class WeekAdapter extends FinalBaseAdapter<WeekBean.RecipeEntity> {

    private boolean opening;
    private boolean hasMore;

    public WeekAdapter(List<WeekBean.RecipeEntity> datas) {
        super(datas);
        opening = false;
        if (datas != null && datas.size() > 0){
            hasMore = datas.size() > 5;
        }
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new WeekHolder(parent);
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder baseHolder) {
        WeekHolder holder = (WeekHolder) baseHolder;
        holder.tv.setText(position+1+"."+mDatas.get(position).getName());
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getCookbook_id();
    }

    public void control(boolean flag){
        if (hasMore){
            opening = flag;
            notifyDataSetChanged();
        }
    }

    public boolean isOpening() {
        return opening;
    }

    @Override
    public int getCount() {
        if (mDatas != null){
            int realSize = mDatas.size();
            if (hasMore){
                return opening ? realSize : 5;
            }else{
                return realSize;
            }
        }
        return 0;
    }
}