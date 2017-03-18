package com.frescoimageviewer;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.kgv.cookbook.R;

import java.util.List;


class ImageViewerView extends RelativeLayout
        implements OnDismissListener, SwipeToDismissListener.OnViewMoveListener {

    private View backgroundView;
    private MultiTouchViewPager pager;
    private ImageViewerAdapter adapter;
    private SwipeDirectionDetector directionDetector;
    private ScaleGestureDetector scaleDetector;
    private ViewPager.OnPageChangeListener pageChangeListener;
    private GestureDetectorCompat gestureDetector;

    private ViewGroup dismissContainer;
    private SwipeToDismissListener swipeDismissListener;
    private View overlayView;

    private SwipeDirectionDetector.Direction direction;

    private GenericDraweeHierarchyBuilder customDraweeHierarchyBuilder;

    private boolean wasScaled;
    private OnDismissListener onDismissListener;

    public ImageViewerView(Context context) {
        super(context);
        init();
    }

    public ImageViewerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageViewerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setUrls(List<String> urls, int startPosition) {
        adapter = new ImageViewerAdapter(
                getContext(), urls, customDraweeHierarchyBuilder);
        pager.setAdapter(adapter);
        setStartPosition(startPosition);
    }

    public void setCustomDraweeHierarchyBuilder(GenericDraweeHierarchyBuilder customDraweeHierarchyBuilder) {
        this.customDraweeHierarchyBuilder = customDraweeHierarchyBuilder;
    }

    @Override
    public void setBackgroundColor(int color) {
        findViewById(R.id.backgroundView)
                .setBackgroundColor(color);
    }

    // modified
    public void setOverlayView(View view) {
        this.overlayView = view;
        if (overlayView != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null){
                parent.removeAllViews();
            }
            dismissContainer.addView(view);
        }
    }

    public void setImageMargin(int marginPixels) {
        pager.setPageMargin(marginPixels);
    }

    private void init() {
        inflate(getContext(), R.layout.image_viewer, this);

        backgroundView = findViewById(R.id.backgroundView);
        pager = (MultiTouchViewPager) findViewById(R.id.pager);

        dismissContainer = (ViewGroup) findViewById(R.id.container);
        swipeDismissListener = new SwipeToDismissListener(findViewById(R.id.dismissView), this, this);
        dismissContainer.setOnTouchListener(swipeDismissListener);

        directionDetector = new SwipeDirectionDetector(getContext()) {
            @Override
            public void onDirectionDetected(Direction direction) {
                ImageViewerView.this.direction = direction;
            }
        };

        scaleDetector = new ScaleGestureDetector(getContext(),
                new ScaleGestureDetector.SimpleOnScaleGestureListener());

        gestureDetector = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                onClick(e, isOverlayWasClicked);
                return super.onSingleTapConfirmed(e);
            }
        });
    }

    boolean isOverlayWasClicked = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            onActionDown(event);
            isOverlayWasClicked = dispatchOverlayTouch(event);
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            onActionUp(event);
            isOverlayWasClicked = dispatchOverlayTouch(event);
        }

        scaleDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);

        if (direction == null) {
            if (scaleDetector.isInProgress() || event.getPointerCount() > 1) {
                wasScaled = true;
                return pager.dispatchTouchEvent(event);
            }
        }

        if (!adapter.isScaled(pager.getCurrentItem())) {
            directionDetector.onTouchEvent(event);
            if (direction != null) {
                switch (direction) {
                    case UP:
                    case DOWN:
                        if (!wasScaled && pager.isScrolled()) {
                            return swipeDismissListener.onTouch(dismissContainer, event);
                        } else break;
                    case LEFT:
                    case RIGHT:
                        return pager.dispatchTouchEvent(event);
                }
            }
            return true;
        }
        return super.dispatchTouchEvent(event);
    }


    @Override
    public void onDismiss() {
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    @Override
    public void onViewMove(float translationY, int translationLimit) {
        float alpha = 1.0f - (1.0f / translationLimit / 4) * Math.abs(translationY);
        backgroundView.setAlpha(alpha);
        if (overlayView != null) overlayView.setAlpha(alpha);
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public void resetScale() {
        adapter.resetScale(pager.getCurrentItem());
    }

    public boolean isScaled() {
        return adapter.isScaled(pager.getCurrentItem());
    }

    public String getUrl() {
        return adapter.getUrl(pager.getCurrentItem());
    }

    public void setPageChangeListener(ViewPager.OnPageChangeListener pageChangeListener) {
        pager.removeOnPageChangeListener(this.pageChangeListener);
        this.pageChangeListener = pageChangeListener;
        pager.addOnPageChangeListener(pageChangeListener);
        pageChangeListener.onPageSelected(pager.getCurrentItem());
    }

    private void setStartPosition(int position) {
        pager.setCurrentItem(position);
    }

    private void onActionDown(MotionEvent event) {
        direction = null;
        wasScaled = false;
        pager.dispatchTouchEvent(event);
        swipeDismissListener.onTouch(dismissContainer, event);
    }

    private void onActionUp(MotionEvent event) {
        swipeDismissListener.onTouch(dismissContainer, event);
        pager.dispatchTouchEvent(event);
    }

    private void onClick(MotionEvent event, boolean isOverlayWasClicked) {
        if (overlayView != null && !isOverlayWasClicked) {
            AnimationUtils.animateVisibility(overlayView);
            super.dispatchTouchEvent(event);
        }
    }

    private boolean dispatchOverlayTouch(MotionEvent event) {
        return overlayView != null
                && overlayView.getVisibility() == VISIBLE
                && overlayView.dispatchTouchEvent(event);
    }

}
