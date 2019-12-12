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

public class DeleteBookPopup extends BasePopupWindow implements View.OnClickListener{

    private TextView tipTv;
    private DeleteBookListener listner;

    public interface DeleteBookListener{
        void confirm();
    }

    public DeleteBookPopup(Context context, DeleteBookListener listner) {
        super(context);
        setPopupGravity(Gravity.CENTER);
        this.listner = listner;
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
        return createPopupById(R.layout.popup_delete_book);
    }

    private void bindEvent() {
        tipTv = findViewById(R.id.tv_delete_book);
        findViewById(R.id.lin_cancel).setOnClickListener(this);
        findViewById(R.id.lin_confirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_cancel:
                dismiss();
                break;
            case R.id.lin_confirm:
                dismiss();
                if(null != listner){
                    listner.confirm();
                }
                break;
            default:
                break;
        }
    }

    public void setComm(String bookName){
        String tip = "是否删除《" + bookName + "》？";
        tipTv.setText(tip);
    }
}
