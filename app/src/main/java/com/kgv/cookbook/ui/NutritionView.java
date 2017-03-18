package com.kgv.cookbook.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.bean.Nutrition;

import java.util.ArrayList;

/**
 * Created by 陈可轩 on 2017/2/17 9:42
 * Email : 18627241899@163.com
 * Description :
 */
public class NutritionView extends LinearLayout implements View.OnClickListener {

    private TextView tv_type;
    private TextView tv_more_breakfast,tv_more_lunch,tv_more_dinner;
    private ImageView iv_more_breakfast,iv_more_lunch,iv_more_dinner;
    private NutritionListView lv_breakfast,lv_lunch,lv_dinner;

    public NutritionView(Context context) {
        super(context);
        init(context);
    }

    public NutritionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public NutritionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.view_nutrition,this);
        tv_type = (TextView) this.findViewById(R.id.tv_type);
        tv_more_breakfast = (TextView) this.findViewById(R.id.tv_more_breakfast);
        tv_more_lunch = (TextView) this.findViewById(R.id.tv_more_lunch);
        tv_more_dinner = (TextView) this.findViewById(R.id.tv_more_dinner);
        iv_more_breakfast = (ImageView) this.findViewById(R.id.iv_more_breakfast);
        iv_more_lunch = (ImageView) this.findViewById(R.id.iv_more_lunch);
        iv_more_dinner = (ImageView) this.findViewById(R.id.iv_more_dinner);
        this.findViewById(R.id.ll_more_breakfast).setOnClickListener(this);
        this.findViewById(R.id.ll_more_lunch).setOnClickListener(this);
        this.findViewById(R.id.ll_more_dinner).setOnClickListener(this);
        lv_breakfast = (NutritionListView) this.findViewById(R.id.lv_breakfast);
        lv_lunch = (NutritionListView) this.findViewById(R.id.lv_lunch);
        lv_dinner = (NutritionListView) this.findViewById(R.id.lv_dinner);
        lv_breakfast.setBackground(true);
        lv_lunch.setBackground(false);
        lv_dinner.setBackground(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_more_breakfast:
                if (lv_breakfast.isHasMore()){
                    tv_more_breakfast.setText(lv_breakfast.isOpening() ? "更多" : "收起" );
                    iv_more_breakfast.setImageResource(lv_breakfast.isOpening() ? R.drawable.all_m_closed : R.drawable.all_m_opened);
                    lv_breakfast.control(!lv_breakfast.isOpening());
                }
                break;
            case R.id.ll_more_lunch:
                if (lv_lunch.isHasMore()){
                    tv_more_lunch.setText(lv_lunch.isOpening() ? "更多" : "收起" );
                    iv_more_lunch.setImageResource(lv_lunch.isOpening() ? R.drawable.all_m_closed : R.drawable.all_m_opened);
                    lv_lunch.control(!lv_lunch.isOpening());
                }
                break;
            case R.id.ll_more_dinner:
                if (lv_dinner.isHasMore()){
                    tv_more_dinner.setText(lv_dinner.isOpening() ? "更多" : "收起" );
                    iv_more_dinner.setImageResource(lv_dinner.isOpening() ? R.drawable.all_m_closed : R.drawable.all_m_opened);
                    lv_dinner.control(!lv_dinner.isOpening());
                }
                break;
        }
    }


    /*  need call.  */
    public void initDataSet(ArrayList<Nutrition> breakfast, ArrayList<Nutrition> lunch, ArrayList<Nutrition> dinner){
        lv_breakfast.setDataSet(breakfast);
        lv_lunch.setDataSet(lunch);
        lv_dinner.setDataSet(dinner);
    }

    public void initBreakfastData(ArrayList<Nutrition> breakfast){
        lv_breakfast.setDataSet(breakfast);
    }

    public void initLunchData(ArrayList<Nutrition> lunch){
        lv_lunch.setDataSet(lunch);
    }

    public void initDinnerData(ArrayList<Nutrition> dinner){
        lv_dinner.setDataSet(dinner);
    }

    public void setDataSet(ArrayList<Nutrition> breakfast, ArrayList<Nutrition> lunch, ArrayList<Nutrition> dinner){
        lv_breakfast.appendDataSet(breakfast);
        lv_lunch.appendDataSet(lunch);
        lv_dinner.appendDataSet(dinner);
        tv_more_breakfast.setText("更多");
        tv_more_lunch.setText("更多");
        tv_more_dinner.setText("更多");
        iv_more_breakfast.setImageResource(R.drawable.all_m_closed);
        iv_more_lunch.setImageResource(R.drawable.all_m_closed);
        iv_more_dinner.setImageResource(R.drawable.all_m_closed);
    }

    public void setBreakfastData(ArrayList<Nutrition> breakfast){
        lv_breakfast.appendDataSet(breakfast);
        tv_more_breakfast.setText("更多");
        iv_more_breakfast.setImageResource(R.drawable.all_m_closed);
    }

    public void setLunchData(ArrayList<Nutrition> lunch){
        lv_lunch.appendDataSet(lunch);
        tv_more_lunch.setText("更多");
        iv_more_lunch.setImageResource(R.drawable.all_m_closed);
    }

    public void setDinnerData(ArrayList<Nutrition> dinner){
        lv_dinner.appendDataSet(dinner);
        tv_more_dinner.setText("更多");
        iv_more_dinner.setImageResource(R.drawable.all_m_closed);
    }

    public void setTitle(String title){
        tv_type.setText(title);
    }

    public void clearData(){
        lv_breakfast.clearData();
        lv_lunch.clearData();
        lv_dinner.clearData();
    }

    public void clearBreakfast(){
        lv_breakfast.clearData();
    }

    public void clearLunch(){
        lv_lunch.clearData();
    }

    public void clearDinner(){
        lv_dinner.clearData();
    }

    public int getBreakfastSize(){
        return lv_breakfast.getSize();
    }

    public int getLunchSize(){
        return lv_lunch.getSize();
    }

    public int getDinnerSize(){
        return lv_dinner.getSize();
    }

}
