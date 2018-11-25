package com.mamode.anthony.mynews.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;


public class Animations extends DecelerateInterpolator {
    public static void pullArrowAnimation(ImageView arrowView) {
        ValueAnimator animation = ValueAnimator.ofFloat(0f, 100f);
        animation.setDuration(1000);
        animation.setStartDelay(600);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.addUpdateListener((ValueAnimator valueAnimator) -> {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                arrowView.setTranslationY(animatedValue);
            });
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                pullArrowAnimation(arrowView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animation.start();
    }
}
