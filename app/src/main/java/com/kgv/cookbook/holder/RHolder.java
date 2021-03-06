package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.RecipeCategory;

/**
 * Created by 陈可轩 on 2016/12/13 11:09
 * Email : 18627241899@163.com
 * Description :
 */
public class RHolder extends BaseHolder<RecipeCategory.ChildrenEntity> {

    private TextView tv_name;
    public ImageView iv_ok;

    public RHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_r,null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        iv_ok = (ImageView) view.findViewById(R.id.iv_ok);
        return view;
    }

    @Override
    public void bindingData(RecipeCategory.ChildrenEntity data, ViewGroup parent) {
        String text = data.getTitle();
        if (text.contains("（")){
            text = text.replace("（","(");
        }
        if (text.contains("）")){
            text = text.replace("）",")");
        }
        tv_name.setText(text);
    }

}
