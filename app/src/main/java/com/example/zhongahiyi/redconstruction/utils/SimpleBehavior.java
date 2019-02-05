package com.example.zhongahiyi.redconstruction.utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class SimpleBehavior extends CoordinatorLayout.Behavior<Button> {

    private Context mContext;
    private int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;

    public SimpleBehavior(Context context, AttributeSet attrs){
        super();
        mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        return true;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button child, View dependency) {
        int x = screenWidth - dependency.getScrollX() - child.getWidth();
        setPosition( child,x,dependency.getScrollY());
        return true;
    }

    private void setPosition(View v,int x,int y){
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) v.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        v.setLayoutParams( layoutParams );
    }

}
