package com.boosal.smartlibrary.base.baseUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.boosal.smartlibrary.R;
import com.boosal.smartlibrary.base.baseMVP.view.IBaseView;
import com.boosal.smartlibrary.utils.ScreenUtil;
import com.boosal.smartlibrary.utils.loading.KprogresshudUtils;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseFragment extends SupportFragment implements IBaseView {
    protected Activity mContext;
    protected boolean isSetStatusBar = false;
    protected View mView;
    protected LinearLayout layout_content;
    protected View top_bar;

    protected View layout;
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

    public BaseFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_base, null);
        layout_content = (LinearLayout) mView.findViewById(R.id.content_container);
        top_bar = (View) mView.findViewById(R.id.included_top_bar);
        return mView;
    }

    protected View setView(int layoutId) {
        layout = (View) View.inflate(getActivity(), layoutId, null);
        layout_content.removeAllViews();
        layout_content.addView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this, mView);
        mContext = getActivity();
        initViews();
        init();
        initEvents();
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract void initViews();

    protected abstract void init();

    protected abstract void initEvents();

    protected void hideTopBar(boolean isHide) {
        if (!isHide) {
            top_bar.setVisibility(View.VISIBLE);
        } else {
            top_bar.setVisibility(View.GONE);
        }
    }

    public View findViewById(int id) {
        return mView.findViewById(id);
    }

    public void setTopBarBackground(int colorId) {
        rvHeader.setBackgroundColor(this.getResources().getColor(colorId));
    }

    public void setTopBarBackgroundDrawable(int resid) {
        rvHeader.setBackground(this.getResources().getDrawable(resid));
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

    public void setRightButtonImage(int resId, Button Btn) {
        Drawable rightDrawable = getResources().getDrawable(resId);
        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        Btn.setCompoundDrawables(null, null, rightDrawable, null);
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
        tvTitle.setText(text + "");
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

    public void setTopTitle(String text, int colorId, int drawableId) {
        tvTitle.setTextColor(this.getResources().getColor(colorId));
        tvTitle.setText(text + "");
        Drawable leftDrawable = getResources().getDrawable(drawableId);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        tvTitle.setCompoundDrawables(leftDrawable, null, null, null);
    }

    public void setTopTitle(Button tvTitle,int resId, int colorId, int drawableId) {
        tvTitle.setTextColor(this.getResources().getColor(colorId));
        tvTitle.setText(resId);
        Drawable leftDrawable = getResources().getDrawable(drawableId);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        tvTitle.setCompoundDrawables(leftDrawable, null, null, null);
    }

    public void setTopTitle(Button tvTitle,String text, int colorId, int drawableId) {
        tvTitle.setTextColor(this.getResources().getColor(colorId));
        tvTitle.setText(text + "");
        Drawable leftDrawable = getResources().getDrawable(drawableId);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        tvTitle.setCompoundDrawables(leftDrawable, null, null, null);
    }

    /**
     * 短暂显示Toast提示(来自res)
     **/
    protected void showShortToast(int resId) {
        Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_SHORT).show();
     }

     /**
     * 短暂显示Toast提示(来自String)
     **/
    protected void showShortToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    public void showLongToast(int resId) {
        Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    protected void startActivity(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        getActivity().startActivity(intent);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivity(intent);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(getContext(), cls);
        getActivity().startActivity(intent);
    }

    /**
     * 含有Bundle通过Action跳转界面
     **/
    protected void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
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
            } catch (java.lang.InstantiationException e) {
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
        if (Build.VERSION.SDK_INT >= 21) {
            View statusBar = (View) findViewById(R.id.SysStatusBar);
            LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.width = LayoutParams.MATCH_PARENT;
            layoutParams.height = getStatusHeight(activity);
            statusBar.setLayoutParams(layoutParams);
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getActivity().getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //由子布局的statusBar动态设置状态栏高度
    public void setStatusBar(Activity activity, View barView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LayoutParams layoutParams = barView.getLayoutParams();
            layoutParams.width = LayoutParams.MATCH_PARENT;
            layoutParams.height = getStatusHeight(activity);
            barView.setLayoutParams(layoutParams);
        }
    }

    public void setStatusTextBlack(boolean isBlack) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (isBlack) {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
            } else {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//恢复状态栏白色字体
            }
        }
    }

    public boolean isSetStatusBar() {
        return isSetStatusBar;
    }

    public void setIsSetStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
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
    }

    public void onRightButtonClick(View view) {
    }

    public void onExtraButtonClick(View view) {

    }

    @Override
    public void showProgressBar() {
        KprogresshudUtils.show(getActivity());
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

}
