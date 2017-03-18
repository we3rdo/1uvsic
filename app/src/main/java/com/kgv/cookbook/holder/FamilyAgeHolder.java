package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.NameAndId;

/**
 * Created by 陈可轩 on 2017/1/11 17:56
 * Email : 18627241899@163.com
 * Description :
 */
public class FamilyAgeHolder extends BaseHolder<NameAndId> {

    private TextView tv_name;

    public FamilyAgeHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_family_age,null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        return view;
    }

    @Override
    public void bindingData(NameAndId data, ViewGroup parent) {
        tv_name.setText(data.getName());
    }
}
