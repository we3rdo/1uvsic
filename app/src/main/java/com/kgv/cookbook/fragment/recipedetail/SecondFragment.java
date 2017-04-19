package com.kgv.cookbook.fragment.recipedetail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.RecipeActivity;
import com.kgv.cookbook.adapter.RecipeDetailMAdapter;
import com.kgv.cookbook.adapter.YyAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.NameAndValue;
import com.kgv.cookbook.bean.ShiPuDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/27 17:46
 * Email : 18627241899@163.com
 * Description : 食谱详情页-2.材料表/营养表
 */
public class SecondFragment extends BaseFragment {

    private GridView gv_zl;
    private GridView gv_fl;
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
        gv_zl = (GridView) contentView.findViewById(R.id.gv_zl);
        gv_fl = (GridView) contentView.findViewById(R.id.gv_fl);
        tv_cj = (TextView) contentView.findViewById(R.id.tv_cj);
        lv_yy = (ListView) contentView.findViewById(R.id.lv_yy);
    }

    @Override
    protected void initOnActivityCreated(Bundle savedInstanceState) {
        RecipeActivity activity = (RecipeActivity) getActivity();
        ShiPuDetail bean = activity.bean;
        //主料
        List<ShiPuDetail.ZhuLiaoAndTiaoliaoEntity> zls = removeEmptyItem(bean.getZhuliao());
        List<NameAndValue> zl = new ArrayList<>();
        for (int i = 0; i < zls.size(); i++) {
            NameAndValue n = new NameAndValue(zls.get(i).getShicai_name(),zls.get(i).getYongliang() + "g");
            zl.add(n);
        }

        RecipeDetailMAdapter zlAdapter = new RecipeDetailMAdapter(zl);
        gv_zl.setAdapter(zlAdapter);

        //辅料
        List<ShiPuDetail.ZhuLiaoAndTiaoliaoEntity> fls = removeEmptyItem(bean.getTiaoliao());
        List<NameAndValue> fl = new ArrayList<>();
        for (int i = 0; i < fls.size(); i++) {
            NameAndValue n = new NameAndValue(fls.get(i).getShicai_name(),fls.get(i).getYongliang() + "g");
            fl.add(n);
        }
        RecipeDetailMAdapter flAdapter = new RecipeDetailMAdapter(fl);
        gv_fl.setAdapter(flAdapter);

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
