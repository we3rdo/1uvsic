package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.ShiPuDetail;

/**
 * Created by 陈可轩 on 2016/12/28 11:35
 * Email : 18627241899@163.com
 * Description :
 */
public class YyHolder extends BaseHolder<ShiPuDetail.NutritionDetailEntity> {

    private TextView tv_name;
    private TextView tv_value;

    public YyHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_yy, null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_value = (TextView) view.findViewById(R.id.tv_value);
        return view;
    }

    @Override
    public void bindingData(ShiPuDetail.NutritionDetailEntity data, ViewGroup parent) {
        tv_name.setText(data.getName());
        tv_value.setText(data.getVal() + "(" + data.getUnit() + ")");
    }
}
