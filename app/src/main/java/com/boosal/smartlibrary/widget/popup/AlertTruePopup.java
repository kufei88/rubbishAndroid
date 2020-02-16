package com.boosal.smartlibrary.widget.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;

import com.boosal.smartlibrary.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by boosal on 2019/9/6.
 */

public class AlertTruePopup extends BasePopupWindow{

    public AlertTruePopup(Context context)  {
            super(context);
            setPopupGravity(Gravity.CENTER);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.truealert);
    }



}
