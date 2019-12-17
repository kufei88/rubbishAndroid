package com.boosal.smartlibrary.ui.recognize.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.boosal.smartlibrary.R;
import com.boosal.smartlibrary.base.baseApp.BaseApplication;
import com.boosal.smartlibrary.base.baseApp.Constant;
import com.boosal.smartlibrary.base.baseUI.BaseActivity;
import com.boosal.smartlibrary.mvp.DataBase.contract.DataBaseContract;
import com.boosal.smartlibrary.mvp.DataBase.presenter.DataBasePresenterImpl;
import com.boosal.smartlibrary.mvp.Tag.contract.TagContract;
import com.boosal.smartlibrary.mvp.Tag.presenter.TagPresenterImpl;
import com.boosal.smartlibrary.net.entity.local.book.Book;
import com.boosal.smartlibrary.net.entity.local.book.Category;
import com.boosal.smartlibrary.ui.recognize.adapter.RecognizeAdapter;
import com.boosal.smartlibrary.utils.KeyBoardUtils;
import com.boosal.smartlibrary.utils.Logger;
import com.boosal.smartlibrary.utils.MediaPlayerUtil;
import com.boosal.smartlibrary.view.CommBottom;
import com.boosal.smartlibrary.widget.popup.AlertFalsePopup;
import com.boosal.smartlibrary.widget.popup.AlertTruePopup;
import com.boosal.smartlibrary.widget.popup.DeleteBookPopup;
import com.boosal.smartlibrary.widget.popup.EndGreadPopup;
import com.boosal.smartlibrary.widget.popup.SelectCategoryPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class RecognizeActivity extends BaseActivity implements CommBottom.CommBottomListener,DataBaseContract.View,
        SelectCategoryPopup.SelectCategoryListner,TagContract.View{

    @BindView(R.id.commbottom)
    CommBottom commbottom;
    @BindView(R.id.recognize_tv_category)
    TextView recognizeTvCategory;
    @BindView(R.id.recognize_rv_books)
    RecyclerView recognizeRvBooks;

    private DataBaseContract.Presenter dbPresenter;
    private TagContract.Presenter tagPresenter;
    private SelectCategoryPopup categoryPopup;
    private EndGreadPopup cpp;//结束测试窗体
    private AlertTruePopup alertT;
    private AlertFalsePopup alertF;
    private List<Category> categories;
    private String currentCategory = "";
    private RecognizeAdapter adapter;
    private List<Book> books = new ArrayList<>();
    private int mTotalPage = 1;
    private int mCurrentPage = 1;
    private int trueCount=0;//当前投放正确的数量
    private int falseCount=0;//当前投放错的误数量

    //记录错误图书数量
    private List<Integer> errors = new ArrayList<>();

    private Map<String,Integer> map=new HashMap<String,Integer>();//记录各类垃圾的错误数量
    private List<Integer> trues=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_recognize);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tagPresenter.loopQueryTag(BaseApplication.getTagReader());
    }

    @Override
    protected void onStop() {
        super.onStop();
        tagPresenter.cancelLoop();
    }

    @Override
    protected void initViews() {
        setStatusBar(this);
        hideTopBar(false);
        setTopTitle(R.string.top_title_recognition, R.color.themeWhite);
        setLeftButtonImage(R.drawable.back);
        setLeftButtonText(R.string.back);

        //初始化map内部的值
        map.put("厨余垃圾",0);
        map.put("有害垃圾",0);
        map.put("其他垃圾",0);
        map.put("可回收垃圾",0);

        dbPresenter = new DataBasePresenterImpl(this);
        tagPresenter = new TagPresenterImpl(this);
        categoryPopup = new SelectCategoryPopup(this,this);
        cpp = new EndGreadPopup(this);
        alertT=new AlertTruePopup(this);
        alertF=new AlertFalsePopup(this);
        categories = dbPresenter.getAllCategories();

        categoryPopup.setCategories(categories);
        categoryPopup.setOutSideDismiss(false);
    }

    @Override
    protected void init() {
        adapter = new RecognizeAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recognizeRvBooks.setLayoutManager(layoutManager);
        recognizeRvBooks.setAdapter(adapter);
        //添加分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_line));
        recognizeRvBooks.addItemDecoration(divider);

        //默认第一个类别
        currentCategory = categories.get(0).getName();
        recognizeTvCategory.setText("当前类别： "+currentCategory);
    }

    @Override
    protected void initEvents() {
        commbottom.setListener(this);
    }

    //更换类别
    @OnClick(R.id.recognize_btn_change)
    public void onViewClicked() {
        categoryPopup.showPopupWindow();
    }
    //测试结束，弹出结束窗口计算分数并显示
    @OnClick(R.id.recognize_btn_grade)
    public void onViewClickedGrade() {
        float chuy=map.get("厨余垃圾");
        float youhai=map.get("有害垃圾");
        float qita=map.get("其他垃圾");
        float kehuishou=map.get("可回收垃圾");
        int i=0;
        //计算总分
        i=(map.get("厨余垃圾")+map.get("有害垃圾")+map.get("其他垃圾")+map.get("可回收垃圾"))*20;
        cpp.setComm(i+"分");
        cpp.showPopupWindow();
    }

    //类别回调
    @Override
    public void selectCategory(int position) {
        currentCategory = categories.get(position).getName();
        recognizeTvCategory.setText("当前类别： "+currentCategory);
        //清空本地，重新回调扫描结果
        tagPresenter.clearlocalSet();
    }

    //rfid回调
    @Override
    public void setTags(Set<String> tags) {
        dbPresenter.findErrorBookByrfid(tags,currentCategory);
    }

    //垃圾投放正确时发生的情况
    @Override
    public void errorBooks(List<Book> _books) {
        this.books = _books;
        mCurrentPage = 1;
        //总页数
        initCommBottom(books.size());
        //分页
        pagingByIndex(mCurrentPage);
        //记录错误图书数量
        if(books.size() == 0){
            //清空记录
            Logger.e("error","清空记录");
            errors.clear();
            trueCount=0;
        }
        errors.add(books.size());
        if(errors.size() != 0 && books.size() != 0){
            if(_books.size() >trueCount ){
                map.put(_books.get(0).getCategory_name(),books.size());

                alertT.showPopupWindow();
                MediaPlayerUtil.playmusic(this, Uri.parse("android.resource://"
                        + this.getPackageName() + "/" + R.raw.play_true));
                trueCount=_books.size();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alertT.dismiss();
                    }
                }, 2000);

            }
        }

    }

    //垃圾投放错误时发生的情况
    @Override
    public void trueBooks(List<Book> _books){
        this.books = _books;
        //记录正确图书数量
        if(books.size() == 0){
            //清空记录
            trues.clear();
            falseCount=0;
        }
        trues.add(books.size());
        if(trues.size() != 0 && books.size() != 0){
            if(_books.size() >falseCount){
                alertF.showPopupWindow();
                MediaPlayerUtil.playmusic(this, Uri.parse("android.resource://"
                        + this.getPackageName() + "/" + R.raw.play_false));
                falseCount=_books.size();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alertF.dismiss();
                    }
                }, 2000);
            }
        }
    }
    //上一页
    @Override
    public void previousClick() {
        if(mCurrentPage > 1){
            mCurrentPage = mCurrentPage - 1;
            pagingByIndex(mCurrentPage);
        }
    }

    //跳转到多少页
    @Override
    public void jumpviousClick(int etPage, int total) {
        mCurrentPage = etPage;
        pagingByIndex(mCurrentPage);
    }

    //下一页
    @Override
    public void nextClick() {
        if(mCurrentPage < mTotalPage){
            mCurrentPage = mCurrentPage + 1;
            pagingByIndex(mCurrentPage);
        }
    }

    //触摸其他地方，软键盘隐藏，输入框失去焦点
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            //键盘是否显示
            if (KeyBoardUtils.isShouldHideInput(view, ev)) {
                //隐藏键盘，取消焦点，更新用户昵称
                KeyBoardUtils.hideSoftInput(this, view);
                commbottom.getCurrentPageEt().clearFocus();
            }
            return super.dispatchTouchEvent(ev);
        }
        //必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    @Override
    public void relateSuccess(Set<String> tags) {

    }

    //初始化页数
    private void initCommBottom(int totalNum){
        int totalPage = 0;
        if(totalNum % Constant.LIMIT != 0){
            totalPage = totalNum / Constant.LIMIT + 1;
        }else {
            totalPage = totalNum / Constant.LIMIT;
        }
        if(totalPage == 0){
            totalPage = 1;
        }
        this.mTotalPage = totalPage;
        commbottom.setTotalPage(mTotalPage);
    }

    //根据页数
    private void pagingByIndex(int page){
        int fromIndex = (page - 1) * Constant.LIMIT;
        int toIndex = Math.min(books.size(),page * Constant.LIMIT);
        try{
            adapter.setDataList(books.subList(fromIndex,toIndex));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
