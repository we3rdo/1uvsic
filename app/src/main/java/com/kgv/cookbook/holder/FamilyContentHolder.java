package com.kgv.cookbook.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.FamilyContent;
import com.kgv.cookbook.config.AppUtils;

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
    public ImageView iv_people_del;
    public ImageView iv_health_del;
    public View f_people;
    public View f_health;


    public FamilyContentHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_family_content, null);
        tv_age = (TextView) view.findViewById(R.id.tv_age);
        tv_people = (TextView) view.findViewById(R.id.tv_people);
        tv_health = (TextView) view.findViewById(R.id.tv_health);
        iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
        iv_people_del = (ImageView) view.findViewById(R.id.iv_people_del);
        iv_health_del = (ImageView) view.findViewById(R.id.iv_health_del);
        f_people = view.findViewById(R.id.f_people);
        f_health = view.findViewById(R.id.f_health);
        return view;
    }

    @Override
    public void bindingData(FamilyContent data, ViewGroup parent) {
        //1.年龄
        tv_age.setText(data.getMember_name());
        //2.人数
        String people = data.getNum() + "个";
        if("0个".equals(people) || TextUtils.isEmpty(people)){
            people = "点击设置";
            tv_people.setTextColor(AppUtils.getColor(R.color.tip));
        }else{
            tv_people.setTextColor(AppUtils.getColor(R.color.text_color_black_2_brown_s));
        }
        tv_people.setText(people);
        //3.健康状况
        String health = data.getHealth_name();
        if (TextUtils.isEmpty(health)){
            health = "点击设置";
            tv_health.setTextColor(AppUtils.getColor(R.color.tip));
        }else{
            tv_health.setTextColor(AppUtils.getColor(R.color.text_color_black_2_brown_s));
        }
        tv_health.setText(health);


    }
}
