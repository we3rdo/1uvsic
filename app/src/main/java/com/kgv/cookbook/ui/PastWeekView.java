package com.kgv.cookbook.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.adapter.PastWeekAdapter;
import com.kgv.cookbook.bean.PastWeekBean;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/23 14:10
 * Email : 18627241899@163.com
 * Description :
 */
public class PastWeekView extends LinearLayout{

    private PastWeekViewListener listener;
    private List<PastWeekBean> mdatas;
    private ArrayList<ViewStub> stubs = new ArrayList<>();
    private ArrayList<ListView4ScrollView> lvs = new ArrayList<>();
    private ArrayList<PastWeekAdapter> adapters = new ArrayList<>();
    private ArrayList<TextView> tv_mores = new ArrayList<>();
    private ArrayList<ImageView> iv_mores = new ArrayList<>();
    private ArrayList<TextView> re_liang = new ArrayList<>();
    private ArrayList<TextView> tan_shui = new ArrayList<>();
    private ArrayList<TextView> zhi_fang = new ArrayList<>();
    private ArrayList<TextView> dan_bai = new ArrayList<>();
    private ArrayList<TextView> dan_gu = new ArrayList<>();
    private ArrayList<TextView> mei = new ArrayList<>();
    private ArrayList<TextView> gai = new ArrayList<>();
    private ArrayList<TextView> tie = new ArrayList<>();
    private ArrayList<TextView> xin = new ArrayList<>();
    private ArrayList<TextView> xi = new ArrayList<>();
    private ArrayList<ImageView> iv0 = new ArrayList<>();
    private ArrayList<ImageView> iv1 = new ArrayList<>();
    private ArrayList<ImageView> iv2 = new ArrayList<>();
    private ArrayList<ImageView> iv3 = new ArrayList<>();
    private ArrayList<ImageView> iv4 = new ArrayList<>();
    private ArrayList<ImageView> iv5 = new ArrayList<>();
    private ArrayList<ImageView> iv6 = new ArrayList<>();
    private ArrayList<ImageView> iv7 = new ArrayList<>();
    private ArrayList<ImageView> iv8 = new ArrayList<>();
    private ArrayList<ImageView> iv9 = new ArrayList<>();

    public interface PastWeekViewListener{
        void onRecipeNameClick(String id);
        void onNutritionClick(String date, String unit);
    }

    public PastWeekView(Context context) {
        super(context);
        init(context);
    }

