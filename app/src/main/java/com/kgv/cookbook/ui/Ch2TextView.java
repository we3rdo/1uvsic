package com.kgv.cookbook.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class Ch2TextView extends TextView {

    public Ch2TextView(Context context) {
        super(context);
        init(context);
    }

    public Ch2TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Ch2TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        Typeface newFont = Typeface.createFromAsset(context.getAssets(), "b.TTF");
        setTypeface(newFont);
    }

}