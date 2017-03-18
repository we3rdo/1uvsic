package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.M;

/**
 * Created by 陈可轩 on 2016/12/13 11:09
 * Email : 18627241899@163.com
 * Description :
 */
public class MHolder extends BaseHolder<M.ChildrenEntity.SubChildrenEntity> {

    public TextView tv_name;
    public ImageView iv_ok;
    public ImageView iv_tip;

    public MHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_m,null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        iv_ok = (ImageView) view.findViewById(R.id.iv_ok);
        iv_tip = (ImageView) view.findViewById(R.id.iv_tip);
        return view;
    }

    @Override
    public void bindingData(M.ChildrenEntity.SubChildrenEntity data, ViewGroup parent) {
        tv_name.setText(data.getTitle());
    }

}
