package com.npay.hackathon.npay;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-09-08.
 */

public class CustomTabLayout extends TabLayout {

    private boolean touchFlag = true;

    public CustomTabLayout(Context context) {
        super(context);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.touchFlag && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return this.touchFlag && super.onTouchEvent(ev);
    }

    public void setTouchFlag(boolean touchFlag) {
        this.touchFlag = touchFlag;
    }

}
