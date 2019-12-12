package com.boosal.smartlibrary.mvp.DataBase.model;


import com.boosal.smartlibrary.mvp.DataBase.contract.DataBaseContract;
import com.boosal.smartlibrary.net.entity.local.book.Book;
import com.boosal.smartlibrary.net.entity.local.book.Category;
import com.boosal.smartlibrary.net.entity.local.book.Label;

import org.litepal.LitePal;

import java.util.List;

public class DataBaseModelImpl implements DataBaseContract.Model{

    public DataBaseModelImpl() {
    }


    @Override
    public Book findBookByIsbn(String isbn) {
        Book book = LitePal.where("isbn = ?",isbn).findFirst(Book.class);
        return book;
    }

    @Override
    public void saveBook(Book book) {//图片，书名，书号，出版社，出版时间，类别
        Book _book = new Book();
        _book.setImgpath(book.getImgpath());
        _book.setName(book.getName());
        _book.setIsbn(book.getIsbn());
        _book.setPress(book.getPress());
        _book.setPublish_time(book.getPublish_time());
        _book.setCategory_name(book.getCategory_name());
        _book.save();
    }

    @Override
    public int getCountOfBooks() {
        return LitePal.count(Book.class);
    }

    @Override
    public List<Book> getBooksByKey(String key, int limit, int offset) {
        List<Book> books = LitePal.where("name like ?" ,"%"+key+"%").order("id desc").limit(limit).offset(offset).find(Book.class);
        return books;
    }

    @Override
    public boolean deleteBookByIsbn(String isbn) {
        //@return The number of rows affected.
        int affectedRow = LitePal.deleteAll(Book.class,"isbn = ?",isbn);
        if(affectedRow != 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Book> findBookByCategory(String category_name) {
        List<Book> books = LitePal.where("category_name = ?" ,category_name).find(Book.class);
        return books;
    }

    @Override
    public Category findCategoryByname(String name) {
        Category category = LitePal.where("name = ?",name).findFirst(Category.class);
        return category;
    }

    @Override
    public void saveCategory(String name) {
        Category category = new Category();
        category.setName(name);
        category.save();
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = LitePal.findAll(Category.class);
        return categories;
    }

    @Override
    public void deleteCategoryByName(String name) {
        LitePal.deleteAll(Category.class,"name = ?",name);
    }

    @Override
    public int getCountOfLabelsByisbn(String isbn) {
        return LitePal.where("book_isbn = ?",isbn).count(Label.class);
    }

    @Override
    public Label findLabelByTag(String rfid) {
        Label label = LitePal.where("rfid = ?",rfid).findFirst(Label.class);
        return label;
    }

    @Override
    public boolean saveLabel(String ibsn,String rfid) {
        Label label = new Label();
        label.setRfid(rfid);
        label.setBook_isbn(ibsn);
        return label.save();
    }

    @Override
    public void deleteLabelByBookIsbn(String isbn) {
        LitePal.deleteAll(Label.class,"book_isbn = ?",isbn);
    }
}
