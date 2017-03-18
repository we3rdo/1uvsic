package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.MenuAndBlogShiPu;

/**
 * Created by 陈可轩 on 2017/1/10 16:02
 * Email : 18627241899@163.com
 * Description :
 */
public class HealthBlogHolder extends BaseHolder<MenuAndBlogShiPu> {

    public TextView tv_name;
    public ImageView iv_del;

    public HealthBlogHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_health_blog,null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        iv_del = (ImageView) view.findViewById(R.id.iv_del);
        return view;
    }

    @Override
    public void bindingData(MenuAndBlogShiPu data, ViewGroup parent) {

    }
}
