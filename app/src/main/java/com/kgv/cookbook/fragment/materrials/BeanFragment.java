package com.kgv.cookbook.fragment.materrials;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseMFragment;

/**
 * Created by 陈可轩 on 2016/12/21 14:01
 * Email : 18627241899@163.com
 * Description : 主页-食谱->食材大全-查看全部(1)-米面豆乳类(2) cate:45
 */
public class BeanFragment extends BaseMFragment{

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mate_bean;
    }


    @Override
    protected String getCateId() {
        return "45";
    }
}
