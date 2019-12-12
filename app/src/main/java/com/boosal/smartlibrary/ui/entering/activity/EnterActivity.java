package com.boosal.smartlibrary.ui.entering.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.boosal.smartlibrary.R;
import com.boosal.smartlibrary.base.baseApp.BaseApplication;
import com.boosal.smartlibrary.base.baseUI.BaseActivity;
import com.boosal.smartlibrary.mvp.DataBase.contract.DataBaseContract;
import com.boosal.smartlibrary.mvp.DataBase.presenter.DataBasePresenterImpl;
import com.boosal.smartlibrary.mvp.Tag.contract.TagContract;
import com.boosal.smartlibrary.mvp.Tag.presenter.TagPresenterImpl;
import com.boosal.smartlibrary.net.entity.local.book.Book;
import com.boosal.smartlibrary.ui.entering.adapter.BookAdapter;
import com.boosal.smartlibrary.utils.FileHelpUtil;
import com.boosal.smartlibrary.utils.KeyBoardUtils;
import com.boosal.smartlibrary.utils.Logger;
import com.boosal.smartlibrary.utils.PathUtil;
import com.boosal.smartlibrary.utils.data.DataHandle;
import com.boosal.smartlibrary.view.CommBottom;
import com.boosal.smartlibrary.widget.popup.DeleteBookPopup;
import com.boosal.smartlibrary.widget.popup.RelatedSuccessPopup;
import com.boosal.smartlibrary.widget.popup.SelectFilePopup;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import razerdp.basepopup.BasePopupWindow;

import static com.boosal.smartlibrary.base.baseApp.Constant.LIMIT;

