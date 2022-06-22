package com.ain.trading.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class AINCustomViewpager extends ViewPager {

    private boolean enableSwipe;

    public AINCustomViewpager(Context context) {
        super(context);
        init();
    }

    public AINCustomViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        enableSwipe = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return enableSwipe && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return enableSwipe && super.onTouchEvent(event);

    }

    public void setEnableSwipe(boolean enableSwipe) {
        this.enableSwipe = enableSwipe;
    }
}
