package com.kgv.cookbook.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.NutritionItem;
import com.kgv.cookbook.holder.NutritionDetailHolder;
import com.kgv.cookbook.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/9 17:53
 * Email : 18627241899@163.com
 * Description :
 */
public class NutritionDetailAdapter extends FinalBaseAdapter<NutritionItem> {

    private boolean needShowStatus = false;

    public NutritionDetailAdapter(List<NutritionItem> datas, boolean showStatus) {
        super(datas);
        ArrayList<NutritionItem> n = new ArrayList<>();
        n.add(new NutritionItem());
        n.add(new NutritionItem());
        n.addAll(datas);
        mDatas = n;
        needShowStatus = showStatus;
        LogUtils.v("adapter", mDatas.size() + "条");
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new NutritionDetailHolder(parent);
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder baseHolder) {
        NutritionDetailHolder holder = (NutritionDetailHolder) baseHolder;
        NutritionItem data = mDatas.get(position);
        switch (position) {
            case 0:
                holder.tv0.setText("热量");
                holder.tv1.setText("碳水化合物");
                holder.tv2.setText("脂肪");
                holder.tv3.setText("蛋白质");
                holder.tv4.setText("胆固醇");
                holder.tv5.setText("镁");
                holder.tv6.setText("钙");
                holder.tv7.setText("铁");
                holder.tv8.setText("锌");
                holder.tv9.setText("硒");
                holder.iv0.setVisibility(View.GONE);
                holder.iv1.setVisibility(View.GONE);
                holder.iv2.setVisibility(View.GONE);
                holder.iv3.setVisibility(View.GONE);
                holder.iv4.setVisibility(View.GONE);
                holder.iv5.setVisibility(View.GONE);
                holder.iv6.setVisibility(View.GONE);
                holder.iv7.setVisibility(View.GONE);
                holder.iv8.setVisibility(View.GONE);
                holder.iv9.setVisibility(View.GONE);
                break;
            case 1:
                holder.tv0.setText("大卡");
                holder.tv1.setText("克");
                holder.tv2.setText("克");
                holder.tv3.setText("克");
                holder.tv4.setText("毫克");
                holder.tv5.setText("毫克");
                holder.tv6.setText("毫克");
                holder.tv7.setText("毫克");
                holder.tv8.setText("毫克");
                holder.tv9.setText("毫克");
                holder.iv0.setVisibility(View.GONE);
                holder.iv1.setVisibility(View.GONE);
                holder.iv2.setVisibility(View.GONE);
                holder.iv3.setVisibility(View.GONE);
                holder.iv4.setVisibility(View.GONE);
                holder.iv5.setVisibility(View.GONE);
                holder.iv6.setVisibility(View.GONE);
                holder.iv7.setVisibility(View.GONE);
                holder.iv8.setVisibility(View.GONE);
                holder.iv9.setVisibility(View.GONE);
                break;
            default:
                holder.tv0.setText(data.getV0());
                holder.tv1.setText(data.getV1());
                holder.tv2.setText(data.getV2());
                holder.tv3.setText(data.getV3());
                holder.tv4.setText(data.getV4());
                holder.tv5.setText(data.getV5());
                holder.tv6.setText(data.getV6());
                holder.tv7.setText(data.getV7());
                holder.tv8.setText(data.getV8());
                holder.tv9.setText(data.getV9());
                holder.iv0.setVisibility(needShowStatus ? View.VISIBLE : View.GONE);
                holder.iv1.setVisibility(needShowStatus ? View.VISIBLE : View.GONE);
                holder.iv2.setVisibility(needShowStatus ? View.VISIBLE : View.GONE);
                holder.iv3.setVisibility(needShowStatus ? View.VISIBLE : View.GONE);
                holder.iv4.setVisibility(needShowStatus ? View.VISIBLE : View.GONE);
                holder.iv5.setVisibility(needShowStatus ? View.VISIBLE : View.GONE);
                holder.iv6.setVisibility(needShowStatus ? View.VISIBLE : View.GONE);
                holder.iv7.setVisibility(needShowStatus ? View.VISIBLE : View.GONE);
                holder.iv8.setVisibility(needShowStatus ? View.VISIBLE : View.GONE);
                holder.iv9.setVisibility(needShowStatus ? View.VISIBLE : View.GONE);
                if (needShowStatus) {
                    holder.iv0.setImageResource(status(data.getS0()));
                    holder.iv1.setImageResource(status(data.getS1()));
                    holder.iv2.setImageResource(status(data.getS2()));
                    holder.iv3.setImageResource(status(data.getS3()));
                    holder.iv4.setImageResource(status(data.getS4()));
                    holder.iv5.setImageResource(status(data.getS5()));
                    holder.iv6.setImageResource(status(data.getS6()));
                    holder.iv7.setImageResource(status(data.getS7()));
                    holder.iv8.setImageResource(status(data.getS8()));
                    holder.iv9.setImageResource(status(data.getS9()));
                }
                break;
        }
    }

    private int status(String a) {
        int img;
        if (">".equals(a)) {
            img = R.drawable.health_nutrition_up;
        } else if ("<".equals(a)) {
            img = R.drawable.health_nutrition_down;
        } else {
            img = R.drawable.health_nutrition_normal;
        }
        return img;
    }

    public void removeItem(String spId) {
        for (int i = 0; i < mDatas.size(); i++) {
            if (spId.equals(mDatas.get(i).getSpId())) {
                mDatas.remove(mDatas.remove(i));
            }
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        mDatas = new ArrayList<>();
        notifyDataSetChanged();
    }
}
