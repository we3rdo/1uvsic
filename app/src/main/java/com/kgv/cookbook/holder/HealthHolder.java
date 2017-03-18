package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.Nutrition;

/**
 * Created by 陈可轩 on 2017/3/11 9:41
 * Email : 18627241899@163.com
 * Description :
 */
public class HealthHolder extends BaseHolder<Nutrition> {

    public LinearLayout llParent;
    public TextView tv_name;
    private TextView tv_d0;
    private TextView tv_d1;
    private TextView tv_d2;
    private TextView tv_d3;
    private TextView tv_d4;
    private TextView tv_d5;
    private TextView tv_d6;
    private TextView tv_d7;
    private TextView tv_d8;
    private TextView tv_d9;

    public HealthHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_health, null);
        llParent = (LinearLayout) view.findViewById(R.id.llParent);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_d0 = (TextView) view.findViewById(R.id.tv_d0);
        tv_d1 = (TextView) view.findViewById(R.id.tv_d1);
        tv_d2 = (TextView) view.findViewById(R.id.tv_d2);
        tv_d3 = (TextView) view.findViewById(R.id.tv_d3);
        tv_d4 = (TextView) view.findViewById(R.id.tv_d4);
        tv_d5 = (TextView) view.findViewById(R.id.tv_d5);
        tv_d6 = (TextView) view.findViewById(R.id.tv_d6);
        tv_d7 = (TextView) view.findViewById(R.id.tv_d7);
        tv_d8 = (TextView) view.findViewById(R.id.tv_d8);
        tv_d9 = (TextView) view.findViewById(R.id.tv_d9);
        return view;
    }

    @Override
    public void bindingData(Nutrition data, ViewGroup parent) {
        if ("empty".equals(data.getSpId())){
            tv_name.setText(" ");
            tv_d0.setText(" ");
            tv_d1.setText(" ");
            tv_d2.setText(" ");
            tv_d3.setText(" ");
            tv_d4.setText(" ");
            tv_d5.setText(" ");
            tv_d6.setText(" ");
            tv_d7.setText(" ");
            tv_d8.setText(" ");
            tv_d9.setText(" ");
        }else{
            tv_name.setText(data.getData().get(0));
            tv_d0.setText(data.getData().get(1));
            tv_d1.setText(data.getData().get(2));
            tv_d2.setText(data.getData().get(3));
            tv_d3.setText(data.getData().get(4));
            tv_d4.setText(data.getData().get(5));
            tv_d5.setText(data.getData().get(6));
            tv_d6.setText(data.getData().get(7));
            tv_d7.setText(data.getData().get(8));
            tv_d8.setText(data.getData().get(9));
            tv_d9.setText(data.getData().get(10));
        }

    }
}
