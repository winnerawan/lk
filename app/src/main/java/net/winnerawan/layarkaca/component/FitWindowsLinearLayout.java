/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.component;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.FitWindowsViewGroup;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @hide
 */
public class FitWindowsLinearLayout extends LinearLayout implements FitWindowsViewGroup {

    private OnFitSystemWindowsListener mListener;

    public FitWindowsLinearLayout(Context context) {
        super(context);
    }

    public FitWindowsLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnFitSystemWindowsListener(OnFitSystemWindowsListener listener) {
        mListener = listener;
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        if (mListener != null) {
            mListener.onFitSystemWindows(insets);
        }
        return super.fitSystemWindows(insets);
    }
}
