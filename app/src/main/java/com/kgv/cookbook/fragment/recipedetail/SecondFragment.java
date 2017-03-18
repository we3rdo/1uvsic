package com.kgv.cookbook.fragment.recipedetail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.RecipeActivity;
import com.kgv.cookbook.adapter.YyAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.ShiPuDetail;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/27 17:46
 * Email : 18627241899@163.com
 * Description : 食谱详情页-2.材料表/营养表
 */
public class SecondFragment extends BaseFragment {

    private TextView tv_zl;
    private TextView tv_fl;
    private TextView tv_cj;
    private ListView lv_yy;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_recipe_second;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        initUI(contentView);
    }

    private void initUI(View contentView) {
        tv_zl = (TextView) contentView.findViewById(R.id.tv_zl);
        tv_fl = (TextView) contentView.findViewById(R.id.tv_fl);
        tv_cj = (TextView) contentView.findViewById(R.id.tv_cj);
        lv_yy = (ListView) contentView.findViewById(R.id.lv_yy);
    }

    @Override
    protected void initOnActivityCreated(Bundle savedInstanceState) {
        RecipeActivity activity = (RecipeActivity) getActivity();
        ShiPuDetail bean = activity.bean;
        //主料
        List<ShiPuDetail.ZhuLiaoAndTiaoliaoEntity> zls = bean.getZhuliao();
        zls = removeEmptyItem(zls);
        String zl = "";
        for (int i = 0; i < zls.size(); i++) {
            zl += zls.get(i).getShicai_name() + zls.get(i).getYongliang() + "g";
            if (i != zls.size() - 1) {
                zl += "、";
            }
        }
        tv_zl.setText(zl);
        //辅料
        List<ShiPuDetail.ZhuLiaoAndTiaoliaoEntity> fls = bean.getTiaoliao();
        fls = removeEmptyItem(fls);
        String fl = "";
        for (int i = 0; i < fls.size(); i++) {
            fl += fls.get(i).getShicai_name() + fls.get(i).getYongliang() + "g";
            if (i != fls.size() - 1) {
                fl += "、";
            }
        }
        if (TextUtils.isEmpty(fl)){
            tv_fl.setText("无");
        }else{
            tv_fl.setText(fl);
        }
        //厨具
        List<ShiPuDetail.KitchenDetailEntity> cjs = bean.getKitchen_detail();
        String cj = "";
        for (int i = 0; i < cjs.size(); i++) {
            cj += cjs.get(i).getName() + "1个";
            if (i != cjs.size() - 1) {
                cj += "、";
            }
        }
        tv_cj.setText(cj);
        //营养信息
        YyAdapter yyAdapter = new YyAdapter(bean.getNutrition_detail());
        lv_yy.setAdapter(yyAdapter);
    }

    private List<ShiPuDetail.ZhuLiaoAndTiaoliaoEntity> removeEmptyItem(List<ShiPuDetail.ZhuLiaoAndTiaoliaoEntity> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            String name = list.get(i).getShicai_name().trim();
            String yongLiang = list.get(i).getYongliang();
            if (TextUtils.isEmpty(name) && "0".equals(yongLiang)) {
                list.remove(i);
            }
        }
        return list;
    }


}
