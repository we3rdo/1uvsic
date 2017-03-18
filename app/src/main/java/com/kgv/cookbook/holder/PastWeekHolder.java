package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.PastWeekBean;

/**
 * Created by 陈可轩 on 2017/2/13 11:39
 * Email : 18627241899@163.com
 * Description :
 */
public class PastWeekHolder extends BaseHolder<PastWeekBean.NameIdEntity> {

    public TextView tv_name;

    public PastWeekHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_past_week,null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        return view;
    }

    @Override
    public void bindingData(PastWeekBean.NameIdEntity data, ViewGroup parent) {

    }
}
