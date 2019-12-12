package com.boosal.smartlibrary.widget.popup;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.boosal.smartlibrary.R;
import com.boosal.smartlibrary.net.entity.local.book.Book;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by boosal on 2019/9/6.
 */

public class RelatedSuccessPopup extends BasePopupWindow implements View.OnClickListener{

    private TextView nameTv,categoryTv;
    private Activity activity;

    public RelatedSuccessPopup(Context context,Activity activity) {
        super(context);
        this.activity = activity;
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
        return createPopupById(R.layout.popup_relate_book);
    }

    private void bindEvent() {
        nameTv = findViewById(R.id.relate_tv_name);
        categoryTv = findViewById(R.id.relate_tv_category);
        findViewById(R.id.lin_confirm).setOnClickListener(this);
    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_confirm:
                dismiss();
                break;
            default:
                break;
        }
    }

    public void setComm(Book book){
        if(book != null){
            nameTv.setText(book.getName());
            categoryTv.setText(book.getCategory_name());
        }
    }
}
