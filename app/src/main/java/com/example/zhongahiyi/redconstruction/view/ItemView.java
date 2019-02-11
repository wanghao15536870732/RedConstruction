package com.example.zhongahiyi.redconstruction.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.zhongahiyi.redconstruction.bean.NineGridModel;

public class ItemView extends LinearLayout implements OnClickListener {

    private int position;
    private NineGridModel mModel;

    private PopupWindow mPopupWindow;
    private int mShowMorePopupWindowWidth;
    private int mShowMorePopupWindowHeight;

    public ItemView(Context context) {
        super( context );
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        super( context, attrs );
    }

    @Override
    public void onClick(View v) {

    }
}
