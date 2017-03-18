package com.kgv.cookbook.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/2 16:58
 * Email : 18627241899@163.com
 * Description :
 */
public class PBaseAdapter<T> extends BaseAdapter{

    public List<T> mDatas = new ArrayList<>();

    public PBaseAdapter(List<T> datas){
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public Object getItem(int position) {
        return mDatas != null ? mDatas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
