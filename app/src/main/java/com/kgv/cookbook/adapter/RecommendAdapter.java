package com.kgv.cookbook.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.ShiPu;
import com.kgv.cookbook.holder.RecommendHolder;
import com.kgv.cookbook.listener.IOnSubClickListener;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/13 11:08
 * Email : 18627241899@163.com
 * Description :
 */
public class RecommendAdapter extends FinalBaseAdapter<ShiPu> {

    private IOnSubClickListener listener;

    public RecommendAdapter(List<ShiPu> datas,IOnSubClickListener listener) {
        super(datas);
        this.listener = listener;
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new RecommendHolder(parent);
    }

    @Override
    protected void handleSubBusiness(final int position, BaseHolder holder) {
        RecommendHolder recommendHolder = (RecommendHolder) holder;
        recommendHolder.tv_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSubClick(position,0);
            }
        });
        recommendHolder.tv_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSubClick(position,1);
            }
        });
        recommendHolder.tv_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSubClick(position,2);
            }
        });
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getId();
    }

    public void replaceDatas(List<ShiPu> obj) {
        mDatas = obj;
        notifyDataSetChanged();
    }

    public void appendData(List<ShiPu> data){
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public int getSize(){
        return mDatas.size();
    }
}
