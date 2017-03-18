package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHasMoreAdapter;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.PastWeekBean;
import com.kgv.cookbook.holder.PastWeekHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/23 14:47
 * Email : 18627241899@163.com
 * Description :
 */
public class PastWeekAdapter extends BaseHasMoreAdapter<PastWeekBean.NameIdEntity> {

    public PastWeekAdapter(List<PastWeekBean.NameIdEntity> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new PastWeekHolder(parent);
    }

    @Override
    protected int getDefaultShowSize () {
        return 2;
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getCookbook_id();
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder baseHolder) {
        PastWeekHolder holder = (PastWeekHolder) baseHolder;
        if (!"-1".equals(mDatas.get(position).getName())){
            holder.tv_name.setText(position + 1 + "." + mDatas.get(position).getName());
        }else{
            holder.tv_name.setText(" ");
        }
    }

}
