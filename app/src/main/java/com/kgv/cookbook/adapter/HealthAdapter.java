package com.kgv.cookbook.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHasMoreAdapter;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.Nutrition;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.holder.HealthHolder;
import com.kgv.cookbook.listener.IOnStringIdClickListener;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/3/11 9:41
 * Email : 18627241899@163.com
 * Description :
 */
public class HealthAdapter extends BaseHasMoreAdapter<Nutrition> {

    private IOnStringIdClickListener listener;
    private boolean bg;

    public HealthAdapter(List<Nutrition> datas,IOnStringIdClickListener listener,boolean bg) {
        super(datas);
        this.listener = listener;
        this.bg = bg;
    }

    @Override
    protected int getDefaultShowSize () {
        return 3;
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new HealthHolder(parent);
    }

    @Override
    protected void handleSubBusiness(final int position, BaseHolder baseHolder) {
        HealthHolder healthHolder = (HealthHolder) baseHolder;
        if (bg){
            if (position % 2 == 0) {
                healthHolder.llParent.setBackgroundColor(AppUtils.getColor(R.color.item_double));
            } else {
                healthHolder.llParent.setBackgroundColor(AppUtils.getColor(R.color.item_single));
            }
        }else{
            if (position % 2 == 0) {
                healthHolder.llParent.setBackgroundColor(AppUtils.getColor(R.color.item_single));
            } else {
                healthHolder.llParent.setBackgroundColor(AppUtils.getColor(R.color.item_double));
            }
        }
        healthHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getItemIdByPosition(position);
                if (!"empty".equals(id)){
                    listener.onStringIdClick(id);
                }

            }
        });
    }

    public String getItemIdByPosition(int position){
        return mDatas.get(position).getSpId();
    }

}
