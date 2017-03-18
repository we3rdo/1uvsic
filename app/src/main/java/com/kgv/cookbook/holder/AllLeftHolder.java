package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.ShiCai;

/**
 * Created by 陈可轩 on 2016/12/13 11:09
 * Email : 18627241899@163.com
 * Description :
 */
public class AllLeftHolder extends BaseHolder<ShiCai> {

    public TextView tv_name;
    public View view_line;
    public ImageView iv_edit;

    public AllLeftHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_all_left,null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        view_line = view.findViewById(R.id.view_line);
        iv_edit = (ImageView) view.findViewById(R.id.iv_edit);
        return view;
    }

    @Override
    public void bindingData(ShiCai data, ViewGroup parent) {
        tv_name.setText(data.getName());
    }
}
