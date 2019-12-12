package com.boosal.smartlibrary.ui.main.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.boosal.smartlibrary.R;
import com.boosal.smartlibrary.base.baseUI.BaseActivity;
import com.boosal.smartlibrary.ui.entering.activity.EnterActivity;
import com.boosal.smartlibrary.ui.recognize.activity.RecognizeActivity;
import com.boosal.smartlibrary.ui.setting.activity.SettingActivity;
import com.boosal.smartlibrary.utils.Logger;
import com.boosal.smartlibrary.utils.ScreenUtil;

import butterknife.OnClick;

public class HomeMainActivity extends BaseActivity {

    public static HomeMainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_home_main);
        instance = this;
    }

    @Override
    protected void initViews() {
        setStatusBar(this);
        hideTopBar(false);
        setTopTitle(R.string.top_title_main, R.color.themeWhite);
        setLeftButtonImage(R.drawable.set);
        setLeftButtonText(R.string.setting);
        setRightButtonImage(R.drawable._import);
        setRightButtonText(R.string._import, R.color.themeWhite);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initEvents() {

    }

    //去识别
    @OnClick(R.id.home_btn_gotorecognize)
    public void onViewClicked() {
        startActivity(RecognizeActivity.class);
    }

    //导入
    @Override
    public void onRightButtonClick(View view) {
        startActivity(EnterActivity.class);
    }

    //设置
    @Override
    public void onLeftButtonClick(View view) {
        startActivity(SettingActivity.class);
    }

    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
