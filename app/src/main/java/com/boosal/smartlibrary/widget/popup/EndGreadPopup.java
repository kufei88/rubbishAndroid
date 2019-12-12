package com.boosal.smartlibrary.widget.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.boosal.smartlibrary.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by boosal on 2019/9/6.
 */

public class EndGreadPopup extends BasePopupWindow implements View.OnClickListener{

    private TextView tipTv;



    public interface DeleteBookListener{
        void confirm();
    }

    public EndGreadPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);

        bindEvent();
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
        return createPopupById(R.layout.endtest);
    }

    private void bindEvent() {
        tipTv = findViewById(R.id.tv_delete_book);

//        findViewById(R.id.lin_cancel).setOnClickListener(this);
        findViewById(R.id.lin_confirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.lin_cancel:
//                dismiss();
//                break;
            case R.id.lin_confirm:
                dismiss();
                break;
            default:
                break;
        }
    }

    public void setComm(String gread){
        String tip = "本次测试结果："+gread;

        tipTv.setText(tip);

    }
}
