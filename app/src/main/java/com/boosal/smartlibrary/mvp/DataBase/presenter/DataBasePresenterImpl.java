package com.boosal.smartlibrary.mvp.DataBase.presenter;

import com.boosal.smartlibrary.base.baseApp.Constant;
import com.boosal.smartlibrary.mvp.DataBase.contract.DataBaseContract;
import com.boosal.smartlibrary.mvp.DataBase.model.DataBaseModelImpl;
import com.boosal.smartlibrary.net.entity.local.book.Book;
import com.boosal.smartlibrary.net.entity.local.book.Category;
import com.boosal.smartlibrary.net.entity.local.book.Label;
import com.boosal.smartlibrary.utils.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataBasePresenterImpl implements DataBaseContract.Presenter{

    private DataBaseContract.View mView;
    private DataBaseContract.Model mModel;

    private int count=0;
    private Set<Book> tempSet = new HashSet<>();

    public DataBasePresenterImpl(DataBaseContract.View view) {
        this.mView = view;
        mModel=new DataBaseModelImpl();}

    @Override
    public void onDestroy() {

    }

    @Override
    public void saveBook(Book book) {
        //判断是否存在isbn
        if(mModel.findBookByIsbn(book.getIsbn()) == null){
            //保存
            mModel.saveBook(book);
        }
    }

    @Override
    public int getTotalPage() {
        int totalPage = 0;
        if(mModel.getCountOfBooks() % Constant.LIMIT != 0){
            totalPage = mModel.getCountOfBooks() / Constant.LIMIT + 1;
        }else {
            totalPage = mModel.getCountOfBooks() / Constant.LIMIT;
        }
        if(totalPage == 0){
            totalPage = 1;
        }
        return totalPage;
    }

    @Override
    public List<Book> getBooksByKey(String key, int limit, int offset) {
        return mModel.getBooksByKey(key,limit,offset);
    }

    @Override
    public boolean deleteBook(Book book) {
        boolean flag = false;
        if(book == null){
            return flag;
        }
        //首先根据isbn删除某本图书
        flag = mModel.deleteBookByIsbn(book.getIsbn());
        //根据isbn删除已保存的标签
        mModel.deleteLabelByBookIsbn(book.getIsbn());
        //根据类别查询图书
        List<Book> books = mModel.findBookByCategory(book.getCategory_name());
        if(books == null || books.size() == 0){
            //当前类别下无图书,删除类别
            mModel.deleteCategoryByName(book.getCategory_name());
        }
        return flag;
    }

    @Override
    public void saveCategory(String name) {
        //判断是否存在isbn
        if(mModel.findCategoryByname(name) == null){
            //保存
            mModel.saveCategory(name);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = mModel.getAllCategories();
        if(categories == null || categories.size() == 0){
            categories = new ArrayList<>();
            categories.add(new Category("暂无类别"));
            return categories;
        }
        return categories;
    }

//    @Override
//    public int getCountOfLabelsByisbn(String isbn) {
//        return mModel.getCountOfLabelsByisbn(isbn);
//    }

    //key:rfid value:isbn
    @Override
    public void saveLabel(Map<String,String> map) {
        Set<String> tags =new HashSet<>();
        Set<String> failedTags =new HashSet<>();
        for(String key:map.keySet()){
            if(mModel.findLabelByTag(key) == null){
                if(mModel.saveLabel(map.get(key),key)){
                    //保存成功
                    tags.add(key);
                }
            }
        }
        if(tags.size() == 0){
            return;
        }
        mView.relateSuccess(tags);
    }

    @Override
    public void findErrorBookByrfid(Set<String> tags,String categoryName) {
        List<Book> books = new ArrayList<>();
        List<Book> books2=new ArrayList<>();
        for(String tag:tags){
            //首先根据标签查询isbn
            Label label = mModel.findLabelByTag(tag);
            if(label != null){
                //根据isbn查询书本信息
                Book book = mModel.findBookByIsbn(label.getBook_isbn());
                if(book != null){
                    if(book.getCategory_name().equals(categoryName)){
                        books.add(book);
                    } else if(!book.getCategory_name().equals(categoryName)){
                        books2.add(book);
                    }
                }
            }
        }

        tempSet.clear();
        tempSet.addAll(books);
        for(Book book:tempSet){
            book.setErrorTimes(Collections.frequency(books, book));
        }
        books.clear();
        books.addAll(tempSet);
        mView.errorBooks(books);
        mView.trueBooks(books2);
        books.clear();
        books2.clear();
       // books.clear();
    }
}
