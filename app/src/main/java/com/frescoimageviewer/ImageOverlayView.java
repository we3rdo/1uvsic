package com.frescoimageviewer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kgv.cookbook.R;


public class ImageOverlayView extends RelativeLayout {

    private TextView tvDescription;

    public ImageOverlayView(Context context) {
        super(context);
        init();
    }

    public ImageOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setDescription(String description) {
        tvDescription.setText(description);
    }

    private void init() {
        View view = inflate(getContext(), R.layout.view_image_overlay, this);
        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
    }
}
