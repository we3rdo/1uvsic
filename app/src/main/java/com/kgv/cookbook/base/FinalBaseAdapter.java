package com.kgv.cookbook.base;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/11/25 10:15
 * Email : 18627241899@163.com
 * Description :
 */
public abstract class FinalBaseAdapter<T> extends PBaseAdapter<T>{

    public FinalBaseAdapter(List<T> datas) {
        super(datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null){
            holder = getDifferentHolder(parent);
        }else{
            holder = (BaseHolder) convertView.getTag();
        }
        holder.setDataAndBindingData(parent,mDatas.get(position));
        handleSubBusiness(position,holder);
        return holder.mHolderView;
    }

    /**
     *  处理子类各自业务逻辑
     */
    protected void handleSubBusiness(int position,BaseHolder baseHolder){

    }

    /**
     *  返回对应的holder
     */
    protected abstract BaseHolder getDifferentHolder(ViewGroup parent);

    /**
     *  清空数据并刷新UI
     */
    public void clearDatas() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    /**
     *  根据position删除指定数据并刷新UI
     */
    public void deleteDataByPosition(int position){
        mDatas.remove(position);
        notifyDataSetChanged();
    }
}
