package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.FamilyContent;

/**
 * Created by 陈可轩 on 2017/1/12 16:28
 * Email : 18627241899@163.com
 * Description :
 */
public class FamilyContentHolder extends BaseHolder<FamilyContent> {

    public TextView tv_age;
    public TextView tv_people;
    public TextView tv_health;
    public ImageView iv_delete;
    public View f_people;
    public View f_health;

    public FamilyContentHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_family_content,null);
        tv_age = (TextView) view.findViewById(R.id.tv_age);
        tv_people = (TextView) view.findViewById(R.id.tv_people);
        tv_health = (TextView) view.findViewById(R.id.tv_health);
        iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
        f_people = view.findViewById(R.id.f_people);
        f_health = view.findViewById(R.id.f_health);
        return view;
    }

    @Override
    public void bindingData(FamilyContent data, ViewGroup parent) {

    }
}
