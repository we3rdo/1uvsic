package com.frescoimageviewer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewConfiguration;


final class AnimationUtils {
    private AnimationUtils() {
        throw new AssertionError();
    }

    public static void animateVisibility(final View view) {
        final boolean isVisible = view.getVisibility() == View.VISIBLE;
        float from = isVisible ? 1.0f : 0.3f,
                to = isVisible ? 0.3f : 1.0f;

        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "alpha", from, to);
        animation.setDuration(ViewConfiguration.getDoubleTapTimeout());

        if (isVisible) {
            animation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                }
            });
        } else view.setVisibility(View.VISIBLE);

        animation.start();
    }

}
