package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHasMoreAdapter;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.MenuAndBlogShiPu;
import com.kgv.cookbook.holder.AllBlogHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/31 15:39
 * Email : 18627241899@163.com
 * Description :
 */
public class AllBlogAdapter extends BaseHasMoreAdapter<MenuAndBlogShiPu> {

    public AllBlogAdapter(List<MenuAndBlogShiPu> datas) {
        super(datas);
    }

    @Override
    protected int getDefaultShowSize () {
        return 4;
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new AllBlogHolder(parent);
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder baseHolder) {
        AllBlogHolder holder = (AllBlogHolder) baseHolder;
        holder.tv_name.setText(position + 1 + "." + mDatas.get(position).getName());
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getCookbook_id();
    }


}
