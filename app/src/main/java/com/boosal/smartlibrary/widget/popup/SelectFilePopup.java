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

public class SelectFilePopup extends BasePopupWindow implements View.OnClickListener{

    private TextView nameTv;
    private String path;
    private SelectFileListner listner;

    public interface SelectFileListner{
        void selectFile();
        void confirm(String path);
    }

    public SelectFilePopup(Context context,SelectFileListner listner) {
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
        return createPopupById(R.layout.popup_select_file);
    }

    private void bindEvent() {
        nameTv = findViewById(R.id.tv_file_name);
        findViewById(R.id.lin_cancel).setOnClickListener(this);
        findViewById(R.id.lin_confirm).setOnClickListener(this);
        findViewById(R.id.rl_select_again).setOnClickListener(this);
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
                    listner.confirm(path);
                }
                break;
            case R.id.rl_select_again:
                if(null != listner){
                    listner.selectFile();
                }
                break;
            default:
                break;
        }
    }

    public void setComm(String fileName,String path){
        this.path = path;
        nameTv.setText(fileName);
    }
}
