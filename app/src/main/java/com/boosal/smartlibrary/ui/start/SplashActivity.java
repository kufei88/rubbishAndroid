package com.boosal.smartlibrary.ui.start;

import android.os.Bundle;
import android.os.Handler;

import com.boosal.smartlibrary.R;
import com.boosal.smartlibrary.base.baseUI.BaseActivity;
import com.boosal.smartlibrary.ui.main.activity.HomeMainActivity;

public class SplashActivity extends BaseActivity {

    private Runnable next_Runnable = new Runnable() {
        @Override
        public void run() {
            startToMain();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_splash);
    }

    @Override
    protected void initViews() {
        //停留1秒后跳转到首页
        new Handler().postDelayed(next_Runnable, 1000);
    }

    @Override
    protected void init() {
        setStatusBar(this);
        hideTopBar(true);
    }

    @Override
    protected void initEvents() {

    }

    private void startToMain(){
        startActivity(HomeMainActivity.class);
    }

}
