package com.boosal.smartlibrary.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boosal.smartlibrary.R;

/**
 * Created by boosal on 2019/9/6.
 */

public class CommBottom extends RelativeLayout implements View.OnClickListener,TextWatcher{

    private Button previousBtn,jumpBtn,nextBtn;
    private EditText currentPageEt;
    private TextView totalTv;
    private CommBottomListener listener;

    //当前页数，总页数
    private int currentPage,totalPage;


    public interface CommBottomListener{
        void previousClick();
        void jumpviousClick(int etPage,int total);
        void nextClick();
    }

    public CommBottom(Context context) {
        super(context);
        initView(context);
    }

    public CommBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CommBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.common_bottom, this, true);
        previousBtn = findViewById(R.id.commbottom_btn_previous);
        jumpBtn = findViewById(R.id.commbottom_btn_jump);
        nextBtn = findViewById(R.id.commbottom_btn_next);
        currentPageEt = findViewById(R.id.commbottom_et_current);
        totalTv = findViewById(R.id.commbottom_tv_total);

        previousBtn.setOnClickListener(this);
        jumpBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        currentPageEt.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.commbottom_btn_previous:
                if(null != listener){
                    listener.previousClick();
                }
                break;
            case R.id.commbottom_btn_jump:
                if(null != listener){
                    String currentPage = currentPageEt.getText().toString().trim();
                    if(!TextUtils.isEmpty(currentPage)){
                        listener.jumpviousClick(Integer.parseInt(currentPage),totalPage);
                    }
                }
                break;
            case R.id.commbottom_btn_next:
                if(null != listener){
                    listener.nextClick();
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!TextUtils.isEmpty(s.toString().trim())){
            int et = Integer.parseInt(s.toString().trim());
            if(et > totalPage){
                currentPageEt.setText(String.valueOf(totalPage));
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setListener(CommBottomListener listener){
        this.listener = listener;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
        totalTv.setText("页 / " + totalPage);
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        if(currentPage > totalPage){
            return;
        }
        currentPageEt.setText(String.valueOf(currentPage));
    }

    public EditText getCurrentPageEt() {
        return currentPageEt;
    }
}
