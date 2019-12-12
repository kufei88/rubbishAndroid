package com.boosal.smartlibrary.base.baseUI;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.boosal.smartlibrary.R;
import com.boosal.smartlibrary.base.baseMVP.view.IBaseView;
import com.boosal.smartlibrary.utils.ScreenUtil;
import com.boosal.smartlibrary.utils.loading.KprogresshudUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseActivity extends SupportActivity implements IBaseView {

    protected LinearLayout layout_content;
    protected ViewGroup content;
    protected View top_bar;
    protected BaseActivity activity;
    protected Context mContext;
    @BindView(R.id.btnLeft)
    Button btnLeft;
    @BindView(R.id.tvTitle)
    Button tvTitle;
    @BindView(R.id.btnRight)
    Button btnRight;
    @BindView(R.id.btnExtra)
    Button btnExtra;
    @BindView(R.id.rv_header)
    RelativeLayout rvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_base);
        layout_content = findViewById(R.id.content_container);
        top_bar = findViewById(R.id.included_top_bar);
        activity = this;
        mContext = this;
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    protected abstract void initViews();

    protected abstract void init();

    protected abstract void initEvents();

    protected void setView(int layoutId) {
        content = (ViewGroup) View.inflate(this, layoutId, null);
        layout_content.removeAllViews();
        layout_content.addView(content, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this);
        initViews();
        init();
        initEvents();
    }

    protected void hideTopBar(boolean isHide) {
        if (!isHide) {
            top_bar.setVisibility(View.VISIBLE);
        } else {
            top_bar.setVisibility(View.GONE);
        }
    }

    public void setTopBarBackground(int colorId) {
        rvHeader.setBackgroundColor(this.getResources().getColor(colorId));
    }

    protected void showShortToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    protected void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    public void showLongToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    protected void startActivity(Class<?> cls, int flags) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.setFlags(flags);
        startActivity(intent);
    }

    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    protected void startActivity(String action) {
        startActivity(action, null);
    }

    protected void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    protected void defaultFinish() {
        super.finish();
    }

    public void setLeftButtonImage(int resId) {
        btnLeft.setVisibility(View.VISIBLE);
        Drawable leftDrawable = getResources().getDrawable(resId);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        btnLeft.setCompoundDrawables(leftDrawable, null, null, null);
    }

    public void setLeftButtonImage(int resId, Button btn) {
        Drawable leftDrawable = getResources().getDrawable(resId);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        btn.setCompoundDrawables(leftDrawable, null, null, null);
    }

    public void setLeftButtonText(String text) {
        if (TextUtils.isEmpty(text)) {
            btnLeft.setVisibility(View.GONE);
        } else {
            btnLeft.setVisibility(View.VISIBLE);
            btnLeft.setText(text + "");
        }
    }

    public void setLeftButtonText(int resId) {
        btnLeft.setText(resId);
    }

    public void setRightButtonImage(int resId) {
        Drawable rightDrawable = getResources().getDrawable(resId);
        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        btnRight.setCompoundDrawables(null, null, rightDrawable, null);
    }

    public void setRightButtonText(String text, int colorId) {
        btnRight.setTextColor(this.getResources().getColor(colorId));
        btnRight.setText(text + "");
    }

    public void setRightButtonText(int resId, int colorId) {
        btnRight.setTextColor(this.getResources().getColor(colorId));
        btnRight.setText(resId);
    }

    public void setExtraButtonImage(Integer resId) {
        if (resId != null) {
            //动态设置第二个按钮的宽度，解决两个按钮重叠冲突
            btnExtra.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btnRight.getLayoutParams();
            layoutParams.width = ScreenUtil.dp2px(mContext, 44);
            btnRight.setLayoutParams(layoutParams);
        }
        Drawable rightDrawable = getResources().getDrawable(resId);
        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        btnExtra.setCompoundDrawables(null, null, rightDrawable, null);
    }

    public void setTopTitle(String text, int colorId) {
        tvTitle.setTextColor(this.getResources().getColor(colorId));
        tvTitle.setText(text);
    }

    public void setTopTitle(int resId, int colorId) {
        tvTitle.setTextColor(this.getResources().getColor(colorId));
        tvTitle.setText(resId);
    }

    public void setTopTitle(int resId, int colorId, int drawableId) {
        tvTitle.setTextColor(this.getResources().getColor(colorId));
        tvTitle.setText(resId);
        Drawable leftDrawable = getResources().getDrawable(drawableId);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        tvTitle.setCompoundDrawables(leftDrawable, null, null, null);
    }

    public void setTopTitle(Button tvTitle,int resId, int colorId) {
        tvTitle.setTextColor(this.getResources().getColor(colorId));
        tvTitle.setText(resId);
    }

    public int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    //设置沉浸式状态栏
    public void setStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View statusBar = (View) findViewById(R.id.SysStatusBar);
            ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.width = 0;
            layoutParams.height = getStatusHeight(activity);
            statusBar.setLayoutParams(layoutParams);
        }
    }

    //由子布局的statusBar动态设置状态栏高度
    public void setStatusBar(Activity activity, View barView) {
        if (Build.VERSION.SDK_INT >= 21) {
            ViewGroup.LayoutParams layoutParams = barView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = getStatusHeight(activity);
            barView.setLayoutParams(layoutParams);
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //6.0后设置状态栏字体颜色
    public void setStatusTextBlack(boolean isBlack) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (isBlack) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//恢复状态栏白色字体
            }
        }
    }

    @OnClick({R.id.btnLeft, R.id.btnRight, R.id.btnExtra})
    public void onTopMenuClick(View view) {
        switch (view.getId()) {
            case R.id.btnLeft:
                onLeftButtonClick(view);
                break;
            case R.id.btnRight:
                onRightButtonClick(view);
                break;
            case R.id.btnExtra:
                onExtraButtonClick(view);
                break;
        }
    }

    public void onLeftButtonClick(View view) {
        defaultFinish();
    }

    public void onRightButtonClick(View view) {

    }

    public void onExtraButtonClick(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showProgressBar() {
        KprogresshudUtils.show(this);
    }

    @Override
    public void hideProgressBar() {
        KprogresshudUtils.dismiss();
    }

    @Override
    public void showMsg(String msg) {
        showShortToast(msg);
    }

    @Override
    public void showMsg(int msgRes) {
        showShortToast(msgRes);
    }

    /**
     * 请求的权限列表
     */
    private LinkedList<String> permissionList = new LinkedList<String>();

    /**
     * 请求权限
     */
    public void requestPermession(String[] permissions, IPermissionCallback callback) {

        this.callback = callback;

        if (null != permissions && permissions.length > 0) {
            //添加到队列
            permissionList.clear();
            for (int i = 0; i < permissions.length; i++) {
                permissionList.add(permissions[i]);
            }
//            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//            permissionList.add(Manifest.permission.READ_PHONE_STATE);
            //判断权限
            checkPermission();
        }
    }

    private void checkPermission() {

        if (permissionList.size() > 0) {
            String permission = permissionList.remove();
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                //权限不被允许，需要手动申请
                ActivityCompat.requestPermissions(this, new String[]{permission}, 100);
            } else {
                //这条权限被同意了，继续访问下一条
                checkPermission();
            }
        } else {
            //所有权限被同意了
            if (null != callback) {
                callback.allGranted();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //权限被允许了
                    if (null == permissionList || permissionList.size() == 0) {
                        //所有权限被同意了
                        if (null != callback) {
                            callback.allGranted();
                        }
                    } else {
                        //继续判断下一个权限
                        checkPermission();
                    }
                } else {
                    //权限被拒绝了,退出App
                    AppExit(this);
                }
                break;
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {

        try {
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            Log.i("AppManager", "退出应用程序异常!");
            StackTraceElement[] stacks = e.getStackTrace();
            StringBuffer sb = new StringBuffer();
            for (StackTraceElement stack : stacks) {
                sb.append(stack.toString() + "\n");
            }
            Log.i("AppManager", sb.toString());
            System.exit(0);
        }
    }

    private IPermissionCallback callback;

    public interface IPermissionCallback {
        public void allGranted();
    }
}
