package com.kgv.cookbook.adapter;

import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.holder.RecipeStepHolder;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/5 11:57
 * Email : 18627241899@163.com
 * Description :
 */
public class RecipeStepAdapter extends FinalBaseAdapter<String> {

    private int selectedPosition = 0;

    public RecipeStepAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new RecipeStepHolder(parent);
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder baseHolder) {
        RecipeStepHolder holder = (RecipeStepHolder) baseHolder;
        holder.tv_position.setText(position + 1 + "");
        setName(holder, position);
        holder.tv_position.setSelected(position == selectedPosition);
        holder.tv_name.setSelected(position == selectedPosition);
    }

    private void setName(RecipeStepHolder holder, int position) {
        switch (position) {
            case 0:
                holder.tv_name.setText("成品图/功效");
                break;
            case 1:
                holder.tv_name.setText("材料表/营养表");
                break;
            case 2:
                holder.tv_name.setText("步骤：1/2");
                break;
            case 3:
                holder.tv_name.setText("步骤：3/4");
                break;
            case 4:
                holder.tv_name.setText("步骤：5/6");
                break;
            case 5:
                holder.tv_name.setText("步骤：7/8");
                break;
            case 6:
                holder.tv_name.setText("步骤：9/10");
                break;
            case 7:
                holder.tv_name.setText("步骤：11/12");
                break;
            case 8:
                holder.tv_name.setText("步骤：13/14");
                break;
            case 9:
                holder.tv_name.setText("步骤：15/16");
                break;
        }
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }
}
