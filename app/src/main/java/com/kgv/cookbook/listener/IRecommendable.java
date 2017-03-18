package com.kgv.cookbook.listener;

import com.kgv.cookbook.bean.ShiPu;

/**
 * Created by 陈可轩 on 2017/1/4 14:38
 * Email : 18627241899@163.com
 * Description :
 */
public interface IRecommendable {

    /**
     *  添加食谱到菜单 type:0早 1中 2晚
     */
    void addToMenu(ShiPu data, int type);
}
