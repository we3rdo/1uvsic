package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;

/**
 * Created by 陈可轩 on 2017/2/20 10:45
 * Email : 18627241899@163.com
 * Description :
 */
public class WeekLeftHolder extends BaseHolder<String> {

    private TextView tv_name;

    public WeekLeftHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view =  View.inflate(parent.getContext(), R.layout.item_week_left,null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        return view;
    }

    @Override
    public void bindingData(String data, ViewGroup parent) {
        tv_name.setText(data);
    }
}
