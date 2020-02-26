package com.bast.lamzone.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Slider extends LinearLayout {
    /* Vitesse désirée pour l'animation */
    protected final static int SPEED = 300;
    protected boolean isOpen = true;
    protected ConstraintLayout boxfilter = null;

    Animation.AnimationListener closeListener = new Animation.AnimationListener() {
        public void onAnimationEnd(Animation animation) {
            boxfilter.setVisibility(View.GONE);
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    };

    Animation.AnimationListener openListener = new Animation.AnimationListener() {
        public void onAnimationEnd(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
            boxfilter.setVisibility(View.VISIBLE);
        }
    };

    public Slider(Context context) {
        super(context);
    }

    public Slider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean toggle() {
        //Animation de transition.
        TranslateAnimation animation = null;

        isOpen = !isOpen;

        if (isOpen) {
            animation = new TranslateAnimation(0.0f, 0.0f,
                    -boxfilter.getHeight(), 0.0f);
            animation.setAnimationListener(openListener);
        } else {
            animation = new TranslateAnimation(0.0f, 0.0f,
                    0.0f, -boxfilter.getHeight());
            animation.setAnimationListener(closeListener);
        }

        animation.setDuration(SPEED);
        animation.setInterpolator(new AccelerateInterpolator());
        startAnimation(animation);

        return isOpen;
    }


    public void setToHide(ConstraintLayout boxfilter) {
        this.boxfilter = boxfilter;
    }
}
