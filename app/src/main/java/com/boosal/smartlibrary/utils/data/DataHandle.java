package com.boosal.smartlibrary.utils.data;

import android.os.Handler;
import android.os.Looper;

import com.boosal.smartlibrary.net.entity.local.book.Book;
import com.boosal.smartlibrary.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;

/**
 * Created by boosal on 2019/9/7.
 */

public class DataHandle {

    private ExcelUtil util;
    private ParseExcelListener listener;
    private Handler mUiHandler;


    public DataHandle(ParseExcelListener listener) {
        this.listener = listener;
        util = new ExcelUtil();
        mUiHandler = new Handler(Looper.getMainLooper());
    }

    public interface ParseExcelListener{
        void parseError(String error);
        void parseResult(Book book);
    }

    public void parseExcel(String path){
        Logger.e("excel","路径："+path);
        //获取Sheet对象
        Sheet sheet = util.getSheetByPath(path);
        if(sheet == null){
            mUiHandler.post(new Runnable() {
                @Override
                public void run() {
                    if( null != listener){
                        listener.parseError("获取Excel对象失败");
                    }
                }
            });
            return;
        }
        //判断列数量是否一致
        if(sheet.getColumns() != 6) {
            mUiHandler.post(new Runnable() {
                @Override
                public void run() {
                    if( null != listener){
                        listener.parseError("请检查Excel格式");
                    }
                }
            });
            return ;
        }
        //获取图片集合
        Map<String,byte[]> indexPicMap = util.getSheetPictures(sheet);
        //获取数据总行数
        int totalRowNum = sheet.getRows();
        //获取所有数据
        for(int i= 1;i<totalRowNum ;i++) {
            //获取第i行对象
            Cell[] cells = sheet.getRow(i);
            Book book = new Book(cells);
            //第1列图片
            byte[] picData = indexPicMap.get(String.valueOf(i));
            book.setImgpath(picData);
            mUiHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(null != listener){
                        listener.parseResult(book);
                    }
                }
            });
        }
        util.realseObject();
    }
}
