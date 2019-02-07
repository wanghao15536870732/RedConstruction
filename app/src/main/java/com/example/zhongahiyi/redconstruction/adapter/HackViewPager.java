package com.example.zhongahiyi.redconstruction.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HackViewPager extends ViewPager {
    private boolean isLocked;

    public HackViewPager(@NonNull Context context) {
        super( context );
        isLocked = false;
    }

    public HackViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super( context, attrs );
        isLocked = false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isLocked){
            try {
                return super.onInterceptTouchEvent( ev );
            }catch (IllegalArgumentException e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isLocked) {
            return super.onTouchEvent( ev );
        }
        return false;
    }
    public void toggleLock() {
        isLocked = !isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean getLocked() {
        return isLocked;
    }
}
