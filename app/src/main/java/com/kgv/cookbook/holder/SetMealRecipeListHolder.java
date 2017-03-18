package com.kgv.cookbook.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.SetMealDetailBean;
import com.kgv.cookbook.config.Urls;

/**
 * Created by 陈可轩 on 2017/2/27 14:57
 * Email : 18627241899@163.com
 * Description :
 */
public class SetMealRecipeListHolder extends BaseHolder<SetMealDetailBean> {

    private ImageView iv_img;
    private TextView tv_name;
    public TextView tv_m;
    public TextView tv_a;
    public TextView tv_n;

    public SetMealRecipeListHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_recommend,null);
        iv_img = (ImageView) view.findViewById(R.id.iv_img);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_m = (TextView) view.findViewById(R.id.tv_m);
        tv_a = (TextView) view.findViewById(R.id.tv_a);
        tv_n = (TextView) view.findViewById(R.id.tv_n);
        return view;
    }

    @Override
    public void bindingData(SetMealDetailBean data, ViewGroup parent) {
        if (data != null){
            if (!TextUtils.isEmpty(data.getName())){
                tv_name.setText(data.getName());
            }
            String imageUrl = Urls.BASE_IMG_URL + data.getImage();
            Glide.with(parent.getContext())
                    .load(imageUrl)
                    .into(iv_img);
        }
    }
}
