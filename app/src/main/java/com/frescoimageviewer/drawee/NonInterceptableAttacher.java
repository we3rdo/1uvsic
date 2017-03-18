package com.frescoimageviewer.drawee;

import android.view.ViewParent;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.DraweeView;

import me.relex.photodraweeview.Attacher;

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
class NonInterceptableAttacher extends Attacher {

    public NonInterceptableAttacher(DraweeView<GenericDraweeHierarchy> draweeView) {
        super(draweeView);
    }

    @Override
    public void onDrag(float dx, float dy) {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null) {
            getDrawMatrix().postTranslate(dx, dy);
            checkMatrixAndInvalidate();

            ViewParent parent = draweeView.getParent();
            if (parent == null) {
                return;
            }

            if (getScale() == 1.0f) {
                parent.requestDisallowInterceptTouchEvent(false);
            } else {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
