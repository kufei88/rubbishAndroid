package com.boosal.smartlibrary.base.baseApp;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.boosal.smartlibrary.EventBus.ReLoginEvent;
import com.facebook.stetho.Stetho;
import com.rfid.api.ADReaderInterface;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

public class BaseApplication extends MultiDexApplication {

    public static BaseApplication mApplication;
    private static BaseApplication instance;
    public static Context context;
    private static String token="";

    private static ADReaderInterface tagReader = new ADReaderInterface();

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        instance = this;
        context = getApplicationContext();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        //初始化友盟
        initUmeng();
        //初始化数据库
        LitePal.initialize(this);
        //初始化Stetho
        Stetho.initializeWithDefaults(this);
        //打开串口
        initTagReader();
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        BaseApplication.token = token;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        BaseApplication.context = context;
    }

    public static BaseApplication getApplication() {
        return instance;
    }

    public static void clearToken() {
        BaseApplication.token = "";
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ReLoginEvent event) {
        clearToken();
    }


    private void initUmeng(){
        //上下文 appkey channel 设备类型 push业务的Secret
        //友盟初始化
        UMConfigure.init(this,Constant.Umeng_AppId
                ,"umeng", UMConfigure.DEVICE_TYPE_PHONE,"");
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    public static ADReaderInterface getTagReader() {
        return tagReader;
    }

    public void initTagReader(){
        tagReader.RDR_Open("RDType=M201;CommType=COM;ComPath=/dev/ttyS4;Baund=38400;Frame=8E1;Addr=255");
    }

}