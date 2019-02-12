package com.eng.asu.adaptivelearning.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * @author Muhammed Sabry
 */
public class CustomViewPager extends ViewPager {
    public static final int PAGER_SWIPE_LEFT = 0;
    public static final int PAGER_SWIPE_RIGHT = 1;

    boolean swipeEnabled = false;

    private OnSwipeListener onSwipeListener;
    private float lastX = -1;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (swipeEnabled) {
            boolean enableThisSwipe = true;

            if (onSwipeListener != null) {
                enableThisSwipe = checkIfThisSwipeAllowed(event);
            }

            if (enableThisSwipe) {
                return super.onTouchEvent(event);
            }
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (swipeEnabled) {
            boolean enableThisSwipe = true;

            if (onSwipeListener != null)
                enableThisSwipe = checkIfThisSwipeAllowed(event);

            if (enableThisSwipe) {
                try {
                    return super.onInterceptTouchEvent(event);
                } catch (IllegalArgumentException e) {
                    return false;
                }
            }
        }

        return false;
    }

    private boolean checkIfThisSwipeAllowed(MotionEvent event) {
        boolean enableThisSwipe = true;
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (lastX == -1) {
                enableThisSwipe = false;
            } else {
                if (lastX > event.getX()) {
                    enableThisSwipe = onSwipeListener.onSwipe(PAGER_SWIPE_RIGHT);
                } else {
                    enableThisSwipe = onSwipeListener.onSwipe(PAGER_SWIPE_LEFT);
                }
            }

            lastX = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            lastX = -1;
        }
        return enableThisSwipe;
    }

    public void setSwipeEnabled(boolean enabled, OnSwipeListener listener) {
        swipeEnabled = enabled;
        this.onSwipeListener = listener;
    }

    public interface OnSwipeListener {
        boolean onSwipe(int direction);
    }
}

