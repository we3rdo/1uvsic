package com.kgv.cookbook.fragment.recipedetail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.ImageActivity;
import com.kgv.cookbook.activity.RecipeActivity;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.ShiPuDetail;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/27 17:46
 * Email : 18627241899@163.com
 * Description : 食谱详情页-1.成品图/功效
 */
public class FirstFragment extends BaseFragment implements View.OnClickListener {

    private ImageView iv_first;
    private TextView tv_description;
    private TextView tv_effect;
    private TextView tv_proper;
    private TextView tv_taboo;
    private TextView tv_tip;
    private RecipeActivity activity;
    private View line_effect;
    private View line_proper;
    private View line_taboo;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_recipe_first;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        initUI(contentView);
    }

    private void initUI(View contentView) {
        iv_first = (ImageView) contentView.findViewById(R.id.iv_first);
        iv_first.setOnClickListener(this);
        tv_description = (TextView) contentView.findViewById(R.id.tv_description);
        tv_effect = (TextView) contentView.findViewById(R.id.tv_effect);
        tv_proper = (TextView) contentView.findViewById(R.id.tv_proper);
        tv_taboo = (TextView) contentView.findViewById(R.id.tv_taboo);
        tv_tip = (TextView) contentView.findViewById(R.id.tv_tip);
        line_effect = contentView.findViewById(R.id.line_effect);
        line_proper = contentView.findViewById(R.id.line_proper);
        line_taboo = contentView.findViewById(R.id.line_taboo);
    }

    @Override
    protected void initOnActivityCreated(Bundle savedInstanceState) {
        activity = (RecipeActivity) getActivity();
        ShiPuDetail bean = activity.bean;
        bindingData(bean);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_first:
                Intent intent = new Intent(activity, ImageActivity.class);
                intent.putExtra(IntentKeys.IMAGES, activity.imageUrls);
                intent.putExtra(IntentKeys.DESCRIPTIONS, activity.descriptions);
                intent.putExtra(IntentKeys.IMAGE_POSITION, 0);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.activity_in_alpha, R.anim.activity_out_alpha);
//
//                int out = (Integer.MAX_VALUE / 2) % (activity.imageUrls.size());
//                int position = Integer.MAX_VALUE / 2 - out;
//                new ImageViewer.Builder(activity, activity.imageUrls)
//                        .setStartPosition(position)
//                        .setImageMargin(activity, R.dimen.image_margin)
//                        .setOnDismissListener(activity.getDisissListener())
//                        .setImageChangeListener(activity.getImageChangeListener())
//                        .setCustomDraweeHierarchyBuilder(activity.getHierarchyBuilder())
//                        .setOverlayView(activity.overlayView)
//                        .show();
                break;
        }
    }

    private void bindingData(ShiPuDetail bean) {
        //成品图
        Glide.with(getContext())
                .load(Urls.BASE_IMG_URL + bean.getImage())
                .into(iv_first);
        //描述
        String des = bean.getDescription().replace("<br>", "");
        tv_description.setText("    " + des);
        //营养功效
        List<ShiPuDetail.IdAndNameEntity> effects = bean.getEffect_label_detail();
        if (effects != null && !effects.isEmpty()) {
            String effect = "。";
            if (!TextUtils.isEmpty(bean.getEffect_txt())) {
                effect = bean.getEffect_txt().replace("<br>", "");
                tv_effect.setText("营养功效：" + splitText(effects) + "。\n" + effect);
            }else{
                tv_effect.setText("营养功效：" + splitText(effects) + effect);
            }
        } else {
            tv_effect.setVisibility(View.GONE);
            line_effect.setVisibility(View.GONE);
        }
        //适宜
        List<ShiPuDetail.IdAndNameEntity> propers = bean.getProper_label_detail();
        if (propers != null && !propers.isEmpty()) {
            String proper = "。";
            if (!TextUtils.isEmpty(bean.getProper_txt())) {
                proper = bean.getProper_txt();
            }
            String p = "适宜：" + splitText(propers) + proper;
            tv_proper.setText(p.replace("<br>", ""));
        } else {
            tv_proper.setVisibility(View.GONE);
            line_proper.setVisibility(View.GONE);
        }
        //禁忌
        List<ShiPuDetail.IdAndNameEntity> taboos = bean.getTaboo_label_detail();
        if (taboos != null && !taboos.isEmpty()) {
            String taboo = "。";
            if (!TextUtils.isEmpty(bean.getTaboo_txt())) {
                taboo = bean.getTaboo_txt().replace("<br>","");
            }
            tv_taboo.setText("禁忌：" + splitText(taboos) + taboo);
        } else {
            tv_taboo.setVisibility(View.GONE);
            line_taboo.setVisibility(View.GONE);
        }
        //小贴士
        if (!TextUtils.isEmpty(bean.getTips())) {
            String tips = bean.getTips().replace("<br>", "");
            tv_tip.setText("小贴士：\n" + tips);
        }
    }

    private String splitText(List<ShiPuDetail.IdAndNameEntity> list) {
        String text = "";
        for (int i = 0; i < list.size(); i++) {
            text += list.get(i).getName();
            if (i != list.size() - 1) {
                text += "、";
            }
        }
        return text;
    }

}