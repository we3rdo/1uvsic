package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.NutritionItem;

/**
 * Created by 陈可轩 on 2017/2/9 17:55
 * Email : 18627241899@163.com
 * Description :
 */
public class NutritionDetailHolder extends BaseHolder<NutritionItem>{

    public TextView tv0,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
    public ImageView iv0,iv1,iv2,iv3,iv4,iv5,iv6,iv7,iv8,iv9;

    public NutritionDetailHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_nutrition_detail,null);
        tv0 = (TextView) view.findViewById(R.id.tv0);
        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv2 = (TextView) view.findViewById(R.id.tv2);
        tv3 = (TextView) view.findViewById(R.id.tv3);
        tv4 = (TextView) view.findViewById(R.id.tv4);
        tv5 = (TextView) view.findViewById(R.id.tv5);
        tv6 = (TextView) view.findViewById(R.id.tv6);
        tv7 = (TextView) view.findViewById(R.id.tv7);
        tv8 = (TextView) view.findViewById(R.id.tv8);
        tv9 = (TextView) view.findViewById(R.id.tv9);
        iv0 = (ImageView) view.findViewById(R.id.iv0);
        iv1 = (ImageView) view.findViewById(R.id.iv1);
        iv2 = (ImageView) view.findViewById(R.id.iv2);
        iv3 = (ImageView) view.findViewById(R.id.iv3);
        iv4 = (ImageView) view.findViewById(R.id.iv4);
        iv5 = (ImageView) view.findViewById(R.id.iv5);
        iv6 = (ImageView) view.findViewById(R.id.iv6);
        iv7 = (ImageView) view.findViewById(R.id.iv7);
        iv8 = (ImageView) view.findViewById(R.id.iv8);
        iv9 = (ImageView) view.findViewById(R.id.iv9);
        return view;
    }

    @Override
    public void bindingData(NutritionItem data, ViewGroup parent) {

    }
}
