package com.kgv.cookbook.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by 陈可轩 on 2016/12/22 10:07
 * Email : 18627241899@163.com
 * Description :
 */
public class GridView4ScrollView extends GridView{

    public GridView4ScrollView(Context context) {
        super(context);
    }

    public GridView4ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GridView4ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
