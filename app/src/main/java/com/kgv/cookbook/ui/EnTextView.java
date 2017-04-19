package com.kgv.cookbook.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class EnTextView extends TextView {

    public EnTextView(Context context) {
        super(context);
        init(context);
    }

    public EnTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EnTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        Typeface newFont = Typeface.createFromAsset(context.getAssets(), "en.TTF");
        setTypeface(newFont);
    }

}