public class EnterActivity extends BaseActivity implements CommBottom.CommBottomListener
        ,SelectFilePopup.SelectFileListner,DataHandle.ParseExcelListener,DataBaseContract.View,
        TagContract.View,BookAdapter.BookListener,DeleteBookPopup.DeleteBookListener{

    @BindView(R.id.commbottom)
    CommBottom commbottom;
    @BindView(R.id.enter_rv_books)
    RecyclerView enterRvBooks;

    public static final int REQUEST_FILE = 101 ;

    private DataBaseContract.Presenter dbPresenter;
    private SelectFilePopup selectFilePopup;
    private DataHandle dataHandle;
    private BookAdapter adapter;

    private int currentOffset;//偏移量为0，4，8，12...
    //关键词搜索
    private String key = "";

    private TagContract.Presenter tagPresenter;
    private Book mSelectBook = null;
    private Map<String,String> relatedMap = new HashMap<>();
    private RelatedSuccessPopup successPopup;

    //删除 or 导入
    private static boolean MODE_DEL = false;
    private DeleteBookPopup deleteBookPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_emter);
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
        setTopTitle(R.string.top_title_main, R.color.themeWhite);
        setLeftButtonImage(R.drawable.back);
        setLeftButtonText(R.string.back);
        setRightButtonImage(R.drawable._import);
        setRightButtonText(R.string._import, R.color.themeWhite);

        selectFilePopup = new SelectFilePopup(this,this);
        selectFilePopup.setOutSideDismiss(false);
        successPopup = new RelatedSuccessPopup(this,this);
        successPopup.setOutSideDismiss(false);
        deleteBookPopup = new DeleteBookPopup(this,this);
        deleteBookPopup.setOutSideDismiss(false);
        dataHandle = new DataHandle(this);
        dbPresenter = new DataBasePresenterImpl(this);
        tagPresenter = new TagPresenterImpl(this);
    }

    @Override
    protected void init() {
        //初始化已录入的图书
        adapter = new BookAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        enterRvBooks.setLayoutManager(layoutManager);
        enterRvBooks.setAdapter(adapter);
        //添加分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_line));
        enterRvBooks.addItemDecoration(divider);

        adapter.setDataList(dbPresenter.getBooksByKey(key,LIMIT,currentOffset));
        initCommButton();
    }

    @Override
    protected void initEvents() {
        commbottom.setListener(this);
        deleteBookPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Logger.e("delete","开始轮询");
                tagPresenter.loopQueryTag(BaseApplication.getTagReader());
            }
        });
    }

    //点击导入
    @Override
    public void onRightButtonClick(View view) {
        if(MODE_DEL && mSelectBook != null){//删除
            Logger.e("delete","停止轮询");
            tagPresenter.cancelLoop();
            deleteBookPopup.setComm(mSelectBook.getName());
            deleteBookPopup.showPopupWindow();
        }else {//导入
            goToSelectFile();
        }
    }

    //文件选择回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_FILE) {
            if(data != null){
                Uri uri = data.getData();
                String usbPath = PathUtil.getStoragePath(this,PathUtil.USB);
                try {
                    String[] path = FileHelpUtil.getPath(this, uri).split("/");
                    String real_path = usbPath + "/" + path[path.length - 1];
                    Logger.e("file_path",real_path);
                    if(path != null){
                        File file = new File(real_path);
                        selectFilePopup.setComm(file.getName(),real_path);
                        selectFilePopup.showPopupWindow();
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //弹窗确认是否删除图书
    @Override
    public void confirm() {
        if(dbPresenter.deleteBook(mSelectBook)){
            //更新显示
            initCommButton();
            adapter.setDataList(dbPresenter.getBooksByKey(key,LIMIT,currentOffset));
            showMsg("删除成功！");
        }
    }

    //弹窗确认重新选择文件
    @Override
    public void selectFile() {
        goToSelectFile();
    }

    //确认导入
    @Override
    public void confirm(String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dataHandle.parseExcel(path);
            }
        }).start();
    }

    //解析Excel出错
    @Override
    public void parseError(String error) {
        if(!TextUtils.isEmpty(error)){
            showMsg(error);
        }
    }

    //解析结果
    @Override
    public void parseResult(Book book) {
        //保存书单信息
        dbPresenter.saveBook(book);
        //保存类别信息
        dbPresenter.saveCategory(book.getCategory_name());
        //更新显示
        initCommButton();
        adapter.setDataList(dbPresenter.getBooksByKey(key,LIMIT,currentOffset));
    }

    //选择某本图书
    @Override
    public void selectedBook(Book book) {
        mSelectBook = book;
        //删除模式
        MODE_DEL = true;
        setRightButtonImage(R.drawable.del);
        setRightButtonText("", R.color.themeWhite);
    }

    //rfid回调//key:rfid value:isbn
    @Override
    public void setTags(Set<String> tags) {
        Logger.e("tag_a","数量:"+tags.size());
        if(mSelectBook != null){
            relatedMap.clear();
            for(String tag:tags){
                relatedMap.put(tag,mSelectBook.getIsbn());
            }
            dbPresenter.saveLabel(relatedMap);
        }
    }

    //绑定成功
    @Override
    public void relateSuccess(Set<String> tags) {
        adapter.notifyDataSetChanged();
        successPopup.setComm(mSelectBook);
        if(!successPopup.isShowing()){
            successPopup.showPopupWindow();
        }
    }

    //点击上一页
    @Override
    public void previousClick() {
        if((currentOffset/LIMIT + 1) > 1){
            currentOffset = currentOffset - LIMIT;
            commbottom.setCurrentPage(currentOffset / LIMIT + 1);
            adapter.setDataList(dbPresenter.getBooksByKey(key,LIMIT,currentOffset));
        }
    }

    //点击跳转
    @Override
    public void jumpviousClick(int etPage, int total) {
        currentOffset = (etPage - 1) * LIMIT;
        adapter.setDataList(dbPresenter.getBooksByKey(key,LIMIT,currentOffset));
    }

    //下一页
    @Override
    public void nextClick() {
        if((currentOffset/LIMIT + 1)< dbPresenter.getTotalPage()){
            currentOffset = currentOffset + LIMIT;
            commbottom.setCurrentPage(currentOffset / LIMIT + 1);
            adapter.setDataList(dbPresenter.getBooksByKey(key,LIMIT,currentOffset));
        }
    }

    private void initCommButton(){
        //当前页数，总页数
        currentOffset = 0;
        commbottom.setTotalPage(dbPresenter.getTotalPage());
        commbottom.setCurrentPage(currentOffset / LIMIT + 1);
    }

    //跳转文件选择
    private void goToSelectFile(){
        String[] mimeTypes = {"application/vnd.ms-excel"};
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "请选择文件"), REQUEST_FILE);
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
    public void errorBooks(List<Book> books) {

    }
    @Override
    public void trueBooks(List<Book> books){

    }
}
