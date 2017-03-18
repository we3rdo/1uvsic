package com.kgv.cookbook.ui;

import android.content.Context;
import android.graphics.Rect;
import android.location.Location;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import com.kgv.cookbook.R;

/**
 *  Created by ckx on 2017/3/16.
 */
public class DragFrameLayout extends RelativeLayout {

    private View view;

    private int width, height;

    private int screenWid, screenHei;

    private boolean isClickDrag = false;

    private boolean isTouchDrag = false;

    private float startX, startY;

    private CheckClick checkClick = new CheckClick();

    private DragImageClickListener dragImageListener;

    public DragImageClickListener getDragImageListener () {
        return dragImageListener;
    }

    public void setDragImageListener (DragImageClickListener dragImageListener) {
        this.dragImageListener = dragImageListener;
    }

    public interface DragImageClickListener {
        void onClick ();
    }

    private class CheckClick implements Runnable {

        @Override
        public void run () {
            isClickDrag = false;
        }

    }

    public DragFrameLayout (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DragFrameLayout (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void dragInit (View view) {
        screenWid = getWidth();
        screenHei = getHeight();
        width = view.getWidth();
        height = view.getHeight();
    }

    @Override
    public boolean onInterceptTouchEvent (MotionEvent ev) {
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                float x = ev.getX();
                float y = ev.getY();
                Rect frame = new Rect();
                if (view == null) {
                    view = findViewById(R.id.dragImg);
                    dragInit(view);
                }
                view.getHitRect(frame);
                if (frame.contains((int) (x), (int) (y))) {

                    isTouchDrag = true;
                    startX = x;
                    startY = y;
                    return true;
                }
                break;

        }
        return false;
    }

    @Override
    protected void onLayout (boolean changed, int left, int top, int right,
                             int bottom) {

        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Rect frame = new Rect();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                view.setAlpha(0.5f);
                view.getHitRect(frame);
                if (frame.contains((int) (x), (int) (y))) {
                    startX = x;
                    startY = y;
                    isTouchDrag = true;
                    isClickDrag = true;
                    postDelayed(checkClick, ViewConfiguration.getTapTimeout());
                }
                break;
            case MotionEvent.ACTION_MOVE:

                float distanX = Math.abs(x - startX);
                float distanY = Math.abs(y - startY);

                if (Math.sqrt(distanY * distanY + distanX * distanX) > 10) {
                    isClickDrag = false;
                }
                move(x, y);
                break;

            case MotionEvent.ACTION_CANCEL:
                isClickDrag = false;
                isTouchDrag = false;
                break;
            case MotionEvent.ACTION_UP:
                view.setAlpha(1f);
                if (isClickDrag) {
                    dragImageListener.onClick();
                    removeCallbacks(checkClick);
                }
                isClickDrag = false;
                isTouchDrag = false;

                // 这段是把控件吸附四周
                //          if (x > width && x < screenWid - width && y > height
                //                  && y < screenHei -  height) {
                //              int minType = minDIstance(x, y);
                //              Log.i("tags", screenHei + "==mintype=" + minType);
                //              switch (minType) {
                //              case LEFT:
                //                  x = width;
                //                  break;
                //              case RIGHT:
                //                  x = screenWid - width;
                //                  break;
                //              case TOP:
                //                  y = height;
                //                  break;
                //              case BOTTOM:
                //                  y = screenHei - height;
                //                  break;
                //              default:
                //                  break;
                //              }
                //              move(x, y);
                //          }
                break;
            case MotionEvent.ACTION_OUTSIDE:
                isClickDrag = false;
                isTouchDrag = false;
                break;
        }
        return true;
    }

    private final static int LEFT = 1;
    private final static int RIGHT = 2;
    private final static int TOP = 3;
    private final static int BOTTOM = 4;

    private int minDIstance (float x, float y) {
        boolean left, top;

        if (x <= (screenWid - x)) {
            left = true;
        } else {
            left = false;
        }
        if (y <= (screenHei - y)) {
            top = true;
        } else {
            top = false;
        }

        if (left && top) {
            if (x <= y) {
                return LEFT;
            } else {
                return TOP;
            }
        }
        if (left && ! top) {
            if (x <= (screenHei - y)) {
                return LEFT;
            } else {
                return BOTTOM;
            }
        }

        if ((! left) & top) {
            if ((screenWid - x) <= y) {
                return RIGHT;
            } else {
                return TOP;
            }
        }

        if ((! left) & (! top)) {
            if ((screenWid - x) <= (screenHei - y)) {
                return RIGHT;
            } else {
                return BOTTOM;
            }
        }
        return 0;

    }

    private void move (float x, float y) {
        int left = (int) (x - width / 2);
        int top = (int) (y - height / 2);
        if (left <= 0)
            left = 0;
        if (top <= 0)
            top = 0;

        if (left > screenWid - width)
            left = screenWid - width;
        if (top > screenHei - height)
            top = screenHei - height;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view
                .getLayoutParams();

        params.setMargins(left, top, (screenWid - left - width), (screenHei
                - top - height));

        view.setLayoutParams(params);
        requestLayout();
    }

    public double getDistance (double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0];
    }

}
