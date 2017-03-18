package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.AddToMenuShipu;

/**
 * Created by 陈可轩 on 2016/12/30 13:38
 * Email : 18627241899@163.com
 * Description :
 */
public class AllMenuHolder extends BaseHolder<AddToMenuShipu> {

    public TextView name;
    public ImageView delete;

    public AllMenuHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_all_menu,null);
        name = (TextView) view.findViewById(R.id.name);
        delete = (ImageView) view.findViewById(R.id.delete);
        return view;
    }

    @Override
    public void bindingData(AddToMenuShipu data, ViewGroup parent) {

    }
}
