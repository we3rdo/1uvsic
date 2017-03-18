package com.kgv.cookbook.fragment.recipedetail;

import android.content.Intent;
import android.graphics.Color;
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

/**
 * Created by 陈可轩 on 2016/12/27 17:47
 * Email : 18627241899@163.com
 * Description : 食谱详情页-3~10.步骤
 */
public class StepFragment extends BaseFragment implements View.OnClickListener {

    private ImageView iv_left;
    private TextView tv_left_number;
    private TextView tv_left_note;
    private ImageView iv_right;
    private TextView tv_right_number;
    private TextView tv_right_note;

    private ShiPuDetail.StepEntity leftStep;
    private ShiPuDetail.StepEntity rightStep;
    private String leftNum;
    private String rightNum;

    private RecipeActivity activity;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_recipe_step;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        initUI(contentView);
        bindingData();
    }

    private void initUI(View contentView) {
        iv_left = (ImageView) contentView.findViewById(R.id.iv_left);
        iv_left.setOnClickListener(this);
        iv_right = (ImageView) contentView.findViewById(R.id.iv_right);
        iv_right.setOnClickListener(this);
        tv_left_number = (TextView) contentView.findViewById(R.id.tv_left_number);
        tv_right_number = (TextView) contentView.findViewById(R.id.tv_right_number);
        tv_left_note = (TextView) contentView.findViewById(R.id.tv_left_note);
        tv_right_note = (TextView) contentView.findViewById(R.id.tv_right_note);
    }

    @Override
    protected void initOnActivityCreated(Bundle savedInstanceState) {
        activity = (RecipeActivity) baseActivity;
    }

    @Override
    public void onClick(View v) {
        int out = (Integer.MAX_VALUE / 2) % (activity.imageUrls.size());
        int position = Integer.MAX_VALUE / 2 - out;
        Intent intent = new Intent(activity, ImageActivity.class);
        intent.putExtra(IntentKeys.IMAGES,activity.imageUrls);
        intent.putExtra(IntentKeys.DESCRIPTIONS,activity.descriptions);
        switch (v.getId()){
            case R.id.iv_left:
                //position = position + Integer.parseInt(leftNum);
                intent.putExtra(IntentKeys.IMAGE_POSITION,position + Integer.parseInt(leftNum));
                break;
            case R.id.iv_right:
                //position = position + Integer.parseInt(rightNum);
                intent.putExtra(IntentKeys.IMAGE_POSITION,position + Integer.parseInt(rightNum));
                break;
        }
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.activity_in_alpha,R.anim.activity_out_alpha);

//        new ImageViewer.Builder(activity, activity.imageUrls)
//                .setStartPosition(position)
//                .setImageMargin(activity, R.dimen.image_margin)
//                .setOnDismissListener(activity.getDisissListener())
//                .setImageChangeListener(activity.getImageChangeListener())
//                .setCustomDraweeHierarchyBuilder(activity.getHierarchyBuilder())
//                .setOverlayView(activity.overlayView)
//                .show();
    }

    private void bindingData() {
        Glide.with(getContext())
                .load(Urls.BASE_IMG_URL + leftStep.getImage())
                .into(iv_left);
        tv_left_number.setText(leftNum);
        tv_left_note.setText(leftStep.getNote());
        if (rightStep == null || TextUtils.isEmpty(rightNum)){
            iv_right.setBackgroundColor(Color.TRANSPARENT);
            return;
        }
        tv_right_number.setVisibility(View.VISIBLE);
        Glide.with(getContext())
                .load(Urls.BASE_IMG_URL + rightStep.getImage())
                .into(iv_right);
        tv_right_number.setText(rightNum);
        tv_right_note.setText(rightStep.getNote());
    }

    public void setData(ShiPuDetail.StepEntity leftStep,ShiPuDetail.StepEntity rightStep,String leftNum,String rightNum){
        this.leftStep = leftStep;
        this.rightStep = rightStep;
        this.leftNum = leftNum;
        this.rightNum = rightNum;
    }


}
