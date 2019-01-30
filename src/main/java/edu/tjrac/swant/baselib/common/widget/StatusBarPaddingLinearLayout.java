package edu.tjrac.swant.baselib.common.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import edu.tjrac.swant.baselib.util.UiUtil;

/**
 * Created by wpc on 2018-10-11.
 */

public class StatusBarPaddingLinearLayout extends LinearLayout {

    public StatusBarPaddingLinearLayout(Context context) {
        super(context);
    }

    public StatusBarPaddingLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusBarPaddingLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (Build.VERSION.SDK_INT >= 19) {
            setPadding(0, UiUtil.getStatusBarHeight(getContext()),0,0);
//            setMeasuredDimension(widthMeasureSpec, UiUtil.getStatusBarHeight(context))
        } else {
//            setMeasuredDimension(widthMeasureSpec, 0)
        }
    }
}
