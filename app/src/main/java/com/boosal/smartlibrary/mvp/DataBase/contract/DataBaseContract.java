package com.boosal.smartlibrary.mvp.DataBase.contract;

import com.boosal.smartlibrary.base.baseMVP.model.IBaseModel;
import com.boosal.smartlibrary.base.baseMVP.presenter.IBasePresenter;
import com.boosal.smartlibrary.base.baseMVP.view.IBaseView;
import com.boosal.smartlibrary.net.entity.local.book.Book;
import com.boosal.smartlibrary.net.entity.local.book.Category;
import com.boosal.smartlibrary.net.entity.local.book.Label;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataBaseContract {

    public interface View extends IBaseView {
        void relateSuccess(Set<String> tags);
//        void relateFailed(Set<String> tags);
        void errorBooks(List<Book>books);

        void trueBooks(List<Book>books);
    }

    public interface Presenter extends IBasePresenter {
        //********************************Table Book**************************/
        void saveBook(Book book);
        int getTotalPage();
        List<Book> getBooksByKey(String key,int limit,int offset);
        boolean deleteBook(Book book);
        //********************************Table Category *********************/
        void saveCategory(String name);
        List<Category> getAllCategories();
        //********************************Table Label**************************/
//        int getCountOfLabelsByisbn(String isbn);
        //key:rfid value:isbn
        void saveLabel(Map<String,String> map);
        //根据查询标签获取isbn，根据isbn获取图书具体信息
        void findErrorBookByrfid(Set<String> tags,String categoryName);
    }

    public interface Model extends IBaseModel {
        //********************************Table Book**************************/
        //根据isbn判断表中是否已经存在此书
        Book findBookByIsbn(String isbn);
        void saveBook(Book book);
        //获取图书总数
        int getCountOfBooks();
        //获取所有图书:关键词，分页
        List<Book> getBooksByKey(String key,int limit,int offset);
        //根据isbn删除某本图书
        boolean deleteBookByIsbn(String isbn);
        //根据类别查询图书
        List<Book> findBookByCategory(String category_name);
        //********************************Table Category *********************/
        //根据category_name判断表中是否存在此类别
        Category findCategoryByname(String name);
        void saveCategory(String name);
        List<Category> getAllCategories();
        void deleteCategoryByName(String name);
        //********************************Table Label**************************/
        //根据book_isbn查找标签数量
        int getCountOfLabelsByisbn(String isbn);
        //查询标签
        Label findLabelByTag(String tag);
        boolean saveLabel(String ibsn,String rfid);
        //根据查询标签获取isbn，根据isbn获取图书具体信息
        //根据book_isbn删除标签
        void deleteLabelByBookIsbn(String isbn);
    }

}
