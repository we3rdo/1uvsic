package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.ShiPuDetail;
import com.kgv.cookbook.holder.YyHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/28 11:30
 * Email : 18627241899@163.com
 * Description :
 */
public class YyAdapter extends FinalBaseAdapter<ShiPuDetail.NutritionDetailEntity> {

    public YyAdapter(List<ShiPuDetail.NutritionDetailEntity> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new YyHolder(parent);
    }
}
