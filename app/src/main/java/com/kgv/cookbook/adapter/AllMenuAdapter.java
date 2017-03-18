package com.kgv.cookbook.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.AddToMenuShipu;
import com.kgv.cookbook.holder.AllMenuHolder;
import com.kgv.cookbook.listener.IOnMenuDeleteListener;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/30 13:38
 * Email : 18627241899@163.com
 * Description :
 */
public class AllMenuAdapter extends FinalBaseAdapter<AddToMenuShipu> {

    private IOnMenuDeleteListener listener;

    public AllMenuAdapter(List<AddToMenuShipu> datas, IOnMenuDeleteListener listener) {
        super(datas);
        this.listener = listener;
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new AllMenuHolder(parent);
    }

    @Override
    protected void handleSubBusiness(final int position, BaseHolder baseHolder) {
        AllMenuHolder holder = (AllMenuHolder) baseHolder;
        holder.name.setText(position + 1 + "." + mDatas.get(position).getName());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMenuDelete(mDatas.get(position),mDatas.get(position).getType());
                mDatas.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    public boolean hasMenu(AddToMenuShipu shiPu){
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).getName().equals(shiPu.getName())){
                return true;
            }
        }
        return false;
    }
}
