package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.SetMealBean;
import com.kgv.cookbook.config.Urls;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/21 9:47
 * Email : 18627241899@163.com
 * Description :
 */
public class SetMealListHolder extends BaseHolder<SetMealBean> {

    public ImageView iv_big;
    private ImageView iv_1;
    private ImageView iv_2;
    private ImageView iv_3;
    private TextView tv_name;

    public SetMealListHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_setmeal_list, null);
        iv_big = (ImageView) view.findViewById(R.id.iv_big);
        iv_1 = (ImageView) view.findViewById(R.id.iv_1);
        iv_2 = (ImageView) view.findViewById(R.id.iv_2);
        iv_3 = (ImageView) view.findViewById(R.id.iv_3);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        return view;
    }

    @Override
    public void bindingData(SetMealBean data, ViewGroup parent) {
        List<SetMealBean.ImageEntity> images = data.getRecipe_cookbook();

        switch (images.size()){
            case 0:
                break;
            case 1:
                Glide.with(parent.getContext())
                        .load(Urls.BASE_IMG_URL + images.get(0).getImage())
                        .into(iv_1);
                break;
            case 2:
                Glide.with(parent.getContext())
                        .load(Urls.BASE_IMG_URL + images.get(0).getImage())
                        .into(iv_1);
                Glide.with(parent.getContext())
                        .load(Urls.BASE_IMG_URL + images.get(1).getImage())
                        .into(iv_2);
                break;
            case 3:
                Glide.with(parent.getContext())
                        .load(Urls.BASE_IMG_URL + images.get(0).getImage())
                        .into(iv_1);
                Glide.with(parent.getContext())
                        .load(Urls.BASE_IMG_URL + images.get(1).getImage())
                        .into(iv_2);
                Glide.with(parent.getContext())
                        .load(Urls.BASE_IMG_URL + images.get(2).getImage())
                        .into(iv_3);
                break;
            default:
                Glide.with(parent.getContext())
                        .load(Urls.BASE_IMG_URL + images.get(0).getImage())
                        .into(iv_1);
                Glide.with(parent.getContext())
                        .load(Urls.BASE_IMG_URL + images.get(1).getImage())
                        .into(iv_2);
                Glide.with(parent.getContext())
                        .load(Urls.BASE_IMG_URL + images.get(2).getImage())
                        .into(iv_3);
                break;
        }
        Glide.with(parent.getContext())
                .load(Urls.BASE_IMG_URL + data.getCover().getImage())
                .into(iv_big);
        tv_name.setText(data.getTitle());
    }
}
