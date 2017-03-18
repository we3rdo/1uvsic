package com.kgv.cookbook.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by 陈可轩 on 2017/2/23 14:16
 * Email : 18627241899@163.com
 * Description :
 */
public class ListView4ScrollView extends ListView{


    public ListView4ScrollView(Context context) {
        super(context);
    }

    public ListView4ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListView4ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
