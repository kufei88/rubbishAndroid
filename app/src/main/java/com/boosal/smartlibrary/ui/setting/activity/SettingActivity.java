package com.boosal.smartlibrary.ui.setting.activity;

import android.os.Bundle;
import android.widget.SeekBar;

import com.boosal.smartlibrary.R;
import com.boosal.smartlibrary.base.baseUI.BaseActivity;
import com.boosal.smartlibrary.utils.AudioMngHelper;

import butterknife.BindView;

public class SettingActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener,VolumeChangeObserver.VolumeChangeListener{

    @BindView(R.id.setting_sk_voice)
    SeekBar settingSkVoice;

    private AudioMngHelper audioMngHelper;
    private VolumeChangeObserver mVolumeChangeObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_setting);
    }

    @Override
    protected void onResume() {
        //注册广播接收器
        mVolumeChangeObserver.registerReceiver();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //解注册广播接收器
        mVolumeChangeObserver.unregisterReceiver();
        super.onPause();
    }

    @Override
    protected void initViews() {
        setStatusBar(this);
        hideTopBar(false);
        setTopTitle(R.string.setting, R.color.themeWhite);
        setLeftButtonImage(R.drawable.back);
        setLeftButtonText(R.string.back);
    }

    @Override
    protected void init() {
        audioMngHelper = new AudioMngHelper(this);
        //实例化对象并设置监听器
        mVolumeChangeObserver = new VolumeChangeObserver(this);
        //设置系统当前音量
        settingSkVoice.setProgress(audioMngHelper.get100CurrentVolume());
    }

    @Override
    protected void initEvents() {
        settingSkVoice.setOnSeekBarChangeListener(this);
        mVolumeChangeObserver.setVolumeChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        audioMngHelper.setVoice100(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onVolumeChanged(int volume) {
        if(volume <= 100){
            settingSkVoice.setProgress(volume);
        }
    }
}