    public PastWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PastWeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.view_past_week,this);
        stubs.add((ViewStub) findViewById(R.id.vs_item0));
        stubs.add((ViewStub) findViewById(R.id.vs_item1));
        stubs.add((ViewStub) findViewById(R.id.vs_item2));
        stubs.add((ViewStub) findViewById(R.id.vs_item3));
        stubs.add((ViewStub) findViewById(R.id.vs_item4));
        stubs.add((ViewStub) findViewById(R.id.vs_item5));
        stubs.add((ViewStub) findViewById(R.id.vs_item6));
        for (int i = 0; i < 7; i++) {
            final int finalI = i;
            LinearLayout parent = (LinearLayout) stubs.get(i).inflate();
            if (i % 2 == 0){
                parent.findViewById(R.id.background).setBackgroundColor(AppUtils.getColor(R.color.item_double));
            }else{
                parent.findViewById(R.id.background).setBackgroundColor(AppUtils.getColor(R.color.item_single));
            }
            //1
            ((TextView)parent.findViewById(R.id.tv_day_of_week)).setText(dayOfWeek(i));
            //2.
            ListView4ScrollView lv = (ListView4ScrollView) parent.findViewById(R.id.lv_recipes);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listener.onRecipeNameClick(adapters.get(finalI).getItemIdByPosition(position));
                }
            });
            lvs.add(lv);
            //3
            parent.findViewById(R.id.ll_more).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    PastWeekAdapter adapter = adapters.get(finalI);
                    adapter.control(!adapter.isOpening());
                    tv_mores.get(finalI).setText(adapter.isOpening() ? "收起" : "更多");
                    iv_mores.get(finalI).setImageResource(adapter.isOpening() ? R.drawable.all_m_opened : R.drawable.all_m_closed);
                }
            });
            tv_mores.add((TextView) parent.findViewById(R.id.tv_more));
            iv_mores.add((ImageView) parent.findViewById(R.id.iv_more));
            //0~9
            parent.findViewById(R.id.ll_item0).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNutritionClick(mdatas.get(finalI).getDate(),"re_liang");
                }
            });
            parent.findViewById(R.id.ll_item1).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNutritionClick(mdatas.get(finalI).getDate(),"tan_shui");
                }
            });
            parent.findViewById(R.id.ll_item2).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNutritionClick(mdatas.get(finalI).getDate(),"zhi_fang");
                }
            });
            parent.findViewById(R.id.ll_item3).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNutritionClick(mdatas.get(finalI).getDate(),"dan_bai");
                }
            });
            parent.findViewById(R.id.ll_item4).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNutritionClick(mdatas.get(finalI).getDate(),"dan_gu");
                }
            });
            parent.findViewById(R.id.ll_item5).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNutritionClick(mdatas.get(finalI).getDate(),"mei");
                }
            });
            parent.findViewById(R.id.ll_item6).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNutritionClick(mdatas.get(finalI).getDate(),"gai");
                }
            });
            parent.findViewById(R.id.ll_item7).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNutritionClick(mdatas.get(finalI).getDate(),"tie");
                }
            });
            parent.findViewById(R.id.ll_item8).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNutritionClick(mdatas.get(finalI).getDate(),"xin");
                }
            });
            parent.findViewById(R.id.ll_item9).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNutritionClick(mdatas.get(finalI).getDate(),"xi");
                }
            });
            re_liang.add((TextView) parent.findViewById(R.id.tv_d0));
            tan_shui.add((TextView) parent.findViewById(R.id.tv_d1));
            zhi_fang.add((TextView) parent.findViewById(R.id.tv_d2));
            dan_bai.add((TextView) parent.findViewById(R.id.tv_d3));
            dan_gu.add((TextView) parent.findViewById(R.id.tv_d4));
            mei.add((TextView) parent.findViewById(R.id.tv_d5));
            gai.add((TextView) parent.findViewById(R.id.tv_d6));
            tie.add((TextView) parent.findViewById(R.id.tv_d7));
            xin.add((TextView) parent.findViewById(R.id.tv_d8));
            xi.add((TextView) parent.findViewById(R.id.tv_d9));
            iv0.add((ImageView) parent.findViewById(R.id.iv_0));
            iv1.add((ImageView) parent.findViewById(R.id.iv_1));
            iv2.add((ImageView) parent.findViewById(R.id.iv_2));
            iv3.add((ImageView) parent.findViewById(R.id.iv_3));
            iv4.add((ImageView) parent.findViewById(R.id.iv_4));
            iv5.add((ImageView) parent.findViewById(R.id.iv_5));
            iv6.add((ImageView) parent.findViewById(R.id.iv_6));
            iv7.add((ImageView) parent.findViewById(R.id.iv_7));
            iv8.add((ImageView) parent.findViewById(R.id.iv_8));
            iv9.add((ImageView) parent.findViewById(R.id.iv_9));
        }
    }

    private int statusImg(String string){
        if ("<".equals(string)){
            return R.drawable.health_nutrition_down;
        }else if (">".equals(string)){
            return R.drawable.health_nutrition_up;
        }else{
            return R.drawable.health_nutrition_normal;
        }
    }

    private String dayOfWeek(int i){
        return DateUtils.getPastWeek().get(i);
    }

    /*  need call.  */
    public void setDataSet(List<PastWeekBean> pastWeekBeans) {
        mdatas = pastWeekBeans;
        for (int i = 0; i < 7; i++) {
            PastWeekBean pastWeekBean = mdatas.get(i);
            PastWeekAdapter adapter;
            if (pastWeekBean.getCookbook_blog() != null && !pastWeekBean.getCookbook_blog().isEmpty()){
                adapter = new PastWeekAdapter(pastWeekBean.getCookbook_blog());
            }else{
                ArrayList<PastWeekBean.NameIdEntity> noData = new ArrayList<>();
                noData.add(new PastWeekBean.NameIdEntity("-1","-1"));
                noData.add(new PastWeekBean.NameIdEntity("-1","-1"));
                adapter = new PastWeekAdapter(noData);
            }
            lvs.get(i).setAdapter(adapter);
            adapters.add(adapter);
            PastWeekBean.NutritionEntity nutrition = pastWeekBean.getNutrition();
            if (nutrition != null){
                re_liang.get(i).setText(Double.toString(nutrition.getRe_liang().getVal()));
                tan_shui.get(i).setText(Double.toString(nutrition.getTan_shui().getVal()));
                zhi_fang.get(i).setText(Double.toString(nutrition.getZhi_fang().getVal()));
                dan_bai.get(i).setText(Double.toString(nutrition.getDan_bai().getVal()));
                dan_gu.get(i).setText(Double.toString(nutrition.getDan_gu().getVal()));
                mei.get(i).setText(Double.toString(nutrition.getMei().getVal()));
                gai.get(i).setText(Double.toString(nutrition.getGai().getVal()));
                tie.get(i).setText(Double.toString(nutrition.getTie().getVal()));
                xin.get(i).setText(Double.toString(nutrition.getXin().getVal()));
                xi.get(i).setText(Double.toString(nutrition.getXi().getVal()));
                iv0.get(i).setImageResource(statusImg(nutrition.getRe_liang().getStatus()));
                iv1.get(i).setImageResource(statusImg(nutrition.getTan_shui().getStatus()));
                iv2.get(i).setImageResource(statusImg(nutrition.getZhi_fang().getStatus()));
                iv3.get(i).setImageResource(statusImg(nutrition.getDan_bai().getStatus()));
                iv4.get(i).setImageResource(statusImg(nutrition.getDan_gu().getStatus()));
                iv5.get(i).setImageResource(statusImg(nutrition.getMei().getStatus()));
                iv6.get(i).setImageResource(statusImg(nutrition.getGai().getStatus()));
                iv7.get(i).setImageResource(statusImg(nutrition.getTie().getStatus()));
                iv8.get(i).setImageResource(statusImg(nutrition.getXin().getStatus()));
                iv9.get(i).setImageResource(statusImg(nutrition.getXi().getStatus()));
            }
        }
    }

    public void setListener(PastWeekViewListener listener) {
        this.listener = listener;
    }

}
