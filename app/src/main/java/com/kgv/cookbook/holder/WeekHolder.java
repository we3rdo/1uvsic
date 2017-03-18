package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.WeekBean;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.config.SpKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.util.ImageUtils;
import com.kgv.cookbook.util.Md5Helper;
import com.kgv.cookbook.util.SpUtils;

import java.io.File;

/**
 * Created by 陈可轩 on 2017/2/15 10:00
 * Email : 18627241899@163.com
 * Description :
 */
public class WeekHolder extends BaseHolder<WeekBean.RecipeEntity> {

    public TextView tv;
    private ImageView iv;
    private ImageView iv_label;

    public WeekHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_week, null);
        tv = (TextView) view.findViewById(R.id.tv);
        iv = (ImageView) view.findViewById(R.id.iv);
        iv_label = (ImageView) view.findViewById(R.id.iv_label);
        return view;
    }

    @Override
    public void bindingData(WeekBean.RecipeEntity data, ViewGroup parent) {
        String imageUrl = Urls.BASE_IMG_URL + data.getImage_thumb();
        //是否下载过
        String download = SpUtils.getString(AppUtils.getContext(), SpKeys.DOWNLOAD_IMG_SUCCESS);
        if ("t".equals(download)) {
            String md5ImageName = Md5Helper.toMD5(imageUrl);
            File file = ImageUtils.getImageByName(md5ImageName);
            if (file != null && file.exists()) {
                Glide.with(parent.getContext())
                        .load(file)
                        .into(iv);
            } else {
                Glide.with(parent.getContext())
                        .load(imageUrl)
                        .into(iv);
            }
        }else{
            //没有下载过
            Glide.with(parent.getContext())
                    .load(imageUrl)
                    .into(iv);
        }
        String label = data.getCrow_name();
        if ("婴儿".equals(label)) {
            iv_label.setImageResource(R.drawable.label_0);
        } else if ("幼儿".equals(label)) {
            iv_label.setImageResource(R.drawable.label_1);
        } else if ("儿童".equals(label)) {
            iv_label.setImageResource(R.drawable.label_2);
        } else if ("青少年".equals(label)) {
            iv_label.setImageResource(R.drawable.label_3);
        } else if ("成年人".equals(label)) {
            iv_label.setImageResource(R.drawable.label_4);
        } else if ("中年人".equals(label)) {
            iv_label.setImageResource(R.drawable.label_5);
        } else if ("老年人".equals(label)) {
            iv_label.setImageResource(R.drawable.label_6);
        } else if ("孕妇".equals(label)) {
            iv_label.setImageResource(R.drawable.label_7);
        }else{
            iv_label.setImageResource(0);
        }
    }
}
